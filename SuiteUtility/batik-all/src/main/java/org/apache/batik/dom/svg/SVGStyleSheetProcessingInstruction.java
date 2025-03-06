// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.HashTable;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.dom.StyleSheetFactory;
import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.css.engine.StyleSheet;
import org.apache.batik.css.engine.CSSStyleSheetNode;
import org.apache.batik.dom.StyleSheetProcessingInstruction;

public class SVGStyleSheetProcessingInstruction extends StyleSheetProcessingInstruction implements CSSStyleSheetNode
{
    protected StyleSheet styleSheet;
    
    protected SVGStyleSheetProcessingInstruction() {
    }
    
    public SVGStyleSheetProcessingInstruction(final String s, final AbstractDocument abstractDocument, final StyleSheetFactory styleSheetFactory) {
        super(s, abstractDocument, styleSheetFactory);
    }
    
    public String getStyleSheetURI() {
        final ParsedURL parsedURL = ((SVGOMDocument)this.getOwnerDocument()).getParsedURL();
        final String s = (String)this.getPseudoAttributes().get("href");
        if (parsedURL != null) {
            return new ParsedURL(parsedURL, s).toString();
        }
        return s;
    }
    
    public StyleSheet getCSSStyleSheet() {
        if (this.styleSheet == null) {
            final HashTable pseudoAttributes = this.getPseudoAttributes();
            if ("text/css".equals(pseudoAttributes.get("type"))) {
                final String title = (String)pseudoAttributes.get("title");
                final String s = (String)pseudoAttributes.get("media");
                final String s2 = (String)pseudoAttributes.get("href");
                final String anObject = (String)pseudoAttributes.get("alternate");
                final SVGOMDocument svgomDocument = (SVGOMDocument)this.getOwnerDocument();
                (this.styleSheet = svgomDocument.getCSSEngine().parseStyleSheet(new ParsedURL(svgomDocument.getParsedURL(), s2), s)).setAlternate("yes".equals(anObject));
                this.styleSheet.setTitle(title);
            }
        }
        return this.styleSheet;
    }
    
    public void setData(final String data) throws DOMException {
        super.setData(data);
        this.styleSheet = null;
    }
    
    protected Node newNode() {
        return new SVGStyleSheetProcessingInstruction();
    }
}
