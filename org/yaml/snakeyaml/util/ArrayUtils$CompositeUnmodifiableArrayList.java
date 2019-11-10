package org.yaml.snakeyaml.util;

import java.util.*;

private static class CompositeUnmodifiableArrayList<E> extends AbstractList<E>
{
    private final E[] array1;
    private final E[] array2;
    
    CompositeUnmodifiableArrayList(final E[] array1, final E[] array2) {
        super();
        this.array1 = array1;
        this.array2 = array2;
    }
    
    @Override
    public E get(final int index) {
        E element;
        if (index < this.array1.length) {
            element = this.array1[index];
        }
        else {
            if (index - this.array1.length >= this.array2.length) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size());
            }
            element = this.array2[index - this.array1.length];
        }
        return element;
    }
    
    @Override
    public int size() {
        return this.array1.length + this.array2.length;
    }
}
