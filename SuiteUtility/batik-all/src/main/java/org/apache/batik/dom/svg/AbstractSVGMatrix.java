// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGException;
import java.awt.geom.NoninvertibleTransformException;
import org.w3c.dom.DOMException;
import java.awt.geom.AffineTransform;
import org.w3c.dom.svg.SVGMatrix;

public abstract class AbstractSVGMatrix implements SVGMatrix
{
    protected static final AffineTransform FLIP_X_TRANSFORM;
    protected static final AffineTransform FLIP_Y_TRANSFORM;
    
    protected abstract AffineTransform getAffineTransform();
    
    public float getA() {
        return (float)this.getAffineTransform().getScaleX();
    }
    
    public void setA(final float n) throws DOMException {
        final AffineTransform affineTransform = this.getAffineTransform();
        affineTransform.setTransform(n, affineTransform.getShearY(), affineTransform.getShearX(), affineTransform.getScaleY(), affineTransform.getTranslateX(), affineTransform.getTranslateY());
    }
    
    public float getB() {
        return (float)this.getAffineTransform().getShearY();
    }
    
    public void setB(final float n) throws DOMException {
        final AffineTransform affineTransform = this.getAffineTransform();
        affineTransform.setTransform(affineTransform.getScaleX(), n, affineTransform.getShearX(), affineTransform.getScaleY(), affineTransform.getTranslateX(), affineTransform.getTranslateY());
    }
    
    public float getC() {
        return (float)this.getAffineTransform().getShearX();
    }
    
    public void setC(final float n) throws DOMException {
        final AffineTransform affineTransform = this.getAffineTransform();
        affineTransform.setTransform(affineTransform.getScaleX(), affineTransform.getShearY(), n, affineTransform.getScaleY(), affineTransform.getTranslateX(), affineTransform.getTranslateY());
    }
    
    public float getD() {
        return (float)this.getAffineTransform().getScaleY();
    }
    
    public void setD(final float n) throws DOMException {
        final AffineTransform affineTransform = this.getAffineTransform();
        affineTransform.setTransform(affineTransform.getScaleX(), affineTransform.getShearY(), affineTransform.getShearX(), n, affineTransform.getTranslateX(), affineTransform.getTranslateY());
    }
    
    public float getE() {
        return (float)this.getAffineTransform().getTranslateX();
    }
    
    public void setE(final float n) throws DOMException {
        final AffineTransform affineTransform = this.getAffineTransform();
        affineTransform.setTransform(affineTransform.getScaleX(), affineTransform.getShearY(), affineTransform.getShearX(), affineTransform.getScaleY(), n, affineTransform.getTranslateY());
    }
    
    public float getF() {
        return (float)this.getAffineTransform().getTranslateY();
    }
    
    public void setF(final float n) throws DOMException {
        final AffineTransform affineTransform = this.getAffineTransform();
        affineTransform.setTransform(affineTransform.getScaleX(), affineTransform.getShearY(), affineTransform.getShearX(), affineTransform.getScaleY(), affineTransform.getTranslateX(), n);
    }
    
    public SVGMatrix multiply(final SVGMatrix svgMatrix) {
        final AffineTransform tx = new AffineTransform(svgMatrix.getA(), svgMatrix.getB(), svgMatrix.getC(), svgMatrix.getD(), svgMatrix.getE(), svgMatrix.getF());
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.concatenate(tx);
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix inverse() throws SVGException {
        try {
            return (SVGMatrix)new SVGOMMatrix(this.getAffineTransform().createInverse());
        }
        catch (NoninvertibleTransformException ex) {
            throw new SVGOMException((short)2, ex.getMessage());
        }
    }
    
    public SVGMatrix translate(final float n, final float n2) {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.translate(n, n2);
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix scale(final float n) {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.scale(n, n);
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix scaleNonUniform(final float n, final float n2) {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.scale(n, n2);
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix rotate(final float n) {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.rotate(Math.toRadians(n));
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix rotateFromVector(final float n, final float n2) throws SVGException {
        if (n == 0.0f || n2 == 0.0f) {
            throw new SVGOMException((short)1, "");
        }
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.rotate(Math.atan2(n2, n));
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix flipX() {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.concatenate(AbstractSVGMatrix.FLIP_X_TRANSFORM);
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix flipY() {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.concatenate(AbstractSVGMatrix.FLIP_Y_TRANSFORM);
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix skewX(final float n) {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.concatenate(AffineTransform.getShearInstance(Math.tan(Math.toRadians(n)), 0.0));
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    public SVGMatrix skewY(final float n) {
        final AffineTransform affineTransform = (AffineTransform)this.getAffineTransform().clone();
        affineTransform.concatenate(AffineTransform.getShearInstance(0.0, Math.tan(Math.toRadians(n))));
        return (SVGMatrix)new SVGOMMatrix(affineTransform);
    }
    
    static {
        FLIP_X_TRANSFORM = new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
        FLIP_Y_TRANSFORM = new AffineTransform(1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f);
    }
}
