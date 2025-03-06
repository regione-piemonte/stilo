// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import org.apache.commons.imaging.formats.png.PngText;
import java.io.IOException;
import java.util.logging.Level;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.util.logging.Logger;

public class PngChunkText extends PngTextChunk
{
    private static final Logger LOGGER;
    public final String keyword;
    public final String text;
    
    public PngChunkText(final int length, final int chunkType, final int crc, final byte[] bytes) throws ImageReadException, IOException {
        super(length, chunkType, crc, bytes);
        final int index = BinaryFunctions.findNull(bytes);
        if (index < 0) {
            throw new ImageReadException("PNG tEXt chunk keyword is not terminated.");
        }
        this.keyword = new String(bytes, 0, index, StandardCharsets.ISO_8859_1);
        final int textLength = bytes.length - (index + 1);
        this.text = new String(bytes, index + 1, textLength, StandardCharsets.ISO_8859_1);
        if (PngChunkText.LOGGER.isLoggable(Level.FINEST)) {
            PngChunkText.LOGGER.finest("Keyword: " + this.keyword);
            PngChunkText.LOGGER.finest("Text: " + this.text);
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
        return new PngText.Text(this.keyword, this.text);
    }
    
    static {
        LOGGER = Logger.getLogger(PngChunkText.class.getName());
    }
}
