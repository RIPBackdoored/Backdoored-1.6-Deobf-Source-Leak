package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;
import java.io.*;

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
