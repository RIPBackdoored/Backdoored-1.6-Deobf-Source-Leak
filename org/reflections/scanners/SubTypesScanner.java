package org.reflections.scanners;

import org.reflections.util.*;
import com.google.common.base.*;
import java.util.*;

public class SubTypesScanner extends AbstractScanner
{
    public SubTypesScanner() {
        this(true);
    }
    
    public SubTypesScanner(final boolean excludeObjectClass) {
        super();
        if (excludeObjectClass) {
            this.filterResultsBy(new FilterBuilder().exclude(Object.class.getName()));
        }
    }
    
    @Override
    public void scan(final Object cls) {
        final String className = this.getMetadataAdapter().getClassName(cls);
        final String superclass = this.getMetadataAdapter().getSuperclassName(cls);
        if (this.acceptResult(superclass)) {
            this.getStore().put((Object)superclass, (Object)className);
        }
        for (final String anInterface : this.getMetadataAdapter().getInterfacesNames(cls)) {
            if (this.acceptResult(anInterface)) {
                this.getStore().put((Object)anInterface, (Object)className);
            }
        }
    }
}
