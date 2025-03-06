// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

public class ConcreteComponentTransferFunction implements ComponentTransferFunction
{
    private int type;
    private float slope;
    private float[] tableValues;
    private float intercept;
    private float amplitude;
    private float exponent;
    private float offset;
    
    private ConcreteComponentTransferFunction() {
    }
    
    public static ComponentTransferFunction getIdentityTransfer() {
        final ConcreteComponentTransferFunction concreteComponentTransferFunction = new ConcreteComponentTransferFunction();
        concreteComponentTransferFunction.type = 0;
        return concreteComponentTransferFunction;
    }
    
    public static ComponentTransferFunction getTableTransfer(final float[] array) {
        final ConcreteComponentTransferFunction concreteComponentTransferFunction = new ConcreteComponentTransferFunction();
        concreteComponentTransferFunction.type = 1;
        if (array == null) {
            throw new IllegalArgumentException();
        }
        if (array.length < 2) {
            throw new IllegalArgumentException();
        }
        System.arraycopy(array, 0, concreteComponentTransferFunction.tableValues = new float[array.length], 0, array.length);
        return concreteComponentTransferFunction;
    }
    
    public static ComponentTransferFunction getDiscreteTransfer(final float[] array) {
        final ConcreteComponentTransferFunction concreteComponentTransferFunction = new ConcreteComponentTransferFunction();
        concreteComponentTransferFunction.type = 2;
        if (array == null) {
            throw new IllegalArgumentException();
        }
        if (array.length < 2) {
            throw new IllegalArgumentException();
        }
        System.arraycopy(array, 0, concreteComponentTransferFunction.tableValues = new float[array.length], 0, array.length);
        return concreteComponentTransferFunction;
    }
    
    public static ComponentTransferFunction getLinearTransfer(final float slope, final float intercept) {
        final ConcreteComponentTransferFunction concreteComponentTransferFunction = new ConcreteComponentTransferFunction();
        concreteComponentTransferFunction.type = 3;
        concreteComponentTransferFunction.slope = slope;
        concreteComponentTransferFunction.intercept = intercept;
        return concreteComponentTransferFunction;
    }
    
    public static ComponentTransferFunction getGammaTransfer(final float amplitude, final float exponent, final float offset) {
        final ConcreteComponentTransferFunction concreteComponentTransferFunction = new ConcreteComponentTransferFunction();
        concreteComponentTransferFunction.type = 4;
        concreteComponentTransferFunction.amplitude = amplitude;
        concreteComponentTransferFunction.exponent = exponent;
        concreteComponentTransferFunction.offset = offset;
        return concreteComponentTransferFunction;
    }
    
    public int getType() {
        return this.type;
    }
    
    public float getSlope() {
        return this.slope;
    }
    
    public float[] getTableValues() {
        return this.tableValues;
    }
    
    public float getIntercept() {
        return this.intercept;
    }
    
    public float getAmplitude() {
        return this.amplitude;
    }
    
    public float getExponent() {
        return this.exponent;
    }
    
    public float getOffset() {
        return this.offset;
    }
}
