// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import org.apache.batik.ext.awt.image.spi.MagicNumberRegistryEntry;

public class ImageIOTIFFRegistryEntry extends AbstractImageIORegistryEntry
{
    static final byte[] sig1;
    static final byte[] sig2;
    static MagicNumber[] magicNumbers;
    static final String[] exts;
    static final String[] mimeTypes;
    
    public ImageIOTIFFRegistryEntry() {
        super("TIFF", ImageIOTIFFRegistryEntry.exts, ImageIOTIFFRegistryEntry.mimeTypes, ImageIOTIFFRegistryEntry.magicNumbers);
    }
    
    static {
        sig1 = new byte[] { 73, 73, 42, 0 };
        sig2 = new byte[] { 77, 77, 0, 42 };
        ImageIOTIFFRegistryEntry.magicNumbers = new MagicNumber[] { new MagicNumber(0, ImageIOTIFFRegistryEntry.sig1), new MagicNumber(0, ImageIOTIFFRegistryEntry.sig2) };
        exts = new String[] { "tiff", "tif" };
        mimeTypes = new String[] { "image/tiff", "image/tif" };
    }
}
