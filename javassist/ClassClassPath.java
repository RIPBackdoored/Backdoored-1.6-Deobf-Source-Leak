package javassist;

import java.io.*;
import java.net.*;

public class ClassClassPath implements ClassPath
{
    private Class thisClass;
    
    public ClassClassPath(final Class c) {
        super();
        this.thisClass = c;
    }
    
    ClassClassPath() {
        this(Object.class);
    }
    
    @Override
    public InputStream openClassfile(final String classname) {
        final String jarname = "/" + classname.replace('.', '/') + ".class";
        return this.thisClass.getResourceAsStream(jarname);
    }
    
    @Override
    public URL find(final String classname) {
        final String jarname = "/" + classname.replace('.', '/') + ".class";
        return this.thisClass.getResource(jarname);
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public String toString() {
        return this.thisClass.getName() + ".class";
    }
}
