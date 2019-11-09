package com.sun.jna;

import java.awt.*;

private static class AWT
{
    private AWT() {
        super();
    }
    
    static long getWindowID(final Window w) throws HeadlessException {
        return getComponentID(w);
    }
    
    static long getComponentID(final Object o) throws HeadlessException {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("No native windows when headless");
        }
        final Component c = (Component)o;
        if (c.isLightweight()) {
            throw new IllegalArgumentException("Component must be heavyweight");
        }
        if (!c.isDisplayable()) {
            throw new IllegalStateException("Component must be displayable");
        }
        if (Platform.isX11() && System.getProperty("java.version").startsWith("1.4") && !c.isVisible()) {
            throw new IllegalStateException("Component must be visible");
        }
        return Native.getWindowHandle0(c);
    }
}
