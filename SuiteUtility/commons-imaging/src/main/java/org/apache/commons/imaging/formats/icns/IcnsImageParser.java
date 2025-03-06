// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.icns;

import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.ImageWriteException;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import org.apache.commons.imaging.ImageInfo;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageMetadata;
import java.util.Map;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageParser;

public class IcnsImageParser extends ImageParser
{
    static final int ICNS_MAGIC;
    private static final String DEFAULT_EXTENSION = ".icns";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    public IcnsImageParser() {
        super.setByteOrder(ByteOrder.BIG_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "Apple Icon Image";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".icns";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return IcnsImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.ICNS };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, Map<String, Object> params) throws ImageReadException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageReadException("Unknown parameter: " + firstKey);
        }
        final IcnsContents contents = this.readImage(byteSource);
        final List<BufferedImage> images = IcnsDecoder.decodeAllImages(contents.icnsElements);
        if (images.isEmpty()) {
            throw new ImageReadException("No icons in ICNS file");
        }
        final BufferedImage image0 = images.get(0);
        return new ImageInfo("Icns", 32, new ArrayList<String>(), ImageFormats.ICNS, "ICNS Apple Icon Image", image0.getHeight(), "image/x-icns", images.size(), 0, 0.0f, 0, 0.0f, image0.getWidth(), false, true, false, ImageInfo.ColorType.RGB, ImageInfo.CompressionAlgorithm.UNKNOWN);
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, Map<String, Object> params) throws ImageReadException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageReadException("Unknown parameter: " + firstKey);
        }
        final IcnsContents contents = this.readImage(byteSource);
        final List<BufferedImage> images = IcnsDecoder.decodeAllImages(contents.icnsElements);
        if (images.isEmpty()) {
            throw new ImageReadException("No icons in ICNS file");
        }
        final BufferedImage image0 = images.get(0);
        return new Dimension(image0.getWidth(), image0.getHeight());
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private IcnsHeader readIcnsHeader(final InputStream is) throws ImageReadException, IOException {
        final int magic = BinaryFunctions.read4Bytes("Magic", is, "Not a Valid ICNS File", this.getByteOrder());
        final int fileSize = BinaryFunctions.read4Bytes("FileSize", is, "Not a Valid ICNS File", this.getByteOrder());
        if (magic != IcnsImageParser.ICNS_MAGIC) {
            throw new ImageReadException("Not a Valid ICNS File: magic is 0x" + Integer.toHexString(magic));
        }
        return new IcnsHeader(magic, fileSize);
    }
    
    private IcnsElement readIcnsElement(final InputStream is) throws IOException {
        final int type = BinaryFunctions.read4Bytes("Type", is, "Not a Valid ICNS File", this.getByteOrder());
        final int elementSize = BinaryFunctions.read4Bytes("ElementSize", is, "Not a Valid ICNS File", this.getByteOrder());
        final byte[] data = BinaryFunctions.readBytes("Data", is, elementSize - 8, "Not a Valid ICNS File");
        return new IcnsElement(type, elementSize, data);
    }
    
    private IcnsContents readImage(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final IcnsHeader icnsHeader = this.readIcnsHeader(is);
            final List<IcnsElement> icnsElementList = new ArrayList<IcnsElement>();
            IcnsElement icnsElement;
            for (int remainingSize = icnsHeader.fileSize - 8; remainingSize > 0; remainingSize -= icnsElement.elementSize) {
                icnsElement = this.readIcnsElement(is);
                icnsElementList.add(icnsElement);
            }
            final IcnsElement[] icnsElements = new IcnsElement[icnsElementList.size()];
            for (int i = 0; i < icnsElements.length; ++i) {
                icnsElements[i] = icnsElementList.get(i);
            }
            final IcnsContents ret = new IcnsContents(icnsHeader, icnsElements);
            return ret;
        }
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        final IcnsContents icnsContents = this.readImage(byteSource);
        icnsContents.icnsHeader.dump(pw);
        for (final IcnsElement icnsElement : icnsContents.icnsElements) {
            icnsElement.dump(pw);
        }
        return true;
    }
    
    @Override
    public final BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final IcnsContents icnsContents = this.readImage(byteSource);
        final List<BufferedImage> result = IcnsDecoder.decodeAllImages(icnsContents.icnsElements);
        if (!result.isEmpty()) {
            return result.get(0);
        }
        throw new ImageReadException("No icons in ICNS file");
    }
    
    @Override
    public List<BufferedImage> getAllBufferedImages(final ByteSource byteSource) throws ImageReadException, IOException {
        final IcnsContents icnsContents = this.readImage(byteSource);
        return IcnsDecoder.decodeAllImages(icnsContents.icnsElements);
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        IcnsType imageType;
        if (src.getWidth() == 16 && src.getHeight() == 16) {
            imageType = IcnsType.ICNS_16x16_32BIT_IMAGE;
        }
        else if (src.getWidth() == 32 && src.getHeight() == 32) {
            imageType = IcnsType.ICNS_32x32_32BIT_IMAGE;
        }
        else if (src.getWidth() == 48 && src.getHeight() == 48) {
            imageType = IcnsType.ICNS_48x48_32BIT_IMAGE;
        }
        else {
            if (src.getWidth() != 128 || src.getHeight() != 128) {
                throw new ImageWriteException("Invalid/unsupported source width " + src.getWidth() + " and height " + src.getHeight());
            }
            imageType = IcnsType.ICNS_128x128_32BIT_IMAGE;
        }
        try (final BinaryOutputStream bos = new BinaryOutputStream(os, ByteOrder.BIG_ENDIAN)) {
            bos.write4Bytes(IcnsImageParser.ICNS_MAGIC);
            bos.write4Bytes(16 + 4 * imageType.getWidth() * imageType.getHeight() + 4 + 4 + imageType.getWidth() * imageType.getHeight());
            bos.write4Bytes(imageType.getType());
            bos.write4Bytes(8 + 4 * imageType.getWidth() * imageType.getHeight());
            for (int y = 0; y < src.getHeight(); ++y) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    bos.write(0);
                    bos.write(argb >> 16);
                    bos.write(argb >> 8);
                    bos.write(argb);
                }
            }
            final IcnsType maskType = IcnsType.find8BPPMaskType(imageType);
            bos.write4Bytes(maskType.getType());
            bos.write4Bytes(8 + imageType.getWidth() * imageType.getWidth());
            for (int y2 = 0; y2 < src.getHeight(); ++y2) {
                for (int x2 = 0; x2 < src.getWidth(); ++x2) {
                    final int argb2 = src.getRGB(x2, y2);
                    bos.write(argb2 >> 24);
                }
            }
        }
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ICNS_MAGIC = IcnsType.typeAsInt("icns");
        ACCEPTED_EXTENSIONS = new String[] { ".icns" };
    }
    
    private static class IcnsHeader
    {
        public final int magic;
        public final int fileSize;
        
        IcnsHeader(final int magic, final int fileSize) {
            this.magic = magic;
            this.fileSize = fileSize;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("IcnsHeader");
            pw.println("Magic: 0x" + Integer.toHexString(this.magic) + " (" + IcnsType.describeType(this.magic) + ")");
            pw.println("FileSize: " + this.fileSize);
            pw.println("");
        }
    }
    
    static class IcnsElement
    {
        public final int type;
        public final int elementSize;
        public final byte[] data;
        
        IcnsElement(final int type, final int elementSize, final byte[] data) {
            this.type = type;
            this.elementSize = elementSize;
            this.data = data;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("IcnsElement");
            final IcnsType icnsType = IcnsType.findAnyType(this.type);
            String typeDescription;
            if (icnsType == null) {
                typeDescription = "";
            }
            else {
                typeDescription = " " + icnsType.toString();
            }
            pw.println("Type: 0x" + Integer.toHexString(this.type) + " (" + IcnsType.describeType(this.type) + ")" + typeDescription);
            pw.println("ElementSize: " + this.elementSize);
            pw.println("");
        }
    }
    
    private static class IcnsContents
    {
        public final IcnsHeader icnsHeader;
        public final IcnsElement[] icnsElements;
        
        IcnsContents(final IcnsHeader icnsHeader, final IcnsElement[] icnsElements) {
            this.icnsHeader = icnsHeader;
            this.icnsElements = icnsElements;
        }
    }
}
