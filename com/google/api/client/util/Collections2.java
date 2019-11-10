package com.google.api.client.util;

import java.util.*;

public final class Collections2
{
    static <T> Collection<T> cast(final Iterable<T> iterable) {
        return (Collection<T>)(Collection)iterable;
    }
    
    private Collections2() {
        super();
    }
}
