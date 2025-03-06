// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pnm;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;

class PbmFileInfo extends FileInfo
{
    private int bitcache;
    private int bitsInCache;
    
    PbmFileInfo(final int width, final int height, final boolean rawbits) {
        super(width, height, rawbits);
    }
    
    public boolean hasAlpha() {
        return false;
    }
    
    public int getNumComponents() {
        return 1;
    }
    
    public int getBitDepth() {
        return 1;
    }
    
    public ImageFormat getImageType() {
        return ImageFormats.PBM;
    }
    
    public ImageInfo.ColorType getColorType() {
        return ImageInfo.ColorType.BW;
    }
    
    public String getImageTypeDescription() {
        return "PBM: portable bitmap fileformat";
    }
    
    public String getMIMEType() {
        return "image/x-portable-bitmap";
    }
    
    protected void newline() {
        this.bitcache = 0;
        this.bitsInCache = 0;
    }
    
    public int getRGB(final InputStream is) throws IOException {
        if (this.bitsInCache < 1) {
            final int bits = is.read();
            if (bits < 0) {
                throw new IOException("PBM: Unexpected EOF");
            }
            this.bitcache = (0xFF & bits);
            this.bitsInCache += 8;
        }
        final int bit = 0x1 & this.bitcache >> 7;
        this.bitcache <<= 1;
        --this.bitsInCache;
        if (bit == 0) {
            return -1;
        }
        if (bit == 1) {
            return -16777216;
        }
        throw new IOException("PBM: bad bit: " + bit);
    }
    
    public int getRGB(final WhiteSpaceReader wsr) throws IOException {
        final int bit = Integer.parseInt(wsr.readtoWhiteSpace());
        if (bit == 0) {
            return -16777216;
        }
        if (bit == 1) {
            return -1;
        }
        throw new IOException("PBM: bad bit: " + bit);
    }
}
