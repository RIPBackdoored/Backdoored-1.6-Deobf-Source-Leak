package com.sun.jna;

static final class Native$1 implements Callback.UncaughtExceptionHandler {
    Native$1() {
        super();
    }
    
    @Override
    public void uncaughtException(final Callback c, final Throwable e) {
        System.err.println("JNA: Callback " + c + " threw the following exception:");
        e.printStackTrace();
    }
}