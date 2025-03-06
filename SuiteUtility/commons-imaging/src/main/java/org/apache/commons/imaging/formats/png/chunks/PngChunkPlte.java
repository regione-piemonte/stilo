// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import org.apache.commons.imaging.formats.png.GammaCorrection;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.ImageReadException;
import java.io.ByteArrayInputStream;

public class PngChunkPlte extends PngChunk
{
    private final int[] rgb;
    
    public PngChunkPlte(final int length, final int chunkType, final int crc, final byte[] bytes) throws ImageReadException, IOException {
        super(length, chunkType, crc, bytes);
        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        if (length % 3 != 0) {
            throw new ImageReadException("PLTE: wrong length: " + length);
        }
        final int count = length / 3;
        this.rgb = new int[count];
        for (int i = 0; i < count; ++i) {
            final int red = BinaryFunctions.readByte("red[" + i + "]", is, "Not a Valid Png File: PLTE Corrupt");
            final int green = BinaryFunctions.readByte("green[" + i + "]", is, "Not a Valid Png File: PLTE Corrupt");
            final int blue = BinaryFunctions.readByte("blue[" + i + "]", is, "Not a Valid Png File: PLTE Corrupt");
            this.rgb[i] = (0xFF000000 | (0xFF & red) << 16 | (0xFF & green) << 8 | (0xFF & blue) << 0);
        }
    }
    
    public int[] getRgb() {
        return this.rgb;
    }
    
    public int getRGB(final int index) throws ImageReadException {
        if (index < 0 || index >= this.rgb.length) {
            throw new ImageReadException("PNG: unknown Palette reference: " + index);
        }
        return this.rgb[index];
    }
    
    public void correct(final GammaCorrection gammaCorrection) {
        for (int i = 0; i < this.rgb.length; ++i) {
            this.rgb[i] = gammaCorrection.correctARGB(this.rgb[i]);
        }
    }
}
