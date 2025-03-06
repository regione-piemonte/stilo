// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class JGVTComponent extends AbstractJGVTComponent
{
    public JGVTComponent() {
    }
    
    public JGVTComponent(final boolean b, final boolean b2) {
        super(b, b2);
    }
    
    protected void addAWTListeners() {
        super.addAWTListeners();
        this.addMouseWheelListener((MouseWheelListener)this.listener);
    }
    
    protected Listener createListener() {
        return new ExtendedListener();
    }
    
    protected class ExtendedListener extends Listener implements MouseWheelListener
    {
        public void mouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
            if (JGVTComponent.this.eventDispatcher != null) {
                this.dispatchMouseWheelMoved(mouseWheelEvent);
            }
        }
        
        protected void dispatchMouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
            JGVTComponent.this.eventDispatcher.mouseWheelMoved(mouseWheelEvent);
        }
    }
}
