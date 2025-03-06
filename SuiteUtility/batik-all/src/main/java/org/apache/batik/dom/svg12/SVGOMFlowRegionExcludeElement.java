// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGStylableElement;

public class SVGOMFlowRegionExcludeElement extends SVGStylableElement
{
    protected SVGOMFlowRegionExcludeElement() {
    }
    
    public SVGOMFlowRegionExcludeElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "flowRegionExclude";
    }
    
    protected Node newNode() {
        return new SVGOMFlowRegionExcludeElement();
    }
}
