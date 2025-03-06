// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import org.apache.batik.gvt.GraphicsNode;

public class GraphicsNodeKeyEvent extends GraphicsNodeInputEvent
{
    static final int KEY_FIRST = 400;
    public static final int KEY_TYPED = 400;
    public static final int KEY_PRESSED = 401;
    public static final int KEY_RELEASED = 402;
    protected int keyCode;
    protected char keyChar;
    protected int keyLocation;
    
    public GraphicsNodeKeyEvent(final GraphicsNode graphicsNode, final int n, final long n2, final int n3, final int n4, final int keyCode, final char keyChar, final int keyLocation) {
        super(graphicsNode, n, n2, n3, n4);
        this.keyCode = keyCode;
        this.keyChar = keyChar;
        this.keyLocation = keyLocation;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    public char getKeyChar() {
        return this.keyChar;
    }
    
    public int getKeyLocation() {
        return this.keyLocation;
    }
}
