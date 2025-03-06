// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import org.apache.batik.gvt.font.GVTFontFace;
import org.apache.batik.gvt.text.TextPaintInfo;
import org.w3c.dom.NodeList;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.Node;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.gvt.font.Glyph;
import org.w3c.dom.Element;
import java.text.AttributedCharacterIterator;

public class SVGAltGlyphElementBridge extends AbstractSVGBridge implements ErrorConstants
{
    public static final AttributedCharacterIterator.Attribute PAINT_INFO;
    
    public String getLocalName() {
        return "altGlyph";
    }
    
    public Glyph[] createAltGlyphArray(final BridgeContext bridgeContext, final Element element, final float n, final AttributedCharacterIterator attributedCharacterIterator) {
        final String xLinkHref = XLinkSupport.getXLinkHref(element);
        Element referencedElement = null;
        try {
            referencedElement = bridgeContext.getReferencedElement(element, xLinkHref);
        }
        catch (BridgeException ex) {
            if ("uri.unsecure".equals(ex.getCode())) {
                bridgeContext.getUserAgent().displayError(ex);
            }
        }
        if (referencedElement == null) {
            return null;
        }
        if (!"http://www.w3.org/2000/svg".equals(referencedElement.getNamespaceURI())) {
            return null;
        }
        if (!referencedElement.getLocalName().equals("glyph")) {
            if (referencedElement.getLocalName().equals("altGlyphDef")) {
                final SVGOMDocument svgomDocument = (SVGOMDocument)element.getOwnerDocument();
                final boolean b = referencedElement.getOwnerDocument() == svgomDocument;
                final Element element2 = (Element)(b ? referencedElement : svgomDocument.importNode(referencedElement, true));
                if (!b) {
                    final String baseURI = AbstractNode.getBaseURI(element);
                    final Element elementNS = svgomDocument.createElementNS("http://www.w3.org/2000/svg", "g");
                    elementNS.appendChild(element2);
                    elementNS.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:base", baseURI);
                    CSSUtilities.computeStyleAndURIs(referencedElement, element2, xLinkHref);
                }
                final NodeList childNodes = element2.getChildNodes();
                boolean b2 = false;
                for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
                    final Node item = childNodes.item(i);
                    if (item.getNodeType() == 1) {
                        final Element element3 = (Element)item;
                        if ("http://www.w3.org/2000/svg".equals(element3.getNamespaceURI()) && "glyphRef".equals(element3.getLocalName())) {
                            b2 = true;
                            break;
                        }
                    }
                }
                if (b2) {
                    final NodeList elementsByTagNameNS = element2.getElementsByTagNameNS("http://www.w3.org/2000/svg", "glyphRef");
                    final int length2 = elementsByTagNameNS.getLength();
                    final Glyph[] array = new Glyph[length2];
                    for (int j = 0; j < length2; ++j) {
                        final Element element4 = (Element)elementsByTagNameNS.item(j);
                        final Glyph glyph = this.getGlyph(bridgeContext, XLinkSupport.getXLinkHref(element4), element4, n, attributedCharacterIterator);
                        if (glyph == null) {
                            return null;
                        }
                        array[j] = glyph;
                    }
                    return array;
                }
                final NodeList elementsByTagNameNS2 = element2.getElementsByTagNameNS("http://www.w3.org/2000/svg", "altGlyphItem");
                final int length3 = elementsByTagNameNS2.getLength();
                if (length3 > 0) {
                    int n2 = 0;
                    Glyph[] array2 = null;
                    for (int n3 = 0; n3 < length3 && n2 == 0; ++n3) {
                        final NodeList elementsByTagNameNS3 = ((Element)elementsByTagNameNS2.item(n3)).getElementsByTagNameNS("http://www.w3.org/2000/svg", "glyphRef");
                        final int length4 = elementsByTagNameNS3.getLength();
                        array2 = new Glyph[length4];
                        n2 = 1;
                        for (int k = 0; k < length4; ++k) {
                            final Element element5 = (Element)elementsByTagNameNS3.item(k);
                            final Glyph glyph2 = this.getGlyph(bridgeContext, XLinkSupport.getXLinkHref(element5), element5, n, attributedCharacterIterator);
                            if (glyph2 == null) {
                                n2 = 0;
                                break;
                            }
                            array2[k] = glyph2;
                        }
                    }
                    if (n2 == 0) {
                        return null;
                    }
                    return array2;
                }
            }
            return null;
        }
        final Glyph glyph3 = this.getGlyph(bridgeContext, xLinkHref, element, n, attributedCharacterIterator);
        if (glyph3 == null) {
            return null;
        }
        return new Glyph[] { glyph3 };
    }
    
    private Glyph getGlyph(final BridgeContext bridgeContext, final String s, final Element element, final float n, final AttributedCharacterIterator attributedCharacterIterator) {
        Element referencedElement = null;
        try {
            referencedElement = bridgeContext.getReferencedElement(element, s);
        }
        catch (BridgeException ex) {
            if ("uri.unsecure".equals(ex.getCode())) {
                bridgeContext.getUserAgent().displayError(ex);
            }
        }
        if (referencedElement == null || !"http://www.w3.org/2000/svg".equals(referencedElement.getNamespaceURI()) || !"glyph".equals(referencedElement.getLocalName())) {
            return null;
        }
        final SVGOMDocument svgomDocument = (SVGOMDocument)element.getOwnerDocument();
        final boolean b = referencedElement.getOwnerDocument() == svgomDocument;
        Element element2 = null;
        Element element3 = null;
        if (b) {
            element2 = referencedElement;
            final NodeList elementsByTagNameNS = ((Element)element2.getParentNode()).getElementsByTagNameNS("http://www.w3.org/2000/svg", "font-face");
            if (elementsByTagNameNS.getLength() > 0) {
                element3 = (Element)elementsByTagNameNS.item(0);
            }
        }
        else {
            final Element element4 = (Element)svgomDocument.importNode(referencedElement.getParentNode(), true);
            final String baseURI = AbstractNode.getBaseURI(element);
            final Element elementNS = svgomDocument.createElementNS("http://www.w3.org/2000/svg", "g");
            elementNS.appendChild(element4);
            elementNS.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:base", baseURI);
            CSSUtilities.computeStyleAndURIs((Element)referencedElement.getParentNode(), element4, s);
            final String attributeNS = referencedElement.getAttributeNS(null, "id");
            final NodeList elementsByTagNameNS2 = element4.getElementsByTagNameNS("http://www.w3.org/2000/svg", "glyph");
            for (int i = 0; i < elementsByTagNameNS2.getLength(); ++i) {
                final Element element5 = (Element)elementsByTagNameNS2.item(i);
                if (element5.getAttributeNS(null, "id").equals(attributeNS)) {
                    element2 = element5;
                    break;
                }
            }
            final NodeList elementsByTagNameNS3 = element4.getElementsByTagNameNS("http://www.w3.org/2000/svg", "font-face");
            if (elementsByTagNameNS3.getLength() > 0) {
                element3 = (Element)elementsByTagNameNS3.item(0);
            }
        }
        if (element2 == null || element3 == null) {
            return null;
        }
        final SVGFontFace fontFace = ((SVGFontFaceElementBridge)bridgeContext.getBridge(element3)).createFontFace(bridgeContext, element3);
        final SVGGlyphElementBridge svgGlyphElementBridge = (SVGGlyphElementBridge)bridgeContext.getBridge(element2);
        attributedCharacterIterator.first();
        return svgGlyphElementBridge.createGlyph(bridgeContext, element2, element, -1, n, fontFace, (TextPaintInfo)attributedCharacterIterator.getAttribute(SVGAltGlyphElementBridge.PAINT_INFO));
    }
    
    static {
        PAINT_INFO = GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO;
    }
}
