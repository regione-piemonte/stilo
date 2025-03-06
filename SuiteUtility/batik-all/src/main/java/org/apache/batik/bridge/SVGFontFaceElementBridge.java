// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.XLinkSupport;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;

public class SVGFontFaceElementBridge extends AbstractSVGBridge implements ErrorConstants
{
    public String getLocalName() {
        return "font-face";
    }
    
    public SVGFontFace createFontFace(final BridgeContext bridgeContext, final Element element) {
        final String attributeNS = element.getAttributeNS(null, "font-family");
        String attributeNS2 = element.getAttributeNS(null, "units-per-em");
        if (attributeNS2.length() == 0) {
            attributeNS2 = "1000";
        }
        float convertSVGNumber;
        try {
            convertSVGNumber = SVGUtilities.convertSVGNumber(attributeNS2);
        }
        catch (NumberFormatException ex) {
            throw new BridgeException(bridgeContext, element, ex, "attribute.malformed", new Object[] { "units-per-em", attributeNS2 });
        }
        String attributeNS3 = element.getAttributeNS(null, "font-weight");
        if (attributeNS3.length() == 0) {
            attributeNS3 = "all";
        }
        String attributeNS4 = element.getAttributeNS(null, "font-style");
        if (attributeNS4.length() == 0) {
            attributeNS4 = "all";
        }
        String attributeNS5 = element.getAttributeNS(null, "font-variant");
        if (attributeNS5.length() == 0) {
            attributeNS5 = "normal";
        }
        String attributeNS6 = element.getAttributeNS(null, "font-stretch");
        if (attributeNS6.length() == 0) {
            attributeNS6 = "normal";
        }
        String attributeNS7 = element.getAttributeNS(null, "slope");
        if (attributeNS7.length() == 0) {
            attributeNS7 = "0";
        }
        float convertSVGNumber2;
        try {
            convertSVGNumber2 = SVGUtilities.convertSVGNumber(attributeNS7);
        }
        catch (NumberFormatException ex2) {
            throw new BridgeException(bridgeContext, element, ex2, "attribute.malformed", new Object[] { "0", attributeNS7 });
        }
        String attributeNS8 = element.getAttributeNS(null, "panose-1");
        if (attributeNS8.length() == 0) {
            attributeNS8 = "0 0 0 0 0 0 0 0 0 0";
        }
        String s = element.getAttributeNS(null, "ascent");
        if (s.length() == 0) {
            s = String.valueOf(convertSVGNumber * 0.8);
        }
        float convertSVGNumber3;
        try {
            convertSVGNumber3 = SVGUtilities.convertSVGNumber(s);
        }
        catch (NumberFormatException ex3) {
            throw new BridgeException(bridgeContext, element, ex3, "attribute.malformed", new Object[] { "0", s });
        }
        String s2 = element.getAttributeNS(null, "descent");
        if (s2.length() == 0) {
            s2 = String.valueOf(convertSVGNumber * 0.2);
        }
        float convertSVGNumber4;
        try {
            convertSVGNumber4 = SVGUtilities.convertSVGNumber(s2);
        }
        catch (NumberFormatException ex4) {
            throw new BridgeException(bridgeContext, element, ex4, "attribute.malformed", new Object[] { "0", s2 });
        }
        String s3 = element.getAttributeNS(null, "underline-position");
        if (s3.length() == 0) {
            s3 = String.valueOf(-3.0f * convertSVGNumber / 40.0f);
        }
        float convertSVGNumber5;
        try {
            convertSVGNumber5 = SVGUtilities.convertSVGNumber(s3);
        }
        catch (NumberFormatException ex5) {
            throw new BridgeException(bridgeContext, element, ex5, "attribute.malformed", new Object[] { "0", s3 });
        }
        String s4 = element.getAttributeNS(null, "underline-thickness");
        if (s4.length() == 0) {
            s4 = String.valueOf(convertSVGNumber / 20.0f);
        }
        float convertSVGNumber6;
        try {
            convertSVGNumber6 = SVGUtilities.convertSVGNumber(s4);
        }
        catch (NumberFormatException ex6) {
            throw new BridgeException(bridgeContext, element, ex6, "attribute.malformed", new Object[] { "0", s4 });
        }
        String s5 = element.getAttributeNS(null, "strikethrough-position");
        if (s5.length() == 0) {
            s5 = String.valueOf(3.0f * convertSVGNumber3 / 8.0f);
        }
        float convertSVGNumber7;
        try {
            convertSVGNumber7 = SVGUtilities.convertSVGNumber(s5);
        }
        catch (NumberFormatException ex7) {
            throw new BridgeException(bridgeContext, element, ex7, "attribute.malformed", new Object[] { "0", s5 });
        }
        String s6 = element.getAttributeNS(null, "strikethrough-thickness");
        if (s6.length() == 0) {
            s6 = String.valueOf(convertSVGNumber / 20.0f);
        }
        float convertSVGNumber8;
        try {
            convertSVGNumber8 = SVGUtilities.convertSVGNumber(s6);
        }
        catch (NumberFormatException ex8) {
            throw new BridgeException(bridgeContext, element, ex8, "attribute.malformed", new Object[] { "0", s6 });
        }
        String s7 = element.getAttributeNS(null, "overline-position");
        if (s7.length() == 0) {
            s7 = String.valueOf(convertSVGNumber3);
        }
        float convertSVGNumber9;
        try {
            convertSVGNumber9 = SVGUtilities.convertSVGNumber(s7);
        }
        catch (NumberFormatException ex9) {
            throw new BridgeException(bridgeContext, element, ex9, "attribute.malformed", new Object[] { "0", s7 });
        }
        String s8 = element.getAttributeNS(null, "overline-thickness");
        if (s8.length() == 0) {
            s8 = String.valueOf(convertSVGNumber / 20.0f);
        }
        float convertSVGNumber10;
        try {
            convertSVGNumber10 = SVGUtilities.convertSVGNumber(s8);
        }
        catch (NumberFormatException ex10) {
            throw new BridgeException(bridgeContext, element, ex10, "attribute.malformed", new Object[] { "0", s8 });
        }
        List fontFaceSrcs = null;
        final Element parentElement = SVGUtilities.getParentElement(element);
        if (!parentElement.getNamespaceURI().equals("http://www.w3.org/2000/svg") || !parentElement.getLocalName().equals("font")) {
            fontFaceSrcs = this.getFontFaceSrcs(element);
        }
        return new SVGFontFace(element, fontFaceSrcs, attributeNS, convertSVGNumber, attributeNS3, attributeNS4, attributeNS5, attributeNS6, convertSVGNumber2, attributeNS8, convertSVGNumber3, convertSVGNumber4, convertSVGNumber7, convertSVGNumber8, convertSVGNumber5, convertSVGNumber6, convertSVGNumber9, convertSVGNumber10);
    }
    
    public List getFontFaceSrcs(final Element element) {
        Node node = null;
        for (Node node2 = element.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            if (node2.getNodeType() == 1 && node2.getNamespaceURI().equals("http://www.w3.org/2000/svg") && node2.getLocalName().equals("font-face-src")) {
                node = node2;
                break;
            }
        }
        if (node == null) {
            return null;
        }
        final LinkedList<String> list = new LinkedList<String>();
        for (Node node3 = node.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
            if (node3.getNodeType() == 1) {
                if (node3.getNamespaceURI().equals("http://www.w3.org/2000/svg")) {
                    if (node3.getLocalName().equals("font-face-uri")) {
                        final Element element2 = (Element)node3;
                        final String xLinkHref = XLinkSupport.getXLinkHref(element2);
                        final String baseURI = AbstractNode.getBaseURI(element2);
                        ParsedURL parsedURL;
                        if (baseURI != null) {
                            parsedURL = new ParsedURL(baseURI, xLinkHref);
                        }
                        else {
                            parsedURL = new ParsedURL(xLinkHref);
                        }
                        list.add((String)parsedURL);
                    }
                    else if (node3.getLocalName().equals("font-face-name")) {
                        final String attribute = ((Element)node3).getAttribute("name");
                        if (attribute.length() != 0) {
                            list.add(attribute);
                        }
                    }
                }
            }
        }
        return list;
    }
}
