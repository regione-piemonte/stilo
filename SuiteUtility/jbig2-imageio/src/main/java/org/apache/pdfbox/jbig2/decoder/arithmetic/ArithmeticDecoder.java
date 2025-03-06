// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.arithmetic;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

public class ArithmeticDecoder
{
    private static final int[][] QE;
    private int a;
    private long c;
    private int ct;
    private int b;
    private long streamPos0;
    private final ImageInputStream iis;
    
    public ArithmeticDecoder(final ImageInputStream iis) throws IOException {
        this.iis = iis;
        this.init();
    }
    
    private void init() throws IOException {
        this.streamPos0 = this.iis.getStreamPosition();
        this.b = this.iis.read();
        this.c = this.b << 16;
        this.byteIn();
        this.c <<= 7;
        this.ct -= 7;
        this.a = 32768;
    }
    
    public int decode(final CX cx) throws IOException {
        final int n = ArithmeticDecoder.QE[cx.cx()][0];
        final int cx2 = cx.cx();
        this.a -= n;
        int n2;
        if (this.c >> 16 < n) {
            n2 = this.lpsExchange(cx, cx2, n);
            this.renormalize();
        }
        else {
            this.c -= n << 16;
            if ((this.a & 0x8000) != 0x0) {
                return cx.mps();
            }
            n2 = this.mpsExchange(cx, cx2);
            this.renormalize();
        }
        return n2;
    }
    
    private void byteIn() throws IOException {
        if (this.iis.getStreamPosition() > this.streamPos0) {
            this.iis.seek(this.iis.getStreamPosition() - 1L);
        }
        this.b = this.iis.read();
        if (this.b == 255) {
            final int read = this.iis.read();
            if (read > 143) {
                this.c += 65280L;
                this.ct = 8;
                this.iis.seek(this.iis.getStreamPosition() - 2L);
            }
            else {
                this.c += read << 9;
                this.ct = 7;
            }
        }
        else {
            this.b = this.iis.read();
            this.c += this.b << 8;
            this.ct = 8;
        }
        this.c &= 0xFFFFFFFFL;
    }
    
    private void renormalize() throws IOException {
        do {
            if (this.ct == 0) {
                this.byteIn();
            }
            this.a <<= 1;
            this.c <<= 1;
            --this.ct;
        } while ((this.a & 0x8000) == 0x0);
        this.c &= 0xFFFFFFFFL;
    }
    
    private int mpsExchange(final CX cx, final int n) {
        final byte mps = cx.mps();
        if (this.a < ArithmeticDecoder.QE[n][0]) {
            if (ArithmeticDecoder.QE[n][3] == 1) {
                cx.toggleMps();
            }
            cx.setCx(ArithmeticDecoder.QE[n][2]);
            return 1 - mps;
        }
        cx.setCx(ArithmeticDecoder.QE[n][1]);
        return mps;
    }
    
    private int lpsExchange(final CX cx, final int n, final int n2) {
        final byte mps = cx.mps();
        if (this.a < n2) {
            cx.setCx(ArithmeticDecoder.QE[n][1]);
            this.a = n2;
            return mps;
        }
        if (ArithmeticDecoder.QE[n][3] == 1) {
            cx.toggleMps();
        }
        cx.setCx(ArithmeticDecoder.QE[n][2]);
        this.a = n2;
        return 1 - mps;
    }
    
    int getA() {
        return this.a;
    }
    
    long getC() {
        return this.c;
    }
    
    static {
        QE = new int[][] { { 22017, 1, 1, 1 }, { 13313, 2, 6, 0 }, { 6145, 3, 9, 0 }, { 2753, 4, 12, 0 }, { 1313, 5, 29, 0 }, { 545, 38, 33, 0 }, { 22017, 7, 6, 1 }, { 21505, 8, 14, 0 }, { 18433, 9, 14, 0 }, { 14337, 10, 14, 0 }, { 12289, 11, 17, 0 }, { 9217, 12, 18, 0 }, { 7169, 13, 20, 0 }, { 5633, 29, 21, 0 }, { 22017, 15, 14, 1 }, { 21505, 16, 14, 0 }, { 20737, 17, 15, 0 }, { 18433, 18, 16, 0 }, { 14337, 19, 17, 0 }, { 13313, 20, 18, 0 }, { 12289, 21, 19, 0 }, { 10241, 22, 19, 0 }, { 9217, 23, 20, 0 }, { 8705, 24, 21, 0 }, { 7169, 25, 22, 0 }, { 6145, 26, 23, 0 }, { 5633, 27, 24, 0 }, { 5121, 28, 25, 0 }, { 4609, 29, 26, 0 }, { 4353, 30, 27, 0 }, { 2753, 31, 28, 0 }, { 2497, 32, 29, 0 }, { 2209, 33, 30, 0 }, { 1313, 34, 31, 0 }, { 1089, 35, 32, 0 }, { 673, 36, 33, 0 }, { 545, 37, 34, 0 }, { 321, 38, 35, 0 }, { 273, 39, 36, 0 }, { 133, 40, 37, 0 }, { 73, 41, 38, 0 }, { 37, 42, 39, 0 }, { 21, 43, 40, 0 }, { 9, 44, 41, 0 }, { 5, 45, 42, 0 }, { 1, 45, 43, 0 }, { 22017, 46, 46, 0 } };
    }
}
