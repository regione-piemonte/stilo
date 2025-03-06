// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.io.IOException;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriter;

public class PNGImageWriter implements ImageWriter
{
    public void writeImage(final RenderedImage renderedImage, final OutputStream outputStream) throws IOException {
        this.writeImage(renderedImage, outputStream, null);
    }
    
    public void writeImage(final RenderedImage renderedImage, final OutputStream outputStream, final ImageWriterParams imageWriterParams) throws IOException {
        new PNGImageEncoder(outputStream, null).encode(renderedImage);
    }
    
    public String getMIMEType() {
        return "image/png";
    }
}
