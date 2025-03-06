// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.xpm;

import org.apache.commons.imaging.palette.SimplePalette;
import org.apache.commons.imaging.palette.PaletteFactory;
import org.apache.commons.imaging.ImageWriteException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;
import java.io.PrintWriter;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.util.Properties;
import java.awt.image.DirectColorModel;
import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.IndexColorModel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.common.BasicCParser;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.io.InputStream;
import java.io.IOException;
import java.util.Locale;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.apache.commons.imaging.ImageReadException;
import java.util.Map;
import org.apache.commons.imaging.ImageParser;

public class XpmImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".xpm";
    private static final String[] ACCEPTED_EXTENSIONS;
    private static Map<String, Integer> colorNames;
    private static final char[] WRITE_PALETTE;
    
    private static void loadColorNames() throws ImageReadException {
        synchronized (XpmImageParser.class) {
            if (XpmImageParser.colorNames != null) {
                return;
            }
            try {
                final InputStream rgbTxtStream = XpmImageParser.class.getResourceAsStream("rgb.txt");
                if (rgbTxtStream == null) {
                    throw new ImageReadException("Couldn't find rgb.txt in our resources");
                }
                final Map<String, Integer> colors = new HashMap<String, Integer>();
                try (final InputStreamReader isReader = new InputStreamReader(rgbTxtStream, StandardCharsets.US_ASCII);
                     final BufferedReader reader = new BufferedReader(isReader)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.charAt(0) == '!') {
                            continue;
                        }
                        try {
                            final int red = Integer.parseInt(line.substring(0, 3).trim());
                            final int green = Integer.parseInt(line.substring(4, 7).trim());
                            final int blue = Integer.parseInt(line.substring(8, 11).trim());
                            final String colorName = line.substring(11).trim();
                            colors.put(colorName.toLowerCase(Locale.ENGLISH), 0xFF000000 | red << 16 | green << 8 | blue);
                            continue;
                        }
                        catch (NumberFormatException nfe) {
                            throw new ImageReadException("Couldn't parse color in rgb.txt", nfe);
                        }
                        break;
                    }
                }
                XpmImageParser.colorNames = colors;
            }
            catch (IOException ioException) {
                throw new ImageReadException("Could not parse rgb.txt", ioException);
            }
        }
    }
    
    @Override
    public String getName() {
        return "X PixMap";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".xpm";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return XpmImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.XPM };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final XpmHeader xpmHeader = this.readXpmHeader(byteSource);
        boolean transparent = false;
        ImageInfo.ColorType colorType = ImageInfo.ColorType.BW;
        for (final Map.Entry<Object, PaletteEntry> entry : xpmHeader.palette.entrySet()) {
            final PaletteEntry paletteEntry = entry.getValue();
            if ((paletteEntry.getBestARGB() & 0xFF000000) != 0xFF000000) {
                transparent = true;
            }
            if (paletteEntry.haveColor) {
                colorType = ImageInfo.ColorType.RGB;
            }
            else {
                if (colorType == ImageInfo.ColorType.RGB || (!paletteEntry.haveGray && !paletteEntry.haveGray4Level)) {
                    continue;
                }
                colorType = ImageInfo.ColorType.GRAYSCALE;
            }
        }
        return new ImageInfo("XPM version 3", xpmHeader.numCharsPerPixel * 8, new ArrayList<String>(), ImageFormats.XPM, "X PixMap", xpmHeader.height, "image/x-xpixmap", 1, 0, 0.0f, 0, 0.0f, xpmHeader.width, false, transparent, true, colorType, ImageInfo.CompressionAlgorithm.NONE);
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final XpmHeader xpmHeader = this.readXpmHeader(byteSource);
        return new Dimension(xpmHeader.width, xpmHeader.height);
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private XpmHeader readXpmHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        return this.parseXpmHeader(byteSource).xpmHeader;
    }
    
    private XpmParseResult parseXpmHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final StringBuilder firstComment = new StringBuilder();
            final ByteArrayOutputStream preprocessedFile = BasicCParser.preprocess(is, firstComment, null);
            if (!"XPM".equals(firstComment.toString().trim())) {
                throw new ImageReadException("Parsing XPM file failed, signature isn't '/* XPM */'");
            }
            final XpmParseResult xpmParseResult = new XpmParseResult();
            xpmParseResult.cParser = new BasicCParser(new ByteArrayInputStream(preprocessedFile.toByteArray()));
            xpmParseResult.xpmHeader = this.parseXpmHeader(xpmParseResult.cParser);
            return xpmParseResult;
        }
    }
    
    private boolean parseNextString(final BasicCParser cParser, final StringBuilder stringBuilder) throws IOException, ImageReadException {
        stringBuilder.setLength(0);
        String token = cParser.nextToken();
        if (token.charAt(0) != '\"') {
            throw new ImageReadException("Parsing XPM file failed, no string found where expected");
        }
        BasicCParser.unescapeString(stringBuilder, token);
        for (token = cParser.nextToken(); token.charAt(0) == '\"'; token = cParser.nextToken()) {
            BasicCParser.unescapeString(stringBuilder, token);
        }
        if (",".equals(token)) {
            return true;
        }
        if ("}".equals(token)) {
            return false;
        }
        throw new ImageReadException("Parsing XPM file failed, no ',' or '}' found where expected");
    }
    
    private XpmHeader parseXpmValuesSection(final String row) throws ImageReadException {
        final String[] tokens = BasicCParser.tokenizeRow(row);
        if (tokens.length < 4 || tokens.length > 7) {
            throw new ImageReadException("Parsing XPM file failed, <Values> section has incorrect tokens");
        }
        try {
            final int width = Integer.parseInt(tokens[0]);
            final int height = Integer.parseInt(tokens[1]);
            final int numColors = Integer.parseInt(tokens[2]);
            final int numCharsPerPixel = Integer.parseInt(tokens[3]);
            int xHotSpot = -1;
            int yHotSpot = -1;
            boolean xpmExt = false;
            if (tokens.length >= 6) {
                xHotSpot = Integer.parseInt(tokens[4]);
                yHotSpot = Integer.parseInt(tokens[5]);
            }
            if (tokens.length == 5 || tokens.length == 7) {
                if (!"XPMEXT".equals(tokens[tokens.length - 1])) {
                    throw new ImageReadException("Parsing XPM file failed, can't parse <Values> section XPMEXT");
                }
                xpmExt = true;
            }
            return new XpmHeader(width, height, numColors, numCharsPerPixel, xHotSpot, yHotSpot, xpmExt);
        }
        catch (NumberFormatException nfe) {
            throw new ImageReadException("Parsing XPM file failed, error parsing <Values> section", nfe);
        }
    }
    
    private int parseColor(String color) throws ImageReadException {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
            if (color.length() == 3) {
                final int red = Integer.parseInt(color.substring(0, 1), 16);
                final int green = Integer.parseInt(color.substring(1, 2), 16);
                final int blue = Integer.parseInt(color.substring(2, 3), 16);
                return 0xFF000000 | red << 20 | green << 12 | blue << 4;
            }
            if (color.length() == 6) {
                return 0xFF000000 | Integer.parseInt(color, 16);
            }
            if (color.length() == 9) {
                final int red = Integer.parseInt(color.substring(0, 1), 16);
                final int green = Integer.parseInt(color.substring(3, 4), 16);
                final int blue = Integer.parseInt(color.substring(6, 7), 16);
                return 0xFF000000 | red << 16 | green << 8 | blue;
            }
            if (color.length() == 12) {
                final int red = Integer.parseInt(color.substring(0, 1), 16);
                final int green = Integer.parseInt(color.substring(4, 5), 16);
                final int blue = Integer.parseInt(color.substring(8, 9), 16);
                return 0xFF000000 | red << 16 | green << 8 | blue;
            }
            if (color.length() == 24) {
                final int red = Integer.parseInt(color.substring(0, 1), 16);
                final int green = Integer.parseInt(color.substring(8, 9), 16);
                final int blue = Integer.parseInt(color.substring(16, 17), 16);
                return 0xFF000000 | red << 16 | green << 8 | blue;
            }
            return 0;
        }
        else {
            if (color.charAt(0) == '%') {
                throw new ImageReadException("HSV colors are not implemented even in the XPM specification!");
            }
            if ("None".equals(color)) {
                return 0;
            }
            loadColorNames();
            final String colorLowercase = color.toLowerCase(Locale.ENGLISH);
            if (XpmImageParser.colorNames.containsKey(colorLowercase)) {
                return XpmImageParser.colorNames.get(colorLowercase);
            }
            return 0;
        }
    }
    
    private void populatePaletteEntry(final PaletteEntry paletteEntry, final String key, final String color) throws ImageReadException {
        if ("m".equals(key)) {
            paletteEntry.monoArgb = this.parseColor(color);
            paletteEntry.haveMono = true;
        }
        else if ("g4".equals(key)) {
            paletteEntry.gray4LevelArgb = this.parseColor(color);
            paletteEntry.haveGray4Level = true;
        }
        else if ("g".equals(key)) {
            paletteEntry.grayArgb = this.parseColor(color);
            paletteEntry.haveGray = true;
        }
        else if ("s".equals(key)) {
            paletteEntry.colorArgb = this.parseColor(color);
            paletteEntry.haveColor = true;
        }
        else if ("c".equals(key)) {
            paletteEntry.colorArgb = this.parseColor(color);
            paletteEntry.haveColor = true;
        }
    }
    
    private void parsePaletteEntries(final XpmHeader xpmHeader, final BasicCParser cParser) throws IOException, ImageReadException {
        final StringBuilder row = new StringBuilder();
        for (int i = 0; i < xpmHeader.numColors; ++i) {
            row.setLength(0);
            final boolean hasMore = this.parseNextString(cParser, row);
            if (!hasMore) {
                throw new ImageReadException("Parsing XPM file failed, file ended while reading palette");
            }
            final String name = row.substring(0, xpmHeader.numCharsPerPixel);
            final String[] tokens = BasicCParser.tokenizeRow(row.substring(xpmHeader.numCharsPerPixel));
            final PaletteEntry paletteEntry = new PaletteEntry();
            paletteEntry.index = i;
            int previousKeyIndex = Integer.MIN_VALUE;
            final StringBuilder colorBuffer = new StringBuilder();
            for (int j = 0; j < tokens.length; ++j) {
                final String token = tokens[j];
                boolean isKey = false;
                if ((previousKeyIndex < j - 1 && "m".equals(token)) || "g4".equals(token) || "g".equals(token) || "c".equals(token) || "s".equals(token)) {
                    isKey = true;
                }
                if (isKey) {
                    if (previousKeyIndex >= 0) {
                        final String key = tokens[previousKeyIndex];
                        final String color = colorBuffer.toString();
                        colorBuffer.setLength(0);
                        this.populatePaletteEntry(paletteEntry, key, color);
                    }
                    previousKeyIndex = j;
                }
                else {
                    if (previousKeyIndex < 0) {
                        break;
                    }
                    if (colorBuffer.length() > 0) {
                        colorBuffer.append(' ');
                    }
                    colorBuffer.append(token);
                }
            }
            if (previousKeyIndex >= 0 && colorBuffer.length() > 0) {
                final String key2 = tokens[previousKeyIndex];
                final String color2 = colorBuffer.toString();
                colorBuffer.setLength(0);
                this.populatePaletteEntry(paletteEntry, key2, color2);
            }
            xpmHeader.palette.put(name, paletteEntry);
        }
    }
    
    private XpmHeader parseXpmHeader(final BasicCParser cParser) throws ImageReadException, IOException {
        String token = cParser.nextToken();
        if (!"static".equals(token)) {
            throw new ImageReadException("Parsing XPM file failed, no 'static' token");
        }
        token = cParser.nextToken();
        if (!"char".equals(token)) {
            throw new ImageReadException("Parsing XPM file failed, no 'char' token");
        }
        token = cParser.nextToken();
        if (!"*".equals(token)) {
            throw new ImageReadException("Parsing XPM file failed, no '*' token");
        }
        final String name = cParser.nextToken();
        if (name == null) {
            throw new ImageReadException("Parsing XPM file failed, no variable name");
        }
        if (name.charAt(0) != '_' && !Character.isLetter(name.charAt(0))) {
            throw new ImageReadException("Parsing XPM file failed, variable name doesn't start with letter or underscore");
        }
        for (int i = 0; i < name.length(); ++i) {
            final char c = name.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                throw new ImageReadException("Parsing XPM file failed, variable name contains non-letter non-digit non-underscore");
            }
        }
        token = cParser.nextToken();
        if (!"[".equals(token)) {
            throw new ImageReadException("Parsing XPM file failed, no '[' token");
        }
        token = cParser.nextToken();
        if (!"]".equals(token)) {
            throw new ImageReadException("Parsing XPM file failed, no ']' token");
        }
        token = cParser.nextToken();
        if (!"=".equals(token)) {
            throw new ImageReadException("Parsing XPM file failed, no '=' token");
        }
        token = cParser.nextToken();
        if (!"{".equals(token)) {
            throw new ImageReadException("Parsing XPM file failed, no '{' token");
        }
        final StringBuilder row = new StringBuilder();
        final boolean hasMore = this.parseNextString(cParser, row);
        if (!hasMore) {
            throw new ImageReadException("Parsing XPM file failed, file too short");
        }
        final XpmHeader xpmHeader = this.parseXpmValuesSection(row.toString());
        this.parsePaletteEntries(xpmHeader, cParser);
        return xpmHeader;
    }
    
    private BufferedImage readXpmImage(final XpmHeader xpmHeader, final BasicCParser cParser) throws ImageReadException, IOException {
        ColorModel colorModel;
        WritableRaster raster;
        int bpp;
        if (xpmHeader.palette.size() <= 256) {
            final int[] palette = new int[xpmHeader.palette.size()];
            for (final Map.Entry<Object, PaletteEntry> entry : xpmHeader.palette.entrySet()) {
                final PaletteEntry paletteEntry = entry.getValue();
                palette[paletteEntry.index] = paletteEntry.getBestARGB();
            }
            colorModel = new IndexColorModel(8, xpmHeader.palette.size(), palette, 0, true, -1, 0);
            raster = Raster.createInterleavedRaster(0, xpmHeader.width, xpmHeader.height, 1, null);
            bpp = 8;
        }
        else if (xpmHeader.palette.size() <= 65536) {
            final int[] palette = new int[xpmHeader.palette.size()];
            for (final Map.Entry<Object, PaletteEntry> entry : xpmHeader.palette.entrySet()) {
                final PaletteEntry paletteEntry = entry.getValue();
                palette[paletteEntry.index] = paletteEntry.getBestARGB();
            }
            colorModel = new IndexColorModel(16, xpmHeader.palette.size(), palette, 0, true, -1, 1);
            raster = Raster.createInterleavedRaster(1, xpmHeader.width, xpmHeader.height, 1, null);
            bpp = 16;
        }
        else {
            colorModel = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
            raster = Raster.createPackedRaster(3, xpmHeader.width, xpmHeader.height, new int[] { 16711680, 65280, 255, -16777216 }, null);
            bpp = 32;
        }
        final BufferedImage image = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), new Properties());
        final DataBuffer dataBuffer = raster.getDataBuffer();
        final StringBuilder row = new StringBuilder();
        boolean hasMore = true;
        for (int y = 0; y < xpmHeader.height; ++y) {
            row.setLength(0);
            hasMore = this.parseNextString(cParser, row);
            if (y < xpmHeader.height - 1 && !hasMore) {
                throw new ImageReadException("Parsing XPM file failed, insufficient image rows in file");
            }
            final int rowOffset = y * xpmHeader.width;
            for (int x = 0; x < xpmHeader.width; ++x) {
                final String index = row.substring(x * xpmHeader.numCharsPerPixel, (x + 1) * xpmHeader.numCharsPerPixel);
                final PaletteEntry paletteEntry2 = xpmHeader.palette.get(index);
                if (paletteEntry2 == null) {
                    throw new ImageReadException("No palette entry was defined for " + index);
                }
                if (bpp <= 16) {
                    dataBuffer.setElem(rowOffset + x, paletteEntry2.index);
                }
                else {
                    dataBuffer.setElem(rowOffset + x, paletteEntry2.getBestARGB());
                }
            }
        }
        while (hasMore) {
            row.setLength(0);
            hasMore = this.parseNextString(cParser, row);
        }
        final String token = cParser.nextToken();
        if (!";".equals(token)) {
            throw new ImageReadException("Last token wasn't ';'");
        }
        return image;
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        this.readXpmHeader(byteSource).dump(pw);
        return true;
    }
    
    @Override
    public final BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final XpmParseResult result = this.parseXpmHeader(byteSource);
        return this.readXpmImage(result.xpmHeader, result.cParser);
    }
    
    private String randomName() {
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
    
    private String pixelsForIndex(int index, final int charsPerPixel) {
        final StringBuilder stringBuilder = new StringBuilder();
        int highestPower = 1;
        for (int i = 1; i < charsPerPixel; ++i) {
            highestPower *= XpmImageParser.WRITE_PALETTE.length;
        }
        for (int i = 0; i < charsPerPixel; ++i) {
            final int multiple = index / highestPower;
            index -= multiple * highestPower;
            highestPower /= XpmImageParser.WRITE_PALETTE.length;
            stringBuilder.append(XpmImageParser.WRITE_PALETTE[multiple]);
        }
        return stringBuilder.toString();
    }
    
    private String toColor(final int color) {
        final String hex = Integer.toHexString(color);
        if (hex.length() < 6) {
            final char[] zeroes = new char[6 - hex.length()];
            Arrays.fill(zeroes, '0');
            return "#" + new String(zeroes) + hex;
        }
        return "#" + hex;
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
        final PaletteFactory paletteFactory = new PaletteFactory();
        boolean hasTransparency = false;
        if (paletteFactory.hasTransparency(src, 1)) {
            hasTransparency = true;
        }
        SimplePalette palette = null;
        int maxColors = XpmImageParser.WRITE_PALETTE.length;
        int charsPerPixel = 1;
        while (palette == null) {
            palette = paletteFactory.makeExactRgbPaletteSimple(src, hasTransparency ? (maxColors - 1) : maxColors);
            if (palette == null) {
                maxColors *= XpmImageParser.WRITE_PALETTE.length;
                ++charsPerPixel;
            }
        }
        int colors = palette.length();
        if (hasTransparency) {
            ++colors;
        }
        String line = "/* XPM */\n";
        os.write(line.getBytes(StandardCharsets.US_ASCII));
        line = "static char *" + this.randomName() + "[] = {\n";
        os.write(line.getBytes(StandardCharsets.US_ASCII));
        line = "\"" + src.getWidth() + " " + src.getHeight() + " " + colors + " " + charsPerPixel + "\",\n";
        os.write(line.getBytes(StandardCharsets.US_ASCII));
        for (int i = 0; i < colors; ++i) {
            String color;
            if (i < palette.length()) {
                color = this.toColor(palette.getEntry(i));
            }
            else {
                color = "None";
            }
            line = "\"" + this.pixelsForIndex(i, charsPerPixel) + " c " + color + "\",\n";
            os.write(line.getBytes(StandardCharsets.US_ASCII));
        }
        String separator = "";
        for (int y = 0; y < src.getHeight(); ++y) {
            os.write(separator.getBytes(StandardCharsets.US_ASCII));
            separator = ",\n";
            line = "\"";
            os.write(line.getBytes(StandardCharsets.US_ASCII));
            for (int x = 0; x < src.getWidth(); ++x) {
                final int argb = src.getRGB(x, y);
                if ((argb & 0xFF000000) == 0x0) {
                    line = this.pixelsForIndex(palette.length(), charsPerPixel);
                }
                else {
                    line = this.pixelsForIndex(palette.getPaletteIndex(0xFFFFFF & argb), charsPerPixel);
                }
                os.write(line.getBytes(StandardCharsets.US_ASCII));
            }
            line = "\"";
            os.write(line.getBytes(StandardCharsets.US_ASCII));
        }
        line = "\n};\n";
        os.write(line.getBytes(StandardCharsets.US_ASCII));
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".xpm" };
        WRITE_PALETTE = new char[] { ' ', '.', 'X', 'o', 'O', '+', '@', '#', '$', '%', '&', '*', '=', '-', ';', ':', '>', ',', '<', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'M', 'N', 'B', 'V', 'C', 'Z', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'P', 'I', 'U', 'Y', 'T', 'R', 'E', 'W', 'Q', '!', '~', '^', '/', '(', ')', '_', '`', '\'', ']', '[', '{', '}', '|' };
    }
    
    private static class XpmHeader
    {
        int width;
        int height;
        int numColors;
        int numCharsPerPixel;
        int xHotSpot;
        int yHotSpot;
        boolean xpmExt;
        Map<Object, PaletteEntry> palette;
        
        XpmHeader(final int width, final int height, final int numColors, final int numCharsPerPixel, final int xHotSpot, final int yHotSpot, final boolean xpmExt) {
            this.xHotSpot = -1;
            this.yHotSpot = -1;
            this.palette = new HashMap<Object, PaletteEntry>();
            this.width = width;
            this.height = height;
            this.numColors = numColors;
            this.numCharsPerPixel = numCharsPerPixel;
            this.xHotSpot = xHotSpot;
            this.yHotSpot = yHotSpot;
            this.xpmExt = xpmExt;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("XpmHeader");
            pw.println("Width: " + this.width);
            pw.println("Height: " + this.height);
            pw.println("NumColors: " + this.numColors);
            pw.println("NumCharsPerPixel: " + this.numCharsPerPixel);
            if (this.xHotSpot != -1 && this.yHotSpot != -1) {
                pw.println("X hotspot: " + this.xHotSpot);
                pw.println("Y hotspot: " + this.yHotSpot);
            }
            pw.println("XpmExt: " + this.xpmExt);
        }
    }
    
    private static class PaletteEntry
    {
        int index;
        boolean haveColor;
        int colorArgb;
        boolean haveGray;
        int grayArgb;
        boolean haveGray4Level;
        int gray4LevelArgb;
        boolean haveMono;
        int monoArgb;
        
        private PaletteEntry() {
            this.haveColor = false;
            this.haveGray = false;
            this.haveGray4Level = false;
            this.haveMono = false;
        }
        
        int getBestARGB() {
            if (this.haveColor) {
                return this.colorArgb;
            }
            if (this.haveGray) {
                return this.grayArgb;
            }
            if (this.haveGray4Level) {
                return this.gray4LevelArgb;
            }
            if (this.haveMono) {
                return this.monoArgb;
            }
            return 0;
        }
    }
    
    private static class XpmParseResult
    {
        XpmHeader xpmHeader;
        BasicCParser cParser;
    }
}
