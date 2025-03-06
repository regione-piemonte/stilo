// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.Element;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.File;
import java.util.ListIterator;
import java.util.LinkedList;
import java.io.ByteArrayOutputStream;
import java.util.zip.Adler32;
import java.util.HashMap;
import java.util.zip.Checksum;
import java.util.Map;

public abstract class ImageCacher implements SVGSyntax, ErrorConstants
{
    DOMTreeManager domTreeManager;
    Map imageCache;
    Checksum checkSum;
    
    public ImageCacher() {
        this.domTreeManager = null;
        this.imageCache = new HashMap();
        this.checkSum = new Adler32();
    }
    
    public ImageCacher(final DOMTreeManager domTreeManager) {
        this();
        this.setDOMTreeManager(domTreeManager);
    }
    
    public void setDOMTreeManager(final DOMTreeManager domTreeManager) {
        if (domTreeManager == null) {
            throw new IllegalArgumentException();
        }
        this.domTreeManager = domTreeManager;
    }
    
    public DOMTreeManager getDOMTreeManager() {
        return this.domTreeManager;
    }
    
    public String lookup(final ByteArrayOutputStream byteArrayOutputStream, final int n, final int n2, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
        final int checksum = this.getChecksum(byteArrayOutputStream.toByteArray());
        final Integer n3 = new Integer(checksum);
        String s = null;
        final Object cacheableData = this.getCacheableData(byteArrayOutputStream);
        LinkedList<ImageCacheEntry> list = this.imageCache.get(n3);
        if (list == null) {
            list = new LinkedList<ImageCacheEntry>();
            this.imageCache.put(n3, list);
        }
        else {
            final ListIterator<ImageCacheEntry> listIterator = list.listIterator(0);
            while (listIterator.hasNext()) {
                final ImageCacheEntry imageCacheEntry = listIterator.next();
                if (imageCacheEntry.checksum == checksum && this.imagesMatch(imageCacheEntry.src, cacheableData)) {
                    s = imageCacheEntry.href;
                    break;
                }
            }
        }
        if (s == null) {
            final ImageCacheEntry entry = this.createEntry(checksum, cacheableData, n, n2, svgGeneratorContext);
            list.add(entry);
            s = entry.href;
        }
        return s;
    }
    
    abstract Object getCacheableData(final ByteArrayOutputStream p0);
    
    abstract boolean imagesMatch(final Object p0, final Object p1) throws SVGGraphics2DIOException;
    
    abstract ImageCacheEntry createEntry(final int p0, final Object p1, final int p2, final int p3, final SVGGeneratorContext p4) throws SVGGraphics2DIOException;
    
    int getChecksum(final byte[] array) {
        this.checkSum.reset();
        this.checkSum.update(array, 0, array.length);
        return (int)this.checkSum.getValue();
    }
    
    public static class External extends ImageCacher
    {
        private String imageDir;
        private String prefix;
        private String suffix;
        
        public External(final String imageDir, final String prefix, final String suffix) {
            this.imageDir = imageDir;
            this.prefix = prefix;
            this.suffix = suffix;
        }
        
        Object getCacheableData(final ByteArrayOutputStream byteArrayOutputStream) {
            return byteArrayOutputStream;
        }
        
        boolean imagesMatch(final Object o, final Object o2) throws SVGGraphics2DIOException {
            boolean equals;
            try {
                final FileInputStream fileInputStream = new FileInputStream((File)o);
                final int available = fileInputStream.available();
                final byte[] array = new byte[available];
                final byte[] byteArray = ((ByteArrayOutputStream)o2).toByteArray();
                for (int i = 0; i != available; i += fileInputStream.read(array, i, available - i)) {}
                equals = Arrays.equals(array, byteArray);
            }
            catch (IOException ex) {
                throw new SVGGraphics2DIOException("could not read image File " + ((File)o).getName());
            }
            return equals;
        }
        
        ImageCacheEntry createEntry(final int n, final Object o, final int n2, final int n3, final SVGGeneratorContext svgGeneratorContext) throws SVGGraphics2DIOException {
            File file = null;
            try {
                while (file == null) {
                    file = new File(this.imageDir, svgGeneratorContext.idGenerator.generateID(this.prefix) + this.suffix);
                    if (file.exists()) {
                        file = null;
                    }
                }
                ((ByteArrayOutputStream)o).writeTo(new FileOutputStream(file));
                ((ByteArrayOutputStream)o).close();
            }
            catch (IOException ex) {
                throw new SVGGraphics2DIOException("could not write image File " + file.getName());
            }
            return new ImageCacheEntry(n, file, file.getName());
        }
    }
    
    private static class ImageCacheEntry
    {
        public int checksum;
        public Object src;
        public String href;
        
        ImageCacheEntry(final int checksum, final Object src, final String href) {
            this.checksum = checksum;
            this.src = src;
            this.href = href;
        }
    }
    
    public static class Embedded extends ImageCacher
    {
        public void setDOMTreeManager(final DOMTreeManager domTreeManager) {
            if (this.domTreeManager != domTreeManager) {
                this.domTreeManager = domTreeManager;
                this.imageCache = new HashMap();
            }
        }
        
        Object getCacheableData(final ByteArrayOutputStream byteArrayOutputStream) {
            return "data:image/png;base64," + byteArrayOutputStream.toString();
        }
        
        boolean imagesMatch(final Object o, final Object obj) {
            return o.equals(obj);
        }
        
        ImageCacheEntry createEntry(final int n, final Object o, final int n2, final int n3, final SVGGeneratorContext svgGeneratorContext) {
            final String generateID = svgGeneratorContext.idGenerator.generateID("image");
            this.addToTree(generateID, (String)o, n2, n3, svgGeneratorContext);
            return new ImageCacheEntry(n, o, "#" + generateID);
        }
        
        private void addToTree(final String s, final String s2, final int i, final int j, final SVGGeneratorContext svgGeneratorContext) {
            final Element elementNS = this.domTreeManager.getDOMFactory().createElementNS("http://www.w3.org/2000/svg", "image");
            elementNS.setAttributeNS(null, "id", s);
            elementNS.setAttributeNS(null, "width", Integer.toString(i));
            elementNS.setAttributeNS(null, "height", Integer.toString(j));
            elementNS.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", s2);
            this.domTreeManager.addOtherDef(elementNS);
        }
    }
}
