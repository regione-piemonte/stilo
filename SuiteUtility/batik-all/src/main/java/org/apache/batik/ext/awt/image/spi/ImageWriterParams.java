// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.spi;

public class ImageWriterParams
{
    private Integer resolution;
    private Float jpegQuality;
    private Boolean jpegForceBaseline;
    private String compressionMethod;
    
    public Integer getResolution() {
        return this.resolution;
    }
    
    public Float getJPEGQuality() {
        return this.jpegQuality;
    }
    
    public Boolean getJPEGForceBaseline() {
        return this.jpegForceBaseline;
    }
    
    public String getCompressionMethod() {
        return this.compressionMethod;
    }
    
    public void setResolution(final int value) {
        this.resolution = new Integer(value);
    }
    
    public void setJPEGQuality(final float value, final boolean b) {
        this.jpegQuality = new Float(value);
        this.jpegForceBaseline = (b ? Boolean.TRUE : Boolean.FALSE);
    }
    
    public void setCompressionMethod(final String compressionMethod) {
        this.compressionMethod = compressionMethod;
    }
}
