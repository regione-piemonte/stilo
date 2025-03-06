// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

public enum ImageFormats implements ImageFormat
{
    UNKNOWN, 
    BMP, 
    DCX, 
    GIF, 
    ICNS, 
    ICO, 
    JBIG2, 
    JPEG, 
    PAM, 
    PSD, 
    PBM, 
    PGM, 
    PNM, 
    PPM, 
    PCX, 
    PNG, 
    RGBE, 
    TGA, 
    TIFF, 
    WBMP, 
    XBM, 
    XPM;
    
    @Override
    public String getName() {
        return this.name();
    }
    
    @Override
    public String getExtension() {
        return this.name();
    }
}
