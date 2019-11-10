package org.reflections.scanners;

import org.reflections.adapters.*;
import java.util.*;

public class MethodParameterScanner extends AbstractScanner
{
    public MethodParameterScanner() {
        super();
    }
    
    @Override
    public void scan(final Object cls) {
        final MetadataAdapter md = this.getMetadataAdapter();
        for (final Object method : md.getMethods(cls)) {
            final String signature = md.getParameterNames(method).toString();
            if (this.acceptResult(signature)) {
                this.getStore().put((Object)signature, (Object)md.getMethodFullKey(cls, method));
            }
            final String returnTypeName = md.getReturnTypeName(method);
            if (this.acceptResult(returnTypeName)) {
                this.getStore().put((Object)returnTypeName, (Object)md.getMethodFullKey(cls, method));
            }
            final List<String> parameterNames = md.getParameterNames(method);
            for (int i = 0; i < parameterNames.size(); ++i) {
                for (final Object paramAnnotation : md.getParameterAnnotationNames(method, i)) {
                    if (this.acceptResult((String)paramAnnotation)) {
                        this.getStore().put((Object)paramAnnotation, (Object)md.getMethodFullKey(cls, method));
                    }
                }
            }
        }
    }
}
