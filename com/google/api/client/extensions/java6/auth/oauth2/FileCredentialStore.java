package com.google.api.client.extensions.java6.auth.oauth2;

import java.util.logging.*;
import java.util.concurrent.locks.*;
import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.store.*;

@Deprecated
@Beta
public class FileCredentialStore implements CredentialStore
{
    private static final Logger LOGGER;
    private final JsonFactory jsonFactory;
    private final Lock lock;
    private FilePersistedCredentials credentials;
    private final File file;
    private static final boolean IS_WINDOWS;
    
    public FileCredentialStore(final File file, final JsonFactory jsonFactory) throws IOException {
        super();
        this.lock = new ReentrantLock();
        this.credentials = new FilePersistedCredentials();
        this.file = Preconditions.checkNotNull(file);
        this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        final File parentDir = file.getCanonicalFile().getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            final String value = String.valueOf(String.valueOf(parentDir));
            throw new IOException(new StringBuilder(35 + value.length()).append("unable to create parent directory: ").append(value).toString());
        }
        if (this.isSymbolicLink(file)) {
            final String value2 = String.valueOf(String.valueOf(file));
            throw new IOException(new StringBuilder(31 + value2.length()).append("unable to use a symbolic link: ").append(value2).toString());
        }
        if (!file.createNewFile()) {
            this.loadCredentials(file);
        }
        else {
            if (!file.setReadable(false, false) || !file.setWritable(false, false) || !file.setExecutable(false, false)) {
                final Logger logger = FileCredentialStore.LOGGER;
                final String value3 = String.valueOf(String.valueOf(file));
                logger.warning(new StringBuilder(49 + value3.length()).append("unable to change file permissions for everybody: ").append(value3).toString());
            }
            if (!file.setReadable(true) || !file.setWritable(true)) {
                final String value4 = String.valueOf(String.valueOf(file));
                throw new IOException(new StringBuilder(32 + value4.length()).append("unable to set file permissions: ").append(value4).toString());
            }
            this.save();
        }
    }
    
    protected boolean isSymbolicLink(final File file) throws IOException {
        if (FileCredentialStore.IS_WINDOWS) {
            return false;
        }
        File canonical = file;
        if (file.getParent() != null) {
            canonical = new File(file.getParentFile().getCanonicalFile(), file.getName());
        }
        return !canonical.getCanonicalFile().equals(canonical.getAbsoluteFile());
    }
    
    public void store(final String userId, final Credential credential) throws IOException {
        this.lock.lock();
        try {
            this.credentials.store(userId, credential);
            this.save();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public void delete(final String userId, final Credential credential) throws IOException {
        this.lock.lock();
        try {
            this.credentials.delete(userId);
            this.save();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public boolean load(final String userId, final Credential credential) {
        this.lock.lock();
        try {
            return this.credentials.load(userId, credential);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    private void loadCredentials(final File file) throws IOException {
        final FileInputStream is = new FileInputStream(file);
        try {
            this.credentials = this.jsonFactory.fromInputStream(is, FilePersistedCredentials.class);
        }
        finally {
            is.close();
        }
    }
    
    private void save() throws IOException {
        final FileOutputStream fos = new FileOutputStream(this.file);
        try {
            final JsonGenerator generator = this.jsonFactory.createJsonGenerator(fos, Charsets.UTF_8);
            generator.serialize(this.credentials);
            generator.close();
        }
        finally {
            fos.close();
        }
    }
    
    public final void migrateTo(final FileDataStoreFactory dataStoreFactory) throws IOException {
        this.migrateTo(StoredCredential.getDefaultDataStore(dataStoreFactory));
    }
    
    public final void migrateTo(final DataStore<StoredCredential> credentialDataStore) throws IOException {
        this.credentials.migrateTo(credentialDataStore);
    }
    
    static {
        LOGGER = Logger.getLogger(FileCredentialStore.class.getName());
        IS_WINDOWS = (File.separatorChar == '\\');
    }
}
