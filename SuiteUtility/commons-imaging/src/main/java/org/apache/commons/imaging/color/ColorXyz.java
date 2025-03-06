// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.color;

public final class ColorXyz
{
    public static final ColorXyz BLACK;
    public static final ColorXyz WHITE;
    public static final ColorXyz RED;
    public static final ColorXyz GREEN;
    public static final ColorXyz BLUE;
    public final double X;
    public final double Y;
    public final double Z;
    
    public ColorXyz(final double X, final double Y, final double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    
    @Override
    public String toString() {
        return "{X: " + this.X + ", Y: " + this.Y + ", Z: " + this.Z + "}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorXyz colorXyz = (ColorXyz)o;
        return Double.compare(colorXyz.X, this.X) == 0 && Double.compare(colorXyz.Y, this.Y) == 0 && Double.compare(colorXyz.Z, this.Z) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.X);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.Y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.Z);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    static {
        BLACK = new ColorXyz(0.0, 0.0, 0.0);
        WHITE = new ColorXyz(95.05, 100.0, 108.9);
        RED = new ColorXyz(41.24, 21.26, 1.93);
        GREEN = new ColorXyz(35.76, 71.52, 11.92);
        BLUE = new ColorXyz(18.05, 7.22, 95.05);
    }
}
