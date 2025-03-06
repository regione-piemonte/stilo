// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.xbm;

import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageWriteException;
import java.io.OutputStream;
import java.util.UUID;
import java.io.PrintWriter;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.util.Properties;
import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.common.BasicCParser;
import java.util.HashMap;
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

public class XbmImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".xbm";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    @Override
    public String getName() {
        return "X BitMap";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".xbm";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return XbmImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.XBM };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final XbmHeader xbmHeader = this.readXbmHeader(byteSource);
        return new ImageInfo("XBM", 1, new ArrayList<String>(), ImageFormats.XBM, "X BitMap", xbmHeader.height, "image/x-xbitmap", 1, 0, 0.0f, 0, 0.0f, xbmHeader.width, false, false, false, ImageInfo.ColorType.BW, ImageInfo.CompressionAlgorithm.NONE);
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final XbmHeader xbmHeader = this.readXbmHeader(byteSource);
        return new Dimension(xbmHeader.width, xbmHeader.height);
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private XbmHeader readXbmHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        return this.parseXbmHeader(byteSource).xbmHeader;
    }
    
    private XbmParseResult parseXbmHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final Map<String, String> defines = new HashMap<String, String>();
            final ByteArrayOutputStream preprocessedFile = BasicCParser.preprocess(is, null, defines);
            int width = -1;
            int height = -1;
            int xHot = -1;
            int yHot = -1;
            for (final Map.Entry<String, String> entry : defines.entrySet()) {
                final String name = entry.getKey();
                if (name.endsWith("_width")) {
                    width = parseCIntegerLiteral(entry.getValue());
                }
                else if (name.endsWith("_height")) {
                    height = parseCIntegerLiteral(entry.getValue());
                }
                else if (name.endsWith("_x_hot")) {
                    xHot = parseCIntegerLiteral(entry.getValue());
                }
                else {
                    if (!name.endsWith("_y_hot")) {
                        continue;
                    }
                    yHot = parseCIntegerLiteral(entry.getValue());
                }
            }
            if (width == -1) {
                throw new ImageReadException("width not found");
            }
            if (height == -1) {
                throw new ImageReadException("height not found");
            }
            final XbmParseResult xbmParseResult = new XbmParseResult();
            xbmParseResult.cParser = new BasicCParser(new ByteArrayInputStream(preprocessedFile.toByteArray()));
            xbmParseResult.xbmHeader = new XbmHeader(width, height, xHot, yHot);
            return xbmParseResult;
        }
    }
    
    private static int parseCIntegerLiteral(final String value) {
        if (!value.startsWith("0")) {
            return Integer.parseInt(value);
        }
        if (value.length() < 2) {
            return 0;
        }
        if (value.charAt(1) == 'x' || value.charAt(1) == 'X') {
            return Integer.parseInt(value.substring(2), 16);
        }
        return Integer.parseInt(value.substring(1), 8);
    }
    
    private BufferedImage readXbmImage(final XbmHeader xbmHeader, final BasicCParser cParser) throws ImageReadException, IOException {
        String token = cParser.nextToken();
        if (!"static".equals(token)) {
            throw new ImageReadException("Parsing XBM file failed, no 'static' token");
        }
        token = cParser.nextToken();
        if (token == null) {
            throw new ImageReadException("Parsing XBM file failed, no 'unsigned' or 'char' or 'short' token");
        }
        if ("unsigned".equals(token)) {
            token = cParser.nextToken();
        }
        int inputWidth;
        int hexWidth;
        if ("char".equals(token)) {
            inputWidth = 8;
            hexWidth = 4;
        }
        else {
            if (!"short".equals(token)) {
                throw new ImageReadException("Parsing XBM file failed, no 'char' or 'short' token");
            }
            inputWidth = 16;
            hexWidth = 6;
        }
        final String name = cParser.nextToken();
        if (name == null) {
            throw new ImageReadException("Parsing XBM file failed, no variable name");
        }
        if (name.charAt(0) != '_' && !Character.isLetter(name.charAt(0))) {
            throw new ImageReadException("Parsing XBM file failed, variable name doesn't start with letter or underscore");
        }
        for (int i = 0; i < name.length(); ++i) {
            final char c = name.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                throw new ImageReadException("Parsing XBM file failed, variable name contains non-letter non-digit non-underscore");
            }
        }
        token = cParser.nextToken();
        if (!"[".equals(token)) {
            throw new ImageReadException("Parsing XBM file failed, no '[' token");
        }
        token = cParser.nextToken();
        if (!"]".equals(token)) {
            throw new ImageReadException("Parsing XBM file failed, no ']' token");
        }
        token = cParser.nextToken();
        if (!"=".equals(token)) {
            throw new ImageReadException("Parsing XBM file failed, no '=' token");
        }
        token = cParser.nextToken();
        if (!"{".equals(token)) {
            throw new ImageReadException("Parsing XBM file failed, no '{' token");
        }
        final int rowLength = (xbmHeader.width + 7) / 8;
        final byte[] imageData = new byte[rowLength * xbmHeader.height];
        int j = 0;
        for (int y = 0; y < xbmHeader.height; ++y) {
            for (int x = 0; x < xbmHeader.width; x += inputWidth) {
                token = cParser.nextToken();
                if (token == null || !token.startsWith("0x")) {
                    throw new ImageReadException("Parsing XBM file failed, hex value missing");
                }
                if (token.length() > hexWidth) {
                    throw new ImageReadException("Parsing XBM file failed, hex value too long");
                }
                final int value = Integer.parseInt(token.substring(2), 16);
                final int flipped = Integer.reverse(value) >>> 32 - inputWidth;
                if (inputWidth == 16) {
                    imageData[j++] = (byte)(flipped >>> 8);
                    if (x + 8 < xbmHeader.width) {
                        imageData[j++] = (byte)flipped;
                    }
                }
                else {
                    imageData[j++] = (byte)flipped;
                }
                token = cParser.nextToken();
                if (token == null) {
                    throw new ImageReadException("Parsing XBM file failed, premature end of file");
                }
                if (!",".equals(token) && (j < imageData.length || !"}".equals(token))) {
                    throw new ImageReadException("Parsing XBM file failed, punctuation error");
                }
            }
        }
        final int[] palette = { 16777215, 0 };
        final ColorModel colorModel = new IndexColorModel(1, 2, palette, 0, false, -1, 0);
        final DataBufferByte dataBuffer = new DataBufferByte(imageData, imageData.length);
        final WritableRaster raster = Raster.createPackedRaster(dataBuffer, xbmHeader.width, xbmHeader.height, 1, null);
        return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), new Properties());
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        this.readXbmHeader(byteSource).dump(pw);
        return true;
    }
    
    @Override
    public final BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final XbmParseResult result = this.parseXbmHeader(byteSource);
        return this.readXbmImage(result.xbmHeader, result.cParser);
    }
    
    private static String randomName() {
        final UUID uuid = UUID.randomUUID();
        final StringBuilder stringBuilder = new StringBuilder("a");
        long bits = uuid.getMostSignificantBits();
        for (int i = 56; i >= 0; i -= 8) {
            stringBuilder.append(Integer.toHexString((int)(bits >> i & 0xFFL)));
        }
        bits = uuid.getLeastSignificantBits();
        for (int i = 56; i >= 0; i -= 8) {
            stringBuilder.append(Integer.toHexString((int)(bits >> i & 0xFFL)));
        }
        return stringBuilder.toString();
    }
    
    private static String toPrettyHex(final int value) {
        final String s = Integer.toHexString(0xFF & value);
        if (s.length() == 2) {
            return "0x" + s;
        }
        return "0x0" + s;
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
        final String name = randomName();
        os.write(("#define " + name + "_width " + src.getWidth() + "\n").getBytes(StandardCharsets.US_ASCII));
        os.write(("#define " + name + "_height " + src.getHeight() + "\n").getBytes(StandardCharsets.US_ASCII));
        os.write(("static unsigned char " + name + "_bits[] = {").getBytes(StandardCharsets.US_ASCII));
        int bitcache = 0;
        int bitsInCache = 0;
        String separator = "\n  ";
        int written = 0;
        for (int y = 0; y < src.getHeight(); ++y) {
            for (int x = 0; x < src.getWidth(); ++x) {
                final int argb = src.getRGB(x, y);
                final int red = 0xFF & argb >> 16;
                final int green = 0xFF & argb >> 8;
                final int blue = 0xFF & argb >> 0;
                int sample = (red + green + blue) / 3;
                if (sample > 127) {
                    sample = 0;
                }
                else {
                    sample = 1;
                }
                bitcache |= sample << bitsInCache;
                if (++bitsInCache == 8) {
                    os.write(separator.getBytes(StandardCharsets.US_ASCII));
                    separator = ",";
                    if (written == 12) {
                        os.write("\n  ".getBytes(StandardCharsets.US_ASCII));
                        written = 0;
                    }
                    os.write(toPrettyHex(bitcache).getBytes(StandardCharsets.US_ASCII));
                    bitcache = 0;
                    bitsInCache = 0;
                    ++written;
                }
            }
            if (bitsInCache != 0) {
                os.write(separator.getBytes(StandardCharsets.US_ASCII));
                separator = ",";
                if (written == 12) {
                    os.write("\n  ".getBytes(StandardCharsets.US_ASCII));
                    written = 0;
                }
                os.write(toPrettyHex(bitcache).getBytes(StandardCharsets.US_ASCII));
                bitcache = 0;
                bitsInCache = 0;
                ++written;
            }
        }
        os.write("\n};\n".getBytes(StandardCharsets.US_ASCII));
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".xbm" };
    }
    
    private static class XbmHeader
    {
        int width;
        int height;
        int xHot;
        int yHot;
        
        XbmHeader(final int width, final int height, final int xHot, final int yHot) {
            this.xHot = -1;
            this.yHot = -1;
            this.width = width;
            this.height = height;
            this.xHot = xHot;
            this.yHot = yHot;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("XbmHeader");
            pw.println("Width: " + this.width);
            pw.println("Height: " + this.height);
            if (this.xHot != -1 && this.yHot != -1) {
                pw.println("X hot: " + this.xHot);
                pw.println("Y hot: " + this.yHot);
            }
        }
    }
    
    private static class XbmParseResult
    {
        XbmHeader xbmHeader;
        BasicCParser cParser;
    }
}
