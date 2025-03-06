// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.ByteArrayInputStream;

public class PngChunkGama extends PngChunk
{
    public final int gamma;
    
    public PngChunkGama(final int length, final int chunkType, final int crc, final byte[] bytes) throws IOException {
        super(length, chunkType, crc, bytes);
        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        this.gamma = BinaryFunctions.read4Bytes("Gamma", is, "Not a Valid Png File: gAMA Corrupt", this.getByteOrder());
    }
    
    public double getGamma() {
        return 1.0 / (this.gamma / 100000.0);
    }
}
