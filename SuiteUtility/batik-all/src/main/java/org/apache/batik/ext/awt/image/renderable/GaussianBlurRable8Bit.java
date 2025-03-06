// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.rendered.AbstractRed;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.Shape;
import org.apache.batik.ext.awt.image.rendered.GaussianBlurRed8Bit;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Map;

public class GaussianBlurRable8Bit extends AbstractColorInterpolationRable implements GaussianBlurRable
{
    private double stdDeviationX;
    private double stdDeviationY;
    static final double DSQRT2PI;
    public static final double eps = 1.0E-4;
    
    public GaussianBlurRable8Bit(final Filter filter, final double stdDeviationX, final double stdDeviationY) {
        super(filter, null);
        this.setStdDeviationX(stdDeviationX);
        this.setStdDeviationY(stdDeviationY);
    }
    
    public void setStdDeviationX(final double stdDeviationX) {
        if (stdDeviationX < 0.0) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.stdDeviationX = stdDeviationX;
    }
    
    public void setStdDeviationY(final double stdDeviationY) {
        if (stdDeviationY < 0.0) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.stdDeviationY = stdDeviationY;
    }
    
    public double getStdDeviationX() {
        return this.stdDeviationX;
    }
    
    public double getStdDeviationY() {
        return this.stdDeviationY;
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Rectangle2D getBounds2D() {
        final Rectangle2D bounds2D = this.getSource().getBounds2D();
        final float n = (float)(this.stdDeviationX * GaussianBlurRable8Bit.DSQRT2PI);
        final float n2 = (float)(this.stdDeviationY * GaussianBlurRable8Bit.DSQRT2PI);
        final float n3 = 3.0f * n / 2.0f;
        final float n4 = 3.0f * n2 / 2.0f;
        return new Rectangle2D.Float((float)(bounds2D.getMinX() - n3), (float)(bounds2D.getMinY() - n4), (float)(bounds2D.getWidth() + 2.0f * n3), (float)(bounds2D.getHeight() + 2.0f * n4));
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public static boolean eps_eq(final double n, final double n2) {
        return n >= n2 - 1.0E-4 && n <= n2 + 1.0E-4;
    }
    
    public static boolean eps_abs_eq(double n, double n2) {
        if (n < 0.0) {
            n = -n;
        }
        if (n2 < 0.0) {
            n2 = -n2;
        }
        return eps_eq(n, n2);
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        RenderingHints renderingHints = renderContext.getRenderingHints();
        if (renderingHints == null) {
            renderingHints = new RenderingHints(null);
        }
        final AffineTransform transform = renderContext.getTransform();
        final double scaleX = transform.getScaleX();
        final double scaleY = transform.getScaleY();
        final double shearX = transform.getShearX();
        final double shearY = transform.getShearY();
        final double translateX = transform.getTranslateX();
        final double translateY = transform.getTranslateY();
        double sqrt = Math.sqrt(scaleX * scaleX + shearY * shearY);
        double sqrt2 = Math.sqrt(scaleY * scaleY + shearX * shearX);
        double n = this.stdDeviationX * sqrt;
        double n2 = this.stdDeviationY * sqrt2;
        AffineTransform scaleInstance;
        AffineTransform affineTransform;
        int n3;
        int n4;
        if (n < 10.0 && n2 < 10.0 && eps_eq(n, n2) && eps_abs_eq(scaleX / sqrt, scaleY / sqrt2)) {
            scaleInstance = transform;
            affineTransform = null;
            n3 = 0;
            n4 = 0;
        }
        else {
            if (n > 10.0) {
                sqrt = sqrt * 10.0 / n;
                n = 10.0;
            }
            if (n2 > 10.0) {
                sqrt2 = sqrt2 * 10.0 / n2;
                n2 = 10.0;
            }
            scaleInstance = AffineTransform.getScaleInstance(sqrt, sqrt2);
            affineTransform = new AffineTransform(scaleX / sqrt, shearY / sqrt, shearX / sqrt2, scaleY / sqrt2, translateX, translateY);
            n3 = 1;
            n4 = 1;
        }
        Shape pSrc = renderContext.getAreaOfInterest();
        if (pSrc == null) {
            pSrc = this.getBounds2D();
        }
        final Rectangle bounds = scaleInstance.createTransformedShape(pSrc).getBounds();
        final int n5 = n3 + GaussianBlurRed8Bit.surroundPixels(n, renderingHints);
        final int n6 = n4 + GaussianBlurRed8Bit.surroundPixels(n2, renderingHints);
        final Rectangle rectangle = bounds;
        rectangle.x -= n5;
        final Rectangle rectangle2 = bounds;
        rectangle2.y -= n6;
        final Rectangle rectangle3 = bounds;
        rectangle3.width += 2 * n5;
        final Rectangle rectangle4 = bounds;
        rectangle4.height += 2 * n6;
        Rectangle2D bounds2D;
        try {
            bounds2D = scaleInstance.createInverse().createTransformedShape(bounds).getBounds2D();
        }
        catch (NoninvertibleTransformException ex) {
            final Rectangle2D bounds2D2 = pSrc.getBounds2D();
            bounds2D = new Rectangle2D.Double(bounds2D2.getX() - n5 / sqrt, bounds2D2.getY() - n6 / sqrt2, bounds2D2.getWidth() + 2 * n5 / sqrt, bounds2D2.getHeight() + 2 * n6 / sqrt2);
        }
        final RenderedImage rendering = this.getSource().createRendering(new RenderContext(scaleInstance, bounds2D, renderingHints));
        if (rendering == null) {
            return null;
        }
        CachableRed convertSourceCS = this.convertSourceCS(rendering);
        if (!bounds.equals(convertSourceCS.getBounds())) {
            convertSourceCS = new PadRed(convertSourceCS, bounds, PadMode.ZERO_PAD, renderingHints);
        }
        AbstractRed abstractRed = new GaussianBlurRed8Bit(convertSourceCS, n, n2, renderingHints);
        if (affineTransform != null && !affineTransform.isIdentity()) {
            abstractRed = new AffineRed(abstractRed, affineTransform, renderingHints);
        }
        return abstractRed;
    }
    
    public Shape getDependencyRegion(final int n, Rectangle2D intersection) {
        if (n != 0) {
            intersection = null;
        }
        else {
            final float n2 = (float)(this.stdDeviationX * GaussianBlurRable8Bit.DSQRT2PI);
            final float n3 = (float)(this.stdDeviationY * GaussianBlurRable8Bit.DSQRT2PI);
            final float n4 = 3.0f * n2 / 2.0f;
            final float n5 = 3.0f * n3 / 2.0f;
            final Rectangle2D.Float float1 = new Rectangle2D.Float((float)(intersection.getMinX() - n4), (float)(intersection.getMinY() - n5), (float)(intersection.getWidth() + 2.0f * n4), (float)(intersection.getHeight() + 2.0f * n5));
            final Rectangle2D bounds2D = this.getBounds2D();
            if (!float1.intersects(bounds2D)) {
                return new Rectangle2D.Float();
            }
            intersection = float1.createIntersection(bounds2D);
        }
        return intersection;
    }
    
    public Shape getDirtyRegion(final int n, final Rectangle2D rectangle2D) {
        Shape intersection = null;
        if (n == 0) {
            final float n2 = (float)(this.stdDeviationX * GaussianBlurRable8Bit.DSQRT2PI);
            final float n3 = (float)(this.stdDeviationY * GaussianBlurRable8Bit.DSQRT2PI);
            final float n4 = 3.0f * n2 / 2.0f;
            final float n5 = 3.0f * n3 / 2.0f;
            final Rectangle2D.Float float1 = new Rectangle2D.Float((float)(rectangle2D.getMinX() - n4), (float)(rectangle2D.getMinY() - n5), (float)(rectangle2D.getWidth() + 2.0f * n4), (float)(rectangle2D.getHeight() + 2.0f * n5));
            final Rectangle2D bounds2D = this.getBounds2D();
            if (!float1.intersects(bounds2D)) {
                return new Rectangle2D.Float();
            }
            intersection = float1.createIntersection(bounds2D);
        }
        return intersection;
    }
    
    static {
        DSQRT2PI = Math.sqrt(6.283185307179586) * 3.0 / 4.0;
    }
}
