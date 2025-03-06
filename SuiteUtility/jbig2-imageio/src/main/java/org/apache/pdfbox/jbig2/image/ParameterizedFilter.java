// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

import org.apache.pdfbox.jbig2.util.Utils;

class ParameterizedFilter
{
    final Filter filter;
    final double scale;
    final double support;
    final int width;
    
    public ParameterizedFilter(final Filter filter, final double n) {
        this.filter = filter;
        this.scale = filter.blur * Math.max(1.0, 1.0 / n);
        this.support = Math.max(0.5, this.scale * filter.support);
        this.width = (int)Math.ceil(2.0 * this.support);
    }
    
    public ParameterizedFilter(final Filter filter, final double scale, final double support, final int width) {
        this.filter = filter;
        this.scale = scale;
        this.support = support;
        this.width = width;
    }
    
    public double eval(final double n, final int n2) {
        return this.filter.fWindowed((n2 + 0.5 - n) / this.scale);
    }
    
    public int minIndex(final double n) {
        return Utils.floor(n - this.support);
    }
    
    public int maxIndex(final double n) {
        return Utils.ceil(n + this.support);
    }
}
