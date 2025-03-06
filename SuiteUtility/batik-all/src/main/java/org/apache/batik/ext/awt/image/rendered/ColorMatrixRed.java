// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Map;
import java.awt.color.ColorSpace;
import org.apache.batik.ext.awt.image.GraphicsUtil;

public class ColorMatrixRed extends AbstractRed
{
    private float[][] matrix;
    
    public float[][] getMatrix() {
        return this.copyMatrix(this.matrix);
    }
    
    public void setMatrix(final float[][] matrix) {
        final float[][] copyMatrix = this.copyMatrix(matrix);
        if (copyMatrix == null) {
            throw new IllegalArgumentException();
        }
        if (copyMatrix.length != 4) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < 4; ++i) {
            if (copyMatrix[i].length != 5) {
                throw new IllegalArgumentException(String.valueOf(i) + " : " + copyMatrix[i].length);
            }
        }
        this.matrix = matrix;
    }
    
    private float[][] copyMatrix(final float[][] array) {
        if (array == null) {
            return null;
        }
        final float[][] array2 = new float[array.length][];
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                array2[i] = new float[array[i].length];
                System.arraycopy(array[i], 0, array2[i], 0, array[i].length);
            }
        }
        return array2;
    }
    
    public ColorMatrixRed(final CachableRed cachableRed, final float[][] matrix) {
        this.setMatrix(matrix);
        final ColorModel colorModel = cachableRed.getColorModel();
        ColorSpace colorSpace = null;
        if (colorModel != null) {
            colorSpace = colorModel.getColorSpace();
        }
        ColorModel colorModel2;
        if (colorSpace == null) {
            colorModel2 = GraphicsUtil.Linear_sRGB_Unpre;
        }
        else if (colorSpace == ColorSpace.getInstance(1004)) {
            colorModel2 = GraphicsUtil.Linear_sRGB_Unpre;
        }
        else {
            colorModel2 = GraphicsUtil.sRGB_Unpre;
        }
        this.init(cachableRed, cachableRed.getBounds(), colorModel2, colorModel2.createCompatibleSampleModel(cachableRed.getWidth(), cachableRed.getHeight()), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
    }
    
    public WritableRaster copyData(WritableRaster copyData) {
        final CachableRed cachableRed = this.getSources().get(0);
        copyData = cachableRed.copyData(copyData);
        GraphicsUtil.coerceData(copyData, cachableRed.getColorModel(), false);
        final int minX = copyData.getMinX();
        final int minY = copyData.getMinY();
        final int width = copyData.getWidth();
        final int height = copyData.getHeight();
        final DataBufferInt dataBufferInt = (DataBufferInt)copyData.getDataBuffer();
        final int[] array = dataBufferInt.getBankData()[0];
        final int n = dataBufferInt.getOffset() + ((SinglePixelPackedSampleModel)copyData.getSampleModel()).getOffset(minX - copyData.getSampleModelTranslateX(), minY - copyData.getSampleModelTranslateY());
        final int n2 = ((SinglePixelPackedSampleModel)copyData.getSampleModel()).getScanlineStride() - width;
        int n3 = n;
        final float n4 = this.matrix[0][0] / 255.0f;
        final float n5 = this.matrix[0][1] / 255.0f;
        final float n6 = this.matrix[0][2] / 255.0f;
        final float n7 = this.matrix[0][3] / 255.0f;
        final float n8 = this.matrix[0][4] / 255.0f;
        final float n9 = this.matrix[1][0] / 255.0f;
        final float n10 = this.matrix[1][1] / 255.0f;
        final float n11 = this.matrix[1][2] / 255.0f;
        final float n12 = this.matrix[1][3] / 255.0f;
        final float n13 = this.matrix[1][4] / 255.0f;
        final float n14 = this.matrix[2][0] / 255.0f;
        final float n15 = this.matrix[2][1] / 255.0f;
        final float n16 = this.matrix[2][2] / 255.0f;
        final float n17 = this.matrix[2][3] / 255.0f;
        final float n18 = this.matrix[2][4] / 255.0f;
        final float n19 = this.matrix[3][0] / 255.0f;
        final float n20 = this.matrix[3][1] / 255.0f;
        final float n21 = this.matrix[3][2] / 255.0f;
        final float n22 = this.matrix[3][3] / 255.0f;
        final float n23 = this.matrix[3][4] / 255.0f;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final int n24 = array[n3];
                final int n25 = n24 >>> 24;
                final int n26 = n24 >> 16 & 0xFF;
                final int n27 = n24 >> 8 & 0xFF;
                final int n28 = n24 & 0xFF;
                int n29 = (int)((n4 * n26 + n5 * n27 + n6 * n28 + n7 * n25 + n8) * 255.0f);
                int n30 = (int)((n9 * n26 + n10 * n27 + n11 * n28 + n12 * n25 + n13) * 255.0f);
                int n31 = (int)((n14 * n26 + n15 * n27 + n16 * n28 + n17 * n25 + n18) * 255.0f);
                int n32 = (int)((n19 * n26 + n20 * n27 + n21 * n28 + n22 * n25 + n23) * 255.0f);
                if ((n29 & 0xFFFFFF00) != 0x0) {
                    n29 = (((n29 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                if ((n30 & 0xFFFFFF00) != 0x0) {
                    n30 = (((n30 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                if ((n31 & 0xFFFFFF00) != 0x0) {
                    n31 = (((n31 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                if ((n32 & 0xFFFFFF00) != 0x0) {
                    n32 = (((n32 & Integer.MIN_VALUE) != 0x0) ? 0 : 255);
                }
                array[n3++] = (n32 << 24 | n29 << 16 | n30 << 8 | n31);
            }
            n3 += n2;
        }
        return copyData;
    }
}
