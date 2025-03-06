// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import org.apache.batik.ext.awt.image.SpotLight;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Map;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.Light;

public class SpecularLightingRed extends AbstractTiledRed
{
    private double ks;
    private double specularExponent;
    private Light light;
    private BumpMap bumpMap;
    private double scaleX;
    private double scaleY;
    private Rectangle litRegion;
    private boolean linear;
    
    public SpecularLightingRed(final double ks, final double specularExponent, final Light light, final BumpMap bumpMap, final Rectangle litRegion, final double scaleX, final double scaleY, final boolean linear) {
        this.ks = ks;
        this.specularExponent = specularExponent;
        this.light = light;
        this.bumpMap = bumpMap;
        this.litRegion = litRegion;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.linear = linear;
        ColorModel colorModel;
        if (linear) {
            colorModel = GraphicsUtil.Linear_sRGB_Unpre;
        }
        else {
            colorModel = GraphicsUtil.sRGB_Unpre;
        }
        int width = litRegion.width;
        int height = litRegion.height;
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        if (width > defaultTileSize) {
            width = defaultTileSize;
        }
        if (height > defaultTileSize) {
            height = defaultTileSize;
        }
        this.init((CachableRed)null, litRegion, colorModel, colorModel.createCompatibleSampleModel(width, height), litRegion.x, litRegion.y, null);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.copyToRaster(writableRaster);
        return writableRaster;
    }
    
    public void genRect(final WritableRaster writableRaster) {
        final double scaleX = this.scaleX;
        final double scaleY = this.scaleY;
        final double[] color = this.light.getColor(this.linear);
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        final int minX = writableRaster.getMinX();
        final int minY = writableRaster.getMinY();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int[] array = dataBufferInt.getBankData()[0];
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(minX - writableRaster.getSampleModelTranslateX(), minY - writableRaster.getSampleModelTranslateY());
        final int n2 = singlePixelPackedSampleModel.getScanlineStride() - width;
        int n3 = n;
        final double n4 = scaleX * minX;
        final double n5 = scaleY * minY;
        final double n6 = (color[0] > color[1]) ? color[0] : color[1];
        final double n7 = (n6 > color[2]) ? n6 : color[2];
        final double n8 = 255.0 / n7;
        final int n9 = ((int)(color[0] * n8 + 0.5) << 8 | (int)(color[1] * n8 + 0.5)) << 8 | (int)(color[2] * n8 + 0.5);
        final double n10 = n7 * (255.0 * this.ks);
        final double[][][] normalArray = this.bumpMap.getNormalArray(minX, minY, width, height);
        if (this.light instanceof SpotLight) {
            final SpotLight spotLight = (SpotLight)this.light;
            final double[][] array2 = new double[width][4];
            for (int i = 0; i < height; ++i) {
                final double[][] array3 = normalArray[i];
                spotLight.getLightRow4(n4, n5 + i * scaleY, scaleX, width, array3, array2);
                for (int j = 0; j < width; ++j) {
                    final double[] array4 = array3[j];
                    final double[] array5 = array2[j];
                    final double n11 = array5[3];
                    int n12;
                    if (n11 == 0.0) {
                        n12 = 0;
                    }
                    else {
                        final double[] array6 = array5;
                        final int n13 = 2;
                        ++array6[n13];
                        n12 = (int)(n10 * (n11 * Math.pow((array4[0] * array5[0] + array4[1] * array5[1] + array4[2] * array5[2]) / Math.sqrt(array5[0] * array5[0] + array5[1] * array5[1] + array5[2] * array5[2]), this.specularExponent)) + 0.5);
                        if ((n12 & 0xFFFFFF00) != 0x0) {
                            n12 = (((n12 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                        }
                    }
                    array[n3++] = (n12 << 24 | n9);
                }
                n3 += n2;
            }
        }
        else if (!this.light.isConstant()) {
            final double[][] array7 = new double[width][4];
            for (int k = 0; k < height; ++k) {
                final double[][] array8 = normalArray[k];
                this.light.getLightRow(n4, n5 + k * scaleY, scaleX, width, array8, array7);
                for (int l = 0; l < width; ++l) {
                    final double[] array9 = array8[l];
                    final double[] array11;
                    final double[] array10 = array11 = array7[l];
                    final int n14 = 2;
                    ++array11[n14];
                    int n15 = (int)(n10 * Math.pow((array9[0] * array10[0] + array9[1] * array10[1] + array9[2] * array10[2]) / Math.sqrt(array10[0] * array10[0] + array10[1] * array10[1] + array10[2] * array10[2]), this.specularExponent) + 0.5);
                    if ((n15 & 0xFFFFFF00) != 0x0) {
                        n15 = (((n15 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    array[n3++] = (n15 << 24 | n9);
                }
                n3 += n2;
            }
        }
        else {
            final double[] array12 = new double[3];
            this.light.getLight(0.0, 0.0, 0.0, array12);
            final double[] array13 = array12;
            final int n16 = 2;
            ++array13[n16];
            final double sqrt = Math.sqrt(array12[0] * array12[0] + array12[1] * array12[1] + array12[2] * array12[2]);
            if (sqrt > 0.0) {
                final double[] array14 = array12;
                final int n17 = 0;
                array14[n17] /= sqrt;
                final double[] array15 = array12;
                final int n18 = 1;
                array15[n18] /= sqrt;
                final double[] array16 = array12;
                final int n19 = 2;
                array16[n19] /= sqrt;
            }
            for (final double[][] array17 : normalArray) {
                for (final double[] array18 : array17) {
                    int n22 = (int)(n10 * Math.pow(array18[0] * array12[0] + array18[1] * array12[1] + array18[2] * array12[2], this.specularExponent) + 0.5);
                    if ((n22 & 0xFFFFFF00) != 0x0) {
                        n22 = (((n22 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    array[n3++] = (n22 << 24 | n9);
                }
                n3 += n2;
            }
        }
    }
}
