// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.OutputStream;

public interface ImageEncoder
{
    ImageEncodeParam getParam();
    
    void setParam(final ImageEncodeParam p0);
    
    OutputStream getOutputStream();
    
    void encode(final Raster p0, final ColorModel p1) throws IOException;
    
    void encode(final RenderedImage p0) throws IOException;
}
