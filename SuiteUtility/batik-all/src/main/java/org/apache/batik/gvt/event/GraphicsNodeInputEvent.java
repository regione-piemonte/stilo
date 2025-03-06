// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import java.awt.event.InputEvent;
import org.apache.batik.gvt.GraphicsNode;

public abstract class GraphicsNodeInputEvent extends GraphicsNodeEvent
{
    public static final int SHIFT_MASK = 1;
    public static final int CTRL_MASK = 2;
    public static final int META_MASK = 4;
    public static final int ALT_MASK = 8;
    public static final int ALT_GRAPH_MASK = 32;
    public static final int BUTTON1_MASK = 1024;
    public static final int BUTTON2_MASK = 2048;
    public static final int BUTTON3_MASK = 4096;
    public static final int CAPS_LOCK_MASK = 1;
    public static final int NUM_LOCK_MASK = 2;
    public static final int SCROLL_LOCK_MASK = 4;
    public static final int KANA_LOCK_MASK = 8;
    long when;
    int modifiers;
    int lockState;
    
    protected GraphicsNodeInputEvent(final GraphicsNode graphicsNode, final int n, final long when, final int modifiers, final int lockState) {
        super(graphicsNode, n);
        this.when = when;
        this.modifiers = modifiers;
        this.lockState = lockState;
    }
    
    protected GraphicsNodeInputEvent(final GraphicsNode graphicsNode, final InputEvent inputEvent, final int lockState) {
        super(graphicsNode, inputEvent.getID());
        this.when = inputEvent.getWhen();
        this.modifiers = inputEvent.getModifiers();
        this.lockState = lockState;
    }
    
    public boolean isShiftDown() {
        return (this.modifiers & 0x1) != 0x0;
    }
    
    public boolean isControlDown() {
        return (this.modifiers & 0x2) != 0x0;
    }
    
    public boolean isMetaDown() {
        return AWTEventDispatcher.isMetaDown(this.modifiers);
    }
    
    public boolean isAltDown() {
        return (this.modifiers & 0x8) != 0x0;
    }
    
    public boolean isAltGraphDown() {
        return (this.modifiers & 0x20) != 0x0;
    }
    
    public long getWhen() {
        return this.when;
    }
    
    public int getModifiers() {
        return this.modifiers;
    }
    
    public int getLockState() {
        return this.lockState;
    }
}
