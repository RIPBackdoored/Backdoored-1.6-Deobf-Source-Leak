package com.google.api.client.util;

import java.util.*;

public final class Lists
{
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }
    
    public static <E> ArrayList<E> newArrayListWithCapacity(final int initialArraySize) {
        return new ArrayList<E>(initialArraySize);
    }
    
    public static <E> ArrayList<E> newArrayList(final Iterable<? extends E> elements) {
        return (elements instanceof Collection) ? new ArrayList<E>(Collections2.cast(elements)) : newArrayList(elements.iterator());
    }
    
    public static <E> ArrayList<E> newArrayList(final Iterator<? extends E> elements) {
        final ArrayList<E> list = newArrayList();
        while (elements.hasNext()) {
            list.add((E)elements.next());
        }
        return list;
    }
    
    private Lists() {
        super();
    }
}
