// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorHsv
{
    public static final ColorHsv BLACK;
    public static final ColorHsv WHITE;
    public static final ColorHsv RED;
    public static final ColorHsv GREEN;
    public static final ColorHsv BLUE;
    public final double H;
    public final double S;
    public final double V;
    
    public ColorHsv(final double H, final double S, final double V) {
        this.H = H;
        this.S = S;
        this.V = V;
    }
    
    @Override
    public String toString() {
        return "{H: " + this.H + ", S: " + this.S + ", V: " + this.V + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorHsv colorHsv = (ColorHsv)o;
        return Double.compare(colorHsv.H, this.H) == 0 && Double.compare(colorHsv.S, this.S) == 0 && Double.compare(colorHsv.V, this.V) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.H);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.S);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.V);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        BLACK = new ColorHsv(0.0, 0.0, 0.0);
        WHITE = new ColorHsv(0.0, 0.0, 100.0);
        RED = new ColorHsv(0.0, 100.0, 100.0);
        GREEN = new ColorHsv(120.0, 100.0, 100.0);
        BLUE = new ColorHsv(240.0, 100.0, 100.0);
    }
}
