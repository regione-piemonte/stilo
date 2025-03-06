// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorCmy
{
    public static final ColorCmy CYAN;
    public static final ColorCmy MAGENTA;
    public static final ColorCmy YELLOW;
    public static final ColorCmy BLACK;
    public static final ColorCmy WHITE;
    public static final ColorCmy RED;
    public static final ColorCmy GREEN;
    public static final ColorCmy BLUE;
    public final double C;
    public final double M;
    public final double Y;
    
    public ColorCmy(final double C, final double M, final double Y) {
        this.C = C;
        this.M = M;
        this.Y = Y;
    }
    
    @Override
    public String toString() {
        return "{C: " + this.C + ", M: " + this.M + ", Y: " + this.Y + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorCmy colorCmy = (ColorCmy)o;
        return Double.compare(colorCmy.C, this.C) == 0 && Double.compare(colorCmy.M, this.M) == 0 && Double.compare(colorCmy.Y, this.Y) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.C);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.M);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.Y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        CYAN = new ColorCmy(100.0, 0.0, 0.0);
        MAGENTA = new ColorCmy(0.0, 100.0, 0.0);
        YELLOW = new ColorCmy(0.0, 0.0, 100.0);
        BLACK = new ColorCmy(100.0, 100.0, 100.0);
        WHITE = new ColorCmy(0.0, 0.0, 0.0);
        RED = new ColorCmy(0.0, 100.0, 100.0);
        GREEN = new ColorCmy(100.0, 0.0, 100.0);
        BLUE = new ColorCmy(100.0, 100.0, 0.0);
    }
}
