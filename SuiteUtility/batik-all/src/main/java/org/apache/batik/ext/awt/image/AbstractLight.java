// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

import java.awt.Color;

public abstract class AbstractLight implements Light
{
    private double[] color;
    
    public static final double sRGBToLsRGB(final double n) {
        if (n <= 0.003928) {
            return n / 12.92;
        }
        return Math.pow((n + 0.055) / 1.055, 2.4);
    }
    
    public double[] getColor(final boolean b) {
        final double[] array = new double[3];
        if (b) {
            array[0] = sRGBToLsRGB(this.color[0]);
            array[1] = sRGBToLsRGB(this.color[1]);
            array[2] = sRGBToLsRGB(this.color[2]);
        }
        else {
            array[0] = this.color[0];
            array[1] = this.color[1];
            array[2] = this.color[2];
        }
        return array;
    }
    
    public AbstractLight(final Color color) {
        this.setColor(color);
    }
    
    public void setColor(final Color color) {
        (this.color = new double[3])[0] = color.getRed() / 255.0;
        this.color[1] = color.getGreen() / 255.0;
        this.color[2] = color.getBlue() / 255.0;
    }
    
    public boolean isConstant() {
        return true;
    }
    
    public double[][][] getLightMap(final double n, double n2, final double n3, final double n4, final int n5, final int n6, final double[][][] array) {
        final double[][][] array2 = new double[n6][][];
        for (int i = 0; i < n6; ++i) {
            array2[i] = this.getLightRow(n, n2, n3, n5, array[i], null);
            n2 += n4;
        }
        return array2;
    }
    
    public double[][] getLightRow(double n, final double n2, final double n3, final int n4, final double[][] array, final double[][] array2) {
        double[][] array3 = array2;
        if (array3 == null) {
            array3 = new double[n4][3];
        }
        for (int i = 0; i < n4; ++i) {
            this.getLight(n, n2, array[i][3], array3[i]);
            n += n3;
        }
        return array3;
    }
}
