package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.cloud.*;

public final class HttpMethod extends StringEnumValue
{
    private static final long serialVersionUID = -1394461645628254471L;
    private static final ApiFunction<String, HttpMethod> CONSTRUCTOR;
    private static final StringEnumType<HttpMethod> type;
    public static final HttpMethod GET;
    public static final HttpMethod HEAD;
    public static final HttpMethod PUT;
    public static final HttpMethod POST;
    public static final HttpMethod DELETE;
    public static final HttpMethod OPTIONS;
    
    private HttpMethod(final String constant) {
        super(constant);
    }
    
    public static HttpMethod valueOfStrict(final String constant) {
        return (HttpMethod)HttpMethod.type.valueOfStrict(constant);
    }
    
    public static HttpMethod valueOf(final String constant) {
        return (HttpMethod)HttpMethod.type.valueOf(constant);
    }
    
    public static HttpMethod[] values() {
        return (HttpMethod[])HttpMethod.type.values();
    }
    
    HttpMethod(final String x0, final HttpMethod$1 x1) {
        this(x0);
    }
    
    static {
        CONSTRUCTOR = (ApiFunction)new ApiFunction<String, HttpMethod>() {
            HttpMethod$1() {
                super();
            }
            
            public HttpMethod apply(final String constant) {
                return new HttpMethod(constant, null);
            }
            
            public /* bridge */ Object apply(final Object o) {
                return this.apply((String)o);
            }
        };
        type = new StringEnumType((Class)HttpMethod.class, (ApiFunction)HttpMethod.CONSTRUCTOR);
        GET = (HttpMethod)HttpMethod.type.createAndRegister("GET");
        HEAD = (HttpMethod)HttpMethod.type.createAndRegister("HEAD");
        PUT = (HttpMethod)HttpMethod.type.createAndRegister("PUT");
        POST = (HttpMethod)HttpMethod.type.createAndRegister("POST");
        DELETE = (HttpMethod)HttpMethod.type.createAndRegister("DELETE");
        OPTIONS = (HttpMethod)HttpMethod.type.createAndRegister("OPTIONS");
    }
}
