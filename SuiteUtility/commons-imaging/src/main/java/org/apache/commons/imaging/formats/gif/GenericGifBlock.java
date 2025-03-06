// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.gif;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

class GenericGifBlock extends GifBlock
{
    final List<byte[]> subblocks;
    
    GenericGifBlock(final int blockCode, final List<byte[]> subblocks) {
        super(blockCode);
        this.subblocks = subblocks;
    }
    
    public byte[] appendSubBlocks() throws IOException {
        return this.appendSubBlocks(false);
    }
    
    public byte[] appendSubBlocks(final boolean includeLengths) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < this.subblocks.size(); ++i) {
            final byte[] subblock = this.subblocks.get(i);
            if (includeLengths && i > 0) {
                out.write(subblock.length);
            }
            out.write(subblock);
        }
        return out.toByteArray();
    }
}
