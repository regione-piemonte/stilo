// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;

abstract class Scanline
{
    int y;
    protected final int length;
    
    protected Scanline(final int length) {
        this.length = length;
    }
    
    protected final int getWidth() {
        return this.length;
    }
    
    protected abstract void clear();
    
    protected abstract void fetch(final int p0, final int p1);
    
    protected abstract void filter(final int[] p0, final int[] p1, final Weighttab[] p2, final Scanline p3);
    
    protected abstract void accumulate(final int p0, final Scanline p1);
    
    protected abstract void shift(final int[] p0);
    
    protected abstract void store(final int p0, final int p1);
    
    protected static final class ByteBGRScanline extends Scanline
    {
        private final Raster srcRaster;
        private final WritableRaster dstRaster;
        private final int[] data;
        
        protected ByteBGRScanline(final Raster srcRaster, final WritableRaster dstRaster, final int n) {
            super(n);
            this.srcRaster = srcRaster;
            this.dstRaster = dstRaster;
            this.data = new int[3 * n];
        }
        
        @Override
        protected void accumulate(final int n, final Scanline scanline) {
            final ByteBGRScanline byteBGRScanline = (ByteBGRScanline)scanline;
            final int[] data = this.data;
            final int[] data2 = byteBGRScanline.data;
            for (int i = 0; i < data2.length; ++i) {
                final int[] array = data2;
                final int n2 = i;
                array[n2] += n * data[i];
            }
        }
        
        @Override
        protected void clear() {
            final int[] data = this.data;
            for (int i = 0; i < data.length; ++i) {
                data[i] = 0;
            }
        }
        
        @Override
        protected void fetch(final int x, final int y) {
            this.srcRaster.getPixels(x, y, this.length, 1, this.data);
        }
        
        @Override
        protected void filter(final int[] array, final int[] array2, final Weighttab[] array3, final Scanline scanline) {
            final ByteBGRScanline byteBGRScanline = (ByteBGRScanline)scanline;
            final int length = scanline.length;
            final int[] array4 = { 1 << array2[0] - 1, 1 << array2[1] - 1, 1 << array2[2] - 1 };
            final int[] data = this.data;
            final int[] data2 = byteBGRScanline.data;
            if (array[0] != 0 || array[1] != 0 || array[2] != 0) {
                int n = 0;
                for (final Weighttab weighttab : array3) {
                    final int length2 = weighttab.weights.length;
                    int n2 = array4[0];
                    int n3 = array4[1];
                    int n4 = array4[2];
                    int n7;
                    for (int n5 = 0, n6 = weighttab.i0 * 3; n5 < length2 && n6 < data.length; n2 += n7 * (data[n6++] >> array[0]), n3 += n7 * (data[n6++] >> array[1]), n4 += n7 * (data[n6++] >> array[2]), ++n5) {
                        n7 = weighttab.weights[n5];
                    }
                    final int n8 = n2 >> array2[0];
                    data2[n++] = ((n8 < 0) ? 0 : ((n8 > 255) ? 255 : n8));
                    final int n9 = n3 >> array2[1];
                    data2[n++] = ((n9 < 0) ? 0 : ((n9 > 255) ? 255 : n9));
                    final int n10 = n4 >> array2[2];
                    data2[n++] = ((n10 < 0) ? 0 : ((n10 > 255) ? 255 : n10));
                }
            }
            else {
                int n11 = 0;
                for (final Weighttab weighttab2 : array3) {
                    final int length3 = weighttab2.weights.length;
                    int n12 = array4[0];
                    int n13 = array4[1];
                    int n14 = array4[2];
                    int n17;
                    for (int n15 = 0, n16 = weighttab2.i0 * 3; n15 < length3 && n16 < data.length; n12 += n17 * data[n16++], n13 += n17 * data[n16++], n14 += n17 * data[n16++], ++n15) {
                        n17 = weighttab2.weights[n15];
                    }
                    data2[n11++] = n12 >> array2[0];
                    data2[n11++] = n13 >> array2[1];
                    data2[n11++] = n14 >> array2[2];
                }
            }
        }
        
        @Override
        protected void shift(final int[] array) {
            final int[] array2 = { 1 << array[0] - 1, 1 << array[1] - 1, 1 << array[2] - 1 };
            final int[] data = this.data;
            int i = 0;
            while (i < data.length) {
                for (int j = 0; j < 3; ++j, ++i) {
                    final int n = data[i] + array2[j] >> array[j];
                    data[i] = ((n < 0) ? 0 : ((n > 255) ? 255 : n));
                }
            }
        }
        
        @Override
        protected void store(final int x, final int y) {
            this.dstRaster.setPixels(x, y, this.length, 1, this.data);
        }
    }
    
    protected static final class IntegerSinglePixelPackedScanline extends Scanline
    {
        private final Raster srcRaster;
        private final WritableRaster dstRaster;
        private final int[] data;
        private final int[] bitMasks;
        private final int[] bitOffsets;
        private final int componentCount;
        private final SinglePixelPackedSampleModel srcSM;
        private final int[] tmp;
        
        protected IntegerSinglePixelPackedScanline(final Raster srcRaster, final WritableRaster dstRaster, final int n) {
            super(n);
            this.srcRaster = srcRaster;
            this.dstRaster = dstRaster;
            this.srcSM = (SinglePixelPackedSampleModel)this.srcRaster.getSampleModel();
            this.bitMasks = this.srcSM.getBitMasks();
            this.bitOffsets = this.srcSM.getBitOffsets();
            this.componentCount = this.bitMasks.length;
            if (this.componentCount != this.bitOffsets.length || this.bitOffsets.length != this.srcSM.getNumBands()) {
                throw new IllegalArgumentException("weird: getBitMasks().length != getBitOffsets().length");
            }
            this.tmp = new int[this.componentCount];
            this.data = new int[this.componentCount * n];
        }
        
        @Override
        protected void accumulate(final int n, final Scanline scanline) {
            final IntegerSinglePixelPackedScanline integerSinglePixelPackedScanline = (IntegerSinglePixelPackedScanline)scanline;
            final int[] data = this.data;
            final int[] data2 = integerSinglePixelPackedScanline.data;
            for (int i = 0; i < data2.length; ++i) {
                final int[] array = data2;
                final int n2 = i;
                array[n2] += n * data[i];
            }
        }
        
        @Override
        protected void clear() {
            final int[] data = this.data;
            for (int i = 0; i < data.length; ++i) {
                data[i] = 0;
            }
        }
        
        @Override
        protected void fetch(final int x, final int y) {
            this.srcRaster.getPixels(x, y, this.length, 1, this.data);
        }
        
        @Override
        protected void filter(final int[] array, final int[] array2, final Weighttab[] array3, final Scanline scanline) {
            final IntegerSinglePixelPackedScanline integerSinglePixelPackedScanline = (IntegerSinglePixelPackedScanline)scanline;
            final int length = scanline.length;
            final int[] tmp = this.tmp;
            for (int i = 0; i < this.componentCount; ++i) {
                tmp[i] = 1 << array2[i] - 1;
            }
            final int[] data = this.data;
            final int[] data2 = integerSinglePixelPackedScanline.data;
            boolean b = false;
            for (int n = 0; n < this.componentCount && !b; b |= (array[n] != 0), ++n) {}
            if (b) {
                int n2 = 0;
                for (final Weighttab weighttab : array3) {
                    final int length2 = weighttab.weights.length;
                    for (int k = 0; k < this.componentCount; ++k) {
                        int n3 = tmp[k];
                        for (int n4 = 0, n5 = weighttab.i0 * this.componentCount + k; n4 < length2 && n5 < data.length; ++n4, n5 += this.componentCount) {
                            n3 += weighttab.weights[n4] * (data[n5] >> array[k]);
                        }
                        final int n6 = n3 >> array2[k];
                        data2[n2++] = ((n6 < 0) ? 0 : ((n6 > 255) ? 255 : n6));
                    }
                }
            }
            else {
                int n7 = 0;
                for (final Weighttab weighttab2 : array3) {
                    final int length3 = weighttab2.weights.length;
                    for (int n8 = 0; n8 < this.componentCount; ++n8) {
                        int n9 = tmp[n8];
                        for (int n10 = 0, n11 = weighttab2.i0 * this.componentCount + n8; n10 < length3 && n11 < data.length; ++n10, n11 += this.componentCount) {
                            n9 += weighttab2.weights[n10] * data[n11];
                        }
                        data2[n7++] = n9 >> array2[n8];
                    }
                }
            }
        }
        
        @Override
        protected void shift(final int[] array) {
            final int[] tmp = this.tmp;
            for (int i = 0; i < this.componentCount; ++i) {
                tmp[i] = 1 << array[i] - 1;
            }
            final int[] data = this.data;
            int j = 0;
            while (j < data.length) {
                for (int k = 0; k < this.componentCount; ++k, ++j) {
                    final int n = data[j] + tmp[k] >> array[k];
                    data[j] = ((n < 0) ? 0 : ((n > 255) ? 255 : n));
                }
            }
        }
        
        @Override
        protected void store(final int x, final int y) {
            this.dstRaster.setPixels(x, y, this.length, 1, this.data);
        }
    }
    
    protected static final class GenericRasterScanline extends Scanline
    {
        private final Raster srcRaster;
        private final WritableRaster dstRaster;
        private final int componentCount;
        private final int[][] data;
        private final SampleModel srcSM;
        private final SampleModel dstSM;
        private final int[] channelMask;
        private final int[] tmp;
        private final ScanlineFilter inputFilter;
        
        protected GenericRasterScanline(final Raster srcRaster, final WritableRaster dstRaster, final int n, final int[] array, final ScanlineFilter inputFilter) {
            super(n);
            this.srcRaster = srcRaster;
            this.dstRaster = dstRaster;
            this.inputFilter = inputFilter;
            this.srcSM = this.srcRaster.getSampleModel();
            this.dstSM = this.dstRaster.getSampleModel();
            this.componentCount = this.srcSM.getNumBands();
            if (this.componentCount != this.dstSM.getNumBands()) {
                throw new IllegalArgumentException("weird: src raster num bands != dst raster num bands");
            }
            this.tmp = new int[this.componentCount];
            this.data = new int[this.componentCount][];
            for (int i = 0; i < this.data.length; ++i) {
                this.data[i] = new int[n];
            }
            this.channelMask = new int[this.componentCount];
            for (int j = 0; j < this.componentCount; ++j) {
                this.channelMask[j] = (1 << array[j]) - 1;
            }
        }
        
        @Override
        protected void accumulate(final int n, final Scanline scanline) {
            final GenericRasterScanline genericRasterScanline = (GenericRasterScanline)scanline;
            final int length = genericRasterScanline.data[0].length;
            for (int i = 0; i < this.componentCount; ++i) {
                final int[] array = this.data[i];
                final int[] array2 = genericRasterScanline.data[i];
                for (int j = 0; j < length; ++j) {
                    final int[] array3 = array2;
                    final int n2 = j;
                    array3[n2] += n * array[j];
                }
            }
        }
        
        @Override
        protected void clear() {
            for (int i = 0; i < this.componentCount; ++i) {
                final int[] array = this.data[i];
                for (int j = 0; j < array.length; ++j) {
                    array[j] = 0;
                }
            }
        }
        
        @Override
        protected void fetch(final int x, final int y) {
            for (int i = 0; i < this.componentCount; ++i) {
                this.srcRaster.getSamples(x, y, this.length, 1, i, this.data[i]);
                if (null != this.inputFilter) {
                    this.inputFilter.filter(x, y, i, this.data[i], this.length);
                }
            }
        }
        
        @Override
        protected void filter(final int[] array, final int[] array2, final Weighttab[] array3, final Scanline scanline) {
            final GenericRasterScanline genericRasterScanline = (GenericRasterScanline)scanline;
            final int length = scanline.length;
            final int[] tmp = this.tmp;
            for (int i = 0; i < this.componentCount; ++i) {
                tmp[i] = 1 << array2[i] - 1;
            }
            final int length2 = this.data[0].length;
            boolean b = false;
            for (int n = 0; n < this.componentCount && !b; b |= (array[n] != 0), ++n) {}
            if (b) {
                for (int j = 0; j < this.componentCount; ++j) {
                    final int[] array4 = this.data[j];
                    final int[] array5 = genericRasterScanline.data[j];
                    final int n2 = this.channelMask[j];
                    for (int k = 0; k < length; ++k) {
                        final Weighttab weighttab = array3[k];
                        final int length3 = weighttab.weights.length;
                        int n3 = tmp[j];
                        for (int n4 = 0, i2 = weighttab.i0; n4 < length3 && i2 < length2; ++n4, ++i2) {
                            n3 += weighttab.weights[n4] * (array4[i2] >> array[j]);
                        }
                        final int n5 = n3 >> array2[j];
                        array5[k] = ((n5 < 0) ? 0 : ((n5 > n2) ? n2 : n5));
                    }
                }
            }
            else {
                for (int l = 0; l < this.componentCount; ++l) {
                    final int[] array6 = this.data[l];
                    final int[] array7 = genericRasterScanline.data[l];
                    for (int n6 = 0; n6 < length; ++n6) {
                        final Weighttab weighttab2 = array3[n6];
                        final int length4 = weighttab2.weights.length;
                        int n7 = tmp[l];
                        for (int n8 = 0, i3 = weighttab2.i0; n8 < length4 && i3 < length2; ++n8, ++i3) {
                            n7 += weighttab2.weights[n8] * array6[i3];
                        }
                        array7[n6] = n7 >> array2[l];
                    }
                }
            }
        }
        
        @Override
        protected void shift(final int[] array) {
            final int[] tmp = this.tmp;
            for (int i = 0; i < this.componentCount; ++i) {
                tmp[i] = 1 << array[i] - 1;
            }
            final int length = this.data[0].length;
            for (int j = 0; j < this.componentCount; ++j) {
                final int[] array2 = this.data[j];
                final int n = this.channelMask[j];
                for (int k = 0; k < length; ++k) {
                    final int n2 = array2[k] + tmp[j] >> array[j];
                    array2[k] = ((n2 < 0) ? 0 : ((n2 > n) ? n : n2));
                }
            }
        }
        
        @Override
        protected void store(final int x, final int y) {
            final int length = this.length;
            for (int i = 0; i < this.componentCount; ++i) {
                this.dstRaster.setSamples(x, y, length, 1, i, this.data[i]);
            }
        }
    }
    
    protected static final class ByteBiLevelPackedScanline extends Scanline
    {
        private final Raster srcRaster;
        private final WritableRaster dstRaster;
        private final int[] data;
        
        protected ByteBiLevelPackedScanline(final Raster srcRaster, final WritableRaster dstRaster, final int n) {
            super(n);
            this.srcRaster = srcRaster;
            this.dstRaster = dstRaster;
            this.data = new int[n];
        }
        
        @Override
        protected void accumulate(final int n, final Scanline scanline) {
            final ByteBiLevelPackedScanline byteBiLevelPackedScanline = (ByteBiLevelPackedScanline)scanline;
            final int[] data = this.data;
            final int[] data2 = byteBiLevelPackedScanline.data;
            for (int i = 0; i < data2.length; ++i) {
                final int[] array = data2;
                final int n2 = i;
                array[n2] += n * data[i];
            }
        }
        
        @Override
        protected void clear() {
            final int[] data = this.data;
            for (int i = 0; i < data.length; ++i) {
                data[i] = 0;
            }
        }
        
        @Override
        protected void fetch(final int x, final int y) {
            this.srcRaster.getPixels(x, y, this.length, 1, this.data);
            for (int i = 0; i < this.length; ++i) {
                if (this.data[i] != 0) {
                    this.data[i] = 255;
                }
            }
        }
        
        @Override
        protected void filter(final int[] array, final int[] array2, final Weighttab[] array3, final Scanline scanline) {
            final ByteBiLevelPackedScanline byteBiLevelPackedScanline = (ByteBiLevelPackedScanline)scanline;
            final int length = scanline.length;
            final int n = 1 << array2[0] - 1;
            final int[] data = this.data;
            final int[] data2 = byteBiLevelPackedScanline.data;
            final int n2 = array[0];
            final int n3 = array2[0];
            if (n2 != 0) {
                int n4 = 0;
                for (final Weighttab weighttab : array3) {
                    final int length2 = weighttab.weights.length;
                    int n5 = n;
                    for (int n6 = 0, i2 = weighttab.i0; n6 < length2 && i2 < data.length; n5 += weighttab.weights[n6] * (data[i2++] >> n2), ++n6) {}
                    final int n7 = n5 >> n3;
                    data2[n4++] = ((n7 < 0) ? 0 : ((n7 > 255) ? 255 : n7));
                }
            }
            else {
                int n8 = 0;
                for (final Weighttab weighttab2 : array3) {
                    final int length3 = weighttab2.weights.length;
                    int n9 = n;
                    for (int n10 = 0, i3 = weighttab2.i0; n10 < length3 && i3 < data.length; n9 += weighttab2.weights[n10] * data[i3++], ++n10) {}
                    data2[n8++] = n9 >> n3;
                }
            }
        }
        
        @Override
        protected void shift(final int[] array) {
            final int n = array[0];
            final int n2 = 1 << n - 1;
            final int[] data = this.data;
            for (int i = 0; i < data.length; ++i) {
                final int n3 = data[i] + n2 >> n;
                data[i] = ((n3 < 0) ? 0 : ((n3 > 255) ? 255 : n3));
            }
        }
        
        @Override
        protected void store(final int x, final int y) {
            this.dstRaster.setPixels(x, y, this.length, 1, this.data);
        }
    }
    
    public interface ScanlineFilter
    {
        void filter(final int p0, final int p1, final int p2, final Object p3, final int p4);
    }
}
