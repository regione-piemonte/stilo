// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.wbmp;

import org.apache.commons.imaging.ImageWriteException;
import java.util.HashMap;
import java.awt.image.WritableRaster;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.util.Properties;
import java.awt.image.IndexColorModel;
import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.OutputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageInfo;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageMetadata;
import java.util.Map;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageParser;

public class WbmpImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".wbmp";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    @Override
    public String getName() {
        return "Wireless Application Protocol Bitmap Format";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".wbmp";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return WbmpImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.WBMP };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final WbmpHeader wbmpHeader = this.readWbmpHeader(byteSource);
        return new ImageInfo("WBMP", 1, new ArrayList<String>(), ImageFormats.WBMP, "Wireless Application Protocol Bitmap", wbmpHeader.height, "image/vnd.wap.wbmp", 1, 0, 0.0f, 0, 0.0f, wbmpHeader.width, false, false, false, ImageInfo.ColorType.BW, ImageInfo.CompressionAlgorithm.NONE);
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final WbmpHeader wbmpHeader = this.readWbmpHeader(byteSource);
        return new Dimension(wbmpHeader.width, wbmpHeader.height);
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private int readMultiByteInteger(final InputStream is) throws ImageReadException, IOException {
        int value = 0;
        int totalBits = 0;
        int nextByte;
        do {
            nextByte = BinaryFunctions.readByte("Header", is, "Error reading WBMP header");
            value <<= 7;
            value |= (nextByte & 0x7F);
            totalBits += 7;
            if (totalBits > 31) {
                throw new ImageReadException("Overflow reading WBMP multi-byte field");
            }
        } while ((nextByte & 0x80) != 0x0);
        return value;
    }
    
    private void writeMultiByteInteger(final OutputStream os, final int value) throws IOException {
        boolean wroteYet = false;
        for (int position = 28; position > 0; position -= 7) {
            final int next7Bits = 0x7F & value >>> position;
            if (next7Bits != 0 || wroteYet) {
                os.write(0x80 | next7Bits);
                wroteYet = true;
            }
        }
        os.write(0x7F & value);
    }
    
    private WbmpHeader readWbmpHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            return this.readWbmpHeader(is);
        }
    }
    
    private WbmpHeader readWbmpHeader(final InputStream is) throws ImageReadException, IOException {
        final int typeField = this.readMultiByteInteger(is);
        if (typeField != 0) {
            throw new ImageReadException("Invalid/unsupported WBMP type " + typeField);
        }
        final byte fixHeaderField = BinaryFunctions.readByte("FixHeaderField", is, "Invalid WBMP File");
        if ((fixHeaderField & 0x9F) != 0x0) {
            throw new ImageReadException("Invalid/unsupported WBMP FixHeaderField 0x" + Integer.toHexString(0xFF & fixHeaderField));
        }
        final int width = this.readMultiByteInteger(is);
        final int height = this.readMultiByteInteger(is);
        return new WbmpHeader(typeField, fixHeaderField, width, height);
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        this.readWbmpHeader(byteSource).dump(pw);
        return true;
    }
    
    private BufferedImage readImage(final WbmpHeader wbmpHeader, final InputStream is) throws IOException {
        final int rowLength = (wbmpHeader.width + 7) / 8;
        final byte[] image = BinaryFunctions.readBytes("Pixels", is, rowLength * wbmpHeader.height, "Error reading image pixels");
        final DataBufferByte dataBuffer = new DataBufferByte(image, image.length);
        final WritableRaster raster = Raster.createPackedRaster(dataBuffer, wbmpHeader.width, wbmpHeader.height, 1, null);
        final int[] palette = { 0, 16777215 };
        final IndexColorModel colorModel = new IndexColorModel(1, 2, palette, 0, false, -1, 0);
        return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), new Properties());
    }
    
    @Override
    public final BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final WbmpHeader wbmpHeader = this.readWbmpHeader(is);
            final BufferedImage ret = this.readImage(wbmpHeader, is);
            return ret;
        }
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
        this.writeMultiByteInteger(os, 0);
        os.write(0);
        this.writeMultiByteInteger(os, src.getWidth());
        this.writeMultiByteInteger(os, src.getHeight());
        for (int y = 0; y < src.getHeight(); ++y) {
            int pixel = 0;
            int nextBit = 128;
            for (int x = 0; x < src.getWidth(); ++x) {
                final int argb = src.getRGB(x, y);
                final int red = 0xFF & argb >> 16;
                final int green = 0xFF & argb >> 8;
                final int blue = 0xFF & argb >> 0;
                final int sample = (red + green + blue) / 3;
                if (sample > 127) {
                    pixel |= nextBit;
                }
                nextBit >>>= 1;
                if (nextBit == 0) {
                    os.write(pixel);
                    pixel = 0;
                    nextBit = 128;
                }
            }
            if (nextBit != 128) {
                os.write(pixel);
            }
        }
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".wbmp" };
    }
    
    static class WbmpHeader
    {
        int typeField;
        byte fixHeaderField;
        int width;
        int height;
        
        WbmpHeader(final int typeField, final byte fixHeaderField, final int width, final int height) {
            this.typeField = typeField;
            this.fixHeaderField = fixHeaderField;
            this.width = width;
            this.height = height;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("WbmpHeader");
            pw.println("TypeField: " + this.typeField);
            pw.println("FixHeaderField: 0x" + Integer.toHexString(0xFF & this.fixHeaderField));
            pw.println("Width: " + this.width);
            pw.println("Height: " + this.height);
        }
    }
}
