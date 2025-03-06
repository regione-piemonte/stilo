// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

import java.awt.Color;

public class DistantLight extends AbstractLight
{
    private double azimuth;
    private double elevation;
    private double Lx;
    private double Ly;
    private double Lz;
    
    public double getAzimuth() {
        return this.azimuth;
    }
    
    public double getElevation() {
        return this.elevation;
    }
    
    public DistantLight(final double angdeg, final double n, final Color color) {
        super(color);
        this.azimuth = angdeg;
        this.elevation = n;
        this.Lx = Math.cos(Math.toRadians(angdeg)) * Math.cos(Math.toRadians(n));
        this.Ly = Math.sin(Math.toRadians(angdeg)) * Math.cos(Math.toRadians(n));
        this.Lz = Math.sin(Math.toRadians(n));
    }
    
    public boolean isConstant() {
        return true;
    }
    
    public void getLight(final double n, final double n2, final double n3, final double[] array) {
        array[0] = this.Lx;
        array[1] = this.Ly;
        array[2] = this.Lz;
    }
    
    public double[][] getLightRow(final double n, final double n2, final double n3, final int n4, final double[][] array, final double[][] array2) {
        double[][] array3 = array2;
        if (array3 == null) {
            array3 = new double[n4][];
            final double[] array4 = { this.Lx, this.Ly, this.Lz };
            for (int i = 0; i < n4; ++i) {
                array3[i] = array4;
            }
        }
        else {
            final double lx = this.Lx;
            final double ly = this.Ly;
            final double lz = this.Lz;
            for (int j = 0; j < n4; ++j) {
                array3[j][0] = lx;
                array3[j][1] = ly;
                array3[j][2] = lz;
            }
        }
        return array3;
    }
}
