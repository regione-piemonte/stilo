// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.g2d;

import java.awt.font.FontRenderContext;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.util.Map;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Composite;
import java.awt.Stroke;
import java.awt.Paint;
import java.util.List;
import java.awt.geom.AffineTransform;

public class GraphicContext implements Cloneable
{
    protected AffineTransform defaultTransform;
    protected AffineTransform transform;
    protected List transformStack;
    protected boolean transformStackValid;
    protected Paint paint;
    protected Stroke stroke;
    protected Composite composite;
    protected Shape clip;
    protected RenderingHints hints;
    protected Font font;
    protected Color background;
    protected Color foreground;
    
    public GraphicContext() {
        this.defaultTransform = new AffineTransform();
        this.transform = new AffineTransform();
        this.transformStack = new ArrayList();
        this.transformStackValid = true;
        this.paint = Color.black;
        this.stroke = new BasicStroke();
        this.composite = AlphaComposite.SrcOver;
        this.clip = null;
        this.hints = new RenderingHints(null);
        this.font = new Font("sanserif", 0, 12);
        this.background = new Color(0, 0, 0, 0);
        this.foreground = Color.black;
        this.hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
    }
    
    public GraphicContext(final AffineTransform tx) {
        this();
        this.defaultTransform = new AffineTransform(tx);
        this.transform = new AffineTransform(this.defaultTransform);
        if (!this.defaultTransform.isIdentity()) {
            this.transformStack.add(TransformStackElement.createGeneralTransformElement(this.defaultTransform));
        }
    }
    
    public Object clone() {
        final GraphicContext graphicContext = new GraphicContext(this.defaultTransform);
        graphicContext.transform = new AffineTransform(this.transform);
        graphicContext.transformStack = new ArrayList(this.transformStack.size());
        for (int i = 0; i < this.transformStack.size(); ++i) {
            graphicContext.transformStack.add(((TransformStackElement)this.transformStack.get(i)).clone());
        }
        graphicContext.transformStackValid = this.transformStackValid;
        graphicContext.paint = this.paint;
        graphicContext.stroke = this.stroke;
        graphicContext.composite = this.composite;
        if (this.clip != null) {
            graphicContext.clip = new GeneralPath(this.clip);
        }
        else {
            graphicContext.clip = null;
        }
        graphicContext.hints = (RenderingHints)this.hints.clone();
        graphicContext.font = this.font;
        graphicContext.background = this.background;
        graphicContext.foreground = this.foreground;
        return graphicContext;
    }
    
    public Color getColor() {
        return this.foreground;
    }
    
    public void setColor(final Color paint) {
        if (paint == null) {
            return;
        }
        if (this.paint != paint) {
            this.setPaint(paint);
        }
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public void setFont(final Font font) {
        if (font != null) {
            this.font = font;
        }
    }
    
    public Rectangle getClipBounds() {
        final Shape clip = this.getClip();
        if (clip == null) {
            return null;
        }
        return clip.getBounds();
    }
    
    public void clipRect(final int x, final int y, final int width, final int height) {
        this.clip(new Rectangle(x, y, width, height));
    }
    
    public void setClip(final int x, final int y, final int width, final int height) {
        this.setClip(new Rectangle(x, y, width, height));
    }
    
    public Shape getClip() {
        try {
            return this.transform.createInverse().createTransformedShape(this.clip);
        }
        catch (NoninvertibleTransformException ex) {
            return null;
        }
    }
    
    public void setClip(final Shape pSrc) {
        if (pSrc != null) {
            this.clip = this.transform.createTransformedShape(pSrc);
        }
        else {
            this.clip = null;
        }
    }
    
    public void setComposite(final Composite composite) {
        this.composite = composite;
    }
    
    public void setPaint(final Paint paint) {
        if (paint == null) {
            return;
        }
        this.paint = paint;
        if (paint instanceof Color) {
            this.foreground = (Color)paint;
        }
    }
    
    public void setStroke(final Stroke stroke) {
        this.stroke = stroke;
    }
    
    public void setRenderingHint(final RenderingHints.Key key, final Object value) {
        this.hints.put(key, value);
    }
    
    public Object getRenderingHint(final RenderingHints.Key key) {
        return this.hints.get(key);
    }
    
    public void setRenderingHints(final Map init) {
        this.hints = new RenderingHints(init);
    }
    
    public void addRenderingHints(final Map m) {
        this.hints.putAll(m);
    }
    
    public RenderingHints getRenderingHints() {
        return this.hints;
    }
    
    public void translate(final int n, final int n2) {
        if (n != 0 || n2 != 0) {
            this.transform.translate(n, n2);
            this.transformStack.add(TransformStackElement.createTranslateElement(n, n2));
        }
    }
    
    public void translate(final double tx, final double ty) {
        this.transform.translate(tx, ty);
        this.transformStack.add(TransformStackElement.createTranslateElement(tx, ty));
    }
    
    public void rotate(final double theta) {
        this.transform.rotate(theta);
        this.transformStack.add(TransformStackElement.createRotateElement(theta));
    }
    
    public void rotate(final double theta, final double anchorx, final double anchory) {
        this.transform.rotate(theta, anchorx, anchory);
        this.transformStack.add(TransformStackElement.createTranslateElement(anchorx, anchory));
        this.transformStack.add(TransformStackElement.createRotateElement(theta));
        this.transformStack.add(TransformStackElement.createTranslateElement(-anchorx, -anchory));
    }
    
    public void scale(final double sx, final double sy) {
        this.transform.scale(sx, sy);
        this.transformStack.add(TransformStackElement.createScaleElement(sx, sy));
    }
    
    public void shear(final double shx, final double shy) {
        this.transform.shear(shx, shy);
        this.transformStack.add(TransformStackElement.createShearElement(shx, shy));
    }
    
    public void transform(final AffineTransform tx) {
        this.transform.concatenate(tx);
        this.transformStack.add(TransformStackElement.createGeneralTransformElement(tx));
    }
    
    public void setTransform(final AffineTransform tx) {
        this.transform = new AffineTransform(tx);
        this.invalidateTransformStack();
        if (!tx.isIdentity()) {
            this.transformStack.add(TransformStackElement.createGeneralTransformElement(tx));
        }
    }
    
    public void validateTransformStack() {
        this.transformStackValid = true;
    }
    
    public boolean isTransformStackValid() {
        return this.transformStackValid;
    }
    
    public TransformStackElement[] getTransformStack() {
        final TransformStackElement[] array = new TransformStackElement[this.transformStack.size()];
        this.transformStack.toArray(array);
        return array;
    }
    
    protected void invalidateTransformStack() {
        this.transformStack.clear();
        this.transformStackValid = false;
    }
    
    public AffineTransform getTransform() {
        return new AffineTransform(this.transform);
    }
    
    public Paint getPaint() {
        return this.paint;
    }
    
    public Composite getComposite() {
        return this.composite;
    }
    
    public void setBackground(final Color background) {
        if (background == null) {
            return;
        }
        this.background = background;
    }
    
    public Color getBackground() {
        return this.background;
    }
    
    public Stroke getStroke() {
        return this.stroke;
    }
    
    public void clip(Shape transformedShape) {
        if (transformedShape != null) {
            transformedShape = this.transform.createTransformedShape(transformedShape);
        }
        if (this.clip != null) {
            final Area s = new Area(this.clip);
            s.intersect(new Area(transformedShape));
            this.clip = new GeneralPath(s);
        }
        else {
            this.clip = transformedShape;
        }
    }
    
    public FontRenderContext getFontRenderContext() {
        final Object value = this.hints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
        boolean isAntiAliased = true;
        if (value != RenderingHints.VALUE_TEXT_ANTIALIAS_ON && value != RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT) {
            if (value != RenderingHints.VALUE_TEXT_ANTIALIAS_OFF) {
                final Object value2 = this.hints.get(RenderingHints.KEY_ANTIALIASING);
                if (value2 != RenderingHints.VALUE_ANTIALIAS_ON && value2 != RenderingHints.VALUE_ANTIALIAS_DEFAULT && value2 == RenderingHints.VALUE_ANTIALIAS_OFF) {
                    isAntiAliased = false;
                }
            }
            else {
                isAntiAliased = false;
            }
        }
        boolean usesFractionalMetrics = true;
        if (this.hints.get(RenderingHints.KEY_FRACTIONALMETRICS) == RenderingHints.VALUE_FRACTIONALMETRICS_OFF) {
            usesFractionalMetrics = false;
        }
        return new FontRenderContext(this.defaultTransform, isAntiAliased, usesFractionalMetrics);
    }
}
