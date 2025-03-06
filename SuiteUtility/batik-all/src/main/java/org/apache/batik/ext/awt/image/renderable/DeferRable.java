// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.Shape;
import java.awt.image.renderable.RenderContext;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.util.Vector;
import java.util.Map;
import java.awt.geom.Rectangle2D;

public class DeferRable implements Filter
{
    Filter src;
    Rectangle2D bounds;
    Map props;
    
    public synchronized Filter getSource() {
        while (this.src == null) {
            try {
                this.wait();
            }
            catch (InterruptedException ex) {}
        }
        return this.src;
    }
    
    public synchronized void setSource(final Filter src) {
        if (this.src != null) {
            return;
        }
        this.src = src;
        this.bounds = src.getBounds2D();
        this.notifyAll();
    }
    
    public synchronized void setBounds(final Rectangle2D bounds) {
        if (this.bounds != null) {
            return;
        }
        this.bounds = bounds;
        this.notifyAll();
    }
    
    public synchronized void setProperties(final Map props) {
        this.props = props;
        this.notifyAll();
    }
    
    public long getTimeStamp() {
        return this.getSource().getTimeStamp();
    }
    
    public Vector getSources() {
        return this.getSource().getSources();
    }
    
    public boolean isDynamic() {
        return this.getSource().isDynamic();
    }
    
    public Rectangle2D getBounds2D() {
        synchronized (this) {
            while (this.src == null && this.bounds == null) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        if (this.src != null) {
            return this.src.getBounds2D();
        }
        return this.bounds;
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
        synchronized (this) {
            while (this.src == null && this.props == null) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        if (this.src != null) {
            return this.src.getProperty(s);
        }
        return this.props.get(s);
    }
    
    public String[] getPropertyNames() {
        synchronized (this) {
            while (this.src == null && this.props == null) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        if (this.src != null) {
            return this.src.getPropertyNames();
        }
        final String[] array = new String[this.props.size()];
        this.props.keySet().toArray(array);
        return array;
    }
    
    public RenderedImage createDefaultRendering() {
        return this.getSource().createDefaultRendering();
    }
    
    public RenderedImage createScaledRendering(final int n, final int n2, final RenderingHints renderingHints) {
        return this.getSource().createScaledRendering(n, n2, renderingHints);
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        return this.getSource().createRendering(renderContext);
    }
    
    public Shape getDependencyRegion(final int n, final Rectangle2D rectangle2D) {
        return this.getSource().getDependencyRegion(n, rectangle2D);
    }
    
    public Shape getDirtyRegion(final int n, final Rectangle2D rectangle2D) {
        return this.getSource().getDirtyRegion(n, rectangle2D);
    }
}
