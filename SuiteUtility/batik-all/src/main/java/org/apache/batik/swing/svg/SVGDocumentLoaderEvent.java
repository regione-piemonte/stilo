// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.w3c.dom.svg.SVGDocument;
import java.util.EventObject;

public class SVGDocumentLoaderEvent extends EventObject
{
    protected SVGDocument svgDocument;
    
    public SVGDocumentLoaderEvent(final Object source, final SVGDocument svgDocument) {
        super(source);
        this.svgDocument = svgDocument;
    }
    
    public SVGDocument getSVGDocument() {
        return this.svgDocument;
    }
}
