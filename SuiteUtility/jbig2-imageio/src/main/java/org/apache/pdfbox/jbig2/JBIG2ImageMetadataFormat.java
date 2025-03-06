// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataFormatImpl;

public class JBIG2ImageMetadataFormat extends IIOMetadataFormatImpl
{
    private static IIOMetadataFormat instance;
    
    private JBIG2ImageMetadataFormat() {
        super("jbig2", 2);
        this.addElement("ImageDescriptor", "jbig2", 0);
        this.addAttribute("ImageDescriptor", "imageWidth", 2, true, null, "1", "65535", true, true);
        this.addAttribute("ImageDescriptor", "imageHeight", 2, true, null, "1", "65535", true, true);
        this.addAttribute("ImageDescriptor", "Xdensity", 3, true, null, "1", "65535", true, true);
        this.addAttribute("ImageDescriptor", "Ydensity", 3, true, null, "1", "65535", true, true);
    }
    
    @Override
    public boolean canNodeAppear(final String s, final ImageTypeSpecifier imageTypeSpecifier) {
        return true;
    }
    
    public static synchronized IIOMetadataFormat getInstance() {
        if (JBIG2ImageMetadataFormat.instance == null) {
            JBIG2ImageMetadataFormat.instance = new JBIG2ImageMetadataFormat();
        }
        return JBIG2ImageMetadataFormat.instance;
    }
    
    static {
        JBIG2ImageMetadataFormat.instance = null;
    }
}
