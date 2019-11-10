package org.reflections.scanners;

import org.reflections.vfs.*;

@Deprecated
public class TypesScanner extends AbstractScanner
{
    public TypesScanner() {
        super();
    }
    
    @Override
    public Object scan(final Vfs.File file, Object classObject) {
        classObject = super.scan(file, classObject);
        final String className = this.getMetadataAdapter().getClassName(classObject);
        this.getStore().put((Object)className, (Object)className);
        return classObject;
    }
    
    @Override
    public void scan(final Object cls) {
        throw new UnsupportedOperationException("should not get here");
    }
}
