package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static final class InsnListSlice extends ReadOnlyInsnList
{
    private final int start;
    private final int end;
    
    protected InsnListSlice(final InsnList inner, final int start, final int end) {
        super(inner);
        this.start = start;
        this.end = end;
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator() {
        return this.iterator(0);
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator(final int index) {
        return new SliceIterator(super.iterator(this.start + index), this.start, this.end, this.start + index);
    }
    
    @Override
    public AbstractInsnNode[] toArray() {
        final AbstractInsnNode[] all = super.toArray();
        final AbstractInsnNode[] subset = new AbstractInsnNode[this.size()];
        System.arraycopy(all, this.start, subset, 0, subset.length);
        return subset;
    }
    
    @Override
    public int size() {
        return this.end - this.start + 1;
    }
    
    @Override
    public AbstractInsnNode getFirst() {
        return super.get(this.start);
    }
    
    @Override
    public AbstractInsnNode getLast() {
        return super.get(this.end);
    }
    
    @Override
    public AbstractInsnNode get(final int index) {
        return super.get(this.start + index);
    }
    
    @Override
    public boolean contains(final AbstractInsnNode insn) {
        for (final AbstractInsnNode node : this.toArray()) {
            if (node == insn) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int indexOf(final AbstractInsnNode insn) {
        final int index = super.indexOf(insn);
        return (index >= this.start && index <= this.end) ? (index - this.start) : -1;
    }
    
    public int realIndexOf(final AbstractInsnNode insn) {
        return super.indexOf(insn);
    }
    
    static class SliceIterator implements ListIterator<AbstractInsnNode>
    {
        private final ListIterator<AbstractInsnNode> iter;
        private int start;
        private int end;
        private int index;
        
        public SliceIterator(final ListIterator<AbstractInsnNode> iter, final int start, final int end, final int index) {
            super();
            this.iter = iter;
            this.start = start;
            this.end = end;
            this.index = index;
        }
        
        @Override
        public boolean hasNext() {
            return this.index <= this.end && this.iter.hasNext();
        }
        
        @Override
        public AbstractInsnNode next() {
            if (this.index > this.end) {
                throw new NoSuchElementException();
            }
            ++this.index;
            return this.iter.next();
        }
        
        @Override
        public boolean hasPrevious() {
            return this.index > this.start;
        }
        
        @Override
        public AbstractInsnNode previous() {
            if (this.index <= this.start) {
                throw new NoSuchElementException();
            }
            --this.index;
            return this.iter.previous();
        }
        
        @Override
        public int nextIndex() {
            return this.index - this.start;
        }
        
        @Override
        public int previousIndex() {
            return this.index - this.start - 1;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove insn from slice");
        }
        
        @Override
        public void set(final AbstractInsnNode e) {
            throw new UnsupportedOperationException("Cannot set insn using slice");
        }
        
        @Override
        public void add(final AbstractInsnNode e) {
            throw new UnsupportedOperationException("Cannot add insn using slice");
        }
        
        @Override
        public /* bridge */ void add(final Object o) {
            this.add((AbstractInsnNode)o);
        }
        
        @Override
        public /* bridge */ void set(final Object o) {
            this.set((AbstractInsnNode)o);
        }
        
        @Override
        public /* bridge */ Object previous() {
            return this.previous();
        }
        
        @Override
        public /* bridge */ Object next() {
            return this.next();
        }
    }
}
