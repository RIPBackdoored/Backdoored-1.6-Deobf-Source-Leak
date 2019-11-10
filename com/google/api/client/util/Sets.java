package com.google.api.client.util;

import java.util.*;

public final class Sets
{
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<E>();
    }
    
    public static <E extends Comparable<?>> TreeSet<E> newTreeSet() {
        return new TreeSet<E>();
    }
    
    private Sets() {
        super();
    }
}
