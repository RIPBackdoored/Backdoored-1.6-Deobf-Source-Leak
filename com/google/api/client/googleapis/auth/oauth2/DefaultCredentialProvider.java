package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import java.security.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;

@Beta
class DefaultCredentialProvider extends SystemEnvironmentProvider
{
    static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
    static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
    static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
    static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
    static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
    private GoogleCredential cachedCredential;
    private Environment detectedEnvironment;
    
    DefaultCredentialProvider() {
        super();
        this.cachedCredential = null;
        this.detectedEnvironment = null;
    }
    
    final GoogleCredential getDefaultCredential(final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        synchronized (this) {
            if (this.cachedCredential == null) {
                this.cachedCredential = this.getDefaultCredentialUnsynchronized(transport, jsonFactory);
            }
            if (this.cachedCredential != null) {
                return this.cachedCredential;
            }
        }
        throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", "GOOGLE_APPLICATION_CREDENTIALS", "https://developers.google.com/accounts/docs/application-default-credentials"));
    }
    
    private final GoogleCredential getDefaultCredentialUnsynchronized(final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        if (this.detectedEnvironment == null) {
            this.detectedEnvironment = this.detectEnvironment(transport);
        }
        switch (this.detectedEnvironment) {
            case ENVIRONMENT_VARIABLE: {
                return this.getCredentialUsingEnvironmentVariable(transport, jsonFactory);
            }
            case WELL_KNOWN_FILE: {
                return this.getCredentialUsingWellKnownFile(transport, jsonFactory);
            }
            case APP_ENGINE: {
                return this.getAppEngineCredential(transport, jsonFactory);
            }
            case CLOUD_SHELL: {
                return this.getCloudShellCredential(jsonFactory);
            }
            case COMPUTE_ENGINE: {
                return this.getComputeCredential(transport, jsonFactory);
            }
            default: {
                return null;
            }
        }
    }
    
    private final File getWellKnownCredentialsFile() {
        File cloudConfigPath = null;
        final String os = this.getProperty("os.name", "").toLowerCase(Locale.US);
        if (os.indexOf("windows") >= 0) {
            final File appDataPath = new File(this.getEnv("APPDATA"));
            cloudConfigPath = new File(appDataPath, "gcloud");
        }
        else {
            final File configPath = new File(this.getProperty("user.home", ""), ".config");
            cloudConfigPath = new File(configPath, "gcloud");
        }
        final File credentialFilePath = new File(cloudConfigPath, "application_default_credentials.json");
        return credentialFilePath;
    }
    
    boolean fileExists(final File file) {
        return file.exists() && !file.isDirectory();
    }
    
    String getProperty(final String property, final String def) {
        return System.getProperty(property, def);
    }
    
    Class<?> forName(final String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
    
    private final Environment detectEnvironment(final HttpTransport transport) throws IOException {
        if (this.runningUsingEnvironmentVariable()) {
            return Environment.ENVIRONMENT_VARIABLE;
        }
        if (this.runningUsingWellKnownFile()) {
            return Environment.WELL_KNOWN_FILE;
        }
        if (this.runningOnAppEngine()) {
            return Environment.APP_ENGINE;
        }
        if (this.runningOnCloudShell()) {
            return Environment.CLOUD_SHELL;
        }
        if (OAuth2Utils.runningOnComputeEngine(transport, this)) {
            return Environment.COMPUTE_ENGINE;
        }
        return Environment.UNKNOWN;
    }
    
    private boolean runningUsingEnvironmentVariable() throws IOException {
        final String credentialsPath = this.getEnv("GOOGLE_APPLICATION_CREDENTIALS");
        if (credentialsPath == null || credentialsPath.length() == 0) {
            return false;
        }
        try {
            final File credentialsFile = new File(credentialsPath);
            if (!credentialsFile.exists() || credentialsFile.isDirectory()) {
                throw new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", "GOOGLE_APPLICATION_CREDENTIALS", credentialsPath));
            }
            return true;
        }
        catch (AccessControlException expected) {
            return false;
        }
    }
    
    private GoogleCredential getCredentialUsingEnvironmentVariable(final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        final String credentialsPath = this.getEnv("GOOGLE_APPLICATION_CREDENTIALS");
        InputStream credentialsStream = null;
        try {
            credentialsStream = new FileInputStream(credentialsPath);
            return GoogleCredential.fromStream(credentialsStream, transport, jsonFactory);
        }
        catch (IOException e) {
            throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Error reading credential file from environment variable %s, value '%s': %s", "GOOGLE_APPLICATION_CREDENTIALS", credentialsPath, e.getMessage())), e);
        }
        finally {
            if (credentialsStream != null) {
                credentialsStream.close();
            }
        }
    }
    
    private boolean runningUsingWellKnownFile() {
        final File wellKnownFileLocation = this.getWellKnownCredentialsFile();
        try {
            return this.fileExists(wellKnownFileLocation);
        }
        catch (AccessControlException expected) {
            return false;
        }
    }
    
    private GoogleCredential getCredentialUsingWellKnownFile(final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        final File wellKnownFileLocation = this.getWellKnownCredentialsFile();
        InputStream credentialsStream = null;
        try {
            credentialsStream = new FileInputStream(wellKnownFileLocation);
            return GoogleCredential.fromStream(credentialsStream, transport, jsonFactory);
        }
        catch (IOException e) {
            throw new IOException(String.format("Error reading credential file from location %s: %s", wellKnownFileLocation, e.getMessage()));
        }
        finally {
            if (credentialsStream != null) {
                credentialsStream.close();
            }
        }
    }
    
    private boolean runningOnAppEngine() {
        Class<?> systemPropertyClass = null;
        try {
            systemPropertyClass = this.forName("com.google.appengine.api.utils.SystemProperty");
        }
        catch (ClassNotFoundException expected) {
            return false;
        }
        Exception cause = null;
        try {
            final Field environmentField = systemPropertyClass.getField("environment");
            final Object environmentValue = environmentField.get(null);
            final Class<?> environmentType = environmentField.getType();
            final Method valueMethod = environmentType.getMethod("value", (Class<?>[])new Class[0]);
            final Object environmentValueValue = valueMethod.invoke(environmentValue, new Object[0]);
            return environmentValueValue != null;
        }
        catch (NoSuchFieldException exception) {
            cause = exception;
        }
        catch (SecurityException exception2) {
            cause = exception2;
        }
        catch (IllegalArgumentException exception3) {
            cause = exception3;
        }
        catch (IllegalAccessException exception4) {
            cause = exception4;
        }
        catch (NoSuchMethodException exception5) {
            cause = exception5;
        }
        catch (InvocationTargetException exception6) {
            cause = exception6;
        }
        throw OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", cause.getMessage())), cause);
    }
    
    private final GoogleCredential getAppEngineCredential(final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        Exception innerException = null;
        try {
            final Class<?> credentialClass = this.forName("com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper");
            final Constructor<?> constructor = credentialClass.getConstructor(HttpTransport.class, JsonFactory.class);
            return (GoogleCredential)constructor.newInstance(transport, jsonFactory);
        }
        catch (ClassNotFoundException e) {
            innerException = e;
        }
        catch (NoSuchMethodException e2) {
            innerException = e2;
        }
        catch (InstantiationException e3) {
            innerException = e3;
        }
        catch (IllegalAccessException e4) {
            innerException = e4;
        }
        catch (InvocationTargetException e5) {
            innerException = e5;
        }
        throw OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper")), innerException);
    }
    
    private boolean runningOnCloudShell() {
        return this.getEnv("DEVSHELL_CLIENT_PORT") != null;
    }
    
    private GoogleCredential getCloudShellCredential(final JsonFactory jsonFactory) {
        final String port = this.getEnv("DEVSHELL_CLIENT_PORT");
        return new CloudShellCredential(Integer.parseInt(port), jsonFactory);
    }
    
    private final GoogleCredential getComputeCredential(final HttpTransport transport, final JsonFactory jsonFactory) {
        return new ComputeGoogleCredential(transport, jsonFactory);
    }
    
    private enum Environment
    {
        UNKNOWN, 
        ENVIRONMENT_VARIABLE, 
        WELL_KNOWN_FILE, 
        CLOUD_SHELL, 
        APP_ENGINE, 
        COMPUTE_ENGINE;
        
        private static final /* synthetic */ Environment[] $VALUES;
        
        public static Environment[] values() {
            return Environment.$VALUES.clone();
        }
        
        public static Environment valueOf(final String name) {
            return Enum.valueOf(Environment.class, name);
        }
        
        static {
            $VALUES = new Environment[] { Environment.UNKNOWN, Environment.ENVIRONMENT_VARIABLE, Environment.WELL_KNOWN_FILE, Environment.CLOUD_SHELL, Environment.APP_ENGINE, Environment.COMPUTE_ENGINE };
        }
    }
    
    private static class ComputeGoogleCredential extends GoogleCredential
    {
        private static final String TOKEN_SERVER_ENCODED_URL;
        
        ComputeGoogleCredential(final HttpTransport transport, final JsonFactory jsonFactory) {
            super(new Builder().setTransport(transport).setJsonFactory(jsonFactory).setTokenServerEncodedUrl(ComputeGoogleCredential.TOKEN_SERVER_ENCODED_URL));
        }
        
        @Override
        protected TokenResponse executeRefreshToken() throws IOException {
            final GenericUrl tokenUrl = new GenericUrl(this.getTokenServerEncodedUrl());
            final HttpRequest request = this.getTransport().createRequestFactory().buildGetRequest(tokenUrl);
            final JsonObjectParser parser = new JsonObjectParser(this.getJsonFactory());
            request.setParser(parser);
            request.getHeaders().set("Metadata-Flavor", "Google");
            request.setThrowExceptionOnExecuteError(false);
            final HttpResponse response = request.execute();
            final int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                final InputStream content = response.getContent();
                if (content == null) {
                    throw new IOException("Empty content from metadata token server request.");
                }
                return parser.parseAndClose(content, response.getContentCharset(), TokenResponse.class);
            }
            else {
                if (statusCode == 404) {
                    throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", statusCode));
                }
                throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", statusCode, response.parseAsString()));
            }
        }
        
        static {
            TOKEN_SERVER_ENCODED_URL = String.valueOf(OAuth2Utils.getMetadataServerUrl()).concat("/computeMetadata/v1/instance/service-accounts/default/token");
        }
    }
}
