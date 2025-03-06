// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Element;
import java.awt.geom.Line2D;

public class SVGLine extends SVGGraphicObjectConverter
{
    public SVGLine(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public Element toSVG(final Line2D line2D) {
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "line");
        elementNS.setAttributeNS(null, "x1", this.doubleString(line2D.getX1()));
        elementNS.setAttributeNS(null, "y1", this.doubleString(line2D.getY1()));
        elementNS.setAttributeNS(null, "x2", this.doubleString(line2D.getX2()));
        elementNS.setAttributeNS(null, "y2", this.doubleString(line2D.getY2()));
        return elementNS;
    }
}
