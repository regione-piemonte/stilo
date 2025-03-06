// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.g2d;

import java.awt.geom.AffineTransform;

public abstract class TransformStackElement implements Cloneable
{
    private TransformType type;
    private double[] transformParameters;
    
    protected TransformStackElement(final TransformType type, final double[] transformParameters) {
        this.type = type;
        this.transformParameters = transformParameters;
    }
    
    public Object clone() {
        TransformStackElement transformStackElement = null;
        try {
            transformStackElement = (TransformStackElement)super.clone();
        }
        catch (CloneNotSupportedException ex) {}
        final double[] transformParameters = new double[this.transformParameters.length];
        System.arraycopy(this.transformParameters, 0, transformParameters, 0, transformParameters.length);
        transformStackElement.transformParameters = transformParameters;
        return transformStackElement;
    }
    
    public static TransformStackElement createTranslateElement(final double n, final double n2) {
        return new TransformStackElement(TransformType.TRANSLATE, new double[] { n, n2 }) {
            boolean isIdentity(final double[] array) {
                return array[0] == 0.0 && array[1] == 0.0;
            }
        };
    }
    
    public static TransformStackElement createRotateElement(final double n) {
        return new TransformStackElement(TransformType.ROTATE, new double[] { n }) {
            boolean isIdentity(final double[] array) {
                return Math.cos(array[0]) == 1.0;
            }
        };
    }
    
    public static TransformStackElement createScaleElement(final double n, final double n2) {
        return new TransformStackElement(TransformType.SCALE, new double[] { n, n2 }) {
            boolean isIdentity(final double[] array) {
                return array[0] == 1.0 && array[1] == 1.0;
            }
        };
    }
    
    public static TransformStackElement createShearElement(final double n, final double n2) {
        return new TransformStackElement(TransformType.SHEAR, new double[] { n, n2 }) {
            boolean isIdentity(final double[] array) {
                return array[0] == 0.0 && array[1] == 0.0;
            }
        };
    }
    
    public static TransformStackElement createGeneralTransformElement(final AffineTransform affineTransform) {
        final double[] flatmatrix = new double[6];
        affineTransform.getMatrix(flatmatrix);
        return new TransformStackElement(TransformType.GENERAL, flatmatrix) {
            boolean isIdentity(final double[] array) {
                return array[0] == 1.0 && array[2] == 0.0 && array[4] == 0.0 && array[1] == 0.0 && array[3] == 1.0 && array[5] == 0.0;
            }
        };
    }
    
    abstract boolean isIdentity(final double[] p0);
    
    public boolean isIdentity() {
        return this.isIdentity(this.transformParameters);
    }
    
    public double[] getTransformParameters() {
        return this.transformParameters;
    }
    
    public TransformType getType() {
        return this.type;
    }
    
    public boolean concatenate(final TransformStackElement transformStackElement) {
        boolean b = false;
        if (this.type.toInt() == transformStackElement.type.toInt()) {
            b = true;
            switch (this.type.toInt()) {
                case 0: {
                    final double[] transformParameters = this.transformParameters;
                    final int n = 0;
                    transformParameters[n] += transformStackElement.transformParameters[0];
                    final double[] transformParameters2 = this.transformParameters;
                    final int n2 = 1;
                    transformParameters2[n2] += transformStackElement.transformParameters[1];
                    break;
                }
                case 1: {
                    final double[] transformParameters3 = this.transformParameters;
                    final int n3 = 0;
                    transformParameters3[n3] += transformStackElement.transformParameters[0];
                    break;
                }
                case 2: {
                    final double[] transformParameters4 = this.transformParameters;
                    final int n4 = 0;
                    transformParameters4[n4] *= transformStackElement.transformParameters[0];
                    final double[] transformParameters5 = this.transformParameters;
                    final int n5 = 1;
                    transformParameters5[n5] *= transformStackElement.transformParameters[1];
                    break;
                }
                case 4: {
                    this.transformParameters = this.matrixMultiply(this.transformParameters, transformStackElement.transformParameters);
                    break;
                }
                default: {
                    b = false;
                    break;
                }
            }
        }
        return b;
    }
    
    private double[] matrixMultiply(final double[] flatmatrix, final double[] flatmatrix2) {
        final double[] flatmatrix3 = new double[6];
        final AffineTransform affineTransform = new AffineTransform(flatmatrix);
        affineTransform.concatenate(new AffineTransform(flatmatrix2));
        affineTransform.getMatrix(flatmatrix3);
        return flatmatrix3;
    }
}
