// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Element;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

public class SVGCustomBufferedImageOp extends AbstractSVGFilterConverter
{
    private static final String ERROR_EXTENSION = "SVGCustomBufferedImageOp:: ExtensionHandler could not convert filter";
    
    public SVGCustomBufferedImageOp(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGFilterDescriptor toSVG(final BufferedImageOp bufferedImageOp, final Rectangle rectangle) {
        SVGFilterDescriptor handleFilter = this.descMap.get(bufferedImageOp);
        if (handleFilter == null) {
            handleFilter = this.generatorContext.extensionHandler.handleFilter(bufferedImageOp, rectangle, this.generatorContext);
            if (handleFilter != null) {
                final Element def = handleFilter.getDef();
                if (def != null) {
                    this.defSet.add(def);
                }
                this.descMap.put(bufferedImageOp, handleFilter);
            }
            else {
                System.err.println("SVGCustomBufferedImageOp:: ExtensionHandler could not convert filter");
            }
        }
        return handleFilter;
    }
}
