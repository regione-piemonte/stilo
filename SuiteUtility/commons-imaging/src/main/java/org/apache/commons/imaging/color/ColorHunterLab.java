// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorHunterLab
{
    public static final ColorHunterLab BLACK;
    public static final ColorHunterLab WHITE;
    public static final ColorHunterLab RED;
    public static final ColorHunterLab GREEN;
    public static final ColorHunterLab BLUE;
    public final double L;
    public final double a;
    public final double b;
    
    public ColorHunterLab(final double L, final double a, final double b) {
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
        final ColorHunterLab that = (ColorHunterLab)o;
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
        BLACK = new ColorHunterLab(0.0, 0.0, 0.0);
        WHITE = new ColorHunterLab(100.0, -5.336, 5.433);
        RED = new ColorHunterLab(46.109, 78.962, 29.794);
        GREEN = new ColorHunterLab(84.569, -72.518, 50.842);
        BLUE = new ColorHunterLab(26.87, 72.885, -190.923);
    }
}
