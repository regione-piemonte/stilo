// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorCieLuv
{
    public static final ColorCieLuv BLACK;
    public static final ColorCieLuv WHITE;
    public static final ColorCieLuv RED;
    public static final ColorCieLuv GREEN;
    public static final ColorCieLuv BLUE;
    public final double L;
    public final double u;
    public final double v;
    
    public ColorCieLuv(final double L, final double u, final double v) {
        this.L = L;
        this.u = u;
        this.v = v;
    }
    
    @Override
    public String toString() {
        return "{L: " + this.L + ", u: " + this.u + ", v: " + this.v + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorCieLuv that = (ColorCieLuv)o;
        return Double.compare(that.L, this.L) == 0 && Double.compare(that.u, this.u) == 0 && Double.compare(that.v, this.v) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.L);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.u);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.v);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        BLACK = new ColorCieLuv(0.0, 0.0, 0.0);
        WHITE = new ColorCieLuv(100.0, 0.0, -0.017);
        RED = new ColorCieLuv(53.233, 175.053, 37.751);
        GREEN = new ColorCieLuv(87.737, -83.08, 107.401);
        BLUE = new ColorCieLuv(32.303, -9.4, -130.358);
    }
}
