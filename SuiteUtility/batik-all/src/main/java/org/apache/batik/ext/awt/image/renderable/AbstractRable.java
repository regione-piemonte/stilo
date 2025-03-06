// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Shape;
import java.util.Set;
import java.awt.image.renderable.RenderableImage;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.rendered.RenderedImageCachableRed;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public abstract class AbstractRable implements Filter
{
    protected Vector srcs;
    protected Map props;
    protected long stamp;
    
    protected AbstractRable() {
        this.props = new HashMap();
        this.stamp = 0L;
        this.srcs = new Vector();
    }
    
    protected AbstractRable(final Filter filter) {
        this.props = new HashMap();
        this.stamp = 0L;
        this.init(filter, null);
    }
    
    protected AbstractRable(final Filter filter, final Map map) {
        this.props = new HashMap();
        this.stamp = 0L;
        this.init(filter, map);
    }
    
    protected AbstractRable(final List list) {
        this(list, null);
    }
    
    protected AbstractRable(final List list, final Map map) {
        this.props = new HashMap();
        this.stamp = 0L;
        this.init(list, map);
    }
    
    public final void touch() {
        ++this.stamp;
    }
    
    public long getTimeStamp() {
        return this.stamp;
    }
    
    protected void init(final Filter e) {
        this.touch();
        this.srcs = new Vector(1);
        if (e != null) {
            this.srcs.add(e);
        }
    }
    
    protected void init(final Filter filter, final Map map) {
        this.init(filter);
        if (map != null) {
            this.props.putAll(map);
        }
    }
    
    protected void init(final List c) {
        this.touch();
        this.srcs = new Vector(c);
    }
    
    protected void init(final List list, final Map map) {
        this.init(list);
        if (map != null) {
            this.props.putAll(map);
        }
    }
    
    public Rectangle2D getBounds2D() {
        Rectangle2D rectangle2D = null;
        if (this.srcs.size() != 0) {
            final Iterator<Filter> iterator = (Iterator<Filter>)this.srcs.iterator();
            rectangle2D = (Rectangle2D)iterator.next().getBounds2D().clone();
            while (iterator.hasNext()) {
                Rectangle2D.union(rectangle2D, iterator.next().getBounds2D(), rectangle2D);
            }
        }
        return rectangle2D;
    }
    
    public Vector getSources() {
        return this.srcs;
    }
    
    public RenderedImage createDefaultRendering() {
        return this.createScaledRendering(100, 100, null);
    }
    
    public RenderedImage createScaledRendering(final int width, final int height, final RenderingHints hints) {
        final float min = Math.min(width / this.getWidth(), height / this.getHeight());
        return new PadRed(RenderedImageCachableRed.wrap(this.createRendering(new RenderContext(AffineTransform.getScaleInstance(min, min), hints))), new Rectangle((int)((this.getWidth() * min - width) / 2.0f), (int)((this.getHeight() * min - height) / 2.0f), width, height), PadMode.ZERO_PAD, null);
    }
    
    public float getMinX() {
        return (float)this.getBounds2D().getX();
    }
    
    public float getMinY() {
        return (float)this.getBounds2D().getY();
    }
    
    public float getWidth() {
        return (float)this.getBounds2D().getWidth();
    }
    
    public float getHeight() {
        return (float)this.getBounds2D().getHeight();
    }
    
    public Object getProperty(final String s) {
        final Object value = this.props.get(s);
        if (value != null) {
            return value;
        }
        final Iterator<RenderableImage> iterator = this.srcs.iterator();
        while (iterator.hasNext()) {
            final Object property = iterator.next().getProperty(s);
            if (property != null) {
                return property;
            }
        }
        return null;
    }
    
    public String[] getPropertyNames() {
        final Set keySet = this.props.keySet();
        final Iterator<String> iterator = keySet.iterator();
        String[] array = new String[keySet.size()];
        int n = 0;
        while (iterator.hasNext()) {
            array[n++] = iterator.next();
        }
        final Iterator<RenderableImage> iterator2 = this.srcs.iterator();
        while (iterator2.hasNext()) {
            final String[] propertyNames = iterator2.next().getPropertyNames();
            if (propertyNames.length != 0) {
                final String[] array2 = new String[array.length + propertyNames.length];
                System.arraycopy(array, 0, array2, 0, array.length);
                System.arraycopy(array2, array.length, propertyNames, 0, propertyNames.length);
                array = array2;
            }
        }
        return array;
    }
    
    public boolean isDynamic() {
        return false;
    }
    
    public Shape getDependencyRegion(final int n, final Rectangle2D rectangle2D) {
        if (n < 0 || n > this.srcs.size()) {
            throw new IndexOutOfBoundsException("Nonexistant source requested.");
        }
        final Rectangle2D rectangle2D2 = (Rectangle2D)rectangle2D.clone();
        final Rectangle2D bounds2D = this.getBounds2D();
        if (!bounds2D.intersects(rectangle2D2)) {
            return new Rectangle2D.Float();
        }
        Rectangle2D.intersect(rectangle2D2, bounds2D, rectangle2D2);
        return rectangle2D2;
    }
    
    public Shape getDirtyRegion(final int n, final Rectangle2D rectangle2D) {
        if (n < 0 || n > this.srcs.size()) {
            throw new IndexOutOfBoundsException("Nonexistant source requested.");
        }
        final Rectangle2D rectangle2D2 = (Rectangle2D)rectangle2D.clone();
        final Rectangle2D bounds2D = this.getBounds2D();
        if (!bounds2D.intersects(rectangle2D2)) {
            return new Rectangle2D.Float();
        }
        Rectangle2D.intersect(rectangle2D2, bounds2D, rectangle2D2);
        return rectangle2D2;
    }
}
