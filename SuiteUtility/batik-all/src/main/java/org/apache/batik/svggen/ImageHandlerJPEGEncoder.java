// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.Dimension;
import org.apache.batik.ext.awt.image.spi.ImageWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import java.io.FileOutputStream;
import java.io.File;
import java.awt.image.BufferedImage;

public class ImageHandlerJPEGEncoder extends AbstractImageHandlerEncoder
{
    public ImageHandlerJPEGEncoder(final String s, final String s2) throws SVGGraphics2DIOException {
        super(s, s2);
    }
    
    public final String getSuffix() {
        return ".jpg";
    }
    
    public final String getPrefix() {
        return "jpegImage";
    }
    
    public void encodeImage(final BufferedImage bufferedImage, final File file) throws SVGGraphics2DIOException {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                final ImageWriter writer = ImageWriterRegistry.getInstance().getWriterFor("image/jpeg");
                final ImageWriterParams imageWriterParams = new ImageWriterParams();
                imageWriterParams.setJPEGQuality(1.0f, false);
                writer.writeImage(bufferedImage, fileOutputStream, imageWriterParams);
            }
            finally {
                fileOutputStream.close();
            }
        }
        catch (IOException ex) {
            throw new SVGGraphics2DIOException("could not write image File " + file.getName());
        }
    }
    
    public BufferedImage buildBufferedImage(final Dimension dimension) {
        return new BufferedImage(dimension.width, dimension.height, 1);
    }
}
