// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGMatrix;
import java.awt.geom.AffineTransform;
import org.w3c.dom.svg.SVGTransform;

public abstract class AbstractSVGTransform implements SVGTransform
{
    protected short type;
    protected AffineTransform affineTransform;
    protected float angle;
    protected float x;
    protected float y;
    
    public AbstractSVGTransform() {
        this.type = 0;
    }
    
    protected abstract SVGMatrix createMatrix();
    
    protected void setType(final short type) {
        this.type = type;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void assign(final AbstractSVGTransform abstractSVGTransform) {
        this.type = abstractSVGTransform.type;
        this.affineTransform = abstractSVGTransform.affineTransform;
        this.angle = abstractSVGTransform.angle;
        this.x = abstractSVGTransform.x;
        this.y = abstractSVGTransform.y;
    }
    
    public short getType() {
        return this.type;
    }
    
    public SVGMatrix getMatrix() {
        return this.createMatrix();
    }
    
    public float getAngle() {
        return this.angle;
    }
    
    public void setMatrix(final SVGMatrix svgMatrix) {
        this.type = 1;
        this.affineTransform = new AffineTransform(svgMatrix.getA(), svgMatrix.getB(), svgMatrix.getC(), svgMatrix.getD(), svgMatrix.getE(), svgMatrix.getF());
    }
    
    public void setTranslate(final float n, final float n2) {
        this.type = 2;
        this.affineTransform = AffineTransform.getTranslateInstance(n, n2);
    }
    
    public void setScale(final float n, final float n2) {
        this.type = 3;
        this.affineTransform = AffineTransform.getScaleInstance(n, n2);
    }
    
    public void setRotate(final float angle, final float x, final float y) {
        this.type = 4;
        this.affineTransform = AffineTransform.getRotateInstance(Math.toRadians(angle), x, y);
        this.angle = angle;
        this.x = x;
        this.y = y;
    }
    
    public void setSkewX(final float angle) {
        this.type = 5;
        this.affineTransform = AffineTransform.getShearInstance(Math.tan(Math.toRadians(angle)), 0.0);
        this.angle = angle;
    }
    
    public void setSkewY(final float angle) {
        this.type = 6;
        this.affineTransform = AffineTransform.getShearInstance(0.0, Math.tan(Math.toRadians(angle)));
        this.angle = angle;
    }
}
