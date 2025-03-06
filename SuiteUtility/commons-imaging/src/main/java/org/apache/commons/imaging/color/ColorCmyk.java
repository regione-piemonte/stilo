// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorCmyk
{
    public static final ColorCmyk CYAN;
    public static final ColorCmyk MAGENTA;
    public static final ColorCmyk YELLOW;
    public static final ColorCmyk BLACK;
    public static final ColorCmyk WHITE;
    public static final ColorCmyk RED;
    public static final ColorCmyk GREEN;
    public static final ColorCmyk BLUE;
    public final double C;
    public final double M;
    public final double Y;
    public final double K;
    
    public ColorCmyk(final double C, final double M, final double Y, final double K) {
        this.C = C;
        this.M = M;
        this.Y = Y;
        this.K = K;
    }
    
    @Override
    public String toString() {
        return "{C: " + this.C + ", M: " + this.M + ", Y: " + this.Y + ", K: " + this.K + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorCmyk colorCmyk = (ColorCmyk)o;
        return Double.compare(colorCmyk.C, this.C) == 0 && Double.compare(colorCmyk.K, this.K) == 0 && Double.compare(colorCmyk.M, this.M) == 0 && Double.compare(colorCmyk.Y, this.Y) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.C);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.M);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.Y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.K);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        CYAN = new ColorCmyk(100.0, 0.0, 0.0, 0.0);
        MAGENTA = new ColorCmyk(0.0, 100.0, 0.0, 0.0);
        YELLOW = new ColorCmyk(0.0, 0.0, 100.0, 0.0);
        BLACK = new ColorCmyk(0.0, 0.0, 0.0, 100.0);
        WHITE = new ColorCmyk(0.0, 0.0, 0.0, 0.0);
        RED = new ColorCmyk(0.0, 100.0, 100.0, 0.0);
        GREEN = new ColorCmyk(100.0, 0.0, 100.0, 0.0);
        BLUE = new ColorCmyk(100.0, 100.0, 0.0, 0.0);
    }
}
