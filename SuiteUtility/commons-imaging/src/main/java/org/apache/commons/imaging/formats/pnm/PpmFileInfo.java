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

class PpmFileInfo extends FileInfo
{
    private final int max;
    private final float scale;
    private final int bytesPerSample;
    
    PpmFileInfo(final int width, final int height, final boolean rawbits, final int max) throws ImageReadException {
        super(width, height, rawbits);
        if (max <= 0) {
            throw new ImageReadException("PPM maxVal " + max + " is out of range [1;65535]");
        }
        if (max <= 255) {
            this.scale = 255.0f;
            this.bytesPerSample = 1;
        }
        else {
            if (max > 65535) {
                throw new ImageReadException("PPM maxVal " + max + " is out of range [1;65535]");
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
        return 3;
    }
    
    public int getBitDepth() {
        return this.max;
    }
    
    public ImageFormat getImageType() {
        return ImageFormats.PPM;
    }
    
    public String getImageTypeDescription() {
        return "PPM: portable pixmap file format";
    }
    
    public String getMIMEType() {
        return "image/x-portable-pixmap";
    }
    
    public ImageInfo.ColorType getColorType() {
        return ImageInfo.ColorType.RGB;
    }
    
    public int getRGB(final InputStream is) throws IOException {
        int red = FileInfo.readSample(is, this.bytesPerSample);
        int green = FileInfo.readSample(is, this.bytesPerSample);
        int blue = FileInfo.readSample(is, this.bytesPerSample);
        red = FileInfo.scaleSample(red, this.scale, this.max);
        green = FileInfo.scaleSample(green, this.scale, this.max);
        blue = FileInfo.scaleSample(blue, this.scale, this.max);
        final int alpha = 255;
        return 0xFF000000 | (0xFF & red) << 16 | (0xFF & green) << 8 | (0xFF & blue) << 0;
    }
    
    public int getRGB(final WhiteSpaceReader wsr) throws IOException {
        int red = Integer.parseInt(wsr.readtoWhiteSpace());
        int green = Integer.parseInt(wsr.readtoWhiteSpace());
        int blue = Integer.parseInt(wsr.readtoWhiteSpace());
        red = FileInfo.scaleSample(red, this.scale, this.max);
        green = FileInfo.scaleSample(green, this.scale, this.max);
        blue = FileInfo.scaleSample(blue, this.scale, this.max);
        final int alpha = 255;
        return 0xFF000000 | (0xFF & red) << 16 | (0xFF & green) << 8 | (0xFF & blue) << 0;
    }
}
