package javassist.util;

import com.sun.jdi.event.*;

class HotSwapper$1 extends Thread {
    final /* synthetic */ HotSwapper this$0;
    
    HotSwapper$1(final HotSwapper this$0) {
        this.this$0 = this$0;
        super();
    }
    
    private void errorMsg(final Throwable e) {
        System.err.print("Exception in thread \"HotSwap\" ");
        e.printStackTrace(System.err);
    }
    
    @Override
    public void run() {
        EventSet events = null;
        try {
            events = this.this$0.waitEvent();
            final EventIterator iter = events.eventIterator();
            while (iter.hasNext()) {
                final Event event = iter.nextEvent();
                if (event instanceof MethodEntryEvent) {
                    this.this$0.hotswap();
                    break;
                }
            }
        }
        catch (Throwable e) {
            this.errorMsg(e);
        }
        try {
            if (events != null) {
                events.resume();
            }
        }
        catch (Throwable e) {
            this.errorMsg(e);
        }
    }
}