// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public abstract class AbstractResetTransformInteractor implements Interactor
{
    protected boolean finished;
    
    public AbstractResetTransformInteractor() {
        this.finished = true;
    }
    
    public boolean endInteraction() {
        return this.finished;
    }
    
    public void keyTyped(final KeyEvent keyEvent) {
        this.resetTransform(keyEvent);
    }
    
    public void keyPressed(final KeyEvent keyEvent) {
        this.resetTransform(keyEvent);
    }
    
    public void keyReleased(final KeyEvent keyEvent) {
        this.resetTransform(keyEvent);
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
        this.resetTransform(mouseEvent);
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
        this.resetTransform(mouseEvent);
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
        this.resetTransform(mouseEvent);
    }
    
    public void mouseEntered(final MouseEvent mouseEvent) {
        this.resetTransform(mouseEvent);
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
        this.resetTransform(mouseEvent);
    }
    
    public void mouseDragged(final MouseEvent mouseEvent) {
        this.resetTransform(mouseEvent);
    }
    
    public void mouseMoved(final MouseEvent mouseEvent) {
        this.resetTransform(mouseEvent);
    }
    
    protected void resetTransform(final InputEvent inputEvent) {
        ((JGVTComponent)inputEvent.getSource()).resetRenderingTransform();
        this.finished = true;
    }
}
