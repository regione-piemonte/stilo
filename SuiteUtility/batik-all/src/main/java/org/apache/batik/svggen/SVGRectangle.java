// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RectangularShape;
import org.w3c.dom.Element;
import java.awt.geom.Rectangle2D;

public class SVGRectangle extends SVGGraphicObjectConverter
{
    private SVGLine svgLine;
    
    public SVGRectangle(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
        this.svgLine = new SVGLine(svgGeneratorContext);
    }
    
    public Element toSVG(final Rectangle2D rectangle2D) {
        return this.toSVG((RectangularShape)rectangle2D);
    }
    
    public Element toSVG(final RoundRectangle2D roundRectangle2D) {
        final Element svg = this.toSVG((RectangularShape)roundRectangle2D);
        if (svg != null && svg.getTagName() == "rect") {
            svg.setAttributeNS(null, "rx", this.doubleString(Math.abs(roundRectangle2D.getArcWidth() / 2.0)));
            svg.setAttributeNS(null, "ry", this.doubleString(Math.abs(roundRectangle2D.getArcHeight() / 2.0)));
        }
        return svg;
    }
    
    private Element toSVG(final RectangularShape rectangularShape) {
        if (rectangularShape.getWidth() > 0.0 && rectangularShape.getHeight() > 0.0) {
            final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "rect");
            elementNS.setAttributeNS(null, "x", this.doubleString(rectangularShape.getX()));
            elementNS.setAttributeNS(null, "y", this.doubleString(rectangularShape.getY()));
            elementNS.setAttributeNS(null, "width", this.doubleString(rectangularShape.getWidth()));
            elementNS.setAttributeNS(null, "height", this.doubleString(rectangularShape.getHeight()));
            return elementNS;
        }
        if (rectangularShape.getWidth() == 0.0 && rectangularShape.getHeight() > 0.0) {
            return this.svgLine.toSVG(new Line2D.Double(rectangularShape.getX(), rectangularShape.getY(), rectangularShape.getX(), rectangularShape.getY() + rectangularShape.getHeight()));
        }
        if (rectangularShape.getWidth() > 0.0 && rectangularShape.getHeight() == 0.0) {
            return this.svgLine.toSVG(new Line2D.Double(rectangularShape.getX(), rectangularShape.getY(), rectangularShape.getX() + rectangularShape.getWidth(), rectangularShape.getY()));
        }
        return null;
    }
}
