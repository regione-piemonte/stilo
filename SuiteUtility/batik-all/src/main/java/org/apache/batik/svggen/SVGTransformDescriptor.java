// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SVGTransformDescriptor implements SVGDescriptor, SVGSyntax
{
    private String transform;
    
    public SVGTransformDescriptor(final String transform) {
        this.transform = transform;
    }
    
    public Map getAttributeMap(Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        hashMap.put("transform", this.transform);
        return hashMap;
    }
    
    public List getDefinitionSet(List list) {
        if (list == null) {
            list = new LinkedList();
        }
        return list;
    }
}
