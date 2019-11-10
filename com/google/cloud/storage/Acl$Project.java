package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.cloud.*;

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
