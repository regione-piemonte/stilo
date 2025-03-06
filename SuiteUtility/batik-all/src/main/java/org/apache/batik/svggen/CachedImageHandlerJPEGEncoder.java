// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.io.IOException;
import org.apache.batik.ext.awt.image.spi.ImageWriter;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import java.io.OutputStream;
import java.awt.image.BufferedImage;

public class CachedImageHandlerJPEGEncoder extends DefaultCachedImageHandler
{
    public static final String CACHED_JPEG_PREFIX = "jpegImage";
    public static final String CACHED_JPEG_SUFFIX = ".jpg";
    protected String refPrefix;
    
    public CachedImageHandlerJPEGEncoder(final String s, final String str) throws SVGGraphics2DIOException {
        this.refPrefix = "";
        this.refPrefix = str + "/";
        this.setImageCacher(new ImageCacher.External(s, "jpegImage", ".jpg"));
    }
    
    public void encodeImage(final BufferedImage bufferedImage, final OutputStream outputStream) throws IOException {
        final ImageWriter writer = ImageWriterRegistry.getInstance().getWriterFor("image/jpeg");
        final ImageWriterParams imageWriterParams = new ImageWriterParams();
        imageWriterParams.setJPEGQuality(1.0f, false);
        writer.writeImage(bufferedImage, outputStream, imageWriterParams);
    }
    
    public int getBufferedImageType() {
        return 1;
    }
    
    public String getRefPrefix() {
        return this.refPrefix;
    }
}
