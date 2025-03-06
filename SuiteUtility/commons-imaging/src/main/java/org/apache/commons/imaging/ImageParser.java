// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

import org.apache.commons.imaging.common.SimpleBufferedImageFactory;
import org.apache.commons.imaging.common.BufferedImageFactory;
import java.util.Locale;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.awt.Dimension;
import java.io.OutputStream;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.List;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import java.util.logging.Level;
import java.io.File;
import org.apache.commons.imaging.common.bytesource.ByteSourceArray;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.formats.xpm.XpmImageParser;
import org.apache.commons.imaging.formats.xbm.XbmImageParser;
import org.apache.commons.imaging.formats.wbmp.WbmpImageParser;
import org.apache.commons.imaging.formats.tiff.TiffImageParser;
import org.apache.commons.imaging.formats.rgbe.RgbeImageParser;
import org.apache.commons.imaging.formats.psd.PsdImageParser;
import org.apache.commons.imaging.formats.pnm.PnmImageParser;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.formats.pcx.PcxImageParser;
import org.apache.commons.imaging.formats.jpeg.JpegImageParser;
import org.apache.commons.imaging.formats.ico.IcoImageParser;
import org.apache.commons.imaging.formats.icns.IcnsImageParser;
import org.apache.commons.imaging.formats.gif.GifImageParser;
import org.apache.commons.imaging.formats.dcx.DcxImageParser;
import org.apache.commons.imaging.formats.bmp.BmpImageParser;
import java.util.logging.Logger;
import org.apache.commons.imaging.common.BinaryFileParser;

public abstract class ImageParser extends BinaryFileParser
{
    private static final Logger LOGGER;
    
    public static ImageParser[] getAllImageParsers() {
        return new ImageParser[] { new BmpImageParser(), new DcxImageParser(), new GifImageParser(), new IcnsImageParser(), new IcoImageParser(), new JpegImageParser(), new PcxImageParser(), new PngImageParser(), new PnmImageParser(), new PsdImageParser(), new RgbeImageParser(), new TiffImageParser(), new WbmpImageParser(), new XbmImageParser(), new XpmImageParser() };
    }
    
    public final ImageMetadata getMetadata(final ByteSource byteSource) throws ImageReadException, IOException {
        return this.getMetadata(byteSource, null);
    }
    
    public abstract ImageMetadata getMetadata(final ByteSource p0, final Map<String, Object> p1) throws ImageReadException, IOException;
    
    public final ImageMetadata getMetadata(final byte[] bytes) throws ImageReadException, IOException {
        return this.getMetadata(bytes, null);
    }
    
    public final ImageMetadata getMetadata(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return this.getMetadata(new ByteSourceArray(bytes), params);
    }
    
    public final ImageMetadata getMetadata(final File file) throws ImageReadException, IOException {
        return this.getMetadata(file, null);
    }
    
    public final ImageMetadata getMetadata(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        if (ImageParser.LOGGER.isLoggable(Level.FINEST)) {
            ImageParser.LOGGER.finest(this.getName() + ".getMetadata: " + file.getName());
        }
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        return this.getMetadata(new ByteSourceFile(file), params);
    }
    
    public abstract ImageInfo getImageInfo(final ByteSource p0, final Map<String, Object> p1) throws ImageReadException, IOException;
    
    public final ImageInfo getImageInfo(final ByteSource byteSource) throws ImageReadException, IOException {
        return this.getImageInfo(byteSource, null);
    }
    
    public final ImageInfo getImageInfo(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return this.getImageInfo(new ByteSourceArray(bytes), params);
    }
    
    public final ImageInfo getImageInfo(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        return this.getImageInfo(new ByteSourceFile(file), params);
    }
    
    public FormatCompliance getFormatCompliance(final ByteSource byteSource) throws ImageReadException, IOException {
        return null;
    }
    
    public final FormatCompliance getFormatCompliance(final byte[] bytes) throws ImageReadException, IOException {
        return this.getFormatCompliance(new ByteSourceArray(bytes));
    }
    
    public final FormatCompliance getFormatCompliance(final File file) throws ImageReadException, IOException {
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        return this.getFormatCompliance(new ByteSourceFile(file));
    }
    
    public List<BufferedImage> getAllBufferedImages(final ByteSource byteSource) throws ImageReadException, IOException {
        final BufferedImage bi = this.getBufferedImage(byteSource, null);
        final List<BufferedImage> result = new ArrayList<BufferedImage>();
        result.add(bi);
        return result;
    }
    
    public final List<BufferedImage> getAllBufferedImages(final byte[] bytes) throws ImageReadException, IOException {
        return this.getAllBufferedImages(new ByteSourceArray(bytes));
    }
    
    public final List<BufferedImage> getAllBufferedImages(final File file) throws ImageReadException, IOException {
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        return this.getAllBufferedImages(new ByteSourceFile(file));
    }
    
    public abstract BufferedImage getBufferedImage(final ByteSource p0, final Map<String, Object> p1) throws ImageReadException, IOException;
    
    public final BufferedImage getBufferedImage(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return this.getBufferedImage(new ByteSourceArray(bytes), params);
    }
    
    public final BufferedImage getBufferedImage(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        return this.getBufferedImage(new ByteSourceFile(file), params);
    }
    
    public void writeImage(final BufferedImage src, final OutputStream os, final Map<String, Object> params) throws ImageWriteException, IOException {
        os.close();
        throw new ImageWriteException("This image format (" + this.getName() + ") cannot be written.");
    }
    
    public final Dimension getImageSize(final byte[] bytes) throws ImageReadException, IOException {
        return this.getImageSize(bytes, null);
    }
    
    public final Dimension getImageSize(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return this.getImageSize(new ByteSourceArray(bytes), params);
    }
    
    public final Dimension getImageSize(final File file) throws ImageReadException, IOException {
        return this.getImageSize(file, null);
    }
    
    public final Dimension getImageSize(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        return this.getImageSize(new ByteSourceFile(file), params);
    }
    
    public abstract Dimension getImageSize(final ByteSource p0, final Map<String, Object> p1) throws ImageReadException, IOException;
    
    public abstract String getXmpXml(final ByteSource p0, final Map<String, Object> p1) throws ImageReadException, IOException;
    
    public final byte[] getICCProfileBytes(final byte[] bytes) throws ImageReadException, IOException {
        return this.getICCProfileBytes(bytes, null);
    }
    
    public final byte[] getICCProfileBytes(final byte[] bytes, final Map<String, Object> params) throws ImageReadException, IOException {
        return this.getICCProfileBytes(new ByteSourceArray(bytes), params);
    }
    
    public final byte[] getICCProfileBytes(final File file) throws ImageReadException, IOException {
        return this.getICCProfileBytes(file, null);
    }
    
    public final byte[] getICCProfileBytes(final File file, final Map<String, Object> params) throws ImageReadException, IOException {
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        if (ImageParser.LOGGER.isLoggable(Level.FINEST)) {
            ImageParser.LOGGER.finest(this.getName() + ": " + file.getName());
        }
        return this.getICCProfileBytes(new ByteSourceFile(file), params);
    }
    
    public abstract byte[] getICCProfileBytes(final ByteSource p0, final Map<String, Object> p1) throws ImageReadException, IOException;
    
    public final String dumpImageFile(final byte[] bytes) throws ImageReadException, IOException {
        return this.dumpImageFile(new ByteSourceArray(bytes));
    }
    
    public final String dumpImageFile(final File file) throws ImageReadException, IOException {
        if (!this.canAcceptExtension(file)) {
            return null;
        }
        if (ImageParser.LOGGER.isLoggable(Level.FINEST)) {
            ImageParser.LOGGER.finest(this.getName() + ": " + file.getName());
        }
        return this.dumpImageFile(new ByteSourceFile(file));
    }
    
    public final String dumpImageFile(final ByteSource byteSource) throws ImageReadException, IOException {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        this.dumpImageFile(pw, byteSource);
        pw.flush();
        return sw.toString();
    }
    
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        return false;
    }
    
    public abstract String getName();
    
    public abstract String getDefaultExtension();
    
    protected abstract String[] getAcceptedExtensions();
    
    protected abstract ImageFormat[] getAcceptedTypes();
    
    public boolean canAcceptType(final ImageFormat type) {
        final ImageFormat[] acceptedTypes;
        final ImageFormat[] types = acceptedTypes = this.getAcceptedTypes();
        for (final ImageFormat type2 : acceptedTypes) {
            if (type2.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    protected final boolean canAcceptExtension(final File file) {
        return this.canAcceptExtension(file.getName());
    }
    
    protected final boolean canAcceptExtension(final String filename) {
        final String[] exts = this.getAcceptedExtensions();
        if (exts == null) {
            return true;
        }
        final int index = filename.lastIndexOf(46);
        if (index >= 0) {
            String ext = filename.substring(index);
            ext = ext.toLowerCase(Locale.ENGLISH);
            for (final String ext2 : exts) {
                final String ext2Lower = ext2.toLowerCase(Locale.ENGLISH);
                if (ext2Lower.equals(ext)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected BufferedImageFactory getBufferedImageFactory(final Map<String, Object> params) {
        if (params == null) {
            return new SimpleBufferedImageFactory();
        }
        final BufferedImageFactory result = params.get("BUFFERED_IMAGE_FACTORY");
        if (null != result) {
            return result;
        }
        return new SimpleBufferedImageFactory();
    }
    
    public static boolean isStrict(final Map<String, Object> params) {
        return params != null && params.containsKey("STRICT") && params.get("STRICT");
    }
    
    static {
        LOGGER = Logger.getLogger(ImageParser.class.getName());
    }
}
