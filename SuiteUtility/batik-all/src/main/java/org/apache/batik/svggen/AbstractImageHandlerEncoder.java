// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.image.renderable.RenderableImage;
import java.awt.image.RenderedImage;
import java.awt.Dimension;
import java.awt.image.ImageObserver;
import org.w3c.dom.Element;
import java.awt.Image;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.awt.geom.AffineTransform;

public abstract class AbstractImageHandlerEncoder extends DefaultImageHandler
{
    private static final AffineTransform IDENTITY;
    private String imageDir;
    private String urlRoot;
    private static Method createGraphics;
    private static boolean initDone;
    private static final Class[] paramc;
    private static Object[] paramo;
    
    private static Graphics2D createGraphics(final BufferedImage bufferedImage) {
        if (!AbstractImageHandlerEncoder.initDone) {
            try {
                AbstractImageHandlerEncoder.createGraphics = Class.forName("org.apache.batik.ext.awt.image.GraphicsUtil").getMethod("createGraphics", (Class<?>[])AbstractImageHandlerEncoder.paramc);
                AbstractImageHandlerEncoder.paramo = new Object[1];
            }
            catch (ThreadDeath threadDeath) {
                throw threadDeath;
            }
            catch (Throwable t) {}
            finally {
                AbstractImageHandlerEncoder.initDone = true;
            }
        }
        if (AbstractImageHandlerEncoder.createGraphics == null) {
            return bufferedImage.createGraphics();
        }
        AbstractImageHandlerEncoder.paramo[0] = bufferedImage;
        Graphics2D graphics2D = null;
        try {
            graphics2D = (Graphics2D)AbstractImageHandlerEncoder.createGraphics.invoke(null, AbstractImageHandlerEncoder.paramo);
        }
        catch (Exception ex) {}
        return graphics2D;
    }
    
    public AbstractImageHandlerEncoder(final String s, final String urlRoot) throws SVGGraphics2DIOException {
        this.imageDir = "";
        this.urlRoot = "";
        if (s == null) {
            throw new SVGGraphics2DRuntimeException("imageDir should not be null");
        }
        final File file = new File(s);
        if (!file.exists()) {
            throw new SVGGraphics2DRuntimeException("imageDir does not exist");
        }
        this.imageDir = s;
        if (urlRoot != null) {
            this.urlRoot = urlRoot;
        }
        else {
            try {
                this.urlRoot = file.toURL().toString();
            }
            catch (MalformedURLException ex) {
                throw new SVGGraphics2DIOException("cannot convert imageDir to a URL value : " + ex.getMessage(), ex);
            }
        }
    }
    
    protected void handleHREF(final Image image, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        final BufferedImage buildBufferedImage = this.buildBufferedImage(new Dimension(image.getWidth(null), image.getHeight(null)));
        final Graphics2D graphics = createGraphics(buildBufferedImage);
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        this.saveBufferedImageToFile(element, buildBufferedImage, svgGeneratorContext);
    }
    
    protected void handleHREF(final RenderedImage renderedImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        final BufferedImage buildBufferedImage = this.buildBufferedImage(new Dimension(renderedImage.getWidth(), renderedImage.getHeight()));
        final Graphics2D graphics = createGraphics(buildBufferedImage);
        graphics.drawRenderedImage(renderedImage, AbstractImageHandlerEncoder.IDENTITY);
        graphics.dispose();
        this.saveBufferedImageToFile(element, buildBufferedImage, svgGeneratorContext);
    }
    
    protected void handleHREF(final RenderableImage renderableImage, final Element element, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        final BufferedImage buildBufferedImage = this.buildBufferedImage(new Dimension((int)Math.ceil(renderableImage.getWidth()), (int)Math.ceil(renderableImage.getHeight())));
        final Graphics2D graphics = createGraphics(buildBufferedImage);
        graphics.drawRenderableImage(renderableImage, AbstractImageHandlerEncoder.IDENTITY);
        graphics.dispose();
        this.saveBufferedImageToFile(element, buildBufferedImage, svgGeneratorContext);
    }
    
    private void saveBufferedImageToFile(final Element element, final BufferedImage bufferedImage, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        if (svgGeneratorContext == null) {
            throw new SVGGraphics2DRuntimeException("generatorContext should not be null");
        }
        File file;
        for (file = null; file == null; file = null) {
            file = new File(this.imageDir, svgGeneratorContext.idGenerator.generateID(this.getPrefix()) + this.getSuffix());
            if (file.exists()) {}
        }
        this.encodeImage(bufferedImage, file);
        element.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", this.urlRoot + "/" + file.getName());
    }
    
    public abstract String getSuffix();
    
    public abstract String getPrefix();
    
    public abstract void encodeImage(final BufferedImage p0, final File p1) throws SVGGraphics2DIOException;
    
    public abstract BufferedImage buildBufferedImage(final Dimension p0);
    
    static {
        IDENTITY = new AffineTransform();
        AbstractImageHandlerEncoder.createGraphics = null;
        AbstractImageHandlerEncoder.initDone = false;
        paramc = new Class[] { BufferedImage.class };
        AbstractImageHandlerEncoder.paramo = null;
    }
}
