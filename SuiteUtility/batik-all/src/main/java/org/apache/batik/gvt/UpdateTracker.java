// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.Rectangle;
import org.apache.batik.gvt.event.GraphicsNodeChangeEvent;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.util.Iterator;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import org.apache.batik.gvt.event.GraphicsNodeChangeAdapter;

public class UpdateTracker extends GraphicsNodeChangeAdapter
{
    Map dirtyNodes;
    Map fromBounds;
    protected static Rectangle2D NULL_RECT;
    
    public UpdateTracker() {
        this.dirtyNodes = null;
        this.fromBounds = new HashMap();
    }
    
    public boolean hasChanged() {
        return this.dirtyNodes != null;
    }
    
    public List getDirtyAreas() {
        if (this.dirtyNodes == null) {
            return null;
        }
        final LinkedList<Rectangle2D> list = new LinkedList<Rectangle2D>();
        for (final WeakReference<GraphicsNode> weakReference : this.dirtyNodes.keySet()) {
            GraphicsNode parent = weakReference.get();
            if (parent == null) {
                continue;
            }
            AffineTransform tx = this.dirtyNodes.get(weakReference);
            if (tx != null) {
                tx = new AffineTransform(tx);
            }
            final Rectangle2D pSrc = this.fromBounds.remove(weakReference);
            Rectangle2D pSrc2 = null;
            AffineTransform transform = null;
            if (!(pSrc instanceof ChngSrcRect)) {
                pSrc2 = parent.getBounds();
                transform = parent.getTransform();
                if (transform != null) {
                    transform = new AffineTransform(transform);
                }
            }
            while (true) {
                parent = parent.getParent();
                if (parent == null) {
                    break;
                }
                final Filter filter = parent.getFilter();
                if (filter != null) {
                    pSrc2 = filter.getBounds2D();
                    transform = null;
                }
                final AffineTransform transform2 = parent.getTransform();
                AffineTransform affineTransform = this.dirtyNodes.get(parent.getWeakReference());
                if (affineTransform == null) {
                    affineTransform = transform2;
                }
                if (affineTransform != null) {
                    if (tx != null) {
                        tx.preConcatenate(affineTransform);
                    }
                    else {
                        tx = new AffineTransform(affineTransform);
                    }
                }
                if (transform2 == null) {
                    continue;
                }
                if (transform != null) {
                    transform.preConcatenate(transform2);
                }
                else {
                    transform = new AffineTransform(transform2);
                }
            }
            if (parent != null) {
                continue;
            }
            Shape transformedShape = pSrc;
            if (transformedShape != null && transformedShape != UpdateTracker.NULL_RECT) {
                if (tx != null) {
                    transformedShape = tx.createTransformedShape(pSrc);
                }
                list.add((Rectangle2D)transformedShape);
            }
            if (pSrc2 == null) {
                continue;
            }
            Shape transformedShape2 = pSrc2;
            if (transform != null) {
                transformedShape2 = transform.createTransformedShape(pSrc2);
            }
            if (transformedShape2 == null) {
                continue;
            }
            list.add((Rectangle2D)transformedShape2);
        }
        this.fromBounds.clear();
        this.dirtyNodes.clear();
        return list;
    }
    
    public Rectangle2D getNodeDirtyRegion(final GraphicsNode graphicsNode, AffineTransform tx) {
        final WeakReference weakReference = graphicsNode.getWeakReference();
        AffineTransform transform = this.dirtyNodes.get(weakReference);
        if (transform == null) {
            transform = graphicsNode.getTransform();
        }
        if (transform != null) {
            tx = new AffineTransform(tx);
            tx.concatenate(transform);
        }
        final Filter filter = graphicsNode.getFilter();
        Rectangle2D pSrc = null;
        if (graphicsNode instanceof CompositeGraphicsNode) {
            final Iterator iterator = ((CompositeGraphicsNode)graphicsNode).iterator();
            while (iterator.hasNext()) {
                final Rectangle2D nodeDirtyRegion = this.getNodeDirtyRegion(iterator.next(), tx);
                if (nodeDirtyRegion != null) {
                    if (filter != null) {
                        pSrc = tx.createTransformedShape(filter.getBounds2D()).getBounds2D();
                        break;
                    }
                    if (pSrc == null || pSrc == UpdateTracker.NULL_RECT) {
                        pSrc = nodeDirtyRegion;
                    }
                    else {
                        pSrc.add(nodeDirtyRegion);
                    }
                }
            }
        }
        else {
            pSrc = this.fromBounds.remove(weakReference);
            if (pSrc == null) {
                if (filter != null) {
                    pSrc = filter.getBounds2D();
                }
                else {
                    pSrc = graphicsNode.getBounds();
                }
            }
            else if (pSrc == UpdateTracker.NULL_RECT) {
                pSrc = null;
            }
            if (pSrc != null) {
                pSrc = tx.createTransformedShape(pSrc).getBounds2D();
            }
        }
        return pSrc;
    }
    
    public Rectangle2D getNodeDirtyRegion(final GraphicsNode graphicsNode) {
        return this.getNodeDirtyRegion(graphicsNode, new AffineTransform());
    }
    
    public void changeStarted(final GraphicsNodeChangeEvent graphicsNodeChangeEvent) {
        final GraphicsNode graphicsNode = graphicsNodeChangeEvent.getGraphicsNode();
        final WeakReference weakReference = graphicsNode.getWeakReference();
        boolean b = false;
        if (this.dirtyNodes == null) {
            this.dirtyNodes = new HashMap();
            b = true;
        }
        else if (!this.dirtyNodes.containsKey(weakReference)) {
            b = true;
        }
        if (b) {
            final AffineTransform transform = graphicsNode.getTransform();
            AffineTransform affineTransform;
            if (transform != null) {
                affineTransform = (AffineTransform)transform.clone();
            }
            else {
                affineTransform = new AffineTransform();
            }
            this.dirtyNodes.put(weakReference, affineTransform);
        }
        final GraphicsNode changeSrc = graphicsNodeChangeEvent.getChangeSrc();
        Rectangle2D bounds = null;
        if (changeSrc != null) {
            final Rectangle2D nodeDirtyRegion = this.getNodeDirtyRegion(changeSrc);
            if (nodeDirtyRegion != null) {
                bounds = new ChngSrcRect(nodeDirtyRegion);
            }
        }
        else {
            bounds = graphicsNode.getBounds();
        }
        Rectangle2D null_RECT = this.fromBounds.remove(weakReference);
        if (bounds != null) {
            if (null_RECT != null && null_RECT != UpdateTracker.NULL_RECT) {
                null_RECT.add(bounds);
            }
            else {
                null_RECT = bounds;
            }
        }
        if (null_RECT == null) {
            null_RECT = UpdateTracker.NULL_RECT;
        }
        this.fromBounds.put(weakReference, null_RECT);
    }
    
    public void clear() {
        this.dirtyNodes = null;
    }
    
    static {
        UpdateTracker.NULL_RECT = new Rectangle();
    }
    
    class ChngSrcRect extends Float
    {
        ChngSrcRect(final Rectangle2D rectangle2D) {
            super((float)rectangle2D.getX(), (float)rectangle2D.getY(), (float)rectangle2D.getWidth(), (float)rectangle2D.getHeight());
        }
    }
}
