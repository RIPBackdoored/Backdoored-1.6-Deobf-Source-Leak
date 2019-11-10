package com.google.api.client.util;

static final class Sleeper$1 implements Sleeper {
    Sleeper$1() {
        super();
    }
    
    @Override
    public void sleep(final long millis) throws InterruptedException {
        Thread.sleep(millis);
    }
}