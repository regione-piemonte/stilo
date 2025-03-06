// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.dcx;

import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.PixelDensity;
import java.io.OutputStream;
import java.util.HashMap;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;
import org.apache.commons.imaging.formats.pcx.PcxImageParser;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.awt.Dimension;
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

public class DcxImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".dcx";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    public DcxImageParser() {
        super.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "Dcx-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".dcx";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return DcxImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.DCX };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private DcxHeader readDcxHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final int id = BinaryFunctions.read4Bytes("Id", is, "Not a Valid DCX File", this.getByteOrder());
            final List<Long> pageTable = new ArrayList<Long>(1024);
            for (int i = 0; i < 1024; ++i) {
                final long pageOffset = 0xFFFFFFFFL & (long)BinaryFunctions.read4Bytes("PageTable", is, "Not a Valid DCX File", this.getByteOrder());
                if (pageOffset == 0L) {
                    break;
                }
                pageTable.add(pageOffset);
            }
            if (id != 987654321) {
                throw new ImageReadException("Not a Valid DCX File: file id incorrect");
            }
            if (pageTable.size() == 1024) {
                throw new ImageReadException("DCX page table not terminated by zero entry");
            }
            final Object[] objects = pageTable.toArray();
            final long[] pages = new long[objects.length];
            for (int j = 0; j < objects.length; ++j) {
                pages[j] = (long)objects[j];
            }
            final DcxHeader ret = new DcxHeader(id, pages);
            return ret;
        }
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        this.readDcxHeader(byteSource).dump(pw);
        return true;
    }
    
    @Override
    public final BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<BufferedImage> list = this.getAllBufferedImages(byteSource);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public List<BufferedImage> getAllBufferedImages(final ByteSource byteSource) throws ImageReadException, IOException {
        final DcxHeader dcxHeader = this.readDcxHeader(byteSource);
        final List<BufferedImage> images = new ArrayList<BufferedImage>();
        final PcxImageParser pcxImageParser = new PcxImageParser();
        for (final long element : dcxHeader.pageTable) {
            try (final InputStream stream = byteSource.getInputStream(element)) {
                final ByteSourceInputStream pcxSource = new ByteSourceInputStream(stream, null);
                final BufferedImage image = pcxImageParser.getBufferedImage(pcxSource, new HashMap<String, Object>());
                images.add(image);
            }
        }
        return images;
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        final HashMap<String, Object> pcxParams = new HashMap<String, Object>();
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        if (params.containsKey("PCX_COMPRESSION")) {
            final Object value = params.remove("PCX_COMPRESSION");
            pcxParams.put("PCX_COMPRESSION", value);
        }
        if (params.containsKey("PIXEL_DENSITY")) {
            final Object value = params.remove("PIXEL_DENSITY");
            if (value != null) {
                if (!(value instanceof PixelDensity)) {
                    throw new ImageWriteException("Invalid pixel density parameter");
                }
                pcxParams.put("PIXEL_DENSITY", value);
            }
        }
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        final int headerSize = 4100;
        final BinaryOutputStream bos = new BinaryOutputStream(os, ByteOrder.LITTLE_ENDIAN);
        bos.write4Bytes(987654321);
        bos.write4Bytes(4100);
        for (int i = 0; i < 1023; ++i) {
            bos.write4Bytes(0);
        }
        final PcxImageParser pcxImageParser = new PcxImageParser();
        pcxImageParser.writeImage(src, bos, pcxParams);
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".dcx" };
    }
    
    private static class DcxHeader
    {
        public static final int DCX_ID = 987654321;
        public final int id;
        public final long[] pageTable;
        
        DcxHeader(final int id, final long[] pageTable) {
            this.id = id;
            this.pageTable = pageTable;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("DcxHeader");
            pw.println("Id: 0x" + Integer.toHexString(this.id));
            pw.println("Pages: " + this.pageTable.length);
            pw.println();
        }
    }
}
