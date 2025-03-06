// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.image.renderable.RenderableImage;
import java.awt.image.RenderedImage;
import java.awt.image.ImageObserver;
import org.w3c.dom.Element;
import java.awt.Image;
import org.apache.batik.util.XMLConstants;

public class DefaultImageHandler implements ImageHandler, ErrorConstants, XMLConstants
{
    public void handleImage(final Image image, final Element element, final SVGGeneratorContext svgGeneratorContext) {
        element.setAttributeNS(null, "width", String.valueOf(image.getWidth(null)));
        element.setAttributeNS(null, "height", String.valueOf(image.getHeight(null)));
        try {
            this.handleHREF(image, element, svgGeneratorContext);
        }
        catch (SVGGraphics2DIOException ex) {
            try {
                svgGeneratorContext.errorHandler.handleError(ex);
            }
            catch (SVGGraphics2DIOException ex2) {
                throw new SVGGraphics2DRuntimeException(ex2);
            }
        }
    }
    
    public void handleImage(final RenderedImage renderedImage, final Element element, final SVGGeneratorContext svgGeneratorContext) {
        element.setAttributeNS(null, "width", String.valueOf(renderedImage.getWidth()));
        element.setAttributeNS(null, "height", String.valueOf(renderedImage.getHeight()));
        try {
            this.handleHREF(renderedImage, element, svgGeneratorContext);
        }
        catch (SVGGraphics2DIOException ex) {
            try {
                svgGeneratorContext.errorHandler.handleError(ex);
            }
            catch (SVGGraphics2DIOException ex2) {
                throw new SVGGraphics2DRuntimeException(ex2);
            }
        }
    }
    
    public void handleImage(final RenderableImage renderableImage, final Element element, final SVGGeneratorContext svgGeneratorContext) {
        element.setAttributeNS(null, "width", String.valueOf(renderableImage.getWidth()));
        element.setAttributeNS(null, "height", String.valueOf(renderableImage.getHeight()));
        try {
            this.handleHREF(renderableImage, element, svgGeneratorContext);
        }
        catch (SVGGraphics2DIOException ex) {
            try {
                svgGeneratorContext.errorHandler.handleError(ex);
            }
            catch (SVGGraphics2DIOException ex2) {
                throw new SVGGraphics2DRuntimeException(ex2);
            }
        }
    }
    
    protected void handleHREF(final Image image, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", image.toString());
    }
    
    protected void handleHREF(final RenderedImage renderedImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", renderedImage.toString());
    }
    
    protected void handleHREF(final RenderableImage renderableImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", renderableImage.toString());
    }
}
