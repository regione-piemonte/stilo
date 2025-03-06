// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import java.awt.event.MouseWheelEvent;
import org.apache.batik.gvt.event.AWTEventDispatcher;
import org.apache.batik.swing.gvt.AbstractJGVTComponent;

public class JSVGComponent extends AbstractJSVGComponent
{
    public JSVGComponent(final SVGUserAgent svgUserAgent, final boolean b, final boolean b2) {
        super(svgUserAgent, b, b2);
    }
    
    protected Listener createListener() {
        return new ExtendedSVGListener();
    }
    
    protected class ExtendedSVGListener extends SVGListener
    {
        private final /* synthetic */ JSVGComponent this$0;
        
        protected void dispatchMouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
            if (!JSVGComponent.this.isInteractiveDocument) {
                super.dispatchMouseWheelMoved(mouseWheelEvent);
                return;
            }
            if (JSVGComponent.this.updateManager != null && JSVGComponent.this.updateManager.isRunning()) {
                JSVGComponent.this.updateManager.getUpdateRunnableQueue().invokeLater(new Runnable() {
                    private final /* synthetic */ ExtendedSVGListener this$1 = this$1;
                    
                    public void run() {
                        this.this$1.this$0.eventDispatcher.mouseWheelMoved(mouseWheelEvent);
                    }
                });
            }
        }
    }
}
