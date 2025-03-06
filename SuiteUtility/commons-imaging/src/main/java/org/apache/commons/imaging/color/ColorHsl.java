// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorHsl
{
    public static final ColorHsl BLACK;
    public static final ColorHsl WHITE;
    public static final ColorHsl RED;
    public static final ColorHsl GREEN;
    public static final ColorHsl BLUE;
    public final double H;
    public final double S;
    public final double L;
    
    public ColorHsl(final double H, final double S, final double L) {
        this.H = H;
        this.S = S;
        this.L = L;
    }
    
    @Override
    public String toString() {
        return "{H: " + this.H + ", S: " + this.S + ", L: " + this.L + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorHsl colorHsl = (ColorHsl)o;
        return Double.compare(colorHsl.H, this.H) == 0 && Double.compare(colorHsl.L, this.L) == 0 && Double.compare(colorHsl.S, this.S) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.H);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.S);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.L);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        BLACK = new ColorHsl(0.0, 0.0, 0.0);
        WHITE = new ColorHsl(0.0, 0.0, 100.0);
        RED = new ColorHsl(0.0, 100.0, 100.0);
        GREEN = new ColorHsl(120.0, 100.0, 100.0);
        BLUE = new ColorHsl(240.0, 100.0, 100.0);
    }
}
