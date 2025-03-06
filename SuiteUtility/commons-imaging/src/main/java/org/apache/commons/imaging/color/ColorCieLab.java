// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorCieLab
{
    public static final ColorCieLab BLACK;
    public static final ColorCieLab WHITE;
    public static final ColorCieLab RED;
    public static final ColorCieLab GREEN;
    public static final ColorCieLab BLUE;
    public final double L;
    public final double a;
    public final double b;
    
    public ColorCieLab(final double L, final double a, final double b) {
        this.L = L;
        this.a = a;
        this.b = b;
    }
    
    @Override
    public String toString() {
        return "{L: " + this.L + ", a: " + this.a + ", b: " + this.b + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorCieLab that = (ColorCieLab)o;
        return Double.compare(that.L, this.L) == 0 && Double.compare(that.a, this.a) == 0 && Double.compare(that.b, this.b) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.L);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.a);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.b);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        BLACK = new ColorCieLab(0.0, 0.0, 0.0);
        WHITE = new ColorCieLab(100.0, 0.0, 0.0);
        RED = new ColorCieLab(53.0, 80.0, 67.0);
        GREEN = new ColorCieLab(88.0, -86.0, 83.0);
        BLUE = new ColorCieLab(32.0, 79.0, -108.0);
    }
}
