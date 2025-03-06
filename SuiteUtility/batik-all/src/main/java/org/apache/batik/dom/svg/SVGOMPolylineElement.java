// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGPolylineElement;

public class SVGOMPolylineElement extends SVGPointShapeElement implements SVGPolylineElement
{
    protected SVGOMPolylineElement() {
    }
    
    public SVGOMPolylineElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "polyline";
    }
    
    protected Node newNode() {
        return new SVGOMPolylineElement();
    }
}
