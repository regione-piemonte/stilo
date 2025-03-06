// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.Line2D;
import org.w3c.dom.Element;
import java.awt.geom.Ellipse2D;

public class SVGEllipse extends SVGGraphicObjectConverter
{
    private SVGLine svgLine;
    
    public SVGEllipse(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public Element toSVG(final Ellipse2D ellipse2D) {
        if (ellipse2D.getWidth() < 0.0 || ellipse2D.getHeight() < 0.0) {
            return null;
        }
        if (ellipse2D.getWidth() == ellipse2D.getHeight()) {
            return this.toSVGCircle(ellipse2D);
        }
        return this.toSVGEllipse(ellipse2D);
    }
    
    private Element toSVGCircle(final Ellipse2D ellipse2D) {
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "circle");
        elementNS.setAttributeNS(null, "cx", this.doubleString(ellipse2D.getX() + ellipse2D.getWidth() / 2.0));
        elementNS.setAttributeNS(null, "cy", this.doubleString(ellipse2D.getY() + ellipse2D.getHeight() / 2.0));
        elementNS.setAttributeNS(null, "r", this.doubleString(ellipse2D.getWidth() / 2.0));
        return elementNS;
    }
    
    private Element toSVGEllipse(final Ellipse2D ellipse2D) {
        if (ellipse2D.getWidth() > 0.0 && ellipse2D.getHeight() > 0.0) {
            final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "ellipse");
            elementNS.setAttributeNS(null, "cx", this.doubleString(ellipse2D.getX() + ellipse2D.getWidth() / 2.0));
            elementNS.setAttributeNS(null, "cy", this.doubleString(ellipse2D.getY() + ellipse2D.getHeight() / 2.0));
            elementNS.setAttributeNS(null, "rx", this.doubleString(ellipse2D.getWidth() / 2.0));
            elementNS.setAttributeNS(null, "ry", this.doubleString(ellipse2D.getHeight() / 2.0));
            return elementNS;
        }
        if (ellipse2D.getWidth() == 0.0 && ellipse2D.getHeight() > 0.0) {
            final Line2D.Double double1 = new Line2D.Double(ellipse2D.getX(), ellipse2D.getY(), ellipse2D.getX(), ellipse2D.getY() + ellipse2D.getHeight());
            if (this.svgLine == null) {
                this.svgLine = new SVGLine(this.generatorContext);
            }
            return this.svgLine.toSVG(double1);
        }
        if (ellipse2D.getWidth() > 0.0 && ellipse2D.getHeight() == 0.0) {
            final Line2D.Double double2 = new Line2D.Double(ellipse2D.getX(), ellipse2D.getY(), ellipse2D.getX() + ellipse2D.getWidth(), ellipse2D.getY());
            if (this.svgLine == null) {
                this.svgLine = new SVGLine(this.generatorContext);
            }
            return this.svgLine.toSVG(double2);
        }
        return null;
    }
}
