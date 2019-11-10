package com.fasterxml.jackson.core.util;

import java.util.regex.*;
import com.fasterxml.jackson.core.*;
import java.util.*;
import java.io.*;

public class VersionUtil
{
    private static final Pattern V_SEP;
    
    protected VersionUtil() {
        super();
    }
    
    @Deprecated
    public Version version() {
        return Version.unknownVersion();
    }
    
    public static Version versionFor(final Class<?> cls) {
        final Version version = packageVersionFor(cls);
        return (version == null) ? Version.unknownVersion() : version;
    }
    
    public static Version packageVersionFor(final Class<?> cls) {
        Version v = null;
        try {
            final String versionInfoClassName = cls.getPackage().getName() + ".PackageVersion";
            final Class<?> vClass = Class.forName(versionInfoClassName, true, cls.getClassLoader());
            try {
                v = ((Versioned)vClass.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0])).version();
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Failed to get Versioned out of " + vClass);
            }
        }
        catch (Exception ex) {}
        return (v == null) ? Version.unknownVersion() : v;
    }
    
    @Deprecated
    public static Version mavenVersionFor(final ClassLoader cl, final String groupId, final String artifactId) {
        final InputStream pomProperties = cl.getResourceAsStream("META-INF/maven/" + groupId.replaceAll("\\.", "/") + "/" + artifactId + "/pom.properties");
        if (pomProperties != null) {
            try {
                final Properties props = new Properties();
                props.load(pomProperties);
                final String versionStr = props.getProperty("version");
                final String pomPropertiesArtifactId = props.getProperty("artifactId");
                final String pomPropertiesGroupId = props.getProperty("groupId");
                return parseVersion(versionStr, pomPropertiesGroupId, pomPropertiesArtifactId);
            }
            catch (IOException e) {}
            finally {
                _close(pomProperties);
            }
        }
        return Version.unknownVersion();
    }
    
    public static Version parseVersion(String s, final String groupId, final String artifactId) {
        if (s != null && (s = s.trim()).length() > 0) {
            final String[] parts = VersionUtil.V_SEP.split(s);
            return new Version(parseVersionPart(parts[0]), (parts.length > 1) ? parseVersionPart(parts[1]) : 0, (parts.length > 2) ? parseVersionPart(parts[2]) : 0, (parts.length > 3) ? parts[3] : null, groupId, artifactId);
        }
        return Version.unknownVersion();
    }
    
    protected static int parseVersionPart(final String s) {
        int number = 0;
        for (int i = 0, len = s.length(); i < len; ++i) {
            final char c = s.charAt(i);
            if (c > '9') {
                break;
            }
            if (c < '0') {
                break;
            }
            number = number * 10 + (c - '0');
        }
        return number;
    }
    
    private static final void _close(final Closeable c) {
        try {
            c.close();
        }
        catch (IOException ex) {}
    }
    
    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }
    
    static {
        V_SEP = Pattern.compile("[-_./;:]");
    }
}
