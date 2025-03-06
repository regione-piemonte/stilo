// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import org.w3c.dom.Element;
import java.awt.Polygon;

public class SVGPolygon extends SVGGraphicObjectConverter
{
    public SVGPolygon(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public Element toSVG(final Polygon polygon) {
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "polygon");
        final StringBuffer sb = new StringBuffer(" ");
        final PathIterator pathIterator = polygon.getPathIterator(null);
        final float[] array = new float[6];
        while (!pathIterator.isDone()) {
            final int currentSegment = pathIterator.currentSegment(array);
            switch (currentSegment) {
                case 0: {
                    this.appendPoint(sb, array[0], array[1]);
                    break;
                }
                case 1: {
                    this.appendPoint(sb, array[0], array[1]);
                    break;
                }
                case 4: {
                    break;
                }
                default: {
                    throw new Error("invalid segmentType:" + currentSegment);
                }
            }
            pathIterator.next();
        }
        elementNS.setAttributeNS(null, "points", sb.substring(0, sb.length() - 1));
        return elementNS;
    }
    
    private void appendPoint(final StringBuffer sb, final float n, final float n2) {
        sb.append(this.doubleString(n));
        sb.append(" ");
        sb.append(this.doubleString(n2));
        sb.append(" ");
    }
}
