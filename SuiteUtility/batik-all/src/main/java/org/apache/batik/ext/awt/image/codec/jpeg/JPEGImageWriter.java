// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.jpeg;

import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.batik.ext.awt.image.spi.ImageWriterParams;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriter;

public class JPEGImageWriter implements ImageWriter
{
    public void writeImage(final RenderedImage renderedImage, final OutputStream outputStream) throws IOException {
        this.writeImage(renderedImage, outputStream, null);
    }
    
    public void writeImage(final RenderedImage renderedImage, final OutputStream outputStream, final ImageWriterParams imageWriterParams) throws IOException {
        BufferedImage linearBufferedImage;
        if (renderedImage instanceof BufferedImage) {
            linearBufferedImage = (BufferedImage)renderedImage;
        }
        else {
            linearBufferedImage = GraphicsUtil.makeLinearBufferedImage(renderedImage.getWidth(), renderedImage.getHeight(), false);
        }
        final JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(outputStream);
        if (imageWriterParams != null) {
            final JPEGEncodeParam defaultJPEGEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(linearBufferedImage);
            if (imageWriterParams.getJPEGQuality() != null) {
                defaultJPEGEncodeParam.setQuality((float)imageWriterParams.getJPEGQuality(), (boolean)imageWriterParams.getJPEGForceBaseline());
            }
            jpegEncoder.encode(linearBufferedImage, defaultJPEGEncodeParam);
        }
        else {
            jpegEncoder.encode(linearBufferedImage);
        }
    }
    
    public String getMIMEType() {
        return "image/jpeg";
    }
}
