// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.extension.GraphicsExtensionElement;

public class BatikRegularPolygonElement extends GraphicsExtensionElement implements BatikExtConstants
{
    protected BatikRegularPolygonElement() {
    }
    
    public BatikRegularPolygonElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "regularPolygon";
    }
    
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    protected Node newNode() {
        return new BatikRegularPolygonElement();
    }
}
