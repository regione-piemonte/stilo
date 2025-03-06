// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

public enum FilterType
{
    Bessel, 
    Blackman, 
    Box, 
    Catrom, 
    Cubic, 
    Gaussian, 
    Hamming, 
    Hanning, 
    Hermite, 
    Lanczos, 
    Mitchell, 
    Point, 
    Quadratic, 
    Sinc, 
    Triangle;
    
    private static FilterType defaultFilter;
    
    public static void setDefaultFilterType(final FilterType defaultFilter) {
        FilterType.defaultFilter = defaultFilter;
    }
    
    public static FilterType getDefaultFilterType() {
        return FilterType.defaultFilter;
    }
    
    static {
        FilterType.defaultFilter = FilterType.Triangle;
    }
}
