// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.image.BufferedImage;
import java.util.EventObject;

public class GVTTreeRendererEvent extends EventObject
{
    protected BufferedImage image;
    
    public GVTTreeRendererEvent(final Object source, final BufferedImage image) {
        super(source);
        this.image = image;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
}
