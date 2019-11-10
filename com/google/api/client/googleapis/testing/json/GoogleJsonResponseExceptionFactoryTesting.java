package com.google.api.client.googleapis.testing.json;

import com.google.api.client.util.*;
import com.google.api.client.json.*;
import com.google.api.client.googleapis.json.*;
import com.google.api.client.testing.http.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public final class GoogleJsonResponseExceptionFactoryTesting
{
    public GoogleJsonResponseExceptionFactoryTesting() {
        super();
    }
    
    public static GoogleJsonResponseException newMock(final JsonFactory jsonFactory, final int httpCode, final String reasonPhrase) throws IOException {
        final MockLowLevelHttpResponse otherServiceUnavaiableLowLevelResponse = new MockLowLevelHttpResponse().setStatusCode(httpCode).setReasonPhrase(reasonPhrase);
        final MockHttpTransport otherTransport = new MockHttpTransport.Builder().setLowLevelHttpResponse(otherServiceUnavaiableLowLevelResponse).build();
        final HttpRequest otherRequest = otherTransport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
        otherRequest.setThrowExceptionOnExecuteError(false);
        final HttpResponse otherServiceUnavailableResponse = otherRequest.execute();
        return GoogleJsonResponseException.from(jsonFactory, otherServiceUnavailableResponse);
    }
}
