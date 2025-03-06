// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.Dimension;
import org.apache.batik.ext.awt.image.spi.ImageWriterRegistry;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.batik.util.Base64EncoderStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.renderable.RenderableImage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.ImageObserver;
import org.w3c.dom.Element;
import java.awt.Image;

public class ImageHandlerBase64Encoder extends DefaultImageHandler
{
    public void handleHREF(final Image image, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        if (image == null) {
            throw new SVGGraphics2DRuntimeException("image should not be null");
        }
        final int width = image.getWidth(null);
        final int height = image.getHeight(null);
        if (width == 0 || height == 0) {
            this.handleEmptyImage(element);
        }
        else if (image instanceof RenderedImage) {
            this.handleHREF((RenderedImage)image, element, svgGeneratorContext);
        }
        else {
            final BufferedImage bufferedImage = new BufferedImage(width, height, 2);
            final Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            this.handleHREF((RenderedImage)bufferedImage, element, svgGeneratorContext);
        }
    }
    
    public void handleHREF(final RenderableImage renderableImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        if (renderableImage == null) {
            throw new SVGGraphics2DRuntimeException("image should not be null");
        }
        final RenderedImage defaultRendering = renderableImage.createDefaultRendering();
        if (defaultRendering == null) {
            this.handleEmptyImage(element);
        }
        else {
            this.handleHREF(defaultRendering, element, svgGeneratorContext);
        }
    }
    
    protected void handleEmptyImage(final Element element) {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "data:image/png;base64,");
        element.setAttributeNS(null, "width", "0");
        element.setAttributeNS(null, "height", "0");
    }
    
    public void handleHREF(final RenderedImage renderedImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final Base64EncoderStream base64EncoderStream = new Base64EncoderStream(byteArrayOutputStream);
        try {
            this.encodeImage(renderedImage, base64EncoderStream);
            base64EncoderStream.close();
        }
        catch (IOException ex) {
            throw new SVGGraphics2DIOException("unexpected exception", ex);
        }
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "data:image/png;base64," + byteArrayOutputStream.toString());
    }
    
    public void encodeImage(final RenderedImage renderedImage, final OutputStream outputStream) throws SVGGraphics2DIOException {
        try {
            ImageWriterRegistry.getInstance().getWriterFor("image/png").writeImage(renderedImage, outputStream);
        }
        catch (IOException ex) {
            throw new SVGGraphics2DIOException("unexpected exception");
        }
    }
    
    public BufferedImage buildBufferedImage(final Dimension dimension) {
        return new BufferedImage(dimension.width, dimension.height, 2);
    }
}
