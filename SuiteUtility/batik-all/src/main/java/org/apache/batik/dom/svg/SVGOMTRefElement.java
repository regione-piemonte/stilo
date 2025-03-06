// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGTRefElement;

public class SVGOMTRefElement extends SVGURIReferenceTextPositioningElement implements SVGTRefElement
{
    protected SVGOMTRefElement() {
    }
    
    public SVGOMTRefElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "tref";
    }
    
    protected Node newNode() {
        return new SVGOMTRefElement();
    }
}
