package com.sun.jna;

import java.util.*;

static class StructureSet extends AbstractCollection<Structure> implements Set<Structure>
{
    Structure[] elements;
    private int count;
    
    StructureSet() {
        super();
    }
    
    private void ensureCapacity(final int size) {
        if (this.elements == null) {
            this.elements = new Structure[size * 3 / 2];
        }
        else if (this.elements.length < size) {
            final Structure[] e = new Structure[size * 3 / 2];
            System.arraycopy(this.elements, 0, e, 0, this.elements.length);
            this.elements = e;
        }
    }
    
    public Structure[] getElements() {
        return this.elements;
    }
    
    @Override
    public int size() {
        return this.count;
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.indexOf((Structure)o) != -1;
    }
    
    @Override
    public boolean add(final Structure o) {
        if (!this.contains(o)) {
            this.ensureCapacity(this.count + 1);
            this.elements[this.count++] = o;
        }
        return true;
    }
    
    private int indexOf(final Structure s1) {
        for (int i = 0; i < this.count; ++i) {
            final Structure s2 = this.elements[i];
            if (s1 == s2 || (s1.getClass() == s2.getClass() && s1.size() == s2.size() && s1.getPointer().equals(s2.getPointer()))) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public boolean remove(final Object o) {
        final int idx = this.indexOf((Structure)o);
        if (idx != -1) {
            if (--this.count >= 0) {
                this.elements[idx] = this.elements[this.count];
                this.elements[this.count] = null;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public Iterator<Structure> iterator() {
        final Structure[] e = new Structure[this.count];
        if (this.count > 0) {
            System.arraycopy(this.elements, 0, e, 0, this.count);
        }
        return Arrays.asList(e).iterator();
    }
    
    @Override
    public /* bridge */ boolean add(final Object o) {
        return this.add((Structure)o);
    }
}
