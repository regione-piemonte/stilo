// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.geom.Point2D;
import org.apache.batik.util.HaltingThread;
import java.util.Iterator;
import java.util.List;
import org.apache.batik.gvt.event.GraphicsNodeChangeListener;
import java.util.Vector;
import java.awt.image.renderable.RenderableImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Shape;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import org.apache.batik.gvt.filter.GraphicsNodeRable8Bit;
import org.apache.batik.gvt.filter.GraphicsNodeRable;
import java.util.Map;
import java.awt.geom.NoninvertibleTransformException;
import org.apache.batik.gvt.event.GraphicsNodeChangeEvent;
import java.awt.geom.Rectangle2D;
import java.lang.ref.WeakReference;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.filter.Mask;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.renderable.ClipRable;
import java.awt.Composite;
import java.awt.geom.AffineTransform;
import javax.swing.event.EventListenerList;

public abstract class AbstractGraphicsNode implements GraphicsNode
{
    protected EventListenerList listeners;
    protected AffineTransform transform;
    protected AffineTransform inverseTransform;
    protected Composite composite;
    protected boolean isVisible;
    protected ClipRable clip;
    protected RenderingHints hints;
    protected CompositeGraphicsNode parent;
    protected RootGraphicsNode root;
    protected Mask mask;
    protected Filter filter;
    protected int pointerEventType;
    protected WeakReference graphicsNodeRable;
    protected WeakReference enableBackgroundGraphicsNodeRable;
    protected WeakReference weakRef;
    private Rectangle2D bounds;
    protected GraphicsNodeChangeEvent changeStartedEvent;
    protected GraphicsNodeChangeEvent changeCompletedEvent;
    static double EPSILON;
    
    protected AbstractGraphicsNode() {
        this.isVisible = true;
        this.pointerEventType = 0;
        this.changeStartedEvent = null;
        this.changeCompletedEvent = null;
    }
    
    public WeakReference getWeakReference() {
        if (this.weakRef == null) {
            this.weakRef = new WeakReference((T)this);
        }
        return this.weakRef;
    }
    
    public int getPointerEventType() {
        return this.pointerEventType;
    }
    
    public void setPointerEventType(final int pointerEventType) {
        this.pointerEventType = pointerEventType;
    }
    
    public void setTransform(final AffineTransform transform) {
        this.fireGraphicsNodeChangeStarted();
        this.transform = transform;
        Label_0056: {
            if (this.transform.getDeterminant() != 0.0) {
                try {
                    this.inverseTransform = this.transform.createInverse();
                    break Label_0056;
                }
                catch (NoninvertibleTransformException ex) {
                    throw new Error(ex.getMessage());
                }
            }
            this.inverseTransform = this.transform;
        }
        if (this.parent != null) {
            this.parent.invalidateGeometryCache();
        }
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public AffineTransform getTransform() {
        return this.transform;
    }
    
    public AffineTransform getInverseTransform() {
        return this.inverseTransform;
    }
    
    public AffineTransform getGlobalTransform() {
        final AffineTransform affineTransform = new AffineTransform();
        for (AbstractGraphicsNode parent = this; parent != null; parent = parent.getParent()) {
            if (parent.getTransform() != null) {
                affineTransform.preConcatenate(parent.getTransform());
            }
        }
        return affineTransform;
    }
    
    public void setComposite(final Composite composite) {
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.composite = composite;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public Composite getComposite() {
        return this.composite;
    }
    
    public void setVisible(final boolean isVisible) {
        this.fireGraphicsNodeChangeStarted();
        this.isVisible = isVisible;
        this.invalidateGeometryCache();
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public boolean isVisible() {
        return this.isVisible;
    }
    
    public void setClip(final ClipRable clip) {
        if (clip == null && this.clip == null) {
            return;
        }
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.clip = clip;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public ClipRable getClip() {
        return this.clip;
    }
    
    public void setRenderingHint(final RenderingHints.Key key, final Object o) {
        this.fireGraphicsNodeChangeStarted();
        if (this.hints == null) {
            this.hints = new RenderingHints(key, o);
        }
        else {
            this.hints.put(key, o);
        }
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public void setRenderingHints(final Map map) {
        this.fireGraphicsNodeChangeStarted();
        if (this.hints == null) {
            this.hints = new RenderingHints(map);
        }
        else {
            this.hints.putAll(map);
        }
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public void setRenderingHints(final RenderingHints hints) {
        this.fireGraphicsNodeChangeStarted();
        this.hints = hints;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public RenderingHints getRenderingHints() {
        return this.hints;
    }
    
    public void setMask(final Mask mask) {
        if (mask == null && this.mask == null) {
            return;
        }
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.mask = mask;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public Mask getMask() {
        return this.mask;
    }
    
    public void setFilter(final Filter filter) {
        if (filter == null && this.filter == null) {
            return;
        }
        this.fireGraphicsNodeChangeStarted();
        this.invalidateGeometryCache();
        this.filter = filter;
        this.fireGraphicsNodeChangeCompleted();
    }
    
    public Filter getFilter() {
        return this.filter;
    }
    
    public Filter getGraphicsNodeRable(final boolean b) {
        Filter referent = null;
        if (this.graphicsNodeRable != null) {
            referent = (GraphicsNodeRable)this.graphicsNodeRable.get();
            if (referent != null) {
                return referent;
            }
        }
        if (b) {
            referent = new GraphicsNodeRable8Bit(this);
            this.graphicsNodeRable = new WeakReference(referent);
        }
        return referent;
    }
    
    public Filter getEnableBackgroundGraphicsNodeRable(final boolean b) {
        GraphicsNodeRable referent = null;
        if (this.enableBackgroundGraphicsNodeRable != null) {
            referent = (GraphicsNodeRable)this.enableBackgroundGraphicsNodeRable.get();
            if (referent != null) {
                return referent;
            }
        }
        if (b) {
            referent = new GraphicsNodeRable8Bit(this);
            referent.setUsePrimitivePaint(false);
            this.enableBackgroundGraphicsNodeRable = new WeakReference(referent);
        }
        return referent;
    }
    
    public void paint(Graphics2D graphics2D) {
        if (this.composite != null && this.composite instanceof AlphaComposite && ((AlphaComposite)this.composite).getAlpha() < 0.001) {
            return;
        }
        final Rectangle2D bounds = this.getBounds();
        if (bounds == null) {
            return;
        }
        Composite composite = null;
        AffineTransform transform = null;
        Map<?, ?> renderingHints = null;
        Graphics2D graphics2D2 = null;
        if (this.clip != null) {
            graphics2D2 = graphics2D;
            graphics2D = (Graphics2D)graphics2D.create();
            if (this.hints != null) {
                graphics2D.addRenderingHints(this.hints);
            }
            if (this.transform != null) {
                graphics2D.transform(this.transform);
            }
            if (this.composite != null) {
                graphics2D.setComposite(this.composite);
            }
            graphics2D.clip(this.clip.getClipPath());
        }
        else {
            if (this.hints != null) {
                renderingHints = graphics2D.getRenderingHints();
                graphics2D.addRenderingHints(this.hints);
            }
            if (this.transform != null) {
                transform = graphics2D.getTransform();
                graphics2D.transform(this.transform);
            }
            if (this.composite != null) {
                composite = graphics2D.getComposite();
                graphics2D.setComposite(this.composite);
            }
        }
        final Shape clip = graphics2D.getClip();
        graphics2D.setRenderingHint(RenderingHintsKeyExt.KEY_AREA_OF_INTEREST, clip);
        boolean b = true;
        final Shape shape = clip;
        if (shape != null) {
            final Rectangle2D bounds2D = shape.getBounds2D();
            if (!bounds.intersects(bounds2D.getX(), bounds2D.getY(), bounds2D.getWidth(), bounds2D.getHeight())) {
                b = false;
            }
        }
        if (b) {
            boolean antialiasedClip = false;
            if (this.clip != null && this.clip.getUseAntialiasedClip()) {
                antialiasedClip = this.isAntialiasedClip(graphics2D.getTransform(), graphics2D.getRenderingHints(), this.clip.getClipPath());
            }
            if (!(this.isOffscreenBufferNeeded() | antialiasedClip)) {
                this.primitivePaint(graphics2D);
            }
            else {
                Filter filter;
                if (this.filter == null) {
                    filter = this.getGraphicsNodeRable(true);
                }
                else {
                    filter = this.filter;
                }
                if (this.mask != null) {
                    if (this.mask.getSource() != filter) {
                        this.mask.setSource(filter);
                    }
                    filter = this.mask;
                }
                if (this.clip != null && antialiasedClip) {
                    if (this.clip.getSource() != filter) {
                        this.clip.setSource(filter);
                    }
                    filter = this.clip;
                }
                final Graphics2D graphics2D3 = graphics2D;
                graphics2D = (Graphics2D)graphics2D.create();
                if (antialiasedClip) {
                    graphics2D.setClip(null);
                }
                graphics2D.clip(filter.getBounds2D());
                GraphicsUtil.drawImage(graphics2D, filter);
                graphics2D.dispose();
                graphics2D = graphics2D3;
                graphics2D2 = null;
            }
        }
        if (graphics2D2 != null) {
            graphics2D.dispose();
        }
        else {
            if (renderingHints != null) {
                graphics2D.setRenderingHints(renderingHints);
            }
            if (transform != null) {
                graphics2D.setTransform(transform);
            }
            if (composite != null) {
                graphics2D.setComposite(composite);
            }
        }
    }
    
    private void traceFilter(final Filter filter, String string) {
        System.out.println(string + filter.getClass().getName());
        System.out.println(string + filter.getBounds2D());
        final Vector<RenderableImage> sources = filter.getSources();
        final int n = (sources != null) ? sources.size() : 0;
        string += "\t";
        for (int i = 0; i < n; ++i) {
            this.traceFilter(sources.get(i), string);
        }
        System.out.flush();
    }
    
    protected boolean isOffscreenBufferNeeded() {
        return this.filter != null || this.mask != null || (this.composite != null && !AlphaComposite.SrcOver.equals(this.composite));
    }
    
    protected boolean isAntialiasedClip(final AffineTransform affineTransform, final RenderingHints renderingHints, final Shape shape) {
        if (shape == null) {
            return false;
        }
        final Object value = renderingHints.get(RenderingHintsKeyExt.KEY_TRANSCODING);
        return value != "Printing" && value != "Vector" && (!(shape instanceof Rectangle2D) || affineTransform.getShearX() != 0.0 || affineTransform.getShearY() != 0.0);
    }
    
    public void fireGraphicsNodeChangeStarted(final GraphicsNode changeSrc) {
        if (this.changeStartedEvent == null) {
            this.changeStartedEvent = new GraphicsNodeChangeEvent(this, 9800);
        }
        this.changeStartedEvent.setChangeSrc(changeSrc);
        this.fireGraphicsNodeChangeStarted(this.changeStartedEvent);
        this.changeStartedEvent.setChangeSrc(null);
    }
    
    public void fireGraphicsNodeChangeStarted() {
        if (this.changeStartedEvent == null) {
            this.changeStartedEvent = new GraphicsNodeChangeEvent(this, 9800);
        }
        else {
            this.changeStartedEvent.setChangeSrc(null);
        }
        this.fireGraphicsNodeChangeStarted(this.changeStartedEvent);
    }
    
    public void fireGraphicsNodeChangeStarted(final GraphicsNodeChangeEvent graphicsNodeChangeEvent) {
        final RootGraphicsNode root = this.getRoot();
        if (root == null) {
            return;
        }
        final List treeGraphicsNodeChangeListeners = root.getTreeGraphicsNodeChangeListeners();
        if (treeGraphicsNodeChangeListeners == null) {
            return;
        }
        final Iterator<GraphicsNodeChangeListener> iterator = treeGraphicsNodeChangeListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().changeStarted(graphicsNodeChangeEvent);
        }
    }
    
    public void fireGraphicsNodeChangeCompleted() {
        if (this.changeCompletedEvent == null) {
            this.changeCompletedEvent = new GraphicsNodeChangeEvent(this, 9801);
        }
        final RootGraphicsNode root = this.getRoot();
        if (root == null) {
            return;
        }
        final List treeGraphicsNodeChangeListeners = root.getTreeGraphicsNodeChangeListeners();
        if (treeGraphicsNodeChangeListeners == null) {
            return;
        }
        final Iterator<GraphicsNodeChangeListener> iterator = treeGraphicsNodeChangeListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().changeCompleted(this.changeCompletedEvent);
        }
    }
    
    public CompositeGraphicsNode getParent() {
        return this.parent;
    }
    
    public RootGraphicsNode getRoot() {
        return this.root;
    }
    
    protected void setRoot(final RootGraphicsNode root) {
        this.root = root;
    }
    
    protected void setParent(final CompositeGraphicsNode parent) {
        this.parent = parent;
    }
    
    protected void invalidateGeometryCache() {
        if (this.parent != null) {
            this.parent.invalidateGeometryCache();
        }
        this.bounds = null;
    }
    
    public Rectangle2D getBounds() {
        if (this.bounds == null) {
            if (this.filter == null) {
                this.bounds = this.getPrimitiveBounds();
            }
            else {
                this.bounds = this.filter.getBounds2D();
            }
            if (this.bounds != null) {
                if (this.clip != null) {
                    final Rectangle2D bounds2D = this.clip.getClipPath().getBounds2D();
                    if (bounds2D.intersects(this.bounds)) {
                        Rectangle2D.intersect(this.bounds, bounds2D, this.bounds);
                    }
                }
                if (this.mask != null) {
                    final Rectangle2D bounds2D2 = this.mask.getBounds2D();
                    if (bounds2D2.intersects(this.bounds)) {
                        Rectangle2D.intersect(this.bounds, bounds2D2, this.bounds);
                    }
                }
            }
            this.bounds = this.normalizeRectangle(this.bounds);
            if (HaltingThread.hasBeenHalted()) {
                this.invalidateGeometryCache();
            }
        }
        return this.bounds;
    }
    
    public Rectangle2D getTransformedBounds(final AffineTransform tx) {
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        Rectangle2D rectangle2D;
        if (this.filter == null) {
            rectangle2D = this.getTransformedPrimitiveBounds(tx);
        }
        else {
            rectangle2D = affineTransform.createTransformedShape(this.filter.getBounds2D()).getBounds2D();
        }
        if (rectangle2D != null) {
            if (this.clip != null) {
                Rectangle2D.intersect(rectangle2D, affineTransform.createTransformedShape(this.clip.getClipPath()).getBounds2D(), rectangle2D);
            }
            if (this.mask != null) {
                Rectangle2D.intersect(rectangle2D, affineTransform.createTransformedShape(this.mask.getBounds2D()).getBounds2D(), rectangle2D);
            }
        }
        return rectangle2D;
    }
    
    public Rectangle2D getTransformedPrimitiveBounds(final AffineTransform tx) {
        final Rectangle2D primitiveBounds = this.getPrimitiveBounds();
        if (primitiveBounds == null) {
            return null;
        }
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        return affineTransform.createTransformedShape(primitiveBounds).getBounds2D();
    }
    
    public Rectangle2D getTransformedGeometryBounds(final AffineTransform tx) {
        final Rectangle2D geometryBounds = this.getGeometryBounds();
        if (geometryBounds == null) {
            return null;
        }
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        return affineTransform.createTransformedShape(geometryBounds).getBounds2D();
    }
    
    public Rectangle2D getTransformedSensitiveBounds(final AffineTransform tx) {
        final Rectangle2D sensitiveBounds = this.getSensitiveBounds();
        if (sensitiveBounds == null) {
            return null;
        }
        AffineTransform affineTransform = tx;
        if (this.transform != null) {
            affineTransform = new AffineTransform(tx);
            affineTransform.concatenate(this.transform);
        }
        return affineTransform.createTransformedShape(sensitiveBounds).getBounds2D();
    }
    
    public boolean contains(final Point2D p) {
        final Rectangle2D sensitiveBounds = this.getSensitiveBounds();
        if (sensitiveBounds == null || !sensitiveBounds.contains(p)) {
            return false;
        }
        switch (this.pointerEventType) {
            case 0:
            case 1:
            case 2:
            case 3: {
                return this.isVisible;
            }
            case 4:
            case 5:
            case 6:
            case 7: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean intersects(final Rectangle2D r) {
        final Rectangle2D bounds = this.getBounds();
        return bounds != null && bounds.intersects(r);
    }
    
    public GraphicsNode nodeHitAt(final Point2D point2D) {
        return this.contains(point2D) ? this : null;
    }
    
    protected Rectangle2D normalizeRectangle(final Rectangle2D rectangle2D) {
        if (rectangle2D == null) {
            return null;
        }
        if (rectangle2D.getWidth() < AbstractGraphicsNode.EPSILON) {
            if (rectangle2D.getHeight() < AbstractGraphicsNode.EPSILON) {
                final double sqrt = Math.sqrt(this.getGlobalTransform().getDeterminant());
                return new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), AbstractGraphicsNode.EPSILON / sqrt, AbstractGraphicsNode.EPSILON / sqrt);
            }
            double width = rectangle2D.getHeight() * AbstractGraphicsNode.EPSILON;
            if (width < rectangle2D.getWidth()) {
                width = rectangle2D.getWidth();
            }
            return new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), width, rectangle2D.getHeight());
        }
        else {
            if (rectangle2D.getHeight() < AbstractGraphicsNode.EPSILON) {
                double height = rectangle2D.getWidth() * AbstractGraphicsNode.EPSILON;
                if (height < rectangle2D.getHeight()) {
                    height = rectangle2D.getHeight();
                }
                return new Rectangle2D.Double(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), height);
            }
            return rectangle2D;
        }
    }
    
    static {
        AbstractGraphicsNode.EPSILON = 1.0E-6;
    }
}
