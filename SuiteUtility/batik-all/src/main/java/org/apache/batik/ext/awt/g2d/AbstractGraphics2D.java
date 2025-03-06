// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.g2d;

import java.awt.font.FontRenderContext;
import java.util.Map;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImageOp;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.text.AttributedCharacterIterator;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.Paint;
import java.awt.geom.Line2D;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class AbstractGraphics2D extends Graphics2D implements Cloneable
{
    protected GraphicContext gc;
    protected boolean textAsShapes;
    
    public AbstractGraphics2D(final boolean textAsShapes) {
        this.textAsShapes = false;
        this.textAsShapes = textAsShapes;
    }
    
    public AbstractGraphics2D(final AbstractGraphics2D abstractGraphics2D) {
        this.textAsShapes = false;
        (this.gc = (GraphicContext)abstractGraphics2D.gc.clone()).validateTransformStack();
        this.textAsShapes = abstractGraphics2D.textAsShapes;
    }
    
    public void translate(final int n, final int n2) {
        this.gc.translate(n, n2);
    }
    
    public Color getColor() {
        return this.gc.getColor();
    }
    
    public void setColor(final Color color) {
        this.gc.setColor(color);
    }
    
    public void setPaintMode() {
        this.gc.setComposite(AlphaComposite.SrcOver);
    }
    
    public Font getFont() {
        return this.gc.getFont();
    }
    
    public void setFont(final Font font) {
        this.gc.setFont(font);
    }
    
    public Rectangle getClipBounds() {
        return this.gc.getClipBounds();
    }
    
    public void clipRect(final int n, final int n2, final int n3, final int n4) {
        this.gc.clipRect(n, n2, n3, n4);
    }
    
    public void setClip(final int n, final int n2, final int n3, final int n4) {
        this.gc.setClip(n, n2, n3, n4);
    }
    
    public Shape getClip() {
        return this.gc.getClip();
    }
    
    public void setClip(final Shape clip) {
        this.gc.setClip(clip);
    }
    
    public void drawLine(final int n, final int n2, final int n3, final int n4) {
        this.draw(new Line2D.Float((float)n, (float)n2, (float)n3, (float)n4));
    }
    
    public void fillRect(final int x, final int y, final int width, final int height) {
        this.fill(new Rectangle(x, y, width, height));
    }
    
    public void drawRect(final int x, final int y, final int width, final int height) {
        this.draw(new Rectangle(x, y, width, height));
    }
    
    public void clearRect(final int n, final int n2, final int n3, final int n4) {
        final Paint paint = this.gc.getPaint();
        this.gc.setColor(this.gc.getBackground());
        this.fillRect(n, n2, n3, n4);
        this.gc.setPaint(paint);
    }
    
    public void drawRoundRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.draw(new RoundRectangle2D.Float((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6));
    }
    
    public void fillRoundRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.fill(new RoundRectangle2D.Float((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6));
    }
    
    public void drawOval(final int n, final int n2, final int n3, final int n4) {
        this.draw(new Ellipse2D.Float((float)n, (float)n2, (float)n3, (float)n4));
    }
    
    public void fillOval(final int n, final int n2, final int n3, final int n4) {
        this.fill(new Ellipse2D.Float((float)n, (float)n2, (float)n3, (float)n4));
    }
    
    public void drawArc(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.draw(new Arc2D.Float((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6, 0));
    }
    
    public void fillArc(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.fill(new Arc2D.Float((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6, 2));
    }
    
    public void drawPolyline(final int[] array, final int[] array2, final int n) {
        if (n > 0) {
            final GeneralPath generalPath = new GeneralPath();
            generalPath.moveTo((float)array[0], (float)array2[0]);
            for (int i = 1; i < n; ++i) {
                generalPath.lineTo((float)array[i], (float)array2[i]);
            }
            this.draw(generalPath);
        }
    }
    
    public void drawPolygon(final int[] xpoints, final int[] ypoints, final int npoints) {
        this.draw(new Polygon(xpoints, ypoints, npoints));
    }
    
    public void fillPolygon(final int[] xpoints, final int[] ypoints, final int npoints) {
        this.fill(new Polygon(xpoints, ypoints, npoints));
    }
    
    public void drawString(final String s, final int n, final int n2) {
        this.drawString(s, (float)n, (float)n2);
    }
    
    public void drawString(final AttributedCharacterIterator attributedCharacterIterator, final int n, final int n2) {
        this.drawString(attributedCharacterIterator, (float)n, (float)n2);
    }
    
    public boolean drawImage(final Image image, final int n, final int n2, final Color color, final ImageObserver imageObserver) {
        return this.drawImage(image, n, n2, image.getWidth(null), image.getHeight(null), color, imageObserver);
    }
    
    public boolean drawImage(final Image image, final int n, final int n2, final int n3, final int n4, final Color paint, final ImageObserver imageObserver) {
        final Paint paint2 = this.gc.getPaint();
        this.gc.setPaint(paint);
        this.fillRect(n, n2, n3, n4);
        this.gc.setPaint(paint2);
        this.drawImage(image, n, n2, n3, n4, imageObserver);
        return true;
    }
    
    public boolean drawImage(final Image image, final int n, final int n2, final int n3, final int n4, final int x, final int y, final int n5, final int n6, final ImageObserver imageObserver) {
        final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
        final Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return this.drawImage(bufferedImage.getSubimage(x, y, n5 - x, n6 - y), n, n2, n3 - n, n4 - n2, imageObserver);
    }
    
    public boolean drawImage(final Image image, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Color paint, final ImageObserver imageObserver) {
        final Paint paint2 = this.gc.getPaint();
        this.gc.setPaint(paint);
        this.fillRect(n, n2, n3 - n, n4 - n2);
        this.gc.setPaint(paint2);
        return this.drawImage(image, n, n2, n3, n4, n5, n6, n7, n8, imageObserver);
    }
    
    public boolean drawImage(final Image image, final AffineTransform affineTransform, final ImageObserver imageObserver) {
        boolean b;
        if (affineTransform.getDeterminant() != 0.0) {
            AffineTransform inverse;
            try {
                inverse = affineTransform.createInverse();
            }
            catch (NoninvertibleTransformException ex) {
                throw new Error(ex.getMessage());
            }
            this.gc.transform(affineTransform);
            b = this.drawImage(image, 0, 0, null);
            this.gc.transform(inverse);
        }
        else {
            final AffineTransform transform = new AffineTransform(this.gc.getTransform());
            this.gc.transform(affineTransform);
            b = this.drawImage(image, 0, 0, null);
            this.gc.setTransform(transform);
        }
        return b;
    }
    
    public void drawImage(BufferedImage filter, final BufferedImageOp bufferedImageOp, final int n, final int n2) {
        filter = bufferedImageOp.filter(filter, null);
        this.drawImage(filter, n, n2, null);
    }
    
    public void drawGlyphVector(final GlyphVector glyphVector, final float n, final float n2) {
        this.fill(glyphVector.getOutline(n, n2));
    }
    
    public boolean hit(final Rectangle rectangle, Shape pSrc, final boolean b) {
        if (b) {
            pSrc = this.gc.getStroke().createStrokedShape(pSrc);
        }
        pSrc = this.gc.getTransform().createTransformedShape(pSrc);
        return pSrc.intersects(rectangle);
    }
    
    public void setComposite(final Composite composite) {
        this.gc.setComposite(composite);
    }
    
    public void setPaint(final Paint paint) {
        this.gc.setPaint(paint);
    }
    
    public void setStroke(final Stroke stroke) {
        this.gc.setStroke(stroke);
    }
    
    public void setRenderingHint(final RenderingHints.Key key, final Object o) {
        this.gc.setRenderingHint(key, o);
    }
    
    public Object getRenderingHint(final RenderingHints.Key key) {
        return this.gc.getRenderingHint(key);
    }
    
    public void setRenderingHints(final Map renderingHints) {
        this.gc.setRenderingHints(renderingHints);
    }
    
    public void addRenderingHints(final Map map) {
        this.gc.addRenderingHints(map);
    }
    
    public RenderingHints getRenderingHints() {
        return this.gc.getRenderingHints();
    }
    
    public void translate(final double n, final double n2) {
        this.gc.translate(n, n2);
    }
    
    public void rotate(final double n) {
        this.gc.rotate(n);
    }
    
    public void rotate(final double n, final double n2, final double n3) {
        this.gc.rotate(n, n2, n3);
    }
    
    public void scale(final double n, final double n2) {
        this.gc.scale(n, n2);
    }
    
    public void shear(final double n, final double n2) {
        this.gc.shear(n, n2);
    }
    
    public void transform(final AffineTransform affineTransform) {
        this.gc.transform(affineTransform);
    }
    
    public void setTransform(final AffineTransform transform) {
        this.gc.setTransform(transform);
    }
    
    public AffineTransform getTransform() {
        return this.gc.getTransform();
    }
    
    public Paint getPaint() {
        return this.gc.getPaint();
    }
    
    public Composite getComposite() {
        return this.gc.getComposite();
    }
    
    public void setBackground(final Color background) {
        this.gc.setBackground(background);
    }
    
    public Color getBackground() {
        return this.gc.getBackground();
    }
    
    public Stroke getStroke() {
        return this.gc.getStroke();
    }
    
    public void clip(final Shape shape) {
        this.gc.clip(shape);
    }
    
    public FontRenderContext getFontRenderContext() {
        return this.gc.getFontRenderContext();
    }
    
    public GraphicContext getGraphicContext() {
        return this.gc;
    }
}
