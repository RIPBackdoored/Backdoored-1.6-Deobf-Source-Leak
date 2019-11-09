package com.sun.jna;

import java.lang.reflect.*;
import java.lang.ref.*;
import java.util.*;
import java.io.*;

public class NativeLibrary
{
    private long handle;
    private final String libraryName;
    private final String libraryPath;
    private final Map<String, Function> functions;
    final int callFlags;
    private String encoding;
    final Map<String, ?> options;
    private static final Map<String, Reference<NativeLibrary>> libraries;
    private static final Map<String, List<String>> searchPaths;
    private static final List<String> librarySearchPath;
    private static final int DEFAULT_OPEN_OPTIONS = -1;
    
    private static String functionKey(final String name, final int flags, final String encoding) {
        return name + "|" + flags + "|" + encoding;
    }
    
    private NativeLibrary(final String libraryName, final String libraryPath, final long handle, final Map<String, ?> options) {
        super();
        this.functions = new HashMap<String, Function>();
        this.libraryName = this.getLibraryName(libraryName);
        this.libraryPath = libraryPath;
        this.handle = handle;
        final Object option = options.get("calling-convention");
        final int callingConvention = (option instanceof Number) ? ((Number)option).intValue() : 0;
        this.callFlags = callingConvention;
        this.options = options;
        this.encoding = (String)options.get("string-encoding");
        if (this.encoding == null) {
            this.encoding = Native.getDefaultStringEncoding();
        }
        if (Platform.isWindows() && "kernel32".equals(this.libraryName.toLowerCase())) {
            synchronized (this.functions) {
                final Function f = new Function(this, "GetLastError", 63, this.encoding) {
                    final /* synthetic */ NativeLibrary this$0;
                    
                    NativeLibrary$1(final NativeLibrary library, final String functionName, final int callFlags, final String encoding) {
                        this.this$0 = this$0;
                        super(library, functionName, callFlags, encoding);
                    }
                    
                    @Override
                    Object invoke(final Object[] args, final Class<?> returnType, final boolean b, final int fixedArgs) {
                        return Native.getLastError();
                    }
                    
                    @Override
                    Object invoke(final Method invokingMethod, final Class<?>[] paramTypes, final Class<?> returnType, final Object[] inArgs, final Map<String, ?> options) {
                        return Native.getLastError();
                    }
                };
                this.functions.put(functionKey("GetLastError", this.callFlags, this.encoding), f);
            }
        }
    }
    
    private static int openFlags(final Map<String, ?> options) {
        final Object opt = options.get("open-flags");
        if (opt instanceof Number) {
            return ((Number)opt).intValue();
        }
        return -1;
    }
    
    private static NativeLibrary loadLibrary(final String libraryName, final Map<String, ?> options) {
        if (Native.DEBUG_LOAD) {
            System.out.println("Looking for library '" + libraryName + "'");
        }
        final boolean isAbsolutePath = new File(libraryName).isAbsolute();
        final List<String> searchPath = new ArrayList<String>();
        final int openFlags = openFlags(options);
        final String webstartPath = Native.getWebStartLibraryPath(libraryName);
        if (webstartPath != null) {
            if (Native.DEBUG_LOAD) {
                System.out.println("Adding web start path " + webstartPath);
            }
            searchPath.add(webstartPath);
        }
        final List<String> customPaths = NativeLibrary.searchPaths.get(libraryName);
        if (customPaths != null) {
            synchronized (customPaths) {
                searchPath.addAll(0, customPaths);
            }
        }
        if (Native.DEBUG_LOAD) {
            System.out.println("Adding paths from jna.library.path: " + System.getProperty("jna.library.path"));
        }
        searchPath.addAll(initPaths("jna.library.path"));
        String libraryPath = findLibraryPath(libraryName, searchPath);
        long handle = 0L;
        try {
            if (Native.DEBUG_LOAD) {
                System.out.println("Trying " + libraryPath);
            }
            handle = Native.open(libraryPath, openFlags);
        }
        catch (UnsatisfiedLinkError e) {
            if (Native.DEBUG_LOAD) {
                System.out.println("Adding system paths: " + NativeLibrary.librarySearchPath);
            }
            searchPath.addAll(NativeLibrary.librarySearchPath);
        }
        try {
            if (handle == 0L) {
                libraryPath = findLibraryPath(libraryName, searchPath);
                if (Native.DEBUG_LOAD) {
                    System.out.println("Trying " + libraryPath);
                }
                handle = Native.open(libraryPath, openFlags);
                if (handle == 0L) {
                    throw new UnsatisfiedLinkError("Failed to load library '" + libraryName + "'");
                }
            }
        }
        catch (UnsatisfiedLinkError e) {
            if (Platform.isAndroid()) {
                try {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Preload (via System.loadLibrary) " + libraryName);
                    }
                    System.loadLibrary(libraryName);
                    handle = Native.open(libraryPath, openFlags);
                }
                catch (UnsatisfiedLinkError e2) {
                    e = e2;
                }
            }
            else if (Platform.isLinux() || Platform.isFreeBSD()) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Looking for version variants");
                }
                libraryPath = matchLibrary(libraryName, searchPath);
                if (libraryPath != null) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Trying " + libraryPath);
                    }
                    try {
                        handle = Native.open(libraryPath, openFlags);
                    }
                    catch (UnsatisfiedLinkError e2) {
                        e = e2;
                    }
                }
            }
            else if (Platform.isMac() && !libraryName.endsWith(".dylib")) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Looking for matching frameworks");
                }
                libraryPath = matchFramework(libraryName);
                if (libraryPath != null) {
                    try {
                        if (Native.DEBUG_LOAD) {
                            System.out.println("Trying " + libraryPath);
                        }
                        handle = Native.open(libraryPath, openFlags);
                    }
                    catch (UnsatisfiedLinkError e2) {
                        e = e2;
                    }
                }
            }
            else if (Platform.isWindows() && !isAbsolutePath) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Looking for lib- prefix");
                }
                libraryPath = findLibraryPath("lib" + libraryName, searchPath);
                if (libraryPath != null) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Trying " + libraryPath);
                    }
                    try {
                        handle = Native.open(libraryPath, openFlags);
                    }
                    catch (UnsatisfiedLinkError e2) {
                        e = e2;
                    }
                }
            }
            if (handle == 0L) {
                try {
                    final File embedded = Native.extractFromResourcePath(libraryName, (ClassLoader)options.get("classloader"));
                    try {
                        handle = Native.open(embedded.getAbsolutePath(), openFlags);
                        libraryPath = embedded.getAbsolutePath();
                    }
                    finally {
                        if (Native.isUnpacked(embedded)) {
                            Native.deleteLibrary(embedded);
                        }
                    }
                }
                catch (IOException e3) {
                    e = new UnsatisfiedLinkError(e3.getMessage());
                }
            }
            if (handle == 0L) {
                throw new UnsatisfiedLinkError("Unable to load library '" + libraryName + "': " + e.getMessage());
            }
        }
        if (Native.DEBUG_LOAD) {
            System.out.println("Found library '" + libraryName + "' at " + libraryPath);
        }
        return new NativeLibrary(libraryName, libraryPath, handle, options);
    }
    
    static String matchFramework(final String libraryName) {
        File framework = new File(libraryName);
        if (framework.isAbsolute()) {
            if (libraryName.indexOf(".framework") != -1 && framework.exists()) {
                return framework.getAbsolutePath();
            }
            framework = new File(new File(framework.getParentFile(), framework.getName() + ".framework"), framework.getName());
            if (framework.exists()) {
                return framework.getAbsolutePath();
            }
        }
        else {
            final String[] PREFIXES = { System.getProperty("user.home"), "", "/System" };
            final String suffix = (libraryName.indexOf(".framework") == -1) ? (libraryName + ".framework/" + libraryName) : libraryName;
            for (int i = 0; i < PREFIXES.length; ++i) {
                final String libraryPath = PREFIXES[i] + "/Library/Frameworks/" + suffix;
                if (new File(libraryPath).exists()) {
                    return libraryPath;
                }
            }
        }
        return null;
    }
    
    private String getLibraryName(final String libraryName) {
        String simplified = libraryName;
        final String BASE = "---";
        final String template = mapSharedLibraryName("---");
        final int prefixEnd = template.indexOf("---");
        if (prefixEnd > 0 && simplified.startsWith(template.substring(0, prefixEnd))) {
            simplified = simplified.substring(prefixEnd);
        }
        final String suffix = template.substring(prefixEnd + "---".length());
        final int suffixStart = simplified.indexOf(suffix);
        if (suffixStart != -1) {
            simplified = simplified.substring(0, suffixStart);
        }
        return simplified;
    }
    
    public static final NativeLibrary getInstance(final String libraryName) {
        return getInstance(libraryName, Collections.emptyMap());
    }
    
    public static final NativeLibrary getInstance(final String libraryName, final ClassLoader classLoader) {
        return getInstance(libraryName, Collections.singletonMap("classloader", classLoader));
    }
    
    public static final NativeLibrary getInstance(String libraryName, final Map<String, ?> libraryOptions) {
        final Map<String, Object> options = new HashMap<String, Object>(libraryOptions);
        if (options.get("calling-convention") == null) {
            options.put("calling-convention", 0);
        }
        if ((Platform.isLinux() || Platform.isFreeBSD() || Platform.isAIX()) && Platform.C_LIBRARY_NAME.equals(libraryName)) {
            libraryName = null;
        }
        synchronized (NativeLibrary.libraries) {
            Reference<NativeLibrary> ref = NativeLibrary.libraries.get(libraryName + options);
            NativeLibrary library = (ref != null) ? ref.get() : null;
            if (library == null) {
                if (libraryName == null) {
                    library = new NativeLibrary("<process>", null, Native.open(null, openFlags(options)), options);
                }
                else {
                    library = loadLibrary(libraryName, options);
                }
                ref = new WeakReference<NativeLibrary>(library);
                NativeLibrary.libraries.put(library.getName() + options, ref);
                final File file = library.getFile();
                if (file != null) {
                    NativeLibrary.libraries.put(file.getAbsolutePath() + options, ref);
                    NativeLibrary.libraries.put(file.getName() + options, ref);
                }
            }
            return library;
        }
    }
    
    public static final synchronized NativeLibrary getProcess() {
        return getInstance(null);
    }
    
    public static final synchronized NativeLibrary getProcess(final Map<String, ?> options) {
        return getInstance(null, options);
    }
    
    public static final void addSearchPath(final String libraryName, final String path) {
        synchronized (NativeLibrary.searchPaths) {
            List<String> customPaths = NativeLibrary.searchPaths.get(libraryName);
            if (customPaths == null) {
                customPaths = Collections.synchronizedList(new ArrayList<String>());
                NativeLibrary.searchPaths.put(libraryName, customPaths);
            }
            customPaths.add(path);
        }
    }
    
    public Function getFunction(final String functionName) {
        return this.getFunction(functionName, this.callFlags);
    }
    
    Function getFunction(String name, final Method method) {
        final FunctionMapper mapper = (FunctionMapper)this.options.get("function-mapper");
        if (mapper != null) {
            name = mapper.getFunctionName(this, method);
        }
        final String prefix = System.getProperty("jna.profiler.prefix", "$$YJP$$");
        if (name.startsWith(prefix)) {
            name = name.substring(prefix.length());
        }
        int flags = this.callFlags;
        final Class<?>[] etypes = method.getExceptionTypes();
        for (int i = 0; i < etypes.length; ++i) {
            if (LastErrorException.class.isAssignableFrom(etypes[i])) {
                flags |= 0x40;
            }
        }
        return this.getFunction(name, flags);
    }
    
    public Function getFunction(final String functionName, final int callFlags) {
        return this.getFunction(functionName, callFlags, this.encoding);
    }
    
    public Function getFunction(final String functionName, final int callFlags, final String encoding) {
        if (functionName == null) {
            throw new NullPointerException("Function name may not be null");
        }
        synchronized (this.functions) {
            final String key = functionKey(functionName, callFlags, encoding);
            Function function = this.functions.get(key);
            if (function == null) {
                function = new Function(this, functionName, callFlags, encoding);
                this.functions.put(key, function);
            }
            return function;
        }
    }
    
    public Map<String, ?> getOptions() {
        return this.options;
    }
    
    public Pointer getGlobalVariableAddress(final String symbolName) {
        try {
            return new Pointer(this.getSymbolAddress(symbolName));
        }
        catch (UnsatisfiedLinkError e) {
            throw new UnsatisfiedLinkError("Error looking up '" + symbolName + "': " + e.getMessage());
        }
    }
    
    long getSymbolAddress(final String name) {
        if (this.handle == 0L) {
            throw new UnsatisfiedLinkError("Library has been unloaded");
        }
        return Native.findSymbol(this.handle, name);
    }
    
    @Override
    public String toString() {
        return "Native Library <" + this.libraryPath + "@" + this.handle + ">";
    }
    
    public String getName() {
        return this.libraryName;
    }
    
    public File getFile() {
        if (this.libraryPath == null) {
            return null;
        }
        return new File(this.libraryPath);
    }
    
    @Override
    protected void finalize() {
        this.dispose();
    }
    
    static void disposeAll() {
        final Set<Reference<NativeLibrary>> values;
        synchronized (NativeLibrary.libraries) {
            values = new LinkedHashSet<Reference<NativeLibrary>>(NativeLibrary.libraries.values());
        }
        for (final Reference<NativeLibrary> ref : values) {
            final NativeLibrary lib = ref.get();
            if (lib != null) {
                lib.dispose();
            }
        }
    }
    
    public void dispose() {
        final Set<String> keys = new HashSet<String>();
        synchronized (NativeLibrary.libraries) {
            for (final Map.Entry<String, Reference<NativeLibrary>> e : NativeLibrary.libraries.entrySet()) {
                final Reference<NativeLibrary> ref = e.getValue();
                if (ref.get() == this) {
                    keys.add(e.getKey());
                }
            }
            for (final String k : keys) {
                NativeLibrary.libraries.remove(k);
            }
        }
        synchronized (this) {
            if (this.handle != 0L) {
                Native.close(this.handle);
                this.handle = 0L;
            }
        }
    }
    
    private static List<String> initPaths(final String key) {
        final String value = System.getProperty(key, "");
        if ("".equals(value)) {
            return Collections.emptyList();
        }
        final StringTokenizer st = new StringTokenizer(value, File.pathSeparator);
        final List<String> list = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            final String path = st.nextToken();
            if (!"".equals(path)) {
                list.add(path);
            }
        }
        return list;
    }
    
    private static String findLibraryPath(final String libName, final List<String> searchPath) {
        if (new File(libName).isAbsolute()) {
            return libName;
        }
        final String name = mapSharedLibraryName(libName);
        for (final String path : searchPath) {
            File file = new File(path, name);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            if (!Platform.isMac() || !name.endsWith(".dylib")) {
                continue;
            }
            file = new File(path, name.substring(0, name.lastIndexOf(".dylib")) + ".jnilib");
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        return name;
    }
    
    static String mapSharedLibraryName(final String libName) {
        if (!Platform.isMac()) {
            if (Platform.isLinux() || Platform.isFreeBSD()) {
                if (isVersionedName(libName) || libName.endsWith(".so")) {
                    return libName;
                }
            }
            else if (Platform.isAIX()) {
                if (libName.startsWith("lib")) {
                    return libName;
                }
            }
            else if (Platform.isWindows() && (libName.endsWith(".drv") || libName.endsWith(".dll"))) {
                return libName;
            }
            return System.mapLibraryName(libName);
        }
        if (libName.startsWith("lib") && (libName.endsWith(".dylib") || libName.endsWith(".jnilib"))) {
            return libName;
        }
        final String name = System.mapLibraryName(libName);
        if (name.endsWith(".jnilib")) {
            return name.substring(0, name.lastIndexOf(".jnilib")) + ".dylib";
        }
        return name;
    }
    
    private static boolean isVersionedName(final String name) {
        if (name.startsWith("lib")) {
            final int so = name.lastIndexOf(".so.");
            if (so != -1 && so + 4 < name.length()) {
                for (int i = so + 4; i < name.length(); ++i) {
                    final char ch = name.charAt(i);
                    if (!Character.isDigit(ch) && ch != '.') {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    static String matchLibrary(final String libName, List<String> searchPath) {
        final File lib = new File(libName);
        if (lib.isAbsolute()) {
            searchPath = Arrays.asList(lib.getParent());
        }
        final FilenameFilter filter = new FilenameFilter() {
            final /* synthetic */ String val$libName;
            
            NativeLibrary$2() {
                super();
            }
            
            @Override
            public boolean accept(final File dir, final String filename) {
                return (filename.startsWith("lib" + libName + ".so") || (filename.startsWith(libName + ".so") && libName.startsWith("lib"))) && isVersionedName(filename);
            }
        };
        final Collection<File> matches = new LinkedList<File>();
        for (final String path : searchPath) {
            final File[] files = new File(path).listFiles(filter);
            if (files != null && files.length > 0) {
                matches.addAll(Arrays.asList(files));
            }
        }
        double bestVersion = -1.0;
        String bestMatch = null;
        for (final File f : matches) {
            final String path2 = f.getAbsolutePath();
            final String ver = path2.substring(path2.lastIndexOf(".so.") + 4);
            final double version = parseVersion(ver);
            if (version > bestVersion) {
                bestVersion = version;
                bestMatch = path2;
            }
        }
        return bestMatch;
    }
    
    static double parseVersion(String ver) {
        double v = 0.0;
        double divisor = 1.0;
        int dot = ver.indexOf(".");
        while (ver != null) {
            String num;
            if (dot != -1) {
                num = ver.substring(0, dot);
                ver = ver.substring(dot + 1);
                dot = ver.indexOf(".");
            }
            else {
                num = ver;
                ver = null;
            }
            try {
                v += Integer.parseInt(num) / divisor;
            }
            catch (NumberFormatException e) {
                return 0.0;
            }
            divisor *= 100.0;
        }
        return v;
    }
    
    private static String getMultiArchPath() {
        String cpu = Platform.ARCH;
        final String kernel = Platform.iskFreeBSD() ? "-kfreebsd" : (Platform.isGNU() ? "" : "-linux");
        String libc = "-gnu";
        if (Platform.isIntel()) {
            cpu = (Platform.is64Bit() ? "x86_64" : "i386");
        }
        else if (Platform.isPPC()) {
            cpu = (Platform.is64Bit() ? "powerpc64" : "powerpc");
        }
        else if (Platform.isARM()) {
            cpu = "arm";
            libc = "-gnueabi";
        }
        return cpu + kernel + libc;
    }
    
    private static ArrayList<String> getLinuxLdPaths() {
        final ArrayList<String> ldPaths = new ArrayList<String>();
        try {
            final Process process = Runtime.getRuntime().exec("/sbin/ldconfig -p");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                final int startPath = buffer.indexOf(" => ");
                final int endPath = buffer.lastIndexOf(47);
                if (startPath != -1 && endPath != -1 && startPath < endPath) {
                    final String path = buffer.substring(startPath + 4, endPath);
                    if (ldPaths.contains(path)) {
                        continue;
                    }
                    ldPaths.add(path);
                }
            }
            reader.close();
        }
        catch (Exception ex) {}
        return ldPaths;
    }
    
    static /* synthetic */ boolean access$000(final String x0) {
        return isVersionedName(x0);
    }
    
    static {
        libraries = new HashMap<String, Reference<NativeLibrary>>();
        searchPaths = Collections.synchronizedMap(new HashMap<String, List<String>>());
        librarySearchPath = new ArrayList<String>();
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        final String webstartPath = Native.getWebStartLibraryPath("jnidispatch");
        if (webstartPath != null) {
            NativeLibrary.librarySearchPath.add(webstartPath);
        }
        if (System.getProperty("jna.platform.library.path") == null && !Platform.isWindows()) {
            String platformPath = "";
            String sep = "";
            String archPath = "";
            if (Platform.isLinux() || Platform.isSolaris() || Platform.isFreeBSD() || Platform.iskFreeBSD()) {
                archPath = (Platform.isSolaris() ? "/" : "") + Pointer.SIZE * 8;
            }
            String[] paths = { "/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib" };
            if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
                final String multiArchPath = getMultiArchPath();
                paths = new String[] { "/usr/lib/" + multiArchPath, "/lib/" + multiArchPath, "/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib" };
            }
            if (Platform.isLinux()) {
                final ArrayList<String> ldPaths = getLinuxLdPaths();
                for (int i = paths.length - 1; 0 <= i; --i) {
                    final int found = ldPaths.indexOf(paths[i]);
                    if (found != -1) {
                        ldPaths.remove(found);
                    }
                    ldPaths.add(0, paths[i]);
                }
                paths = ldPaths.toArray(new String[ldPaths.size()]);
            }
            for (int j = 0; j < paths.length; ++j) {
                final File dir = new File(paths[j]);
                if (dir.exists() && dir.isDirectory()) {
                    platformPath = platformPath + sep + paths[j];
                    sep = File.pathSeparator;
                }
            }
            if (!"".equals(platformPath)) {
                System.setProperty("jna.platform.library.path", platformPath);
            }
        }
        NativeLibrary.librarySearchPath.addAll(initPaths("jna.platform.library.path"));
    }
}
