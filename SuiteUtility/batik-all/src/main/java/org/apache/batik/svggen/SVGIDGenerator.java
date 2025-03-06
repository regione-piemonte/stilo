// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.HashMap;
import java.util.Map;

public class SVGIDGenerator
{
    private Map prefixMap;
    
    public SVGIDGenerator() {
        this.prefixMap = new HashMap();
    }
    
    public String generateID(final String str) {
        Integer n = this.prefixMap.get(str);
        if (n == null) {
            n = new Integer(0);
            this.prefixMap.put(str, n);
        }
        final Integer obj = new Integer(n + 1);
        this.prefixMap.put(str, obj);
        return str + obj;
    }
}
