// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

class ClipKey
{
    int hashCodeValue;
    
    public ClipKey(final GeneralPath generalPath, final SVGGeneratorContext svgGeneratorContext) {
        this.hashCodeValue = 0;
        this.hashCodeValue = SVGPath.toSVGPathData(generalPath, svgGeneratorContext).hashCode();
    }
    
    public int hashCode() {
        return this.hashCodeValue;
    }
    
    public boolean equals(final Object o) {
        return o instanceof ClipKey && this.hashCodeValue == ((ClipKey)o).hashCodeValue;
    }
}
