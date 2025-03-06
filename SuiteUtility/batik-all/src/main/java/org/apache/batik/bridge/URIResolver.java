// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.Document;
import org.apache.batik.util.ParsedURL;
import java.io.IOException;
import java.net.MalformedURLException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.dom.svg.SVGOMDocument;

public class URIResolver
{
    protected SVGOMDocument document;
    protected String documentURI;
    protected DocumentLoader documentLoader;
    
    public URIResolver(final SVGDocument svgDocument, final DocumentLoader documentLoader) {
        this.document = (SVGOMDocument)svgDocument;
        this.documentLoader = documentLoader;
    }
    
    public Element getElement(final String s, final Element element) throws MalformedURLException, IOException {
        final Node node = this.getNode(s, element);
        if (node == null) {
            return null;
        }
        if (node.getNodeType() == 9) {
            throw new IllegalArgumentException();
        }
        return (Element)node;
    }
    
    public Node getNode(final String s, final Element element) throws MalformedURLException, IOException, SecurityException {
        final String refererBaseURI = this.getRefererBaseURI(element);
        if (refererBaseURI == null && s.charAt(0) == '#') {
            return this.getNodeByFragment(s.substring(1), element);
        }
        final ParsedURL parsedURL = new ParsedURL(refererBaseURI, s);
        if (this.documentURI == null) {
            this.documentURI = this.document.getURL();
        }
        final String ref = parsedURL.getRef();
        if (ref != null && this.documentURI != null && new ParsedURL(this.documentURI).sameFile(parsedURL)) {
            return this.document.getElementById(ref);
        }
        ParsedURL parsedURL2 = null;
        if (this.documentURI != null) {
            parsedURL2 = new ParsedURL(this.documentURI);
        }
        this.documentLoader.getUserAgent().checkLoadExternalResource(parsedURL, parsedURL2);
        String s2 = parsedURL.toString();
        if (ref != null) {
            s2 = s2.substring(0, s2.length() - (ref.length() + 1));
        }
        final Document loadDocument = this.documentLoader.loadDocument(s2);
        if (ref != null) {
            return loadDocument.getElementById(ref);
        }
        return loadDocument;
    }
    
    protected String getRefererBaseURI(final Element element) {
        return ((AbstractNode)element).getBaseURI();
    }
    
    protected Node getNodeByFragment(final String s, final Element element) {
        return element.getOwnerDocument().getElementById(s);
    }
}
