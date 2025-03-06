// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.image.ConvolveOp;
import java.awt.image.RescaleOp;
import java.awt.image.LookupOp;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SVGBufferedImageOp extends AbstractSVGFilterConverter
{
    private SVGLookupOp svgLookupOp;
    private SVGRescaleOp svgRescaleOp;
    private SVGConvolveOp svgConvolveOp;
    private SVGCustomBufferedImageOp svgCustomBufferedImageOp;
    
    public SVGBufferedImageOp(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
        this.svgLookupOp = new SVGLookupOp(svgGeneratorContext);
        this.svgRescaleOp = new SVGRescaleOp(svgGeneratorContext);
        this.svgConvolveOp = new SVGConvolveOp(svgGeneratorContext);
        this.svgCustomBufferedImageOp = new SVGCustomBufferedImageOp(svgGeneratorContext);
    }
    
    public List getDefinitionSet() {
        final LinkedList list = new LinkedList(this.svgLookupOp.getDefinitionSet());
        list.addAll(this.svgRescaleOp.getDefinitionSet());
        list.addAll(this.svgConvolveOp.getDefinitionSet());
        list.addAll(this.svgCustomBufferedImageOp.getDefinitionSet());
        return list;
    }
    
    public SVGLookupOp getLookupOpConverter() {
        return this.svgLookupOp;
    }
    
    public SVGRescaleOp getRescaleOpConverter() {
        return this.svgRescaleOp;
    }
    
    public SVGConvolveOp getConvolveOpConverter() {
        return this.svgConvolveOp;
    }
    
    public SVGCustomBufferedImageOp getCustomBufferedImageOpConverter() {
        return this.svgCustomBufferedImageOp;
    }
    
    public SVGFilterDescriptor toSVG(final BufferedImageOp bufferedImageOp, final Rectangle rectangle) {
        SVGFilterDescriptor svgFilterDescriptor = this.svgCustomBufferedImageOp.toSVG(bufferedImageOp, rectangle);
        if (svgFilterDescriptor == null) {
            if (bufferedImageOp instanceof LookupOp) {
                svgFilterDescriptor = this.svgLookupOp.toSVG(bufferedImageOp, rectangle);
            }
            else if (bufferedImageOp instanceof RescaleOp) {
                svgFilterDescriptor = this.svgRescaleOp.toSVG(bufferedImageOp, rectangle);
            }
            else if (bufferedImageOp instanceof ConvolveOp) {
                svgFilterDescriptor = this.svgConvolveOp.toSVG(bufferedImageOp, rectangle);
            }
        }
        return svgFilterDescriptor;
    }
}
