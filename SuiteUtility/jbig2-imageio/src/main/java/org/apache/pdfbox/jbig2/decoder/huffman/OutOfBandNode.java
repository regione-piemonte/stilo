// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.huffman;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

class OutOfBandNode extends Node
{
    protected OutOfBandNode(final HuffmanTable.Code code) {
    }
    
    @Override
    protected long decode(final ImageInputStream imageInputStream) throws IOException {
        return Long.MAX_VALUE;
    }
}
