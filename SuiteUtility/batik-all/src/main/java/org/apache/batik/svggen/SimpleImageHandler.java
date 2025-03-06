// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.image.renderable.RenderableImage;
import java.awt.image.RenderedImage;
import java.awt.image.ImageObserver;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import org.w3c.dom.Element;

public class SimpleImageHandler implements GenericImageHandler, SVGSyntax, ErrorConstants
{
    static final String XLINK_NAMESPACE_URI = "http://www.w3.org/1999/xlink";
    protected ImageHandler imageHandler;
    
    public SimpleImageHandler(final ImageHandler imageHandler) {
        if (imageHandler == null) {
            throw new IllegalArgumentException();
        }
        this.imageHandler = imageHandler;
    }
    
    public void setDOMTreeManager(final DOMTreeManager domTreeManager) {
    }
    
    public Element createElement(final SVGGeneratorContext svgGeneratorContext) {
        return svgGeneratorContext.getDOMFactory().createElementNS("http://www.w3.org/2000/svg", "image");
    }
    
    public AffineTransform handleImage(final Image image, final Element element, final int n, final int n2, final int n3, final int n4, final SVGGeneratorContext svgGeneratorContext) {
        final int width = image.getWidth(null);
        final int height = image.getHeight(null);
        if (width == 0 || height == 0 || n3 == 0 || n4 == 0) {
            this.handleEmptyImage(element);
        }
        else {
            this.imageHandler.handleImage(image, element, svgGeneratorContext);
            this.setImageAttributes(element, n, n2, n3, n4, svgGeneratorContext);
        }
        return null;
    }
    
    public AffineTransform handleImage(final RenderedImage renderedImage, final Element element, final int n, final int n2, final int n3, final int n4, final SVGGeneratorContext svgGeneratorContext) {
        final int width = renderedImage.getWidth();
        final int height = renderedImage.getHeight();
        if (width == 0 || height == 0 || n3 == 0 || n4 == 0) {
            this.handleEmptyImage(element);
        }
        else {
            this.imageHandler.handleImage(renderedImage, element, svgGeneratorContext);
            this.setImageAttributes(element, n, n2, n3, n4, svgGeneratorContext);
        }
        return null;
    }
    
    public AffineTransform handleImage(final RenderableImage renderableImage, final Element element, final double n, final double n2, final double n3, final double n4, final SVGGeneratorContext svgGeneratorContext) {
        final double n5 = renderableImage.getWidth();
        final double n6 = renderableImage.getHeight();
        if (n5 == 0.0 || n6 == 0.0 || n3 == 0.0 || n4 == 0.0) {
            this.handleEmptyImage(element);
        }
        else {
            this.imageHandler.handleImage(renderableImage, element, svgGeneratorContext);
            this.setImageAttributes(element, n, n2, n3, n4, svgGeneratorContext);
        }
        return null;
    }
    
    protected void setImageAttributes(final Element element, final double n, final double n2, final double n3, final double n4, final SVGGeneratorContext svgGeneratorContext) {
        element.setAttributeNS(null, "x", svgGeneratorContext.doubleString(n));
        element.setAttributeNS(null, "y", svgGeneratorContext.doubleString(n2));
        element.setAttributeNS(null, "width", svgGeneratorContext.doubleString(n3));
        element.setAttributeNS(null, "height", svgGeneratorContext.doubleString(n4));
        element.setAttributeNS(null, "preserveAspectRatio", "none");
    }
    
    protected void handleEmptyImage(final Element element) {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "");
        element.setAttributeNS(null, "width", "0");
        element.setAttributeNS(null, "height", "0");
    }
}
