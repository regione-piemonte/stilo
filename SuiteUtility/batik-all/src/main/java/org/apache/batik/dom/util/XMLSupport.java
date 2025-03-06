// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.apache.batik.util.XMLConstants;

public final class XMLSupport implements XMLConstants
{
    private XMLSupport() {
    }
    
    public static String getXMLLang(final Element element) {
        final Attr attributeNodeNS = element.getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "lang");
        if (attributeNodeNS != null) {
            return attributeNodeNS.getNodeValue();
        }
        for (Node node = element.getParentNode(); node != null; node = node.getParentNode()) {
            if (node.getNodeType() == 1) {
                final Attr attributeNodeNS2 = ((Element)node).getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "lang");
                if (attributeNodeNS2 != null) {
                    return attributeNodeNS2.getNodeValue();
                }
            }
        }
        return "en";
    }
    
    public static String getXMLSpace(final Element element) {
        final Attr attributeNodeNS = element.getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "space");
        if (attributeNodeNS != null) {
            return attributeNodeNS.getNodeValue();
        }
        for (Node node = element.getParentNode(); node != null; node = node.getParentNode()) {
            if (node.getNodeType() == 1) {
                final Attr attributeNodeNS2 = ((Element)node).getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", "space");
                if (attributeNodeNS2 != null) {
                    return attributeNodeNS2.getNodeValue();
                }
            }
        }
        return "default";
    }
    
    public static String defaultXMLSpace(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer(length);
        int n = 0;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            switch (char1) {
                case 10:
                case 13: {
                    n = 0;
                    break;
                }
                case 9:
                case 32: {
                    if (n == 0) {
                        sb.append(' ');
                        n = 1;
                        break;
                    }
                    break;
                }
                default: {
                    sb.append(char1);
                    n = 0;
                    break;
                }
            }
        }
        return sb.toString().trim();
    }
    
    public static String preserveXMLSpace(final String s) {
        final StringBuffer sb = new StringBuffer(s.length());
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            switch (char1) {
                case 9:
                case 10:
                case 13: {
                    sb.append(' ');
                    break;
                }
                default: {
                    sb.append(char1);
                    break;
                }
            }
        }
        return sb.toString();
    }
}
