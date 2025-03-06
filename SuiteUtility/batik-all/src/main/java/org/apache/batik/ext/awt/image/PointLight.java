// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

import java.awt.Color;

public class PointLight extends AbstractLight
{
    private double lightX;
    private double lightY;
    private double lightZ;
    
    public double getLightX() {
        return this.lightX;
    }
    
    public double getLightY() {
        return this.lightY;
    }
    
    public double getLightZ() {
        return this.lightZ;
    }
    
    public PointLight(final double lightX, final double lightY, final double lightZ, final Color color) {
        super(color);
        this.lightX = lightX;
        this.lightY = lightY;
        this.lightZ = lightZ;
    }
    
    public boolean isConstant() {
        return false;
    }
    
    public final void getLight(final double n, final double n2, final double n3, final double[] array) {
        double n4 = this.lightX - n;
        double n5 = this.lightY - n2;
        double n6 = this.lightZ - n3;
        final double sqrt = Math.sqrt(n4 * n4 + n5 * n5 + n6 * n6);
        if (sqrt > 0.0) {
            final double n7 = 1.0 / sqrt;
            n4 *= n7;
            n5 *= n7;
            n6 *= n7;
        }
        array[0] = n4;
        array[1] = n5;
        array[2] = n6;
    }
}
