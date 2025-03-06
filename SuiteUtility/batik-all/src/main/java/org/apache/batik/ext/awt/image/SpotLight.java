// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

import java.awt.Color;

public class SpotLight extends AbstractLight
{
    private double lightX;
    private double lightY;
    private double lightZ;
    private double pointAtX;
    private double pointAtY;
    private double pointAtZ;
    private double specularExponent;
    private double limitingConeAngle;
    private double limitingCos;
    private final double[] S;
    
    public double getLightX() {
        return this.lightX;
    }
    
    public double getLightY() {
        return this.lightY;
    }
    
    public double getLightZ() {
        return this.lightZ;
    }
    
    public double getPointAtX() {
        return this.pointAtX;
    }
    
    public double getPointAtY() {
        return this.pointAtY;
    }
    
    public double getPointAtZ() {
        return this.pointAtZ;
    }
    
    public double getSpecularExponent() {
        return this.specularExponent;
    }
    
    public double getLimitingConeAngle() {
        return this.limitingConeAngle;
    }
    
    public SpotLight(final double lightX, final double lightY, final double lightZ, final double pointAtX, final double pointAtY, final double pointAtZ, final double specularExponent, final double n, final Color color) {
        super(color);
        this.S = new double[3];
        this.lightX = lightX;
        this.lightY = lightY;
        this.lightZ = lightZ;
        this.pointAtX = pointAtX;
        this.pointAtY = pointAtY;
        this.pointAtZ = pointAtZ;
        this.specularExponent = specularExponent;
        this.limitingConeAngle = n;
        this.limitingCos = Math.cos(Math.toRadians(n));
        this.S[0] = pointAtX - lightX;
        this.S[1] = pointAtY - lightY;
        this.S[2] = pointAtZ - lightZ;
        final double n2 = 1.0 / Math.sqrt(this.S[0] * this.S[0] + this.S[1] * this.S[1] + this.S[2] * this.S[2]);
        final double[] s = this.S;
        final int n3 = 0;
        s[n3] *= n2;
        final double[] s2 = this.S;
        final int n4 = 1;
        s2[n4] *= n2;
        final double[] s3 = this.S;
        final int n5 = 2;
        s3[n5] *= n2;
    }
    
    public boolean isConstant() {
        return false;
    }
    
    public final double getLightBase(final double n, final double n2, final double n3, final double[] array) {
        final double n4 = this.lightX - n;
        final double n5 = this.lightY - n2;
        final double n6 = this.lightZ - n3;
        final double n7 = 1.0 / Math.sqrt(n4 * n4 + n5 * n5 + n6 * n6);
        final double n8 = n4 * n7;
        final double n9 = n5 * n7;
        final double n10 = n6 * n7;
        final double a = -(n8 * this.S[0] + n9 * this.S[1] + n10 * this.S[2]);
        array[0] = n8;
        array[1] = n9;
        array[2] = n10;
        if (a <= this.limitingCos) {
            return 0.0;
        }
        final double n11 = this.limitingCos / a;
        final double n12 = n11 * n11;
        final double n13 = n12 * n12;
        final double n14 = n13 * n13;
        final double n15 = n14 * n14;
        final double n16 = n15 * n15;
        return (1.0 - n16 * n16) * Math.pow(a, this.specularExponent);
    }
    
    public final void getLight(final double n, final double n2, final double n3, final double[] array) {
        final double lightBase = this.getLightBase(n, n2, n3, array);
        final int n4 = 0;
        array[n4] *= lightBase;
        final int n5 = 1;
        array[n5] *= lightBase;
        final int n6 = 2;
        array[n6] *= lightBase;
    }
    
    public final void getLight4(final double n, final double n2, final double n3, final double[] array) {
        array[3] = this.getLightBase(n, n2, n3, array);
    }
    
    public double[][] getLightRow4(double n, final double n2, final double n3, final int n4, final double[][] array, final double[][] array2) {
        double[][] array3 = array2;
        if (array3 == null) {
            array3 = new double[n4][4];
        }
        for (int i = 0; i < n4; ++i) {
            this.getLight4(n, n2, array[i][3], array3[i]);
            n += n3;
        }
        return array3;
    }
}
