// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Element;
import java.awt.Composite;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class SVGCustomComposite extends AbstractSVGConverter
{
    public SVGCustomComposite(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG(graphicContext.getComposite());
    }
    
    public SVGCompositeDescriptor toSVG(final Composite composite) {
        if (composite == null) {
            throw new NullPointerException();
        }
        final SVGCompositeDescriptor svgCompositeDescriptor = this.descMap.get(composite);
        if (svgCompositeDescriptor == null) {
            final SVGCompositeDescriptor handleComposite = this.generatorContext.extensionHandler.handleComposite(composite, this.generatorContext);
            if (handleComposite != null) {
                final Element def = handleComposite.getDef();
                if (def != null) {
                    this.defSet.add(def);
                }
                this.descMap.put(composite, handleComposite);
            }
        }
        return svgCompositeDescriptor;
    }
}
