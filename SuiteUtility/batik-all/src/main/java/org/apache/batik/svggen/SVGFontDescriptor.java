// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;

public class SVGFontDescriptor implements SVGDescriptor, SVGSyntax
{
    private Element def;
    private String fontSize;
    private String fontWeight;
    private String fontStyle;
    private String fontFamily;
    
    public SVGFontDescriptor(final String fontSize, final String fontWeight, final String fontStyle, final String fontFamily, final Element def) {
        if (fontSize == null || fontWeight == null || fontStyle == null || fontFamily == null) {
            throw new SVGGraphics2DRuntimeException("none of the font description parameters should be null");
        }
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.fontFamily = fontFamily;
        this.def = def;
    }
    
    public Map getAttributeMap(Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        hashMap.put("font-size", this.fontSize);
        hashMap.put("font-weight", this.fontWeight);
        hashMap.put("font-style", this.fontStyle);
        hashMap.put("font-family", this.fontFamily);
        return hashMap;
    }
    
    public Element getDef() {
        return this.def;
    }
    
    public List getDefinitionSet(List list) {
        if (list == null) {
            list = new LinkedList<Element>();
        }
        if (this.def != null && !list.contains(this.def)) {
            list.add(this.def);
        }
        return list;
    }
}
