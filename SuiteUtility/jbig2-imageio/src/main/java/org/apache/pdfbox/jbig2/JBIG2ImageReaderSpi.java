// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import java.util.Locale;
import javax.imageio.ImageReader;
import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.spi.ImageReaderSpi;

public class JBIG2ImageReaderSpi extends ImageReaderSpi
{
    private static final String VENDOR = "Apache Software Foundation";
    private static final String VERSION = "1.4.1";
    private static final String READER_CLASS_NAME = "org.apache.pdfbox.jbig2.JBIG2ImageReader";
    private static final String[] NAMES;
    private static final String[] SUFFIXES;
    private static final String[] MIME_TYPES;
    private static final Class<?>[] INPUT_TYPES;
    private static final int[] FILEHEADER_PREAMBLE;
    private static final String[] WRITER_SPI_NAMES;
    static final boolean SUPPORTS_STANDARD_STREAM_METADATE_FORMAT = false;
    static final String NATIVE_STREAM_METADATA_FORMAT_NAME = "JBIG2 Stream Metadata";
    static final String NATIVE_STREAM_METADATA_FORMAT_CLASSNAME = "JBIG2Metadata";
    static final String[] EXTRA_STREAM_METADATA_FORMAT_NAME;
    static final String[] EXTRA_STREAM_METADATA_FORMAT_CLASSNAME;
    static final boolean SUPPORTS_STANDARD_IMAGE_METADATA_FORMAT = false;
    static final String NATIVE_IMAGE_METADATA_FORMAT_NAME = "JBIG2 File Metadata";
    static final String NATIVE_IMAGE_METADATA_FORMAT_CLASSNAME = "JBIG2Metadata";
    static final String[] EXTRA_IMAGE_METADATA_FORMAT_NAME;
    static final String[] EXTRA_IMAGE_METADATA_FORMAT_CLASSNAME;
    
    public JBIG2ImageReaderSpi() {
        super("Apache Software Foundation", "1.4.1", JBIG2ImageReaderSpi.NAMES, JBIG2ImageReaderSpi.SUFFIXES, JBIG2ImageReaderSpi.MIME_TYPES, "org.apache.pdfbox.jbig2.JBIG2ImageReader", JBIG2ImageReaderSpi.INPUT_TYPES, JBIG2ImageReaderSpi.WRITER_SPI_NAMES, false, "JBIG2 Stream Metadata", "JBIG2Metadata", JBIG2ImageReaderSpi.EXTRA_STREAM_METADATA_FORMAT_NAME, JBIG2ImageReaderSpi.EXTRA_STREAM_METADATA_FORMAT_CLASSNAME, false, "JBIG2 File Metadata", "JBIG2Metadata", JBIG2ImageReaderSpi.EXTRA_IMAGE_METADATA_FORMAT_NAME, JBIG2ImageReaderSpi.EXTRA_IMAGE_METADATA_FORMAT_CLASSNAME);
    }
    
    @Override
    public boolean canDecodeInput(final Object o) throws IOException {
        if (o == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        if (!(o instanceof ImageInputStream)) {
            System.out.println("source is not an ImageInputStream");
            return false;
        }
        final ImageInputStream imageInputStream = (ImageInputStream)o;
        imageInputStream.mark();
        for (int i = 0; i < JBIG2ImageReaderSpi.FILEHEADER_PREAMBLE.length; ++i) {
            if ((imageInputStream.read() & 0xFF) != JBIG2ImageReaderSpi.FILEHEADER_PREAMBLE[i]) {
                return false;
            }
        }
        imageInputStream.reset();
        return true;
    }
    
    @Override
    public ImageReader createReaderInstance(final Object o) throws IOException {
        return new JBIG2ImageReader(this);
    }
    
    @Override
    public String getDescription(final Locale locale) {
        return "JBIG2 Image Reader";
    }
    
    static {
        NAMES = new String[] { "jbig2", "JBIG2" };
        SUFFIXES = new String[] { "jb2", "jbig2", "JB2", "JBIG2" };
        MIME_TYPES = new String[] { "image/x-jbig2", "image/x-jb2" };
        INPUT_TYPES = new Class[] { ImageInputStream.class };
        FILEHEADER_PREAMBLE = new int[] { 151, 74, 66, 50, 13, 10, 26, 10 };
        WRITER_SPI_NAMES = new String[0];
        EXTRA_STREAM_METADATA_FORMAT_NAME = null;
        EXTRA_STREAM_METADATA_FORMAT_CLASSNAME = null;
        EXTRA_IMAGE_METADATA_FORMAT_NAME = null;
        EXTRA_IMAGE_METADATA_FORMAT_CLASSNAME = null;
    }
}
