// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pnm;

import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;

class PamFileInfo extends FileInfo
{
    private final int depth;
    private final int maxval;
    private final float scale;
    private final int bytesPerSample;
    private final boolean hasAlpha;
    private final TupleReader tupleReader;
    
    PamFileInfo(final int width, final int height, final int depth, final int maxval, final String tupleType) throws ImageReadException {
        super(width, height, true);
        this.depth = depth;
        this.maxval = maxval;
        if (maxval <= 0) {
            throw new ImageReadException("PAM maxVal " + maxval + " is out of range [1;65535]");
        }
        if (maxval <= 255) {
            this.scale = 255.0f;
            this.bytesPerSample = 1;
        }
        else {
            if (maxval > 65535) {
                throw new ImageReadException("PAM maxVal " + maxval + " is out of range [1;65535]");
            }
            this.scale = 65535.0f;
            this.bytesPerSample = 2;
        }
        this.hasAlpha = tupleType.endsWith("_ALPHA");
        if ("BLACKANDWHITE".equals(tupleType) || "BLACKANDWHITE_ALPHA".equals(tupleType)) {
            this.tupleReader = new GrayscaleTupleReader(ImageInfo.ColorType.BW);
        }
        else if ("GRAYSCALE".equals(tupleType) || "GRAYSCALE_ALPHA".equals(tupleType)) {
            this.tupleReader = new GrayscaleTupleReader(ImageInfo.ColorType.GRAYSCALE);
        }
        else {
            if (!"RGB".equals(tupleType) && !"RGB_ALPHA".equals(tupleType)) {
                throw new ImageReadException("Unknown PAM tupletype '" + tupleType + "'");
            }
            this.tupleReader = new ColorTupleReader();
        }
    }
    
    public boolean hasAlpha() {
        return this.hasAlpha;
    }
    
    public int getNumComponents() {
        return this.depth;
    }
    
    public int getBitDepth() {
        return this.maxval;
    }
    
    public ImageFormat getImageType() {
        return ImageFormats.PAM;
    }
    
    public String getImageTypeDescription() {
        return "PAM: portable arbitrary map file format";
    }
    
    public String getMIMEType() {
        return "image/x-portable-arbitrary-map";
    }
    
    public ImageInfo.ColorType getColorType() {
        return this.tupleReader.getColorType();
    }
    
    public int getRGB(final WhiteSpaceReader wsr) throws IOException {
        throw new UnsupportedOperationException("PAM files are only ever binary");
    }
    
    public int getRGB(final InputStream is) throws IOException {
        return this.tupleReader.getRGB(is);
    }
    
    private abstract class TupleReader
    {
        public abstract ImageInfo.ColorType getColorType();
        
        public abstract int getRGB(final InputStream p0) throws IOException;
    }
    
    private class GrayscaleTupleReader extends TupleReader
    {
        private final ImageInfo.ColorType colorType;
        
        GrayscaleTupleReader(final ImageInfo.ColorType colorType) {
            this.colorType = colorType;
        }
        
        @Override
        public ImageInfo.ColorType getColorType() {
            return this.colorType;
        }
        
        @Override
        public int getRGB(final InputStream is) throws IOException {
            int sample = FileInfo.readSample(is, PamFileInfo.this.bytesPerSample);
            sample = FileInfo.scaleSample(sample, PamFileInfo.this.scale, PamFileInfo.this.maxval);
            int alpha = 255;
            if (PamFileInfo.this.hasAlpha) {
                alpha = FileInfo.readSample(is, PamFileInfo.this.bytesPerSample);
                alpha = FileInfo.scaleSample(alpha, PamFileInfo.this.scale, PamFileInfo.this.maxval);
            }
            return (0xFF & alpha) << 24 | (0xFF & sample) << 16 | (0xFF & sample) << 8 | (0xFF & sample) << 0;
        }
    }
    
    private class ColorTupleReader extends TupleReader
    {
        @Override
        public ImageInfo.ColorType getColorType() {
            return ImageInfo.ColorType.RGB;
        }
        
        @Override
        public int getRGB(final InputStream is) throws IOException {
            int red = FileInfo.readSample(is, PamFileInfo.this.bytesPerSample);
            int green = FileInfo.readSample(is, PamFileInfo.this.bytesPerSample);
            int blue = FileInfo.readSample(is, PamFileInfo.this.bytesPerSample);
            red = FileInfo.scaleSample(red, PamFileInfo.this.scale, PamFileInfo.this.maxval);
            green = FileInfo.scaleSample(green, PamFileInfo.this.scale, PamFileInfo.this.maxval);
            blue = FileInfo.scaleSample(blue, PamFileInfo.this.scale, PamFileInfo.this.maxval);
            int alpha = 255;
            if (PamFileInfo.this.hasAlpha) {
                alpha = FileInfo.readSample(is, PamFileInfo.this.bytesPerSample);
                alpha = FileInfo.scaleSample(alpha, PamFileInfo.this.scale, PamFileInfo.this.maxval);
            }
            return (0xFF & alpha) << 24 | (0xFF & red) << 16 | (0xFF & green) << 8 | (0xFF & blue) << 0;
        }
    }
}
