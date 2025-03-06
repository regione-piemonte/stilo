// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DataBufferInt;
import java.awt.Rectangle;
import java.awt.image.RenderedImage;

public final class BumpMap
{
    private RenderedImage texture;
    private double surfaceScale;
    private double surfaceScaleX;
    private double surfaceScaleY;
    private double scaleX;
    private double scaleY;
    
    public BumpMap(final RenderedImage texture, final double surfaceScale, final double scaleX, final double scaleY) {
        this.texture = texture;
        this.surfaceScaleX = surfaceScale * scaleX;
        this.surfaceScaleY = surfaceScale * scaleY;
        this.surfaceScale = surfaceScale;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
    
    public double getSurfaceScale() {
        return this.surfaceScale;
    }
    
    public double[][][] getNormalArray(final int n, final int n2, final int n3, final int n4) {
        final double[][][] array = new double[n4][n3][4];
        final Rectangle rectangle = new Rectangle(n - 1, n2 - 1, n3 + 2, n4 + 2);
        final Rectangle rectangle2 = new Rectangle(this.texture.getMinX(), this.texture.getMinY(), this.texture.getWidth(), this.texture.getHeight());
        if (!rectangle.intersects(rectangle2)) {
            return array;
        }
        final Raster data = this.texture.getData(rectangle.intersection(rectangle2));
        final Rectangle bounds = data.getBounds();
        final DataBufferInt dataBufferInt = (DataBufferInt)data.getDataBuffer();
        final int[] array2 = dataBufferInt.getBankData()[0];
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)data.getSampleModel();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int n5 = scanlineStride + 1;
        final int n6 = scanlineStride - 1;
        final double n7 = this.surfaceScaleX / 4.0;
        final double n8 = this.surfaceScaleY / 4.0;
        final double n9 = this.surfaceScaleX / 2.0;
        final double n10 = this.surfaceScaleY / 2.0;
        final double n11 = this.surfaceScaleX / 3.0;
        final double n12 = this.surfaceScaleY / 3.0;
        final double n13 = this.surfaceScaleX * 2.0 / 3.0;
        final double n14 = this.surfaceScaleY * 2.0 / 3.0;
        if (n3 <= 0) {
            return array;
        }
        if (n4 <= 0) {
            return array;
        }
        final int min = Math.min(bounds.x + bounds.width - 1, n + n3);
        final int min2 = Math.min(bounds.y + bounds.height - 1, n2 + n4);
        final int n15 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(bounds.x - data.getSampleModelTranslateX(), bounds.y - data.getSampleModelTranslateY());
        int i = n2;
        if (i < bounds.y) {
            i = bounds.y;
        }
        if (i == bounds.y) {
            if (i == min2) {
                final double[][] array3 = array[i - n2];
                int j = n;
                if (j < bounds.x) {
                    j = bounds.x;
                }
                int n16 = n15 + (j - bounds.x) + scanlineStride * (i - bounds.y);
                double n17 = (array2[n16] >>> 24) * 0.00392156862745098;
                double n18;
                if (j != bounds.x) {
                    n18 = (array2[n16 - 1] >>> 24) * 0.00392156862745098;
                }
                else if (j < min) {
                    final double n19 = (array2[n16 + 1] >>> 24) * 0.00392156862745098;
                    final double[] array4 = array3[j - n];
                    array4[0] = 2.0 * this.surfaceScaleX * (n17 - n19);
                    final double n20 = 1.0 / Math.sqrt(array4[0] * array4[0] + 1.0);
                    final double[] array5 = array4;
                    final int n21 = 0;
                    array5[n21] *= n20;
                    array4[1] = 0.0;
                    array4[2] = n20;
                    array4[3] = n17 * this.surfaceScale;
                    ++n16;
                    ++j;
                    n18 = n17;
                    n17 = n19;
                }
                else {
                    n18 = n17;
                }
                while (j < min) {
                    final double n22 = (array2[n16 + 1] >>> 24) * 0.00392156862745098;
                    final double[] array6 = array3[j - n];
                    array6[0] = this.surfaceScaleX * (n18 - n22);
                    final double n23 = 1.0 / Math.sqrt(array6[0] * array6[0] + 1.0);
                    final double[] array7 = array6;
                    final int n24 = 0;
                    array7[n24] *= n23;
                    array6[1] = 0.0;
                    array6[2] = n23;
                    array6[3] = n17 * this.surfaceScale;
                    ++n16;
                    n18 = n17;
                    n17 = n22;
                    ++j;
                }
                if (j < n + n3 && j == bounds.x + bounds.width - 1) {
                    final double[] array8 = array3[j - n];
                    array8[0] = 2.0 * this.surfaceScaleX * (n18 - n17);
                    final double n25 = 1.0 / Math.sqrt(array8[0] * array8[0] + array8[1] * array8[1] + 1.0);
                    final double[] array9 = array8;
                    final int n26 = 0;
                    array9[n26] *= n25;
                    final double[] array10 = array8;
                    final int n27 = 1;
                    array10[n27] *= n25;
                    array8[2] = n25;
                    array8[3] = n17 * this.surfaceScale;
                }
                return array;
            }
            final double[][] array11 = array[i - n2];
            final int n28 = n15 + scanlineStride * (i - bounds.y);
            int k = n;
            if (k < bounds.x) {
                k = bounds.x;
            }
            int n29 = n28 + (k - bounds.x);
            double n30 = (array2[n29] >>> 24) * 0.00392156862745098;
            double n31 = (array2[n29 + scanlineStride] >>> 24) * 0.00392156862745098;
            double n32;
            double n33;
            if (k != bounds.x) {
                n32 = (array2[n29 - 1] >>> 24) * 0.00392156862745098;
                n33 = (array2[n29 + n6] >>> 24) * 0.00392156862745098;
            }
            else if (k < min) {
                final double n34 = (array2[n29 + 1] >>> 24) * 0.00392156862745098;
                final double n35 = (array2[n29 + n5] >>> 24) * 0.00392156862745098;
                final double[] array12 = array11[k - n];
                array12[0] = -n13 * (2.0 * n34 + n35 - 2.0 * n30 - n31);
                array12[1] = -n14 * (2.0 * n31 + n35 - 2.0 * n30 - n34);
                final double n36 = 1.0 / Math.sqrt(array12[0] * array12[0] + array12[1] * array12[1] + 1.0);
                final double[] array13 = array12;
                final int n37 = 0;
                array13[n37] *= n36;
                final double[] array14 = array12;
                final int n38 = 1;
                array14[n38] *= n36;
                array12[2] = n36;
                array12[3] = n30 * this.surfaceScale;
                ++n29;
                ++k;
                n32 = n30;
                n33 = n31;
                n30 = n34;
                n31 = n35;
            }
            else {
                n32 = n30;
                n33 = n31;
            }
            while (k < min) {
                final double n39 = (array2[n29 + 1] >>> 24) * 0.00392156862745098;
                final double n40 = (array2[n29 + n5] >>> 24) * 0.00392156862745098;
                final double[] array15 = array11[k - n];
                array15[0] = -n11 * (2.0 * n39 + n40 - (2.0 * n32 + n33));
                array15[1] = -n10 * (n33 + 2.0 * n31 + n40 - (n32 + 2.0 * n30 + n39));
                final double n41 = 1.0 / Math.sqrt(array15[0] * array15[0] + array15[1] * array15[1] + 1.0);
                final double[] array16 = array15;
                final int n42 = 0;
                array16[n42] *= n41;
                final double[] array17 = array15;
                final int n43 = 1;
                array17[n43] *= n41;
                array15[2] = n41;
                array15[3] = n30 * this.surfaceScale;
                ++n29;
                n32 = n30;
                n33 = n31;
                n30 = n39;
                n31 = n40;
                ++k;
            }
            if (k < n + n3 && k == bounds.x + bounds.width - 1) {
                final double[] array18 = array11[k - n];
                array18[0] = -n13 * (2.0 * n30 + n31 - (2.0 * n32 + n33));
                array18[1] = -n14 * (2.0 * n31 + n33 - (2.0 * n30 + n32));
                final double n44 = 1.0 / Math.sqrt(array18[0] * array18[0] + array18[1] * array18[1] + 1.0);
                final double[] array19 = array18;
                final int n45 = 0;
                array19[n45] *= n44;
                final double[] array20 = array18;
                final int n46 = 1;
                array20[n46] *= n44;
                array18[2] = n44;
                array18[3] = n30 * this.surfaceScale;
            }
            ++i;
        }
        while (i < min2) {
            final double[][] array21 = array[i - n2];
            final int n47 = n15 + scanlineStride * (i - bounds.y);
            int l = n;
            if (l < bounds.x) {
                l = bounds.x;
            }
            int n48 = n47 + (l - bounds.x);
            double n49 = (array2[n48 - scanlineStride] >>> 24) * 0.00392156862745098;
            double n50 = (array2[n48] >>> 24) * 0.00392156862745098;
            double n51 = (array2[n48 + scanlineStride] >>> 24) * 0.00392156862745098;
            double n52;
            double n53;
            double n54;
            if (l != bounds.x) {
                n52 = (array2[n48 - n5] >>> 24) * 0.00392156862745098;
                n53 = (array2[n48 - 1] >>> 24) * 0.00392156862745098;
                n54 = (array2[n48 + n6] >>> 24) * 0.00392156862745098;
            }
            else if (l < min) {
                final double n55 = (array2[n48 + 1] >>> 24) * 0.00392156862745098;
                final double n56 = (array2[n48 - n6] >>> 24) * 0.00392156862745098;
                final double n57 = (array2[n48 + n5] >>> 24) * 0.00392156862745098;
                final double[] array22 = array21[l - n];
                array22[0] = -n9 * (n56 + 2.0 * n55 + n57 - (n49 + 2.0 * n50 + n51));
                array22[1] = -n12 * (2.0 * n49 + n56 - (2.0 * n50 + n55));
                final double n58 = 1.0 / Math.sqrt(array22[0] * array22[0] + array22[1] * array22[1] + 1.0);
                final double[] array23 = array22;
                final int n59 = 0;
                array23[n59] *= n58;
                final double[] array24 = array22;
                final int n60 = 1;
                array24[n60] *= n58;
                array22[2] = n58;
                array22[3] = n50 * this.surfaceScale;
                ++n48;
                ++l;
                n52 = n49;
                n53 = n50;
                n54 = n51;
                n49 = n56;
                n50 = n55;
                n51 = n57;
            }
            else {
                n52 = n49;
                n53 = n50;
                n54 = n51;
            }
            while (l < min) {
                final double n61 = (array2[n48 - n6] >>> 24) * 0.00392156862745098;
                final double n62 = (array2[n48 + 1] >>> 24) * 0.00392156862745098;
                final double n63 = (array2[n48 + n5] >>> 24) * 0.00392156862745098;
                final double[] array25 = array21[l - n];
                array25[0] = -n7 * (n61 + 2.0 * n62 + n63 - (n52 + 2.0 * n53 + n54));
                array25[1] = -n8 * (n54 + 2.0 * n51 + n63 - (n52 + 2.0 * n49 + n61));
                final double n64 = 1.0 / Math.sqrt(array25[0] * array25[0] + array25[1] * array25[1] + 1.0);
                final double[] array26 = array25;
                final int n65 = 0;
                array26[n65] *= n64;
                final double[] array27 = array25;
                final int n66 = 1;
                array27[n66] *= n64;
                array25[2] = n64;
                array25[3] = n50 * this.surfaceScale;
                ++n48;
                n52 = n49;
                n53 = n50;
                n54 = n51;
                n49 = n61;
                n50 = n62;
                n51 = n63;
                ++l;
            }
            if (l < n + n3 && l == bounds.x + bounds.width - 1) {
                final double[] array28 = array21[l - n];
                array28[0] = -n9 * (n49 + 2.0 * n50 + n51 - (n52 + 2.0 * n53 + n54));
                array28[1] = -n12 * (n54 + 2.0 * n51 - (n52 + 2.0 * n49));
                final double n67 = 1.0 / Math.sqrt(array28[0] * array28[0] + array28[1] * array28[1] + 1.0);
                final double[] array29 = array28;
                final int n68 = 0;
                array29[n68] *= n67;
                final double[] array30 = array28;
                final int n69 = 1;
                array30[n69] *= n67;
                array28[2] = n67;
                array28[3] = n50 * this.surfaceScale;
            }
            ++i;
        }
        if (i < n2 + n4 && i == bounds.y + bounds.height - 1) {
            final double[][] array31 = array[i - n2];
            final int n70 = n15 + scanlineStride * (i - bounds.y);
            int x = n;
            if (x < bounds.x) {
                x = bounds.x;
            }
            int n71 = n70 + (x - bounds.x);
            double n72 = (array2[n71] >>> 24) * 0.00392156862745098;
            double n73 = (array2[n71 - scanlineStride] >>> 24) * 0.00392156862745098;
            double n74;
            double n75;
            if (x != bounds.x) {
                n74 = (array2[n71 - n5] >>> 24) * 0.00392156862745098;
                n75 = (array2[n71 - 1] >>> 24) * 0.00392156862745098;
            }
            else if (x < min) {
                final double n76 = (array2[n71 + 1] >>> 24) * 0.00392156862745098;
                final double n77 = (array2[n71 - n6] >>> 24) * 0.00392156862745098;
                final double[] array32 = array31[x - n];
                array32[0] = -n13 * (2.0 * n76 + n77 - 2.0 * n72 - n73);
                array32[1] = -n14 * (2.0 * n72 + n76 - 2.0 * n73 - n77);
                final double n78 = 1.0 / Math.sqrt(array32[0] * array32[0] + array32[1] * array32[1] + 1.0);
                final double[] array33 = array32;
                final int n79 = 0;
                array33[n79] *= n78;
                final double[] array34 = array32;
                final int n80 = 1;
                array34[n80] *= n78;
                array32[2] = n78;
                array32[3] = n72 * this.surfaceScale;
                ++n71;
                ++x;
                n75 = n72;
                n74 = n73;
                n72 = n76;
                n73 = n77;
            }
            else {
                n75 = n72;
                n74 = n73;
            }
            while (x < min) {
                final double n81 = (array2[n71 + 1] >>> 24) * 0.00392156862745098;
                final double n82 = (array2[n71 - n6] >>> 24) * 0.00392156862745098;
                final double[] array35 = array31[x - n];
                array35[0] = -n11 * (2.0 * n81 + n82 - (2.0 * n75 + n74));
                array35[1] = -n10 * (n75 + 2.0 * n72 + n81 - (n74 + 2.0 * n73 + n82));
                final double n83 = 1.0 / Math.sqrt(array35[0] * array35[0] + array35[1] * array35[1] + 1.0);
                final double[] array36 = array35;
                final int n84 = 0;
                array36[n84] *= n83;
                final double[] array37 = array35;
                final int n85 = 1;
                array37[n85] *= n83;
                array35[2] = n83;
                array35[3] = n72 * this.surfaceScale;
                ++n71;
                n75 = n72;
                n74 = n73;
                n72 = n81;
                n73 = n82;
                ++x;
            }
            if (x < n + n3 && x == bounds.x + bounds.width - 1) {
                final double[] array38 = array31[x - n];
                array38[0] = -n13 * (2.0 * n72 + n73 - (2.0 * n75 + n74));
                array38[1] = -n14 * (2.0 * n72 + n75 - (2.0 * n73 + n74));
                final double n86 = 1.0 / Math.sqrt(array38[0] * array38[0] + array38[1] * array38[1] + 1.0);
                final double[] array39 = array38;
                final int n87 = 0;
                array39[n87] *= n86;
                final double[] array40 = array38;
                final int n88 = 1;
                array40[n88] *= n86;
                array38[2] = n86;
                array38[3] = n72 * this.surfaceScale;
            }
        }
        return array;
    }
}
