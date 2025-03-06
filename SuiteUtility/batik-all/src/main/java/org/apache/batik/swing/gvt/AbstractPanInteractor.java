// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public abstract class AbstractPanInteractor extends InteractorAdapter
{
    public static final Cursor PAN_CURSOR;
    protected boolean finished;
    protected int xStart;
    protected int yStart;
    protected int xCurrent;
    protected int yCurrent;
    protected Cursor previousCursor;
    
    public AbstractPanInteractor() {
        this.finished = true;
    }
    
    public boolean endInteraction() {
        return this.finished;
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
        if (!this.finished) {
            this.mouseExited(mouseEvent);
            return;
        }
        this.finished = false;
        this.xStart = mouseEvent.getX();
        this.yStart = mouseEvent.getY();
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        this.previousCursor = jgvtComponent.getCursor();
        jgvtComponent.setCursor(AbstractPanInteractor.PAN_CURSOR);
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
        if (this.finished) {
            return;
        }
        this.finished = true;
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        this.xCurrent = mouseEvent.getX();
        this.yCurrent = mouseEvent.getY();
        final AffineTransform translateInstance = AffineTransform.getTranslateInstance(this.xCurrent - this.xStart, this.yCurrent - this.yStart);
        final AffineTransform renderingTransform = (AffineTransform)jgvtComponent.getRenderingTransform().clone();
        renderingTransform.preConcatenate(translateInstance);
        jgvtComponent.setRenderingTransform(renderingTransform);
        if (jgvtComponent.getCursor() == AbstractPanInteractor.PAN_CURSOR) {
            jgvtComponent.setCursor(this.previousCursor);
        }
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
        this.finished = true;
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        jgvtComponent.setPaintingTransform(null);
        if (jgvtComponent.getCursor() == AbstractPanInteractor.PAN_CURSOR) {
            jgvtComponent.setCursor(this.previousCursor);
        }
    }
    
    public void mouseDragged(final MouseEvent mouseEvent) {
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        this.xCurrent = mouseEvent.getX();
        this.yCurrent = mouseEvent.getY();
        jgvtComponent.setPaintingTransform(AffineTransform.getTranslateInstance(this.xCurrent - this.xStart, this.yCurrent - this.yStart));
    }
    
    static {
        PAN_CURSOR = new Cursor(13);
    }
}
