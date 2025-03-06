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

public class PngChunkItxt extends PngTextChunk
{
    public final String keyword;
    public final String text;
    public final String languageTag;
    public final String translatedKeyword;
    
    public PngChunkItxt(final int length, final int chunkType, final int crc, final byte[] bytes) throws ImageReadException, IOException {
        super(length, chunkType, crc, bytes);
        int terminator = BinaryFunctions.findNull(bytes);
        if (terminator < 0) {
            throw new ImageReadException("PNG iTXt chunk keyword is not terminated.");
        }
        this.keyword = new String(bytes, 0, terminator, StandardCharsets.ISO_8859_1);
        int index = terminator + 1;
        final int compressionFlag = bytes[index++];
        if (compressionFlag != 0 && compressionFlag != 1) {
            throw new ImageReadException("PNG iTXt chunk has invalid compression flag: " + compressionFlag);
        }
        final boolean compressed = compressionFlag == 1;
        final int compressionMethod = bytes[index++];
        if (compressed && compressionMethod != 0) {
            throw new ImageReadException("PNG iTXt chunk has unexpected compression method: " + compressionMethod);
        }
        terminator = BinaryFunctions.findNull(bytes, index);
        if (terminator < 0) {
            throw new ImageReadException("PNG iTXt chunk language tag is not terminated.");
        }
        this.languageTag = new String(bytes, index, terminator - index, StandardCharsets.ISO_8859_1);
        index = terminator + 1;
        terminator = BinaryFunctions.findNull(bytes, index);
        if (terminator < 0) {
            throw new ImageReadException("PNG iTXt chunk translated keyword is not terminated.");
        }
        this.translatedKeyword = new String(bytes, index, terminator - index, StandardCharsets.UTF_8);
        index = terminator + 1;
        if (compressed) {
            final int compressedTextLength = bytes.length - index;
            final byte[] compressedText = new byte[compressedTextLength];
            System.arraycopy(bytes, index, compressedText, 0, compressedTextLength);
            this.text = new String(BinaryFunctions.getStreamBytes(new InflaterInputStream(new ByteArrayInputStream(compressedText))), StandardCharsets.UTF_8);
        }
        else {
            this.text = new String(bytes, index, bytes.length - index, StandardCharsets.UTF_8);
        }
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
        return new PngText.Itxt(this.keyword, this.text, this.languageTag, this.translatedKeyword);
    }
}
