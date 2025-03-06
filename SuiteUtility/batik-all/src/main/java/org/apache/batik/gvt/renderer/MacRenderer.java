// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.renderer;

import java.util.Iterator;
import org.apache.batik.util.HaltingThread;
import java.awt.image.ImageObserver;
import java.awt.Image;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Shape;
import java.util.Collection;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.util.Map;
import java.awt.Color;
import org.apache.batik.ext.awt.geom.RectListManager;
import java.awt.image.BufferedImage;
import org.apache.batik.gvt.GraphicsNode;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class MacRenderer implements ImageRenderer
{
    static final int COPY_OVERHEAD = 1000;
    static final int COPY_LINE_OVERHEAD = 10;
    static final AffineTransform IDENTITY;
    protected RenderingHints renderingHints;
    protected AffineTransform usr2dev;
    protected GraphicsNode rootGN;
    protected int offScreenWidth;
    protected int offScreenHeight;
    protected boolean isDoubleBuffered;
    protected BufferedImage currImg;
    protected BufferedImage workImg;
    protected RectListManager damagedAreas;
    public static int IMAGE_TYPE;
    public static Color TRANSPARENT_WHITE;
    protected static RenderingHints defaultRenderingHints;
    
    public MacRenderer() {
        (this.renderingHints = new RenderingHints(null)).add(MacRenderer.defaultRenderingHints);
        this.usr2dev = new AffineTransform();
    }
    
    public MacRenderer(final RenderingHints hints, AffineTransform tx) {
        (this.renderingHints = new RenderingHints(null)).add(hints);
        if (tx == null) {
            tx = new AffineTransform();
        }
        else {
            tx = new AffineTransform(tx);
        }
    }
    
    public void dispose() {
        this.rootGN = null;
        this.currImg = null;
        this.workImg = null;
        this.renderingHints = null;
        this.usr2dev = null;
        if (this.damagedAreas != null) {
            this.damagedAreas.clear();
        }
        this.damagedAreas = null;
    }
    
    public void setTree(final GraphicsNode rootGN) {
        this.rootGN = rootGN;
    }
    
    public GraphicsNode getTree() {
        return this.rootGN;
    }
    
    public void setTransform(final AffineTransform tx) {
        if (tx == null) {
            this.usr2dev = new AffineTransform();
        }
        else {
            this.usr2dev = new AffineTransform(tx);
        }
        if (this.workImg == null) {
            return;
        }
        synchronized (this.workImg) {
            final Graphics2D graphics = this.workImg.createGraphics();
            graphics.setComposite(AlphaComposite.Clear);
            graphics.fillRect(0, 0, this.workImg.getWidth(), this.workImg.getHeight());
            graphics.dispose();
        }
        this.damagedAreas = null;
    }
    
    public AffineTransform getTransform() {
        return this.usr2dev;
    }
    
    public void setRenderingHints(final RenderingHints hints) {
        (this.renderingHints = new RenderingHints(null)).add(hints);
        this.damagedAreas = null;
    }
    
    public RenderingHints getRenderingHints() {
        return this.renderingHints;
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
            this.workImg = null;
        }
        else {
            this.workImg = this.currImg;
            this.damagedAreas = null;
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
        return this.currImg;
    }
    
    public void clearOffScreen() {
        if (this.isDoubleBuffered) {
            return;
        }
        this.updateWorkingBuffers();
        if (this.workImg == null) {
            return;
        }
        synchronized (this.workImg) {
            final Graphics2D graphics = this.workImg.createGraphics();
            graphics.setComposite(AlphaComposite.Clear);
            graphics.fillRect(0, 0, this.workImg.getWidth(), this.workImg.getHeight());
            graphics.dispose();
        }
        this.damagedAreas = null;
    }
    
    public void flush() {
    }
    
    public void flush(final Rectangle rectangle) {
    }
    
    public void flush(final Collection collection) {
    }
    
    protected void updateWorkingBuffers() {
        if (this.rootGN == null) {
            this.currImg = null;
            this.workImg = null;
            return;
        }
        final int offScreenWidth = this.offScreenWidth;
        final int offScreenHeight = this.offScreenHeight;
        if (this.workImg == null || this.workImg.getWidth() < offScreenWidth || this.workImg.getHeight() < offScreenHeight) {
            this.workImg = new BufferedImage(offScreenWidth, offScreenHeight, MacRenderer.IMAGE_TYPE);
        }
        if (!this.isDoubleBuffered) {
            this.currImg = this.workImg;
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
    
    public void repaint(final RectListManager damagedAreas) {
        if (damagedAreas == null) {
            return;
        }
        this.updateWorkingBuffers();
        if (this.rootGN == null || this.workImg == null) {
            return;
        }
        try {
            synchronized (this.workImg) {
                final Graphics2D graphics = GraphicsUtil.createGraphics(this.workImg, this.renderingHints);
                final Rectangle rectangle = new Rectangle(0, 0, this.offScreenWidth, this.offScreenHeight);
                if (this.isDoubleBuffered && this.currImg != null && this.damagedAreas != null) {
                    this.damagedAreas.subtract(damagedAreas, 1000, 10);
                    this.damagedAreas.mergeRects(1000, 10);
                    final Iterator iterator = this.damagedAreas.iterator();
                    graphics.setComposite(AlphaComposite.Src);
                    while (iterator.hasNext()) {
                        final Rectangle rectangle2 = iterator.next();
                        if (!rectangle.intersects(rectangle2)) {
                            continue;
                        }
                        final Rectangle intersection = rectangle.intersection(rectangle2);
                        graphics.setClip(intersection.x, intersection.y, intersection.width, intersection.height);
                        graphics.setComposite(AlphaComposite.Clear);
                        graphics.fillRect(intersection.x, intersection.y, intersection.width, intersection.height);
                        graphics.setComposite(AlphaComposite.SrcOver);
                        graphics.drawImage(this.currImg, 0, 0, null);
                    }
                }
                for (final Rectangle rectangle3 : damagedAreas) {
                    if (!rectangle.intersects(rectangle3)) {
                        continue;
                    }
                    final Rectangle intersection2 = rectangle.intersection(rectangle3);
                    graphics.setTransform(MacRenderer.IDENTITY);
                    graphics.setClip(intersection2.x, intersection2.y, intersection2.width, intersection2.height);
                    graphics.setComposite(AlphaComposite.Clear);
                    graphics.fillRect(intersection2.x, intersection2.y, intersection2.width, intersection2.height);
                    graphics.setComposite(AlphaComposite.SrcOver);
                    graphics.transform(this.usr2dev);
                    this.rootGN.paint(graphics);
                }
                graphics.dispose();
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        if (HaltingThread.hasBeenHalted()) {
            return;
        }
        if (this.isDoubleBuffered) {
            final BufferedImage workImg = this.workImg;
            this.workImg = this.currImg;
            this.currImg = workImg;
            this.damagedAreas = damagedAreas;
        }
    }
    
    static {
        IDENTITY = new AffineTransform();
        MacRenderer.IMAGE_TYPE = 3;
        MacRenderer.TRANSPARENT_WHITE = new Color(255, 255, 255, 0);
        (MacRenderer.defaultRenderingHints = new RenderingHints(null)).put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        MacRenderer.defaultRenderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }
}
