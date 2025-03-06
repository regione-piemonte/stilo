// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.arithmetic;

import java.io.IOException;

public class ArithmeticIntegerDecoder
{
    private final ArithmeticDecoder decoder;
    private int prev;
    
    public ArithmeticIntegerDecoder(final ArithmeticDecoder decoder) {
        this.decoder = decoder;
    }
    
    public long decode(CX cx) throws IOException {
        int n = 0;
        if (cx == null) {
            cx = new CX(512, 1);
        }
        cx.setIndex(this.prev = 1);
        final int decode = this.decoder.decode(cx);
        this.setPrev(decode);
        cx.setIndex(this.prev);
        final int decode2 = this.decoder.decode(cx);
        this.setPrev(decode2);
        int n2;
        int n3;
        if (decode2 == 1) {
            cx.setIndex(this.prev);
            final int decode3 = this.decoder.decode(cx);
            this.setPrev(decode3);
            if (decode3 == 1) {
                cx.setIndex(this.prev);
                final int decode4 = this.decoder.decode(cx);
                this.setPrev(decode4);
                if (decode4 == 1) {
                    cx.setIndex(this.prev);
                    final int decode5 = this.decoder.decode(cx);
                    this.setPrev(decode5);
                    if (decode5 == 1) {
                        cx.setIndex(this.prev);
                        final int decode6 = this.decoder.decode(cx);
                        this.setPrev(decode6);
                        if (decode6 == 1) {
                            n2 = 32;
                            n3 = 4436;
                        }
                        else {
                            n2 = 12;
                            n3 = 340;
                        }
                    }
                    else {
                        n2 = 8;
                        n3 = 84;
                    }
                }
                else {
                    n2 = 6;
                    n3 = 20;
                }
            }
            else {
                n2 = 4;
                n3 = 4;
            }
        }
        else {
            n2 = 2;
            n3 = 0;
        }
        for (int i = 0; i < n2; ++i) {
            cx.setIndex(this.prev);
            final int decode7 = this.decoder.decode(cx);
            this.setPrev(decode7);
            n = (n << 1 | decode7);
        }
        final int n4 = n + n3;
        if (decode == 0) {
            return n4;
        }
        if (decode == 1 && n4 > 0) {
            return -n4;
        }
        return Long.MAX_VALUE;
    }
    
    private void setPrev(final int n) {
        if (this.prev < 256) {
            this.prev = ((this.prev << 1 | n) & 0x1FF);
        }
        else {
            this.prev = ((((this.prev << 1 | n) & 0x1FF) | 0x100) & 0x1FF);
        }
    }
    
    public int decodeIAID(final CX cx, final long n) throws IOException {
        this.prev = 1;
        for (int n2 = 0; n2 < n; ++n2) {
            cx.setIndex(this.prev);
            this.prev = (this.prev << 1 | this.decoder.decode(cx));
        }
        return this.prev - (1 << (int)n);
    }
}
