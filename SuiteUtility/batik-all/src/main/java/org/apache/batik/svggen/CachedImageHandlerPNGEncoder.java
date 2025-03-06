// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.io.IOException;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import java.io.OutputStream;
import java.awt.image.BufferedImage;

public class CachedImageHandlerPNGEncoder extends DefaultCachedImageHandler
{
    public static final String CACHED_PNG_PREFIX = "pngImage";
    public static final String CACHED_PNG_SUFFIX = ".png";
    protected String refPrefix;
    
    public CachedImageHandlerPNGEncoder(final String s, final String str) throws SVGGraphics2DIOException {
        this.refPrefix = "";
        this.refPrefix = str + "/";
        this.setImageCacher(new ImageCacher.External(s, "pngImage", ".png"));
    }
    
    public void encodeImage(final BufferedImage bufferedImage, final OutputStream outputStream) throws IOException {
        ImageWriterRegistry.getInstance().getWriterFor("image/png").writeImage(bufferedImage, outputStream);
    }
    
    public int getBufferedImageType() {
        return 2;
    }
    
    public String getRefPrefix() {
        return this.refPrefix;
    }
}
