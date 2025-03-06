// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.AlphaComposite;
import java.awt.Composite;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SVGComposite implements SVGConverter
{
    private SVGAlphaComposite svgAlphaComposite;
    private SVGCustomComposite svgCustomComposite;
    
    public SVGComposite(final SVGGeneratorContext svgGeneratorContext) {
        this.svgAlphaComposite = new SVGAlphaComposite(svgGeneratorContext);
        this.svgCustomComposite = new SVGCustomComposite(svgGeneratorContext);
    }
    
    public List getDefinitionSet() {
        final LinkedList list = new LinkedList(this.svgAlphaComposite.getDefinitionSet());
        list.addAll(this.svgCustomComposite.getDefinitionSet());
        return list;
    }
    
    public SVGAlphaComposite getAlphaCompositeConverter() {
        return this.svgAlphaComposite;
    }
    
    public SVGCustomComposite getCustomCompositeConverter() {
        return this.svgCustomComposite;
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG(graphicContext.getComposite());
    }
    
    public SVGCompositeDescriptor toSVG(final Composite composite) {
        if (composite instanceof AlphaComposite) {
            return this.svgAlphaComposite.toSVG((AlphaComposite)composite);
        }
        return this.svgCustomComposite.toSVG(composite);
    }
}
