// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.ByteArrayInputStream;

public class PngChunkPhys extends PngChunk
{
    public final int pixelsPerUnitXAxis;
    public final int pixelsPerUnitYAxis;
    public final int unitSpecifier;
    
    public PngChunkPhys(final int length, final int chunkType, final int crc, final byte[] bytes) throws IOException {
        super(length, chunkType, crc, bytes);
        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        this.pixelsPerUnitXAxis = BinaryFunctions.read4Bytes("PixelsPerUnitXAxis", is, "Not a Valid Png File: pHYs Corrupt", this.getByteOrder());
        this.pixelsPerUnitYAxis = BinaryFunctions.read4Bytes("PixelsPerUnitYAxis", is, "Not a Valid Png File: pHYs Corrupt", this.getByteOrder());
        this.unitSpecifier = BinaryFunctions.readByte("Unit specifier", is, "Not a Valid Png File: pHYs Corrupt");
    }
}
