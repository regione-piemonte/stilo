// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.imageio;

import org.apache.batik.ext.awt.image.spi.MagicNumberRegistryEntry;

public class ImageIOJPEGRegistryEntry extends AbstractImageIORegistryEntry
{
    static final byte[] sigJPEG;
    static final String[] exts;
    static final String[] mimeTypes;
    static final MagicNumber[] magicNumbers;
    
    public ImageIOJPEGRegistryEntry() {
        super("JPEG", ImageIOJPEGRegistryEntry.exts, ImageIOJPEGRegistryEntry.mimeTypes, ImageIOJPEGRegistryEntry.magicNumbers);
    }
    
    static {
        sigJPEG = new byte[] { -1, -40, -1 };
        exts = new String[] { "jpeg", "jpg" };
        mimeTypes = new String[] { "image/jpeg", "image/jpg" };
        magicNumbers = new MagicNumber[] { new MagicNumber(0, ImageIOJPEGRegistryEntry.sigJPEG) };
    }
}
