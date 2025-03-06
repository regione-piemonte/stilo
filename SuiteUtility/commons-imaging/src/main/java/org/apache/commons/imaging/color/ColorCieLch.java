// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorCieLch
{
    public static final ColorCieLch BLACK;
    public static final ColorCieLch WHITE;
    public static final ColorCieLch RED;
    public static final ColorCieLch GREEN;
    public static final ColorCieLch BLUE;
    public final double L;
    public final double C;
    public final double H;
    
    public ColorCieLch(final double L, final double C, final double H) {
        this.L = L;
        this.C = C;
        this.H = H;
    }
    
    @Override
    public String toString() {
        return "{L: " + this.L + ", C: " + this.C + ", H: " + this.H + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorCieLch that = (ColorCieLch)o;
        return Double.compare(that.C, this.C) == 0 && Double.compare(that.H, this.H) == 0 && Double.compare(that.L, this.L) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.L);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.C);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.H);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        BLACK = new ColorCieLch(0.0, 0.0, 0.0);
        WHITE = new ColorCieLch(100.0, 0.0, 297.0);
        RED = new ColorCieLch(53.0, 80.0, 67.0);
        GREEN = new ColorCieLch(88.0, -86.0, 83.0);
        BLUE = new ColorCieLch(32.0, 79.0, -108.0);
    }
}
