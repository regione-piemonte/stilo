// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png.chunks;

import org.apache.commons.imaging.formats.png.PngText;

public abstract class PngTextChunk extends PngChunk
{
    public PngTextChunk(final int length, final int chunkType, final int crc, final byte[] bytes) {
        super(length, chunkType, crc, bytes);
    }
    
    public abstract String getKeyword();
    
    public abstract String getText();
    
    public abstract PngText getContents();
}
