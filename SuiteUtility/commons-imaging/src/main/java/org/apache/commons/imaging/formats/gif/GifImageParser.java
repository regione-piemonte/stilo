// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.gif;

import org.apache.commons.imaging.palette.Palette;
import org.apache.commons.imaging.common.mylzw.MyLzwCompressor;
import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.palette.PaletteFactory;
import org.apache.commons.imaging.ImageWriteException;
import java.util.HashMap;
import java.io.OutputStream;
import org.apache.commons.imaging.common.ImageBuilder;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.common.ImageMetadata;
import java.awt.Dimension;
import java.util.Map;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.util.Iterator;
import org.apache.commons.imaging.common.mylzw.MyLzwDecompressor;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.util.logging.Level;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.FormatCompliance;
import java.io.InputStream;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.nio.ByteOrder;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageParser;

public class GifImageParser extends ImageParser
{
    private static final Logger LOGGER;
    private static final String DEFAULT_EXTENSION = ".gif";
    private static final String[] ACCEPTED_EXTENSIONS;
    private static final byte[] GIF_HEADER_SIGNATURE;
    private static final int EXTENSION_CODE = 33;
    private static final int IMAGE_SEPARATOR = 44;
    private static final int GRAPHIC_CONTROL_EXTENSION = 8697;
    private static final int COMMENT_EXTENSION = 254;
    private static final int PLAIN_TEXT_EXTENSION = 1;
    private static final int XMP_EXTENSION = 255;
    private static final int TERMINATOR_BYTE = 59;
    private static final int APPLICATION_EXTENSION_LABEL = 255;
    private static final int XMP_COMPLETE_CODE = 8703;
    private static final int LOCAL_COLOR_TABLE_FLAG_MASK = 128;
    private static final int INTERLACE_FLAG_MASK = 64;
    private static final int SORT_FLAG_MASK = 32;
    private static final byte[] XMP_APPLICATION_ID_AND_AUTH_CODE;
    
    public GifImageParser() {
        super.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "Graphics Interchange Format";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".gif";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return GifImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.GIF };
    }
    
    private GifHeaderInfo readHeader(final InputStream is, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final byte identifier1 = BinaryFunctions.readByte("identifier1", is, "Not a Valid GIF File");
        final byte identifier2 = BinaryFunctions.readByte("identifier2", is, "Not a Valid GIF File");
        final byte identifier3 = BinaryFunctions.readByte("identifier3", is, "Not a Valid GIF File");
        final byte version1 = BinaryFunctions.readByte("version1", is, "Not a Valid GIF File");
        final byte version2 = BinaryFunctions.readByte("version2", is, "Not a Valid GIF File");
        final byte version3 = BinaryFunctions.readByte("version3", is, "Not a Valid GIF File");
        if (formatCompliance != null) {
            formatCompliance.compareBytes("Signature", GifImageParser.GIF_HEADER_SIGNATURE, new byte[] { identifier1, identifier2, identifier3 });
            formatCompliance.compare("version", 56, version1);
            formatCompliance.compare("version", new int[] { 55, 57 }, version2);
            formatCompliance.compare("version", 97, version3);
        }
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            BinaryFunctions.printCharQuad("identifier: ", identifier1 << 16 | identifier2 << 8 | identifier3 << 0);
            BinaryFunctions.printCharQuad("version: ", version1 << 16 | version2 << 8 | version3 << 0);
        }
        final int logicalScreenWidth = BinaryFunctions.read2Bytes("Logical Screen Width", is, "Not a Valid GIF File", this.getByteOrder());
        final int logicalScreenHeight = BinaryFunctions.read2Bytes("Logical Screen Height", is, "Not a Valid GIF File", this.getByteOrder());
        if (formatCompliance != null) {
            formatCompliance.checkBounds("Width", 1, Integer.MAX_VALUE, logicalScreenWidth);
            formatCompliance.checkBounds("Height", 1, Integer.MAX_VALUE, logicalScreenHeight);
        }
        final byte packedFields = BinaryFunctions.readByte("Packed Fields", is, "Not a Valid GIF File");
        final byte backgroundColorIndex = BinaryFunctions.readByte("Background Color Index", is, "Not a Valid GIF File");
        final byte pixelAspectRatio = BinaryFunctions.readByte("Pixel Aspect Ratio", is, "Not a Valid GIF File");
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            BinaryFunctions.printByteBits("PackedFields bits", packedFields);
        }
        final boolean globalColorTableFlag = (packedFields & 0x80) > 0;
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("GlobalColorTableFlag: " + globalColorTableFlag);
        }
        final byte colorResolution = (byte)(packedFields >> 4 & 0x7);
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("ColorResolution: " + colorResolution);
        }
        final boolean sortFlag = (packedFields & 0x8) > 0;
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("SortFlag: " + sortFlag);
        }
        final byte sizeofGlobalColorTable = (byte)(packedFields & 0x7);
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("SizeofGlobalColorTable: " + sizeofGlobalColorTable);
        }
        if (formatCompliance != null && globalColorTableFlag && backgroundColorIndex != -1) {
            formatCompliance.checkBounds("Background Color Index", 0, this.convertColorTableSize(sizeofGlobalColorTable), backgroundColorIndex);
        }
        return new GifHeaderInfo(identifier1, identifier2, identifier3, version1, version2, version3, logicalScreenWidth, logicalScreenHeight, packedFields, backgroundColorIndex, pixelAspectRatio, globalColorTableFlag, colorResolution, sortFlag, sizeofGlobalColorTable);
    }
    
    private GraphicControlExtension readGraphicControlExtension(final int code, final InputStream is) throws IOException {
        BinaryFunctions.readByte("block_size", is, "GIF: corrupt GraphicControlExt");
        final int packed = BinaryFunctions.readByte("packed fields", is, "GIF: corrupt GraphicControlExt");
        final int dispose = (packed & 0x1C) >> 2;
        final boolean transparency = (packed & 0x1) != 0x0;
        final int delay = BinaryFunctions.read2Bytes("delay in milliseconds", is, "GIF: corrupt GraphicControlExt", this.getByteOrder());
        final int transparentColorIndex = 0xFF & BinaryFunctions.readByte("transparent color index", is, "GIF: corrupt GraphicControlExt");
        BinaryFunctions.readByte("block terminator", is, "GIF: corrupt GraphicControlExt");
        return new GraphicControlExtension(code, packed, dispose, transparency, delay, transparentColorIndex);
    }
    
    private byte[] readSubBlock(final InputStream is) throws IOException {
        final int blockSize = 0xFF & BinaryFunctions.readByte("block_size", is, "GIF: corrupt block");
        return BinaryFunctions.readBytes("block", is, blockSize, "GIF: corrupt block");
    }
    
    private GenericGifBlock readGenericGIFBlock(final InputStream is, final int code) throws IOException {
        return this.readGenericGIFBlock(is, code, null);
    }
    
    private GenericGifBlock readGenericGIFBlock(final InputStream is, final int code, final byte[] first) throws IOException {
        final List<byte[]> subblocks = new ArrayList<byte[]>();
        if (first != null) {
            subblocks.add(first);
        }
        while (true) {
            final byte[] bytes = this.readSubBlock(is);
            if (bytes.length < 1) {
                break;
            }
            subblocks.add(bytes);
        }
        return new GenericGifBlock(code, subblocks);
    }
    
    private List<GifBlock> readBlocks(final GifHeaderInfo ghi, final InputStream is, final boolean stopBeforeImageData, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final List<GifBlock> result = new ArrayList<GifBlock>();
        while (true) {
            final int code = is.read();
            switch (code) {
                case -1: {
                    throw new ImageReadException("GIF: unexpected end of data");
                }
                case 44: {
                    final ImageDescriptor id = this.readImageDescriptor(ghi, code, is, stopBeforeImageData, formatCompliance);
                    result.add(id);
                    continue;
                }
                case 33: {
                    final int extensionCode = is.read();
                    final int completeCode = (0xFF & code) << 8 | (0xFF & extensionCode);
                    switch (extensionCode) {
                        case 249: {
                            final GraphicControlExtension gce = this.readGraphicControlExtension(completeCode, is);
                            result.add(gce);
                            continue;
                        }
                        case 1:
                        case 254: {
                            final GenericGifBlock block = this.readGenericGIFBlock(is, completeCode);
                            result.add(block);
                            continue;
                        }
                        case 255: {
                            final byte[] label = this.readSubBlock(is);
                            if (formatCompliance != null) {
                                formatCompliance.addComment("Unknown Application Extension (" + new String(label, StandardCharsets.US_ASCII) + ")", completeCode);
                            }
                            if (label != null && label.length > 0) {
                                final GenericGifBlock block2 = this.readGenericGIFBlock(is, completeCode, label);
                                result.add(block2);
                                continue;
                            }
                            continue;
                        }
                        default: {
                            if (formatCompliance != null) {
                                formatCompliance.addComment("Unknown block", completeCode);
                            }
                            final GenericGifBlock block = this.readGenericGIFBlock(is, completeCode);
                            result.add(block);
                            continue;
                        }
                    }
                    continue;
                }
                case 59: {
                    return result;
                }
                case 0: {
                    continue;
                }
                default: {
                    throw new ImageReadException("GIF: unknown code: " + code);
                }
            }
        }
    }
    
    private ImageDescriptor readImageDescriptor(final GifHeaderInfo ghi, final int blockCode, final InputStream is, final boolean stopBeforeImageData, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final int imageLeftPosition = BinaryFunctions.read2Bytes("Image Left Position", is, "Not a Valid GIF File", this.getByteOrder());
        final int imageTopPosition = BinaryFunctions.read2Bytes("Image Top Position", is, "Not a Valid GIF File", this.getByteOrder());
        final int imageWidth = BinaryFunctions.read2Bytes("Image Width", is, "Not a Valid GIF File", this.getByteOrder());
        final int imageHeight = BinaryFunctions.read2Bytes("Image Height", is, "Not a Valid GIF File", this.getByteOrder());
        final byte packedFields = BinaryFunctions.readByte("Packed Fields", is, "Not a Valid GIF File");
        if (formatCompliance != null) {
            formatCompliance.checkBounds("Width", 1, ghi.logicalScreenWidth, imageWidth);
            formatCompliance.checkBounds("Height", 1, ghi.logicalScreenHeight, imageHeight);
            formatCompliance.checkBounds("Left Position", 0, ghi.logicalScreenWidth - imageWidth, imageLeftPosition);
            formatCompliance.checkBounds("Top Position", 0, ghi.logicalScreenHeight - imageHeight, imageTopPosition);
        }
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            BinaryFunctions.printByteBits("PackedFields bits", packedFields);
        }
        final boolean localColorTableFlag = (packedFields >> 7 & 0x1) > 0;
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("LocalColorTableFlag: " + localColorTableFlag);
        }
        final boolean interlaceFlag = (packedFields >> 6 & 0x1) > 0;
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("Interlace Flag: " + interlaceFlag);
        }
        final boolean sortFlag = (packedFields >> 5 & 0x1) > 0;
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("Sort Flag: " + sortFlag);
        }
        final byte sizeOfLocalColorTable = (byte)(packedFields & 0x7);
        if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
            GifImageParser.LOGGER.finest("SizeofLocalColorTable: " + sizeOfLocalColorTable);
        }
        byte[] localColorTable = null;
        if (localColorTableFlag) {
            localColorTable = this.readColorTable(is, sizeOfLocalColorTable);
        }
        byte[] imageData = null;
        if (!stopBeforeImageData) {
            final int lzwMinimumCodeSize = is.read();
            final GenericGifBlock block = this.readGenericGIFBlock(is, -1);
            final byte[] bytes = block.appendSubBlocks();
            final InputStream bais = new ByteArrayInputStream(bytes);
            final int size = imageWidth * imageHeight;
            final MyLzwDecompressor myLzwDecompressor = new MyLzwDecompressor(lzwMinimumCodeSize, ByteOrder.LITTLE_ENDIAN);
            imageData = myLzwDecompressor.decompress(bais, size);
        }
        else {
            final int LZWMinimumCodeSize = is.read();
            if (GifImageParser.LOGGER.isLoggable(Level.FINEST)) {
                GifImageParser.LOGGER.finest("LZWMinimumCodeSize: " + LZWMinimumCodeSize);
            }
            this.readGenericGIFBlock(is, -1);
        }
        return new ImageDescriptor(blockCode, imageLeftPosition, imageTopPosition, imageWidth, imageHeight, packedFields, localColorTableFlag, interlaceFlag, sortFlag, sizeOfLocalColorTable, localColorTable, imageData);
    }
    
    private int simplePow(final int base, final int power) {
        int result = 1;
        for (int i = 0; i < power; ++i) {
            result *= base;
        }
        return result;
    }
    
    private int convertColorTableSize(final int tableSize) {
        return 3 * this.simplePow(2, tableSize + 1);
    }
    
    private byte[] readColorTable(final InputStream is, final int tableSize) throws IOException {
        final int actualSize = this.convertColorTableSize(tableSize);
        return BinaryFunctions.readBytes("block", is, actualSize, "GIF: corrupt Color Table");
    }
    
    private GifBlock findBlock(final List<GifBlock> blocks, final int code) {
        for (final GifBlock gifBlock : blocks) {
            if (gifBlock.blockCode == code) {
                return gifBlock;
            }
        }
        return null;
    }
    
    private GifImageContents readFile(final ByteSource byteSource, final boolean stopBeforeImageData) throws ImageReadException, IOException {
        return this.readFile(byteSource, stopBeforeImageData, FormatCompliance.getDefault());
    }
    
    private GifImageContents readFile(final ByteSource byteSource, final boolean stopBeforeImageData, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final GifHeaderInfo ghi = this.readHeader(is, formatCompliance);
            byte[] globalColorTable = null;
            if (ghi.globalColorTableFlag) {
                globalColorTable = this.readColorTable(is, ghi.sizeOfGlobalColorTable);
            }
            final List<GifBlock> blocks = this.readBlocks(ghi, is, stopBeforeImageData, formatCompliance);
            final GifImageContents result = new GifImageContents(ghi, globalColorTable, blocks);
            return result;
        }
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final GifImageContents blocks = this.readFile(byteSource, false);
        if (blocks == null) {
            throw new ImageReadException("GIF: Couldn't read blocks");
        }
        final GifHeaderInfo bhi = blocks.gifHeaderInfo;
        if (bhi == null) {
            throw new ImageReadException("GIF: Couldn't read Header");
        }
        final ImageDescriptor id = (ImageDescriptor)this.findBlock(blocks.blocks, 44);
        if (id == null) {
            throw new ImageReadException("GIF: Couldn't read ImageDescriptor");
        }
        return new Dimension(id.imageWidth, id.imageHeight);
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private List<String> getComments(final List<GifBlock> blocks) throws IOException {
        final List<String> result = new ArrayList<String>();
        final int code = 8702;
        for (final GifBlock block : blocks) {
            if (block.blockCode == 8702) {
                final byte[] bytes = ((GenericGifBlock)block).appendSubBlocks();
                result.add(new String(bytes, StandardCharsets.US_ASCII));
            }
        }
        return result;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final GifImageContents blocks = this.readFile(byteSource, false);
        if (blocks == null) {
            throw new ImageReadException("GIF: Couldn't read blocks");
        }
        final GifHeaderInfo bhi = blocks.gifHeaderInfo;
        if (bhi == null) {
            throw new ImageReadException("GIF: Couldn't read Header");
        }
        final ImageDescriptor id = (ImageDescriptor)this.findBlock(blocks.blocks, 44);
        if (id == null) {
            throw new ImageReadException("GIF: Couldn't read ImageDescriptor");
        }
        final GraphicControlExtension gce = (GraphicControlExtension)this.findBlock(blocks.blocks, 8697);
        final int height = id.imageHeight;
        final int width = id.imageWidth;
        final List<String> comments = this.getComments(blocks.blocks);
        final int bitsPerPixel = bhi.colorResolution + 1;
        final ImageFormat format = ImageFormats.GIF;
        final String formatName = "GIF Graphics Interchange Format";
        final String mimeType = "image/gif";
        final int numberOfImages = -1;
        final boolean progressive = id.interlaceFlag;
        final int physicalWidthDpi = 72;
        final float physicalWidthInch = (float)(width / 72.0);
        final int physicalHeightDpi = 72;
        final float physicalHeightInch = (float)(height / 72.0);
        final String formatDetails = "Gif " + (char)blocks.gifHeaderInfo.version1 + (char)blocks.gifHeaderInfo.version2 + (char)blocks.gifHeaderInfo.version3;
        boolean transparent = false;
        if (gce != null && gce.transparency) {
            transparent = true;
        }
        final boolean usesPalette = true;
        final ImageInfo.ColorType colorType = ImageInfo.ColorType.RGB;
        final ImageInfo.CompressionAlgorithm compressionAlgorithm = ImageInfo.CompressionAlgorithm.LZW;
        return new ImageInfo(formatDetails, bitsPerPixel, comments, format, "GIF Graphics Interchange Format", height, "image/gif", -1, 72, physicalHeightInch, 72, physicalWidthInch, width, progressive, transparent, true, colorType, compressionAlgorithm);
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        pw.println("gif.dumpImageFile");
        final ImageInfo imageData = this.getImageInfo(byteSource);
        if (imageData == null) {
            return false;
        }
        imageData.toString(pw, "");
        final GifImageContents blocks = this.readFile(byteSource, false);
        pw.println("gif.blocks: " + blocks.blocks.size());
        for (int i = 0; i < blocks.blocks.size(); ++i) {
            final GifBlock gifBlock = blocks.blocks.get(i);
            this.debugNumber(pw, "\t" + i + " (" + gifBlock.getClass().getName() + ")", gifBlock.blockCode, 4);
        }
        pw.println("");
        return true;
    }
    
    private int[] getColorTable(final byte[] bytes) throws ImageReadException {
        if (bytes.length % 3 != 0) {
            throw new ImageReadException("Bad Color Table Length: " + bytes.length);
        }
        final int length = bytes.length / 3;
        final int[] result = new int[length];
        for (int i = 0; i < length; ++i) {
            final int red = 0xFF & bytes[i * 3 + 0];
            final int green = 0xFF & bytes[i * 3 + 1];
            final int blue = 0xFF & bytes[i * 3 + 2];
            final int alpha = 255;
            final int rgb = 0xFF000000 | red << 16 | green << 8 | blue << 0;
            result[i] = rgb;
        }
        return result;
    }
    
    @Override
    public FormatCompliance getFormatCompliance(final ByteSource byteSource) throws ImageReadException, IOException {
        final FormatCompliance result = new FormatCompliance(byteSource.getDescription());
        this.readFile(byteSource, false, result);
        return result;
    }
    
    @Override
    public BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final GifImageContents imageContents = this.readFile(byteSource, false);
        if (imageContents == null) {
            throw new ImageReadException("GIF: Couldn't read blocks");
        }
        final GifHeaderInfo ghi = imageContents.gifHeaderInfo;
        if (ghi == null) {
            throw new ImageReadException("GIF: Couldn't read Header");
        }
        final ImageDescriptor id = (ImageDescriptor)this.findBlock(imageContents.blocks, 44);
        if (id == null) {
            throw new ImageReadException("GIF: Couldn't read Image Descriptor");
        }
        final GraphicControlExtension gce = (GraphicControlExtension)this.findBlock(imageContents.blocks, 8697);
        final int width = id.imageWidth;
        final int height = id.imageHeight;
        boolean hasAlpha = false;
        if (gce != null && gce.transparency) {
            hasAlpha = true;
        }
        final ImageBuilder imageBuilder = new ImageBuilder(width, height, hasAlpha);
        int[] colorTable;
        if (id.localColorTable != null) {
            colorTable = this.getColorTable(id.localColorTable);
        }
        else {
            if (imageContents.globalColorTable == null) {
                throw new ImageReadException("Gif: No Color Table");
            }
            colorTable = this.getColorTable(imageContents.globalColorTable);
        }
        int transparentIndex = -1;
        if (gce != null && hasAlpha) {
            transparentIndex = gce.transparentColorIndex;
        }
        int counter = 0;
        final int rowsInPass1 = (height + 7) / 8;
        final int rowsInPass2 = (height + 3) / 8;
        final int rowsInPass3 = (height + 1) / 4;
        final int rowsInPass4 = height / 2;
        for (int row = 0; row < height; ++row) {
            int y;
            if (id.interlaceFlag) {
                int theRow = row;
                if (theRow < rowsInPass1) {
                    y = theRow * 8;
                }
                else {
                    theRow -= rowsInPass1;
                    if (theRow < rowsInPass2) {
                        y = 4 + theRow * 8;
                    }
                    else {
                        theRow -= rowsInPass2;
                        if (theRow < rowsInPass3) {
                            y = 2 + theRow * 4;
                        }
                        else {
                            theRow -= rowsInPass3;
                            if (theRow >= rowsInPass4) {
                                throw new ImageReadException("Gif: Strange Row");
                            }
                            y = 1 + theRow * 2;
                        }
                    }
                }
            }
            else {
                y = row;
            }
            for (int x = 0; x < width; ++x) {
                final int index = 0xFF & id.imageData[counter++];
                int rgb = colorTable[index];
                if (transparentIndex == index) {
                    rgb = 0;
                }
                imageBuilder.setRGB(x, y, rgb);
            }
        }
        return imageBuilder.getBufferedImage();
    }
    
    private void writeAsSubBlocks(final OutputStream os, final byte[] bytes) throws IOException {
        int blockSize;
        for (int index = 0; index < bytes.length; index += blockSize) {
            blockSize = Math.min(bytes.length - index, 255);
            os.write(blockSize);
            os.write(bytes, index, blockSize);
        }
        os.write(0);
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        params = new HashMap<String, Object>(params);
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        String xmpXml = null;
        if (params.containsKey("XMP_XML")) {
            xmpXml = params.get("XMP_XML");
            params.remove("XMP_XML");
        }
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        final int width = src.getWidth();
        final int height = src.getHeight();
        final boolean hasAlpha = new PaletteFactory().hasTransparency(src);
        final int maxColors = hasAlpha ? 255 : 256;
        Palette palette2 = new PaletteFactory().makeExactRgbPaletteSimple(src, maxColors);
        if (palette2 == null) {
            palette2 = new PaletteFactory().makeQuantizedRgbPalette(src, maxColors);
            if (GifImageParser.LOGGER.isLoggable(Level.FINE)) {
                GifImageParser.LOGGER.fine("quantizing");
            }
        }
        else if (GifImageParser.LOGGER.isLoggable(Level.FINE)) {
            GifImageParser.LOGGER.fine("exact palette");
        }
        if (palette2 == null) {
            throw new ImageWriteException("Gif: can't write images with more than 256 colors");
        }
        final int paletteSize = palette2.length() + (hasAlpha ? 1 : 0);
        final BinaryOutputStream bos = new BinaryOutputStream(os, ByteOrder.LITTLE_ENDIAN);
        os.write(71);
        os.write(73);
        os.write(70);
        os.write(56);
        os.write(57);
        os.write(97);
        bos.write2Bytes(width);
        bos.write2Bytes(height);
        final int colorTableScaleLessOne = (paletteSize > 128) ? 7 : ((paletteSize > 64) ? 6 : ((paletteSize > 32) ? 5 : ((paletteSize > 16) ? 4 : ((paletteSize > 8) ? 3 : ((paletteSize > 4) ? 2 : ((paletteSize > 2) ? 1 : 0))))));
        final int colorTableSizeInFormat = 1 << colorTableScaleLessOne + 1;
        final byte colorResolution = (byte)colorTableScaleLessOne;
        final int packedFields = (0x7 & colorResolution) * 16;
        bos.write(packedFields);
        final byte backgroundColorIndex = 0;
        bos.write(0);
        final byte pixelAspectRatio = 0;
        bos.write(0);
        bos.write(33);
        bos.write(-7);
        bos.write(4);
        final int packedFields2 = hasAlpha ? 1 : 0;
        bos.write((byte)packedFields2);
        bos.write(0);
        bos.write(0);
        bos.write((byte)(hasAlpha ? palette2.length() : 0));
        bos.write(0);
        if (null != xmpXml) {
            bos.write(33);
            bos.write(255);
            bos.write(GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE.length);
            bos.write(GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE);
            final byte[] xmpXmlBytes = xmpXml.getBytes(StandardCharsets.UTF_8);
            bos.write(xmpXmlBytes);
            for (int magic = 0; magic <= 255; ++magic) {
                bos.write(255 - magic);
            }
            bos.write(0);
        }
        bos.write(44);
        bos.write2Bytes(0);
        bos.write2Bytes(0);
        bos.write2Bytes(width);
        bos.write2Bytes(height);
        final boolean localColorTableFlag = true;
        final boolean interlaceFlag = false;
        final boolean sortFlag = false;
        final int sizeOfLocalColorTable = colorTableScaleLessOne;
        final int packedFields3 = 0x80 | (0x7 & sizeOfLocalColorTable);
        bos.write(packedFields3);
        for (int i = 0; i < colorTableSizeInFormat; ++i) {
            if (i < palette2.length()) {
                final int rgb = palette2.getEntry(i);
                final int red = 0xFF & rgb >> 16;
                final int green = 0xFF & rgb >> 8;
                final int blue = 0xFF & rgb >> 0;
                bos.write(red);
                bos.write(green);
                bos.write(blue);
            }
            else {
                bos.write(0);
                bos.write(0);
                bos.write(0);
            }
        }
        int lzwMinimumCodeSize = colorTableScaleLessOne + 1;
        if (lzwMinimumCodeSize < 2) {
            lzwMinimumCodeSize = 2;
        }
        bos.write(lzwMinimumCodeSize);
        final MyLzwCompressor compressor = new MyLzwCompressor(lzwMinimumCodeSize, ByteOrder.LITTLE_ENDIAN, false);
        final byte[] imagedata = new byte[width * height];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = src.getRGB(x, y);
                final int rgb2 = 0xFFFFFF & argb;
                int index;
                if (hasAlpha) {
                    final int alpha = 0xFF & argb >> 24;
                    final int alphaThreshold = 255;
                    if (alpha < 255) {
                        index = palette2.length();
                    }
                    else {
                        index = palette2.getPaletteIndex(rgb2);
                    }
                }
                else {
                    index = palette2.getPaletteIndex(rgb2);
                }
                imagedata[y * width + x] = (byte)index;
            }
        }
        final byte[] compressed = compressor.compress(imagedata);
        this.writeAsSubBlocks(bos, compressed);
        bos.write(59);
        bos.close();
        os.close();
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final FormatCompliance formatCompliance = null;
            final GifHeaderInfo ghi = this.readHeader(is, formatCompliance);
            if (ghi.globalColorTableFlag) {
                this.readColorTable(is, ghi.sizeOfGlobalColorTable);
            }
            final List<GifBlock> blocks = this.readBlocks(ghi, is, true, formatCompliance);
            final List<String> result = new ArrayList<String>();
            for (final GifBlock block : blocks) {
                if (block.blockCode != 8703) {
                    continue;
                }
                final GenericGifBlock genericBlock = (GenericGifBlock)block;
                final byte[] blockBytes = genericBlock.appendSubBlocks(true);
                if (blockBytes.length < GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE.length) {
                    continue;
                }
                if (!BinaryFunctions.compareBytes(blockBytes, 0, GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE, 0, GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE.length)) {
                    continue;
                }
                final byte[] GIF_MAGIC_TRAILER = new byte[256];
                for (int magic = 0; magic <= 255; ++magic) {
                    GIF_MAGIC_TRAILER[magic] = (byte)(255 - magic);
                }
                if (blockBytes.length < GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE.length + GIF_MAGIC_TRAILER.length) {
                    continue;
                }
                if (!BinaryFunctions.compareBytes(blockBytes, blockBytes.length - GIF_MAGIC_TRAILER.length, GIF_MAGIC_TRAILER, 0, GIF_MAGIC_TRAILER.length)) {
                    throw new ImageReadException("XMP block in GIF missing magic trailer.");
                }
                final String xml = new String(blockBytes, GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE.length, blockBytes.length - (GifImageParser.XMP_APPLICATION_ID_AND_AUTH_CODE.length + GIF_MAGIC_TRAILER.length), StandardCharsets.UTF_8);
                result.add(xml);
            }
            if (result.size() < 1) {
                return null;
            }
            if (result.size() > 1) {
                throw new ImageReadException("More than one XMP Block in GIF.");
            }
            return result.get(0);
        }
    }
    
    static {
        LOGGER = Logger.getLogger(GifImageParser.class.getName());
        ACCEPTED_EXTENSIONS = new String[] { ".gif" };
        GIF_HEADER_SIGNATURE = new byte[] { 71, 73, 70 };
        XMP_APPLICATION_ID_AND_AUTH_CODE = new byte[] { 88, 77, 80, 32, 68, 97, 116, 97, 88, 77, 80 };
    }
}
