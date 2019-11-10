package com.google.api.client.googleapis.notifications;

import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.nio.charset.*;

@Beta
public abstract class TypedNotificationCallback<T> implements UnparsedNotificationCallback
{
    private static final long serialVersionUID = 1L;
    
    public TypedNotificationCallback() {
        super();
    }
    
    protected abstract void onNotification(final StoredChannel p0, final TypedNotification<T> p1) throws IOException;
    
    protected abstract ObjectParser getObjectParser() throws IOException;
    
    protected abstract Class<T> getDataClass() throws IOException;
    
    public final void onNotification(final StoredChannel storedChannel, final UnparsedNotification notification) throws IOException {
        final TypedNotification<T> typedNotification = new TypedNotification<T>(notification);
        final String contentType = notification.getContentType();
        if (contentType != null) {
            final Charset charset = new HttpMediaType(contentType).getCharsetParameter();
            final Class<T> dataClass = Preconditions.checkNotNull(this.getDataClass());
            typedNotification.setContent(this.getObjectParser().parseAndClose(notification.getContentStream(), charset, dataClass));
        }
        this.onNotification(storedChannel, typedNotification);
    }
}
