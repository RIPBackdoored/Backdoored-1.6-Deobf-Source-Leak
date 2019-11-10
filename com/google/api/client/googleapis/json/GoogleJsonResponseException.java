package com.google.api.client.googleapis.json;

import java.io.*;
import com.google.api.client.util.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;

public class GoogleJsonResponseException extends HttpResponseException
{
    private static final long serialVersionUID = 409811126989994864L;
    private final transient GoogleJsonError details;
    
    public GoogleJsonResponseException(final Builder builder, final GoogleJsonError details) {
        super(builder);
        this.details = details;
    }
    
    public final GoogleJsonError getDetails() {
        return this.details;
    }
    
    public static GoogleJsonResponseException from(final JsonFactory jsonFactory, final HttpResponse response) {
        final Builder builder = new Builder(response.getStatusCode(), response.getStatusMessage(), response.getHeaders());
        Preconditions.checkNotNull(jsonFactory);
        GoogleJsonError details = null;
        String detailString = null;
        try {
            if (!response.isSuccessStatusCode() && HttpMediaType.equalsIgnoreParameters("application/json; charset=UTF-8", response.getContentType()) && response.getContent() != null) {
                JsonParser parser = null;
                try {
                    parser = jsonFactory.createJsonParser(response.getContent());
                    JsonToken currentToken = parser.getCurrentToken();
                    if (currentToken == null) {
                        currentToken = parser.nextToken();
                    }
                    if (currentToken != null) {
                        parser.skipToKey("error");
                        if (parser.getCurrentToken() != JsonToken.END_OBJECT) {
                            details = parser.parseAndClose(GoogleJsonError.class);
                            detailString = details.toPrettyString();
                        }
                    }
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
                finally {
                    if (parser == null) {
                        response.ignore();
                    }
                    else if (details == null) {
                        parser.close();
                    }
                }
            }
            else {
                detailString = response.parseAsString();
            }
        }
        catch (IOException exception2) {
            exception2.printStackTrace();
        }
        final StringBuilder message = HttpResponseException.computeMessageBuffer(response);
        if (!Strings.isNullOrEmpty(detailString)) {
            message.append(StringUtils.LINE_SEPARATOR).append(detailString);
            builder.setContent(detailString);
        }
        builder.setMessage(message.toString());
        return new GoogleJsonResponseException(builder, details);
    }
    
    public static HttpResponse execute(final JsonFactory jsonFactory, final HttpRequest request) throws GoogleJsonResponseException, IOException {
        Preconditions.checkNotNull(jsonFactory);
        final boolean originalThrowExceptionOnExecuteError = request.getThrowExceptionOnExecuteError();
        if (originalThrowExceptionOnExecuteError) {
            request.setThrowExceptionOnExecuteError(false);
        }
        final HttpResponse response = request.execute();
        request.setThrowExceptionOnExecuteError(originalThrowExceptionOnExecuteError);
        if (!originalThrowExceptionOnExecuteError || response.isSuccessStatusCode()) {
            return response;
        }
        throw from(jsonFactory, response);
    }
}
