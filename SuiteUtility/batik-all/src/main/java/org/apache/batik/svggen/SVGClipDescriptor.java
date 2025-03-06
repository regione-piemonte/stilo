// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;

public class SVGClipDescriptor implements SVGDescriptor, SVGSyntax
{
    private String clipPathValue;
    private Element clipPathDef;
    
    public SVGClipDescriptor(final String clipPathValue, final Element clipPathDef) {
        if (clipPathValue == null) {
            throw new SVGGraphics2DRuntimeException("clipPathValue should not be null");
        }
        this.clipPathValue = clipPathValue;
        this.clipPathDef = clipPathDef;
    }
    
    public Map getAttributeMap(Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        hashMap.put("clip-path", this.clipPathValue);
        return hashMap;
    }
    
    public List getDefinitionSet(List list) {
        if (list == null) {
            list = new LinkedList<Element>();
        }
        if (this.clipPathDef != null) {
            list.add(this.clipPathDef);
        }
        return list;
    }
}
