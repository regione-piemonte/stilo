// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.events.Event;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.Node;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.LinkStyle;
import org.w3c.dom.svg.SVGStyleElement;
import org.apache.batik.css.engine.CSSStyleSheetNode;

public class SVGOMStyleElement extends SVGOMElement implements CSSStyleSheetNode, SVGStyleElement, LinkStyle
{
    protected static final AttributeInitializer attributeInitializer;
    protected transient StyleSheet sheet;
    protected transient org.apache.batik.css.engine.StyleSheet styleSheet;
    protected transient EventListener domCharacterDataModifiedListener;
    
    protected SVGOMStyleElement() {
        this.domCharacterDataModifiedListener = new DOMCharacterDataModifiedListener();
    }
    
    public SVGOMStyleElement(final String s, final AbstractDocument abstractDocument) {
        super(s, abstractDocument);
        this.domCharacterDataModifiedListener = new DOMCharacterDataModifiedListener();
    }
    
    public String getLocalName() {
        return "style";
    }
    
    public org.apache.batik.css.engine.StyleSheet getCSSStyleSheet() {
        if (this.styleSheet == null && this.getType().equals("text/css")) {
            final CSSEngine cssEngine = ((SVGOMDocument)this.getOwnerDocument()).getCSSEngine();
            String string = "";
            Node node = this.getFirstChild();
            if (node != null) {
                final StringBuffer sb = new StringBuffer();
                while (node != null) {
                    if (node.getNodeType() == 4 || node.getNodeType() == 3) {
                        sb.append(node.getNodeValue());
                    }
                    node = node.getNextSibling();
                }
                string = sb.toString();
            }
            ParsedURL parsedURL = null;
            final String baseURI = this.getBaseURI();
            if (baseURI != null) {
                parsedURL = new ParsedURL(baseURI);
            }
            this.styleSheet = cssEngine.parseStyleSheet(string, parsedURL, this.getAttributeNS(null, "media"));
            this.addEventListenerNS("http://www.w3.org/2001/xml-events", "DOMCharacterDataModified", this.domCharacterDataModifiedListener, false, null);
        }
        return this.styleSheet;
    }
    
    public StyleSheet getSheet() {
        throw new UnsupportedOperationException("LinkStyle.getSheet() is not implemented");
    }
    
    public String getXMLspace() {
        return XMLSupport.getXMLSpace(this);
    }
    
    public void setXMLspace(final String s) throws DOMException {
        this.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:space", s);
    }
    
    public String getType() {
        return this.getAttributeNS(null, "type");
    }
    
    public void setType(final String s) throws DOMException {
        this.setAttributeNS(null, "type", s);
    }
    
    public String getMedia() {
        return this.getAttribute("media");
    }
    
    public void setMedia(final String s) throws DOMException {
        this.setAttribute("media", s);
    }
    
    public String getTitle() {
        return this.getAttribute("title");
    }
    
    public void setTitle(final String s) throws DOMException {
        this.setAttribute("title", s);
    }
    
    protected AttributeInitializer getAttributeInitializer() {
        return SVGOMStyleElement.attributeInitializer;
    }
    
    protected Node newNode() {
        return new SVGOMStyleElement();
    }
    
    static {
        (attributeInitializer = new AttributeInitializer(1)).addAttribute("http://www.w3.org/XML/1998/namespace", "xml", "space", "preserve");
    }
    
    protected class DOMCharacterDataModifiedListener implements EventListener
    {
        public void handleEvent(final Event event) {
            SVGOMStyleElement.this.styleSheet = null;
        }
    }
}
