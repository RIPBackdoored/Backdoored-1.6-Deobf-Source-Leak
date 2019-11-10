package javassist.bytecode.analysis;

import java.util.*;

class IntQueue
{
    private Entry head;
    private Entry tail;
    
    IntQueue() {
        super();
    }
    
    void add(final int value) {
        final Entry entry = new Entry(value);
        if (this.tail != null) {
            this.tail.next = entry;
        }
        this.tail = entry;
        if (this.head == null) {
            this.head = entry;
        }
    }
    
    boolean isEmpty() {
        return this.head == null;
    }
    
    int take() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        final int value = this.head.value;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        }
        return value;
    }
    
    private static class Entry
    {
        private Entry next;
        private int value;
        
        private Entry(final int value) {
            super();
            this.value = value;
        }
        
        Entry(final int x0, final IntQueue$1 x1) {
            this(x0);
        }
        
        static /* synthetic */ Entry access$102(final Entry x0, final Entry x1) {
            return x0.next = x1;
        }
        
        static /* synthetic */ int access$200(final Entry x0) {
            return x0.value;
        }
        
        static /* synthetic */ Entry access$100(final Entry x0) {
            return x0.next;
        }
    }
}
