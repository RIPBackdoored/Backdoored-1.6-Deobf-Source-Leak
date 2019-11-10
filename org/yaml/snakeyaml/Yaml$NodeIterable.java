package org.yaml.snakeyaml;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;

private static class NodeIterable implements Iterable<Node>
{
    private Iterator<Node> iterator;
    
    public NodeIterable(final Iterator<Node> iterator) {
        super();
        this.iterator = iterator;
    }
    
    @Override
    public Iterator<Node> iterator() {
        return this.iterator;
    }
}
