// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

import java.awt.image.WritableRaster;
import org.apache.pdfbox.jbig2.Bitmap;

final class BitmapScanline extends Scanline
{
    private Bitmap bitmap;
    private WritableRaster raster;
    private int[] lineBuffer;
    
    public BitmapScanline(final Bitmap bitmap, final WritableRaster raster, final int n) {
        super(n);
        this.bitmap = bitmap;
        this.raster = raster;
        this.lineBuffer = new int[this.length];
    }
    
    @Override
    protected void clear() {
        this.lineBuffer = new int[this.length];
    }
    
    @Override
    protected void fetch(int i, final int n) {
        this.lineBuffer = new int[this.length];
        int byteIndex = this.bitmap.getByteIndex(i, n);
        while (i < this.length) {
            final byte b = (byte)~this.bitmap.getByte(byteIndex++);
            for (int j = ((this.bitmap.getWidth() - i > 8) ? 8 : (this.bitmap.getWidth() - i)) - 1; j >= 0; --j, ++i) {
                if ((b >> j & 0x1) != 0x0) {
                    this.lineBuffer[i] = 255;
                }
            }
        }
    }
    
    @Override
    protected void filter(final int[] array, final int[] array2, final Weighttab[] array3, final Scanline scanline) {
        final BitmapScanline bitmapScanline = (BitmapScanline)scanline;
        final int length = scanline.length;
        final int n = 1 << array2[0] - 1;
        final int[] lineBuffer = this.lineBuffer;
        final int[] lineBuffer2 = bitmapScanline.lineBuffer;
        final int n2 = array[0];
        final int n3 = array2[0];
        if (n2 != 0) {
            int n4 = 0;
            for (final Weighttab weighttab : array3) {
                final int length2 = weighttab.weights.length;
                int n5 = n;
                for (int n6 = 0, i2 = weighttab.i0; n6 < length2 && i2 < lineBuffer.length; n5 += weighttab.weights[n6] * (lineBuffer[i2++] >> n2), ++n6) {}
                final int n7 = n5 >> n3;
                lineBuffer2[n4++] = ((n7 < 0) ? 0 : ((n7 > 255) ? 255 : n7));
            }
        }
        else {
            int n8 = 0;
            for (final Weighttab weighttab2 : array3) {
                final int length3 = weighttab2.weights.length;
                int n9 = n;
                for (int n10 = 0, i3 = weighttab2.i0; n10 < length3 && i3 < lineBuffer.length; n9 += weighttab2.weights[n10] * lineBuffer[i3++], ++n10) {}
                lineBuffer2[n8++] = n9 >> n3;
            }
        }
    }
    
    @Override
    protected void accumulate(final int n, final Scanline scanline) {
        final BitmapScanline bitmapScanline = (BitmapScanline)scanline;
        final int[] lineBuffer = this.lineBuffer;
        final int[] lineBuffer2 = bitmapScanline.lineBuffer;
        for (int i = 0; i < lineBuffer2.length; ++i) {
            final int[] array = lineBuffer2;
            final int n2 = i;
            array[n2] += n * lineBuffer[i];
        }
    }
    
    @Override
    protected void shift(final int[] array) {
        final int n = array[0];
        final int n2 = 1 << n - 1;
        final int[] lineBuffer = this.lineBuffer;
        for (int i = 0; i < lineBuffer.length; ++i) {
            final int n3 = lineBuffer[i] + n2 >> n;
            lineBuffer[i] = ((n3 < 0) ? 0 : ((n3 > 255) ? 255 : n3));
        }
    }
    
    @Override
    protected void store(final int x, final int y) {
        this.raster.setSamples(x, y, this.length, 1, 0, this.lineBuffer);
    }
}
