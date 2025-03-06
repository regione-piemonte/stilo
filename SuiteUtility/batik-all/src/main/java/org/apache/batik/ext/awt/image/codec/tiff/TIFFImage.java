// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Map;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import java.util.HashMap;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.Rectangle;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.SampleModel;
import java.util.zip.DataFormatException;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.io.IOException;
import java.io.InputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import java.io.ByteArrayInputStream;
import java.awt.image.Raster;
import java.util.zip.Inflater;
import com.sun.image.codec.jpeg.JPEGDecodeParam;
import org.apache.batik.ext.awt.image.codec.util.SeekableStream;
import org.apache.batik.ext.awt.image.rendered.AbstractRed;

public class TIFFImage extends AbstractRed
{
    public static final int COMP_NONE = 1;
    public static final int COMP_FAX_G3_1D = 2;
    public static final int COMP_FAX_G3_2D = 3;
    public static final int COMP_FAX_G4_2D = 4;
    public static final int COMP_LZW = 5;
    public static final int COMP_JPEG_OLD = 6;
    public static final int COMP_JPEG_TTN2 = 7;
    public static final int COMP_PACKBITS = 32773;
    public static final int COMP_DEFLATE = 32946;
    private static final int TYPE_UNSUPPORTED = -1;
    private static final int TYPE_BILEVEL = 0;
    private static final int TYPE_GRAY_4BIT = 1;
    private static final int TYPE_GRAY = 2;
    private static final int TYPE_GRAY_ALPHA = 3;
    private static final int TYPE_PALETTE = 4;
    private static final int TYPE_RGB = 5;
    private static final int TYPE_RGB_ALPHA = 6;
    private static final int TYPE_YCBCR_SUB = 7;
    private static final int TYPE_GENERIC = 8;
    private static final int TIFF_JPEG_TABLES = 347;
    private static final int TIFF_YCBCR_SUBSAMPLING = 530;
    SeekableStream stream;
    int tileSize;
    int tilesX;
    int tilesY;
    long[] tileOffsets;
    long[] tileByteCounts;
    char[] colormap;
    int sampleSize;
    int compression;
    byte[] palette;
    int numBands;
    int chromaSubH;
    int chromaSubV;
    long tiffT4Options;
    long tiffT6Options;
    int fillOrder;
    int predictor;
    JPEGDecodeParam decodeParam;
    boolean colorConvertJPEG;
    Inflater inflater;
    boolean isBigEndian;
    int imageType;
    boolean isWhiteZero;
    int dataType;
    boolean decodePaletteAsShorts;
    boolean tiled;
    private TIFFFaxDecoder decoder;
    private TIFFLZWDecoder lzwDecoder;
    
    private static final Raster decodeJPEG(final byte[] buf, final JPEGDecodeParam jpegDecodeParam, final boolean b, final int childMinX, final int childMinY) {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
        final JPEGImageDecoder jpegImageDecoder = (jpegDecodeParam == null) ? JPEGCodec.createJPEGDecoder((InputStream)byteArrayInputStream) : JPEGCodec.createJPEGDecoder((InputStream)byteArrayInputStream, jpegDecodeParam);
        Raster raster;
        try {
            raster = (b ? jpegImageDecoder.decodeAsBufferedImage().getWritableTile(0, 0) : jpegImageDecoder.decodeAsRaster());
        }
        catch (IOException ex) {
            throw new RuntimeException("TIFFImage13");
        }
        return raster.createTranslatedChild(childMinX, childMinY);
    }
    
    private final void inflate(final byte[] input, final byte[] output) {
        this.inflater.setInput(input);
        try {
            this.inflater.inflate(output);
        }
        catch (DataFormatException ex) {
            throw new RuntimeException("TIFFImage17: " + ex.getMessage());
        }
        this.inflater.reset();
    }
    
    private static SampleModel createPixelInterleavedSampleModel(final int dataType, final int w, final int h, final int pixelStride) {
        final int[] bandOffsets = new int[pixelStride];
        for (int i = 0; i < pixelStride; ++i) {
            bandOffsets[i] = i;
        }
        return new PixelInterleavedSampleModel(dataType, w, h, pixelStride, w * pixelStride, bandOffsets);
    }
    
    private long[] getFieldAsLongs(final TIFFField tiffField) {
        long[] asLongs;
        if (tiffField.getType() == 3) {
            final char[] asChars = tiffField.getAsChars();
            asLongs = new long[asChars.length];
            for (int i = 0; i < asChars.length; ++i) {
                asLongs[i] = (asChars[i] & '\uffff');
            }
        }
        else {
            if (tiffField.getType() != 4) {
                throw new RuntimeException();
            }
            asLongs = tiffField.getAsLongs();
        }
        return asLongs;
    }
    
    public TIFFImage(final SeekableStream stream, TIFFDecodeParam tiffDecodeParam, final int n) throws IOException {
        this.decodeParam = null;
        this.colorConvertJPEG = false;
        this.inflater = null;
        this.isWhiteZero = false;
        this.decoder = null;
        this.lzwDecoder = null;
        this.stream = stream;
        if (tiffDecodeParam == null) {
            tiffDecodeParam = new TIFFDecodeParam();
        }
        this.decodePaletteAsShorts = tiffDecodeParam.getDecodePaletteAsShorts();
        final TIFFDirectory tiffDirectory = (tiffDecodeParam.getIFDOffset() == null) ? new TIFFDirectory(stream, n) : new TIFFDirectory(stream, tiffDecodeParam.getIFDOffset(), n);
        final TIFFField field = tiffDirectory.getField(277);
        final int numBands = (field == null) ? 1 : ((int)field.getAsLong(0));
        final TIFFField field2 = tiffDirectory.getField(284);
        if (((field2 == null) ? new char[] { '\u0001' } : field2.getAsChars())[0] != '\u0001' && numBands != 1) {
            throw new RuntimeException("TIFFImage0");
        }
        final TIFFField field3 = tiffDirectory.getField(258);
        char[] asChars;
        if (field3 != null) {
            asChars = field3.getAsChars();
        }
        else {
            asChars = new char[] { '\u0001' };
            for (int i = 1; i < asChars.length; ++i) {
                if (asChars[i] != asChars[0]) {
                    throw new RuntimeException("TIFFImage1");
                }
            }
        }
        this.sampleSize = asChars[0];
        final TIFFField field4 = tiffDirectory.getField(339);
        char[] asChars2;
        if (field4 != null) {
            asChars2 = field4.getAsChars();
            for (int j = 1; j < asChars2.length; ++j) {
                if (asChars2[j] != asChars2[0]) {
                    throw new RuntimeException("TIFFImage2");
                }
            }
        }
        else {
            asChars2 = new char[] { '\u0001' };
        }
        boolean b = false;
        switch (this.sampleSize) {
            case 1:
            case 4:
            case 8: {
                if (asChars2[0] != '\u0003') {
                    this.dataType = 0;
                    b = true;
                    break;
                }
                break;
            }
            case 16: {
                if (asChars2[0] != '\u0003') {
                    this.dataType = ((asChars2[0] == '\u0002') ? 2 : 1);
                    b = true;
                    break;
                }
                break;
            }
            case 32: {
                if (asChars2[0] == '\u0003') {
                    b = false;
                    break;
                }
                this.dataType = 3;
                b = true;
                break;
            }
        }
        if (!b) {
            throw new RuntimeException("TIFFImage3");
        }
        final TIFFField field5 = tiffDirectory.getField(259);
        this.compression = ((field5 == null) ? 1 : field5.getAsInt(0));
        final int n2 = (int)tiffDirectory.getFieldAsLong(262);
        this.imageType = -1;
        switch (n2) {
            case 0: {
                this.isWhiteZero = true;
            }
            case 1: {
                if (this.sampleSize == 1 && numBands == 1) {
                    this.imageType = 0;
                    break;
                }
                if (this.sampleSize == 4 && numBands == 1) {
                    this.imageType = 1;
                    break;
                }
                if (this.sampleSize % 8 != 0) {
                    break;
                }
                if (numBands == 1) {
                    this.imageType = 2;
                    break;
                }
                if (numBands == 2) {
                    this.imageType = 3;
                    break;
                }
                this.imageType = 8;
                break;
            }
            case 2: {
                if (this.sampleSize % 8 != 0) {
                    break;
                }
                if (numBands == 3) {
                    this.imageType = 5;
                    break;
                }
                if (numBands == 4) {
                    this.imageType = 6;
                    break;
                }
                this.imageType = 8;
                break;
            }
            case 3: {
                if (numBands == 1 && (this.sampleSize == 4 || this.sampleSize == 8 || this.sampleSize == 16)) {
                    this.imageType = 4;
                    break;
                }
                break;
            }
            case 4: {
                if (this.sampleSize == 1 && numBands == 1) {
                    this.imageType = 0;
                    break;
                }
                break;
            }
            case 6: {
                if (this.compression == 7 && this.sampleSize == 8 && numBands == 3) {
                    this.colorConvertJPEG = tiffDecodeParam.getJPEGDecompressYCbCrToRGB();
                    this.imageType = (this.colorConvertJPEG ? 5 : 8);
                    break;
                }
                final TIFFField field6 = tiffDirectory.getField(530);
                if (field6 != null) {
                    this.chromaSubH = field6.getAsInt(0);
                    this.chromaSubV = field6.getAsInt(1);
                }
                else {
                    final int n3 = 2;
                    this.chromaSubV = n3;
                    this.chromaSubH = n3;
                }
                if (this.chromaSubH * this.chromaSubV == 1) {
                    this.imageType = 8;
                    break;
                }
                if (this.sampleSize == 8 && numBands == 3) {
                    this.imageType = 7;
                    break;
                }
                break;
            }
            default: {
                if (this.sampleSize % 8 == 0) {
                    this.imageType = 8;
                    break;
                }
                break;
            }
        }
        if (this.imageType == -1) {
            throw new RuntimeException("TIFFImage4");
        }
        final Rectangle rectangle = new Rectangle(0, 0, (int)tiffDirectory.getFieldAsLong(256), (int)tiffDirectory.getFieldAsLong(257));
        this.numBands = numBands;
        final TIFFField field7 = tiffDirectory.getField(338);
        final int n4 = (field7 == null) ? 0 : ((int)field7.getAsLong(0));
        int n5;
        int height;
        if (tiffDirectory.getField(324) != null) {
            this.tiled = true;
            n5 = (int)tiffDirectory.getFieldAsLong(322);
            height = (int)tiffDirectory.getFieldAsLong(323);
            this.tileOffsets = tiffDirectory.getField(324).getAsLongs();
            this.tileByteCounts = this.getFieldAsLongs(tiffDirectory.getField(325));
        }
        else {
            this.tiled = false;
            n5 = ((tiffDirectory.getField(322) != null) ? ((int)tiffDirectory.getFieldAsLong(322)) : rectangle.width);
            final TIFFField field8 = tiffDirectory.getField(278);
            if (field8 == null) {
                height = ((tiffDirectory.getField(323) != null) ? ((int)tiffDirectory.getFieldAsLong(323)) : rectangle.height);
            }
            else {
                final long asLong = field8.getAsLong(0);
                if (asLong == (1L << 32) - 1L) {
                    height = rectangle.height;
                }
                else {
                    height = (int)asLong;
                }
            }
            final TIFFField field9 = tiffDirectory.getField(273);
            if (field9 == null) {
                throw new RuntimeException("TIFFImage5");
            }
            this.tileOffsets = this.getFieldAsLongs(field9);
            final TIFFField field10 = tiffDirectory.getField(279);
            if (field10 == null) {
                throw new RuntimeException("TIFFImage6");
            }
            this.tileByteCounts = this.getFieldAsLongs(field10);
        }
        this.tilesX = (rectangle.width + n5 - 1) / n5;
        this.tilesY = (rectangle.height + height - 1) / height;
        this.tileSize = n5 * height * this.numBands;
        this.isBigEndian = tiffDirectory.isBigEndian();
        final TIFFField field11 = tiffDirectory.getField(266);
        if (field11 != null) {
            this.fillOrder = field11.getAsInt(0);
        }
        else {
            this.fillOrder = 1;
        }
        switch (this.compression) {
            case 1:
            case 32773: {
                break;
            }
            case 32946: {
                this.inflater = new Inflater();
                break;
            }
            case 2:
            case 3:
            case 4: {
                if (this.sampleSize != 1) {
                    throw new RuntimeException("TIFFImage7");
                }
                if (this.compression == 3) {
                    final TIFFField field12 = tiffDirectory.getField(292);
                    if (field12 != null) {
                        this.tiffT4Options = field12.getAsLong(0);
                    }
                    else {
                        this.tiffT4Options = 0L;
                    }
                }
                if (this.compression == 4) {
                    final TIFFField field13 = tiffDirectory.getField(293);
                    if (field13 != null) {
                        this.tiffT6Options = field13.getAsLong(0);
                    }
                    else {
                        this.tiffT6Options = 0L;
                    }
                }
                this.decoder = new TIFFFaxDecoder(this.fillOrder, n5, height);
                break;
            }
            case 5: {
                final TIFFField field14 = tiffDirectory.getField(317);
                if (field14 == null) {
                    this.predictor = 1;
                }
                else {
                    this.predictor = field14.getAsInt(0);
                    if (this.predictor != 1 && this.predictor != 2) {
                        throw new RuntimeException("TIFFImage8");
                    }
                    if (this.predictor == 2 && this.sampleSize != 8) {
                        throw new RuntimeException(this.sampleSize + "TIFFImage9");
                    }
                }
                this.lzwDecoder = new TIFFLZWDecoder(n5, this.predictor, numBands);
                break;
            }
            case 6: {
                throw new RuntimeException("TIFFImage15");
            }
            case 7: {
                if (this.sampleSize != 8 || ((this.imageType != 2 || numBands != 1) && (this.imageType != 4 || numBands != 1) && (this.imageType != 5 || numBands != 3))) {
                    throw new RuntimeException("TIFFImage16");
                }
                if (tiffDirectory.isTagPresent(347)) {
                    final JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder((InputStream)new ByteArrayInputStream(tiffDirectory.getField(347).getAsBytes()));
                    jpegDecoder.decodeAsRaster();
                    this.decodeParam = jpegDecoder.getJPEGDecodeParam();
                    break;
                }
                break;
            }
            default: {
                throw new RuntimeException("TIFFImage10");
            }
        }
        SampleModel sampleModel = null;
        ColorModel alphaComponentColorModel = null;
        switch (this.imageType) {
            case 0:
            case 1: {
                sampleModel = new MultiPixelPackedSampleModel(this.dataType, n5, height, this.sampleSize);
                if (this.imageType == 0) {
                    final byte[] array = { (byte)(this.isWhiteZero ? 255 : 0), (byte)(this.isWhiteZero ? 0 : 255) };
                    alphaComponentColorModel = new IndexColorModel(1, 2, array, array, array);
                    break;
                }
                final byte[] array2 = new byte[16];
                if (this.isWhiteZero) {
                    for (int k = 0; k < array2.length; ++k) {
                        array2[k] = (byte)(255 - 16 * k);
                    }
                }
                else {
                    for (int l = 0; l < array2.length; ++l) {
                        array2[l] = (byte)(16 * l);
                    }
                }
                alphaComponentColorModel = new IndexColorModel(4, 16, array2, array2, array2);
                break;
            }
            case 2:
            case 3:
            case 5:
            case 6: {
                final int[] bandOffsets = new int[this.numBands];
                for (int n6 = 0; n6 < this.numBands; ++n6) {
                    bandOffsets[n6] = this.numBands - 1 - n6;
                }
                sampleModel = new PixelInterleavedSampleModel(this.dataType, n5, height, this.numBands, this.numBands * n5, bandOffsets);
                if (this.imageType == 2) {
                    alphaComponentColorModel = new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { this.sampleSize }, false, false, 1, this.dataType);
                    break;
                }
                if (this.imageType == 5) {
                    alphaComponentColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { this.sampleSize, this.sampleSize, this.sampleSize }, false, false, 1, this.dataType);
                    break;
                }
                int n7 = 1;
                if (n4 == 1) {
                    n7 = 3;
                }
                else if (n4 == 2) {
                    n7 = 2;
                }
                alphaComponentColorModel = this.createAlphaComponentColorModel(this.dataType, this.numBands, n4 == 1, n7);
                break;
            }
            case 7:
            case 8: {
                final int[] bandOffsets2 = new int[this.numBands];
                for (int n8 = 0; n8 < this.numBands; ++n8) {
                    bandOffsets2[n8] = n8;
                }
                sampleModel = new PixelInterleavedSampleModel(this.dataType, n5, height, this.numBands, this.numBands * n5, bandOffsets2);
                alphaComponentColorModel = null;
                break;
            }
            case 4: {
                final TIFFField field15 = tiffDirectory.getField(320);
                if (field15 == null) {
                    throw new RuntimeException("TIFFImage11");
                }
                this.colormap = field15.getAsChars();
                if (this.decodePaletteAsShorts) {
                    this.numBands = 3;
                    if (this.dataType == 0) {
                        this.dataType = 1;
                    }
                    sampleModel = createPixelInterleavedSampleModel(this.dataType, n5, height, this.numBands);
                    alphaComponentColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 16, 16, 16 }, false, false, 1, this.dataType);
                    break;
                }
                this.numBands = 1;
                if (this.sampleSize == 4) {
                    sampleModel = new MultiPixelPackedSampleModel(0, n5, height, this.sampleSize);
                }
                else if (this.sampleSize == 8) {
                    sampleModel = createPixelInterleavedSampleModel(0, n5, height, this.numBands);
                }
                else if (this.sampleSize == 16) {
                    this.dataType = 1;
                    sampleModel = createPixelInterleavedSampleModel(1, n5, height, this.numBands);
                }
                final int size = this.colormap.length / 3;
                final byte[] r = new byte[size];
                final byte[] g = new byte[size];
                final byte[] b2 = new byte[size];
                final int n9 = size;
                final int n10 = size * 2;
                if (this.dataType == 2) {
                    for (int n11 = 0; n11 < size; ++n11) {
                        r[n11] = tiffDecodeParam.decodeSigned16BitsTo8Bits((short)this.colormap[n11]);
                        g[n11] = tiffDecodeParam.decodeSigned16BitsTo8Bits((short)this.colormap[n9 + n11]);
                        b2[n11] = tiffDecodeParam.decodeSigned16BitsTo8Bits((short)this.colormap[n10 + n11]);
                    }
                }
                else {
                    for (int n12 = 0; n12 < size; ++n12) {
                        r[n12] = tiffDecodeParam.decode16BitsTo8Bits(this.colormap[n12] & '\uffff');
                        g[n12] = tiffDecodeParam.decode16BitsTo8Bits(this.colormap[n9 + n12] & '\uffff');
                        b2[n12] = tiffDecodeParam.decode16BitsTo8Bits(this.colormap[n10 + n12] & '\uffff');
                    }
                }
                alphaComponentColorModel = new IndexColorModel(this.sampleSize, size, r, g, b2);
                break;
            }
            default: {
                throw new RuntimeException("TIFFImage4");
            }
        }
        final HashMap<String, TIFFDirectory> hashMap = new HashMap<String, TIFFDirectory>();
        hashMap.put("tiff_directory", tiffDirectory);
        this.init((CachableRed)null, rectangle, alphaComponentColorModel, sampleModel, 0, 0, hashMap);
    }
    
    public TIFFDirectory getPrivateIFD(final long n) throws IOException {
        return new TIFFDirectory(this.stream, n, 0);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.copyToRaster(writableRaster);
        return writableRaster;
    }
    
    public synchronized Raster getTile(final int n, final int n2) {
        if (n < 0 || n >= this.tilesX || n2 < 0 || n2 >= this.tilesY) {
            throw new IllegalArgumentException("TIFFImage12");
        }
        byte[] data = null;
        short[] array = null;
        int[] data2 = null;
        final SampleModel sampleModel = this.getSampleModel();
        final WritableRaster tile = this.makeTile(n, n2);
        final DataBuffer dataBuffer = tile.getDataBuffer();
        final int dataType = sampleModel.getDataType();
        if (dataType == 0) {
            data = ((DataBufferByte)dataBuffer).getData();
        }
        else if (dataType == 1) {
            array = ((DataBufferUShort)dataBuffer).getData();
        }
        else if (dataType == 2) {
            array = ((DataBufferShort)dataBuffer).getData();
        }
        else if (dataType == 3) {
            data2 = ((DataBufferInt)dataBuffer).getData();
        }
        long filePointer;
        try {
            filePointer = this.stream.getFilePointer();
            this.stream.seek(this.tileOffsets[n2 * this.tilesX + n]);
        }
        catch (IOException ex) {
            throw new RuntimeException("TIFFImage13");
        }
        final int n3 = (int)this.tileByteCounts[n2 * this.tilesX + n];
        Rectangle bounds;
        if (!this.tiled) {
            bounds = tile.getBounds();
        }
        else {
            bounds = new Rectangle(tile.getMinX(), tile.getMinY(), this.tileWidth, this.tileHeight);
        }
        final int n4 = bounds.width * bounds.height * this.numBands;
        final byte[] array2 = (byte[])((this.compression != 1 || this.imageType == 4) ? new byte[n3] : null);
        if (this.imageType == 0) {
            try {
                if (this.compression == 32773) {
                    this.stream.readFully(array2, 0, n3);
                    int n5;
                    if (bounds.width % 8 == 0) {
                        n5 = bounds.width / 8 * bounds.height;
                    }
                    else {
                        n5 = (bounds.width / 8 + 1) * bounds.height;
                    }
                    this.decodePackbits(array2, n5, data);
                }
                else if (this.compression == 5) {
                    this.stream.readFully(array2, 0, n3);
                    this.lzwDecoder.decode(array2, data, bounds.height);
                }
                else if (this.compression == 2) {
                    this.stream.readFully(array2, 0, n3);
                    this.decoder.decode1D(data, array2, 0, bounds.height);
                }
                else if (this.compression == 3) {
                    this.stream.readFully(array2, 0, n3);
                    this.decoder.decode2D(data, array2, 0, bounds.height, this.tiffT4Options);
                }
                else if (this.compression == 4) {
                    this.stream.readFully(array2, 0, n3);
                    this.decoder.decodeT6(data, array2, 0, bounds.height, this.tiffT6Options);
                }
                else if (this.compression == 32946) {
                    this.stream.readFully(array2, 0, n3);
                    this.inflate(array2, data);
                }
                else if (this.compression == 1) {
                    this.stream.readFully(data, 0, n3);
                }
                this.stream.seek(filePointer);
                return tile;
            }
            catch (IOException ex2) {
                throw new RuntimeException("TIFFImage13");
            }
        }
        if (this.imageType == 4) {
            if (this.sampleSize == 16) {
                if (this.decodePaletteAsShorts) {
                    short[] array3 = null;
                    final int n6 = n4 / 3;
                    final int n7 = n6 * 2;
                    try {
                        if (this.compression == 32773) {
                            this.stream.readFully(array2, 0, n3);
                            final byte[] array4 = new byte[n7];
                            this.decodePackbits(array2, n7, array4);
                            array3 = new short[n6];
                            this.interpretBytesAsShorts(array4, array3, n6);
                        }
                        else if (this.compression == 5) {
                            this.stream.readFully(array2, 0, n3);
                            final byte[] array5 = new byte[n7];
                            this.lzwDecoder.decode(array2, array5, bounds.height);
                            array3 = new short[n6];
                            this.interpretBytesAsShorts(array5, array3, n6);
                        }
                        else if (this.compression == 32946) {
                            this.stream.readFully(array2, 0, n3);
                            final byte[] array6 = new byte[n7];
                            this.inflate(array2, array6);
                            array3 = new short[n6];
                            this.interpretBytesAsShorts(array6, array3, n6);
                        }
                        else if (this.compression == 1) {
                            array3 = new short[n3 / 2];
                            this.readShorts(n3 / 2, array3);
                        }
                        this.stream.seek(filePointer);
                    }
                    catch (IOException ex3) {
                        throw new RuntimeException("TIFFImage13");
                    }
                    if (dataType == 1) {
                        int n8 = 0;
                        final int n9 = this.colormap.length / 3;
                        final int n10 = n9 * 2;
                        for (int i = 0; i < n6; ++i) {
                            final int n11 = array3[i] & 0xFFFF;
                            array[n8++] = (short)(this.colormap[n11 + n10] & '\uffff');
                            array[n8++] = (short)(this.colormap[n11 + n9] & '\uffff');
                            array[n8++] = (short)(this.colormap[n11] & '\uffff');
                        }
                        return tile;
                    }
                    if (dataType == 2) {
                        int n12 = 0;
                        final int n13 = this.colormap.length / 3;
                        final int n14 = n13 * 2;
                        for (int j = 0; j < n6; ++j) {
                            final int n15 = array3[j] & 0xFFFF;
                            array[n12++] = (short)this.colormap[n15 + n14];
                            array[n12++] = (short)this.colormap[n15 + n13];
                            array[n12++] = (short)this.colormap[n15];
                        }
                        return tile;
                    }
                    return tile;
                }
                else {
                    try {
                        if (this.compression == 32773) {
                            this.stream.readFully(array2, 0, n3);
                            final int n16 = n4 * 2;
                            final byte[] array7 = new byte[n16];
                            this.decodePackbits(array2, n16, array7);
                            this.interpretBytesAsShorts(array7, array, n4);
                        }
                        else if (this.compression == 5) {
                            this.stream.readFully(array2, 0, n3);
                            final byte[] array8 = new byte[n4 * 2];
                            this.lzwDecoder.decode(array2, array8, bounds.height);
                            this.interpretBytesAsShorts(array8, array, n4);
                        }
                        else if (this.compression == 32946) {
                            this.stream.readFully(array2, 0, n3);
                            final byte[] array9 = new byte[n4 * 2];
                            this.inflate(array2, array9);
                            this.interpretBytesAsShorts(array9, array, n4);
                        }
                        else if (this.compression == 1) {
                            this.readShorts(n3 / 2, array);
                        }
                        this.stream.seek(filePointer);
                        return tile;
                    }
                    catch (IOException ex4) {
                        throw new RuntimeException("TIFFImage13");
                    }
                }
            }
            if (this.sampleSize == 8) {
                if (this.decodePaletteAsShorts) {
                    byte[] array10 = null;
                    final int n17 = n4 / 3;
                    try {
                        if (this.compression == 32773) {
                            this.stream.readFully(array2, 0, n3);
                            array10 = new byte[n17];
                            this.decodePackbits(array2, n17, array10);
                        }
                        else if (this.compression == 5) {
                            this.stream.readFully(array2, 0, n3);
                            array10 = new byte[n17];
                            this.lzwDecoder.decode(array2, array10, bounds.height);
                        }
                        else if (this.compression == 7) {
                            this.stream.readFully(array2, 0, n3);
                            final Raster decodeJPEG = decodeJPEG(array2, this.decodeParam, this.colorConvertJPEG, tile.getMinX(), tile.getMinY());
                            final int[] iArray = new int[n17];
                            decodeJPEG.getPixels(tile.getMinX(), tile.getMinY(), tile.getWidth(), tile.getHeight(), iArray);
                            array10 = new byte[n17];
                            for (int k = 0; k < n17; ++k) {
                                array10[k] = (byte)iArray[k];
                            }
                        }
                        else if (this.compression == 32946) {
                            this.stream.readFully(array2, 0, n3);
                            array10 = new byte[n17];
                            this.inflate(array2, array10);
                        }
                        else if (this.compression == 1) {
                            array10 = new byte[n3];
                            this.stream.readFully(array10, 0, n3);
                        }
                        this.stream.seek(filePointer);
                    }
                    catch (IOException ex5) {
                        throw new RuntimeException("TIFFImage13");
                    }
                    int n18 = 0;
                    final int n19 = this.colormap.length / 3;
                    final int n20 = n19 * 2;
                    for (int l = 0; l < n17; ++l) {
                        final int n21 = array10[l] & 0xFF;
                        array[n18++] = (short)(this.colormap[n21 + n20] & '\uffff');
                        array[n18++] = (short)(this.colormap[n21 + n19] & '\uffff');
                        array[n18++] = (short)(this.colormap[n21] & '\uffff');
                    }
                    return tile;
                }
                try {
                    if (this.compression == 32773) {
                        this.stream.readFully(array2, 0, n3);
                        this.decodePackbits(array2, n4, data);
                    }
                    else if (this.compression == 5) {
                        this.stream.readFully(array2, 0, n3);
                        this.lzwDecoder.decode(array2, data, bounds.height);
                    }
                    else if (this.compression == 7) {
                        this.stream.readFully(array2, 0, n3);
                        tile.setRect(decodeJPEG(array2, this.decodeParam, this.colorConvertJPEG, tile.getMinX(), tile.getMinY()));
                    }
                    else if (this.compression == 32946) {
                        this.stream.readFully(array2, 0, n3);
                        this.inflate(array2, data);
                    }
                    else if (this.compression == 1) {
                        this.stream.readFully(data, 0, n3);
                    }
                    this.stream.seek(filePointer);
                    return tile;
                }
                catch (IOException ex6) {
                    throw new RuntimeException("TIFFImage13");
                }
            }
            if (this.sampleSize != 4) {
                return tile;
            }
            final int n22 = (bounds.width % 2 != 0) ? 1 : 0;
            final int n23 = (bounds.width / 2 + n22) * bounds.height;
            if (this.decodePaletteAsShorts) {
                byte[] array11 = null;
                try {
                    this.stream.readFully(array2, 0, n3);
                    this.stream.seek(filePointer);
                }
                catch (IOException ex7) {
                    throw new RuntimeException("TIFFImage13");
                }
                if (this.compression == 32773) {
                    array11 = new byte[n23];
                    this.decodePackbits(array2, n23, array11);
                }
                else if (this.compression == 5) {
                    array11 = new byte[n23];
                    this.lzwDecoder.decode(array2, array11, bounds.height);
                }
                else if (this.compression == 32946) {
                    array11 = new byte[n23];
                    this.inflate(array2, array11);
                }
                else if (this.compression == 1) {
                    array11 = array2;
                }
                final int n24 = n4 / 3;
                final byte[] array12 = new byte[n24];
                int n25 = 0;
                int n26 = 0;
                for (int n27 = 0; n27 < bounds.height; ++n27) {
                    for (int n28 = 0; n28 < bounds.width / 2; ++n28) {
                        array12[n26++] = (byte)((array11[n25] & 0xF0) >> 4);
                        array12[n26++] = (byte)(array11[n25++] & 0xF);
                    }
                    if (n22 == 1) {
                        array12[n26++] = (byte)((array11[n25++] & 0xF0) >> 4);
                    }
                }
                final int n29 = this.colormap.length / 3;
                final int n30 = n29 * 2;
                int n31 = 0;
                for (int n32 = 0; n32 < n24; ++n32) {
                    final int n33 = array12[n32] & 0xFF;
                    array[n31++] = (short)(this.colormap[n33 + n30] & '\uffff');
                    array[n31++] = (short)(this.colormap[n33 + n29] & '\uffff');
                    array[n31++] = (short)(this.colormap[n33] & '\uffff');
                }
                return tile;
            }
            try {
                if (this.compression == 32773) {
                    this.stream.readFully(array2, 0, n3);
                    this.decodePackbits(array2, n23, data);
                }
                else if (this.compression == 5) {
                    this.stream.readFully(array2, 0, n3);
                    this.lzwDecoder.decode(array2, data, bounds.height);
                }
                else if (this.compression == 32946) {
                    this.stream.readFully(array2, 0, n3);
                    this.inflate(array2, data);
                }
                else if (this.compression == 1) {
                    this.stream.readFully(data, 0, n3);
                }
                this.stream.seek(filePointer);
                return tile;
            }
            catch (IOException ex8) {
                throw new RuntimeException("TIFFImage13");
            }
        }
        if (this.imageType == 1) {
            try {
                if (this.compression == 32773) {
                    this.stream.readFully(array2, 0, n3);
                    int n34;
                    if (bounds.width % 8 == 0) {
                        n34 = bounds.width / 2 * bounds.height;
                    }
                    else {
                        n34 = (bounds.width / 2 + 1) * bounds.height;
                    }
                    this.decodePackbits(array2, n34, data);
                }
                else if (this.compression == 5) {
                    this.stream.readFully(array2, 0, n3);
                    this.lzwDecoder.decode(array2, data, bounds.height);
                }
                else if (this.compression == 32946) {
                    this.stream.readFully(array2, 0, n3);
                    this.inflate(array2, data);
                }
                else {
                    this.stream.readFully(data, 0, n3);
                }
                this.stream.seek(filePointer);
                return tile;
            }
            catch (IOException ex9) {
                throw new RuntimeException("TIFFImage13");
            }
        }
        try {
            if (this.sampleSize == 8) {
                if (this.compression == 1) {
                    this.stream.readFully(data, 0, n3);
                }
                else if (this.compression == 5) {
                    this.stream.readFully(array2, 0, n3);
                    this.lzwDecoder.decode(array2, data, bounds.height);
                }
                else if (this.compression == 32773) {
                    this.stream.readFully(array2, 0, n3);
                    this.decodePackbits(array2, n4, data);
                }
                else if (this.compression == 7) {
                    this.stream.readFully(array2, 0, n3);
                    tile.setRect(decodeJPEG(array2, this.decodeParam, this.colorConvertJPEG, tile.getMinX(), tile.getMinY()));
                }
                else if (this.compression == 32946) {
                    this.stream.readFully(array2, 0, n3);
                    this.inflate(array2, data);
                }
            }
            else if (this.sampleSize == 16) {
                if (this.compression == 1) {
                    this.readShorts(n3 / 2, array);
                }
                else if (this.compression == 5) {
                    this.stream.readFully(array2, 0, n3);
                    final byte[] array13 = new byte[n4 * 2];
                    this.lzwDecoder.decode(array2, array13, bounds.height);
                    this.interpretBytesAsShorts(array13, array, n4);
                }
                else if (this.compression == 32773) {
                    this.stream.readFully(array2, 0, n3);
                    final int n35 = n4 * 2;
                    final byte[] array14 = new byte[n35];
                    this.decodePackbits(array2, n35, array14);
                    this.interpretBytesAsShorts(array14, array, n4);
                }
                else if (this.compression == 32946) {
                    this.stream.readFully(array2, 0, n3);
                    final byte[] array15 = new byte[n4 * 2];
                    this.inflate(array2, array15);
                    this.interpretBytesAsShorts(array15, array, n4);
                }
            }
            else if (this.sampleSize == 32 && dataType == 3) {
                if (this.compression == 1) {
                    this.readInts(n3 / 4, data2);
                }
                else if (this.compression == 5) {
                    this.stream.readFully(array2, 0, n3);
                    final byte[] array16 = new byte[n4 * 4];
                    this.lzwDecoder.decode(array2, array16, bounds.height);
                    this.interpretBytesAsInts(array16, data2, n4);
                }
                else if (this.compression == 32773) {
                    this.stream.readFully(array2, 0, n3);
                    final int n36 = n4 * 4;
                    final byte[] array17 = new byte[n36];
                    this.decodePackbits(array2, n36, array17);
                    this.interpretBytesAsInts(array17, data2, n4);
                }
                else if (this.compression == 32946) {
                    this.stream.readFully(array2, 0, n3);
                    final byte[] array18 = new byte[n4 * 4];
                    this.inflate(array2, array18);
                    this.interpretBytesAsInts(array18, data2, n4);
                }
            }
            this.stream.seek(filePointer);
        }
        catch (IOException ex10) {
            throw new RuntimeException("TIFFImage13");
        }
        switch (this.imageType) {
            case 2:
            case 3: {
                if (!this.isWhiteZero) {
                    break;
                }
                if (dataType == 0 && !(this.getColorModel() instanceof IndexColorModel)) {
                    for (int n37 = 0; n37 < data.length; n37 += this.numBands) {
                        data[n37] = (byte)(255 - data[n37]);
                    }
                    break;
                }
                if (dataType == 1) {
                    final int n38 = 65535;
                    for (int n39 = 0; n39 < array.length; n39 += this.numBands) {
                        array[n39] = (short)(n38 - array[n39]);
                    }
                    break;
                }
                if (dataType == 2) {
                    for (int n40 = 0; n40 < array.length; n40 += this.numBands) {
                        array[n40] ^= -1;
                    }
                    break;
                }
                if (dataType == 3) {
                    final long n41 = 4294967295L;
                    for (int n42 = 0; n42 < data2.length; n42 += this.numBands) {
                        data2[n42] = (int)(n41 - data2[n42]);
                    }
                    break;
                }
                break;
            }
            case 5: {
                if (this.sampleSize == 8 && this.compression != 7) {
                    for (int n43 = 0; n43 < n4; n43 += 3) {
                        final byte b = data[n43];
                        data[n43] = data[n43 + 2];
                        data[n43 + 2] = b;
                    }
                    break;
                }
                if (this.sampleSize == 16) {
                    for (int n44 = 0; n44 < n4; n44 += 3) {
                        final short n45 = array[n44];
                        array[n44] = array[n44 + 2];
                        array[n44 + 2] = n45;
                    }
                    break;
                }
                if (this.sampleSize == 32 && dataType == 3) {
                    for (int n46 = 0; n46 < n4; n46 += 3) {
                        final int n47 = data2[n46];
                        data2[n46] = data2[n46 + 2];
                        data2[n46 + 2] = n47;
                    }
                    break;
                }
                break;
            }
            case 6: {
                if (this.sampleSize == 8) {
                    for (int n48 = 0; n48 < n4; n48 += 4) {
                        final byte b2 = data[n48];
                        data[n48] = data[n48 + 3];
                        data[n48 + 3] = b2;
                        final byte b3 = data[n48 + 1];
                        data[n48 + 1] = data[n48 + 2];
                        data[n48 + 2] = b3;
                    }
                    break;
                }
                if (this.sampleSize == 16) {
                    for (int n49 = 0; n49 < n4; n49 += 4) {
                        final short n50 = array[n49];
                        array[n49] = array[n49 + 3];
                        array[n49 + 3] = n50;
                        final short n51 = array[n49 + 1];
                        array[n49 + 1] = array[n49 + 2];
                        array[n49 + 2] = n51;
                    }
                    break;
                }
                if (this.sampleSize == 32 && dataType == 3) {
                    for (int n52 = 0; n52 < n4; n52 += 4) {
                        final int n53 = data2[n52];
                        data2[n52] = data2[n52 + 3];
                        data2[n52 + 3] = n53;
                        final int n54 = data2[n52 + 1];
                        data2[n52 + 1] = data2[n52 + 2];
                        data2[n52 + 2] = n54;
                    }
                    break;
                }
                break;
            }
            case 7: {
                final int n55 = this.chromaSubH * this.chromaSubV;
                final int n56 = bounds.width / this.chromaSubH;
                final int n57 = bounds.height / this.chromaSubV;
                final byte[] array19 = new byte[n56 * n57 * (n55 + 2)];
                System.arraycopy(data, 0, array19, 0, array19.length);
                final int n58 = n55 * 3;
                final int[] iArray2 = new int[n58];
                int n59 = 0;
                final int n60 = n55;
                final int n61 = n60 + 1;
                int y = bounds.y;
                for (int n62 = 0; n62 < n57; ++n62) {
                    int x = bounds.x;
                    for (int n63 = 0; n63 < n56; ++n63) {
                        final byte b4 = array19[n59 + n60];
                        final byte b5 = array19[n59 + n61];
                        for (int n64 = 0; n64 < n58; iArray2[n64++] = array19[n59++], iArray2[n64++] = b4, iArray2[n64++] = b5) {}
                        n59 += 2;
                        tile.setPixels(x, y, this.chromaSubH, this.chromaSubV, iArray2);
                        x += this.chromaSubH;
                    }
                    y += this.chromaSubV;
                }
                break;
            }
        }
        return tile;
    }
    
    private void readShorts(final int n, final short[] array) {
        final int n2 = 2 * n;
        final byte[] array2 = new byte[n2];
        try {
            this.stream.readFully(array2, 0, n2);
        }
        catch (IOException ex) {
            throw new RuntimeException("TIFFImage13");
        }
        this.interpretBytesAsShorts(array2, array, n);
    }
    
    private void readInts(final int n, final int[] array) {
        final int n2 = 4 * n;
        final byte[] array2 = new byte[n2];
        try {
            this.stream.readFully(array2, 0, n2);
        }
        catch (IOException ex) {
            throw new RuntimeException("TIFFImage13");
        }
        this.interpretBytesAsInts(array2, array, n);
    }
    
    private void interpretBytesAsShorts(final byte[] array, final short[] array2, final int n) {
        int n2 = 0;
        if (this.isBigEndian) {
            for (int i = 0; i < n; ++i) {
                array2[i] = (short)(((array[n2++] & 0xFF) << 8) + (array[n2++] & 0xFF));
            }
        }
        else {
            for (int j = 0; j < n; ++j) {
                array2[j] = (short)(((array[n2++] & 0xFF) << 8) + (array[n2++] & 0xFF));
            }
        }
    }
    
    private void interpretBytesAsInts(final byte[] array, final int[] array2, final int n) {
        int n2 = 0;
        if (this.isBigEndian) {
            for (int i = 0; i < n; ++i) {
                array2[i] = ((array[n2++] & 0xFF) << 24 | (array[n2++] & 0xFF) << 16 | (array[n2++] & 0xFF) << 8 | (array[n2++] & 0xFF));
            }
        }
        else {
            for (int j = 0; j < n; ++j) {
                array2[j] = ((array[n2++] & 0xFF) | (array[n2++] & 0xFF) << 8 | (array[n2++] & 0xFF) << 16 | (array[n2++] & 0xFF) << 24);
            }
        }
    }
    
    private byte[] decodePackbits(final byte[] array, final int n, byte[] array2) {
        if (array2 == null) {
            array2 = new byte[n];
        }
        int n2 = 0;
        int i = 0;
        try {
            while (i < n) {
                final byte b = array[n2++];
                if (b >= 0 && b <= 127) {
                    for (int j = 0; j < b + 1; ++j) {
                        array2[i++] = array[n2++];
                    }
                }
                else if (b <= -1 && b >= -127) {
                    final byte b2 = array[n2++];
                    for (int k = 0; k < -b + 1; ++k) {
                        array2[i++] = b2;
                    }
                }
                else {
                    ++n2;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new RuntimeException("TIFFImage14");
        }
        return array2;
    }
    
    private ComponentColorModel createAlphaComponentColorModel(final int transferType, final int n, final boolean isAlphaPremultiplied, final int transparency) {
        ColorSpace colorSpace = null;
        switch (n) {
            case 2: {
                colorSpace = ColorSpace.getInstance(1003);
                break;
            }
            case 4: {
                colorSpace = ColorSpace.getInstance(1000);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        int n2 = 0;
        switch (transferType) {
            case 0: {
                n2 = 8;
                break;
            }
            case 1:
            case 2: {
                n2 = 16;
                break;
            }
            case 3: {
                n2 = 32;
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        final int[] bits = new int[n];
        for (int i = 0; i < n; ++i) {
            bits[i] = n2;
        }
        return new ComponentColorModel(colorSpace, bits, true, isAlphaPremultiplied, transparency, transferType);
    }
}
