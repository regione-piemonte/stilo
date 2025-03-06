// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.svg.SVGPolygonElement;

public class SVGOMPolygonElement extends SVGPointShapeElement implements SVGPolygonElement
{
    protected SVGOMPolygonElement() {
    }
    
    public SVGOMPolygonElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "polygon";
    }
    
    protected Node newNode() {
        return new SVGOMPolygonElement();
    }
}
