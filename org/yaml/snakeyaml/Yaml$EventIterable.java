package org.yaml.snakeyaml;

import org.yaml.snakeyaml.events.*;
import java.util.*;

private static class EventIterable implements Iterable<Event>
{
    private Iterator<Event> iterator;
    
    public EventIterable(final Iterator<Event> iterator) {
        super();
        this.iterator = iterator;
    }
    
    @Override
    public Iterator<Event> iterator() {
        return this.iterator;
    }
}
