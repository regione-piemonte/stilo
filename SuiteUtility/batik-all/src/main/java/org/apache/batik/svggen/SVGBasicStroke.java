// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.BasicStroke;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class SVGBasicStroke extends AbstractSVGConverter
{
    public SVGBasicStroke(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        if (graphicContext.getStroke() instanceof BasicStroke) {
            return this.toSVG((BasicStroke)graphicContext.getStroke());
        }
        return null;
    }
    
    public final SVGStrokeDescriptor toSVG(final BasicStroke basicStroke) {
        final String doubleString = this.doubleString(basicStroke.getLineWidth());
        final String endCapToSVG = endCapToSVG(basicStroke.getEndCap());
        final String joinToSVG = joinToSVG(basicStroke.getLineJoin());
        final String doubleString2 = this.doubleString(basicStroke.getMiterLimit());
        final float[] dashArray = basicStroke.getDashArray();
        String dashArrayToSVG;
        if (dashArray != null) {
            dashArrayToSVG = this.dashArrayToSVG(dashArray);
        }
        else {
            dashArrayToSVG = "none";
        }
        return new SVGStrokeDescriptor(doubleString, endCapToSVG, joinToSVG, doubleString2, dashArrayToSVG, this.doubleString(basicStroke.getDashPhase()));
    }
    
    private final String dashArrayToSVG(final float[] array) {
        final StringBuffer sb = new StringBuffer(array.length * 8);
        if (array.length > 0) {
            sb.append(this.doubleString(array[0]));
        }
        for (int i = 1; i < array.length; ++i) {
            sb.append(",");
            sb.append(this.doubleString(array[i]));
        }
        return sb.toString();
    }
    
    private static String joinToSVG(final int n) {
        switch (n) {
            case 2: {
                return "bevel";
            }
            case 1: {
                return "round";
            }
            default: {
                return "miter";
            }
        }
    }
    
    private static String endCapToSVG(final int n) {
        switch (n) {
            case 0: {
                return "butt";
            }
            case 1: {
                return "round";
            }
            default: {
                return "square";
            }
        }
    }
}
