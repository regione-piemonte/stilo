// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;

public class SVGCompositeDescriptor implements SVGDescriptor, SVGSyntax
{
    private Element def;
    private String opacityValue;
    private String filterValue;
    
    public SVGCompositeDescriptor(final String opacityValue, final String filterValue) {
        this.opacityValue = opacityValue;
        this.filterValue = filterValue;
    }
    
    public SVGCompositeDescriptor(final String s, final String s2, final Element def) {
        this(s, s2);
        this.def = def;
    }
    
    public String getOpacityValue() {
        return this.opacityValue;
    }
    
    public String getFilterValue() {
        return this.filterValue;
    }
    
    public Element getDef() {
        return this.def;
    }
    
    public Map getAttributeMap(Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        hashMap.put("opacity", this.opacityValue);
        hashMap.put("filter", this.filterValue);
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
