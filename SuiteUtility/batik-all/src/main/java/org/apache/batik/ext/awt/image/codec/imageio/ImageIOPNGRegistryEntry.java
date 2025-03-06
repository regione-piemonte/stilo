// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

public class ImageIOPNGRegistryEntry extends AbstractImageIORegistryEntry
{
    static final byte[] signature;
    
    public ImageIOPNGRegistryEntry() {
        super("PNG", "png", "image/png", 0, ImageIOPNGRegistryEntry.signature);
    }
    
    static {
        signature = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
    }
}
