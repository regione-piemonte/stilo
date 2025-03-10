// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import org.apache.batik.gvt.GraphicsNode;

public class GraphicsNodeChangeEvent extends GraphicsNodeEvent
{
    static final int CHANGE_FIRST = 9800;
    public static final int CHANGE_STARTED = 9800;
    public static final int CHANGE_COMPLETED = 9801;
    protected GraphicsNode changeSource;
    
    public GraphicsNodeChangeEvent(final GraphicsNode graphicsNode, final int n) {
        super(graphicsNode, n);
    }
    
    public void setChangeSrc(final GraphicsNode changeSource) {
        this.changeSource = changeSource;
    }
    
    public GraphicsNode getChangeSrc() {
        return this.changeSource;
    }
}
