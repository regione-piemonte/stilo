// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.extension.PrefixableStylableExtensionElement;

public class FlowRegionElement extends PrefixableStylableExtensionElement implements BatikExtConstants
{
    protected FlowRegionElement() {
    }
    
    public FlowRegionElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public String getLocalName() {
        return "flowRegion";
    }
    
    public String getNamespaceURI() {
        return "http://xml.apache.org/batik/ext";
    }
    
    protected Node newNode() {
        return new FlowRegionElement();
    }
}
