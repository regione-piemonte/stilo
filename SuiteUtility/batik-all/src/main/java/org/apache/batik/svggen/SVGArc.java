// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import org.w3c.dom.Element;
import java.awt.geom.Arc2D;

public class SVGArc extends SVGGraphicObjectConverter
{
    private SVGLine svgLine;
    private SVGEllipse svgEllipse;
    
    public SVGArc(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public Element toSVG(final Arc2D arc2D) {
        final double angleExtent = arc2D.getAngleExtent();
        final double width = arc2D.getWidth();
        final double height = arc2D.getHeight();
        if (width == 0.0 || height == 0.0) {
            final Line2D.Double double1 = new Line2D.Double(arc2D.getX(), arc2D.getY(), arc2D.getX() + width, arc2D.getY() + height);
            if (this.svgLine == null) {
                this.svgLine = new SVGLine(this.generatorContext);
            }
            return this.svgLine.toSVG(double1);
        }
        if (angleExtent >= 360.0 || angleExtent <= -360.0) {
            final Ellipse2D.Double double2 = new Ellipse2D.Double(arc2D.getX(), arc2D.getY(), width, height);
            if (this.svgEllipse == null) {
                this.svgEllipse = new SVGEllipse(this.generatorContext);
            }
            return this.svgEllipse.toSVG(double2);
        }
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "path");
        final StringBuffer sb = new StringBuffer(64);
        final Point2D startPoint = arc2D.getStartPoint();
        final Point2D endPoint = arc2D.getEndPoint();
        final int arcType = arc2D.getArcType();
        sb.append("M");
        sb.append(this.doubleString(startPoint.getX()));
        sb.append(" ");
        sb.append(this.doubleString(startPoint.getY()));
        sb.append(" ");
        sb.append("A");
        sb.append(this.doubleString(width / 2.0));
        sb.append(" ");
        sb.append(this.doubleString(height / 2.0));
        sb.append(" ");
        sb.append('0');
        sb.append(" ");
        if (angleExtent > 0.0) {
            if (angleExtent > 180.0) {
                sb.append('1');
            }
            else {
                sb.append('0');
            }
            sb.append(" ");
            sb.append('0');
        }
        else {
            if (angleExtent < -180.0) {
                sb.append('1');
            }
            else {
                sb.append('0');
            }
            sb.append(" ");
            sb.append('1');
        }
        sb.append(" ");
        sb.append(this.doubleString(endPoint.getX()));
        sb.append(" ");
        sb.append(this.doubleString(endPoint.getY()));
        if (arcType == 1) {
            sb.append("Z");
        }
        else if (arcType == 2) {
            final double n = arc2D.getX() + width / 2.0;
            final double n2 = arc2D.getY() + height / 2.0;
            sb.append("L");
            sb.append(" ");
            sb.append(this.doubleString(n));
            sb.append(" ");
            sb.append(this.doubleString(n2));
            sb.append(" ");
            sb.append("Z");
        }
        elementNS.setAttributeNS(null, "d", sb.toString());
        return elementNS;
    }
}
