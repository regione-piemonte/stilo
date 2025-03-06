// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.dom.events.NodeEventTarget;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.Element;
import org.apache.batik.css.engine.CSSNavigableNode;
import org.apache.batik.dom.AbstractDocumentFragment;

public class SVGOMUseShadowRoot extends AbstractDocumentFragment implements CSSNavigableNode, IdContainer
{
    protected Element cssParentElement;
    protected boolean isLocal;
    
    protected SVGOMUseShadowRoot() {
    }
    
    public SVGOMUseShadowRoot(final AbstractDocument ownerDocument, final Element cssParentElement, final boolean isLocal) {
        this.ownerDocument = ownerDocument;
        this.cssParentElement = cssParentElement;
        this.isLocal = isLocal;
    }
    
    public boolean isReadonly() {
        return false;
    }
    
    public void setReadonly(final boolean b) {
    }
    
    public Element getElementById(final String s) {
        return this.ownerDocument.getChildElementById(this, s);
    }
    
    public Node getCSSParentNode() {
        return this.cssParentElement;
    }
    
    public Node getCSSPreviousSibling() {
        return null;
    }
    
    public Node getCSSNextSibling() {
        return null;
    }
    
    public Node getCSSFirstChild() {
        return this.getFirstChild();
    }
    
    public Node getCSSLastChild() {
        return this.getLastChild();
    }
    
    public boolean isHiddenFromSelectors() {
        return false;
    }
    
    public NodeEventTarget getParentNodeEventTarget() {
        return (NodeEventTarget)this.getCSSParentNode();
    }
    
    protected Node newNode() {
        return new SVGOMUseShadowRoot();
    }
}
