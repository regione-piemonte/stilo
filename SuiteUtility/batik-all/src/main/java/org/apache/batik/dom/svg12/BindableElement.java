// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.Node;
import org.apache.batik.dom.svg.AttributeInitializer;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.svg.SVGGraphicsElement;

public class BindableElement extends SVGGraphicsElement
{
    protected String namespaceURI;
    protected String localName;
    protected XBLOMShadowTreeElement xblShadowTree;
    
    protected BindableElement() {
    }
    
    public BindableElement(final String s, final AbstractDocument abstractDocument, final String namespaceURI, final String localName) {
        super(s, abstractDocument);
        this.namespaceURI = namespaceURI;
        this.localName = localName;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalName() {
        return this.localName;
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return null;
    }
    
    protected Node newNode() {
        return new BindableElement(null, null, this.namespaceURI, this.localName);
    }
    
    public void setShadowTree(final XBLOMShadowTreeElement xblShadowTree) {
        this.xblShadowTree = xblShadowTree;
    }
    
    public XBLOMShadowTreeElement getShadowTree() {
        return this.xblShadowTree;
    }
    
    public Node getCSSFirstChild() {
        if (this.xblShadowTree != null) {
            return this.xblShadowTree.getFirstChild();
        }
        return null;
    }
    
    public Node getCSSLastChild() {
        return this.getCSSFirstChild();
    }
}
