// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension;

import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.apache.batik.css.engine.StyleDeclarationProvider;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.svg.SVGStylable;
import org.apache.batik.css.engine.CSSStylableElement;

public abstract class StylableExtensionElement extends ExtensionElement implements CSSStylableElement, SVGStylable
{
    protected ParsedURL cssBase;
    protected StyleMap computedStyleMap;
    
    protected StylableExtensionElement() {
    }
    
    protected StylableExtensionElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
    }
    
    public StyleMap getComputedStyleMap(final String s) {
        return this.computedStyleMap;
    }
    
    public void setComputedStyleMap(final String s, final StyleMap computedStyleMap) {
        this.computedStyleMap = computedStyleMap;
    }
    
    public String getXMLId() {
        return this.getAttributeNS(null, "id");
    }
    
    public String getCSSClass() {
        return this.getAttributeNS(null, "class");
    }
    
    public ParsedURL getCSSBase() {
        if (this.cssBase == null) {
            final String baseURI = this.getBaseURI();
            if (baseURI == null) {
                return null;
            }
            this.cssBase = new ParsedURL(baseURI);
        }
        return this.cssBase;
    }
    
    public boolean isPseudoInstanceOf(final String s) {
        if (s.equals("first-child")) {
            Node node;
            for (node = this.getPreviousSibling(); node != null && node.getNodeType() != 1; node = node.getPreviousSibling()) {}
            return node == null;
        }
        return false;
    }
    
    public StyleDeclarationProvider getOverrideStyleDeclarationProvider() {
        return null;
    }
    
    public CSSStyleDeclaration getStyle() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public CSSValue getPresentationAttribute(final String s) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public SVGAnimatedString getClassName() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
