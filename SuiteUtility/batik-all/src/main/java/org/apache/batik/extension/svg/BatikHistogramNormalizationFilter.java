// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.ext.awt.image.renderable.FilterColorInterpolation;

public interface BatikHistogramNormalizationFilter extends FilterColorInterpolation
{
    Filter getSource();
    
    void setSource(final Filter p0);
    
    float getTrim();
    
    void setTrim(final float p0);
}
