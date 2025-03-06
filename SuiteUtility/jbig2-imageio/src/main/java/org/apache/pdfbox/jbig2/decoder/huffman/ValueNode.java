// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.huffman;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

class ValueNode extends Node
{
    private int rangeLen;
    private int rangeLow;
    private boolean isLowerRange;
    
    protected ValueNode(final HuffmanTable.Code code) {
        this.rangeLen = code.rangeLength;
        this.rangeLow = code.rangeLow;
        this.isLowerRange = code.isLowerRange;
    }
    
    @Override
    protected long decode(final ImageInputStream imageInputStream) throws IOException {
        if (this.isLowerRange) {
            return this.rangeLow - imageInputStream.readBits(this.rangeLen);
        }
        return this.rangeLow + imageInputStream.readBits(this.rangeLen);
    }
    
    static String bitPattern(final int n, final int n2) {
        final char[] value = new char[n2];
        for (int i = 1; i <= n2; ++i) {
            value[i - 1] = (char)(((n >> n2 - i & 0x1) != 0x0) ? 49 : 48);
        }
        return new String(value);
    }
}
