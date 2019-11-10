package org.yaml.snakeyaml;

import org.yaml.snakeyaml.emitter.*;
import org.yaml.snakeyaml.events.*;
import java.util.*;
import java.io.*;

private static class SilentEmitter implements Emitable
{
    private List<Event> events;
    
    private SilentEmitter() {
        super();
        this.events = new ArrayList<Event>(100);
    }
    
    public List<Event> getEvents() {
        return this.events;
    }
    
    @Override
    public void emit(final Event event) throws IOException {
        this.events.add(event);
    }
    
    SilentEmitter(final Yaml$1 x0) {
        this();
    }
}
