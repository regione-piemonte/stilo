// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

import org.apache.batik.ext.awt.image.codec.util.ImageDecodeParam;

public class TIFFDecodeParam implements ImageDecodeParam
{
    private boolean decodePaletteAsShorts;
    private Long ifdOffset;
    private boolean convertJPEGYCbCrToRGB;
    
    public TIFFDecodeParam() {
        this.decodePaletteAsShorts = false;
        this.ifdOffset = null;
        this.convertJPEGYCbCrToRGB = true;
    }
    
    public void setDecodePaletteAsShorts(final boolean decodePaletteAsShorts) {
        this.decodePaletteAsShorts = decodePaletteAsShorts;
    }
    
    public boolean getDecodePaletteAsShorts() {
        return this.decodePaletteAsShorts;
    }
    
    public byte decode16BitsTo8Bits(final int n) {
        return (byte)(n >> 8 & 0xFFFF);
    }
    
    public byte decodeSigned16BitsTo8Bits(final short n) {
        return (byte)(n - 32768 >> 8);
    }
    
    public void setIFDOffset(final long value) {
        this.ifdOffset = new Long(value);
    }
    
    public Long getIFDOffset() {
        return this.ifdOffset;
    }
    
    public void setJPEGDecompressYCbCrToRGB(final boolean convertJPEGYCbCrToRGB) {
        this.convertJPEGYCbCrToRGB = convertJPEGYCbCrToRGB;
    }
    
    public boolean getJPEGDecompressYCbCrToRGB() {
        return this.convertJPEGYCbCrToRGB;
    }
}
