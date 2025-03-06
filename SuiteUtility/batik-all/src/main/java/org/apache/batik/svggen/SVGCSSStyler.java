// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SVGCSSStyler implements SVGSyntax
{
    private static final char CSS_PROPERTY_VALUE_SEPARATOR = ':';
    private static final char CSS_RULE_SEPARATOR = ';';
    private static final char SPACE = ' ';
    
    public static void style(final Node node) {
        final NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            final Element element = (Element)node;
            final StringBuffer sb = new StringBuffer();
            final int length = attributes.getLength();
            final ArrayList list = new ArrayList<Object>();
            for (int i = 0; i < length; ++i) {
                final Attr attr = (Attr)attributes.item(i);
                final String name = attr.getName();
                if (SVGStylingAttributes.set.contains(name)) {
                    sb.append(name);
                    sb.append(':');
                    sb.append(attr.getValue());
                    sb.append(';');
                    sb.append(' ');
                    list.add(name);
                }
            }
            if (sb.length() > 0) {
                element.setAttributeNS(null, "style", sb.toString().trim());
                for (int size = list.size(), j = 0; j < size; ++j) {
                    element.removeAttribute((String)list.get(j));
                }
            }
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length2 = childNodes.getLength(), k = 0; k < length2; ++k) {
            style(childNodes.item(k));
        }
    }
}
