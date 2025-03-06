// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.util.List;
import org.apache.commons.imaging.common.ImageMetadata;
import java.awt.Dimension;
import org.apache.commons.imaging.icc.IccProfileInfo;
import org.apache.commons.imaging.icc.IccProfileParser;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;
import java.util.Map;
import java.awt.color.ICC_Profile;
import java.io.InputStream;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import java.io.IOException;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;
import java.util.Locale;
import java.io.File;

public final class Imaging
{
    private static final int[] MAGIC_NUMBERS_GIF;
    private static final int[] MAGIC_NUMBERS_PNG;
    private static final int[] MAGIC_NUMBERS_JPEG;
    private static final int[] MAGIC_NUMBERS_BMP;
    private static final int[] MAGIC_NUMBERS_TIFF_MOTOROLA;
    private static final int[] MAGIC_NUMBERS_TIFF_INTEL;
    private static final int[] MAGIC_NUMBERS_PAM;
    private static final int[] MAGIC_NUMBERS_PSD;
    private static final int[] MAGIC_NUMBERS_PBM_A;
    private static final int[] MAGIC_NUMBERS_PBM_B;
    private static final int[] MAGIC_NUMBERS_PGM_A;
    private static final int[] MAGIC_NUMBERS_PGM_B;
    private static final int[] MAGIC_NUMBERS_PPM_A;
    private static final int[] MAGIC_NUMBERS_PPM_B;
    private static final int[] MAGIC_NUMBERS_JBIG2_1;
    private static final int[] MAGIC_NUMBERS_JBIG2_2;
    private static final int[] MAGIC_NUMBERS_ICNS;
    private static final int[] MAGIC_NUMBERS_DCX;
    private static final int[] MAGIC_NUMBERS_RGBE;
    
    private Imaging() {
    }
    
    public static boolean hasImageFileExtension(final File file) {
        return file != null && file.isFile() && hasImageFileExtension(file.getName());
    }
    
    public static boolean hasImageFileExtension(final String filename) {
        if (filename == null) {
            return false;
        }
        final String normalizedFilename = filename.toLowerCase(Locale.ENGLISH);
        for (final ImageParser imageParser : ImageParser.getAllImageParsers()) {
            for (final String extension : imageParser.getAcceptedExtensions()) {
                if (normalizedFilename.endsWith(extension.toLowerCase(Locale.ENGLISH))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static ImageFormat guessFormat(final byte[] bytes) throws ImageReadException, IOException {
        return guessFormat(new ByteSourceArray(bytes));
    }
    
    public static ImageFormat guessFormat(final File file) throws ImageReadException, IOException {
        return guessFormat(new ByteSourceFile(file));
    }
    
    private static boolean compareBytePair(final int[] a, final int[] b) {
        if (a.length != 2 && b.length != 2) {
            throw new RuntimeException("Invalid Byte Pair.");
        }
        return a[0] == b[0] && a[1] == b[1];
    }
    
    public static ImageFormat guessFormat(final ByteSource byteSource) throws ImageReadException, IOException {
        if (byteSource == null) {
            return ImageFormats.UNKNOWN;
        }
        try (final InputStream is = byteSource.getInputStream()) {
            final int i1 = is.read();
            final int i2 = is.read();
            if (i1 < 0 || i2 < 0) {
                throw new ImageReadException("Couldn't read magic numbers to guess format.");
            }
            final int b1 = i1 & 0xFF;
            final int b2 = i2 & 0xFF;
            final int[] bytePair = { b1, b2 };
            if (compareBytePair(Imaging.MAGIC_NUMBERS_GIF, bytePair)) {
                return ImageFormats.GIF;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PNG, bytePair)) {
                return ImageFormats.PNG;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_JPEG, bytePair)) {
                return ImageFormats.JPEG;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_BMP, bytePair)) {
                return ImageFormats.BMP;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_TIFF_MOTOROLA, bytePair)) {
                return ImageFormats.TIFF;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_TIFF_INTEL, bytePair)) {
                return ImageFormats.TIFF;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PSD, bytePair)) {
                return ImageFormats.PSD;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PAM, bytePair)) {
                return ImageFormats.PAM;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PBM_A, bytePair)) {
                return ImageFormats.PBM;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PBM_B, bytePair)) {
                return ImageFormats.PBM;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PGM_A, bytePair)) {
                return ImageFormats.PGM;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PGM_B, bytePair)) {
                return ImageFormats.PGM;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PPM_A, bytePair)) {
                return ImageFormats.PPM;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_PPM_B, bytePair)) {
                return ImageFormats.PPM;
            }
            if (compareBytePair(Imaging.MAGIC_NUMBERS_JBIG2_1, bytePair)) {
                final int i3 = is.read();
                final int i4 = is.read();
                if (i3 < 0 || i4 < 0) {
                    throw new ImageReadException("Couldn't read magic numbers to guess format.");
                }
                final int b3 = i3 & 0xFF;
                final int b4 = i4 & 0xFF;
                final int[] bytePair2 = { b3, b4 };
                if (compareBytePair(Imaging.MAGIC_NUMBERS_JBIG2_2, bytePair2)) {
                    return ImageFormats.JBIG2;
                }
            }
            else {
                if (compareBytePair(Imaging.MAGIC_NUMBERS_ICNS, bytePair)) {
                    return ImageFormats.ICNS;
                }
                if (compareBytePair(Imaging.MAGIC_NUMBERS_DCX, bytePair)) {
                    return ImageFormats.DCX;
                }
                if (compareBytePair(Imaging.MAGIC_NUMBERS_RGBE, bytePair)) {
                    return ImageFormats.RGBE;
                }
            }
            return ImageFormats.UNKNOWN;
        }
    }
    
    public static ICC_Profile getICCProfile(final byte[] bytes) throws ImageReadException, IOException {
        return getICCProfile(bytes, null);
    }
    
    public static ICC_Profile getICCProfile(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getICCProfile(new ByteSourceArray(bytes), params);
    }
    
    public static ICC_Profile getICCProfile(final InputStream is, final String filename) throws ImageReadException, IOException {
        return getICCProfile(is, filename, null);
    }
    
    public static ICC_Profile getICCProfile(final InputStream is, final String filename, final Map<String, Object> params) throws ImageReadException, IOException {
        return getICCProfile(new ByteSourceInputStream(is, filename), params);
    }
    
    public static ICC_Profile getICCProfile(final File file) throws ImageReadException, IOException {
        return getICCProfile(file, null);
    }
    
    public static ICC_Profile getICCProfile(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        return getICCProfile(new ByteSourceFile(file), params);
    }
    
    protected static ICC_Profile getICCProfile(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final byte[] bytes = getICCProfileBytes(byteSource, params);
        if (bytes == null) {
            return null;
        }
        final IccProfileParser parser = new IccProfileParser();
        final IccProfileInfo info = parser.getICCProfileInfo(bytes);
        if (info == null) {
            return null;
        }
        if (info.issRGB()) {
            return null;
        }
        return ICC_Profile.getInstance(bytes);
    }
    
    public static byte[] getICCProfileBytes(final byte[] bytes) throws ImageReadException, IOException {
        return getICCProfileBytes(bytes, null);
    }
    
    public static byte[] getICCProfileBytes(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getICCProfileBytes(new ByteSourceArray(bytes), params);
    }
    
    public static byte[] getICCProfileBytes(final File file) throws ImageReadException, IOException {
        return getICCProfileBytes(file, null);
    }
    
    public static byte[] getICCProfileBytes(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        return getICCProfileBytes(new ByteSourceFile(file), params);
    }
    
    private static byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        return imageParser.getICCProfileBytes(byteSource, params);
    }
    
    public static ImageInfo getImageInfo(final String filename, final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageInfo(new ByteSourceArray(filename, bytes), params);
    }
    
    public static ImageInfo getImageInfo(final String filename, final byte[] bytes) throws ImageReadException, IOException {
        return getImageInfo(new ByteSourceArray(filename, bytes), null);
    }
    
    public static ImageInfo getImageInfo(final InputStream is, final String filename) throws ImageReadException, IOException {
        return getImageInfo(new ByteSourceInputStream(is, filename), null);
    }
    
    public static ImageInfo getImageInfo(final InputStream is, final String filename, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageInfo(new ByteSourceInputStream(is, filename), params);
    }
    
    public static ImageInfo getImageInfo(final byte[] bytes) throws ImageReadException, IOException {
        return getImageInfo(new ByteSourceArray(bytes), null);
    }
    
    public static ImageInfo getImageInfo(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageInfo(new ByteSourceArray(bytes), params);
    }
    
    public static ImageInfo getImageInfo(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageInfo(new ByteSourceFile(file), params);
    }
    
    public static ImageInfo getImageInfo(final File file) throws ImageReadException, IOException {
        return getImageInfo(file, null);
    }
    
    private static ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageParser(byteSource).getImageInfo(byteSource, params);
    }
    
    private static ImageParser getImageParser(final ByteSource byteSource) throws ImageReadException, IOException {
        final ImageFormat format = guessFormat(byteSource);
        if (!format.equals(ImageFormats.UNKNOWN)) {
            final ImageParser[] allImageParsers;
            final ImageParser[] imageParsers = allImageParsers = ImageParser.getAllImageParsers();
            for (final ImageParser imageParser : allImageParsers) {
                if (imageParser.canAcceptType(format)) {
                    return imageParser;
                }
            }
        }
        final String filename = byteSource.getFilename();
        if (filename != null) {
            final ImageParser[] allImageParsers2;
            final ImageParser[] imageParsers2 = allImageParsers2 = ImageParser.getAllImageParsers();
            for (final ImageParser imageParser2 : allImageParsers2) {
                if (imageParser2.canAcceptExtension(filename)) {
                    return imageParser2;
                }
            }
        }
        throw new ImageReadException("Can't parse this format.");
    }
    
    public static Dimension getImageSize(final InputStream is, final String filename) throws ImageReadException, IOException {
        return getImageSize(is, filename, null);
    }
    
    public static Dimension getImageSize(final InputStream is, final String filename, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageSize(new ByteSourceInputStream(is, filename), params);
    }
    
    public static Dimension getImageSize(final byte[] bytes) throws ImageReadException, IOException {
        return getImageSize(bytes, null);
    }
    
    public static Dimension getImageSize(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageSize(new ByteSourceArray(bytes), params);
    }
    
    public static Dimension getImageSize(final File file) throws ImageReadException, IOException {
        return getImageSize(file, null);
    }
    
    public static Dimension getImageSize(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        return getImageSize(new ByteSourceFile(file), params);
    }
    
    public static Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        return imageParser.getImageSize(byteSource, params);
    }
    
    public static String getXmpXml(final InputStream is, final String filename) throws ImageReadException, IOException {
        return getXmpXml(is, filename, null);
    }
    
    public static String getXmpXml(final InputStream is, final String filename, final Map<String, Object> params) throws ImageReadException, IOException {
        return getXmpXml(new ByteSourceInputStream(is, filename), params);
    }
    
    public static String getXmpXml(final byte[] bytes) throws ImageReadException, IOException {
        return getXmpXml(bytes, null);
    }
    
    public static String getXmpXml(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getXmpXml(new ByteSourceArray(bytes), params);
    }
    
    public static String getXmpXml(final File file) throws ImageReadException, IOException {
        return getXmpXml(file, null);
    }
    
    public static String getXmpXml(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        return getXmpXml(new ByteSourceFile(file), params);
    }
    
    public static String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        return imageParser.getXmpXml(byteSource, params);
    }
    
    public static ImageMetadata getMetadata(final byte[] bytes) throws ImageReadException, IOException {
        return getMetadata(bytes, null);
    }
    
    public static ImageMetadata getMetadata(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getMetadata(new ByteSourceArray(bytes), params);
    }
    
    public static ImageMetadata getMetadata(final InputStream is, final String filename) throws ImageReadException, IOException {
        return getMetadata(is, filename, null);
    }
    
    public static ImageMetadata getMetadata(final InputStream is, final String filename, final Map<String, Object> params) throws ImageReadException, IOException {
        return getMetadata(new ByteSourceInputStream(is, filename), params);
    }
    
    public static ImageMetadata getMetadata(final File file) throws ImageReadException, IOException {
        return getMetadata(file, null);
    }
    
    public static ImageMetadata getMetadata(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        return getMetadata(new ByteSourceFile(file), params);
    }
    
    private static ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        return imageParser.getMetadata(byteSource, params);
    }
    
    public static String dumpImageFile(final byte[] bytes) throws ImageReadException, IOException {
        return dumpImageFile(new ByteSourceArray(bytes));
    }
    
    public static String dumpImageFile(final File file) throws ImageReadException, IOException {
        return dumpImageFile(new ByteSourceFile(file));
    }
    
    private static String dumpImageFile(final ByteSource byteSource) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        return imageParser.dumpImageFile(byteSource);
    }
    
    public static FormatCompliance getFormatCompliance(final byte[] bytes) throws ImageReadException, IOException {
        return getFormatCompliance(new ByteSourceArray(bytes));
    }
    
    public static FormatCompliance getFormatCompliance(final File file) throws ImageReadException, IOException {
        return getFormatCompliance(new ByteSourceFile(file));
    }
    
    private static FormatCompliance getFormatCompliance(final ByteSource byteSource) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        return imageParser.getFormatCompliance(byteSource);
    }
    
    public static List<BufferedImage> getAllBufferedImages(final InputStream is, final String filename) throws ImageReadException, IOException {
        return getAllBufferedImages(new ByteSourceInputStream(is, filename));
    }
    
    public static List<BufferedImage> getAllBufferedImages(final byte[] bytes) throws ImageReadException, IOException {
        return getAllBufferedImages(new ByteSourceArray(bytes));
    }
    
    public static List<BufferedImage> getAllBufferedImages(final File file) throws ImageReadException, IOException {
        return getAllBufferedImages(new ByteSourceFile(file));
    }
    
    private static List<BufferedImage> getAllBufferedImages(final ByteSource byteSource) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        return imageParser.getAllBufferedImages(byteSource);
    }
    
    public static BufferedImage getBufferedImage(final InputStream is) throws ImageReadException, IOException {
        return getBufferedImage(is, null);
    }
    
    public static BufferedImage getBufferedImage(final InputStream is, final Map<String, Object> params) throws ImageReadException, IOException {
        String filename = null;
        if (params != null && params.containsKey("FILENAME")) {
            filename = params.get("FILENAME");
        }
        return getBufferedImage(new ByteSourceInputStream(is, filename), params);
    }
    
    public static BufferedImage getBufferedImage(final byte[] bytes) throws ImageReadException, IOException {
        return getBufferedImage(new ByteSourceArray(bytes), null);
    }
    
    public static BufferedImage getBufferedImage(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return getBufferedImage(new ByteSourceArray(bytes), params);
    }
    
    public static BufferedImage getBufferedImage(final File file) throws ImageReadException, IOException {
        return getBufferedImage(new ByteSourceFile(file), null);
    }
    
    public static BufferedImage getBufferedImage(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        return getBufferedImage(new ByteSourceFile(file), params);
    }
    
    private static BufferedImage getBufferedImage(final ByteSource byteSource, Map<String, Object> params) throws ImageReadException, IOException {
        final ImageParser imageParser = getImageParser(byteSource);
        if (null == params) {
            params = new HashMap<String, Object>();
        }
        return imageParser.getBufferedImage(byteSource, params);
    }
    
    public static void writeImage(final BufferedImage src, final File file, final ImageFormat format, final Map<String, Object> params) throws ImageWriteException, IOException {
        try (final FileOutputStream fos = new FileOutputStream(file);
             final BufferedOutputStream os = new BufferedOutputStream(fos)) {
            writeImage(src, os, format, params);
        }
    }
    
    public static byte[] writeImageToBytes(final BufferedImage src, final ImageFormat format, final Map<String, Object> params) throws ImageWriteException, IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        writeImage(src, os, format, params);
        return os.toByteArray();
    }
    
    public static void writeImage(final BufferedImage src, final OutputStream os, final ImageFormat format, Map<String, Object> params) throws ImageWriteException, IOException {
        final ImageParser[] imageParsers = ImageParser.getAllImageParsers();
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("FORMAT", format);
        ImageParser imageParser = null;
        for (final ImageParser imageParser2 : imageParsers) {
            if (imageParser2.canAcceptType(format)) {
                imageParser = imageParser2;
                break;
            }
        }
        if (imageParser != null) {
            imageParser.writeImage(src, os, params);
            return;
        }
        throw new ImageWriteException("Unknown Format: " + format);
    }
    
    static {
        MAGIC_NUMBERS_GIF = new int[] { 71, 73 };
        MAGIC_NUMBERS_PNG = new int[] { 137, 80 };
        MAGIC_NUMBERS_JPEG = new int[] { 255, 216 };
        MAGIC_NUMBERS_BMP = new int[] { 66, 77 };
        MAGIC_NUMBERS_TIFF_MOTOROLA = new int[] { 77, 77 };
        MAGIC_NUMBERS_TIFF_INTEL = new int[] { 73, 73 };
        MAGIC_NUMBERS_PAM = new int[] { 80, 55 };
        MAGIC_NUMBERS_PSD = new int[] { 56, 66 };
        MAGIC_NUMBERS_PBM_A = new int[] { 80, 49 };
        MAGIC_NUMBERS_PBM_B = new int[] { 80, 52 };
        MAGIC_NUMBERS_PGM_A = new int[] { 80, 50 };
        MAGIC_NUMBERS_PGM_B = new int[] { 80, 53 };
        MAGIC_NUMBERS_PPM_A = new int[] { 80, 51 };
        MAGIC_NUMBERS_PPM_B = new int[] { 80, 54 };
        MAGIC_NUMBERS_JBIG2_1 = new int[] { 151, 74 };
        MAGIC_NUMBERS_JBIG2_2 = new int[] { 66, 50 };
        MAGIC_NUMBERS_ICNS = new int[] { 105, 99 };
        MAGIC_NUMBERS_DCX = new int[] { 177, 104 };
        MAGIC_NUMBERS_RGBE = new int[] { 35, 63 };
    }
}
