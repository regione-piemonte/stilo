// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import org.w3c.dom.Element;
import java.util.Map;
import org.apache.batik.util.SVGConstants;

public class DefaultStyleHandler implements StyleHandler, SVGConstants
{
    static Map ignoreAttributes;
    
    public void setStyle(final Element element, final Map map, final SVGGeneratorContext svgGeneratorContext) {
        final String tagName = element.getTagName();
        for (final String s : map.keySet()) {
            if (element.getAttributeNS(null, s).length() == 0 && this.appliesTo(s, tagName)) {
                element.setAttributeNS(null, s, map.get(s));
            }
        }
    }
    
    protected boolean appliesTo(final String s, final String s2) {
        final Set set = DefaultStyleHandler.ignoreAttributes.get(s2);
        return set == null || !set.contains(s);
    }
    
    static {
        DefaultStyleHandler.ignoreAttributes = new HashMap();
        final HashSet<String> set = new HashSet<String>();
        set.add("font-size");
        set.add("font-family");
        set.add("font-style");
        set.add("font-weight");
        DefaultStyleHandler.ignoreAttributes.put("rect", set);
        DefaultStyleHandler.ignoreAttributes.put("circle", set);
        DefaultStyleHandler.ignoreAttributes.put("ellipse", set);
        DefaultStyleHandler.ignoreAttributes.put("polygon", set);
        DefaultStyleHandler.ignoreAttributes.put("polygon", set);
        DefaultStyleHandler.ignoreAttributes.put("line", set);
        DefaultStyleHandler.ignoreAttributes.put("path", set);
    }
}
