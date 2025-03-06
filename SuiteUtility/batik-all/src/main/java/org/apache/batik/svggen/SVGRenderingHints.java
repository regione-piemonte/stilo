// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.RenderingHints;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class SVGRenderingHints extends AbstractSVGConverter
{
    public SVGRenderingHints(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return toSVG(graphicContext.getRenderingHints());
    }
    
    public static SVGHintsDescriptor toSVG(final RenderingHints renderingHints) {
        String s = "auto";
        String s2 = "auto";
        String s3 = "auto";
        String s4 = "auto";
        String s5 = "auto";
        if (renderingHints != null) {
            final Object value = renderingHints.get(RenderingHints.KEY_RENDERING);
            if (value == RenderingHints.VALUE_RENDER_DEFAULT) {
                s = "auto";
                s2 = "auto";
                s3 = "auto";
                s4 = "auto";
                s5 = "auto";
            }
            else if (value == RenderingHints.VALUE_RENDER_SPEED) {
                s = "sRGB";
                s2 = "optimizeSpeed";
                s3 = "optimizeSpeed";
                s4 = "geometricPrecision";
                s5 = "optimizeSpeed";
            }
            else if (value == RenderingHints.VALUE_RENDER_QUALITY) {
                s = "linearRGB";
                s2 = "optimizeQuality";
                s3 = "optimizeQuality";
                s4 = "geometricPrecision";
                s5 = "optimizeQuality";
            }
            final Object value2 = renderingHints.get(RenderingHints.KEY_FRACTIONALMETRICS);
            if (value2 == RenderingHints.VALUE_FRACTIONALMETRICS_ON) {
                s3 = "optimizeQuality";
                s4 = "geometricPrecision";
            }
            else if (value2 == RenderingHints.VALUE_FRACTIONALMETRICS_OFF) {
                s3 = "optimizeSpeed";
                s4 = "optimizeSpeed";
            }
            else if (value2 == RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT) {
                s3 = "auto";
                s4 = "auto";
            }
            final Object value3 = renderingHints.get(RenderingHints.KEY_ANTIALIASING);
            if (value3 == RenderingHints.VALUE_ANTIALIAS_ON) {
                s3 = "optimizeLegibility";
                s4 = "auto";
            }
            else if (value3 == RenderingHints.VALUE_ANTIALIAS_OFF) {
                s3 = "geometricPrecision";
                s4 = "crispEdges";
            }
            else if (value3 == RenderingHints.VALUE_ANTIALIAS_DEFAULT) {
                s3 = "auto";
                s4 = "auto";
            }
            final Object value4 = renderingHints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
            if (value4 == RenderingHints.VALUE_TEXT_ANTIALIAS_ON) {
                s3 = "geometricPrecision";
            }
            else if (value4 == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF) {
                s3 = "optimizeSpeed";
            }
            else if (value4 == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT) {
                s3 = "auto";
            }
            final Object value5 = renderingHints.get(RenderingHints.KEY_COLOR_RENDERING);
            if (value5 == RenderingHints.VALUE_COLOR_RENDER_DEFAULT) {
                s2 = "auto";
            }
            else if (value5 == RenderingHints.VALUE_COLOR_RENDER_QUALITY) {
                s2 = "optimizeQuality";
            }
            else if (value5 == RenderingHints.VALUE_COLOR_RENDER_SPEED) {
                s2 = "optimizeSpeed";
            }
            final Object value6 = renderingHints.get(RenderingHints.KEY_INTERPOLATION);
            if (value6 == RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR) {
                s5 = "optimizeSpeed";
            }
            else if (value6 == RenderingHints.VALUE_INTERPOLATION_BICUBIC || value6 == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
                s5 = "optimizeQuality";
            }
        }
        return new SVGHintsDescriptor(s, s2, s3, s4, s5);
    }
}
