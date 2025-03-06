// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.awt.Dimension;
import java.awt.image.renderable.RenderableImage;
import java.awt.image.RenderedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;
import org.w3c.dom.Element;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.awt.geom.AffineTransform;

public abstract class DefaultCachedImageHandler implements CachedImageHandler, SVGSyntax, ErrorConstants
{
    static final String XLINK_NAMESPACE_URI = "http://www.w3.org/1999/xlink";
    static final AffineTransform IDENTITY;
    private static Method createGraphics;
    private static boolean initDone;
    private static final Class[] paramc;
    private static Object[] paramo;
    protected ImageCacher imageCacher;
    
    public ImageCacher getImageCacher() {
        return this.imageCacher;
    }
    
    void setImageCacher(final ImageCacher imageCacher) {
        if (imageCacher == null) {
            throw new IllegalArgumentException();
        }
        DOMTreeManager domTreeManager = null;
        if (this.imageCacher != null) {
            domTreeManager = this.imageCacher.getDOMTreeManager();
        }
        this.imageCacher = imageCacher;
        if (domTreeManager != null) {
            this.imageCacher.setDOMTreeManager(domTreeManager);
        }
    }
    
    public void setDOMTreeManager(final DOMTreeManager domTreeManager) {
        this.imageCacher.setDOMTreeManager(domTreeManager);
    }
    
    private static Graphics2D createGraphics(final BufferedImage bufferedImage) {
        if (!DefaultCachedImageHandler.initDone) {
            try {
                DefaultCachedImageHandler.createGraphics = Class.forName("org.apache.batik.ext.awt.image.GraphicsUtil").getMethod("createGraphics", (Class<?>[])DefaultCachedImageHandler.paramc);
                DefaultCachedImageHandler.paramo = new Object[1];
            }
            catch (Throwable t) {}
            finally {
                DefaultCachedImageHandler.initDone = true;
            }
        }
        if (DefaultCachedImageHandler.createGraphics == null) {
            return bufferedImage.createGraphics();
        }
        DefaultCachedImageHandler.paramo[0] = bufferedImage;
        Graphics2D graphics2D = null;
        try {
            graphics2D = (Graphics2D)DefaultCachedImageHandler.createGraphics.invoke(null, DefaultCachedImageHandler.paramo);
        }
        catch (Exception ex) {}
        return graphics2D;
    }
    
    public Element createElement(final SVGGeneratorContext svgGeneratorContext) {
        return svgGeneratorContext.getDOMFactory().createElementNS("http://www.w3.org/2000/svg", "image");
    }
    
    public AffineTransform handleImage(final Image image, final Element element, final int n, final int n2, final int n3, final int n4, final SVGGeneratorContext svgGeneratorContext) {
        final int width = image.getWidth(null);
        final int height = image.getHeight(null);
        AffineTransform handleTransform = null;
        if (width == 0 || height == 0 || n3 == 0 || n4 == 0) {
            this.handleEmptyImage(element);
        }
        else {
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
            handleTransform = this.handleTransform(element, n, n2, width, height, n3, n4, svgGeneratorContext);
        }
        return handleTransform;
    }
    
    public AffineTransform handleImage(final RenderedImage renderedImage, final Element element, final int n, final int n2, final int n3, final int n4, final SVGGeneratorContext svgGeneratorContext) {
        final int width = renderedImage.getWidth();
        final int height = renderedImage.getHeight();
        AffineTransform handleTransform = null;
        if (width == 0 || height == 0 || n3 == 0 || n4 == 0) {
            this.handleEmptyImage(element);
        }
        else {
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
            handleTransform = this.handleTransform(element, n, n2, width, height, n3, n4, svgGeneratorContext);
        }
        return handleTransform;
    }
    
    public AffineTransform handleImage(final RenderableImage renderableImage, final Element element, final double n, final double n2, final double n3, final double n4, final SVGGeneratorContext svgGeneratorContext) {
        final double n5 = renderableImage.getWidth();
        final double n6 = renderableImage.getHeight();
        AffineTransform handleTransform = null;
        if (n5 == 0.0 || n6 == 0.0 || n3 == 0.0 || n4 == 0.0) {
            this.handleEmptyImage(element);
        }
        else {
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
            handleTransform = this.handleTransform(element, n, n2, n5, n6, n3, n4, svgGeneratorContext);
        }
        return handleTransform;
    }
    
    protected AffineTransform handleTransform(final Element element, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final SVGGeneratorContext svgGeneratorContext) {
        element.setAttributeNS(null, "x", svgGeneratorContext.doubleString(n));
        element.setAttributeNS(null, "y", svgGeneratorContext.doubleString(n2));
        element.setAttributeNS(null, "width", svgGeneratorContext.doubleString(n5));
        element.setAttributeNS(null, "height", svgGeneratorContext.doubleString(n6));
        return null;
    }
    
    protected void handleEmptyImage(final Element element) {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "");
        element.setAttributeNS(null, "width", "0");
        element.setAttributeNS(null, "height", "0");
    }
    
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
            final BufferedImage buildBufferedImage = this.buildBufferedImage(new Dimension(width, height));
            final Graphics2D graphics = createGraphics(buildBufferedImage);
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            this.handleHREF((RenderedImage)buildBufferedImage, element, svgGeneratorContext);
        }
    }
    
    public BufferedImage buildBufferedImage(final Dimension dimension) {
        return new BufferedImage(dimension.width, dimension.height, this.getBufferedImageType());
    }
    
    protected void handleHREF(final RenderedImage renderedImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        BufferedImage buildBufferedImage;
        if (renderedImage instanceof BufferedImage && ((BufferedImage)renderedImage).getType() == this.getBufferedImageType()) {
            buildBufferedImage = (BufferedImage)renderedImage;
        }
        else {
            buildBufferedImage = this.buildBufferedImage(new Dimension(renderedImage.getWidth(), renderedImage.getHeight()));
            final Graphics2D graphics = createGraphics(buildBufferedImage);
            graphics.drawRenderedImage(renderedImage, DefaultCachedImageHandler.IDENTITY);
            graphics.dispose();
        }
        this.cacheBufferedImage(element, buildBufferedImage, svgGeneratorContext);
    }
    
    protected void handleHREF(final RenderableImage renderableImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        final BufferedImage buildBufferedImage = this.buildBufferedImage(new Dimension((int)Math.ceil(renderableImage.getWidth()), (int)Math.ceil(renderableImage.getHeight())));
        final Graphics2D graphics = createGraphics(buildBufferedImage);
        graphics.drawRenderableImage(renderableImage, DefaultCachedImageHandler.IDENTITY);
        graphics.dispose();
        this.handleHREF((RenderedImage)buildBufferedImage, element, svgGeneratorContext);
    }
    
    protected void cacheBufferedImage(final Element element, final BufferedImage bufferedImage, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        if (svgGeneratorContext == null) {
            throw new SVGGraphics2DRuntimeException("generatorContext should not be null");
        }
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            this.encodeImage(bufferedImage, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        }
        catch (IOException ex) {
            throw new SVGGraphics2DIOException("unexpected exception", ex);
        }
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", this.getRefPrefix() + this.imageCacher.lookup(byteArrayOutputStream, bufferedImage.getWidth(), bufferedImage.getHeight(), svgGeneratorContext));
    }
    
    public abstract String getRefPrefix();
    
    public abstract void encodeImage(final BufferedImage p0, final OutputStream p1) throws IOException;
    
    public abstract int getBufferedImageType();
    
    static {
        IDENTITY = new AffineTransform();
        DefaultCachedImageHandler.createGraphics = null;
        DefaultCachedImageHandler.initDone = false;
        paramc = new Class[] { BufferedImage.class };
        DefaultCachedImageHandler.paramo = null;
    }
}
