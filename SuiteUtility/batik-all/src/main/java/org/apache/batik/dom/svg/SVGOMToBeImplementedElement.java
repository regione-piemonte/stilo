// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;

public class SVGOMToBeImplementedElement extends SVGGraphicsElement
{
    protected String localName;
    
    protected SVGOMToBeImplementedElement() {
    }
    
    public SVGOMToBeImplementedElement(final String s, final AbstractDocument abstractDocument, final String localName) {
        super(s, abstractDocument);
        this.localName = localName;
    }
    
    public String getLocalName() {
        return this.localName;
    }
    
    protected Node newNode() {
        return new SVGOMToBeImplementedElement();
    }
    
    protected Node export(final Node node, final AbstractDocument abstractDocument) {
        super.export(node, abstractDocument);
        ((SVGOMToBeImplementedElement)node).localName = this.localName;
        return node;
    }
    
    protected Node deepExport(final Node node, final AbstractDocument abstractDocument) {
        super.deepExport(node, abstractDocument);
        ((SVGOMToBeImplementedElement)node).localName = this.localName;
        return node;
    }
    
    protected Node copyInto(final Node node) {
        super.copyInto(node);
        ((SVGOMToBeImplementedElement)node).localName = this.localName;
        return node;
    }
    
    protected Node deepCopyInto(final Node node) {
        super.deepCopyInto(node);
        ((SVGOMToBeImplementedElement)node).localName = this.localName;
        return node;
    }
}
