package com.google.api.client.util.store;

import java.util.logging.*;
import java.lang.reflect.*;
import com.google.api.client.util.*;
import java.util.*;
import java.io.*;

public class FileDataStoreFactory extends AbstractDataStoreFactory
{
    private static final Logger LOGGER;
    private final File dataDirectory;
    
    public FileDataStoreFactory(File dataDirectory) throws IOException {
        super();
        dataDirectory = dataDirectory.getCanonicalFile();
        this.dataDirectory = dataDirectory;
        if (IOUtils.isSymbolicLink(dataDirectory)) {
            throw new IOException("unable to use a symbolic link: " + dataDirectory);
        }
        if (!dataDirectory.exists() && !dataDirectory.mkdirs()) {
            throw new IOException("unable to create directory: " + dataDirectory);
        }
        setPermissionsToOwnerOnly(dataDirectory);
    }
    
    public final File getDataDirectory() {
        return this.dataDirectory;
    }
    
    @Override
    protected <V extends Serializable> DataStore<V> createDataStore(final String id) throws IOException {
        return new FileDataStore<V>(this, this.dataDirectory, id);
    }
    
    static void setPermissionsToOwnerOnly(final File file) throws IOException {
        try {
            final Method setReadable = File.class.getMethod("setReadable", Boolean.TYPE, Boolean.TYPE);
            final Method setWritable = File.class.getMethod("setWritable", Boolean.TYPE, Boolean.TYPE);
            final Method setExecutable = File.class.getMethod("setExecutable", Boolean.TYPE, Boolean.TYPE);
            if (!(boolean)setReadable.invoke(file, false, false) || !(boolean)setWritable.invoke(file, false, false) || !(boolean)setExecutable.invoke(file, false, false)) {
                FileDataStoreFactory.LOGGER.warning("unable to change permissions for everybody: " + file);
            }
            if (!(boolean)setReadable.invoke(file, true, true) || !(boolean)setWritable.invoke(file, true, true) || !(boolean)setExecutable.invoke(file, true, true)) {
                FileDataStoreFactory.LOGGER.warning("unable to change permissions for owner: " + file);
            }
        }
        catch (InvocationTargetException exception) {
            final Throwable cause = exception.getCause();
            Throwables.propagateIfPossible(cause, IOException.class);
            throw new RuntimeException(cause);
        }
        catch (NoSuchMethodException exception2) {
            FileDataStoreFactory.LOGGER.warning("Unable to set permissions for " + file + ", likely because you are running a version of Java prior to 1.6");
        }
        catch (SecurityException ex) {}
        catch (IllegalAccessException ex2) {}
        catch (IllegalArgumentException ex3) {}
    }
    
    static {
        LOGGER = Logger.getLogger(FileDataStoreFactory.class.getName());
    }
    
    static class FileDataStore<V extends Serializable> extends AbstractMemoryDataStore<V>
    {
        private final File dataFile;
        
        FileDataStore(final FileDataStoreFactory dataStore, final File dataDirectory, final String id) throws IOException {
            super(dataStore, id);
            this.dataFile = new File(dataDirectory, id);
            if (IOUtils.isSymbolicLink(this.dataFile)) {
                throw new IOException("unable to use a symbolic link: " + this.dataFile);
            }
            if (this.dataFile.createNewFile()) {
                this.keyValueMap = Maps.newHashMap();
                this.save();
            }
            else {
                this.keyValueMap = IOUtils.deserialize(new FileInputStream(this.dataFile));
            }
        }
        
        @Override
        public void save() throws IOException {
            IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
        }
        
        @Override
        public FileDataStoreFactory getDataStoreFactory() {
            return (FileDataStoreFactory)super.getDataStoreFactory();
        }
        
        @Override
        public /* bridge */ DataStoreFactory getDataStoreFactory() {
            return this.getDataStoreFactory();
        }
    }
}
