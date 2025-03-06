// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;

public class AbstractImageZoomInteractor extends InteractorAdapter
{
    protected boolean finished;
    protected int xStart;
    protected int yStart;
    protected int xCurrent;
    protected int yCurrent;
    
    public AbstractImageZoomInteractor() {
        this.finished = true;
    }
    
    public boolean endInteraction() {
        return this.finished;
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
        if (!this.finished) {
            ((JGVTComponent)mouseEvent.getSource()).setPaintingTransform(null);
            return;
        }
        this.finished = false;
        this.xStart = mouseEvent.getX();
        this.yStart = mouseEvent.getY();
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
        this.finished = true;
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        final AffineTransform paintingTransform = jgvtComponent.getPaintingTransform();
        if (paintingTransform != null) {
            final AffineTransform renderingTransform = (AffineTransform)jgvtComponent.getRenderingTransform().clone();
            renderingTransform.preConcatenate(paintingTransform);
            jgvtComponent.setRenderingTransform(renderingTransform);
        }
    }
    
    public void mouseDragged(final MouseEvent mouseEvent) {
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        this.xCurrent = mouseEvent.getX();
        this.yCurrent = mouseEvent.getY();
        final AffineTransform translateInstance = AffineTransform.getTranslateInstance(this.xStart, this.yStart);
        int n = this.yCurrent - this.yStart;
        double n2;
        if (n < 0) {
            n -= 10;
            n2 = ((n > -15) ? 1.0 : (-15.0 / n));
        }
        else {
            n += 10;
            n2 = ((n < 15) ? 1.0 : (n / 15.0));
        }
        translateInstance.scale(n2, n2);
        translateInstance.translate(-this.xStart, -this.yStart);
        jgvtComponent.setPaintingTransform(translateInstance);
    }
}
