// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg;

import org.apache.commons.imaging.internal.Debug;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.imaging.formats.tiff.TiffImageData;
import org.apache.commons.imaging.formats.tiff.JpegImageData;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.ImagingException;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import org.apache.commons.imaging.Imaging;
import java.awt.Dimension;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata;

public class JpegImageMetadata implements ImageMetadata
{
    private final JpegPhotoshopMetadata photoshop;
    private final TiffImageMetadata exif;
    private static final String NEWLINE;
    
    public JpegImageMetadata(final JpegPhotoshopMetadata photoshop, final TiffImageMetadata exif) {
        this.photoshop = photoshop;
        this.exif = exif;
    }
    
    public TiffImageMetadata getExif() {
        return this.exif;
    }
    
    public JpegPhotoshopMetadata getPhotoshop() {
        return this.photoshop;
    }
    
    public TiffField findEXIFValue(final TagInfo tagInfo) {
        try {
            return (this.exif != null) ? this.exif.findField(tagInfo) : null;
        }
        catch (ImageReadException cannotHappen) {
            return null;
        }
    }
    
    public TiffField findEXIFValueWithExactMatch(final TagInfo tagInfo) {
        try {
            return (this.exif != null) ? this.exif.findField(tagInfo, true) : null;
        }
        catch (ImageReadException cannotHappen) {
            return null;
        }
    }
    
    public Dimension getEXIFThumbnailSize() throws ImageReadException, IOException {
        final byte[] data = this.getEXIFThumbnailData();
        if (data != null) {
            return Imaging.getImageSize(data);
        }
        return null;
    }
    
    public byte[] getEXIFThumbnailData() throws ImageReadException, IOException {
        if (this.exif == null) {
            return null;
        }
        final List<? extends ImageMetadataItem> dirs = this.exif.getDirectories();
        for (final ImageMetadataItem d : dirs) {
            final TiffImageMetadata.Directory dir = (TiffImageMetadata.Directory)d;
            byte[] data = null;
            if (dir.getJpegImageData() != null) {
                data = dir.getJpegImageData().getData();
            }
            if (data != null) {
                return data;
            }
        }
        return null;
    }
    
    public BufferedImage getEXIFThumbnail() throws ImageReadException, IOException {
        if (this.exif == null) {
            return null;
        }
        final List<? extends ImageMetadataItem> dirs = this.exif.getDirectories();
        for (final ImageMetadataItem d : dirs) {
            final TiffImageMetadata.Directory dir = (TiffImageMetadata.Directory)d;
            BufferedImage image = dir.getThumbnail();
            if (null != image) {
                return image;
            }
            final JpegImageData jpegImageData = dir.getJpegImageData();
            if (jpegImageData == null) {
                continue;
            }
            boolean imageSucceeded = false;
            try {
                image = Imaging.getBufferedImage(jpegImageData.getData());
                imageSucceeded = true;
            }
            catch (ImagingException ex) {}
            catch (IOException ex2) {}
            finally {
                if (!imageSucceeded) {
                    final ByteArrayInputStream input = new ByteArrayInputStream(jpegImageData.getData());
                    image = ImageIO.read(input);
                }
            }
            if (image != null) {
                return image;
            }
        }
        return null;
    }
    
    public TiffImageData getRawImageData() {
        if (this.exif == null) {
            return null;
        }
        final List<? extends ImageMetadataItem> dirs = this.exif.getDirectories();
        for (final ImageMetadataItem d : dirs) {
            final TiffImageMetadata.Directory dir = (TiffImageMetadata.Directory)d;
            final TiffImageData rawImageData = dir.getTiffImageData();
            if (null != rawImageData) {
                return rawImageData;
            }
        }
        return null;
    }
    
    @Override
    public List<ImageMetadataItem> getItems() {
        final List<ImageMetadataItem> result = new ArrayList<ImageMetadataItem>();
        if (null != this.exif) {
            result.addAll(this.exif.getItems());
        }
        if (null != this.photoshop) {
            result.addAll(this.photoshop.getItems());
        }
        return result;
    }
    
    @Override
    public String toString() {
        return this.toString(null);
    }
    
    @Override
    public String toString(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        final StringBuilder result = new StringBuilder();
        result.append(prefix);
        if (null == this.exif) {
            result.append("No Exif metadata.");
        }
        else {
            result.append("Exif metadata:");
            result.append(JpegImageMetadata.NEWLINE);
            result.append(this.exif.toString("\t"));
        }
        result.append(JpegImageMetadata.NEWLINE);
        result.append(prefix);
        if (null == this.photoshop) {
            result.append("No Photoshop (IPTC) metadata.");
        }
        else {
            result.append("Photoshop (IPTC) metadata:");
            result.append(JpegImageMetadata.NEWLINE);
            result.append(this.photoshop.toString("\t"));
        }
        return result.toString();
    }
    
    public void dump() {
        Debug.debug(this.toString());
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
