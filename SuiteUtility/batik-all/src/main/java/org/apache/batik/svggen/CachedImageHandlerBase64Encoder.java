// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.io.IOException;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import org.apache.batik.util.Base64EncoderStream;
import java.io.OutputStream;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import org.w3c.dom.Element;

public class CachedImageHandlerBase64Encoder extends DefaultCachedImageHandler
{
    public CachedImageHandlerBase64Encoder() {
        this.setImageCacher(new ImageCacher.Embedded());
    }
    
    public Element createElement(final SVGGeneratorContext svgGeneratorContext) {
        return svgGeneratorContext.getDOMFactory().createElementNS("http://www.w3.org/2000/svg", "use");
    }
    
    public String getRefPrefix() {
        return "";
    }
    
    protected AffineTransform handleTransform(final Element element, final double tx, final double ty, final double n, final double n2, final double n3, final double n4, final SVGGeneratorContext svgGeneratorContext) {
        final AffineTransform affineTransform = new AffineTransform();
        final double sx = n3 / n;
        final double sy = n4 / n2;
        affineTransform.translate(tx, ty);
        if (sx != 1.0 || sy != 1.0) {
            affineTransform.scale(sx, sy);
        }
        if (!affineTransform.isIdentity()) {
            return affineTransform;
        }
        return null;
    }
    
    public void encodeImage(final BufferedImage bufferedImage, final OutputStream outputStream) throws IOException {
        final Base64EncoderStream base64EncoderStream = new Base64EncoderStream(outputStream);
        ImageWriterRegistry.getInstance().getWriterFor("image/png").writeImage(bufferedImage, base64EncoderStream);
        base64EncoderStream.close();
    }
    
    public int getBufferedImageType() {
        return 2;
    }
}
