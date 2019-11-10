package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.cloud.*;

public static final class Role extends StringEnumValue
{
    private static final long serialVersionUID = 123037132067643600L;
    private static final ApiFunction<String, Role> CONSTRUCTOR;
    private static final StringEnumType<Role> type;
    public static final Role OWNER;
    public static final Role READER;
    public static final Role WRITER;
    
    private Role(final String constant) {
        super(constant);
    }
    
    public static Role valueOfStrict(final String constant) {
        return (Role)Role.type.valueOfStrict(constant);
    }
    
    public static Role valueOf(final String constant) {
        return (Role)Role.type.valueOf(constant);
    }
    
    public static Role[] values() {
        return (Role[])Role.type.values();
    }
    
    Role(final String x0, final Acl$1 x1) {
        this(x0);
    }
    
    static {
        CONSTRUCTOR = (ApiFunction)new ApiFunction<String, Role>() {
            Acl$Role$1() {
                super();
            }
            
            public Role apply(final String constant) {
                return new Role(constant);
            }
            
            public /* bridge */ Object apply(final Object o) {
                return this.apply((String)o);
            }
        };
        type = new StringEnumType((Class)Role.class, (ApiFunction)Role.CONSTRUCTOR);
        OWNER = (Role)Role.type.createAndRegister("OWNER");
        READER = (Role)Role.type.createAndRegister("READER");
        WRITER = (Role)Role.type.createAndRegister("WRITER");
    }
}
