package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

public static class Node
{
    private Block block;
    private Node parent;
    private Node[] children;
    
    Node(final Block b) {
        super();
        this.block = b;
        this.parent = null;
    }
    
    @Override
    public String toString() {
        final StringBuffer sbuf = new StringBuffer();
        sbuf.append("Node[pos=").append(this.block().position());
        sbuf.append(", parent=");
        sbuf.append((this.parent == null) ? "*" : Integer.toString(this.parent.block().position()));
        sbuf.append(", children{");
        for (int i = 0; i < this.children.length; ++i) {
            sbuf.append(this.children[i].block().position()).append(", ");
        }
        sbuf.append("}]");
        return sbuf.toString();
    }
    
    public Block block() {
        return this.block;
    }
    
    public Node parent() {
        return this.parent;
    }
    
    public int children() {
        return this.children.length;
    }
    
    public Node child(final int n) {
        return this.children[n];
    }
    
    int makeDepth1stTree(final Node caller, final boolean[] visited, int counter, final int[] distance, final Access access) {
        final int index = this.block.index;
        if (visited[index]) {
            return counter;
        }
        visited[index] = true;
        this.parent = caller;
        final BasicBlock[] exits = access.exits(this);
        if (exits != null) {
            for (int i = 0; i < exits.length; ++i) {
                final Node n = access.node(exits[i]);
                counter = n.makeDepth1stTree(this, visited, counter, distance, access);
            }
        }
        distance[index] = counter++;
        return counter;
    }
    
    boolean makeDominatorTree(final boolean[] visited, final int[] distance, final Access access) {
        final int index = this.block.index;
        if (visited[index]) {
            return false;
        }
        visited[index] = true;
        boolean changed = false;
        final BasicBlock[] exits = access.exits(this);
        if (exits != null) {
            for (int i = 0; i < exits.length; ++i) {
                final Node n = access.node(exits[i]);
                if (n.makeDominatorTree(visited, distance, access)) {
                    changed = true;
                }
            }
        }
        final BasicBlock[] entrances = access.entrances(this);
        if (entrances != null) {
            for (int j = 0; j < entrances.length; ++j) {
                if (this.parent != null) {
                    final Node n2 = getAncestor(this.parent, access.node(entrances[j]), distance);
                    if (n2 != this.parent) {
                        this.parent = n2;
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }
    
    private static Node getAncestor(Node n1, Node n2, final int[] distance) {
        while (n1 != n2) {
            if (distance[n1.block.index] < distance[n2.block.index]) {
                n1 = n1.parent;
            }
            else {
                n2 = n2.parent;
            }
            if (n1 == null || n2 == null) {
                return null;
            }
        }
        return n1;
    }
    
    private static void setChildren(final Node[] all) {
        final int size = all.length;
        final int[] nchildren = new int[size];
        for (int i = 0; i < size; ++i) {
            nchildren[i] = 0;
        }
        for (int i = 0; i < size; ++i) {
            final Node p = all[i].parent;
            if (p != null) {
                final int[] array = nchildren;
                final int index = p.block.index;
                ++array[index];
            }
        }
        for (int i = 0; i < size; ++i) {
            all[i].children = new Node[nchildren[i]];
        }
        for (int i = 0; i < size; ++i) {
            nchildren[i] = 0;
        }
        for (final Node n : all) {
            final Node p2 = n.parent;
            if (p2 != null) {
                p2.children[nchildren[p2.block.index]++] = n;
            }
        }
    }
    
    static /* synthetic */ Block access$200(final Node x0) {
        return x0.block;
    }
    
    static /* synthetic */ void access$300(final Node[] x0) {
        setChildren(x0);
    }
}
