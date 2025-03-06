// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SVGHintsDescriptor implements SVGDescriptor, SVGSyntax
{
    private String colorInterpolation;
    private String colorRendering;
    private String textRendering;
    private String shapeRendering;
    private String imageRendering;
    
    public SVGHintsDescriptor(final String colorInterpolation, final String colorRendering, final String textRendering, final String shapeRendering, final String imageRendering) {
        if (colorInterpolation == null || colorRendering == null || textRendering == null || shapeRendering == null || imageRendering == null) {
            throw new SVGGraphics2DRuntimeException("none of the hints description parameters should be null");
        }
        this.colorInterpolation = colorInterpolation;
        this.colorRendering = colorRendering;
        this.textRendering = textRendering;
        this.shapeRendering = shapeRendering;
        this.imageRendering = imageRendering;
    }
    
    public Map getAttributeMap(Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        hashMap.put("color-interpolation", this.colorInterpolation);
        hashMap.put("color-rendering", this.colorRendering);
        hashMap.put("text-rendering", this.textRendering);
        hashMap.put("shape-rendering", this.shapeRendering);
        hashMap.put("image-rendering", this.imageRendering);
        return hashMap;
    }
    
    public List getDefinitionSet(List list) {
        if (list == null) {
            list = new LinkedList();
        }
        return list;
    }
}
