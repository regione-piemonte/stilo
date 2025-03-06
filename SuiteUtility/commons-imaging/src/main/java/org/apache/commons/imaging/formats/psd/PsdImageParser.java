// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.psd;

import java.util.Collection;
import org.apache.commons.imaging.formats.psd.datareaders.DataReader;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParser;
import org.apache.commons.imaging.common.BinaryFileParser;
import org.apache.commons.imaging.formats.psd.datareaders.CompressedDataReader;
import org.apache.commons.imaging.formats.psd.datareaders.UncompressedDataReader;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParserIndexed;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParserLab;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParserCmyk;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParserRgb;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParserGrayscale;
import org.apache.commons.imaging.formats.psd.dataparsers.DataParserBitmap;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.common.ImageMetadata;
import java.awt.Dimension;
import java.util.Map;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageParser;

public class PsdImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".psd";
    private static final String[] ACCEPTED_EXTENSIONS;
    private static final int PSD_SECTION_HEADER = 0;
    private static final int PSD_SECTION_COLOR_MODE = 1;
    private static final int PSD_SECTION_IMAGE_RESOURCES = 2;
    private static final int PSD_SECTION_LAYER_AND_MASK_DATA = 3;
    private static final int PSD_SECTION_IMAGE_DATA = 4;
    private static final int PSD_HEADER_LENGTH = 26;
    private static final int COLOR_MODE_INDEXED = 2;
    public static final int IMAGE_RESOURCE_ID_ICC_PROFILE = 1039;
    public static final int IMAGE_RESOURCE_ID_XMP = 1060;
    public static final String BLOCK_NAME_XMP = "XMP";
    
    public PsdImageParser() {
        super.setByteOrder(ByteOrder.BIG_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "PSD-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".psd";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return PsdImageParser.ACCEPTED_EXTENSIONS.clone();
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.PSD };
    }
    
    private PsdHeaderInfo readHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final PsdHeaderInfo ret = this.readHeader(is);
            return ret;
        }
    }
    
    private PsdHeaderInfo readHeader(final InputStream is) throws ImageReadException, IOException {
        BinaryFunctions.readAndVerifyBytes(is, new byte[] { 56, 66, 80, 83 }, "Not a Valid PSD File");
        final int version = BinaryFunctions.read2Bytes("Version", is, "Not a Valid PSD File", this.getByteOrder());
        final byte[] reserved = BinaryFunctions.readBytes("Reserved", is, 6, "Not a Valid PSD File");
        final int channels = BinaryFunctions.read2Bytes("Channels", is, "Not a Valid PSD File", this.getByteOrder());
        final int rows = BinaryFunctions.read4Bytes("Rows", is, "Not a Valid PSD File", this.getByteOrder());
        final int columns = BinaryFunctions.read4Bytes("Columns", is, "Not a Valid PSD File", this.getByteOrder());
        final int depth = BinaryFunctions.read2Bytes("Depth", is, "Not a Valid PSD File", this.getByteOrder());
        final int mode = BinaryFunctions.read2Bytes("Mode", is, "Not a Valid PSD File", this.getByteOrder());
        return new PsdHeaderInfo(version, reserved, channels, rows, columns, depth, mode);
    }
    
    private PsdImageContents readImageContents(final InputStream is) throws ImageReadException, IOException {
        final PsdHeaderInfo header = this.readHeader(is);
        final int ColorModeDataLength = BinaryFunctions.read4Bytes("ColorModeDataLength", is, "Not a Valid PSD File", this.getByteOrder());
        BinaryFunctions.skipBytes(is, ColorModeDataLength);
        final int ImageResourcesLength = BinaryFunctions.read4Bytes("ImageResourcesLength", is, "Not a Valid PSD File", this.getByteOrder());
        BinaryFunctions.skipBytes(is, ImageResourcesLength);
        final int LayerAndMaskDataLength = BinaryFunctions.read4Bytes("LayerAndMaskDataLength", is, "Not a Valid PSD File", this.getByteOrder());
        BinaryFunctions.skipBytes(is, LayerAndMaskDataLength);
        final int Compression = BinaryFunctions.read2Bytes("Compression", is, "Not a Valid PSD File", this.getByteOrder());
        return new PsdImageContents(header, ColorModeDataLength, ImageResourcesLength, LayerAndMaskDataLength, Compression);
    }
    
    private List<ImageResourceBlock> readImageResourceBlocks(final byte[] bytes, final int[] imageResourceIDs, final int maxBlocksToRead) throws ImageReadException, IOException {
        return this.readImageResourceBlocks(new ByteArrayInputStream(bytes), imageResourceIDs, maxBlocksToRead, bytes.length);
    }
    
    private boolean keepImageResourceBlock(final int ID, final int[] imageResourceIDs) {
        if (imageResourceIDs == null) {
            return true;
        }
        for (final int imageResourceID : imageResourceIDs) {
            if (ID == imageResourceID) {
                return true;
            }
        }
        return false;
    }
    
    private List<ImageResourceBlock> readImageResourceBlocks(final InputStream is, final int[] imageResourceIDs, final int maxBlocksToRead, int available) throws ImageReadException, IOException {
        final List<ImageResourceBlock> result = new ArrayList<ImageResourceBlock>();
        while (available > 0) {
            BinaryFunctions.readAndVerifyBytes(is, new byte[] { 56, 66, 73, 77 }, "Not a Valid PSD File");
            available -= 4;
            final int id = BinaryFunctions.read2Bytes("ID", is, "Not a Valid PSD File", this.getByteOrder());
            available -= 2;
            final int nameLength = BinaryFunctions.readByte("NameLength", is, "Not a Valid PSD File");
            --available;
            final byte[] nameBytes = BinaryFunctions.readBytes("NameData", is, nameLength, "Not a Valid PSD File");
            available -= nameLength;
            if ((nameLength + 1) % 2 != 0) {
                BinaryFunctions.readByte("NameDiscard", is, "Not a Valid PSD File");
                --available;
            }
            final int dataSize = BinaryFunctions.read4Bytes("Size", is, "Not a Valid PSD File", this.getByteOrder());
            available -= 4;
            final byte[] data = BinaryFunctions.readBytes("Data", is, dataSize, "Not a Valid PSD File");
            available -= dataSize;
            if (dataSize % 2 != 0) {
                BinaryFunctions.readByte("DataDiscard", is, "Not a Valid PSD File");
                --available;
            }
            if (this.keepImageResourceBlock(id, imageResourceIDs)) {
                result.add(new ImageResourceBlock(id, nameBytes, data));
                if (maxBlocksToRead >= 0 && result.size() >= maxBlocksToRead) {
                    return result;
                }
                continue;
            }
        }
        return result;
    }
    
    private List<ImageResourceBlock> readImageResourceBlocks(final ByteSource byteSource, final int[] imageResourceIDs, final int maxBlocksToRead) throws ImageReadException, IOException {
        try (final InputStream imageStream = byteSource.getInputStream();
             final InputStream resourceStream = this.getInputStream(byteSource, 2)) {
            final PsdImageContents imageContents = this.readImageContents(imageStream);
            final byte[] ImageResources = BinaryFunctions.readBytes("ImageResources", resourceStream, imageContents.ImageResourcesLength, "Not a Valid PSD File");
            final List<ImageResourceBlock> ret = this.readImageResourceBlocks(ImageResources, imageResourceIDs, maxBlocksToRead);
            return ret;
        }
    }
    
    private InputStream getInputStream(final ByteSource byteSource, final int section) throws ImageReadException, IOException {
        InputStream is = null;
        boolean notFound = false;
        try {
            is = byteSource.getInputStream();
            if (section == 0) {
                return is;
            }
            BinaryFunctions.skipBytes(is, 26L);
            final int colorModeDataLength = BinaryFunctions.read4Bytes("ColorModeDataLength", is, "Not a Valid PSD File", this.getByteOrder());
            if (section == 1) {
                return is;
            }
            BinaryFunctions.skipBytes(is, colorModeDataLength);
            final int imageResourcesLength = BinaryFunctions.read4Bytes("ImageResourcesLength", is, "Not a Valid PSD File", this.getByteOrder());
            if (section == 2) {
                return is;
            }
            BinaryFunctions.skipBytes(is, imageResourcesLength);
            final int layerAndMaskDataLength = BinaryFunctions.read4Bytes("LayerAndMaskDataLength", is, "Not a Valid PSD File", this.getByteOrder());
            if (section == 3) {
                return is;
            }
            BinaryFunctions.skipBytes(is, layerAndMaskDataLength);
            BinaryFunctions.read2Bytes("Compression", is, "Not a Valid PSD File", this.getByteOrder());
            if (section == 4) {
                return is;
            }
            notFound = true;
        }
        finally {
            if (notFound && is != null) {
                is.close();
            }
        }
        throw new ImageReadException("getInputStream: Unknown Section: " + section);
    }
    
    private byte[] getData(final ByteSource byteSource, final int section) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            if (section == 0) {
                return BinaryFunctions.readBytes("Header", is, 26, "Not a Valid PSD File");
            }
            BinaryFunctions.skipBytes(is, 26L);
            final int ColorModeDataLength = BinaryFunctions.read4Bytes("ColorModeDataLength", is, "Not a Valid PSD File", this.getByteOrder());
            if (section == 1) {
                return BinaryFunctions.readBytes("ColorModeData", is, ColorModeDataLength, "Not a Valid PSD File");
            }
            BinaryFunctions.skipBytes(is, ColorModeDataLength);
            final int ImageResourcesLength = BinaryFunctions.read4Bytes("ImageResourcesLength", is, "Not a Valid PSD File", this.getByteOrder());
            if (section == 2) {
                return BinaryFunctions.readBytes("ImageResources", is, ImageResourcesLength, "Not a Valid PSD File");
            }
            BinaryFunctions.skipBytes(is, ImageResourcesLength);
            final int LayerAndMaskDataLength = BinaryFunctions.read4Bytes("LayerAndMaskDataLength", is, "Not a Valid PSD File", this.getByteOrder());
            if (section == 3) {
                return BinaryFunctions.readBytes("LayerAndMaskData", is, LayerAndMaskDataLength, "Not a Valid PSD File");
            }
            BinaryFunctions.skipBytes(is, LayerAndMaskDataLength);
            BinaryFunctions.read2Bytes("Compression", is, "Not a Valid PSD File", this.getByteOrder());
        }
        throw new ImageReadException("getInputStream: Unknown Section: " + section);
    }
    
    private PsdImageContents readImageContents(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            return this.readImageContents(is);
        }
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<ImageResourceBlock> blocks = this.readImageResourceBlocks(byteSource, new int[] { 1039 }, 1);
        if (blocks == null || blocks.size() < 1) {
            return null;
        }
        final ImageResourceBlock irb = blocks.get(0);
        final byte[] bytes = irb.data;
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        return bytes;
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final PsdHeaderInfo bhi = this.readHeader(byteSource);
        if (bhi == null) {
            throw new ImageReadException("PSD: couldn't read header");
        }
        return new Dimension(bhi.columns, bhi.rows);
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private int getChannelsPerMode(final int mode) {
        switch (mode) {
            case 0: {
                return 1;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return -1;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 4;
            }
            case 7: {
                return -1;
            }
            case 8: {
                return -1;
            }
            case 9: {
                return 4;
            }
            default: {
                return -1;
            }
        }
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final PsdImageContents imageContents = this.readImageContents(byteSource);
        if (imageContents == null) {
            throw new ImageReadException("PSD: Couldn't read blocks");
        }
        final PsdHeaderInfo header = imageContents.header;
        if (header == null) {
            throw new ImageReadException("PSD: Couldn't read Header");
        }
        final int width = header.columns;
        final int height = header.rows;
        final List<String> comments = new ArrayList<String>();
        int BitsPerPixel = header.depth * this.getChannelsPerMode(header.mode);
        if (BitsPerPixel < 0) {
            BitsPerPixel = 0;
        }
        final ImageFormat format = ImageFormats.PSD;
        final String formatName = "Photoshop";
        final String mimeType = "image/x-photoshop";
        final int numberOfImages = -1;
        final boolean progressive = false;
        final int physicalWidthDpi = 72;
        final float physicalWidthInch = (float)(width / 72.0);
        final int physicalHeightDpi = 72;
        final float physicalHeightInch = (float)(height / 72.0);
        final String formatDetails = "Psd";
        final boolean transparent = false;
        final boolean usesPalette = header.mode == 2;
        final ImageInfo.ColorType colorType = ImageInfo.ColorType.UNKNOWN;
        ImageInfo.CompressionAlgorithm compressionAlgorithm = null;
        switch (imageContents.Compression) {
            case 0: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.NONE;
                break;
            }
            case 1: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.PSD;
                break;
            }
            default: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.UNKNOWN;
                break;
            }
        }
        return new ImageInfo("Psd", BitsPerPixel, comments, format, "Photoshop", height, "image/x-photoshop", -1, 72, physicalHeightInch, 72, physicalWidthInch, width, false, false, usesPalette, colorType, compressionAlgorithm);
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        pw.println("gif.dumpImageFile");
        final ImageInfo fImageData = this.getImageInfo(byteSource);
        if (fImageData == null) {
            return false;
        }
        fImageData.toString(pw, "");
        final PsdImageContents imageContents = this.readImageContents(byteSource);
        imageContents.dump(pw);
        imageContents.header.dump(pw);
        final List<ImageResourceBlock> blocks = this.readImageResourceBlocks(byteSource, null, -1);
        pw.println("blocks.size(): " + blocks.size());
        for (int i = 0; i < blocks.size(); ++i) {
            final ImageResourceBlock block = blocks.get(i);
            pw.println("\t" + i + " (" + Integer.toHexString(block.id) + ", '" + new String(block.nameData, StandardCharsets.ISO_8859_1) + "' (" + block.nameData.length + "),  data: " + block.data.length + " type: '" + ImageResourceType.getDescription(block.id) + "' )");
        }
        pw.println("");
        return true;
    }
    
    @Override
    public BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final PsdImageContents imageContents = this.readImageContents(byteSource);
        if (imageContents == null) {
            throw new ImageReadException("PSD: Couldn't read blocks");
        }
        final PsdHeaderInfo header = imageContents.header;
        if (header == null) {
            throw new ImageReadException("PSD: Couldn't read Header");
        }
        this.readImageResourceBlocks(byteSource, null, -1);
        final int width = header.columns;
        final int height = header.rows;
        final boolean hasAlpha = false;
        final BufferedImage result = this.getBufferedImageFactory(params).getColorBufferedImage(width, height, false);
        DataParser dataParser = null;
        switch (imageContents.header.mode) {
            case 0: {
                dataParser = new DataParserBitmap();
                break;
            }
            case 1:
            case 8: {
                dataParser = new DataParserGrayscale();
                break;
            }
            case 3: {
                dataParser = new DataParserRgb();
                break;
            }
            case 4: {
                dataParser = new DataParserCmyk();
                break;
            }
            case 9: {
                dataParser = new DataParserLab();
                break;
            }
            case 2: {
                final byte[] ColorModeData = this.getData(byteSource, 1);
                dataParser = new DataParserIndexed(ColorModeData);
                break;
            }
            default: {
                throw new ImageReadException("Unknown Mode: " + imageContents.header.mode);
            }
        }
        DataReader fDataReader = null;
        switch (imageContents.Compression) {
            case 0: {
                fDataReader = new UncompressedDataReader(dataParser);
                break;
            }
            case 1: {
                fDataReader = new CompressedDataReader(dataParser);
                break;
            }
            default: {
                throw new ImageReadException("Unknown Compression: " + imageContents.Compression);
            }
        }
        try (final InputStream is = this.getInputStream(byteSource, 4)) {
            fDataReader.readData(is, result, imageContents, this);
        }
        return result;
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final PsdImageContents imageContents = this.readImageContents(byteSource);
        if (imageContents == null) {
            throw new ImageReadException("PSD: Couldn't read blocks");
        }
        final PsdHeaderInfo header = imageContents.header;
        if (header == null) {
            throw new ImageReadException("PSD: Couldn't read Header");
        }
        final List<ImageResourceBlock> blocks = this.readImageResourceBlocks(byteSource, new int[] { 1060 }, -1);
        if (blocks == null || blocks.size() < 1) {
            return null;
        }
        final List<ImageResourceBlock> xmpBlocks = new ArrayList<ImageResourceBlock>();
        xmpBlocks.addAll(blocks);
        if (xmpBlocks.size() < 1) {
            return null;
        }
        if (xmpBlocks.size() > 1) {
            throw new ImageReadException("PSD contains more than one XMP block.");
        }
        final ImageResourceBlock block = xmpBlocks.get(0);
        return new String(block.data, 0, block.data.length, StandardCharsets.UTF_8);
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".psd" };
    }
}
