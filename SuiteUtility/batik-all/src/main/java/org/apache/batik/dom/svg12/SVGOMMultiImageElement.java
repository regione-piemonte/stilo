// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGStylableElement;

public class SVGOMMultiImageElement extends SVGStylableElement
{
    protected SVGOMMultiImageElement() {
    }
    
    public SVGOMMultiImageElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "multiImage";
    }
    
    protected Node newNode() {
        return new SVGOMMultiImageElement();
    }
}
