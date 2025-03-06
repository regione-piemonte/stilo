// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class ImageNode extends CompositeGraphicsNode
{
    protected boolean hitCheckChildren;
    
    public ImageNode() {
        this.hitCheckChildren = false;
    }
    
    public void setVisible(final boolean isVisible) {
        this.fireGraphicsNodeChangeStarted();
        this.isVisible = isVisible;
        this.invalidateGeometryCache();
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public Rectangle2D getPrimitiveBounds() {
        if (!this.isVisible) {
            return null;
        }
        return super.getPrimitiveBounds();
    }
    
    public void setHitCheckChildren(final boolean hitCheckChildren) {
        this.hitCheckChildren = hitCheckChildren;
    }
    
    public boolean getHitCheckChildren() {
        return this.hitCheckChildren;
    }
    
    public void paint(final Graphics2D graphics2D) {
        if (this.isVisible) {
            super.paint(graphics2D);
        }
    }
    
    public boolean contains(final Point2D point2D) {
        switch (this.pointerEventType) {
            case 0:
            case 1:
            case 2:
            case 3: {
                return this.isVisible && super.contains(point2D);
            }
            case 4:
            case 5:
            case 6:
            case 7: {
                return super.contains(point2D);
            }
            case 8: {
                return false;
            }
            default: {
                return false;
            }
        }
    }
    
    public GraphicsNode nodeHitAt(final Point2D point2D) {
        if (this.hitCheckChildren) {
            return super.nodeHitAt(point2D);
        }
        return this.contains(point2D) ? this : null;
    }
    
    public void setImage(final GraphicsNode graphicsNode) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        if (this.count == 0) {
            this.ensureCapacity(1);
        }
        this.children[0] = graphicsNode;
        ((AbstractGraphicsNode)graphicsNode).setParent(this);
        ((AbstractGraphicsNode)graphicsNode).setRoot(this.getRoot());
        this.count = 1;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public GraphicsNode getImage() {
        if (this.count > 0) {
            return this.children[0];
        }
        return null;
    }
}
