// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.awt.Font;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.svg.SVGDocument;
import java.util.Iterator;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.gvt.font.AWTFontFamily;
import org.apache.batik.gvt.font.FontFamilyResolver;
import org.apache.batik.gvt.font.GVTFontFamily;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.batik.gvt.font.GVTFontFace;

public abstract class FontFace extends GVTFontFace implements ErrorConstants
{
    List srcs;
    
    public FontFace(final List srcs, final String s, final float n, final String s2, final String s3, final String s4, final String s5, final float n2, final String s6, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10) {
        super(s, n, s2, s3, s4, s5, n2, s6, n3, n4, n5, n6, n7, n8, n9, n10);
        this.srcs = srcs;
    }
    
    protected FontFace(final String s) {
        super(s);
    }
    
    public static CSSFontFace createFontFace(final String s, final FontFace fontFace) {
        return new CSSFontFace(new LinkedList(fontFace.srcs), s, fontFace.unitsPerEm, fontFace.fontWeight, fontFace.fontStyle, fontFace.fontVariant, fontFace.fontStretch, fontFace.slope, fontFace.panose1, fontFace.ascent, fontFace.descent, fontFace.strikethroughPosition, fontFace.strikethroughThickness, fontFace.underlinePosition, fontFace.underlineThickness, fontFace.overlinePosition, fontFace.overlineThickness);
    }
    
    public GVTFontFamily getFontFamily(final BridgeContext bridgeContext) {
        final String lookup = FontFamilyResolver.lookup(this.familyName);
        if (lookup != null) {
            return new AWTFontFamily(createFontFace(lookup, this));
        }
        for (final String next : this.srcs) {
            if (next instanceof String) {
                final String s = next;
                if (FontFamilyResolver.lookup(s) != null) {
                    return new AWTFontFamily(createFontFace(s, this));
                }
                continue;
            }
            else {
                if (!(next instanceof ParsedURL)) {
                    continue;
                }
                try {
                    final GVTFontFamily fontFamily = this.getFontFamily(bridgeContext, (ParsedURL)next);
                    if (fontFamily != null) {
                        return fontFamily;
                    }
                    continue;
                }
                catch (SecurityException ex) {
                    bridgeContext.getUserAgent().displayError(ex);
                }
                catch (BridgeException ex2) {
                    if (!"uri.unsecure".equals(ex2.getCode())) {
                        continue;
                    }
                    bridgeContext.getUserAgent().displayError(ex2);
                }
                catch (Exception ex3) {}
            }
        }
        return new AWTFontFamily(this);
    }
    
    protected GVTFontFamily getFontFamily(final BridgeContext bridgeContext, final ParsedURL parsedURL) {
        final String string = parsedURL.toString();
        final Element baseElement = this.getBaseElement(bridgeContext);
        final String url = ((SVGDocument)baseElement.getOwnerDocument()).getURL();
        ParsedURL parsedURL2 = null;
        if (url != null) {
            parsedURL2 = new ParsedURL(url);
        }
        final ParsedURL parsedURL3 = new ParsedURL(AbstractNode.getBaseURI(baseElement), string);
        final UserAgent userAgent = bridgeContext.getUserAgent();
        try {
            userAgent.checkLoadExternalResource(parsedURL3, parsedURL2);
        }
        catch (SecurityException ex) {
            userAgent.displayError(ex);
            return null;
        }
        if (parsedURL3.getRef() != null) {
            final Element referencedElement = bridgeContext.getReferencedElement(baseElement, string);
            if (!referencedElement.getNamespaceURI().equals("http://www.w3.org/2000/svg") || !referencedElement.getLocalName().equals("font")) {
                return null;
            }
            final SVGDocument svgDocument = (SVGDocument)baseElement.getOwnerDocument();
            final SVGDocument svgDocument2 = (SVGDocument)referencedElement.getOwnerDocument();
            Element element = referencedElement;
            if (svgDocument != svgDocument2) {
                element = (Element)svgDocument.importNode((Node)referencedElement, true);
                final String baseURI = AbstractNode.getBaseURI(referencedElement);
                final Element elementNS = svgDocument.createElementNS("http://www.w3.org/2000/svg", "g");
                elementNS.appendChild(element);
                elementNS.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:base", baseURI);
                CSSUtilities.computeStyleAndURIs(referencedElement, element, string);
            }
            Element element2 = null;
            for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 1 && node.getNamespaceURI().equals("http://www.w3.org/2000/svg") && node.getLocalName().equals("font-face")) {
                    element2 = (Element)node;
                    break;
                }
            }
            return new SVGFontFamily(((SVGFontFaceElementBridge)bridgeContext.getBridge("http://www.w3.org/2000/svg", "font-face")).createFontFace(bridgeContext, element2), element, bridgeContext);
        }
        else {
            try {
                return new AWTFontFamily(this, Font.createFont(0, parsedURL3.openStream()));
            }
            catch (Exception ex2) {
                return null;
            }
        }
    }
    
    protected Element getBaseElement(final BridgeContext bridgeContext) {
        return (Element)((SVGDocument)bridgeContext.getDocument()).getRootElement();
    }
}
