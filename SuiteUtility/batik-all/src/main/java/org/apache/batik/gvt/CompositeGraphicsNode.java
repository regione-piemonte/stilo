// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.awt.Rectangle;
import java.util.ListIterator;
import java.util.Collection;
import java.util.Iterator;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import org.apache.batik.util.HaltingThread;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class CompositeGraphicsNode extends AbstractGraphicsNode implements List
{
    public static final Rectangle2D VIEWPORT;
    public static final Rectangle2D NULL_RECT;
    protected GraphicsNode[] children;
    protected volatile int count;
    protected volatile int modCount;
    protected Rectangle2D backgroundEnableRgn;
    private volatile Rectangle2D geometryBounds;
    private volatile Rectangle2D primitiveBounds;
    private volatile Rectangle2D sensitiveBounds;
    private Shape outline;
    
    public CompositeGraphicsNode() {
        this.backgroundEnableRgn = null;
    }
    
    public List getChildren() {
        return this;
    }
    
    public void setBackgroundEnable(final Rectangle2D backgroundEnableRgn) {
        this.backgroundEnableRgn = backgroundEnableRgn;
    }
    
    public Rectangle2D getBackgroundEnable() {
        return this.backgroundEnableRgn;
    }
    
    public void setVisible(final boolean isVisible) {
        this.isVisible = isVisible;
    }
    
    public void primitivePaint(final Graphics2D graphics2D) {
        if (this.count == 0) {
            return;
        }
        final Thread currentThread = Thread.currentThread();
        for (int i = 0; i < this.count; ++i) {
            if (HaltingThread.hasBeenHalted(currentThread)) {
                return;
            }
            final GraphicsNode graphicsNode = this.children[i];
            if (graphicsNode != null) {
                graphicsNode.paint(graphics2D);
            }
        }
    }
    
    protected void invalidateGeometryCache() {
        super.invalidateGeometryCache();
        this.geometryBounds = null;
        this.primitiveBounds = null;
        this.sensitiveBounds = null;
        this.outline = null;
    }
    
    public Rectangle2D getPrimitiveBounds() {
        if (this.primitiveBounds != null) {
            if (this.primitiveBounds == CompositeGraphicsNode.NULL_RECT) {
                return null;
            }
            return this.primitiveBounds;
        }
        else {
            final Thread currentThread = Thread.currentThread();
            int i = 0;
            Rectangle2D transformedBounds = null;
            while (transformedBounds == null && i < this.count) {
                transformedBounds = this.children[i++].getTransformedBounds(GraphicsNode.IDENTITY);
                if ((i & 0xF) == 0x0 && HaltingThread.hasBeenHalted(currentThread)) {
                    break;
                }
            }
            if (HaltingThread.hasBeenHalted(currentThread)) {
                this.invalidateGeometryCache();
                return null;
            }
            if (transformedBounds == null) {
                this.primitiveBounds = CompositeGraphicsNode.NULL_RECT;
                return null;
            }
            this.primitiveBounds = transformedBounds;
            while (i < this.count) {
                final Rectangle2D transformedBounds2 = this.children[i++].getTransformedBounds(GraphicsNode.IDENTITY);
                if (transformedBounds2 != null) {
                    if (this.primitiveBounds == null) {
                        return null;
                    }
                    this.primitiveBounds.add(transformedBounds2);
                }
                if ((i & 0xF) == 0x0 && HaltingThread.hasBeenHalted(currentThread)) {
                    break;
                }
            }
            if (HaltingThread.hasBeenHalted(currentThread)) {
                this.invalidateGeometryCache();
            }
            return this.primitiveBounds;
        }
    }
    
    public static Rectangle2D getTransformedBBox(final Rectangle2D rectangle2D, final AffineTransform affineTransform) {
        if (affineTransform == null || rectangle2D == null) {
            return rectangle2D;
        }
        double x = rectangle2D.getX();
        final double width = rectangle2D.getWidth();
        double y = rectangle2D.getY();
        final double height = rectangle2D.getHeight();
        double scaleX = affineTransform.getScaleX();
        double scaleY = affineTransform.getScaleY();
        if (scaleX < 0.0) {
            x = -(x + width);
            scaleX = -scaleX;
        }
        if (scaleY < 0.0) {
            y = -(y + height);
            scaleY = -scaleY;
        }
        return new Rectangle2D.Float((float)(x * scaleX + affineTransform.getTranslateX()), (float)(y * scaleY + affineTransform.getTranslateY()), (float)(width * scaleX), (float)(height * scaleY));
    }
    
    public Rectangle2D getTransformedPrimitiveBounds(final AffineTransform tx) {
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        if (affineTransform == null || (affineTransform.getShearX() == 0.0 && affineTransform.getShearY() == 0.0)) {
            return getTransformedBBox(this.getPrimitiveBounds(), affineTransform);
        }
        int i;
        Rectangle2D transformedBounds;
        for (i = 0, transformedBounds = null; transformedBounds == null && i < this.count; transformedBounds = this.children[i++].getTransformedBounds(affineTransform)) {}
        while (i < this.count) {
            final Rectangle2D transformedBounds2 = this.children[i++].getTransformedBounds(affineTransform);
            if (transformedBounds2 != null) {
                transformedBounds.add(transformedBounds2);
            }
        }
        return transformedBounds;
    }
    
    public Rectangle2D getGeometryBounds() {
        if (this.geometryBounds == null) {
            int i;
            for (i = 0; this.geometryBounds == null && i < this.count; this.geometryBounds = this.children[i++].getTransformedGeometryBounds(GraphicsNode.IDENTITY)) {}
            while (i < this.count) {
                final Rectangle2D transformedGeometryBounds = this.children[i++].getTransformedGeometryBounds(GraphicsNode.IDENTITY);
                if (transformedGeometryBounds != null) {
                    if (this.geometryBounds == null) {
                        return this.getGeometryBounds();
                    }
                    this.geometryBounds.add(transformedGeometryBounds);
                }
            }
        }
        return this.geometryBounds;
    }
    
    public Rectangle2D getTransformedGeometryBounds(final AffineTransform tx) {
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        if (affineTransform == null || (affineTransform.getShearX() == 0.0 && affineTransform.getShearY() == 0.0)) {
            return getTransformedBBox(this.getGeometryBounds(), affineTransform);
        }
        Rectangle2D transformedGeometryBounds;
        int i;
        for (transformedGeometryBounds = null, i = 0; transformedGeometryBounds == null && i < this.count; transformedGeometryBounds = this.children[i++].getTransformedGeometryBounds(affineTransform)) {}
        while (i < this.count) {
            final Rectangle2D transformedGeometryBounds2 = this.children[i++].getTransformedGeometryBounds(affineTransform);
            if (transformedGeometryBounds2 != null) {
                transformedGeometryBounds.add(transformedGeometryBounds2);
            }
        }
        return transformedGeometryBounds;
    }
    
    public Rectangle2D getSensitiveBounds() {
        if (this.sensitiveBounds != null) {
            return this.sensitiveBounds;
        }
        int i;
        for (i = 0; this.sensitiveBounds == null && i < this.count; this.sensitiveBounds = this.children[i++].getTransformedSensitiveBounds(GraphicsNode.IDENTITY)) {}
        while (i < this.count) {
            final Rectangle2D transformedSensitiveBounds = this.children[i++].getTransformedSensitiveBounds(GraphicsNode.IDENTITY);
            if (transformedSensitiveBounds != null) {
                if (this.sensitiveBounds == null) {
                    return this.getSensitiveBounds();
                }
                this.sensitiveBounds.add(transformedSensitiveBounds);
            }
        }
        return this.sensitiveBounds;
    }
    
    public Rectangle2D getTransformedSensitiveBounds(final AffineTransform tx) {
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        if (affineTransform == null || (affineTransform.getShearX() == 0.0 && affineTransform.getShearY() == 0.0)) {
            return getTransformedBBox(this.getSensitiveBounds(), affineTransform);
        }
        Rectangle2D transformedSensitiveBounds;
        int i;
        for (transformedSensitiveBounds = null, i = 0; transformedSensitiveBounds == null && i < this.count; transformedSensitiveBounds = this.children[i++].getTransformedSensitiveBounds(affineTransform)) {}
        while (i < this.count) {
            final Rectangle2D transformedSensitiveBounds2 = this.children[i++].getTransformedSensitiveBounds(affineTransform);
            if (transformedSensitiveBounds2 != null) {
                transformedSensitiveBounds.add(transformedSensitiveBounds2);
            }
        }
        return transformedSensitiveBounds;
    }
    
    public boolean contains(final Point2D point2D) {
        final Rectangle2D sensitiveBounds = this.getSensitiveBounds();
        if (this.count > 0 && sensitiveBounds != null && sensitiveBounds.contains(point2D)) {
            Point2D ptDst = null;
            for (int i = 0; i < this.count; ++i) {
                final AffineTransform inverseTransform = this.children[i].getInverseTransform();
                Point2D transform;
                if (inverseTransform != null) {
                    ptDst = (transform = inverseTransform.transform(point2D, ptDst));
                }
                else {
                    transform = point2D;
                }
                if (this.children[i].contains(transform)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public GraphicsNode nodeHitAt(final Point2D point2D) {
        final Rectangle2D sensitiveBounds = this.getSensitiveBounds();
        if (this.count > 0 && sensitiveBounds != null && sensitiveBounds.contains(point2D)) {
            Point2D ptDst = null;
            for (int i = this.count - 1; i >= 0; --i) {
                final AffineTransform inverseTransform = this.children[i].getInverseTransform();
                Point2D transform;
                if (inverseTransform != null) {
                    ptDst = (transform = inverseTransform.transform(point2D, ptDst));
                }
                else {
                    transform = point2D;
                }
                final GraphicsNode nodeHit = this.children[i].nodeHitAt(transform);
                if (nodeHit != null) {
                    return nodeHit;
                }
            }
        }
        return null;
    }
    
    public Shape getOutline() {
        if (this.outline != null) {
            return this.outline;
        }
        this.outline = new GeneralPath();
        for (int i = 0; i < this.count; ++i) {
            final Shape outline = this.children[i].getOutline();
            if (outline != null) {
                final AffineTransform transform = this.children[i].getTransform();
                if (transform != null) {
                    ((GeneralPath)this.outline).append(transform.createTransformedShape(outline), false);
                }
                else {
                    ((GeneralPath)this.outline).append(outline, false);
                }
            }
        }
        return this.outline;
    }
    
    protected void setRoot(final RootGraphicsNode rootGraphicsNode) {
        super.setRoot(rootGraphicsNode);
        for (int i = 0; i < this.count; ++i) {
            ((AbstractGraphicsNode)this.children[i]).setRoot(rootGraphicsNode);
        }
    }
    
    public int size() {
        return this.count;
    }
    
    public boolean isEmpty() {
        return this.count == 0;
    }
    
    public boolean contains(final Object o) {
        return this.indexOf(o) >= 0;
    }
    
    public Iterator iterator() {
        return new Itr();
    }
    
    public Object[] toArray() {
        final GraphicsNode[] array = new GraphicsNode[this.count];
        System.arraycopy(this.children, 0, array, 0, this.count);
        return array;
    }
    
    public Object[] toArray(Object[] array) {
        if (array.length < this.count) {
            array = new GraphicsNode[this.count];
        }
        System.arraycopy(this.children, 0, array, 0, this.count);
        if (array.length > this.count) {
            array[this.count] = null;
        }
        return array;
    }
    
    public Object get(final int n) {
        this.checkRange(n);
        return this.children[n];
    }
    
    public Object set(final int n, final Object obj) {
        if (!(obj instanceof GraphicsNode)) {
            throw new IllegalArgumentException(obj + " is not a GraphicsNode");
        }
        this.checkRange(n);
        final GraphicsNode graphicsNode = (GraphicsNode)obj;
        this.fireGraphicsNodeChangeStarted(graphicsNode);
        if (graphicsNode.getParent() != null) {
            graphicsNode.getParent().getChildren().remove(graphicsNode);
        }
        final GraphicsNode graphicsNode2 = this.children[n];
        this.children[n] = graphicsNode;
        ((AbstractGraphicsNode)graphicsNode).setParent(this);
        ((AbstractGraphicsNode)graphicsNode2).setParent(null);
        ((AbstractGraphicsNode)graphicsNode).setRoot(this.getRoot());
        ((AbstractGraphicsNode)graphicsNode2).setRoot(null);
        this.invalidateGeometryCache();
        this.fireGraphicsNodeChangeCompleted();
        return graphicsNode2;
    }
    
    public boolean add(final Object obj) {
        if (!(obj instanceof GraphicsNode)) {
            throw new IllegalArgumentException(obj + " is not a GraphicsNode");
        }
        final GraphicsNode graphicsNode = (GraphicsNode)obj;
        this.fireGraphicsNodeChangeStarted(graphicsNode);
        if (graphicsNode.getParent() != null) {
            graphicsNode.getParent().getChildren().remove(graphicsNode);
        }
        this.ensureCapacity(this.count + 1);
        this.children[this.count++] = graphicsNode;
        ((AbstractGraphicsNode)graphicsNode).setParent(this);
        ((AbstractGraphicsNode)graphicsNode).setRoot(this.getRoot());
        this.invalidateGeometryCache();
        this.fireGraphicsNodeChangeCompleted();
        return true;
    }
    
    public void add(final int i, final Object obj) {
        if (!(obj instanceof GraphicsNode)) {
            throw new IllegalArgumentException(obj + " is not a GraphicsNode");
        }
        if (i > this.count || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + this.count);
        }
        final GraphicsNode graphicsNode = (GraphicsNode)obj;
        this.fireGraphicsNodeChangeStarted(graphicsNode);
        if (graphicsNode.getParent() != null) {
            graphicsNode.getParent().getChildren().remove(graphicsNode);
        }
        this.ensureCapacity(this.count + 1);
        System.arraycopy(this.children, i, this.children, i + 1, this.count - i);
        this.children[i] = graphicsNode;
        ++this.count;
        ((AbstractGraphicsNode)graphicsNode).setParent(this);
        ((AbstractGraphicsNode)graphicsNode).setRoot(this.getRoot());
        this.invalidateGeometryCache();
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public boolean addAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final int n, final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final Object obj) {
        if (!(obj instanceof GraphicsNode)) {
            throw new IllegalArgumentException(obj + " is not a GraphicsNode");
        }
        final GraphicsNode graphicsNode = (GraphicsNode)obj;
        if (graphicsNode.getParent() != this) {
            return false;
        }
        int n;
        for (n = 0; graphicsNode != this.children[n]; ++n) {}
        this.remove(n);
        return true;
    }
    
    public Object remove(final int n) {
        this.checkRange(n);
        final GraphicsNode graphicsNode = this.children[n];
        this.fireGraphicsNodeChangeStarted(graphicsNode);
        ++this.modCount;
        final int n2 = this.count - n - 1;
        if (n2 > 0) {
            System.arraycopy(this.children, n + 1, this.children, n, n2);
        }
        this.children[--this.count] = null;
        if (this.count == 0) {
            this.children = null;
        }
        ((AbstractGraphicsNode)graphicsNode).setParent(null);
        ((AbstractGraphicsNode)graphicsNode).setRoot(null);
        this.invalidateGeometryCache();
        this.fireGraphicsNodeChangeCompleted();
        return graphicsNode;
    }
    
    public boolean removeAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection collection) {
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public int indexOf(final Object o) {
        if (o == null || !(o instanceof GraphicsNode)) {
            return -1;
        }
        if (((GraphicsNode)o).getParent() == this) {
            final int count = this.count;
            final GraphicsNode[] children = this.children;
            for (int i = 0; i < count; ++i) {
                if (o == children[i]) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final Object o) {
        if (o == null || !(o instanceof GraphicsNode)) {
            return -1;
        }
        if (((GraphicsNode)o).getParent() == this) {
            for (int i = this.count - 1; i >= 0; --i) {
                if (o == this.children[i]) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public ListIterator listIterator() {
        return this.listIterator(0);
    }
    
    public ListIterator listIterator(final int i) {
        if (i < 0 || i > this.count) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        return new ListItr(i);
    }
    
    public List subList(final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    private void checkRange(final int i) {
        if (i >= this.count || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + this.count);
        }
    }
    
    public void ensureCapacity(final int n) {
        if (this.children == null) {
            this.children = new GraphicsNode[4];
        }
        ++this.modCount;
        final int length = this.children.length;
        if (n > length) {
            final GraphicsNode[] children = this.children;
            int n2 = length + length / 2 + 1;
            if (n2 < n) {
                n2 = n;
            }
            System.arraycopy(children, 0, this.children = new GraphicsNode[n2], 0, this.count);
        }
    }
    
    static {
        VIEWPORT = new Rectangle();
        NULL_RECT = new Rectangle();
    }
    
    private class ListItr extends Itr implements ListIterator
    {
        ListItr(final int cursor) {
            this.cursor = cursor;
        }
        
        public boolean hasPrevious() {
            return this.cursor != 0;
        }
        
        public Object previous() {
            try {
                final CompositeGraphicsNode this$0 = CompositeGraphicsNode.this;
                final int cursor = this.cursor - 1;
                this.cursor = cursor;
                final Object value = this$0.get(cursor);
                this.checkForComodification();
                this.lastRet = this.cursor;
                return value;
            }
            catch (IndexOutOfBoundsException ex) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
        }
        
        public int nextIndex() {
            return this.cursor;
        }
        
        public int previousIndex() {
            return this.cursor - 1;
        }
        
        public void set(final Object o) {
            if (this.lastRet == -1) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            try {
                CompositeGraphicsNode.this.set(this.lastRet, o);
                this.expectedModCount = CompositeGraphicsNode.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        
        public void add(final Object o) {
            this.checkForComodification();
            try {
                CompositeGraphicsNode.this.add(this.cursor++, o);
                this.lastRet = -1;
                this.expectedModCount = CompositeGraphicsNode.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
    
    private class Itr implements Iterator
    {
        int cursor;
        int lastRet;
        int expectedModCount;
        
        private Itr() {
            this.cursor = 0;
            this.lastRet = -1;
            this.expectedModCount = CompositeGraphicsNode.this.modCount;
        }
        
        public boolean hasNext() {
            return this.cursor != CompositeGraphicsNode.this.count;
        }
        
        public Object next() {
            try {
                final Object value = CompositeGraphicsNode.this.get(this.cursor);
                this.checkForComodification();
                this.lastRet = this.cursor++;
                return value;
            }
            catch (IndexOutOfBoundsException ex) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
        }
        
        public void remove() {
            if (this.lastRet == -1) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            try {
                CompositeGraphicsNode.this.remove(this.lastRet);
                if (this.lastRet < this.cursor) {
                    --this.cursor;
                }
                this.lastRet = -1;
                this.expectedModCount = CompositeGraphicsNode.this.modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
        
        final void checkForComodification() {
            if (CompositeGraphicsNode.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
