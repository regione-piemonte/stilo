// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.awt.image.RenderedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.InputStream;

public abstract class ImageDecoderImpl implements ImageDecoder
{
    protected SeekableStream input;
    protected ImageDecodeParam param;
    
    public ImageDecoderImpl(final SeekableStream input, final ImageDecodeParam param) {
        this.input = input;
        this.param = param;
    }
    
    public ImageDecoderImpl(final InputStream inputStream, final ImageDecodeParam param) {
        this.input = new ForwardSeekableStream(inputStream);
        this.param = param;
    }
    
    public ImageDecodeParam getParam() {
        return this.param;
    }
    
    public void setParam(final ImageDecodeParam param) {
        this.param = param;
    }
    
    public SeekableStream getInputStream() {
        return this.input;
    }
    
    public int getNumPages() throws IOException {
        return 1;
    }
    
    public Raster decodeAsRaster() throws IOException {
        return this.decodeAsRaster(0);
    }
    
    public Raster decodeAsRaster(final int n) throws IOException {
        return this.decodeAsRenderedImage(n).getData();
    }
    
    public RenderedImage decodeAsRenderedImage() throws IOException {
        return this.decodeAsRenderedImage(0);
    }
    
    public abstract RenderedImage decodeAsRenderedImage(final int p0) throws IOException;
}
