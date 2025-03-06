// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGPoint;

public class SVGPathSupport
{
    public static float getTotalLength(final SVGOMPathElement svgomPathElement) {
        return ((SVGPathContext)svgomPathElement.getSVGContext()).getTotalLength();
    }
    
    public static int getPathSegAtLength(final SVGOMPathElement svgomPathElement, final float n) {
        return ((SVGPathContext)svgomPathElement.getSVGContext()).getPathSegAtLength(n);
    }
    
    public static SVGPoint getPointAtLength(final SVGOMPathElement svgomPathElement, final float n) {
        final SVGPathContext svgPathContext = (SVGPathContext)svgomPathElement.getSVGContext();
        if (svgPathContext == null) {
            return null;
        }
        return (SVGPoint)new SVGPoint() {
            public float getX() {
                return (float)svgPathContext.getPointAtLength(n).getX();
            }
            
            public float getY() {
                return (float)svgPathContext.getPointAtLength(n).getY();
            }
            
            public void setX(final float n) throws DOMException {
                throw svgomPathElement.createDOMException((short)7, "readonly.point", null);
            }
            
            public void setY(final float n) throws DOMException {
                throw svgomPathElement.createDOMException((short)7, "readonly.point", null);
            }
            
            public SVGPoint matrixTransform(final SVGMatrix svgMatrix) {
                throw svgomPathElement.createDOMException((short)7, "readonly.point", null);
            }
        };
    }
}
