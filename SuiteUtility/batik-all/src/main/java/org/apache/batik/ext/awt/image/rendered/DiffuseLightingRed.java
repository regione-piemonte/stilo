// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Map;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.Light;

public class DiffuseLightingRed extends AbstractRed
{
    private double kd;
    private Light light;
    private BumpMap bumpMap;
    private double scaleX;
    private double scaleY;
    private Rectangle litRegion;
    private boolean linear;
    
    public DiffuseLightingRed(final double kd, final Light light, final BumpMap bumpMap, final Rectangle litRegion, final double scaleX, final double scaleY, final boolean linear) {
        this.kd = kd;
        this.light = light;
        this.bumpMap = bumpMap;
        this.litRegion = litRegion;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.linear = linear;
        ColorModel colorModel;
        if (linear) {
            colorModel = GraphicsUtil.Linear_sRGB_Pre;
        }
        else {
            colorModel = GraphicsUtil.sRGB_Pre;
        }
        this.init((CachableRed)null, litRegion, colorModel, colorModel.createCompatibleSampleModel(litRegion.width, litRegion.height), litRegion.x, litRegion.y, null);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
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
        final double n4 = this.scaleX * minX;
        final double n5 = this.scaleY * minY;
        final double[][][] normalArray = this.bumpMap.getNormalArray(minX, minY, width, height);
        if (!this.light.isConstant()) {
            final double[][] array2 = new double[width][3];
            for (int i = 0; i < height; ++i) {
                final double[][] array3 = normalArray[i];
                this.light.getLightRow(n4, n5 + i * this.scaleY, this.scaleX, width, array3, array2);
                for (int j = 0; j < width; ++j) {
                    final double[] array4 = array3[j];
                    final double[] array5 = array2[j];
                    final double n6 = 255.0 * this.kd * (array4[0] * array5[0] + array4[1] * array5[1] + array4[2] * array5[2]);
                    int n7 = (int)(n6 * color[0]);
                    int n8 = (int)(n6 * color[1]);
                    int n9 = (int)(n6 * color[2]);
                    if ((n7 & 0xFFFFFF00) != 0x0) {
                        n7 = (((n7 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    if ((n8 & 0xFFFFFF00) != 0x0) {
                        n8 = (((n8 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    if ((n9 & 0xFFFFFF00) != 0x0) {
                        n9 = (((n9 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    array[n3++] = (0xFF000000 | n7 << 16 | n8 << 8 | n9);
                }
                n3 += n2;
            }
        }
        else {
            final double[] array6 = new double[3];
            this.light.getLight(0.0, 0.0, 0.0, array6);
            for (final double[][] array7 : normalArray) {
                for (final double[] array8 : array7) {
                    final double n10 = 255.0 * this.kd * (array8[0] * array6[0] + array8[1] * array6[1] + array8[2] * array6[2]);
                    int n11 = (int)(n10 * color[0]);
                    int n12 = (int)(n10 * color[1]);
                    int n13 = (int)(n10 * color[2]);
                    if ((n11 & 0xFFFFFF00) != 0x0) {
                        n11 = (((n11 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    if ((n12 & 0xFFFFFF00) != 0x0) {
                        n12 = (((n12 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    if ((n13 & 0xFFFFFF00) != 0x0) {
                        n13 = (((n13 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                    }
                    array[n3++] = (0xFF000000 | n11 << 16 | n12 << 8 | n13);
                }
                n3 += n2;
            }
        }
        return writableRaster;
    }
}
