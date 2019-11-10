package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.http.*;

@Beta
public final class ClientLogin
{
    public HttpTransport transport;
    public GenericUrl serverUrl;
    @Key("source")
    public String applicationName;
    @Key("service")
    public String authTokenType;
    @Key("Email")
    public String username;
    @Key("Passwd")
    public String password;
    @Key
    public String accountType;
    @Key("logintoken")
    public String captchaToken;
    @Key("logincaptcha")
    public String captchaAnswer;
    
    public ClientLogin() {
        super();
        this.serverUrl = new GenericUrl("https://www.google.com");
    }
    
    public Response authenticate() throws IOException {
        final GenericUrl url = this.serverUrl.clone();
        url.appendRawPath("/accounts/ClientLogin");
        final HttpRequest request = this.transport.createRequestFactory().buildPostRequest(url, new UrlEncodedContent(this));
        request.setParser(AuthKeyValueParser.INSTANCE);
        request.setContentLoggingLimit(0);
        request.setThrowExceptionOnExecuteError(false);
        final HttpResponse response = request.execute();
        if (response.isSuccessStatusCode()) {
            return response.parseAs(Response.class);
        }
        final HttpResponseException.Builder builder = new HttpResponseException.Builder(response.getStatusCode(), response.getStatusMessage(), response.getHeaders());
        final ErrorInfo details = response.parseAs(ErrorInfo.class);
        final String detailString = details.toString();
        final StringBuilder message = HttpResponseException.computeMessageBuffer(response);
        if (!Strings.isNullOrEmpty(detailString)) {
            message.append(StringUtils.LINE_SEPARATOR).append(detailString);
            builder.setContent(detailString);
        }
        builder.setMessage(message.toString());
        throw new ClientLoginResponseException(builder, details);
    }
    
    public static String getAuthorizationHeaderValue(final String authToken) {
        final String s = "GoogleLogin auth=";
        final String value = String.valueOf(authToken);
        return (value.length() != 0) ? s.concat(value) : new String(s);
    }
    
    public static final class Response implements HttpExecuteInterceptor, HttpRequestInitializer
    {
        @Key("Auth")
        public String auth;
        
        public Response() {
            super();
        }
        
        public String getAuthorizationHeaderValue() {
            return ClientLogin.getAuthorizationHeaderValue(this.auth);
        }
        
        public void initialize(final HttpRequest request) {
            request.setInterceptor(this);
        }
        
        public void intercept(final HttpRequest request) {
            request.getHeaders().setAuthorization(this.getAuthorizationHeaderValue());
        }
    }
    
    public static final class ErrorInfo
    {
        @Key("Error")
        public String error;
        @Key("Url")
        public String url;
        @Key("CaptchaToken")
        public String captchaToken;
        @Key("CaptchaUrl")
        public String captchaUrl;
        
        public ErrorInfo() {
            super();
        }
    }
}
