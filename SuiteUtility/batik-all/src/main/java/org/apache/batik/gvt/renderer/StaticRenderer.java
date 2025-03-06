// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.renderer;

import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.rendered.TranslateRed;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.renderable.RenderContext;
import java.util.Iterator;
import java.util.Collection;
import org.apache.batik.ext.awt.image.rendered.TileCacheRed;
import java.awt.Rectangle;
import org.apache.batik.util.HaltingThread;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.geom.RectListManager;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.image.ColorModel;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.util.Hashtable;
import java.util.Map;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.lang.ref.SoftReference;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.GraphicsNode;

public class StaticRenderer implements ImageRenderer
{
    protected GraphicsNode rootGN;
    protected Filter rootFilter;
    protected CachableRed rootCR;
    protected SoftReference lastCR;
    protected SoftReference lastCache;
    protected boolean isDoubleBuffered;
    protected WritableRaster currentBaseRaster;
    protected WritableRaster currentRaster;
    protected BufferedImage currentOffScreen;
    protected WritableRaster workingBaseRaster;
    protected WritableRaster workingRaster;
    protected BufferedImage workingOffScreen;
    protected int offScreenWidth;
    protected int offScreenHeight;
    protected RenderingHints renderingHints;
    protected AffineTransform usr2dev;
    protected static RenderingHints defaultRenderingHints;
    
    public StaticRenderer(final RenderingHints hints, final AffineTransform tx) {
        this.isDoubleBuffered = false;
        (this.renderingHints = new RenderingHints(null)).add(hints);
        this.usr2dev = new AffineTransform(tx);
    }
    
    public StaticRenderer() {
        this.isDoubleBuffered = false;
        (this.renderingHints = new RenderingHints(null)).add(StaticRenderer.defaultRenderingHints);
        this.usr2dev = new AffineTransform();
    }
    
    public void dispose() {
        this.rootGN = null;
        this.rootFilter = null;
        this.rootCR = null;
        this.workingOffScreen = null;
        this.workingBaseRaster = null;
        this.workingRaster = null;
        this.currentOffScreen = null;
        this.currentBaseRaster = null;
        this.currentRaster = null;
        this.renderingHints = null;
        this.lastCache = null;
        this.lastCR = null;
    }
    
    public void setTree(final GraphicsNode rootGN) {
        this.rootGN = rootGN;
        this.rootFilter = null;
        this.rootCR = null;
        this.workingOffScreen = null;
        this.workingRaster = null;
        this.currentOffScreen = null;
        this.currentRaster = null;
    }
    
    public GraphicsNode getTree() {
        return this.rootGN;
    }
    
    public void setRenderingHints(final RenderingHints hints) {
        (this.renderingHints = new RenderingHints(null)).add(hints);
        this.rootFilter = null;
        this.rootCR = null;
        this.workingOffScreen = null;
        this.workingRaster = null;
        this.currentOffScreen = null;
        this.currentRaster = null;
    }
    
    public RenderingHints getRenderingHints() {
        return this.renderingHints;
    }
    
    public void setTransform(final AffineTransform affineTransform) {
        if (this.usr2dev.equals(affineTransform)) {
            return;
        }
        if (affineTransform == null) {
            this.usr2dev = new AffineTransform();
        }
        else {
            this.usr2dev = new AffineTransform(affineTransform);
        }
        this.rootCR = null;
    }
    
    public AffineTransform getTransform() {
        return this.usr2dev;
    }
    
    public boolean isDoubleBuffered() {
        return this.isDoubleBuffered;
    }
    
    public void setDoubleBuffered(final boolean isDoubleBuffered) {
        if (this.isDoubleBuffered == isDoubleBuffered) {
            return;
        }
        this.isDoubleBuffered = isDoubleBuffered;
        if (isDoubleBuffered) {
            this.currentOffScreen = null;
            this.currentBaseRaster = null;
            this.currentRaster = null;
        }
        else {
            this.currentOffScreen = this.workingOffScreen;
            this.currentBaseRaster = this.workingBaseRaster;
            this.currentRaster = this.workingRaster;
        }
    }
    
    public void updateOffScreen(final int offScreenWidth, final int offScreenHeight) {
        this.offScreenWidth = offScreenWidth;
        this.offScreenHeight = offScreenHeight;
    }
    
    public BufferedImage getOffScreen() {
        if (this.rootGN == null) {
            return null;
        }
        return this.currentOffScreen;
    }
    
    public void clearOffScreen() {
        if (this.isDoubleBuffered) {
            return;
        }
        this.updateWorkingBuffers();
        if (this.rootCR == null || this.workingBaseRaster == null) {
            return;
        }
        final ColorModel colorModel = this.rootCR.getColorModel();
        final WritableRaster workingBaseRaster = this.workingBaseRaster;
        synchronized (workingBaseRaster) {
            final BufferedImage bufferedImage = new BufferedImage(colorModel, this.workingBaseRaster, colorModel.isAlphaPremultiplied(), null);
            final Graphics2D graphics = bufferedImage.createGraphics();
            graphics.setComposite(AlphaComposite.Clear);
            graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
            graphics.dispose();
        }
    }
    
    public void repaint(final Shape pSrc) {
        if (pSrc == null) {
            return;
        }
        final RectListManager rectListManager = new RectListManager();
        rectListManager.add(this.usr2dev.createTransformedShape(pSrc).getBounds());
        this.repaint(rectListManager);
    }
    
    public void repaint(final RectListManager rectListManager) {
        if (rectListManager == null) {
            return;
        }
        this.updateWorkingBuffers();
        if (this.rootCR == null || this.workingBaseRaster == null) {
            return;
        }
        CachableRed rootCR = this.rootCR;
        final WritableRaster workingBaseRaster = this.workingBaseRaster;
        final WritableRaster workingRaster = this.workingRaster;
        final Rectangle bounds = this.rootCR.getBounds();
        final Rectangle bounds2 = this.workingRaster.getBounds();
        if (bounds2.x < bounds.x || bounds2.y < bounds.y || bounds2.x + bounds2.width > bounds.x + bounds.width || bounds2.y + bounds2.height > bounds.y + bounds.height) {
            rootCR = new PadRed(rootCR, bounds2, PadMode.ZERO_PAD, null);
        }
        synchronized (workingBaseRaster) {
            rootCR.copyData(workingRaster);
        }
        if (!HaltingThread.hasBeenHalted()) {
            final BufferedImage workingOffScreen = this.workingOffScreen;
            this.workingBaseRaster = this.currentBaseRaster;
            this.workingRaster = this.currentRaster;
            this.workingOffScreen = this.currentOffScreen;
            this.currentRaster = workingRaster;
            this.currentBaseRaster = workingBaseRaster;
            this.currentOffScreen = workingOffScreen;
        }
    }
    
    public void flush() {
        if (this.lastCache == null) {
            return;
        }
        final TileCacheRed value = this.lastCache.get();
        if (value == null) {
            return;
        }
        final TileCacheRed tileCacheRed = value;
        tileCacheRed.flushCache(tileCacheRed.getBounds());
    }
    
    public void flush(final Collection collection) {
        final AffineTransform transform = this.getTransform();
        final Iterator<Shape> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.flush(transform.createTransformedShape(iterator.next()).getBounds());
        }
    }
    
    public void flush(Rectangle rectangle) {
        if (this.lastCache == null) {
            return;
        }
        final TileCacheRed value = this.lastCache.get();
        if (value == null) {
            return;
        }
        final TileCacheRed tileCacheRed = value;
        final Rectangle rectangle2;
        rectangle = (rectangle2 = (Rectangle)rectangle.clone());
        rectangle2.x -= Math.round((float)this.usr2dev.getTranslateX());
        final Rectangle rectangle3 = rectangle;
        rectangle3.y -= Math.round((float)this.usr2dev.getTranslateY());
        tileCacheRed.flushCache(rectangle);
    }
    
    protected CachableRed setupCache(final CachableRed referent) {
        if (this.lastCR == null || referent != this.lastCR.get()) {
            this.lastCR = new SoftReference((T)referent);
            this.lastCache = null;
        }
        CachableRed value = null;
        if (this.lastCache != null) {
            value = this.lastCache.get();
        }
        if (value != null) {
            return value;
        }
        final TileCacheRed referent2 = new TileCacheRed(referent);
        this.lastCache = new SoftReference(referent2);
        return referent2;
    }
    
    protected CachableRed renderGNR() {
        final AffineTransform usr2dev = this.usr2dev;
        final RenderedImage rendering = this.rootFilter.createRendering(new RenderContext(new AffineTransform(usr2dev.getScaleX(), usr2dev.getShearY(), usr2dev.getShearX(), usr2dev.getScaleY(), 0.0, 0.0), null, this.renderingHints));
        if (rendering == null) {
            return null;
        }
        final CachableRed setupCache = this.setupCache(GraphicsUtil.wrap(rendering));
        return GraphicsUtil.convertTosRGB(new TranslateRed(setupCache, setupCache.getMinX() + Math.round((float)usr2dev.getTranslateX()), setupCache.getMinY() + Math.round((float)usr2dev.getTranslateY())));
    }
    
    protected void updateWorkingBuffers() {
        if (this.rootFilter == null) {
            this.rootFilter = this.rootGN.getGraphicsNodeRable(true);
            this.rootCR = null;
        }
        this.rootCR = this.renderGNR();
        if (this.rootCR == null) {
            this.workingRaster = null;
            this.workingOffScreen = null;
            this.workingBaseRaster = null;
            this.currentOffScreen = null;
            this.currentBaseRaster = null;
            this.currentRaster = null;
            return;
        }
        final SampleModel sampleModel = this.rootCR.getSampleModel();
        final int offScreenWidth = this.offScreenWidth;
        final int offScreenHeight = this.offScreenHeight;
        final int width = sampleModel.getWidth();
        final int height = sampleModel.getHeight();
        final int w = ((offScreenWidth + width - 1) / width + 1) * width;
        final int h = ((offScreenHeight + height - 1) / height + 1) * height;
        if (this.workingBaseRaster == null || this.workingBaseRaster.getWidth() < w || this.workingBaseRaster.getHeight() < h) {
            this.workingBaseRaster = Raster.createWritableRaster(sampleModel.createCompatibleSampleModel(w, h), new Point(0, 0));
        }
        final int n = -this.rootCR.getTileGridXOffset();
        final int n2 = -this.rootCR.getTileGridYOffset();
        int n3;
        if (n >= 0) {
            n3 = n / width;
        }
        else {
            n3 = (n - width + 1) / width;
        }
        int n4;
        if (n2 >= 0) {
            n4 = n2 / height;
        }
        else {
            n4 = (n2 - height + 1) / height;
        }
        this.workingRaster = this.workingBaseRaster.createWritableChild(0, 0, w, h, n3 * width - n, n4 * height - n2, null);
        this.workingOffScreen = new BufferedImage(this.rootCR.getColorModel(), this.workingRaster.createWritableChild(0, 0, this.offScreenWidth, this.offScreenHeight, 0, 0, null), this.rootCR.getColorModel().isAlphaPremultiplied(), null);
        if (!this.isDoubleBuffered) {
            this.currentOffScreen = this.workingOffScreen;
            this.currentBaseRaster = this.workingBaseRaster;
            this.currentRaster = this.workingRaster;
        }
    }
    
    static {
        (StaticRenderer.defaultRenderingHints = new RenderingHints(null)).put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        StaticRenderer.defaultRenderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }
}
