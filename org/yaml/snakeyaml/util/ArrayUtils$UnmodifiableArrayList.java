package org.yaml.snakeyaml.util;

import java.util.*;

private static class UnmodifiableArrayList<E> extends AbstractList<E>
{
    private final E[] array;
    
    UnmodifiableArrayList(final E[] array) {
        super();
        this.array = array;
    }
    
    @Override
    public E get(final int index) {
        if (index >= this.array.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size());
        }
        return this.array[index];
    }
    
    @Override
    public int size() {
        return this.array.length;
    }
}
