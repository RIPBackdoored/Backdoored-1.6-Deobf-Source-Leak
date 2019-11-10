package com.google.cloud.storage;

import java.io.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.net.*;

public final class Cors implements Serializable
{
    private static final long serialVersionUID = -8637770919343335655L;
    static final Function<Bucket.Cors, Cors> FROM_PB_FUNCTION;
    static final Function<Cors, Bucket.Cors> TO_PB_FUNCTION;
    private final Integer maxAgeSeconds;
    private final ImmutableList<HttpMethod> methods;
    private final ImmutableList<Origin> origins;
    private final ImmutableList<String> responseHeaders;
    
    private Cors(final Builder builder) {
        super();
        this.maxAgeSeconds = builder.maxAgeSeconds;
        this.methods = builder.methods;
        this.origins = builder.origins;
        this.responseHeaders = builder.responseHeaders;
    }
    
    public Integer getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }
    
    public List<HttpMethod> getMethods() {
        return this.methods;
    }
    
    public List<Origin> getOrigins() {
        return this.origins;
    }
    
    public List<String> getResponseHeaders() {
        return this.responseHeaders;
    }
    
    public Builder toBuilder() {
        return newBuilder().setMaxAgeSeconds(this.maxAgeSeconds).setMethods(this.methods).setOrigins(this.origins).setResponseHeaders(this.responseHeaders);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.maxAgeSeconds, this.methods, this.origins, this.responseHeaders);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Cors)) {
            return false;
        }
        final Cors other = (Cors)obj;
        return Objects.equals(this.maxAgeSeconds, other.maxAgeSeconds) && Objects.equals(this.methods, other.methods) && Objects.equals(this.origins, other.origins) && Objects.equals(this.responseHeaders, other.responseHeaders);
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    Bucket.Cors toPb() {
        final Bucket.Cors pb = new Bucket.Cors();
        pb.setMaxAgeSeconds(this.maxAgeSeconds);
        pb.setResponseHeader((List)this.responseHeaders);
        if (this.methods != null) {
            pb.setMethod((List)Lists.newArrayList(Iterables.transform((Iterable<HttpMethod>)this.methods, (Function<? super HttpMethod, ?>)Functions.toStringFunction())));
        }
        if (this.origins != null) {
            pb.setOrigin((List)Lists.newArrayList(Iterables.transform((Iterable<Origin>)this.origins, (Function<? super Origin, ?>)Functions.toStringFunction())));
        }
        return pb;
    }
    
    static Cors fromPb(final Bucket.Cors cors) {
        final Builder builder = newBuilder().setMaxAgeSeconds(cors.getMaxAgeSeconds());
        if (cors.getMethod() != null) {
            builder.setMethods(Iterables.transform((Iterable<Object>)cors.getMethod(), (Function<? super Object, ? extends HttpMethod>)new Function<String, HttpMethod>() {
                Cors$3() {
                    super();
                }
                
                @Override
                public HttpMethod apply(final String name) {
                    return HttpMethod.valueOf(name.toUpperCase());
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }));
        }
        if (cors.getOrigin() != null) {
            builder.setOrigins(Iterables.transform((Iterable<Object>)cors.getOrigin(), (Function<? super Object, ? extends Origin>)new Function<String, Origin>() {
                Cors$4() {
                    super();
                }
                
                @Override
                public Origin apply(final String value) {
                    return Origin.of(value);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }));
        }
        builder.setResponseHeaders(cors.getResponseHeader());
        return builder.build();
    }
    
    Cors(final Builder x0, final Cors$1 x1) {
        this(x0);
    }
    
    static {
        FROM_PB_FUNCTION = new Function<Bucket.Cors, Cors>() {
            Cors$1() {
                super();
            }
            
            @Override
            public Cors apply(final Bucket.Cors pb) {
                return Cors.fromPb(pb);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Bucket.Cors)o);
            }
        };
        TO_PB_FUNCTION = new Function<Cors, Bucket.Cors>() {
            Cors$2() {
                super();
            }
            
            @Override
            public Bucket.Cors apply(final Cors cors) {
                return cors.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Cors)o);
            }
        };
    }
    
    public static final class Origin implements Serializable
    {
        private static final long serialVersionUID = -4447958124895577993L;
        private static final String ANY_URI = "*";
        private final String value;
        private static final Origin ANY;
        
        private Origin(final String value) {
            super();
            this.value = Preconditions.checkNotNull(value);
        }
        
        public static Origin any() {
            return Origin.ANY;
        }
        
        public static Origin of(final String scheme, final String host, final int port) {
            try {
                return of(new URI(scheme, null, host, port, null, null, null).toString());
            }
            catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
        
        public static Origin of(final String value) {
            if ("*".equals(value)) {
                return any();
            }
            return new Origin(value);
        }
        
        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
        
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Origin && this.value.equals(((Origin)obj).value);
        }
        
        @Override
        public String toString() {
            return this.getValue();
        }
        
        public String getValue() {
            return this.value;
        }
        
        static {
            ANY = new Origin("*");
        }
    }
    
    public static final class Builder
    {
        private Integer maxAgeSeconds;
        private ImmutableList<HttpMethod> methods;
        private ImmutableList<Origin> origins;
        private ImmutableList<String> responseHeaders;
        
        private Builder() {
            super();
        }
        
        public Builder setMaxAgeSeconds(final Integer maxAgeSeconds) {
            this.maxAgeSeconds = maxAgeSeconds;
            return this;
        }
        
        public Builder setMethods(final Iterable<HttpMethod> methods) {
            this.methods = ((methods != null) ? ImmutableList.copyOf((Iterable<? extends HttpMethod>)methods) : null);
            return this;
        }
        
        public Builder setOrigins(final Iterable<Origin> origins) {
            this.origins = ((origins != null) ? ImmutableList.copyOf((Iterable<? extends Origin>)origins) : null);
            return this;
        }
        
        public Builder setResponseHeaders(final Iterable<String> headers) {
            this.responseHeaders = ((headers != null) ? ImmutableList.copyOf((Iterable<? extends String>)headers) : null);
            return this;
        }
        
        public Cors build() {
            return new Cors(this, null);
        }
        
        static /* synthetic */ Integer access$100(final Builder x0) {
            return x0.maxAgeSeconds;
        }
        
        static /* synthetic */ ImmutableList access$200(final Builder x0) {
            return x0.methods;
        }
        
        static /* synthetic */ ImmutableList access$300(final Builder x0) {
            return x0.origins;
        }
        
        static /* synthetic */ ImmutableList access$400(final Builder x0) {
            return x0.responseHeaders;
        }
        
        Builder(final Cors$1 x0) {
            this();
        }
    }
}
