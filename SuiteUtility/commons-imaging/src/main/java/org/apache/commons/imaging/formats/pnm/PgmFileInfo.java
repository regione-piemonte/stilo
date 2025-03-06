// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pnm;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageReadException;

class PgmFileInfo extends FileInfo
{
    private final int max;
    private final float scale;
    private final int bytesPerSample;
    
    PgmFileInfo(final int width, final int height, final boolean rawbits, final int max) throws ImageReadException {
        super(width, height, rawbits);
        if (max <= 0) {
            throw new ImageReadException("PGM maxVal " + max + " is out of range [1;65535]");
        }
        if (max <= 255) {
            this.scale = 255.0f;
            this.bytesPerSample = 1;
        }
        else {
            if (max > 65535) {
                throw new ImageReadException("PGM maxVal " + max + " is out of range [1;65535]");
            }
            this.scale = 65535.0f;
            this.bytesPerSample = 2;
        }
        this.max = max;
    }
    
    public boolean hasAlpha() {
        return false;
    }
    
    public int getNumComponents() {
        return 1;
    }
    
    public int getBitDepth() {
        return this.max;
    }
    
    public ImageFormat getImageType() {
        return ImageFormats.PGM;
    }
    
    public String getImageTypeDescription() {
        return "PGM: portable graymap file format";
    }
    
    public String getMIMEType() {
        return "image/x-portable-graymap";
    }
    
    public ImageInfo.ColorType getColorType() {
        return ImageInfo.ColorType.GRAYSCALE;
    }
    
    public int getRGB(final InputStream is) throws IOException {
        int sample = FileInfo.readSample(is, this.bytesPerSample);
        sample = FileInfo.scaleSample(sample, this.scale, this.max);
        final int alpha = 255;
        return 0xFF000000 | (0xFF & sample) << 16 | (0xFF & sample) << 8 | (0xFF & sample) << 0;
    }
    
    public int getRGB(final WhiteSpaceReader wsr) throws IOException {
        int sample = Integer.parseInt(wsr.readtoWhiteSpace());
        sample = FileInfo.scaleSample(sample, this.scale, this.max);
        final int alpha = 255;
        return 0xFF000000 | (0xFF & sample) << 16 | (0xFF & sample) << 8 | (0xFF & sample) << 0;
    }
}
