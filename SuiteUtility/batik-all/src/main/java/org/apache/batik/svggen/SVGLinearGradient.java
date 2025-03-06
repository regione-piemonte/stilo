// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.Point2D;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.awt.GradientPaint;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class SVGLinearGradient extends AbstractSVGConverter
{
    public SVGLinearGradient(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG((GradientPaint)graphicContext.getPaint());
    }
    
    public SVGPaintDescriptor toSVG(final GradientPaint gradientPaint) {
        SVGPaintDescriptor svgPaintDescriptor = this.descMap.get(gradientPaint);
        final Document domFactory = this.generatorContext.domFactory;
        if (svgPaintDescriptor == null) {
            final Element elementNS = domFactory.createElementNS("http://www.w3.org/2000/svg", "linearGradient");
            elementNS.setAttributeNS(null, "gradientUnits", "userSpaceOnUse");
            final Point2D point1 = gradientPaint.getPoint1();
            final Point2D point2 = gradientPaint.getPoint2();
            elementNS.setAttributeNS(null, "x1", this.doubleString(point1.getX()));
            elementNS.setAttributeNS(null, "y1", this.doubleString(point1.getY()));
            elementNS.setAttributeNS(null, "x2", this.doubleString(point2.getX()));
            elementNS.setAttributeNS(null, "y2", this.doubleString(point2.getY()));
            String s = "pad";
            if (gradientPaint.isCyclic()) {
                s = "reflect";
            }
            elementNS.setAttributeNS(null, "spreadMethod", s);
            final Element elementNS2 = domFactory.createElementNS("http://www.w3.org/2000/svg", "stop");
            elementNS2.setAttributeNS(null, "offset", "0%");
            final SVGPaintDescriptor svg = SVGColor.toSVG(gradientPaint.getColor1(), this.generatorContext);
            elementNS2.setAttributeNS(null, "stop-color", svg.getPaintValue());
            elementNS2.setAttributeNS(null, "stop-opacity", svg.getOpacityValue());
            elementNS.appendChild(elementNS2);
            final Element elementNS3 = domFactory.createElementNS("http://www.w3.org/2000/svg", "stop");
            elementNS3.setAttributeNS(null, "offset", "100%");
            final SVGPaintDescriptor svg2 = SVGColor.toSVG(gradientPaint.getColor2(), this.generatorContext);
            elementNS3.setAttributeNS(null, "stop-color", svg2.getPaintValue());
            elementNS3.setAttributeNS(null, "stop-opacity", svg2.getOpacityValue());
            elementNS.appendChild(elementNS3);
            elementNS.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("linearGradient"));
            final StringBuffer sb = new StringBuffer("url(");
            sb.append("#");
            sb.append(elementNS.getAttributeNS(null, "id"));
            sb.append(")");
            svgPaintDescriptor = new SVGPaintDescriptor(sb.toString(), "1", elementNS);
            this.descMap.put(gradientPaint, svgPaintDescriptor);
            this.defSet.add(elementNS);
        }
        return svgPaintDescriptor;
    }
}
