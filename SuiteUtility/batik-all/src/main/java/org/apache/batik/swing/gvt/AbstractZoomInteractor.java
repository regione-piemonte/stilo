// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;

public class AbstractZoomInteractor extends InteractorAdapter
{
    protected boolean finished;
    protected int xStart;
    protected int yStart;
    protected int xCurrent;
    protected int yCurrent;
    protected Line2D markerTop;
    protected Line2D markerLeft;
    protected Line2D markerBottom;
    protected Line2D markerRight;
    protected Overlay overlay;
    protected BasicStroke markerStroke;
    
    public AbstractZoomInteractor() {
        this.finished = true;
        this.overlay = new ZoomOverlay();
        this.markerStroke = new BasicStroke(1.0f, 2, 0, 10.0f, new float[] { 4.0f, 4.0f }, 0.0f);
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
        this.markerTop = null;
        this.markerLeft = null;
        this.markerBottom = null;
        this.markerRight = null;
        this.xStart = mouseEvent.getX();
        this.yStart = mouseEvent.getY();
        ((JGVTComponent)mouseEvent.getSource()).getOverlays().add(this.overlay);
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
        this.finished = true;
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        jgvtComponent.getOverlays().remove(this.overlay);
        this.overlay.paint(jgvtComponent.getGraphics());
        this.xCurrent = mouseEvent.getX();
        this.yCurrent = mouseEvent.getY();
        if (this.xCurrent - this.xStart != 0 && this.yCurrent - this.yStart != 0) {
            int n = this.xCurrent - this.xStart;
            int n2 = this.yCurrent - this.yStart;
            if (n < 0) {
                n = -n;
                this.xStart = this.xCurrent;
            }
            if (n2 < 0) {
                n2 = -n2;
                this.yStart = this.yCurrent;
            }
            final Dimension size = jgvtComponent.getSize();
            final float n3 = size.width / (float)n;
            final float n4 = size.height / (float)n2;
            final float n5 = (n3 < n4) ? n3 : n4;
            final AffineTransform renderingTransform = new AffineTransform();
            renderingTransform.scale(n5, n5);
            renderingTransform.translate(-this.xStart, -this.yStart);
            renderingTransform.concatenate(jgvtComponent.getRenderingTransform());
            jgvtComponent.setRenderingTransform(renderingTransform);
        }
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
        this.finished = true;
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        jgvtComponent.getOverlays().remove(this.overlay);
        this.overlay.paint(jgvtComponent.getGraphics());
    }
    
    public void mouseDragged(final MouseEvent mouseEvent) {
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        this.overlay.paint(jgvtComponent.getGraphics());
        this.xCurrent = mouseEvent.getX();
        this.yCurrent = mouseEvent.getY();
        float x1;
        float n;
        if (this.xStart < this.xCurrent) {
            x1 = (float)this.xStart;
            n = (float)(this.xCurrent - this.xStart);
        }
        else {
            x1 = (float)this.xCurrent;
            n = (float)(this.xStart - this.xCurrent);
        }
        float n2;
        float n3;
        if (this.yStart < this.yCurrent) {
            n2 = (float)this.yStart;
            n3 = (float)(this.yCurrent - this.yStart);
        }
        else {
            n2 = (float)this.yCurrent;
            n3 = (float)(this.yStart - this.yCurrent);
        }
        final Dimension size = jgvtComponent.getSize();
        final float n4 = size.width / (float)size.height;
        if (n4 > n / n3) {
            n = n4 * n3;
        }
        else {
            n3 = n / n4;
        }
        this.markerTop = new Line2D.Float(x1, n2, x1 + n, n2);
        this.markerLeft = new Line2D.Float(x1, n2, x1, n2 + n3);
        this.markerBottom = new Line2D.Float(x1, n2 + n3, x1 + n, n2 + n3);
        this.markerRight = new Line2D.Float(x1 + n, n2, x1 + n, n2 + n3);
        this.overlay.paint(jgvtComponent.getGraphics());
    }
    
    protected class ZoomOverlay implements Overlay
    {
        public void paint(final Graphics graphics) {
            if (AbstractZoomInteractor.this.markerTop != null) {
                final Graphics2D graphics2D = (Graphics2D)graphics;
                graphics2D.setXORMode(Color.white);
                graphics2D.setColor(Color.black);
                graphics2D.setStroke(AbstractZoomInteractor.this.markerStroke);
                graphics2D.draw(AbstractZoomInteractor.this.markerTop);
                graphics2D.draw(AbstractZoomInteractor.this.markerLeft);
                graphics2D.draw(AbstractZoomInteractor.this.markerBottom);
                graphics2D.draw(AbstractZoomInteractor.this.markerRight);
            }
        }
    }
}
