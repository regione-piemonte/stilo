// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Collection;
import java.util.List;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.text.TextPaintInfo;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Glyph
{
    private String unicode;
    private Vector names;
    private String orientation;
    private String arabicForm;
    private String lang;
    private Point2D horizOrigin;
    private Point2D vertOrigin;
    private float horizAdvX;
    private float vertAdvY;
    private int glyphCode;
    private AffineTransform transform;
    private Point2D.Float position;
    private GVTGlyphMetrics metrics;
    private Shape outline;
    private Rectangle2D bounds;
    private TextPaintInfo tpi;
    private TextPaintInfo cacheTPI;
    private Shape dShape;
    private GraphicsNode glyphChildrenNode;
    
    public Glyph(final String unicode, final List c, final String orientation, final String arabicForm, final String lang, final Point2D horizOrigin, final Point2D vertOrigin, final float horizAdvX, final float vertAdvY, final int glyphCode, final TextPaintInfo tpi, final Shape dShape, final GraphicsNode glyphChildrenNode) {
        if (unicode == null) {
            throw new IllegalArgumentException();
        }
        if (horizOrigin == null) {
            throw new IllegalArgumentException();
        }
        if (vertOrigin == null) {
            throw new IllegalArgumentException();
        }
        this.unicode = unicode;
        this.names = new Vector(c);
        this.orientation = orientation;
        this.arabicForm = arabicForm;
        this.lang = lang;
        this.horizOrigin = horizOrigin;
        this.vertOrigin = vertOrigin;
        this.horizAdvX = horizAdvX;
        this.vertAdvY = vertAdvY;
        this.glyphCode = glyphCode;
        this.position = new Point2D.Float(0.0f, 0.0f);
        this.outline = null;
        this.bounds = null;
        this.tpi = tpi;
        this.dShape = dShape;
        this.glyphChildrenNode = glyphChildrenNode;
    }
    
    public String getUnicode() {
        return this.unicode;
    }
    
    public Vector getNames() {
        return this.names;
    }
    
    public String getOrientation() {
        return this.orientation;
    }
    
    public String getArabicForm() {
        return this.arabicForm;
    }
    
    public String getLang() {
        return this.lang;
    }
    
    public Point2D getHorizOrigin() {
        return this.horizOrigin;
    }
    
    public Point2D getVertOrigin() {
        return this.vertOrigin;
    }
    
    public float getHorizAdvX() {
        return this.horizAdvX;
    }
    
    public float getVertAdvY() {
        return this.vertAdvY;
    }
    
    public int getGlyphCode() {
        return this.glyphCode;
    }
    
    public AffineTransform getTransform() {
        return this.transform;
    }
    
    public void setTransform(final AffineTransform transform) {
        this.transform = transform;
        this.outline = null;
        this.bounds = null;
    }
    
    public Point2D getPosition() {
        return this.position;
    }
    
    public void setPosition(final Point2D point2D) {
        this.position.x = (float)point2D.getX();
        this.position.y = (float)point2D.getY();
        this.outline = null;
        this.bounds = null;
    }
    
    public GVTGlyphMetrics getGlyphMetrics() {
        if (this.metrics == null) {
            final Rectangle2D geometryBounds = this.getGeometryBounds();
            this.metrics = new GVTGlyphMetrics(this.getHorizAdvX(), this.getVertAdvY(), new Rectangle2D.Double(geometryBounds.getX() - this.position.getX(), geometryBounds.getY() - this.position.getY(), geometryBounds.getWidth(), geometryBounds.getHeight()), (byte)3);
        }
        return this.metrics;
    }
    
    public GVTGlyphMetrics getGlyphMetrics(final float n, final float n2) {
        return new GVTGlyphMetrics(this.getHorizAdvX() - n, this.getVertAdvY() - n2, this.getGeometryBounds(), (byte)3);
    }
    
    public Rectangle2D getGeometryBounds() {
        return this.getOutline().getBounds2D();
    }
    
    public Rectangle2D getBounds2D() {
        if (this.bounds != null && TextPaintInfo.equivilent(this.tpi, this.cacheTPI)) {
            return this.bounds;
        }
        final AffineTransform translateInstance = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY());
        if (this.transform != null) {
            translateInstance.concatenate(this.transform);
        }
        Rectangle2D bounds2D = null;
        if (this.dShape != null && this.tpi != null) {
            if (this.tpi.fillPaint != null) {
                bounds2D = translateInstance.createTransformedShape(this.dShape).getBounds2D();
            }
            if (this.tpi.strokeStroke != null && this.tpi.strokePaint != null) {
                final Rectangle2D bounds2D2 = translateInstance.createTransformedShape(this.tpi.strokeStroke.createStrokedShape(this.dShape)).getBounds2D();
                if (bounds2D == null) {
                    bounds2D = bounds2D2;
                }
                else {
                    bounds2D.add(bounds2D2);
                }
            }
        }
        if (this.glyphChildrenNode != null) {
            final Rectangle2D transformedBounds = this.glyphChildrenNode.getTransformedBounds(translateInstance);
            if (bounds2D == null) {
                bounds2D = transformedBounds;
            }
            else {
                bounds2D.add(transformedBounds);
            }
        }
        if (bounds2D == null) {
            bounds2D = new Rectangle2D.Double(this.position.getX(), this.position.getY(), 0.0, 0.0);
        }
        this.cacheTPI = new TextPaintInfo(this.tpi);
        return bounds2D;
    }
    
    public Shape getOutline() {
        if (this.outline == null) {
            final AffineTransform translateInstance = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY());
            if (this.transform != null) {
                translateInstance.concatenate(this.transform);
            }
            Shape outline = null;
            if (this.glyphChildrenNode != null) {
                outline = this.glyphChildrenNode.getOutline();
            }
            GeneralPath pSrc;
            if (this.dShape != null && outline != null) {
                pSrc = new GeneralPath(this.dShape);
                pSrc.append(outline, false);
            }
            else if (this.dShape != null && outline == null) {
                pSrc = new GeneralPath(this.dShape);
            }
            else if (this.dShape == null && outline != null) {
                pSrc = new GeneralPath(outline);
            }
            else {
                pSrc = new GeneralPath();
            }
            this.outline = translateInstance.createTransformedShape(pSrc);
        }
        return this.outline;
    }
    
    public void draw(final Graphics2D graphics2D) {
        final AffineTransform translateInstance = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY());
        if (this.transform != null) {
            translateInstance.concatenate(this.transform);
        }
        if (this.dShape != null && this.tpi != null) {
            final Shape transformedShape = translateInstance.createTransformedShape(this.dShape);
            if (this.tpi.fillPaint != null) {
                graphics2D.setPaint(this.tpi.fillPaint);
                graphics2D.fill(transformedShape);
            }
            if (this.tpi.strokeStroke != null && this.tpi.strokePaint != null) {
                graphics2D.setStroke(this.tpi.strokeStroke);
                graphics2D.setPaint(this.tpi.strokePaint);
                graphics2D.draw(transformedShape);
            }
        }
        if (this.glyphChildrenNode != null) {
            this.glyphChildrenNode.setTransform(translateInstance);
            this.glyphChildrenNode.paint(graphics2D);
        }
    }
}
