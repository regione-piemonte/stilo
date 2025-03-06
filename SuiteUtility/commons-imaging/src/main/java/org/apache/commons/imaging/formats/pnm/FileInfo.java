// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pnm;

import org.apache.commons.imaging.common.ImageBuilder;
import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageFormat;

abstract class FileInfo
{
    final int width;
    final int height;
    final boolean rawbits;
    
    FileInfo(final int width, final int height, final boolean rawbits) {
        this.width = width;
        this.height = height;
        this.rawbits = rawbits;
    }
    
    abstract boolean hasAlpha();
    
    abstract int getNumComponents();
    
    abstract int getBitDepth();
    
    abstract ImageFormat getImageType();
    
    abstract String getImageTypeDescription();
    
    abstract String getMIMEType();
    
    abstract ImageInfo.ColorType getColorType();
    
    abstract int getRGB(final WhiteSpaceReader p0) throws IOException;
    
    abstract int getRGB(final InputStream p0) throws IOException;
    
    void newline() {
    }
    
    static int readSample(final InputStream is, final int bytesPerSample) throws IOException {
        int sample = 0;
        for (int i = 0; i < bytesPerSample; ++i) {
            final int nextByte = is.read();
            if (nextByte < 0) {
                throw new IOException("PNM: Unexpected EOF");
            }
            sample <<= 8;
            sample |= nextByte;
        }
        return sample;
    }
    
    static int scaleSample(int sample, final float scale, final int max) throws IOException {
        if (sample < 0) {
            throw new IOException("Negative pixel values are invalid in PNM files");
        }
        if (sample > max) {
            sample = 0;
        }
        return (int)(sample * scale / max + 0.5f);
    }
    
    void readImage(final ImageBuilder imageBuilder, final InputStream is) throws IOException {
        if (!this.rawbits) {
            final WhiteSpaceReader wsr = new WhiteSpaceReader(is);
            for (int y = 0; y < this.height; ++y) {
                for (int x = 0; x < this.width; ++x) {
                    final int rgb = this.getRGB(wsr);
                    imageBuilder.setRGB(x, y, rgb);
                }
                this.newline();
            }
        }
        else {
            for (int y2 = 0; y2 < this.height; ++y2) {
                for (int x2 = 0; x2 < this.width; ++x2) {
                    final int rgb2 = this.getRGB(is);
                    imageBuilder.setRGB(x2, y2, rgb2);
                }
                this.newline();
            }
        }
    }
}
