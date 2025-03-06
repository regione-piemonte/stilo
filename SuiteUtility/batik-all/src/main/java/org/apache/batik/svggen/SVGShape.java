// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import java.awt.Polygon;
import org.w3c.dom.Element;
import java.awt.Shape;

public class SVGShape extends SVGGraphicObjectConverter
{
    private SVGArc svgArc;
    private SVGEllipse svgEllipse;
    private SVGLine svgLine;
    private SVGPath svgPath;
    private SVGPolygon svgPolygon;
    private SVGRectangle svgRectangle;
    
    public SVGShape(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
        this.svgArc = new SVGArc(svgGeneratorContext);
        this.svgEllipse = new SVGEllipse(svgGeneratorContext);
        this.svgLine = new SVGLine(svgGeneratorContext);
        this.svgPath = new SVGPath(svgGeneratorContext);
        this.svgPolygon = new SVGPolygon(svgGeneratorContext);
        this.svgRectangle = new SVGRectangle(svgGeneratorContext);
    }
    
    public Element toSVG(final Shape shape) {
        if (shape instanceof Polygon) {
            return this.svgPolygon.toSVG((Polygon)shape);
        }
        if (shape instanceof Rectangle2D) {
            return this.svgRectangle.toSVG((Rectangle2D)shape);
        }
        if (shape instanceof RoundRectangle2D) {
            return this.svgRectangle.toSVG((RoundRectangle2D)shape);
        }
        if (shape instanceof Ellipse2D) {
            return this.svgEllipse.toSVG((Ellipse2D)shape);
        }
        if (shape instanceof Line2D) {
            return this.svgLine.toSVG((Line2D)shape);
        }
        if (shape instanceof Arc2D) {
            return this.svgArc.toSVG((Arc2D)shape);
        }
        return this.svgPath.toSVG(shape);
    }
}
