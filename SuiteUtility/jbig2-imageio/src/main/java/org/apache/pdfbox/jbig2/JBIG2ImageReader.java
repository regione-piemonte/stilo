// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.Raster;
import org.apache.pdfbox.jbig2.image.Bitmaps;
import org.apache.pdfbox.jbig2.image.FilterType;
import org.apache.pdfbox.jbig2.util.cache.CacheFactory;
import java.awt.image.BufferedImage;
import javax.imageio.ImageReadParam;
import java.util.ArrayList;
import javax.imageio.ImageTypeSpecifier;
import java.util.Iterator;
import javax.imageio.metadata.IIOMetadata;
import org.apache.pdfbox.jbig2.err.JBIG2Exception;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.spi.ImageReaderSpi;
import org.apache.pdfbox.jbig2.util.log.Logger;
import javax.imageio.ImageReader;

public class JBIG2ImageReader extends ImageReader
{
    private static final Logger log;
    public static final boolean DEBUG = false;
    public static final boolean PERFORMANCE_TEST = false;
    private JBIG2Document document;
    private JBIG2Globals globals;
    
    public JBIG2ImageReader(final ImageReaderSpi originatingProvider) throws IOException {
        super(originatingProvider);
    }
    
    @Override
    public JBIG2ReadParam getDefaultReadParam() {
        return new JBIG2ReadParam();
    }
    
    private JBIG2ReadParam getDefaultReadParam(final int n) {
        int width = 1;
        int height = 1;
        try {
            final int n2 = (n < this.getDocument().getAmountOfPages()) ? n : 0;
            width = this.getWidth(n2);
            height = this.getHeight(n2);
        }
        catch (IOException ex) {
            if (JBIG2ImageReader.log.isInfoEnabled()) {
                JBIG2ImageReader.log.info("Dimensions could not be determined. Returning read params with size " + width + "x" + height);
            }
        }
        return new JBIG2ReadParam(1, 1, 0, 0, new Rectangle(0, 0, width, height), new Dimension(width, height));
    }
    
    @Override
    public int getWidth(final int n) throws IOException {
        return this.getDocument().getPage(n + 1).getWidth();
    }
    
    @Override
    public int getHeight(final int n) throws IOException {
        try {
            return this.getDocument().getPage(n + 1).getHeight();
        }
        catch (JBIG2Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
    
    @Override
    public IIOMetadata getImageMetadata(final int n) throws IOException {
        return new JBIG2ImageMetadata(this.getDocument().getPage(n + 1));
    }
    
    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(final int n) throws IOException {
        final ArrayList<ImageTypeSpecifier> list = new ArrayList<ImageTypeSpecifier>();
        list.add(ImageTypeSpecifier.createFromBufferedImageType(13));
        return (Iterator<ImageTypeSpecifier>)list.iterator();
    }
    
    @Override
    public int getNumImages(final boolean b) throws IOException {
        if (b) {
            if (!this.getDocument().isAmountOfPagesUnknown()) {
                return this.getDocument().getAmountOfPages();
            }
            JBIG2ImageReader.log.info("Amount of pages is unknown.");
        }
        else {
            JBIG2ImageReader.log.info("Search is not allowed.");
        }
        return -1;
    }
    
    @Override
    public IIOMetadata getStreamMetadata() {
        JBIG2ImageReader.log.info("No metadata recorded");
        return null;
    }
    
    public JBIG2Globals getGlobals() throws IOException {
        return this.getDocument().getGlobalSegments();
    }
    
    @Override
    public BufferedImage read(final int n, ImageReadParam defaultReadParam) throws IOException {
        if (defaultReadParam == null) {
            JBIG2ImageReader.log.info("JBIG2ReadParam not specified. Default will be used.");
            defaultReadParam = this.getDefaultReadParam(n);
        }
        final JBIG2Page page = this.getPage(n);
        Bitmap bitmap = (Bitmap)CacheFactory.getCache().get(page);
        if (bitmap == null) {
            try {
                bitmap = page.getBitmap();
                CacheFactory.getCache().put(page, bitmap, bitmap.getMemorySize());
                page.clearPageData();
            }
            catch (JBIG2Exception ex) {
                throw new IOException(ex.getMessage());
            }
        }
        return Bitmaps.asBufferedImage(bitmap, defaultReadParam, FilterType.Gaussian);
    }
    
    @Override
    public boolean canReadRaster() {
        return true;
    }
    
    @Override
    public Raster readRaster(final int n, ImageReadParam defaultReadParam) throws IOException {
        if (defaultReadParam == null) {
            JBIG2ImageReader.log.info("JBIG2ReadParam not specified. Default will be used.");
            defaultReadParam = this.getDefaultReadParam(n);
        }
        final JBIG2Page page = this.getPage(n);
        Bitmap bitmap = (Bitmap)CacheFactory.getCache().get(page);
        if (bitmap == null) {
            try {
                bitmap = page.getBitmap();
                CacheFactory.getCache().put(page, bitmap, bitmap.getMemorySize());
                page.clearPageData();
            }
            catch (JBIG2Exception ex) {
                throw new IOException(ex.getMessage());
            }
        }
        return Bitmaps.asRaster(bitmap, defaultReadParam, FilterType.Gaussian);
    }
    
    public JBIG2Globals processGlobals(final ImageInputStream imageInputStream) throws IOException {
        return new JBIG2Document(imageInputStream).getGlobalSegments();
    }
    
    public void setGlobals(final JBIG2Globals globals) throws IOException {
        this.globals = globals;
        this.document = null;
    }
    
    @Override
    public void setInput(final Object input, final boolean seekForwardOnly, final boolean ignoreMetadata) {
        super.setInput(input, seekForwardOnly, ignoreMetadata);
        this.document = null;
    }
    
    private JBIG2Document getDocument() throws IOException {
        if (this.document == null) {
            if (this.input == null) {
                throw new IOException("Input not set.");
            }
            if (this.globals == null) {
                JBIG2ImageReader.log.debug("Globals not set.");
            }
            this.document = new JBIG2Document((ImageInputStream)this.input, this.globals);
        }
        return this.document;
    }
    
    private JBIG2Page getPage(final int i) throws IOException {
        final JBIG2Page page = this.getDocument().getPage(i + 1);
        if (page == null) {
            throw new IndexOutOfBoundsException("Requested page at index=" + i + " does not exist.");
        }
        return page;
    }
    
    static {
        log = LoggerFactory.getLogger(JBIG2ImageReader.class);
    }
}
