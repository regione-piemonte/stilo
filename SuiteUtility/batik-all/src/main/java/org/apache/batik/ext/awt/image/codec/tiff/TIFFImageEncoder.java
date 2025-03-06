// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

import com.sun.image.codec.jpeg.JPEGQTable;
import java.awt.image.Raster;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import java.awt.Rectangle;
import java.awt.image.ComponentSampleModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.util.zip.Deflater;
import java.io.RandomAccessFile;
import java.io.File;
import org.apache.batik.ext.awt.image.codec.util.SeekableOutputStream;
import java.util.SortedSet;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import java.util.TreeSet;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.util.Iterator;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.codec.util.ImageEncodeParam;
import java.io.OutputStream;
import org.apache.batik.ext.awt.image.codec.util.ImageEncoderImpl;

public class TIFFImageEncoder extends ImageEncoderImpl
{
    private static final int TIFF_UNSUPPORTED = -1;
    private static final int TIFF_BILEVEL_WHITE_IS_ZERO = 0;
    private static final int TIFF_BILEVEL_BLACK_IS_ZERO = 1;
    private static final int TIFF_GRAY = 2;
    private static final int TIFF_PALETTE = 3;
    private static final int TIFF_RGB = 4;
    private static final int TIFF_CMYK = 5;
    private static final int TIFF_YCBCR = 6;
    private static final int TIFF_CIELAB = 7;
    private static final int TIFF_GENERIC = 8;
    private static final int COMP_NONE = 1;
    private static final int COMP_JPEG_TTN2 = 7;
    private static final int COMP_PACKBITS = 32773;
    private static final int COMP_DEFLATE = 32946;
    private static final int TIFF_JPEG_TABLES = 347;
    private static final int TIFF_YCBCR_SUBSAMPLING = 530;
    private static final int TIFF_YCBCR_POSITIONING = 531;
    private static final int TIFF_REF_BLACK_WHITE = 532;
    private static final int EXTRA_SAMPLE_UNSPECIFIED = 0;
    private static final int EXTRA_SAMPLE_ASSOCIATED_ALPHA = 1;
    private static final int EXTRA_SAMPLE_UNASSOCIATED_ALPHA = 2;
    private static final int DEFAULT_ROWS_PER_STRIP = 8;
    private static final int[] sizeOfType;
    
    public TIFFImageEncoder(final OutputStream outputStream, final ImageEncodeParam imageEncodeParam) {
        super(outputStream, imageEncodeParam);
        if (this.param == null) {
            this.param = new TIFFEncodeParam();
        }
    }
    
    public void encode(final RenderedImage renderedImage) throws IOException {
        this.writeFileHeader();
        final TIFFEncodeParam tiffEncodeParam = (TIFFEncodeParam)this.param;
        final Iterator extraImages = tiffEncodeParam.getExtraImages();
        if (extraImages != null) {
            int encode = 8;
            RenderedImage renderedImage2 = renderedImage;
            TIFFEncodeParam tiffEncodeParam2 = tiffEncodeParam;
            boolean hasNext;
            do {
                hasNext = extraImages.hasNext();
                encode = this.encode(renderedImage2, tiffEncodeParam2, encode, !hasNext);
                if (hasNext) {
                    final RenderedImage next = extraImages.next();
                    if (next instanceof RenderedImage) {
                        renderedImage2 = next;
                        tiffEncodeParam2 = tiffEncodeParam;
                    }
                    else {
                        if (!(next instanceof Object[])) {
                            continue;
                        }
                        final Object[] array = (Object)next;
                        renderedImage2 = (RenderedImage)array[0];
                        tiffEncodeParam2 = (TIFFEncodeParam)array[1];
                    }
                }
            } while (hasNext);
        }
        else {
            this.encode(renderedImage, tiffEncodeParam, 8, true);
        }
    }
    
    private int encode(final RenderedImage renderedImage, final TIFFEncodeParam tiffEncodeParam, final int n, final boolean b) throws IOException {
        final int compression = tiffEncodeParam.getCompression();
        final boolean writeTiled = tiffEncodeParam.getWriteTiled();
        final int minX = renderedImage.getMinX();
        final int minY = renderedImage.getMinY();
        final int width = renderedImage.getWidth();
        final int height = renderedImage.getHeight();
        final SampleModel sampleModel = renderedImage.getSampleModel();
        final int[] sampleSize = sampleModel.getSampleSize();
        for (int i = 1; i < sampleSize.length; ++i) {
            if (sampleSize[i] != sampleSize[0]) {
                throw new Error("TIFFImageEncoder0");
            }
        }
        final int numBands = sampleModel.getNumBands();
        if ((sampleSize[0] == 1 || sampleSize[0] == 4) && numBands != 1) {
            throw new Error("TIFFImageEncoder1");
        }
        final int dataType = sampleModel.getDataType();
        switch (dataType) {
            case 0: {
                if (sampleSize[0] != 1 && sampleSize[0] == 4 && sampleSize[0] != 8) {
                    throw new Error("TIFFImageEncoder2");
                }
                break;
            }
            case 1:
            case 2: {
                if (sampleSize[0] != 16) {
                    throw new Error("TIFFImageEncoder3");
                }
                break;
            }
            case 3:
            case 4: {
                if (sampleSize[0] != 32) {
                    throw new Error("TIFFImageEncoder4");
                }
                break;
            }
            default: {
                throw new Error("TIFFImageEncoder5");
            }
        }
        final boolean b2 = dataType == 2 || dataType == 1;
        final ColorModel colorModel = renderedImage.getColorModel();
        if (colorModel != null && colorModel instanceof IndexColorModel && dataType != 0) {
            throw new Error("TIFFImageEncoder6");
        }
        int n2 = 0;
        Object o = null;
        int n3 = -1;
        int n4 = 0;
        int n5 = 0;
        if (colorModel instanceof IndexColorModel) {
            final IndexColorModel indexColorModel = (IndexColorModel)colorModel;
            final int mapSize = indexColorModel.getMapSize();
            if (sampleSize[0] == 1 && numBands == 1) {
                if (mapSize != 2) {
                    throw new IllegalArgumentException("TIFFImageEncoder7");
                }
                final byte[] r = new byte[mapSize];
                indexColorModel.getReds(r);
                final byte[] g = new byte[mapSize];
                indexColorModel.getGreens(g);
                final byte[] b3 = new byte[mapSize];
                indexColorModel.getBlues(b3);
                if ((r[0] & 0xFF) == 0x0 && (r[1] & 0xFF) == 0xFF && (g[0] & 0xFF) == 0x0 && (g[1] & 0xFF) == 0xFF && (b3[0] & 0xFF) == 0x0 && (b3[1] & 0xFF) == 0xFF) {
                    n3 = 1;
                }
                else if ((r[0] & 0xFF) == 0xFF && (r[1] & 0xFF) == 0x0 && (g[0] & 0xFF) == 0xFF && (g[1] & 0xFF) == 0x0 && (b3[0] & 0xFF) == 0xFF && (b3[1] & 0xFF) == 0x0) {
                    n3 = 0;
                }
                else {
                    n3 = 3;
                }
            }
            else if (numBands == 1) {
                n3 = 3;
            }
        }
        else if (colorModel == null) {
            if (sampleSize[0] == 1 && numBands == 1) {
                n3 = 1;
            }
            else {
                n3 = 8;
                if (numBands > 1) {
                    n4 = numBands - 1;
                }
            }
        }
        else {
            final ColorSpace colorSpace = colorModel.getColorSpace();
            switch (colorSpace.getType()) {
                case 9: {
                    n3 = 5;
                    break;
                }
                case 6: {
                    n3 = 2;
                    break;
                }
                case 1: {
                    n3 = 7;
                    break;
                }
                case 5: {
                    if (compression == 7 && tiffEncodeParam.getJPEGCompressRGBToYCbCr()) {
                        n3 = 6;
                        break;
                    }
                    n3 = 4;
                    break;
                }
                case 3: {
                    n3 = 6;
                    break;
                }
                default: {
                    n3 = 8;
                    break;
                }
            }
            if (n3 == 8) {
                n4 = numBands - 1;
            }
            else if (numBands > 1) {
                n4 = numBands - colorSpace.getNumComponents();
            }
            if (n4 == 1 && colorModel.hasAlpha()) {
                n5 = (colorModel.isAlphaPremultiplied() ? 1 : 2);
            }
        }
        if (n3 == -1) {
            throw new Error("TIFFImageEncoder8");
        }
        if (compression == 7) {
            if (n3 == 3) {
                throw new Error("TIFFImageEncoder11");
            }
            if (sampleSize[0] != 8 || (n3 != 2 && n3 != 4 && n3 != 6)) {
                throw new Error("TIFFImageEncoder9");
            }
        }
        int n6 = 0;
        switch (n3) {
            case 0: {
                n6 = 0;
                break;
            }
            case 1: {
                n6 = 1;
                break;
            }
            case 2:
            case 8: {
                n6 = 1;
                break;
            }
            case 3: {
                n6 = 3;
                final IndexColorModel indexColorModel2 = (IndexColorModel)colorModel;
                final int mapSize2 = indexColorModel2.getMapSize();
                final byte[] r2 = new byte[mapSize2];
                indexColorModel2.getReds(r2);
                final byte[] g2 = new byte[mapSize2];
                indexColorModel2.getGreens(g2);
                final byte[] b4 = new byte[mapSize2];
                indexColorModel2.getBlues(b4);
                int n7 = 0;
                int n8 = mapSize2;
                int n9 = 2 * mapSize2;
                o = new char[mapSize2 * 3];
                for (int j = 0; j < mapSize2; ++j) {
                    final int n10 = 0xFF & r2[j];
                    o[n7++] = (char)(n10 << 8 | n10);
                    final int n11 = 0xFF & g2[j];
                    o[n8++] = (char)(n11 << 8 | n11);
                    final int n12 = 0xFF & b4[j];
                    o[n9++] = (char)(n12 << 8 | n12);
                }
                n2 = mapSize2 * 3;
                break;
            }
            case 4: {
                n6 = 2;
                break;
            }
            case 5: {
                n6 = 5;
                break;
            }
            case 6: {
                n6 = 6;
                break;
            }
            case 7: {
                n6 = 8;
                break;
            }
            default: {
                throw new Error("TIFFImageEncoder8");
            }
        }
        int w;
        int a;
        if (writeTiled) {
            w = ((tiffEncodeParam.getTileWidth() > 0) ? tiffEncodeParam.getTileWidth() : renderedImage.getTileWidth());
            a = ((tiffEncodeParam.getTileHeight() > 0) ? tiffEncodeParam.getTileHeight() : renderedImage.getTileHeight());
        }
        else {
            w = width;
            a = ((tiffEncodeParam.getTileHeight() > 0) ? tiffEncodeParam.getTileHeight() : 8);
        }
        JPEGEncodeParam jpegEncodeParam = null;
        if (compression == 7) {
            jpegEncodeParam = tiffEncodeParam.getJPEGEncodeParam();
            int horizontalSubsampling = jpegEncodeParam.getHorizontalSubsampling(0);
            int verticalSubsampling = jpegEncodeParam.getVerticalSubsampling(0);
            for (int k = 1; k < numBands; ++k) {
                final int horizontalSubsampling2 = jpegEncodeParam.getHorizontalSubsampling(k);
                if (horizontalSubsampling2 > horizontalSubsampling) {
                    horizontalSubsampling = horizontalSubsampling2;
                }
                final int verticalSubsampling2 = jpegEncodeParam.getVerticalSubsampling(k);
                if (verticalSubsampling2 > verticalSubsampling) {
                    verticalSubsampling = verticalSubsampling2;
                }
            }
            final int n13 = 8 * verticalSubsampling;
            a = (int)(a / (float)n13 + 0.5f) * n13;
            if (a < n13) {
                a = n13;
            }
            if (writeTiled) {
                final int n14 = 8 * horizontalSubsampling;
                w = (int)(w / (float)n14 + 0.5f) * n14;
                if (w < n14) {
                    w = n14;
                }
            }
        }
        int n15;
        if (writeTiled) {
            n15 = (width + w - 1) / w * ((height + a - 1) / a);
        }
        else {
            n15 = (int)Math.ceil(height / (double)a);
        }
        final long[] array = new long[n15];
        final long n16 = (long)Math.ceil(sampleSize[0] / 8.0 * w * numBands);
        final long n17 = n16 * a;
        for (int l = 0; l < n15; ++l) {
            array[l] = n17;
        }
        if (!writeTiled) {
            array[n15 - 1] = (height - a * (n15 - 1)) * n16;
        }
        final long n18 = n17 * (n15 - 1) + array[n15 - 1];
        final long[] array2 = new long[n15];
        final TreeSet<TIFFField> set = new TreeSet<TIFFField>();
        set.add(new TIFFField(256, 4, 1, new long[] { width }));
        set.add(new TIFFField(257, 4, 1, new long[] { height }));
        final char[] array3 = new char[numBands];
        for (int n19 = 0; n19 < numBands; ++n19) {
            array3[n19] = (char)sampleSize[n19];
        }
        set.add(new TIFFField(258, 3, numBands, array3));
        set.add(new TIFFField(259, 3, 1, new char[] { (char)compression }));
        set.add(new TIFFField(262, 3, 1, new char[] { (char)n6 }));
        if (!writeTiled) {
            set.add(new TIFFField(273, 4, n15, array2));
        }
        set.add(new TIFFField(277, 3, 1, new char[] { (char)numBands }));
        if (!writeTiled) {
            set.add(new TIFFField(278, 4, 1, new long[] { a }));
            set.add(new TIFFField(279, 4, n15, array));
        }
        if (o != null) {
            set.add(new TIFFField(320, 3, n2, o));
        }
        if (writeTiled) {
            set.add(new TIFFField(322, 4, 1, new long[] { w }));
            set.add(new TIFFField(323, 4, 1, new long[] { a }));
            set.add(new TIFFField(324, 4, n15, array2));
            set.add(new TIFFField(325, 4, n15, array));
        }
        if (n4 > 0) {
            final char[] array4 = new char[n4];
            for (int n20 = 0; n20 < n4; ++n20) {
                array4[n20] = (char)n5;
            }
            set.add(new TIFFField(338, 3, n4, array4));
        }
        if (dataType != 0) {
            final char[] array5 = new char[numBands];
            if (dataType == 4) {
                array5[0] = '\u0003';
            }
            else if (dataType == 1) {
                array5[0] = '\u0001';
            }
            else {
                array5[0] = '\u0002';
            }
            for (int n21 = 1; n21 < numBands; ++n21) {
                array5[n21] = array5[0];
            }
            set.add(new TIFFField(339, 3, numBands, array5));
        }
        JPEGEncodeParam jpegEncodeParam2 = null;
        JPEGImageEncoder jpegEncoder = null;
        int n22 = 0;
        if (compression == 7) {
            n22 = 0;
            switch (n3) {
                case 2:
                case 3: {
                    n22 = 1;
                    break;
                }
                case 4: {
                    n22 = 2;
                    break;
                }
                case 6: {
                    n22 = 3;
                    break;
                }
            }
            final Raster tile = renderedImage.getTile(0, 0);
            jpegEncodeParam2 = JPEGCodec.getDefaultJPEGEncodeParam(tile, n22);
            modifyEncodeParam(jpegEncodeParam, jpegEncodeParam2, numBands);
            jpegEncodeParam2.setImageInfoValid(false);
            jpegEncodeParam2.setTableInfoValid(true);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JPEGCodec.createJPEGEncoder((OutputStream)byteArrayOutputStream, jpegEncodeParam2).encode(tile);
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            set.add(new TIFFField(347, 7, byteArray.length, byteArray));
            jpegEncoder = null;
        }
        if (n3 == 6) {
            char c = '\u0001';
            char c2 = '\u0001';
            if (compression == 7) {
                c = (char)jpegEncodeParam.getHorizontalSubsampling(0);
                c2 = (char)jpegEncodeParam.getVerticalSubsampling(0);
                for (int n23 = 1; n23 < numBands; ++n23) {
                    final char c3 = (char)jpegEncodeParam.getHorizontalSubsampling(n23);
                    if (c3 > c) {
                        c = c3;
                    }
                    final char c4 = (char)jpegEncodeParam.getVerticalSubsampling(n23);
                    if (c4 > c2) {
                        c2 = c4;
                    }
                }
            }
            set.add(new TIFFField(530, 3, 2, new char[] { c, c2 }));
            set.add(new TIFFField(531, 3, 1, new char[] { (char)((compression == 7) ? 1 : 2) }));
            long[][] array6;
            if (compression == 7) {
                array6 = new long[][] { { 0L, 1L }, { 255L, 1L }, { 128L, 1L }, { 255L, 1L }, { 128L, 1L }, { 255L, 1L } };
            }
            else {
                array6 = new long[][] { { 15L, 1L }, { 235L, 1L }, { 128L, 1L }, { 240L, 1L }, { 128L, 1L }, { 240L, 1L } };
            }
            set.add(new TIFFField(532, 5, 6, array6));
        }
        final TIFFField[] extraFields = tiffEncodeParam.getExtraFields();
        if (extraFields != null) {
            final ArrayList list = new ArrayList<Integer>(set.size());
            final Iterator<Object> iterator = set.iterator();
            while (iterator.hasNext()) {
                list.add(new Integer(iterator.next().getTag()));
            }
            for (final TIFFField tiffField : extraFields) {
                final Integer n25 = new Integer(tiffField.getTag());
                if (!list.contains(n25)) {
                    set.add(tiffField);
                    list.add(n25);
                }
            }
        }
        final int directorySize = this.getDirectorySize(set);
        array2[0] = n + directorySize;
        OutputStream output = null;
        byte[] array7 = null;
        File tempFile = null;
        int n26 = 0;
        int n27 = 0;
        Deflater deflater = null;
        boolean b5 = false;
        if (compression == 1) {
            int n28 = 0;
            if (sampleSize[0] == 16 && array2[0] % 2L != 0L) {
                n28 = 1;
                final long[] array8 = array2;
                final int n29 = 0;
                ++array8[n29];
            }
            else if (sampleSize[0] == 32 && array2[0] % 4L != 0L) {
                n28 = (int)(4L - array2[0] % 4L);
                final long[] array9 = array2;
                final int n30 = 0;
                array9[n30] += n28;
            }
            for (int n31 = 1; n31 < n15; ++n31) {
                array2[n31] = array2[n31 - 1] + array[n31 - 1];
            }
            if (!b) {
                n26 = (int)(array2[0] + n18);
                if ((n26 & 0x1) != 0x0) {
                    ++n26;
                    n27 = 1;
                }
            }
            this.writeDirectory(n, set, n26);
            if (n28 != 0) {
                for (int n32 = 0; n32 < n28; ++n32) {
                    this.output.write(0);
                }
            }
        }
        else {
            if (this.output instanceof SeekableOutputStream) {
                ((SeekableOutputStream)this.output).seek(array2[0]);
            }
            else {
                output = this.output;
                try {
                    tempFile = File.createTempFile("jai-SOS-", ".tmp");
                    tempFile.deleteOnExit();
                    this.output = new SeekableOutputStream(new RandomAccessFile(tempFile, "rw"));
                }
                catch (Exception ex) {
                    this.output = new ByteArrayOutputStream((int)n18);
                }
            }
            int n33 = 0;
            switch (compression) {
                case 32773: {
                    n33 = (int)(n17 + (n16 + 127L) / 128L * a);
                    break;
                }
                case 7: {
                    n33 = 0;
                    if (n3 == 6 && colorModel != null && colorModel.getColorSpace().getType() == 5) {
                        b5 = true;
                        break;
                    }
                    break;
                }
                case 32946: {
                    n33 = (int)n17;
                    deflater = new Deflater(tiffEncodeParam.getDeflateLevel());
                    break;
                }
                default: {
                    n33 = 0;
                    break;
                }
            }
            if (n33 != 0) {
                array7 = new byte[n33];
            }
        }
        int[] pixels = null;
        float[] pixels2 = null;
        final boolean b6 = (sampleSize[0] == 1 && sampleModel instanceof MultiPixelPackedSampleModel && dataType == 0) || (sampleSize[0] == 8 && sampleModel instanceof ComponentSampleModel);
        byte[] b7 = null;
        if (compression != 7) {
            if (dataType == 0) {
                b7 = new byte[a * w * numBands];
            }
            else if (b2) {
                b7 = new byte[2 * a * w * numBands];
            }
            else if (dataType == 3 || dataType == 4) {
                b7 = new byte[4 * a * w * numBands];
            }
        }
        final int n34 = minY + height;
        final int n35 = minX + width;
        int n36 = 0;
        for (int y = minY; y < n34; y += a) {
            final int h = writeTiled ? a : Math.min(a, n34 - y);
            final int len = h * w * numBands;
            for (int x = minX; x < n35; x += w) {
                final Raster data = renderedImage.getData(new Rectangle(x, y, w, h));
                int n37 = 0;
                if (compression != 7) {
                    if (b6) {
                        if (sampleSize[0] == 8) {
                            final ComponentSampleModel componentSampleModel = (ComponentSampleModel)data.getSampleModel();
                            final int[] bankIndices = componentSampleModel.getBankIndices();
                            final int[] bandOffsets = componentSampleModel.getBandOffsets();
                            final int pixelStride = componentSampleModel.getPixelStride();
                            final int scanlineStride = componentSampleModel.getScanlineStride();
                            if (pixelStride != numBands || scanlineStride != n16) {
                                n37 = 0;
                            }
                            else {
                                n37 = 1;
                                for (int n38 = 0; n37 != 0 && n38 < numBands; ++n38) {
                                    if (bankIndices[n38] != 0 || bandOffsets[n38] != n38) {
                                        n37 = 0;
                                    }
                                }
                            }
                        }
                        else {
                            final MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)data.getSampleModel();
                            if (multiPixelPackedSampleModel.getNumBands() == 1 && multiPixelPackedSampleModel.getDataBitOffset() == 0 && multiPixelPackedSampleModel.getPixelBitStride() == 1) {
                                n37 = 1;
                            }
                        }
                    }
                    if (n37 == 0) {
                        if (dataType == 4) {
                            pixels2 = data.getPixels(x, y, w, h, pixels2);
                        }
                        else {
                            pixels = data.getPixels(x, y, w, h, pixels);
                        }
                    }
                }
                int n39 = 0;
                switch (sampleSize[0]) {
                    case 1: {
                        if (n37 != 0) {
                            final byte[] data2 = ((DataBufferByte)data.getDataBuffer()).getData();
                            final MultiPixelPackedSampleModel multiPixelPackedSampleModel2 = (MultiPixelPackedSampleModel)data.getSampleModel();
                            final int scanlineStride2 = multiPixelPackedSampleModel2.getScanlineStride();
                            int offset = multiPixelPackedSampleModel2.getOffset(x - data.getSampleModelTranslateX(), y - data.getSampleModelTranslateY());
                            if (scanlineStride2 == (int)n16) {
                                System.arraycopy(data2, offset, b7, 0, (int)n16 * h);
                            }
                            else {
                                int n40 = 0;
                                for (int n41 = 0; n41 < h; ++n41) {
                                    System.arraycopy(data2, offset, b7, n40, (int)n16);
                                    offset += scanlineStride2;
                                    n40 += (int)n16;
                                }
                            }
                        }
                        else {
                            int n42 = 0;
                            for (int n43 = 0; n43 < h; ++n43) {
                                for (int n44 = 0; n44 < w / 8; ++n44) {
                                    b7[n39++] = (byte)(pixels[n42++] << 7 | pixels[n42++] << 6 | pixels[n42++] << 5 | pixels[n42++] << 4 | pixels[n42++] << 3 | pixels[n42++] << 2 | pixels[n42++] << 1 | pixels[n42++]);
                                }
                                if (w % 8 > 0) {
                                    int n45 = 0;
                                    for (int n46 = 0; n46 < w % 8; ++n46) {
                                        n45 |= pixels[n42++] << 7 - n46;
                                    }
                                    b7[n39++] = (byte)n45;
                                }
                            }
                        }
                        if (compression == 1) {
                            this.output.write(b7, 0, h * ((w + 7) / 8));
                            break;
                        }
                        if (compression == 32773) {
                            final int compressPackBits = compressPackBits(b7, h, (int)n16, array7);
                            array[n36++] = compressPackBits;
                            this.output.write(array7, 0, compressPackBits);
                            break;
                        }
                        if (compression == 32946) {
                            final int deflate = deflate(deflater, b7, array7);
                            array[n36++] = deflate;
                            this.output.write(array7, 0, deflate);
                            break;
                        }
                        break;
                    }
                    case 4: {
                        int n47 = 0;
                        for (int n48 = 0; n48 < h; ++n48) {
                            for (int n49 = 0; n49 < w / 2; ++n49) {
                                b7[n39++] = (byte)(pixels[n47++] << 4 | pixels[n47++]);
                            }
                            if ((w & 0x1) == 0x1) {
                                b7[n39++] = (byte)(pixels[n47++] << 4);
                            }
                        }
                        if (compression == 1) {
                            this.output.write(b7, 0, h * ((w + 1) / 2));
                            break;
                        }
                        if (compression == 32773) {
                            final int compressPackBits2 = compressPackBits(b7, h, (int)n16, array7);
                            array[n36++] = compressPackBits2;
                            this.output.write(array7, 0, compressPackBits2);
                            break;
                        }
                        if (compression == 32946) {
                            final int deflate2 = deflate(deflater, b7, array7);
                            array[n36++] = deflate2;
                            this.output.write(array7, 0, deflate2);
                            break;
                        }
                        break;
                    }
                    case 8: {
                        if (compression != 7) {
                            if (n37 != 0) {
                                final byte[] data3 = ((DataBufferByte)data.getDataBuffer()).getData();
                                final ComponentSampleModel componentSampleModel2 = (ComponentSampleModel)data.getSampleModel();
                                int offset2 = componentSampleModel2.getOffset(x - data.getSampleModelTranslateX(), y - data.getSampleModelTranslateY());
                                final int scanlineStride3 = componentSampleModel2.getScanlineStride();
                                if (scanlineStride3 == (int)n16) {
                                    System.arraycopy(data3, offset2, b7, 0, (int)n16 * h);
                                }
                                else {
                                    int n50 = 0;
                                    for (int n51 = 0; n51 < h; ++n51) {
                                        System.arraycopy(data3, offset2, b7, n50, (int)n16);
                                        offset2 += scanlineStride3;
                                        n50 += (int)n16;
                                    }
                                }
                            }
                            else {
                                for (int n52 = 0; n52 < len; ++n52) {
                                    b7[n52] = (byte)pixels[n52];
                                }
                            }
                        }
                        if (compression == 1) {
                            this.output.write(b7, 0, len);
                            break;
                        }
                        if (compression == 32773) {
                            final int compressPackBits3 = compressPackBits(b7, h, (int)n16, array7);
                            array[n36++] = compressPackBits3;
                            this.output.write(array7, 0, compressPackBits3);
                            break;
                        }
                        if (compression == 7) {
                            final long offset3 = this.getOffset(this.output);
                            if (jpegEncoder == null || jpegEncodeParam2.getWidth() != data.getWidth() || jpegEncodeParam2.getHeight() != data.getHeight()) {
                                jpegEncodeParam2 = JPEGCodec.getDefaultJPEGEncodeParam(data, n22);
                                modifyEncodeParam(jpegEncodeParam, jpegEncodeParam2, numBands);
                                jpegEncoder = JPEGCodec.createJPEGEncoder(this.output, jpegEncodeParam2);
                            }
                            if (b5) {
                                WritableRaster raster;
                                if (data instanceof WritableRaster) {
                                    raster = (WritableRaster)data;
                                }
                                else {
                                    raster = data.createCompatibleWritableRaster();
                                    raster.setRect(data);
                                }
                                if (raster.getMinX() != 0 || raster.getMinY() != 0) {
                                    raster = raster.createWritableTranslatedChild(0, 0);
                                }
                                jpegEncoder.encode(new BufferedImage(colorModel, raster, false, null));
                            }
                            else {
                                jpegEncoder.encode(data.createTranslatedChild(0, 0));
                            }
                            array[n36++] = (int)(this.getOffset(this.output) - offset3);
                            break;
                        }
                        if (compression == 32946) {
                            final int deflate3 = deflate(deflater, b7, array7);
                            array[n36++] = deflate3;
                            this.output.write(array7, 0, deflate3);
                            break;
                        }
                        break;
                    }
                    case 16: {
                        int n53 = 0;
                        for (final int n55 : pixels) {
                            b7[n53++] = (byte)((n55 & 0xFF00) >> 8);
                            b7[n53++] = (byte)(n55 & 0xFF);
                        }
                        if (compression == 1) {
                            this.output.write(b7, 0, len * 2);
                            break;
                        }
                        if (compression == 32773) {
                            final int compressPackBits4 = compressPackBits(b7, h, (int)n16, array7);
                            array[n36++] = compressPackBits4;
                            this.output.write(array7, 0, compressPackBits4);
                            break;
                        }
                        if (compression == 32946) {
                            final int deflate4 = deflate(deflater, b7, array7);
                            array[n36++] = deflate4;
                            this.output.write(array7, 0, deflate4);
                            break;
                        }
                        break;
                    }
                    case 32: {
                        if (dataType == 3) {
                            int n56 = 0;
                            for (final int n58 : pixels) {
                                b7[n56++] = (byte)((n58 & 0xFF000000) >>> 24);
                                b7[n56++] = (byte)((n58 & 0xFF0000) >>> 16);
                                b7[n56++] = (byte)((n58 & 0xFF00) >>> 8);
                                b7[n56++] = (byte)(n58 & 0xFF);
                            }
                        }
                        else {
                            int n59 = 0;
                            for (int n60 = 0; n60 < len; ++n60) {
                                final int floatToIntBits = Float.floatToIntBits(pixels2[n60]);
                                b7[n59++] = (byte)((floatToIntBits & 0xFF000000) >>> 24);
                                b7[n59++] = (byte)((floatToIntBits & 0xFF0000) >>> 16);
                                b7[n59++] = (byte)((floatToIntBits & 0xFF00) >>> 8);
                                b7[n59++] = (byte)(floatToIntBits & 0xFF);
                            }
                        }
                        if (compression == 1) {
                            this.output.write(b7, 0, len * 4);
                            break;
                        }
                        if (compression == 32773) {
                            final int compressPackBits5 = compressPackBits(b7, h, (int)n16, array7);
                            array[n36++] = compressPackBits5;
                            this.output.write(array7, 0, compressPackBits5);
                            break;
                        }
                        if (compression == 32946) {
                            final int deflate5 = deflate(deflater, b7, array7);
                            array[n36++] = deflate5;
                            this.output.write(array7, 0, deflate5);
                            break;
                        }
                        break;
                    }
                }
            }
        }
        if (compression == 1) {
            if (n27 != 0) {
                this.output.write(0);
            }
        }
        else {
            int n61 = 0;
            for (int n62 = 1; n62 < n15; ++n62) {
                final int n63 = (int)array[n62 - 1];
                n61 += n63;
                array2[n62] = array2[n62 - 1] + n63;
            }
            final int n64 = n61 + (int)array[n15 - 1];
            n26 = (b ? 0 : (n + directorySize + n64));
            if ((n26 & 0x1) != 0x0) {
                ++n26;
                n27 = 1;
            }
            if (output == null) {
                if (n27 != 0) {
                    this.output.write(0);
                }
                final SeekableOutputStream seekableOutputStream = (SeekableOutputStream)this.output;
                final long filePointer = seekableOutputStream.getFilePointer();
                seekableOutputStream.seek(n);
                this.writeDirectory(n, set, n26);
                seekableOutputStream.seek(filePointer);
            }
            else if (tempFile != null) {
                final FileInputStream fileInputStream = new FileInputStream(tempFile);
                this.output.close();
                this.output = output;
                this.writeDirectory(n, set, n26);
                final byte[] array10 = new byte[8192];
                int read;
                for (int n65 = 0; n65 < n64; n65 += read) {
                    read = fileInputStream.read(array10);
                    if (read == -1) {
                        break;
                    }
                    this.output.write(array10, 0, read);
                }
                fileInputStream.close();
                tempFile.delete();
                if (n27 != 0) {
                    this.output.write(0);
                }
            }
            else {
                if (!(this.output instanceof ByteArrayOutputStream)) {
                    throw new IllegalStateException();
                }
                final ByteArrayOutputStream byteArrayOutputStream2 = (ByteArrayOutputStream)this.output;
                this.output = output;
                this.writeDirectory(n, set, n26);
                byteArrayOutputStream2.writeTo(this.output);
                if (n27 != 0) {
                    this.output.write(0);
                }
            }
        }
        return n26;
    }
    
    private int getDirectorySize(final SortedSet set) {
        int n = 2 + set.size() * 12 + 4;
        for (final TIFFField tiffField : set) {
            final int n2 = tiffField.getCount() * TIFFImageEncoder.sizeOfType[tiffField.getType()];
            if (n2 > 4) {
                n += n2;
            }
        }
        return n;
    }
    
    private void writeFileHeader() throws IOException {
        this.output.write(77);
        this.output.write(77);
        this.output.write(0);
        this.output.write(42);
        this.writeLong(8L);
    }
    
    private void writeDirectory(final int n, final SortedSet set, final int n2) throws IOException {
        final int size = set.size();
        long n3 = n + 12 * size + 4 + 2;
        final ArrayList<TIFFField> list = new ArrayList<TIFFField>();
        this.writeUnsignedShort(size);
        for (final TIFFField tiffField : set) {
            this.writeUnsignedShort(tiffField.getTag());
            final int type = tiffField.getType();
            this.writeUnsignedShort(type);
            final int count = tiffField.getCount();
            final int valueSize = getValueSize(tiffField);
            this.writeLong((type == 2) ? ((long)valueSize) : ((long)count));
            if (valueSize > 4) {
                this.writeLong(n3);
                n3 += valueSize;
                list.add(tiffField);
            }
            else {
                this.writeValuesAsFourBytes(tiffField);
            }
        }
        this.writeLong(n2);
        for (int i = 0; i < list.size(); ++i) {
            this.writeValues((TIFFField)list.get(i));
        }
    }
    
    private static int getValueSize(final TIFFField tiffField) {
        final int type = tiffField.getType();
        final int count = tiffField.getCount();
        int n = 0;
        if (type == 2) {
            for (int i = 0; i < count; ++i) {
                final byte[] bytes = tiffField.getAsString(i).getBytes();
                n += bytes.length;
                if (bytes[bytes.length - 1] != 0) {
                    ++n;
                }
            }
        }
        else {
            n = count * TIFFImageEncoder.sizeOfType[type];
        }
        return n;
    }
    
    private void writeValuesAsFourBytes(final TIFFField tiffField) throws IOException {
        final int type = tiffField.getType();
        int count = tiffField.getCount();
        switch (type) {
            case 1: {
                final byte[] asBytes = tiffField.getAsBytes();
                if (count > 4) {
                    count = 4;
                }
                for (int i = 0; i < count; ++i) {
                    this.output.write(asBytes[i]);
                }
                for (int j = 0; j < 4 - count; ++j) {
                    this.output.write(0);
                }
                break;
            }
            case 3: {
                final char[] asChars = tiffField.getAsChars();
                if (count > 2) {
                    count = 2;
                }
                for (int k = 0; k < count; ++k) {
                    this.writeUnsignedShort(asChars[k]);
                }
                for (int l = 0; l < 2 - count; ++l) {
                    this.writeUnsignedShort(0);
                }
                break;
            }
            case 4: {
                final long[] asLongs = tiffField.getAsLongs();
                for (int n = 0; n < count; ++n) {
                    this.writeLong(asLongs[n]);
                }
                break;
            }
        }
    }
    
    private void writeValues(final TIFFField tiffField) throws IOException {
        final int type = tiffField.getType();
        final int count = tiffField.getCount();
        switch (type) {
            case 1:
            case 6:
            case 7: {
                final byte[] asBytes = tiffField.getAsBytes();
                for (int i = 0; i < count; ++i) {
                    this.output.write(asBytes[i]);
                }
                break;
            }
            case 3: {
                final char[] asChars = tiffField.getAsChars();
                for (int j = 0; j < count; ++j) {
                    this.writeUnsignedShort(asChars[j]);
                }
                break;
            }
            case 8: {
                final short[] asShorts = tiffField.getAsShorts();
                for (int k = 0; k < count; ++k) {
                    this.writeUnsignedShort(asShorts[k]);
                }
                break;
            }
            case 4:
            case 9: {
                final long[] asLongs = tiffField.getAsLongs();
                for (int l = 0; l < count; ++l) {
                    this.writeLong(asLongs[l]);
                }
                break;
            }
            case 11: {
                final float[] asFloats = tiffField.getAsFloats();
                for (int n = 0; n < count; ++n) {
                    this.writeLong(Float.floatToIntBits(asFloats[n]));
                }
                break;
            }
            case 12: {
                final double[] asDoubles = tiffField.getAsDoubles();
                for (int n2 = 0; n2 < count; ++n2) {
                    final long doubleToLongBits = Double.doubleToLongBits(asDoubles[n2]);
                    this.writeLong(doubleToLongBits >>> 32);
                    this.writeLong(doubleToLongBits & 0xFFFFFFFFL);
                }
                break;
            }
            case 5:
            case 10: {
                final long[][] asRationals = tiffField.getAsRationals();
                for (int n3 = 0; n3 < count; ++n3) {
                    this.writeLong(asRationals[n3][0]);
                    this.writeLong(asRationals[n3][1]);
                }
                break;
            }
            case 2: {
                for (int n4 = 0; n4 < count; ++n4) {
                    final byte[] bytes = tiffField.getAsString(n4).getBytes();
                    this.output.write(bytes);
                    if (bytes[bytes.length - 1] != 0) {
                        this.output.write(0);
                    }
                }
                break;
            }
            default: {
                throw new Error("TIFFImageEncoder10");
            }
        }
    }
    
    private void writeUnsignedShort(final int n) throws IOException {
        this.output.write((n & 0xFF00) >>> 8);
        this.output.write(n & 0xFF);
    }
    
    private void writeLong(final long n) throws IOException {
        this.output.write((int)((n & 0xFFFFFFFFFF000000L) >>> 24));
        this.output.write((int)((n & 0xFF0000L) >>> 16));
        this.output.write((int)((n & 0xFF00L) >>> 8));
        this.output.write((int)(n & 0xFFL));
    }
    
    private long getOffset(final OutputStream outputStream) throws IOException {
        if (outputStream instanceof ByteArrayOutputStream) {
            return ((ByteArrayOutputStream)outputStream).size();
        }
        if (outputStream instanceof SeekableOutputStream) {
            return ((SeekableOutputStream)outputStream).getFilePointer();
        }
        throw new IllegalStateException();
    }
    
    private static int compressPackBits(final byte[] array, final int n, final int n2, final byte[] array2) {
        int n3 = 0;
        int packBits = 0;
        for (int i = 0; i < n; ++i) {
            packBits = packBits(array, n3, n2, array2, packBits);
            n3 += n2;
        }
        return packBits;
    }
    
    private static int packBits(final byte[] array, int i, final int n, final byte[] array2, int n2) {
        final int n3 = i + n - 1;
        final int n4 = n3 - 1;
        while (i <= n3) {
            int n5 = 1;
            final byte b = array[i];
            while (n5 < 127 && i < n3 && array[i] == array[i + 1]) {
                ++n5;
                ++i;
            }
            if (n5 > 1) {
                ++i;
                array2[n2++] = (byte)(-(n5 - 1));
                array2[n2++] = b;
            }
            int n6 = 0;
            final int n7 = n2;
            while (n6 < 128 && ((i < n3 && array[i] != array[i + 1]) || (i < n4 && array[i] != array[i + 2]))) {
                ++n6;
                array2[++n2] = array[i++];
            }
            if (n6 > 0) {
                array2[n7] = (byte)(n6 - 1);
                ++n2;
            }
            if (i == n3) {
                if (n6 > 0 && n6 < 128) {
                    final int n8 = n7;
                    ++array2[n8];
                    array2[n2++] = array[i++];
                }
                else {
                    array2[n2++] = 0;
                    array2[n2++] = array[i++];
                }
            }
        }
        return n2;
    }
    
    private static int deflate(final Deflater deflater, final byte[] input, final byte[] output) {
        deflater.setInput(input);
        deflater.finish();
        final int deflate = deflater.deflate(output);
        deflater.reset();
        return deflate;
    }
    
    private static void modifyEncodeParam(final JPEGEncodeParam jpegEncodeParam, final JPEGEncodeParam jpegEncodeParam2, final int n) {
        jpegEncodeParam2.setDensityUnit(jpegEncodeParam.getDensityUnit());
        jpegEncodeParam2.setXDensity(jpegEncodeParam.getXDensity());
        jpegEncodeParam2.setYDensity(jpegEncodeParam.getYDensity());
        jpegEncodeParam2.setRestartInterval(jpegEncodeParam.getRestartInterval());
        for (int i = 0; i < 4; ++i) {
            final JPEGQTable qTable = jpegEncodeParam.getQTable(i);
            if (qTable != null) {
                jpegEncodeParam2.setQTable(i, qTable);
            }
        }
    }
    
    static {
        sizeOfType = new int[] { 0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8 };
    }
}
