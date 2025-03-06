// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGPoint;

public class SVGOMPoint implements SVGPoint
{
    protected float x;
    protected float y;
    
    public SVGOMPoint() {
    }
    
    public SVGOMPoint(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) throws DOMException {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) throws DOMException {
        this.y = y;
    }
    
    public SVGPoint matrixTransform(final SVGMatrix svgMatrix) {
        return matrixTransform((SVGPoint)this, svgMatrix);
    }
    
    public static SVGPoint matrixTransform(final SVGPoint svgPoint, final SVGMatrix svgMatrix) {
        return (SVGPoint)new SVGOMPoint(svgMatrix.getA() * svgPoint.getX() + svgMatrix.getC() * svgPoint.getY() + svgMatrix.getE(), svgMatrix.getB() * svgPoint.getX() + svgMatrix.getD() * svgPoint.getY() + svgMatrix.getF());
    }
}
