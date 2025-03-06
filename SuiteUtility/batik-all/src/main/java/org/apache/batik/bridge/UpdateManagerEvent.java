// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.List;
import java.awt.image.BufferedImage;
import java.util.EventObject;

public class UpdateManagerEvent extends EventObject
{
    protected BufferedImage image;
    protected List dirtyAreas;
    protected boolean clearPaintingTransform;
    
    public UpdateManagerEvent(final Object source, final BufferedImage image, final List dirtyAreas) {
        super(source);
        this.image = image;
        this.dirtyAreas = dirtyAreas;
        this.clearPaintingTransform = false;
    }
    
    public UpdateManagerEvent(final Object source, final BufferedImage image, final List dirtyAreas, final boolean clearPaintingTransform) {
        super(source);
        this.image = image;
        this.dirtyAreas = dirtyAreas;
        this.clearPaintingTransform = clearPaintingTransform;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public List getDirtyAreas() {
        return this.dirtyAreas;
    }
    
    public boolean getClearPaintingTransform() {
        return this.clearPaintingTransform;
    }
}
