// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

class TIFFFaxDecoder
{
    private int bitPointer;
    private int bytePointer;
    private byte[] data;
    private int w;
    private int h;
    private int fillOrder;
    private int changingElemSize;
    private int[] prevChangingElems;
    private int[] currChangingElems;
    private int lastChangingElement;
    private int compression;
    private int uncompressedMode;
    private int fillBits;
    private int oneD;
    static int[] table1;
    static int[] table2;
    static byte[] flipTable;
    static short[] white;
    static short[] additionalMakeup;
    static short[] initBlack;
    static short[] twoBitBlack;
    static short[] black;
    static byte[] twoDCodes;
    
    public TIFFFaxDecoder(final int fillOrder, final int w, final int h) {
        this.changingElemSize = 0;
        this.lastChangingElement = 0;
        this.compression = 2;
        this.uncompressedMode = 0;
        this.fillBits = 0;
        this.fillOrder = fillOrder;
        this.w = w;
        this.h = h;
        this.bitPointer = 0;
        this.bytePointer = 0;
        this.prevChangingElems = new int[w];
        this.currChangingElems = new int[w];
    }
    
    public void decode1D(final byte[] array, final byte[] data, final int n, final int n2) {
        this.data = data;
        int n3 = 0;
        final int n4 = (this.w + 7) / 8;
        this.bitPointer = 0;
        this.bytePointer = 0;
        for (int i = 0; i < n2; ++i) {
            this.decodeNextScanline(array, n3, n);
            n3 += n4;
        }
    }
    
    public void decodeNextScanline(final byte[] array, final int n, int i) {
        int j = 1;
        this.changingElemSize = 0;
        while (i < this.w) {
            while (j != 0) {
                final int nextNBits = this.nextNBits(10);
                final short n2 = TIFFFaxDecoder.white[nextNBits];
                final int n3 = n2 & 0x1;
                final int n4 = n2 >>> 1 & 0xF;
                if (n4 == 12) {
                    final short n5 = TIFFFaxDecoder.additionalMakeup[(nextNBits << 2 & 0xC) | this.nextLesserThan8Bits(2)];
                    final int n6 = n5 >>> 1 & 0x7;
                    i += (n5 >>> 4 & 0xFFF);
                    this.updatePointer(4 - n6);
                }
                else {
                    if (n4 == 0) {
                        throw new Error("TIFFFaxDecoder0");
                    }
                    if (n4 == 15) {
                        throw new Error("TIFFFaxDecoder1");
                    }
                    i += (n2 >>> 5 & 0x7FF);
                    this.updatePointer(10 - n4);
                    if (n3 != 0) {
                        continue;
                    }
                    j = 0;
                    this.currChangingElems[this.changingElemSize++] = i;
                }
            }
            if (i == this.w) {
                if (this.compression == 2) {
                    this.advancePointer();
                    break;
                }
                break;
            }
            else {
                while (j == 0) {
                    final short n7 = TIFFFaxDecoder.initBlack[this.nextLesserThan8Bits(4)];
                    final int n8 = n7 >>> 1 & 0xF;
                    final int n9 = n7 >>> 5 & 0x7FF;
                    if (n9 == 100) {
                        final short n10 = TIFFFaxDecoder.black[this.nextNBits(9)];
                        final int n11 = n10 & 0x1;
                        final int n12 = n10 >>> 1 & 0xF;
                        final int n13 = n10 >>> 5 & 0x7FF;
                        if (n12 == 12) {
                            this.updatePointer(5);
                            final short n14 = TIFFFaxDecoder.additionalMakeup[this.nextLesserThan8Bits(4)];
                            final int n15 = n14 >>> 1 & 0x7;
                            final int n16 = n14 >>> 4 & 0xFFF;
                            this.setToBlack(array, n, i, n16);
                            i += n16;
                            this.updatePointer(4 - n15);
                        }
                        else {
                            if (n12 == 15) {
                                throw new Error("TIFFFaxDecoder2");
                            }
                            this.setToBlack(array, n, i, n13);
                            i += n13;
                            this.updatePointer(9 - n12);
                            if (n11 != 0) {
                                continue;
                            }
                            j = 1;
                            this.currChangingElems[this.changingElemSize++] = i;
                        }
                    }
                    else if (n9 == 200) {
                        final short n17 = TIFFFaxDecoder.twoBitBlack[this.nextLesserThan8Bits(2)];
                        final int n18 = n17 >>> 5 & 0x7FF;
                        final int n19 = n17 >>> 1 & 0xF;
                        this.setToBlack(array, n, i, n18);
                        i += n18;
                        this.updatePointer(2 - n19);
                        j = 1;
                        this.currChangingElems[this.changingElemSize++] = i;
                    }
                    else {
                        this.setToBlack(array, n, i, n9);
                        i += n9;
                        this.updatePointer(4 - n8);
                        j = 1;
                        this.currChangingElems[this.changingElemSize++] = i;
                    }
                }
                if (i != this.w) {
                    continue;
                }
                if (this.compression == 2) {
                    this.advancePointer();
                    break;
                }
                break;
            }
        }
        this.currChangingElems[this.changingElemSize++] = i;
    }
    
    public void decode2D(final byte[] array, final byte[] data, final int n, final int n2, final long n3) {
        this.data = data;
        this.compression = 3;
        this.bitPointer = 0;
        this.bytePointer = 0;
        final int n4 = (this.w + 7) / 8;
        final int[] array2 = new int[2];
        this.oneD = (int)(n3 & 0x1L);
        this.uncompressedMode = (int)((n3 & 0x2L) >> 1);
        this.fillBits = (int)((n3 & 0x4L) >> 2);
        if (this.readEOL() != 1) {
            throw new Error("TIFFFaxDecoder3");
        }
        final int n5 = 0;
        this.decodeNextScanline(array, n5, n);
        int n6 = n5 + n4;
        for (int i = 1; i < n2; ++i) {
            if (this.readEOL() == 0) {
                final int[] prevChangingElems = this.prevChangingElems;
                this.prevChangingElems = this.currChangingElems;
                this.currChangingElems = prevChangingElems;
                int changingElemSize = 0;
                int n7 = -1;
                boolean b = true;
                int j = n;
                this.lastChangingElement = 0;
                while (j < this.w) {
                    this.getNextChangingElement(n7, b, array2);
                    final int n8 = array2[0];
                    final int n9 = array2[1];
                    final int n10 = TIFFFaxDecoder.twoDCodes[this.nextLesserThan8Bits(7)] & 0xFF;
                    final int n11 = (n10 & 0x78) >>> 3;
                    final int n12 = n10 & 0x7;
                    if (n11 == 0) {
                        if (!b) {
                            this.setToBlack(array, n6, j, n9 - j);
                        }
                        n7 = (j = n9);
                        this.updatePointer(7 - n12);
                    }
                    else if (n11 == 1) {
                        this.updatePointer(7 - n12);
                        if (b) {
                            final int n13 = j + this.decodeWhiteCodeWord();
                            this.currChangingElems[changingElemSize++] = n13;
                            final int decodeBlackCodeWord = this.decodeBlackCodeWord();
                            this.setToBlack(array, n6, n13, decodeBlackCodeWord);
                            j = n13 + decodeBlackCodeWord;
                            this.currChangingElems[changingElemSize++] = j;
                        }
                        else {
                            final int decodeBlackCodeWord2 = this.decodeBlackCodeWord();
                            this.setToBlack(array, n6, j, decodeBlackCodeWord2);
                            final int n14 = j + decodeBlackCodeWord2;
                            this.currChangingElems[changingElemSize++] = n14;
                            j = n14 + this.decodeWhiteCodeWord();
                            this.currChangingElems[changingElemSize++] = j;
                        }
                        n7 = j;
                    }
                    else {
                        if (n11 > 8) {
                            throw new Error("TIFFFaxDecoder4");
                        }
                        final int n15 = n8 + (n11 - 5);
                        this.currChangingElems[changingElemSize++] = n15;
                        if (!b) {
                            this.setToBlack(array, n6, j, n15 - j);
                        }
                        n7 = (j = n15);
                        b = !b;
                        this.updatePointer(7 - n12);
                    }
                }
                this.currChangingElems[changingElemSize++] = j;
                this.changingElemSize = changingElemSize;
            }
            else {
                this.decodeNextScanline(array, n6, n);
            }
            n6 += n4;
        }
    }
    
    public synchronized void decodeT6(final byte[] array, final byte[] data, final int n, final int n2, final long n3) {
        this.data = data;
        this.compression = 4;
        this.bitPointer = 0;
        this.bytePointer = 0;
        final int n4 = (this.w + 7) / 8;
        final int[] array2 = new int[2];
        this.uncompressedMode = (int)((n3 & 0x2L) >> 1);
        final int[] currChangingElems = this.currChangingElems;
        this.changingElemSize = 0;
        currChangingElems[this.changingElemSize++] = this.w;
        currChangingElems[this.changingElemSize++] = this.w;
        int n5 = 0;
        for (int i = 0; i < n2; ++i) {
            int n6 = -1;
            boolean b = true;
            final int[] prevChangingElems = this.prevChangingElems;
            this.prevChangingElems = this.currChangingElems;
            final int[] currChangingElems2 = prevChangingElems;
            this.currChangingElems = currChangingElems2;
            final int[] array3 = currChangingElems2;
            int changingElemSize = 0;
            int j = n;
            this.lastChangingElement = 0;
            while (j < this.w) {
                this.getNextChangingElement(n6, b, array2);
                final int n7 = array2[0];
                final int n8 = array2[1];
                final int n9 = TIFFFaxDecoder.twoDCodes[this.nextLesserThan8Bits(7)] & 0xFF;
                final int n10 = (n9 & 0x78) >>> 3;
                final int n11 = n9 & 0x7;
                if (n10 == 0) {
                    if (!b) {
                        this.setToBlack(array, n5, j, n8 - j);
                    }
                    n6 = (j = n8);
                    this.updatePointer(7 - n11);
                }
                else if (n10 == 1) {
                    this.updatePointer(7 - n11);
                    if (b) {
                        final int n12 = j + this.decodeWhiteCodeWord();
                        array3[changingElemSize++] = n12;
                        final int decodeBlackCodeWord = this.decodeBlackCodeWord();
                        this.setToBlack(array, n5, n12, decodeBlackCodeWord);
                        j = n12 + decodeBlackCodeWord;
                        array3[changingElemSize++] = j;
                    }
                    else {
                        final int decodeBlackCodeWord2 = this.decodeBlackCodeWord();
                        this.setToBlack(array, n5, j, decodeBlackCodeWord2);
                        final int n13 = j + decodeBlackCodeWord2;
                        array3[changingElemSize++] = n13;
                        j = n13 + this.decodeWhiteCodeWord();
                        array3[changingElemSize++] = j;
                    }
                    n6 = j;
                }
                else if (n10 <= 8) {
                    final int n14 = n7 + (n10 - 5);
                    array3[changingElemSize++] = n14;
                    if (!b) {
                        this.setToBlack(array, n5, j, n14 - j);
                    }
                    n6 = (j = n14);
                    b = !b;
                    this.updatePointer(7 - n11);
                }
                else {
                    if (n10 != 11) {
                        throw new Error("TIFFFaxDecoder5");
                    }
                    if (this.nextLesserThan8Bits(3) != 7) {
                        throw new Error("TIFFFaxDecoder5");
                    }
                    int n15 = 0;
                    int k = 0;
                    while (k == 0) {
                        while (this.nextLesserThan8Bits(1) != 1) {
                            ++n15;
                        }
                        if (n15 > 5) {
                            n15 -= 6;
                            if (!b && n15 > 0) {
                                array3[changingElemSize++] = j;
                            }
                            j += n15;
                            if (n15 > 0) {
                                b = true;
                            }
                            if (this.nextLesserThan8Bits(1) == 0) {
                                if (!b) {
                                    array3[changingElemSize++] = j;
                                }
                                b = true;
                            }
                            else {
                                if (b) {
                                    array3[changingElemSize++] = j;
                                }
                                b = false;
                            }
                            k = 1;
                        }
                        if (n15 == 5) {
                            if (!b) {
                                array3[changingElemSize++] = j;
                            }
                            j += n15;
                            b = true;
                        }
                        else {
                            j += n15;
                            this.setToBlack(array, n5, array3[changingElemSize++] = j, 1);
                            ++j;
                            b = false;
                        }
                    }
                }
            }
            array3[changingElemSize++] = j;
            this.changingElemSize = changingElemSize;
            n5 += n4;
        }
    }
    
    private void setToBlack(final byte[] array, final int n, final int n2, final int n3) {
        int i = 8 * n + n2;
        final int n4 = i + n3;
        final int n5 = i >> 3;
        final int n6 = i & 0x7;
        if (n6 > 0) {
            int n7 = 1 << 7 - n6;
            byte b = array[n5];
            while (n7 > 0 && i < n4) {
                b |= (byte)n7;
                n7 >>= 1;
                ++i;
            }
            array[n5] = b;
        }
        int n8 = i >> 3;
        while (i < n4 - 7) {
            array[n8++] = -1;
            i += 8;
        }
        while (i < n4) {
            final int n9 = i >> 3;
            array[n9] |= (byte)(1 << 7 - (i & 0x7));
            ++i;
        }
    }
    
    private int decodeWhiteCodeWord() {
        int n = 0;
        int i = 1;
        while (i != 0) {
            final int nextNBits = this.nextNBits(10);
            final short n2 = TIFFFaxDecoder.white[nextNBits];
            final int n3 = n2 & 0x1;
            final int n4 = n2 >>> 1 & 0xF;
            if (n4 == 12) {
                final short n5 = TIFFFaxDecoder.additionalMakeup[(nextNBits << 2 & 0xC) | this.nextLesserThan8Bits(2)];
                final int n6 = n5 >>> 1 & 0x7;
                n += (n5 >>> 4 & 0xFFF);
                this.updatePointer(4 - n6);
            }
            else {
                if (n4 == 0) {
                    throw new Error("TIFFFaxDecoder0");
                }
                if (n4 == 15) {
                    throw new Error("TIFFFaxDecoder1");
                }
                n += (n2 >>> 5 & 0x7FF);
                this.updatePointer(10 - n4);
                if (n3 != 0) {
                    continue;
                }
                i = 0;
            }
        }
        return n;
    }
    
    private int decodeBlackCodeWord() {
        int n = 0;
        int i = 0;
        while (i == 0) {
            final short n2 = TIFFFaxDecoder.initBlack[this.nextLesserThan8Bits(4)];
            final int n3 = n2 >>> 1 & 0xF;
            final int n4 = n2 >>> 5 & 0x7FF;
            if (n4 == 100) {
                final short n5 = TIFFFaxDecoder.black[this.nextNBits(9)];
                final int n6 = n5 & 0x1;
                final int n7 = n5 >>> 1 & 0xF;
                final int n8 = n5 >>> 5 & 0x7FF;
                if (n7 == 12) {
                    this.updatePointer(5);
                    final short n9 = TIFFFaxDecoder.additionalMakeup[this.nextLesserThan8Bits(4)];
                    final int n10 = n9 >>> 1 & 0x7;
                    n += (n9 >>> 4 & 0xFFF);
                    this.updatePointer(4 - n10);
                }
                else {
                    if (n7 == 15) {
                        throw new Error("TIFFFaxDecoder2");
                    }
                    n += n8;
                    this.updatePointer(9 - n7);
                    if (n6 != 0) {
                        continue;
                    }
                    i = 1;
                }
            }
            else if (n4 == 200) {
                final short n11 = TIFFFaxDecoder.twoBitBlack[this.nextLesserThan8Bits(2)];
                n += (n11 >>> 5 & 0x7FF);
                this.updatePointer(2 - (n11 >>> 1 & 0xF));
                i = 1;
            }
            else {
                n += n4;
                this.updatePointer(4 - n3);
                i = 1;
            }
        }
        return n;
    }
    
    private int readEOL() {
        if (this.fillBits == 0) {
            if (this.nextNBits(12) != 1) {
                throw new Error("TIFFFaxDecoder6");
            }
        }
        else if (this.fillBits == 1) {
            final int n = 8 - this.bitPointer;
            if (this.nextNBits(n) != 0) {
                throw new Error("TIFFFaxDecoder8");
            }
            if (n < 4 && this.nextNBits(8) != 0) {
                throw new Error("TIFFFaxDecoder8");
            }
            int nextNBits;
            while ((nextNBits = this.nextNBits(8)) != 1) {
                if (nextNBits != 0) {
                    throw new Error("TIFFFaxDecoder8");
                }
            }
        }
        if (this.oneD == 0) {
            return 1;
        }
        return this.nextLesserThan8Bits(1);
    }
    
    private void getNextChangingElement(final int n, final boolean b, final int[] array) {
        final int[] prevChangingElems = this.prevChangingElems;
        final int changingElemSize = this.changingElemSize;
        final int n2 = (this.lastChangingElement > 0) ? (this.lastChangingElement - 1) : 0;
        int n3;
        if (b) {
            n3 = (n2 & 0xFFFFFFFE);
        }
        else {
            n3 = (n2 | 0x1);
        }
        int i;
        for (i = n3; i < changingElemSize; i += 2) {
            final int n4 = prevChangingElems[i];
            if (n4 > n) {
                this.lastChangingElement = i;
                array[0] = n4;
                break;
            }
        }
        if (i + 1 < changingElemSize) {
            array[1] = prevChangingElems[i + 1];
        }
    }
    
    private int nextNBits(final int n) {
        final int n2 = this.data.length - 1;
        final int bytePointer = this.bytePointer;
        byte b;
        byte b2;
        byte b3;
        if (this.fillOrder == 1) {
            b = this.data[bytePointer];
            if (bytePointer == n2) {
                b2 = 0;
                b3 = 0;
            }
            else if (bytePointer + 1 == n2) {
                b2 = this.data[bytePointer + 1];
                b3 = 0;
            }
            else {
                b2 = this.data[bytePointer + 1];
                b3 = this.data[bytePointer + 2];
            }
        }
        else {
            if (this.fillOrder != 2) {
                throw new Error("TIFFFaxDecoder7");
            }
            b = TIFFFaxDecoder.flipTable[this.data[bytePointer] & 0xFF];
            if (bytePointer == n2) {
                b2 = 0;
                b3 = 0;
            }
            else if (bytePointer + 1 == n2) {
                b2 = TIFFFaxDecoder.flipTable[this.data[bytePointer + 1] & 0xFF];
                b3 = 0;
            }
            else {
                b2 = TIFFFaxDecoder.flipTable[this.data[bytePointer + 1] & 0xFF];
                b3 = TIFFFaxDecoder.flipTable[this.data[bytePointer + 2] & 0xFF];
            }
        }
        final int n3 = 8 - this.bitPointer;
        int bitPointer = n - n3;
        int bitPointer2 = 0;
        if (bitPointer > 8) {
            bitPointer2 = bitPointer - 8;
            bitPointer = 8;
        }
        ++this.bytePointer;
        final int n4 = (b & TIFFFaxDecoder.table1[n3]) << n - n3;
        int n5 = (b2 & TIFFFaxDecoder.table2[bitPointer]) >>> 8 - bitPointer;
        if (bitPointer2 != 0) {
            n5 = (n5 << bitPointer2 | (b3 & TIFFFaxDecoder.table2[bitPointer2]) >>> 8 - bitPointer2);
            ++this.bytePointer;
            this.bitPointer = bitPointer2;
        }
        else if (bitPointer == 8) {
            this.bitPointer = 0;
            ++this.bytePointer;
        }
        else {
            this.bitPointer = bitPointer;
        }
        return n4 | n5;
    }
    
    private int nextLesserThan8Bits(final int n) {
        final int n2 = this.data.length - 1;
        final int bytePointer = this.bytePointer;
        byte b;
        byte b2;
        if (this.fillOrder == 1) {
            b = this.data[bytePointer];
            if (bytePointer == n2) {
                b2 = 0;
            }
            else {
                b2 = this.data[bytePointer + 1];
            }
        }
        else {
            if (this.fillOrder != 2) {
                throw new Error("TIFFFaxDecoder7");
            }
            b = TIFFFaxDecoder.flipTable[this.data[bytePointer] & 0xFF];
            if (bytePointer == n2) {
                b2 = 0;
            }
            else {
                b2 = TIFFFaxDecoder.flipTable[this.data[bytePointer + 1] & 0xFF];
            }
        }
        final int n3 = 8 - this.bitPointer;
        final int bitPointer = n - n3;
        final int n4 = n3 - n;
        int n5;
        if (n4 >= 0) {
            n5 = (b & TIFFFaxDecoder.table1[n3]) >>> n4;
            this.bitPointer += n;
            if (this.bitPointer == 8) {
                this.bitPointer = 0;
                ++this.bytePointer;
            }
        }
        else {
            n5 = ((b & TIFFFaxDecoder.table1[n3]) << -n4 | (b2 & TIFFFaxDecoder.table2[bitPointer]) >>> 8 - bitPointer);
            ++this.bytePointer;
            this.bitPointer = bitPointer;
        }
        return n5;
    }
    
    private void updatePointer(final int n) {
        final int bitPointer = this.bitPointer - n;
        if (bitPointer < 0) {
            --this.bytePointer;
            this.bitPointer = 8 + bitPointer;
        }
        else {
            this.bitPointer = bitPointer;
        }
    }
    
    private boolean advancePointer() {
        if (this.bitPointer != 0) {
            ++this.bytePointer;
            this.bitPointer = 0;
        }
        return true;
    }
    
    static {
        TIFFFaxDecoder.table1 = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255 };
        TIFFFaxDecoder.table2 = new int[] { 0, 128, 192, 224, 240, 248, 252, 254, 255 };
        TIFFFaxDecoder.flipTable = new byte[] { 0, -128, 64, -64, 32, -96, 96, -32, 16, -112, 80, -48, 48, -80, 112, -16, 8, -120, 72, -56, 40, -88, 104, -24, 24, -104, 88, -40, 56, -72, 120, -8, 4, -124, 68, -60, 36, -92, 100, -28, 20, -108, 84, -44, 52, -76, 116, -12, 12, -116, 76, -52, 44, -84, 108, -20, 28, -100, 92, -36, 60, -68, 124, -4, 2, -126, 66, -62, 34, -94, 98, -30, 18, -110, 82, -46, 50, -78, 114, -14, 10, -118, 74, -54, 42, -86, 106, -22, 26, -102, 90, -38, 58, -70, 122, -6, 6, -122, 70, -58, 38, -90, 102, -26, 22, -106, 86, -42, 54, -74, 118, -10, 14, -114, 78, -50, 46, -82, 110, -18, 30, -98, 94, -34, 62, -66, 126, -2, 1, -127, 65, -63, 33, -95, 97, -31, 17, -111, 81, -47, 49, -79, 113, -15, 9, -119, 73, -55, 41, -87, 105, -23, 25, -103, 89, -39, 57, -71, 121, -7, 5, -123, 69, -59, 37, -91, 101, -27, 21, -107, 85, -43, 53, -75, 117, -11, 13, -115, 77, -51, 45, -83, 109, -19, 29, -99, 93, -35, 61, -67, 125, -3, 3, -125, 67, -61, 35, -93, 99, -29, 19, -109, 83, -45, 51, -77, 115, -13, 11, -117, 75, -53, 43, -85, 107, -21, 27, -101, 91, -37, 59, -69, 123, -5, 7, -121, 71, -57, 39, -89, 103, -25, 23, -105, 87, -41, 55, -73, 119, -9, 15, -113, 79, -49, 47, -81, 111, -17, 31, -97, 95, -33, 63, -65, 127, -1 };
        TIFFFaxDecoder.white = new short[] { 6430, 6400, 6400, 6400, 3225, 3225, 3225, 3225, 944, 944, 944, 944, 976, 976, 976, 976, 1456, 1456, 1456, 1456, 1488, 1488, 1488, 1488, 718, 718, 718, 718, 718, 718, 718, 718, 750, 750, 750, 750, 750, 750, 750, 750, 1520, 1520, 1520, 1520, 1552, 1552, 1552, 1552, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 654, 654, 654, 654, 654, 654, 654, 654, 1072, 1072, 1072, 1072, 1104, 1104, 1104, 1104, 1136, 1136, 1136, 1136, 1168, 1168, 1168, 1168, 1200, 1200, 1200, 1200, 1232, 1232, 1232, 1232, 622, 622, 622, 622, 622, 622, 622, 622, 1008, 1008, 1008, 1008, 1040, 1040, 1040, 1040, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 1712, 1712, 1712, 1712, 1744, 1744, 1744, 1744, 846, 846, 846, 846, 846, 846, 846, 846, 1264, 1264, 1264, 1264, 1296, 1296, 1296, 1296, 1328, 1328, 1328, 1328, 1360, 1360, 1360, 1360, 1392, 1392, 1392, 1392, 1424, 1424, 1424, 1424, 686, 686, 686, 686, 686, 686, 686, 686, 910, 910, 910, 910, 910, 910, 910, 910, 1968, 1968, 1968, 1968, 2000, 2000, 2000, 2000, 2032, 2032, 2032, 2032, 16, 16, 16, 16, 10257, 10257, 10257, 10257, 12305, 12305, 12305, 12305, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 878, 878, 878, 878, 878, 878, 878, 878, 1904, 1904, 1904, 1904, 1936, 1936, 1936, 1936, -18413, -18413, -16365, -16365, -14317, -14317, -10221, -10221, 590, 590, 590, 590, 590, 590, 590, 590, 782, 782, 782, 782, 782, 782, 782, 782, 1584, 1584, 1584, 1584, 1616, 1616, 1616, 1616, 1648, 1648, 1648, 1648, 1680, 1680, 1680, 1680, 814, 814, 814, 814, 814, 814, 814, 814, 1776, 1776, 1776, 1776, 1808, 1808, 1808, 1808, 1840, 1840, 1840, 1840, 1872, 1872, 1872, 1872, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, 14353, 14353, 14353, 14353, 16401, 16401, 16401, 16401, 22547, 22547, 24595, 24595, 20497, 20497, 20497, 20497, 18449, 18449, 18449, 18449, 26643, 26643, 28691, 28691, 30739, 30739, -32749, -32749, -30701, -30701, -28653, -28653, -26605, -26605, -24557, -24557, -22509, -22509, -20461, -20461, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232 };
        TIFFFaxDecoder.additionalMakeup = new short[] { 28679, 28679, 31752, -32759, -31735, -30711, -29687, -28663, 29703, 29703, 30727, 30727, -27639, -26615, -25591, -24567 };
        TIFFFaxDecoder.initBlack = new short[] { 3226, 6412, 200, 168, 38, 38, 134, 134, 100, 100, 100, 100, 68, 68, 68, 68 };
        TIFFFaxDecoder.twoBitBlack = new short[] { 292, 260, 226, 226 };
        TIFFFaxDecoder.black = new short[] { 62, 62, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 588, 588, 588, 588, 588, 588, 588, 588, 1680, 1680, 20499, 22547, 24595, 26643, 1776, 1776, 1808, 1808, -24557, -22509, -20461, -18413, 1904, 1904, 1936, 1936, -16365, -14317, 782, 782, 782, 782, 814, 814, 814, 814, -12269, -10221, 10257, 10257, 12305, 12305, 14353, 14353, 16403, 18451, 1712, 1712, 1744, 1744, 28691, 30739, -32749, -30701, -28653, -26605, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 750, 750, 750, 750, 1616, 1616, 1648, 1648, 1424, 1424, 1456, 1456, 1488, 1488, 1520, 1520, 1840, 1840, 1872, 1872, 1968, 1968, 8209, 8209, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 1552, 1552, 1584, 1584, 2000, 2000, 2032, 2032, 976, 976, 1008, 1008, 1040, 1040, 1072, 1072, 1296, 1296, 1328, 1328, 718, 718, 718, 718, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 4113, 4113, 6161, 6161, 848, 848, 880, 880, 912, 912, 944, 944, 622, 622, 622, 622, 654, 654, 654, 654, 1104, 1104, 1136, 1136, 1168, 1168, 1200, 1200, 1232, 1232, 1264, 1264, 686, 686, 686, 686, 1360, 1360, 1392, 1392, 12, 12, 12, 12, 12, 12, 12, 12, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390 };
        TIFFFaxDecoder.twoDCodes = new byte[] { 80, 88, 23, 71, 30, 30, 62, 62, 4, 4, 4, 4, 4, 4, 4, 4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41 };
    }
}
