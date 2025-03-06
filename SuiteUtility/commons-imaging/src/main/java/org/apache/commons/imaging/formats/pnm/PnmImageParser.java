// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pnm;

import org.apache.commons.imaging.ImageWriteException;
import java.util.HashMap;
import org.apache.commons.imaging.palette.PaletteFactory;
import java.io.OutputStream;
import org.apache.commons.imaging.common.ImageBuilder;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.common.ImageMetadata;
import java.awt.Dimension;
import java.util.Map;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageParser;

public class PnmImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".pnm";
    private static final String[] ACCEPTED_EXTENSIONS;
    public static final String PARAM_KEY_PNM_RAWBITS = "PNM_RAWBITS";
    public static final String PARAM_VALUE_PNM_RAWBITS_YES = "YES";
    public static final String PARAM_VALUE_PNM_RAWBITS_NO = "NO";
    
    public PnmImageParser() {
        super.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "Pbm-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".pnm";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return PnmImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.PBM, ImageFormats.PGM, ImageFormats.PPM, ImageFormats.PNM, ImageFormats.PAM };
    }
    
    private FileInfo readHeader(final InputStream is) throws ImageReadException, IOException {
        final byte identifier1 = BinaryFunctions.readByte("Identifier1", is, "Not a Valid PNM File");
        final byte identifier2 = BinaryFunctions.readByte("Identifier2", is, "Not a Valid PNM File");
        if (identifier1 != 80) {
            throw new ImageReadException("PNM file has invalid prefix byte 1");
        }
        final WhiteSpaceReader wsr = new WhiteSpaceReader(is);
        if (identifier2 != 49 && identifier2 != 52 && identifier2 != 50 && identifier2 != 53 && identifier2 != 51) {
            if (identifier2 != 54) {
                if (identifier2 != 55) {
                    throw new ImageReadException("PNM file has invalid prefix byte 2");
                }
                int width = -1;
                boolean seenWidth = false;
                int height = -1;
                boolean seenHeight = false;
                int depth = -1;
                boolean seenDepth = false;
                int maxVal = -1;
                boolean seenMaxVal = false;
                final StringBuilder tupleType = new StringBuilder();
                boolean seenTupleType = false;
                wsr.readLine();
                String line;
                while ((line = wsr.readLine()) != null) {
                    line = line.trim();
                    if (line.charAt(0) == '#') {
                        continue;
                    }
                    final StringTokenizer tokenizer = new StringTokenizer(line, " ", false);
                    final String type = tokenizer.nextToken();
                    if ("WIDTH".equals(type)) {
                        seenWidth = true;
                        if (!tokenizer.hasMoreTokens()) {
                            throw new ImageReadException("PAM header has no WIDTH value");
                        }
                        width = Integer.parseInt(tokenizer.nextToken());
                    }
                    else if ("HEIGHT".equals(type)) {
                        seenHeight = true;
                        if (!tokenizer.hasMoreTokens()) {
                            throw new ImageReadException("PAM header has no HEIGHT value");
                        }
                        height = Integer.parseInt(tokenizer.nextToken());
                    }
                    else if ("DEPTH".equals(type)) {
                        seenDepth = true;
                        if (!tokenizer.hasMoreTokens()) {
                            throw new ImageReadException("PAM header has no DEPTH value");
                        }
                        depth = Integer.parseInt(tokenizer.nextToken());
                    }
                    else if ("MAXVAL".equals(type)) {
                        seenMaxVal = true;
                        if (!tokenizer.hasMoreTokens()) {
                            throw new ImageReadException("PAM header has no MAXVAL value");
                        }
                        maxVal = Integer.parseInt(tokenizer.nextToken());
                    }
                    else if ("TUPLTYPE".equals(type)) {
                        seenTupleType = true;
                        if (!tokenizer.hasMoreTokens()) {
                            throw new ImageReadException("PAM header has no TUPLTYPE value");
                        }
                        tupleType.append(tokenizer.nextToken());
                    }
                    else {
                        if ("ENDHDR".equals(type)) {
                            break;
                        }
                        throw new ImageReadException("Invalid PAM file header type " + type);
                    }
                }
                if (!seenWidth) {
                    throw new ImageReadException("PAM header has no WIDTH");
                }
                if (!seenHeight) {
                    throw new ImageReadException("PAM header has no HEIGHT");
                }
                if (!seenDepth) {
                    throw new ImageReadException("PAM header has no DEPTH");
                }
                if (!seenMaxVal) {
                    throw new ImageReadException("PAM header has no MAXVAL");
                }
                if (!seenTupleType) {
                    throw new ImageReadException("PAM header has no TUPLTYPE");
                }
                return new PamFileInfo(width, height, depth, maxVal, tupleType.toString());
            }
        }
        int width;
        try {
            width = Integer.parseInt(wsr.readtoWhiteSpace());
        }
        catch (NumberFormatException e) {
            throw new ImageReadException("Invalid width specified.", e);
        }
        int height2;
        try {
            height2 = Integer.parseInt(wsr.readtoWhiteSpace());
        }
        catch (NumberFormatException e2) {
            throw new ImageReadException("Invalid height specified.", e2);
        }
        if (identifier2 == 49) {
            return new PbmFileInfo(width, height2, false);
        }
        if (identifier2 == 52) {
            return new PbmFileInfo(width, height2, true);
        }
        if (identifier2 == 50) {
            final int maxgray = Integer.parseInt(wsr.readtoWhiteSpace());
            return new PgmFileInfo(width, height2, false, maxgray);
        }
        if (identifier2 == 53) {
            final int maxgray = Integer.parseInt(wsr.readtoWhiteSpace());
            return new PgmFileInfo(width, height2, true, maxgray);
        }
        if (identifier2 == 51) {
            final int max = Integer.parseInt(wsr.readtoWhiteSpace());
            return new PpmFileInfo(width, height2, false, max);
        }
        if (identifier2 == 54) {
            final int max = Integer.parseInt(wsr.readtoWhiteSpace());
            return new PpmFileInfo(width, height2, true, max);
        }
        throw new ImageReadException("PNM file has invalid header.");
    }
    
    private FileInfo readHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            return this.readHeader(is);
        }
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FileInfo info = this.readHeader(byteSource);
        if (info == null) {
            throw new ImageReadException("PNM: Couldn't read Header");
        }
        return new Dimension(info.width, info.height);
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FileInfo info = this.readHeader(byteSource);
        if (info == null) {
            throw new ImageReadException("PNM: Couldn't read Header");
        }
        final List<String> comments = new ArrayList<String>();
        final int bitsPerPixel = info.getBitDepth() * info.getNumComponents();
        final ImageFormat format = info.getImageType();
        final String formatName = info.getImageTypeDescription();
        final String mimeType = info.getMIMEType();
        final int numberOfImages = 1;
        final boolean progressive = false;
        final int physicalWidthDpi = 72;
        final float physicalWidthInch = (float)(info.width / 72.0);
        final int physicalHeightDpi = 72;
        final float physicalHeightInch = (float)(info.height / 72.0);
        final String formatDetails = info.getImageTypeDescription();
        final boolean transparent = info.hasAlpha();
        final boolean usesPalette = false;
        final ImageInfo.ColorType colorType = info.getColorType();
        final ImageInfo.CompressionAlgorithm compressionAlgorithm = ImageInfo.CompressionAlgorithm.NONE;
        return new ImageInfo(formatDetails, bitsPerPixel, comments, format, formatName, info.height, mimeType, 1, 72, physicalHeightInch, 72, physicalWidthInch, info.width, false, transparent, false, colorType, compressionAlgorithm);
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        pw.println("pnm.dumpImageFile");
        final ImageInfo imageData = this.getImageInfo(byteSource);
        if (imageData == null) {
            return false;
        }
        imageData.toString(pw, "");
        pw.println("");
        return true;
    }
    
    @Override
    public BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final FileInfo info = this.readHeader(is);
            final int width = info.width;
            final int height = info.height;
            final boolean hasAlpha = info.hasAlpha();
            final ImageBuilder imageBuilder = new ImageBuilder(width, height, hasAlpha);
            info.readImage(imageBuilder, is);
            final BufferedImage ret = imageBuilder.getBufferedImage();
            return ret;
        }
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        PnmWriter writer = null;
        boolean useRawbits = true;
        if (params != null) {
            final Object useRawbitsParam = params.get("PNM_RAWBITS");
            if (useRawbitsParam != null && useRawbitsParam.equals("NO")) {
                useRawbits = false;
            }
            final Object subtype = params.get("FORMAT");
            if (subtype != null) {
                if (subtype.equals(ImageFormats.PBM)) {
                    writer = new PbmWriter(useRawbits);
                }
                else if (subtype.equals(ImageFormats.PGM)) {
                    writer = new PgmWriter(useRawbits);
                }
                else if (subtype.equals(ImageFormats.PPM)) {
                    writer = new PpmWriter(useRawbits);
                }
                else if (subtype.equals(ImageFormats.PAM)) {
                    writer = new PamWriter();
                }
            }
        }
        if (writer == null) {
            final boolean hasAlpha = new PaletteFactory().hasTransparency(src);
            if (hasAlpha) {
                writer = new PamWriter();
            }
            else {
                writer = new PpmWriter(useRawbits);
            }
        }
        if (params != null) {
            params = new HashMap<String, Object>(params);
        }
        else {
            params = new HashMap<String, Object>();
        }
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        if (params.containsKey("PNM_RAWBITS")) {
            params.remove("PNM_RAWBITS");
        }
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        writer.writeImage(src, os, params);
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".pbm", ".pgm", ".ppm", ".pnm", ".pam" };
    }
}
