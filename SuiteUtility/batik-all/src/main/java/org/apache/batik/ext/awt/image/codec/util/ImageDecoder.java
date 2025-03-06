// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.awt.image.RenderedImage;
import java.awt.image.Raster;
import java.io.IOException;

public interface ImageDecoder
{
    ImageDecodeParam getParam();
    
    void setParam(final ImageDecodeParam p0);
    
    SeekableStream getInputStream();
    
    int getNumPages() throws IOException;
    
    Raster decodeAsRaster() throws IOException;
    
    Raster decodeAsRaster(final int p0) throws IOException;
    
    RenderedImage decodeAsRenderedImage() throws IOException;
    
    RenderedImage decodeAsRenderedImage(final int p0) throws IOException;
}
