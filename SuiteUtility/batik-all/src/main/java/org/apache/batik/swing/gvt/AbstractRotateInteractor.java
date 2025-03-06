// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.geom.AffineTransform;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

public class AbstractRotateInteractor extends InteractorAdapter
{
    protected boolean finished;
    protected double initialRotation;
    
    public boolean endInteraction() {
        return this.finished;
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
        this.finished = false;
        final Dimension size = ((JGVTComponent)mouseEvent.getSource()).getSize();
        final double n = mouseEvent.getX() - size.width / 2;
        final double n2 = mouseEvent.getY() - size.height / 2;
        final double n3 = -n2 / Math.sqrt(n * n + n2 * n2);
        this.initialRotation = ((n > 0.0) ? Math.acos(n3) : (-Math.acos(n3)));
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
        this.finished = true;
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        final AffineTransform rotateTransform = this.rotateTransform(jgvtComponent.getSize(), mouseEvent.getX(), mouseEvent.getY());
        rotateTransform.concatenate(jgvtComponent.getRenderingTransform());
        jgvtComponent.setRenderingTransform(rotateTransform);
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
        this.finished = true;
        ((JGVTComponent)mouseEvent.getSource()).setPaintingTransform(null);
    }
    
    public void mouseDragged(final MouseEvent mouseEvent) {
        final JGVTComponent jgvtComponent = (JGVTComponent)mouseEvent.getSource();
        jgvtComponent.setPaintingTransform(this.rotateTransform(jgvtComponent.getSize(), mouseEvent.getX(), mouseEvent.getY()));
    }
    
    protected AffineTransform rotateTransform(final Dimension dimension, final int n, final int n2) {
        final double n3 = n - dimension.width / 2;
        final double n4 = n2 - dimension.height / 2;
        final double n5 = -n4 / Math.sqrt(n3 * n3 + n4 * n4);
        return AffineTransform.getRotateInstance(((n3 > 0.0) ? Math.acos(n5) : (-Math.acos(n5))) - this.initialRotation, dimension.width / 2, dimension.height / 2);
    }
}
