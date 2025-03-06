// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

import org.apache.pdfbox.jbig2.util.CombinationOperator;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.Point;
import javax.imageio.ImageReadParam;
import org.apache.pdfbox.jbig2.JBIG2ReadParam;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.WritableRaster;
import org.apache.pdfbox.jbig2.Bitmap;

public class Bitmaps
{
    public static WritableRaster asRaster(final Bitmap bitmap) {
        return asRaster(bitmap, FilterType.Gaussian);
    }
    
    public static WritableRaster asRaster(final Bitmap bitmap, final FilterType filterType) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must not be null");
        }
        return asRaster(bitmap, new JBIG2ReadParam(1, 1, 0, 0, new Rectangle(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Dimension(bitmap.getWidth(), bitmap.getHeight())), filterType);
    }
    
    public static WritableRaster asRaster(Bitmap bitmap, final ImageReadParam imageReadParam, final FilterType filterType) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must not be null");
        }
        if (imageReadParam == null) {
            throw new IllegalArgumentException("param must not be null");
        }
        final Dimension sourceRenderSize = imageReadParam.getSourceRenderSize();
        double n;
        double n2;
        if (sourceRenderSize != null) {
            n = sourceRenderSize.getWidth() / bitmap.getWidth();
            n2 = sourceRenderSize.getHeight() / bitmap.getHeight();
        }
        else {
            n2 = (n = 1.0);
        }
        final Rectangle sourceRegion = imageReadParam.getSourceRegion();
        if (sourceRegion != null && !bitmap.getBounds().equals(sourceRegion)) {
            bitmap = extract(bitmap.getBounds().intersection(sourceRegion), bitmap);
        }
        final boolean b = n != 1.0 || n2 != 1.0;
        final boolean b2 = imageReadParam.getSourceXSubsampling() != 1;
        final boolean b3 = imageReadParam.getSourceYSubsampling() != 1;
        if (b2 && b3) {
            if (b) {
                n /= imageReadParam.getSourceXSubsampling();
                n2 /= imageReadParam.getSourceYSubsampling();
            }
            else {
                bitmap = subsample(bitmap, imageReadParam);
            }
        }
        else {
            if (b2) {
                if (b) {
                    n /= imageReadParam.getSourceXSubsampling();
                }
                else {
                    bitmap = subsampleX(bitmap, imageReadParam.getSourceXSubsampling(), imageReadParam.getSubsamplingXOffset());
                }
            }
            if (b3) {
                if (b) {
                    n2 /= imageReadParam.getSourceYSubsampling();
                }
                else {
                    bitmap = subsampleY(bitmap, imageReadParam.getSourceYSubsampling(), imageReadParam.getSubsamplingYOffset());
                }
            }
        }
        return buildRaster(bitmap, filterType, n, n2);
    }
    
    private static WritableRaster buildRaster(final Bitmap bitmap, final FilterType filterType, final double n, final double n2) {
        final Rectangle rectangle = new Rectangle(0, 0, (int)Math.round(bitmap.getWidth() * n), (int)Math.round(bitmap.getHeight() * n2));
        WritableRaster writableRaster;
        if (n != 1.0 || n2 != 1.0) {
            writableRaster = Raster.createInterleavedRaster(0, rectangle.width, rectangle.height, 1, new Point());
            final Resizer resizer = new Resizer(n, n2);
            final Filter byType = Filter.byType(filterType);
            resizer.resize(bitmap, bitmap.getBounds(), writableRaster, rectangle, byType, byType);
        }
        else {
            writableRaster = Raster.createPackedRaster(0, rectangle.width, rectangle.height, 1, 1, new Point());
            int n3 = 0;
            for (int i = 0; i < bitmap.getHeight(); ++i) {
                int j = 0;
                while (j < bitmap.getWidth()) {
                    final int n4 = ~bitmap.getByte(n3) & 0xFF;
                    for (int n5 = 7 - ((bitmap.getWidth() - j > 8) ? 8 : (bitmap.getWidth() - j)), k = 7; k > n5; --k, ++j) {
                        writableRaster.setSample(j, i, 0, n4 >> k & 0x1);
                    }
                    ++n3;
                }
            }
        }
        return writableRaster;
    }
    
    public static BufferedImage asBufferedImage(final Bitmap bitmap) {
        return asBufferedImage(bitmap, FilterType.Gaussian);
    }
    
    public static BufferedImage asBufferedImage(final Bitmap bitmap, final FilterType filterType) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must not be null");
        }
        return asBufferedImage(bitmap, new JBIG2ReadParam(1, 1, 0, 0, new Rectangle(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Dimension(bitmap.getWidth(), bitmap.getHeight())), filterType);
    }
    
    public static BufferedImage asBufferedImage(final Bitmap bitmap, final ImageReadParam imageReadParam, final FilterType filterType) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must not be null");
        }
        if (imageReadParam == null) {
            throw new IllegalArgumentException("param must not be null");
        }
        final WritableRaster raster = asRaster(bitmap, imageReadParam, filterType);
        final Dimension sourceRenderSize = imageReadParam.getSourceRenderSize();
        double n;
        double n2;
        if (sourceRenderSize != null) {
            n = sourceRenderSize.getWidth() / bitmap.getWidth();
            n2 = sourceRenderSize.getHeight() / bitmap.getHeight();
        }
        else {
            n2 = (n = 1.0);
        }
        IndexColorModel cm;
        if (n != 1.0 || n2 != 1.0) {
            final byte[] array = new byte[256];
            for (int i = 255, n3 = 0; i >= 0; --i, ++n3) {
                array[i] = (byte)(255 - n3 * 255 / 255);
            }
            cm = new IndexColorModel(8, 256, array, array, array);
        }
        else {
            cm = new IndexColorModel(1, 2, new byte[] { 0, -1 }, new byte[] { 0, -1 }, new byte[] { 0, -1 });
        }
        return new BufferedImage(cm, raster, false, null);
    }
    
    public static Bitmap extract(final Rectangle rectangle, final Bitmap bitmap) {
        final Bitmap bitmap2 = new Bitmap(rectangle.width, rectangle.height);
        final int n = rectangle.x & 0x7;
        final int n2 = 8 - n;
        int n3 = 0;
        final int n4 = 8 - bitmap2.getWidth() & 0x7;
        int byteIndex = bitmap.getByteIndex(rectangle.x, rectangle.y);
        int byteIndex2 = bitmap.getByteIndex(rectangle.x + rectangle.width - 1, rectangle.y);
        final boolean b = bitmap2.getRowStride() == byteIndex2 + 1 - byteIndex;
        for (int y = rectangle.y; y < rectangle.getMaxY(); ++y) {
            int n5 = byteIndex;
            int n6 = n3;
            if (byteIndex == byteIndex2) {
                bitmap2.setByte(n6, unpad(n4, (byte)(bitmap.getByte(n5) << n)));
            }
            else if (n == 0) {
                for (int i = byteIndex; i <= byteIndex2; ++i) {
                    byte b2 = bitmap.getByte(n5++);
                    if (i == byteIndex2 && b) {
                        b2 = unpad(n4, b2);
                    }
                    bitmap2.setByte(n6++, b2);
                }
            }
            else {
                copyLine(bitmap, bitmap2, n, n2, n4, byteIndex, byteIndex2, b, n5, n6);
            }
            byteIndex += bitmap.getRowStride();
            byteIndex2 += bitmap.getRowStride();
            n3 += bitmap2.getRowStride();
        }
        return bitmap2;
    }
    
    private static void copyLine(final Bitmap bitmap, final Bitmap bitmap2, final int n, final int n2, final int n3, final int n4, final int n5, final boolean b, int n6, int n7) {
        for (int i = n4; i < n5; ++i) {
            if (n6 + 1 < bitmap.getByteArray().length) {
                final boolean b2 = i + 1 == n5;
                byte unpad = (byte)(bitmap.getByte(n6++) << n | (bitmap.getByte(n6) & 0xFF) >>> n2);
                if (b2 && !b) {
                    unpad = unpad(n3, unpad);
                }
                bitmap2.setByte(n7++, unpad);
                if (b2 && b) {
                    bitmap2.setByte(n7, unpad(n3, (byte)((bitmap.getByte(n6) & 0xFF) << n)));
                }
            }
            else {
                bitmap2.setByte(n7++, (byte)(bitmap.getByte(n6++) << n & 0xFF));
            }
        }
    }
    
    private static byte unpad(final int n, final byte b) {
        return (byte)(b >> n << n);
    }
    
    public static Bitmap subsample(final Bitmap bitmap, final ImageReadParam imageReadParam) {
        if (bitmap == null) {
            throw new IllegalArgumentException("src must not be null");
        }
        if (imageReadParam == null) {
            throw new IllegalArgumentException("param must not be null");
        }
        final int sourceXSubsampling = imageReadParam.getSourceXSubsampling();
        final int sourceYSubsampling = imageReadParam.getSourceYSubsampling();
        final int subsamplingXOffset = imageReadParam.getSubsamplingXOffset();
        final int subsamplingYOffset = imageReadParam.getSubsamplingYOffset();
        final Bitmap bitmap2 = new Bitmap((bitmap.getWidth() - subsamplingXOffset) / sourceXSubsampling, (bitmap.getHeight() - subsamplingYOffset) / sourceYSubsampling);
        for (int i = 0, n = subsamplingYOffset; i < bitmap2.getHeight(); ++i, n += sourceYSubsampling) {
            for (int j = 0, n2 = subsamplingXOffset; j < bitmap2.getWidth(); ++j, n2 += sourceXSubsampling) {
                final byte pixel = bitmap.getPixel(n2, n);
                if (pixel != 0) {
                    bitmap2.setPixel(j, i, pixel);
                }
            }
        }
        return bitmap2;
    }
    
    public static Bitmap subsampleX(final Bitmap bitmap, final int n, final int n2) {
        if (bitmap == null) {
            throw new IllegalArgumentException("src must not be null");
        }
        final Bitmap bitmap2 = new Bitmap(bitmap.getWidth(), (bitmap.getWidth() - n2) / n);
        for (int i = 0; i < bitmap2.getHeight(); ++i) {
            for (int j = 0, n3 = n2; j < bitmap2.getWidth(); ++j, n3 += n) {
                final byte pixel = bitmap.getPixel(n3, i);
                if (pixel != 0) {
                    bitmap2.setPixel(j, i, pixel);
                }
            }
        }
        return bitmap2;
    }
    
    public static Bitmap subsampleY(final Bitmap bitmap, final int n, final int n2) {
        if (bitmap == null) {
            throw new IllegalArgumentException("src must not be null");
        }
        final Bitmap bitmap2 = new Bitmap((bitmap.getWidth() - n2) / n, bitmap.getHeight());
        for (int i = 0, n3 = n2; i < bitmap2.getHeight(); ++i, n3 += n) {
            for (int j = 0; j < bitmap2.getWidth(); ++j) {
                final byte pixel = bitmap.getPixel(j, n3);
                if (pixel != 0) {
                    bitmap2.setPixel(j, i, pixel);
                }
            }
        }
        return bitmap2;
    }
    
    public static byte combineBytes(final byte b, final byte b2, final CombinationOperator combinationOperator) {
        switch (combinationOperator) {
            case OR: {
                return (byte)(b2 | b);
            }
            case AND: {
                return (byte)(b2 & b);
            }
            case XOR: {
                return (byte)(b2 ^ b);
            }
            case XNOR: {
                return (byte)~(b ^ b2);
            }
            default: {
                return b2;
            }
        }
    }
    
    public static void blit(final Bitmap bitmap, final Bitmap bitmap2, int n, int n2, final CombinationOperator combinationOperator) {
        int n3 = 0;
        int n4 = 0;
        int n5 = bitmap.getRowStride() - 1;
        if (n < 0) {
            n4 = -n;
            n = 0;
        }
        else if (n + bitmap.getWidth() > bitmap2.getWidth()) {
            n5 -= bitmap.getWidth() + n - bitmap2.getWidth();
        }
        if (n2 < 0) {
            n3 = -n2;
            n2 = 0;
            n4 += bitmap.getRowStride();
            n5 += bitmap.getRowStride();
        }
        else if (n2 + bitmap.getHeight() > bitmap2.getHeight()) {
            n3 = bitmap.getHeight() + n2 - bitmap2.getHeight();
        }
        final int n6 = n & 0x7;
        final int n7 = 8 - n6;
        final int n8 = bitmap.getWidth() & 0x7;
        final int n9 = n7 - n8;
        final boolean b = (n7 & 0x7) != 0x0;
        final boolean b2 = bitmap.getWidth() <= (n5 - n4 << 3) + n7;
        final int byteIndex = bitmap2.getByteIndex(n, n2);
        final int min = Math.min(bitmap.getHeight(), n3 + bitmap2.getHeight());
        if (!b) {
            blitUnshifted(bitmap, bitmap2, n3, min, byteIndex, n4, n5, combinationOperator);
        }
        else if (b2) {
            blitSpecialShifted(bitmap, bitmap2, n3, min, byteIndex, n4, n5, n9, n6, n7, combinationOperator);
        }
        else {
            blitShifted(bitmap, bitmap2, n3, min, byteIndex, n4, n5, n9, n6, n7, combinationOperator, n8);
        }
    }
    
    private static void blitUnshifted(final Bitmap bitmap, final Bitmap bitmap2, final int n, final int n2, int n3, int n4, int n5, final CombinationOperator combinationOperator) {
        for (int i = n; i < n2; ++i, n3 += bitmap2.getRowStride(), n4 += bitmap.getRowStride(), n5 += bitmap.getRowStride()) {
            int n6 = n3;
            for (int j = n4; j <= n5; ++j) {
                bitmap2.setByte(n6++, combineBytes(bitmap2.getByte(n6), bitmap.getByte(j), combinationOperator));
            }
        }
    }
    
    private static void blitSpecialShifted(final Bitmap bitmap, final Bitmap bitmap2, final int n, final int n2, int n3, int n4, int n5, final int n6, final int n7, final int n8, final CombinationOperator combinationOperator) {
        for (int i = n; i < n2; ++i, n3 += bitmap2.getRowStride(), n4 += bitmap.getRowStride(), n5 += bitmap.getRowStride()) {
            short n9 = 0;
            int n10 = n3;
            for (int j = n4; j <= n5; ++j) {
                final byte byte1 = bitmap2.getByte(n10);
                final short n11 = (short)((n9 | (bitmap.getByte(j) & 0xFF)) << n8);
                byte unpad = (byte)(n11 >> 8);
                if (j == n5) {
                    unpad = unpad(n6, unpad);
                }
                bitmap2.setByte(n10++, combineBytes(byte1, unpad, combinationOperator));
                n9 = (short)(n11 << n7);
            }
        }
    }
    
    private static void blitShifted(final Bitmap bitmap, final Bitmap bitmap2, final int n, final int n2, int n3, int n4, int n5, final int n6, final int n7, final int n8, final CombinationOperator combinationOperator, final int n9) {
        for (int i = n; i < n2; ++i, n3 += bitmap2.getRowStride(), n4 += bitmap.getRowStride(), n5 += bitmap.getRowStride()) {
            int n10 = 0;
            int n11 = n3;
            for (int j = n4; j <= n5; ++j) {
                final byte byte1 = bitmap2.getByte(n11);
                final short n12 = (short)((n10 | (bitmap.getByte(j) & 0xFF)) << n8);
                bitmap2.setByte(n11++, combineBytes(byte1, (byte)(n12 >> 8), combinationOperator));
                n10 = (short)(n12 << n7);
                if (j == n5) {
                    byte unpad = (byte)(n10 >> 8 - n8);
                    if (n9 != 0) {
                        unpad = unpad(8 + n6, unpad);
                    }
                    bitmap2.setByte(n11, combineBytes(bitmap2.getByte(n11), unpad, combinationOperator));
                }
            }
        }
    }
}
