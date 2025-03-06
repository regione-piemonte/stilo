// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.io.IOException;
import java.awt.image.RenderedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.OutputStream;

public abstract class ImageEncoderImpl implements ImageEncoder
{
    protected OutputStream output;
    protected ImageEncodeParam param;
    
    public ImageEncoderImpl(final OutputStream output, final ImageEncodeParam param) {
        this.output = output;
        this.param = param;
    }
    
    public ImageEncodeParam getParam() {
        return this.param;
    }
    
    public void setParam(final ImageEncodeParam param) {
        this.param = param;
    }
    
    public OutputStream getOutputStream() {
        return this.output;
    }
    
    public void encode(final Raster raster, final ColorModel colorModel) throws IOException {
        this.encode(new SingleTileRenderedImage(raster, colorModel));
    }
    
    public abstract void encode(final RenderedImage p0) throws IOException;
}
