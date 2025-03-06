// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.TexturePaint;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.Paint;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SVGPaint implements SVGConverter
{
    private SVGLinearGradient svgLinearGradient;
    private SVGTexturePaint svgTexturePaint;
    private SVGColor svgColor;
    private SVGCustomPaint svgCustomPaint;
    private SVGGeneratorContext generatorContext;
    
    public SVGPaint(final SVGGeneratorContext generatorContext) {
        this.svgLinearGradient = new SVGLinearGradient(generatorContext);
        this.svgTexturePaint = new SVGTexturePaint(generatorContext);
        this.svgCustomPaint = new SVGCustomPaint(generatorContext);
        this.svgColor = new SVGColor(generatorContext);
        this.generatorContext = generatorContext;
    }
    
    public List getDefinitionSet() {
        final LinkedList list = new LinkedList(this.svgLinearGradient.getDefinitionSet());
        list.addAll(this.svgTexturePaint.getDefinitionSet());
        list.addAll(this.svgCustomPaint.getDefinitionSet());
        list.addAll(this.svgColor.getDefinitionSet());
        return list;
    }
    
    public SVGTexturePaint getTexturePaintConverter() {
        return this.svgTexturePaint;
    }
    
    public SVGLinearGradient getGradientPaintConverter() {
        return this.svgLinearGradient;
    }
    
    public SVGCustomPaint getCustomPaintConverter() {
        return this.svgCustomPaint;
    }
    
    public SVGColor getColorConverter() {
        return this.svgColor;
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG(graphicContext.getPaint());
    }
    
    public SVGPaintDescriptor toSVG(final Paint paint) {
        SVGPaintDescriptor svgPaintDescriptor = this.svgCustomPaint.toSVG(paint);
        if (svgPaintDescriptor == null) {
            if (paint instanceof Color) {
                svgPaintDescriptor = SVGColor.toSVG((Color)paint, this.generatorContext);
            }
            else if (paint instanceof GradientPaint) {
                svgPaintDescriptor = this.svgLinearGradient.toSVG((GradientPaint)paint);
            }
            else if (paint instanceof TexturePaint) {
                svgPaintDescriptor = this.svgTexturePaint.toSVG((TexturePaint)paint);
            }
        }
        return svgPaintDescriptor;
    }
}
