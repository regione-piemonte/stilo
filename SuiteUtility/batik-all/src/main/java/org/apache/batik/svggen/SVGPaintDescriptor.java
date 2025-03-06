// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;

public class SVGPaintDescriptor implements SVGDescriptor, SVGSyntax
{
    private Element def;
    private String paintValue;
    private String opacityValue;
    
    public SVGPaintDescriptor(final String paintValue, final String opacityValue) {
        this.paintValue = paintValue;
        this.opacityValue = opacityValue;
    }
    
    public SVGPaintDescriptor(final String s, final String s2, final Element def) {
        this(s, s2);
        this.def = def;
    }
    
    public String getPaintValue() {
        return this.paintValue;
    }
    
    public String getOpacityValue() {
        return this.opacityValue;
    }
    
    public Element getDef() {
        return this.def;
    }
    
    public Map getAttributeMap(Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        hashMap.put("fill", this.paintValue);
        hashMap.put("stroke", this.paintValue);
        hashMap.put("fill-opacity", this.opacityValue);
        hashMap.put("stroke-opacity", this.opacityValue);
        return hashMap;
    }
    
    public List getDefinitionSet(List list) {
        if (list == null) {
            list = new LinkedList<Element>();
        }
        if (this.def != null) {
            list.add(this.def);
        }
        return list;
    }
}
