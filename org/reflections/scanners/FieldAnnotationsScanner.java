package org.reflections.scanners;

import java.util.*;

public class FieldAnnotationsScanner extends AbstractScanner
{
    public FieldAnnotationsScanner() {
        super();
    }
    
    @Override
    public void scan(final Object cls) {
        final String className = this.getMetadataAdapter().getClassName(cls);
        final List<Object> fields = this.getMetadataAdapter().getFields(cls);
        for (final Object field : fields) {
            final List<String> fieldAnnotations = this.getMetadataAdapter().getFieldAnnotationNames(field);
            for (final String fieldAnnotation : fieldAnnotations) {
                if (this.acceptResult(fieldAnnotation)) {
                    final String fieldName = this.getMetadataAdapter().getFieldName(field);
                    this.getStore().put((Object)fieldAnnotation, (Object)String.format("%s.%s", className, fieldName));
                }
            }
        }
    }
}
