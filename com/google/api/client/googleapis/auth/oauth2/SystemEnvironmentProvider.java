package com.google.api.client.googleapis.auth.oauth2;

class SystemEnvironmentProvider
{
    static final SystemEnvironmentProvider INSTANCE;
    
    SystemEnvironmentProvider() {
        super();
    }
    
    String getEnv(final String name) {
        return System.getenv(name);
    }
    
    static {
        INSTANCE = new SystemEnvironmentProvider();
    }
}
