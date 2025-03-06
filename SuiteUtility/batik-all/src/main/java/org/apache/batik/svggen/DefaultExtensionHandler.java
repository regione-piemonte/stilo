// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;
import java.awt.Composite;
import java.awt.Paint;

public class DefaultExtensionHandler implements ExtensionHandler
{
    public SVGPaintDescriptor handlePaint(final Paint paint, final SVGGeneratorContext svgGeneratorContext) {
        return null;
    }
    
    public SVGCompositeDescriptor handleComposite(final Composite composite, final SVGGeneratorContext svgGeneratorContext) {
        return null;
    }
    
    public SVGFilterDescriptor handleFilter(final BufferedImageOp bufferedImageOp, final Rectangle rectangle, final SVGGeneratorContext svgGeneratorContext) {
        return null;
    }
}
