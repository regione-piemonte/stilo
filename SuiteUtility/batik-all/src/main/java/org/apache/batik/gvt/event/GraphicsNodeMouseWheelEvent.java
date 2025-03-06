// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import org.apache.batik.gvt.GraphicsNode;

public class GraphicsNodeMouseWheelEvent extends GraphicsNodeInputEvent
{
    public static final int MOUSE_WHEEL = 600;
    protected int wheelDelta;
    
    public GraphicsNodeMouseWheelEvent(final GraphicsNode graphicsNode, final int n, final long n2, final int n3, final int n4, final int wheelDelta) {
        super(graphicsNode, n, n2, n3, n4);
        this.wheelDelta = wheelDelta;
    }
    
    public int getWheelDelta() {
        return this.wheelDelta;
    }
}
