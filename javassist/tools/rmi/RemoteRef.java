package javassist.tools.rmi;

import java.io.*;

public class RemoteRef implements Serializable
{
    public int oid;
    public String classname;
    
    public RemoteRef(final int i) {
        super();
        this.oid = i;
        this.classname = null;
    }
    
    public RemoteRef(final int i, final String name) {
        super();
        this.oid = i;
        this.classname = name;
    }
}
