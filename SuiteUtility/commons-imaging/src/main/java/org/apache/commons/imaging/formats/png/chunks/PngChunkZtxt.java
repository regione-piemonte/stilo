// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import org.apache.commons.imaging.formats.png.PngText;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;

public class PngChunkZtxt extends PngTextChunk
{
    public final String keyword;
    public final String text;
    
    public PngChunkZtxt(final int length, final int chunkType, final int crc, final byte[] bytes) throws ImageReadException, IOException {
        super(length, chunkType, crc, bytes);
        int index = BinaryFunctions.findNull(bytes);
        if (index < 0) {
            throw new ImageReadException("PNG zTXt chunk keyword is unterminated.");
        }
        this.keyword = new String(bytes, 0, index, StandardCharsets.ISO_8859_1);
        ++index;
        final int compressionMethod = bytes[index++];
        if (compressionMethod != 0) {
            throw new ImageReadException("PNG zTXt chunk has unexpected compression method: " + compressionMethod);
        }
        final int compressedTextLength = bytes.length - index;
        final byte[] compressedText = new byte[compressedTextLength];
        System.arraycopy(bytes, index, compressedText, 0, compressedTextLength);
        this.text = new String(BinaryFunctions.getStreamBytes(new InflaterInputStream(new ByteArrayInputStream(compressedText))), StandardCharsets.ISO_8859_1);
    }
    
    @Override
    public String getKeyword() {
        return this.keyword;
    }
    
    @Override
    public String getText() {
        return this.text;
    }
    
    @Override
    public PngText getContents() {
        return new PngText.Ztxt(this.keyword, this.text);
    }
}
