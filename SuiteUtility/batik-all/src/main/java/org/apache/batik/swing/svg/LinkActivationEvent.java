// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import org.w3c.dom.svg.SVGAElement;
import java.util.EventObject;

public class LinkActivationEvent extends EventObject
{
    protected String referencedURI;
    
    public LinkActivationEvent(final Object source, final SVGAElement svgaElement, final String referencedURI) {
        super(source);
        this.referencedURI = referencedURI;
    }
    
    public String getReferencedURI() {
        return this.referencedURI;
    }
}
