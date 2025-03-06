// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.ImageReadException;

public class PngChunkScal extends PngChunk
{
    public final double unitsPerPixelXAxis;
    public final double unitsPerPixelYAxis;
    public final int unitSpecifier;
    
    public PngChunkScal(final int length, final int chunkType, final int crc, final byte[] bytes) throws ImageReadException, IOException {
        super(length, chunkType, crc, bytes);
        this.unitSpecifier = bytes[0];
        if (this.unitSpecifier != 1 && this.unitSpecifier != 2) {
            throw new ImageReadException("PNG sCAL invalid unit specifier: " + this.unitSpecifier);
        }
        final int separator = BinaryFunctions.findNull(bytes);
        if (separator < 0) {
            throw new ImageReadException("PNG sCAL x and y axis value separator not found.");
        }
        final int xIndex = 1;
        final String xStr = new String(bytes, 1, separator - 1, StandardCharsets.ISO_8859_1);
        this.unitsPerPixelXAxis = this.toDouble(xStr);
        final int yIndex = separator + 1;
        if (yIndex >= length) {
            throw new ImageReadException("PNG sCAL chunk missing the y axis value.");
        }
        final String yStr = new String(bytes, yIndex, length - yIndex, StandardCharsets.ISO_8859_1);
        this.unitsPerPixelYAxis = this.toDouble(yStr);
    }
    
    private double toDouble(final String str) throws ImageReadException {
        try {
            return Double.valueOf(str);
        }
        catch (NumberFormatException e) {
            throw new ImageReadException("PNG sCAL error reading axis value - " + str);
        }
    }
}
