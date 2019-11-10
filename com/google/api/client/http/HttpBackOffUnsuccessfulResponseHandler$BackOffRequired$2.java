package com.google.api.client.http;

static final class HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$2 implements BackOffRequired {
    HttpBackOffUnsuccessfulResponseHandler$BackOffRequired$2() {
        super();
    }
    
    @Override
    public boolean isRequired(final HttpResponse response) {
        return response.getStatusCode() / 100 == 5;
    }
}