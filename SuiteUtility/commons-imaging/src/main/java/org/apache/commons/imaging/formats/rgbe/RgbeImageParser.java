// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.rgbe;

import java.awt.Dimension;
import java.awt.image.DataBuffer;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.BandedSampleModel;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.DataBufferFloat;
import java.awt.image.BufferedImage;
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
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageParser;

public class RgbeImageParser extends ImageParser
{
    public RgbeImageParser() {
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "Radiance HDR";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".hdr";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return new String[] { ".hdr", ".pic" };
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.RGBE };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final RgbeInfo info = new RgbeInfo(byteSource)) {
            final ImageMetadata ret = info.getMetadata();
            return ret;
        }
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final RgbeInfo info = new RgbeInfo(byteSource)) {
            return new ImageInfo(this.getName(), 32, new ArrayList<String>(), ImageFormats.RGBE, this.getName(), info.getHeight(), "image/vnd.radiance", 1, -1, -1.0f, -1, -1.0f, info.getWidth(), false, false, false, ImageInfo.ColorType.RGB, ImageInfo.CompressionAlgorithm.ADAPTIVE_RLE);
        }
    }
    
    @Override
    public BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final RgbeInfo info = new RgbeInfo(byteSource)) {
            final DataBuffer buffer = new DataBufferFloat(info.getPixelData(), info.getWidth() * info.getHeight());
            final BufferedImage ret = new BufferedImage(new ComponentColorModel(ColorSpace.getInstance(1000), false, false, 1, buffer.getDataType()), Raster.createWritableRaster(new BandedSampleModel(buffer.getDataType(), info.getWidth(), info.getHeight(), 3), buffer, new Point()), false, null);
            return ret;
        }
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final RgbeInfo info = new RgbeInfo(byteSource)) {
            final Dimension ret = new Dimension(info.getWidth(), info.getHeight());
            return ret;
        }
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
}
