package com.google.api.client.json;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeDef {
    String key();
    
    Class<?> ref();
}
