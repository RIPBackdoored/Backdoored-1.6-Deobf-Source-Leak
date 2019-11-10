package com.google.cloud.storage;

import java.io.*;
import java.util.*;

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
