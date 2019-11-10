package com.google.cloud.storage;

import java.io.*;
import com.google.api.services.storage.model.*;
import com.google.common.base.*;
import java.util.*;
import com.google.api.core.*;
import com.google.cloud.*;

public final class Acl implements Serializable
{
    private static final long serialVersionUID = 7516713233557576082L;
    static final Function<ObjectAccessControl, Acl> FROM_OBJECT_PB_FUNCTION;
    static final Function<BucketAccessControl, Acl> FROM_BUCKET_PB_FUNCTION;
    private final Entity entity;
    private final Role role;
    private final String id;
    private final String etag;
    
    private Acl(final Builder builder) {
        super();
        this.entity = Preconditions.checkNotNull(builder.entity);
        this.role = Preconditions.checkNotNull(builder.role);
        this.id = builder.id;
        this.etag = builder.etag;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public Role getRole() {
        return this.role;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getEtag() {
        return this.etag;
    }
    
    public Builder toBuilder() {
        return new Builder(this);
    }
    
    public static Acl of(final Entity entity, final Role role) {
        return newBuilder(entity, role).build();
    }
    
    public static Builder newBuilder(final Entity entity, final Role role) {
        return new Builder(entity, role);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("entity", this.entity).add("role", this.role).add("etag", this.etag).add("id", this.id).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.entity, this.role);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final Acl other = (Acl)obj;
        return Objects.equals(this.entity, other.entity) && Objects.equals(this.role, other.role) && Objects.equals(this.etag, other.etag) && Objects.equals(this.id, other.id);
    }
    
    BucketAccessControl toBucketPb() {
        final BucketAccessControl bucketPb = new BucketAccessControl();
        bucketPb.setEntity(this.getEntity().toString());
        bucketPb.setRole(this.getRole().toString());
        bucketPb.setId(this.getId());
        bucketPb.setEtag(this.getEtag());
        return bucketPb;
    }
    
    ObjectAccessControl toObjectPb() {
        final ObjectAccessControl objectPb = new ObjectAccessControl();
        objectPb.setEntity(this.getEntity().toPb());
        objectPb.setRole(this.getRole().name());
        objectPb.setId(this.getId());
        objectPb.setEtag(this.getEtag());
        return objectPb;
    }
    
    static Acl fromPb(final ObjectAccessControl objectAccessControl) {
        final Role role = Role.valueOf(objectAccessControl.getRole());
        final Entity entity = Entity.fromPb(objectAccessControl.getEntity());
        return newBuilder(entity, role).setEtag(objectAccessControl.getEtag()).setId(objectAccessControl.getId()).build();
    }
    
    static Acl fromPb(final BucketAccessControl bucketAccessControl) {
        final Role role = Role.valueOf(bucketAccessControl.getRole());
        final Entity entity = Entity.fromPb(bucketAccessControl.getEntity());
        return newBuilder(entity, role).setEtag(bucketAccessControl.getEtag()).setId(bucketAccessControl.getId()).build();
    }
    
    static /* synthetic */ Entity access$100(final Acl x0) {
        return x0.entity;
    }
    
    static /* synthetic */ Role access$200(final Acl x0) {
        return x0.role;
    }
    
    static /* synthetic */ String access$300(final Acl x0) {
        return x0.id;
    }
    
    static /* synthetic */ String access$400(final Acl x0) {
        return x0.etag;
    }
    
    Acl(final Builder x0, final Acl$1 x1) {
        this(x0);
    }
    
    static {
        FROM_OBJECT_PB_FUNCTION = new Function<ObjectAccessControl, Acl>() {
            Acl$1() {
                super();
            }
            
            @Override
            public Acl apply(final ObjectAccessControl aclPb) {
                return Acl.fromPb(aclPb);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((ObjectAccessControl)o);
            }
        };
        FROM_BUCKET_PB_FUNCTION = new Function<BucketAccessControl, Acl>() {
            Acl$2() {
                super();
            }
            
            @Override
            public Acl apply(final BucketAccessControl aclPb) {
                return Acl.fromPb(aclPb);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BucketAccessControl)o);
            }
        };
    }
    
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
    
    public static class Builder
    {
        private Entity entity;
        private Role role;
        private String id;
        private String etag;
        
        private Builder(final Entity entity, final Role role) {
            super();
            this.entity = entity;
            this.role = role;
        }
        
        private Builder(final Acl acl) {
            super();
            this.entity = acl.entity;
            this.role = acl.role;
            this.id = acl.id;
            this.etag = acl.etag;
        }
        
        public Builder setEntity(final Entity entity) {
            this.entity = entity;
            return this;
        }
        
        public Builder setRole(final Role role) {
            this.role = role;
            return this;
        }
        
        Builder setId(final String id) {
            this.id = id;
            return this;
        }
        
        Builder setEtag(final String etag) {
            this.etag = etag;
            return this;
        }
        
        public Acl build() {
            return new Acl(this, null);
        }
        
        static /* synthetic */ Entity access$700(final Builder x0) {
            return x0.entity;
        }
        
        static /* synthetic */ Role access$800(final Builder x0) {
            return x0.role;
        }
        
        static /* synthetic */ String access$900(final Builder x0) {
            return x0.id;
        }
        
        static /* synthetic */ String access$1000(final Builder x0) {
            return x0.etag;
        }
        
        Builder(final Acl x0, final Acl$1 x1) {
            this(x0);
        }
        
        Builder(final Entity x0, final Role x1, final Acl$1 x2) {
            this(x0, x1);
        }
    }
    
    public abstract static class Entity implements Serializable
    {
        private static final long serialVersionUID = -2707407252771255840L;
        private final Type type;
        private final String value;
        
        Entity(final Type type, final String value) {
            super();
            this.type = type;
            this.value = value;
        }
        
        public Type getType() {
            return this.type;
        }
        
        protected String getValue() {
            return this.value;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            final Entity entity = (Entity)obj;
            return Objects.equals(this.type, entity.type) && Objects.equals(this.value, entity.value);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.type, this.value);
        }
        
        @Override
        public String toString() {
            return this.toPb();
        }
        
        String toPb() {
            return this.type.name().toLowerCase() + "-" + this.getValue();
        }
        
        static Entity fromPb(final String entity) {
            if (entity.startsWith("user-")) {
                return new User(entity.substring(5));
            }
            if (entity.equals("allUsers")) {
                return User.ofAllUsers();
            }
            if (entity.equals("allAuthenticatedUsers")) {
                return User.ofAllAuthenticatedUsers();
            }
            if (entity.startsWith("group-")) {
                return new Group(entity.substring(6));
            }
            if (entity.startsWith("domain-")) {
                return new Domain(entity.substring(7));
            }
            if (entity.startsWith("project-")) {
                final int idx = entity.indexOf(45, 8);
                final String team = entity.substring(8, idx);
                final String projectId = entity.substring(idx + 1);
                return new Project(Project.ProjectRole.valueOf(team.toUpperCase()), projectId);
            }
            return new RawEntity(entity);
        }
        
        public enum Type
        {
            DOMAIN, 
            GROUP, 
            USER, 
            PROJECT, 
            UNKNOWN;
            
            private static final /* synthetic */ Type[] $VALUES;
            
            public static Type[] values() {
                return Type.$VALUES.clone();
            }
            
            public static Type valueOf(final String name) {
                return Enum.valueOf(Type.class, name);
            }
            
            static {
                $VALUES = new Type[] { Type.DOMAIN, Type.GROUP, Type.USER, Type.PROJECT, Type.UNKNOWN };
            }
        }
    }
    
    public static final class Domain extends Entity
    {
        private static final long serialVersionUID = -3033025857280447253L;
        
        public Domain(final String domain) {
            super(Type.DOMAIN, domain);
        }
        
        public String getDomain() {
            return this.getValue();
        }
    }
    
    public static final class Group extends Entity
    {
        private static final long serialVersionUID = -1660987136294408826L;
        
        public Group(final String email) {
            super(Type.GROUP, email);
        }
        
        public String getEmail() {
            return this.getValue();
        }
    }
    
    public static final class User extends Entity
    {
        private static final long serialVersionUID = 3076518036392737008L;
        private static final String ALL_USERS = "allUsers";
        private static final String ALL_AUTHENTICATED_USERS = "allAuthenticatedUsers";
        
        public User(final String email) {
            super(Type.USER, email);
        }
        
        public String getEmail() {
            return this.getValue();
        }
        
        @Override
        String toPb() {
            final String value = this.getValue();
            switch (value) {
                case "allAuthenticatedUsers": {
                    return "allAuthenticatedUsers";
                }
                case "allUsers": {
                    return "allUsers";
                }
                default: {
                    return super.toPb();
                }
            }
        }
        
        public static User ofAllUsers() {
            return new User("allUsers");
        }
        
        public static User ofAllAuthenticatedUsers() {
            return new User("allAuthenticatedUsers");
        }
    }
    
    public static final class Project extends Entity
    {
        private static final long serialVersionUID = 7933776866530023027L;
        private final ProjectRole projectRole;
        private final String projectId;
        
        public Project(final ProjectRole projectRole, final String projectId) {
            super(Type.PROJECT, projectRole.name().toLowerCase() + "-" + projectId);
            this.projectRole = projectRole;
            this.projectId = projectId;
        }
        
        public ProjectRole getProjectRole() {
            return this.projectRole;
        }
        
        public String getProjectId() {
            return this.projectId;
        }
        
        public static final class ProjectRole extends StringEnumValue
        {
            private static final long serialVersionUID = -8360324311187914382L;
            private static final ApiFunction<String, ProjectRole> CONSTRUCTOR;
            private static final StringEnumType<ProjectRole> type;
            public static final ProjectRole OWNERS;
            public static final ProjectRole EDITORS;
            public static final ProjectRole VIEWERS;
            
            private ProjectRole(final String constant) {
                super(constant);
            }
            
            public static ProjectRole valueOfStrict(final String constant) {
                return (ProjectRole)ProjectRole.type.valueOfStrict(constant);
            }
            
            public static ProjectRole valueOf(final String constant) {
                return (ProjectRole)ProjectRole.type.valueOf(constant);
            }
            
            public static ProjectRole[] values() {
                return (ProjectRole[])ProjectRole.type.values();
            }
            
            ProjectRole(final String x0, final Acl$1 x1) {
                this(x0);
            }
            
            static {
                CONSTRUCTOR = (ApiFunction)new ApiFunction<String, ProjectRole>() {
                    Acl$Project$ProjectRole$1() {
                        super();
                    }
                    
                    public ProjectRole apply(final String constant) {
                        return new ProjectRole(constant);
                    }
                    
                    public /* bridge */ Object apply(final Object o) {
                        return this.apply((String)o);
                    }
                };
                type = new StringEnumType((Class)ProjectRole.class, (ApiFunction)ProjectRole.CONSTRUCTOR);
                OWNERS = (ProjectRole)ProjectRole.type.createAndRegister("OWNERS");
                EDITORS = (ProjectRole)ProjectRole.type.createAndRegister("EDITORS");
                VIEWERS = (ProjectRole)ProjectRole.type.createAndRegister("VIEWERS");
            }
        }
    }
    
    public static final class RawEntity extends Entity
    {
        private static final long serialVersionUID = 3966205614223053950L;
        
        RawEntity(final String entity) {
            super(Type.UNKNOWN, entity);
        }
        
        @Override
        String toPb() {
            return this.getValue();
        }
    }
}
