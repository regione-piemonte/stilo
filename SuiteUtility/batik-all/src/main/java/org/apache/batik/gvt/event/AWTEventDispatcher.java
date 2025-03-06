// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.event;

import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class AWTEventDispatcher extends AbstractAWTEventDispatcher implements MouseWheelListener
{
    public void mouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
        this.dispatchEvent(mouseWheelEvent);
    }
    
    public void dispatchEvent(final EventObject eventObject) {
        if (eventObject instanceof MouseWheelEvent) {
            if (this.root == null) {
                return;
            }
            if (!this.eventDispatchEnabled) {
                if (this.eventQueueMaxSize > 0) {
                    this.eventQueue.add(eventObject);
                    while (this.eventQueue.size() > this.eventQueueMaxSize) {
                        this.eventQueue.remove(0);
                    }
                }
                return;
            }
            this.dispatchMouseWheelEvent((MouseWheelEvent)eventObject);
        }
        else {
            super.dispatchEvent(eventObject);
        }
    }
    
    protected void dispatchMouseWheelEvent(final MouseWheelEvent mouseWheelEvent) {
        if (this.lastHit != null) {
            this.processMouseWheelEvent(new GraphicsNodeMouseWheelEvent(this.lastHit, mouseWheelEvent.getID(), mouseWheelEvent.getWhen(), mouseWheelEvent.getModifiersEx(), this.getCurrentLockState(), mouseWheelEvent.getWheelRotation()));
        }
    }
    
    protected void processMouseWheelEvent(final GraphicsNodeMouseWheelEvent graphicsNodeMouseWheelEvent) {
        if (this.glisteners != null) {
            final GraphicsNodeMouseWheelListener[] array = (GraphicsNodeMouseWheelListener[])this.getListeners(GraphicsNodeMouseWheelListener.class);
            for (int i = 0; i < array.length; ++i) {
                array[i].mouseWheelMoved(graphicsNodeMouseWheelEvent);
            }
        }
    }
    
    protected void dispatchKeyEvent(final KeyEvent keyEvent) {
        this.currentKeyEventTarget = this.lastHit;
        this.processKeyEvent(new GraphicsNodeKeyEvent((this.currentKeyEventTarget == null) ? this.root : this.currentKeyEventTarget, keyEvent.getID(), keyEvent.getWhen(), keyEvent.getModifiersEx(), this.getCurrentLockState(), keyEvent.getKeyCode(), keyEvent.getKeyChar(), keyEvent.getKeyLocation()));
    }
    
    protected int getModifiers(final InputEvent inputEvent) {
        return inputEvent.getModifiersEx();
    }
    
    protected int getButton(final MouseEvent mouseEvent) {
        return mouseEvent.getButton();
    }
    
    protected static boolean isMetaDown(final int n) {
        return (n & 0x100) != 0x0;
    }
}
