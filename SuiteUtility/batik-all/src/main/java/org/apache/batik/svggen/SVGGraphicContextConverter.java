// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import org.apache.batik.ext.awt.g2d.TransformStackElement;

public class SVGGraphicContextConverter
{
    private static final int GRAPHIC_CONTEXT_CONVERTER_COUNT = 6;
    private SVGTransform transformConverter;
    private SVGPaint paintConverter;
    private SVGBasicStroke strokeConverter;
    private SVGComposite compositeConverter;
    private SVGClip clipConverter;
    private SVGRenderingHints hintsConverter;
    private SVGFont fontConverter;
    private SVGConverter[] converters;
    
    public SVGTransform getTransformConverter() {
        return this.transformConverter;
    }
    
    public SVGPaint getPaintConverter() {
        return this.paintConverter;
    }
    
    public SVGBasicStroke getStrokeConverter() {
        return this.strokeConverter;
    }
    
    public SVGComposite getCompositeConverter() {
        return this.compositeConverter;
    }
    
    public SVGClip getClipConverter() {
        return this.clipConverter;
    }
    
    public SVGRenderingHints getHintsConverter() {
        return this.hintsConverter;
    }
    
    public SVGFont getFontConverter() {
        return this.fontConverter;
    }
    
    public SVGGraphicContextConverter(final SVGGeneratorContext svgGeneratorContext) {
        this.converters = new SVGConverter[6];
        if (svgGeneratorContext == null) {
            throw new SVGGraphics2DRuntimeException("generatorContext should not be null");
        }
        this.transformConverter = new SVGTransform(svgGeneratorContext);
        this.paintConverter = new SVGPaint(svgGeneratorContext);
        this.strokeConverter = new SVGBasicStroke(svgGeneratorContext);
        this.compositeConverter = new SVGComposite(svgGeneratorContext);
        this.clipConverter = new SVGClip(svgGeneratorContext);
        this.hintsConverter = new SVGRenderingHints(svgGeneratorContext);
        this.fontConverter = new SVGFont(svgGeneratorContext);
        int n = 0;
        this.converters[n++] = this.paintConverter;
        this.converters[n++] = this.strokeConverter;
        this.converters[n++] = this.compositeConverter;
        this.converters[n++] = this.clipConverter;
        this.converters[n++] = this.hintsConverter;
        this.converters[n++] = this.fontConverter;
    }
    
    public String toSVG(final TransformStackElement[] array) {
        return this.transformConverter.toSVGTransform(array);
    }
    
    public SVGGraphicContext toSVG(final GraphicContext graphicContext) {
        final HashMap hashMap = new HashMap();
        for (int i = 0; i < this.converters.length; ++i) {
            final SVGDescriptor svg = this.converters[i].toSVG(graphicContext);
            if (svg != null) {
                svg.getAttributeMap(hashMap);
            }
        }
        return new SVGGraphicContext(hashMap, graphicContext.getTransformStack());
    }
    
    public List getDefinitionSet() {
        final LinkedList list = new LinkedList();
        for (int i = 0; i < this.converters.length; ++i) {
            list.addAll(this.converters[i].getDefinitionSet());
        }
        return list;
    }
}
