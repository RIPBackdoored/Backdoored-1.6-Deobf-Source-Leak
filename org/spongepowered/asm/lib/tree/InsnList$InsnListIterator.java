package org.spongepowered.asm.lib.tree;

import java.util.*;

private final class InsnListIterator implements ListIterator
{
    AbstractInsnNode next;
    AbstractInsnNode prev;
    AbstractInsnNode remove;
    final /* synthetic */ InsnList this$0;
    
    InsnListIterator(final InsnList this$0, final int index) {
        this.this$0 = this$0;
        super();
        if (index == this$0.size()) {
            this.next = null;
            this.prev = this$0.getLast();
        }
        else {
            this.next = this$0.get(index);
            this.prev = this.next.prev;
        }
    }
    
    public boolean hasNext() {
        return this.next != null;
    }
    
    public Object next() {
        if (this.next == null) {
            throw new NoSuchElementException();
        }
        final AbstractInsnNode result = this.next;
        this.prev = result;
        this.next = result.next;
        return this.remove = result;
    }
    
    public void remove() {
        if (this.remove != null) {
            if (this.remove == this.next) {
                this.next = this.next.next;
            }
            else {
                this.prev = this.prev.prev;
            }
            this.this$0.remove(this.remove);
            this.remove = null;
            return;
        }
        throw new IllegalStateException();
    }
    
    public boolean hasPrevious() {
        return this.prev != null;
    }
    
    public Object previous() {
        final AbstractInsnNode result = this.prev;
        this.next = result;
        this.prev = result.prev;
        return this.remove = result;
    }
    
    public int nextIndex() {
        if (this.next == null) {
            return this.this$0.size();
        }
        if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
        }
        return this.next.index;
    }
    
    public int previousIndex() {
        if (this.prev == null) {
            return -1;
        }
        if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
        }
        return this.prev.index;
    }
    
    public void add(final Object o) {
        if (this.next != null) {
            this.this$0.insertBefore(this.next, (AbstractInsnNode)o);
        }
        else if (this.prev != null) {
            this.this$0.insert(this.prev, (AbstractInsnNode)o);
        }
        else {
            this.this$0.add((AbstractInsnNode)o);
        }
        this.prev = (AbstractInsnNode)o;
        this.remove = null;
    }
    
    public void set(final Object o) {
        if (this.remove != null) {
            this.this$0.set(this.remove, (AbstractInsnNode)o);
            if (this.remove == this.prev) {
                this.prev = (AbstractInsnNode)o;
            }
            else {
                this.next = (AbstractInsnNode)o;
            }
            return;
        }
        throw new IllegalStateException();
    }
}
