// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.huffman;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

class InternalNode extends Node
{
    private final int depth;
    private Node zero;
    private Node one;
    
    protected InternalNode() {
        this.depth = 0;
    }
    
    protected InternalNode(final int depth) {
        this.depth = depth;
    }
    
    protected void append(final HuffmanTable.Code code) {
        if (code.prefixLength == 0) {
            return;
        }
        final int n = code.prefixLength - 1 - this.depth;
        if (n < 0) {
            throw new IllegalArgumentException("Negative shifting is not possible.");
        }
        final int n2 = code.code >> n & 0x1;
        if (n == 0) {
            if (code.rangeLength == -1) {
                if (n2 == 1) {
                    if (this.one != null) {
                        throw new IllegalStateException("already have a OOB for " + code);
                    }
                    this.one = new OutOfBandNode(code);
                }
                else {
                    if (this.zero != null) {
                        throw new IllegalStateException("already have a OOB for " + code);
                    }
                    this.zero = new OutOfBandNode(code);
                }
            }
            else if (n2 == 1) {
                if (this.one != null) {
                    throw new IllegalStateException("already have a ValueNode for " + code);
                }
                this.one = new ValueNode(code);
            }
            else {
                if (this.zero != null) {
                    throw new IllegalStateException("already have a ValueNode for " + code);
                }
                this.zero = new ValueNode(code);
            }
        }
        else if (n2 == 1) {
            if (this.one == null) {
                this.one = new InternalNode(this.depth + 1);
            }
            ((InternalNode)this.one).append(code);
        }
        else {
            if (this.zero == null) {
                this.zero = new InternalNode(this.depth + 1);
            }
            ((InternalNode)this.zero).append(code);
        }
    }
    
    @Override
    protected long decode(final ImageInputStream imageInputStream) throws IOException {
        return ((imageInputStream.readBit() == 0) ? this.zero : this.one).decode(imageInputStream);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\n");
        this.pad(sb);
        sb.append("0: ").append(this.zero).append("\n");
        this.pad(sb);
        sb.append("1: ").append(this.one).append("\n");
        return sb.toString();
    }
    
    private void pad(final StringBuilder sb) {
        for (int i = 0; i < this.depth; ++i) {
            sb.append("   ");
        }
    }
}
