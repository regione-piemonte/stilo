// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Element;
import java.awt.Paint;
import org.apache.batik.ext.awt.g2d.GraphicContext;

public class SVGCustomPaint extends AbstractSVGConverter
{
    public SVGCustomPaint(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG(graphicContext.getPaint());
    }
    
    public SVGPaintDescriptor toSVG(final Paint paint) {
        SVGPaintDescriptor handlePaint = this.descMap.get(paint);
        if (handlePaint == null) {
            handlePaint = this.generatorContext.extensionHandler.handlePaint(paint, this.generatorContext);
            if (handlePaint != null) {
                final Element def = handlePaint.getDef();
                if (def != null) {
                    this.defSet.add(def);
                }
                this.descMap.put(paint, handlePaint);
            }
        }
        return handlePaint;
    }
}
