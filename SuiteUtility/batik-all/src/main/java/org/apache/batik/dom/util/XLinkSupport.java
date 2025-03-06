// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.apache.batik.util.XMLConstants;

public class XLinkSupport implements XMLConstants
{
    public static String getXLinkType(final Element element) {
        return element.getAttributeNS("http://www.w3.org/1999/xlink", "type");
    }
    
    public static void setXLinkType(final Element element, final String str) {
        if (!"simple".equals(str) && !"extended".equals(str) && !"locator".equals(str) && !"arc".equals(str)) {
            throw new DOMException((short)12, "xlink:type='" + str + "'");
        }
        element.setAttributeNS("http://www.w3.org/1999/xlink", "type", str);
    }
    
    public static String getXLinkRole(final Element element) {
        return element.getAttributeNS("http://www.w3.org/1999/xlink", "role");
    }
    
    public static void setXLinkRole(final Element element, final String s) {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "role", s);
    }
    
    public static String getXLinkArcRole(final Element element) {
        return element.getAttributeNS("http://www.w3.org/1999/xlink", "arcrole");
    }
    
    public static void setXLinkArcRole(final Element element, final String s) {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "arcrole", s);
    }
    
    public static String getXLinkTitle(final Element element) {
        return element.getAttributeNS("http://www.w3.org/1999/xlink", "title");
    }
    
    public static void setXLinkTitle(final Element element, final String s) {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "title", s);
    }
    
    public static String getXLinkShow(final Element element) {
        return element.getAttributeNS("http://www.w3.org/1999/xlink", "show");
    }
    
    public static void setXLinkShow(final Element element, final String s) {
        if (!"new".equals(s) && !"replace".equals(s) && !"embed".equals(s)) {
            throw new DOMException((short)12, "xlink:show='" + s + "'");
        }
        element.setAttributeNS("http://www.w3.org/1999/xlink", "show", s);
    }
    
    public static String getXLinkActuate(final Element element) {
        return element.getAttributeNS("http://www.w3.org/1999/xlink", "actuate");
    }
    
    public static void setXLinkActuate(final Element element, final String str) {
        if (!"onReplace".equals(str) && !"onLoad".equals(str)) {
            throw new DOMException((short)12, "xlink:actuate='" + str + "'");
        }
        element.setAttributeNS("http://www.w3.org/1999/xlink", "actuate", str);
    }
    
    public static String getXLinkHref(final Element element) {
        return element.getAttributeNS("http://www.w3.org/1999/xlink", "href");
    }
    
    public static void setXLinkHref(final Element element, final String s) {
        element.setAttributeNS("http://www.w3.org/1999/xlink", "href", s);
    }
}
