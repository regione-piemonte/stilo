// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.rendered.AbstractRed;
import org.apache.batik.ext.awt.image.rendered.AffineRed;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.rendered.SpecularLightingRed;
import org.apache.batik.ext.awt.image.rendered.BumpMap;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.util.Map;
import java.awt.geom.Rectangle2D;
import org.apache.batik.ext.awt.image.Light;

public class SpecularLightingRable8Bit extends AbstractColorInterpolationRable implements SpecularLightingRable
{
    private double surfaceScale;
    private double ks;
    private double specularExponent;
    private Light light;
    private Rectangle2D litRegion;
    private float[] kernelUnitLength;
    
    public SpecularLightingRable8Bit(final Filter filter, final Rectangle2D litRegion, final Light light, final double ks, final double specularExponent, final double surfaceScale, final double[] kernelUnitLength) {
        super(filter, null);
        this.kernelUnitLength = null;
        this.setLight(light);
        this.setKs(ks);
        this.setSpecularExponent(specularExponent);
        this.setSurfaceScale(surfaceScale);
        this.setLitRegion(litRegion);
        this.setKernelUnitLength(kernelUnitLength);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Rectangle2D getBounds2D() {
        return (Rectangle2D)this.litRegion.clone();
    }
    
    public Rectangle2D getLitRegion() {
        return this.getBounds2D();
    }
    
    public void setLitRegion(final Rectangle2D litRegion) {
        this.touch();
        this.litRegion = litRegion;
    }
    
    public Light getLight() {
        return this.light;
    }
    
    public void setLight(final Light light) {
        this.touch();
        this.light = light;
    }
    
    public double getSurfaceScale() {
        return this.surfaceScale;
    }
    
    public void setSurfaceScale(final double surfaceScale) {
        this.touch();
        this.surfaceScale = surfaceScale;
    }
    
    public double getKs() {
        return this.ks;
    }
    
    public void setKs(final double ks) {
        this.touch();
        this.ks = ks;
    }
    
    public double getSpecularExponent() {
        return this.specularExponent;
    }
    
    public void setSpecularExponent(final double specularExponent) {
        this.touch();
        this.specularExponent = specularExponent;
    }
    
    public double[] getKernelUnitLength() {
        if (this.kernelUnitLength == null) {
            return null;
        }
        return new double[] { this.kernelUnitLength[0], this.kernelUnitLength[1] };
    }
    
    public void setKernelUnitLength(final double[] array) {
        this.touch();
        if (array == null) {
            this.kernelUnitLength = null;
            return;
        }
        if (this.kernelUnitLength == null) {
            this.kernelUnitLength = new float[2];
        }
        this.kernelUnitLength[0] = (float)array[0];
        this.kernelUnitLength[1] = (float)array[1];
    }
    
    public RenderedImage createRendering(RenderContext renderContext) {
        Shape shape = renderContext.getAreaOfInterest();
        if (shape == null) {
            shape = this.getBounds2D();
        }
        final Rectangle2D bounds2D = shape.getBounds2D();
        Rectangle2D.intersect(bounds2D, this.getBounds2D(), bounds2D);
        final AffineTransform transform = renderContext.getTransform();
        final Rectangle bounds = transform.createTransformedShape(bounds2D).getBounds();
        if (bounds.width == 0 || bounds.height == 0) {
            return null;
        }
        final double scaleX = transform.getScaleX();
        final double scaleY = transform.getScaleY();
        final double shearX = transform.getShearX();
        final double shearY = transform.getShearY();
        final double translateX = transform.getTranslateX();
        final double translateY = transform.getTranslateY();
        double sqrt = Math.sqrt(scaleX * scaleX + shearY * shearY);
        double sqrt2 = Math.sqrt(scaleY * scaleY + shearX * shearX);
        if (sqrt == 0.0 || sqrt2 == 0.0) {
            return null;
        }
        if (this.kernelUnitLength != null) {
            if (sqrt >= 1.0f / this.kernelUnitLength[0]) {
                sqrt = 1.0f / this.kernelUnitLength[0];
            }
            if (sqrt2 >= 1.0f / this.kernelUnitLength[1]) {
                sqrt2 = 1.0f / this.kernelUnitLength[1];
            }
        }
        final AffineTransform scaleInstance = AffineTransform.getScaleInstance(sqrt, sqrt2);
        final Rectangle bounds2 = scaleInstance.createTransformedShape(bounds2D).getBounds();
        bounds2D.setRect(bounds2D.getX() - 2.0 / sqrt, bounds2D.getY() - 2.0 / sqrt2, bounds2D.getWidth() + 4.0 / sqrt, bounds2D.getHeight() + 4.0 / sqrt2);
        renderContext = (RenderContext)renderContext.clone();
        renderContext.setAreaOfInterest(bounds2D);
        renderContext.setTransform(scaleInstance);
        AbstractRed abstractRed = new SpecularLightingRed(this.ks, this.specularExponent, this.light, new BumpMap(GraphicsUtil.wrap(this.getSource().createRendering(renderContext)), this.surfaceScale, sqrt, sqrt2), bounds2, 1.0 / sqrt, 1.0 / sqrt2, this.isColorSpaceLinear());
        final AffineTransform affineTransform = new AffineTransform(scaleX / sqrt, shearY / sqrt, shearX / sqrt2, scaleY / sqrt2, translateX, translateY);
        if (!affineTransform.isIdentity()) {
            final RenderingHints renderingHints = renderContext.getRenderingHints();
            abstractRed = new AffineRed(new PadRed(abstractRed, new Rectangle(bounds2.x - 1, bounds2.y - 1, bounds2.width + 2, bounds2.height + 2), PadMode.REPLICATE, renderingHints), affineTransform, renderingHints);
        }
        return abstractRed;
    }
}
