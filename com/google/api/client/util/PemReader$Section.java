package com.google.api.client.util;

public static final class Section
{
    private final String title;
    private final byte[] base64decodedBytes;
    
    Section(final String title, final byte[] base64decodedBytes) {
        super();
        this.title = Preconditions.checkNotNull(title);
        this.base64decodedBytes = Preconditions.checkNotNull(base64decodedBytes);
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public byte[] getBase64DecodedBytes() {
        return this.base64decodedBytes;
    }
}
