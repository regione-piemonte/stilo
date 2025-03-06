// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.awt.geom.AffineTransform;
import org.apache.batik.ext.awt.geom.RectListManager;
import java.awt.Shape;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.batik.gvt.renderer.ImageRenderer;

public class RepaintManager
{
    static final int COPY_OVERHEAD = 10000;
    static final int COPY_LINE_OVERHEAD = 10;
    protected ImageRenderer renderer;
    
    public RepaintManager(final ImageRenderer renderer) {
        this.renderer = renderer;
    }
    
    public Collection updateRendering(final Collection collection) throws InterruptedException {
        this.renderer.flush(collection);
        final ArrayList<Rectangle> list = new ArrayList<Rectangle>(collection.size());
        final AffineTransform transform = this.renderer.getTransform();
        final Iterator<Shape> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final Rectangle2D bounds2D = transform.createTransformedShape(iterator.next()).getBounds2D();
            final int n = (int)Math.floor(bounds2D.getX());
            final int n2 = (int)Math.floor(bounds2D.getY());
            list.add(new Rectangle(n - 1, n2 - 1, (int)Math.ceil(bounds2D.getX() + bounds2D.getWidth()) - n + 3, (int)Math.ceil(bounds2D.getY() + bounds2D.getHeight()) - n2 + 3));
        }
        RectListManager rectListManager = null;
        try {
            rectListManager = new RectListManager(list);
            rectListManager.mergeRects(10000, 10);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        this.renderer.repaint(rectListManager);
        return rectListManager;
    }
    
    public void setupRenderer(final AffineTransform transform, final boolean doubleBuffered, final Shape shape, final int n, final int n2) {
        this.renderer.setTransform(transform);
        this.renderer.setDoubleBuffered(doubleBuffered);
        this.renderer.updateOffScreen(n, n2);
        this.renderer.clearOffScreen();
    }
    
    public BufferedImage getOffScreen() {
        return this.renderer.getOffScreen();
    }
}
