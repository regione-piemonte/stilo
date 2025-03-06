// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import org.w3c.dom.Element;
import java.awt.Shape;

public class SVGPath extends SVGGraphicObjectConverter
{
    public SVGPath(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public Element toSVG(final Shape shape) {
        final String svgPathData = toSVGPathData(shape, this.generatorContext);
        if (svgPathData == null || svgPathData.length() == 0) {
            return null;
        }
        final Element elementNS = this.generatorContext.domFactory.createElementNS("http://www.w3.org/2000/svg", "path");
        elementNS.setAttributeNS(null, "d", svgPathData);
        if (shape.getPathIterator(null).getWindingRule() == 0) {
            elementNS.setAttributeNS(null, "fill-rule", "evenodd");
        }
        return elementNS;
    }
    
    public static String toSVGPathData(final Shape shape, final SVGGeneratorContext svgGeneratorContext) {
        final StringBuffer sb = new StringBuffer(40);
        final PathIterator pathIterator = shape.getPathIterator(null);
        final float[] array = new float[6];
        while (!pathIterator.isDone()) {
            final int currentSegment = pathIterator.currentSegment(array);
            switch (currentSegment) {
                case 0: {
                    sb.append("M");
                    appendPoint(sb, array[0], array[1], svgGeneratorContext);
                    break;
                }
                case 1: {
                    sb.append("L");
                    appendPoint(sb, array[0], array[1], svgGeneratorContext);
                    break;
                }
                case 4: {
                    sb.append("Z");
                    break;
                }
                case 2: {
                    sb.append("Q");
                    appendPoint(sb, array[0], array[1], svgGeneratorContext);
                    appendPoint(sb, array[2], array[3], svgGeneratorContext);
                    break;
                }
                case 3: {
                    sb.append("C");
                    appendPoint(sb, array[0], array[1], svgGeneratorContext);
                    appendPoint(sb, array[2], array[3], svgGeneratorContext);
                    appendPoint(sb, array[4], array[5], svgGeneratorContext);
                    break;
                }
                default: {
                    throw new Error("invalid segmentType:" + currentSegment);
                }
            }
            pathIterator.next();
        }
        if (sb.length() > 0) {
            return sb.toString().trim();
        }
        return "";
    }
    
    private static void appendPoint(final StringBuffer sb, final float n, final float n2, final SVGGeneratorContext svgGeneratorContext) {
        sb.append(svgGeneratorContext.doubleString(n));
        sb.append(" ");
        sb.append(svgGeneratorContext.doubleString(n2));
        sb.append(" ");
    }
}
