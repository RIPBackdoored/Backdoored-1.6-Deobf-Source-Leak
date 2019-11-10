package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.util.*;

public class TokenResponseException extends HttpResponseException
{
    private static final long serialVersionUID = 4020689092957439244L;
    private final transient TokenErrorResponse details;
    
    TokenResponseException(final Builder builder, final TokenErrorResponse details) {
        super(builder);
        this.details = details;
    }
    
    public final TokenErrorResponse getDetails() {
        return this.details;
    }
    
    public static TokenResponseException from(final JsonFactory jsonFactory, final HttpResponse response) {
        final Builder builder = new Builder(response.getStatusCode(), response.getStatusMessage(), response.getHeaders());
        Preconditions.checkNotNull(jsonFactory);
        TokenErrorResponse details = null;
        String detailString = null;
        final String contentType = response.getContentType();
        try {
            if (!response.isSuccessStatusCode() && contentType != null && response.getContent() != null && HttpMediaType.equalsIgnoreParameters("application/json; charset=UTF-8", contentType)) {
                details = new JsonObjectParser(jsonFactory).parseAndClose(response.getContent(), response.getContentCharset(), TokenErrorResponse.class);
                detailString = details.toPrettyString();
            }
            else {
                detailString = response.parseAsString();
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        final StringBuilder message = HttpResponseException.computeMessageBuffer(response);
        if (!Strings.isNullOrEmpty(detailString)) {
            message.append(StringUtils.LINE_SEPARATOR).append(detailString);
            builder.setContent(detailString);
        }
        builder.setMessage(message.toString());
        return new TokenResponseException(builder, details);
    }
}
