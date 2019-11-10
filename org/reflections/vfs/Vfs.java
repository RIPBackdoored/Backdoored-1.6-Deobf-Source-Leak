package org.reflections.vfs;

import org.reflections.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;
import javax.annotation.*;
import java.util.jar.*;
import java.net.*;
import org.reflections.util.*;
import java.io.*;

public abstract class Vfs
{
    private static List<UrlType> defaultUrlTypes;
    
    public Vfs() {
        super();
    }
    
    public static List<UrlType> getDefaultUrlTypes() {
        return Vfs.defaultUrlTypes;
    }
    
    public static void setDefaultURLTypes(final List<UrlType> urlTypes) {
        Vfs.defaultUrlTypes = urlTypes;
    }
    
    public static void addDefaultURLTypes(final UrlType urlType) {
        Vfs.defaultUrlTypes.add(0, urlType);
    }
    
    public static Dir fromURL(final URL url) {
        return fromURL(url, Vfs.defaultUrlTypes);
    }
    
    public static Dir fromURL(final URL url, final List<UrlType> urlTypes) {
        for (final UrlType type : urlTypes) {
            try {
                if (!type.matches(url)) {
                    continue;
                }
                final Dir dir = type.createDir(url);
                if (dir != null) {
                    return dir;
                }
                continue;
            }
            catch (Throwable e) {
                if (Reflections.log == null) {
                    continue;
                }
                Reflections.log.warn("could not create Dir using " + type + " from url " + url.toExternalForm() + ". skipping.", e);
            }
        }
        throw new ReflectionsException("could not create Vfs.Dir from url, no matching UrlType was found [" + url.toExternalForm() + "]\neither use fromURL(final URL url, final List<UrlType> urlTypes) or use the static setDefaultURLTypes(final List<UrlType> urlTypes) or addDefaultURLTypes(UrlType urlType) with your specialized UrlType.");
    }
    
    public static Dir fromURL(final URL url, final UrlType... urlTypes) {
        return fromURL(url, Lists.newArrayList(urlTypes));
    }
    
    public static Iterable<File> findFiles(final Collection<URL> inUrls, final String packagePrefix, final Predicate<String> nameFilter) {
        final Predicate<File> fileNamePredicate = new Predicate<File>() {
            final /* synthetic */ String val$packagePrefix;
            final /* synthetic */ Predicate val$nameFilter;
            
            Vfs$1() {
                super();
            }
            
            @Override
            public boolean apply(final File file) {
                final String path = file.getRelativePath();
                if (path.startsWith(packagePrefix)) {
                    final String filename = path.substring(path.indexOf(packagePrefix) + packagePrefix.length());
                    return !Utils.isEmpty(filename) && nameFilter.apply(filename.substring(1));
                }
                return false;
            }
            
            @Override
            public /* bridge */ boolean apply(final Object o) {
                return this.apply((File)o);
            }
        };
        return findFiles(inUrls, fileNamePredicate);
    }
    
    public static Iterable<File> findFiles(final Collection<URL> inUrls, final Predicate<File> filePredicate) {
        Iterable<File> result = new ArrayList<File>();
        for (final URL url : inUrls) {
            try {
                result = Iterables.concat((Iterable<? extends File>)result, (Iterable<? extends File>)Iterables.filter((Iterable<? extends T>)new Iterable<File>() {
                    final /* synthetic */ URL val$url;
                    
                    Vfs$2() {
                        super();
                    }
                    
                    @Override
                    public Iterator<File> iterator() {
                        return Vfs.fromURL(url).getFiles().iterator();
                    }
                }, (Predicate<? super T>)filePredicate));
            }
            catch (Throwable e) {
                if (Reflections.log == null) {
                    continue;
                }
                Reflections.log.error("could not findFiles for url. continuing. [" + url + "]", e);
            }
        }
        return result;
    }
    
    @Nullable
    public static java.io.File getFile(final URL url) {
        try {
            final String path = url.toURI().getSchemeSpecificPart();
            final java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (URISyntaxException ex) {}
        try {
            String path = URLDecoder.decode(url.getPath(), "UTF-8");
            if (path.contains(".jar!")) {
                path = path.substring(0, path.lastIndexOf(".jar!") + ".jar".length());
            }
            final java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (UnsupportedEncodingException ex2) {}
        try {
            String path = url.toExternalForm();
            if (path.startsWith("jar:")) {
                path = path.substring("jar:".length());
            }
            if (path.startsWith("wsjar:")) {
                path = path.substring("wsjar:".length());
            }
            if (path.startsWith("file:")) {
                path = path.substring("file:".length());
            }
            if (path.contains(".jar!")) {
                path = path.substring(0, path.indexOf(".jar!") + ".jar".length());
            }
            java.io.File file;
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
            path = path.replace("%20", " ");
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (Exception ex3) {}
        return null;
    }
    
    private static boolean hasJarFileInPath(final URL url) {
        return url.toExternalForm().matches(".*\\.jar(\\!.*|$)");
    }
    
    static /* synthetic */ boolean access$100(final URL x0) {
        return hasJarFileInPath(x0);
    }
    
    static {
        Vfs.defaultUrlTypes = (List<UrlType>)Lists.newArrayList(DefaultUrlTypes.values());
    }
    
    public enum DefaultUrlTypes implements UrlType
    {
        jarFile {
            Vfs$DefaultUrlTypes$1(final String x0, final int x2) {
            }
            
            @Override
            public boolean matches(final URL url) {
                return url.getProtocol().equals("file") && hasJarFileInPath(url);
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new ZipDir(new JarFile(Vfs.getFile(url)));
            }
        }, 
        jarUrl {
            Vfs$DefaultUrlTypes$2(final String x0, final int x2) {
            }
            
            @Override
            public boolean matches(final URL url) {
                return "jar".equals(url.getProtocol()) || "zip".equals(url.getProtocol()) || "wsjar".equals(url.getProtocol());
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                try {
                    final URLConnection urlConnection = url.openConnection();
                    if (urlConnection instanceof JarURLConnection) {
                        return new ZipDir(((JarURLConnection)urlConnection).getJarFile());
                    }
                }
                catch (Throwable t) {}
                final java.io.File file = Vfs.getFile(url);
                if (file != null) {
                    return new ZipDir(new JarFile(file));
                }
                return null;
            }
        }, 
        directory {
            Vfs$DefaultUrlTypes$3(final String x0, final int x2) {
            }
            
            @Override
            public boolean matches(final URL url) {
                if (url.getProtocol().equals("file") && !hasJarFileInPath(url)) {
                    final java.io.File file = Vfs.getFile(url);
                    return file != null && file.isDirectory();
                }
                return false;
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new SystemDir(Vfs.getFile(url));
            }
        }, 
        jboss_vfs {
            Vfs$DefaultUrlTypes$4(final String x0, final int x2) {
            }
            
            @Override
            public boolean matches(final URL url) {
                return url.getProtocol().equals("vfs");
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                final Object content = url.openConnection().getContent();
                final Class<?> virtualFile = ClasspathHelper.contextClassLoader().loadClass("org.jboss.vfs.VirtualFile");
                final java.io.File physicalFile = (java.io.File)virtualFile.getMethod("getPhysicalFile", (Class<?>[])new Class[0]).invoke(content, new Object[0]);
                final String name = (String)virtualFile.getMethod("getName", (Class<?>[])new Class[0]).invoke(content, new Object[0]);
                java.io.File file = new java.io.File(physicalFile.getParentFile(), name);
                if (!file.exists() || !file.canRead()) {
                    file = physicalFile;
                }
                return file.isDirectory() ? new SystemDir(file) : new ZipDir(new JarFile(file));
            }
        }, 
        jboss_vfsfile {
            Vfs$DefaultUrlTypes$5(final String x0, final int x2) {
            }
            
            @Override
            public boolean matches(final URL url) throws Exception {
                return "vfszip".equals(url.getProtocol()) || "vfsfile".equals(url.getProtocol());
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new UrlTypeVFS().createDir(url);
            }
        }, 
        bundle {
            Vfs$DefaultUrlTypes$6(final String x0, final int x2) {
            }
            
            @Override
            public boolean matches(final URL url) throws Exception {
                return url.getProtocol().startsWith("bundle");
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return Vfs.fromURL((URL)ClasspathHelper.contextClassLoader().loadClass("org.eclipse.core.runtime.FileLocator").getMethod("resolve", URL.class).invoke(null, url));
            }
        }, 
        jarInputStream {
            Vfs$DefaultUrlTypes$7(final String x0, final int x2) {
            }
            
            @Override
            public boolean matches(final URL url) throws Exception {
                return url.toExternalForm().contains(".jar");
            }
            
            @Override
            public Dir createDir(final URL url) throws Exception {
                return new JarInputDir(url);
            }
        };
        
        private static final /* synthetic */ DefaultUrlTypes[] $VALUES;
        
        public static DefaultUrlTypes[] values() {
            return DefaultUrlTypes.$VALUES.clone();
        }
        
        public static DefaultUrlTypes valueOf(final String name) {
            return Enum.valueOf(DefaultUrlTypes.class, name);
        }
        
        DefaultUrlTypes(final String x0, final int x1, final Vfs$1 x2) {
            this();
        }
        
        static {
            $VALUES = new DefaultUrlTypes[] { DefaultUrlTypes.jarFile, DefaultUrlTypes.jarUrl, DefaultUrlTypes.directory, DefaultUrlTypes.jboss_vfs, DefaultUrlTypes.jboss_vfsfile, DefaultUrlTypes.bundle, DefaultUrlTypes.jarInputStream };
        }
    }
    
    public interface UrlType
    {
        boolean matches(final URL p0) throws Exception;
        
        Dir createDir(final URL p0) throws Exception;
    }
    
    public interface Dir
    {
        String getPath();
        
        Iterable<File> getFiles();
        
        void close();
    }
    
    public interface File
    {
        String getName();
        
        String getRelativePath();
        
        InputStream openInputStream() throws IOException;
    }
}
