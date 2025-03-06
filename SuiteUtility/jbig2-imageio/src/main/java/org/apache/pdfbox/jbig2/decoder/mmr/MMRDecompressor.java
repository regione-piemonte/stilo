// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.decoder.mmr;

import java.util.Arrays;
import java.io.EOFException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.Bitmap;
import javax.imageio.stream.ImageInputStream;

public class MMRDecompressor
{
    private int width;
    private int height;
    private static final int FIRST_LEVEL_TABLE_SIZE = 8;
    private static final int FIRST_LEVEL_TABLE_MASK = 255;
    private static final int SECOND_LEVEL_TABLE_SIZE = 5;
    private static final int SECOND_LEVEL_TABLE_MASK = 31;
    private static Code[] whiteTable;
    private static Code[] blackTable;
    private static Code[] modeTable;
    private RunData data;
    
    private static final synchronized void initTables() {
        if (null == MMRDecompressor.whiteTable) {
            MMRDecompressor.whiteTable = createLittleEndianTable(MMRConstants.WhiteCodes);
            MMRDecompressor.blackTable = createLittleEndianTable(MMRConstants.BlackCodes);
            MMRDecompressor.modeTable = createLittleEndianTable(MMRConstants.ModeCodes);
        }
    }
    
    private final int uncompress2D(final RunData runData, final int[] array, final int n, final int[] array2, final int n2) {
        int i = 0;
        int j = 0;
        int k = 0;
        boolean b = true;
        Code obj = null;
        array[n] = (array[n + 1] = n2);
        array[n + 2] = (array[n + 3] = n2 + 1);
        try {
        Label_0672:
            while (k < n2) {
                obj = runData.uncompressGetCode(MMRDecompressor.modeTable);
                if (obj == null) {
                    ++runData.offset;
                    break;
                }
                runData.offset += obj.bitLength;
                switch (obj.runLength) {
                    case 2: {
                        k = array[i];
                        break;
                    }
                    case 3: {
                        k = array[i] + 1;
                        break;
                    }
                    case 6: {
                        k = array[i] - 1;
                        break;
                    }
                    case 1: {
                        while (1 > 0) {
                            obj = runData.uncompressGetCode(b ? MMRDecompressor.whiteTable : MMRDecompressor.blackTable);
                            if (obj == null) {
                                break Label_0672;
                            }
                            runData.offset += obj.bitLength;
                            if (obj.runLength < 64) {
                                if (obj.runLength < 0) {
                                    array2[j++] = k;
                                    obj = null;
                                    break Label_0672;
                                }
                                k += obj.runLength;
                                array2[j++] = k;
                                break;
                            }
                            else {
                                k += obj.runLength;
                            }
                        }
                        final int n3 = k;
                        while (1 > 0) {
                            obj = runData.uncompressGetCode((!b) ? MMRDecompressor.whiteTable : MMRDecompressor.blackTable);
                            if (obj == null) {
                                break Label_0672;
                            }
                            runData.offset += obj.bitLength;
                            if (obj.runLength < 64) {
                                if (obj.runLength < 0) {
                                    array2[j++] = k;
                                    break Label_0672;
                                }
                                k += obj.runLength;
                                if (k < n2 || k != n3) {
                                    array2[j++] = k;
                                    break;
                                }
                                break;
                            }
                            else {
                                k += obj.runLength;
                            }
                        }
                        while (k < n2 && array[i] <= k) {
                            i += 2;
                        }
                        continue;
                    }
                    case 0: {
                        ++i;
                        k = array[i++];
                        continue;
                    }
                    case 4: {
                        k = array[i] + 2;
                        break;
                    }
                    case 7: {
                        k = array[i] - 2;
                        break;
                    }
                    case 5: {
                        k = array[i] + 3;
                        break;
                    }
                    case 8: {
                        k = array[i] - 3;
                        break;
                    }
                    default: {
                        System.err.println("Should not happen!");
                        if (runData.offset == 12 && obj.runLength == -1) {
                            runData.offset = 0;
                            this.uncompress1D(runData, array, n2);
                            ++runData.offset;
                            this.uncompress1D(runData, array2, n2);
                            final int uncompress1D = this.uncompress1D(runData, array, n2);
                            ++runData.offset;
                            return uncompress1D;
                        }
                        k = n2;
                        continue;
                    }
                }
                if (k > n2) {
                    continue;
                }
                b = !b;
                array2[j++] = k;
                if (i > 0) {
                    --i;
                }
                else {
                    ++i;
                }
                while (k < n2 && array[i] <= k) {
                    i += 2;
                }
            }
        }
        catch (Throwable t) {
            final StringBuffer sb = new StringBuffer();
            sb.append("whiteRun           = ");
            sb.append(b);
            sb.append("\n");
            sb.append("code               = ");
            sb.append(obj);
            sb.append("\n");
            sb.append("refOffset          = ");
            sb.append(i);
            sb.append("\n");
            sb.append("curOffset          = ");
            sb.append(j);
            sb.append("\n");
            sb.append("bitPos             = ");
            sb.append(k);
            sb.append("\n");
            sb.append("runData.offset = ");
            sb.append(runData.offset);
            sb.append(" ( byte:");
            sb.append(runData.offset / 8);
            sb.append(", bit:");
            sb.append(runData.offset & 0x7);
            sb.append(" )");
            System.out.println(sb.toString());
            return -3;
        }
        if (array2[j] != n2) {
            array2[j] = n2;
        }
        if (obj == null) {
            return -1;
        }
        return j;
    }
    
    public MMRDecompressor(final int width, final int height, final ImageInputStream imageInputStream) {
        this.width = width;
        this.height = height;
        this.data = new RunData(imageInputStream);
        initTables();
    }
    
    public Bitmap uncompress() {
        final Bitmap bitmap = new Bitmap(this.width, this.height);
        int[] array = new int[this.width + 5];
        int[] array2 = new int[this.width + 5];
        array2[0] = this.width;
        int n = 1;
        for (int i = 0; i < this.height; ++i) {
            final int uncompress2D = this.uncompress2D(this.data, array2, n, array, this.width);
            if (uncompress2D == -3) {
                break;
            }
            if (uncompress2D > 0) {
                this.fillBitmap(bitmap, i, array, uncompress2D);
            }
            final int[] array3 = array2;
            array2 = array;
            array = array3;
            n = uncompress2D;
        }
        this.detectAndSkipEOL();
        this.data.align();
        return bitmap;
    }
    
    private void detectAndSkipEOL() {
        while (true) {
            final Code access$000 = this.data.uncompressGetCode(MMRDecompressor.modeTable);
            if (null == access$000 || access$000.runLength != -1) {
                break;
            }
            final RunData data = this.data;
            data.offset += access$000.bitLength;
        }
    }
    
    private void fillBitmap(final Bitmap bitmap, final int n, final int[] array, final int n2) {
        int i = 0;
        int byteIndex = bitmap.getByteIndex(0, n);
        byte b = 0;
        for (int j = 0; j < n2; ++j) {
            final int n3 = array[j];
            final boolean b2 = (j & 0x1) != 0x0;
            while (i < n3) {
                b = (byte)(b << 1 | (b2 ? 1 : 0));
                if ((++i & 0x7) == 0x0) {
                    bitmap.setByte(byteIndex++, b);
                    b = 0;
                }
            }
        }
        if ((i & 0x7) != 0x0) {
            bitmap.setByte(byteIndex, (byte)(b << 8 - (i & 0x7)));
        }
    }
    
    private final int uncompress1D(final RunData runData, final int[] array, final int n) {
        boolean b = true;
        int i = 0;
        Code code = null;
        int n2 = 0;
    Label_0113:
        while (i < n) {
            do {
                if (b) {
                    code = runData.uncompressGetCode(MMRDecompressor.whiteTable);
                }
                else {
                    code = runData.uncompressGetCode(MMRDecompressor.blackTable);
                }
                runData.offset += code.bitLength;
                if (code.runLength < 0) {
                    break Label_0113;
                }
                i += code.runLength;
            } while (code.runLength >= 64);
            b = !b;
            array[n2++] = i;
        }
        if (array[n2] != n) {
            array[n2] = n;
        }
        return (code != null && code.runLength != -1) ? n2 : -1;
    }
    
    private static Code[] createLittleEndianTable(final int[][] array) {
        final Code[] array2 = new Code[256];
        for (int i = 0; i < array.length; ++i) {
            final Code code = new Code(array[i]);
            if (code.bitLength <= 8) {
                final int n = 8 - code.bitLength;
                final int n2 = code.codeWord << n;
                for (int j = (1 << n) - 1; j >= 0; --j) {
                    array2[n2 | j] = code;
                }
            }
            else {
                final int n3 = code.codeWord >>> code.bitLength - 8;
                if (array2[n3] == null) {
                    final Code code2 = new Code(new int[3]);
                    code2.subTable = new Code[32];
                    array2[n3] = code2;
                }
                if (code.bitLength > 13) {
                    throw new IllegalArgumentException("Code table overflow in MMRDecompressor");
                }
                final Code[] subTable = array2[n3].subTable;
                final int n4 = 13 - code.bitLength;
                final int n5 = code.codeWord << n4 & 0x1F;
                for (int k = (1 << n4) - 1; k >= 0; --k) {
                    subTable[n5 | k] = code;
                }
            }
        }
        return array2;
    }
    
    static {
        MMRDecompressor.whiteTable = null;
        MMRDecompressor.blackTable = null;
        MMRDecompressor.modeTable = null;
    }
    
    private final class RunData
    {
        private static final int MAX_RUN_DATA_BUFFER = 131072;
        private static final int MIN_RUN_DATA_BUFFER = 3;
        private static final int CODE_OFFSET = 24;
        ImageInputStream stream;
        int offset;
        int lastOffset;
        int lastCode;
        byte[] buffer;
        int bufferBase;
        int bufferTop;
        
        RunData(final ImageInputStream stream) {
            this.lastOffset = 0;
            this.lastCode = 0;
            this.stream = stream;
            this.offset = 0;
            this.lastOffset = 1;
            try {
                this.buffer = new byte[(int)Math.min(Math.max(3L, stream.length()), 131072L)];
                this.fillBuffer(0);
            }
            catch (IOException ex) {
                this.buffer = new byte[10];
                ex.printStackTrace();
            }
        }
        
        private final Code uncompressGetCode(final Code[] array) {
            return this.uncompressGetCodeLittleEndian(array);
        }
        
        private final Code uncompressGetCodeLittleEndian(final Code[] array) {
            final int n = this.uncompressGetNextCodeLittleEndian() & 0xFFFFFF;
            Code code = array[n >> 16];
            if (null != code && null != code.subTable) {
                code = code.subTable[n >> 11 & 0x1F];
            }
            return code;
        }
        
        private final int uncompressGetNextCodeLittleEndian() {
            try {
                int i = this.offset - this.lastOffset;
                if (i < 0 || i > 24) {
                    int n = (this.offset >> 3) - this.bufferBase;
                    if (n >= this.bufferTop) {
                        final int n2 = n + this.bufferBase;
                        this.fillBuffer(n2);
                        n = n2 - this.bufferBase;
                    }
                    this.lastCode = ((this.buffer[n] & 0xFF) << 16 | (this.buffer[n + 1] & 0xFF) << 8 | (this.buffer[n + 2] & 0xFF));
                    this.lastCode <<= (this.offset & 0x7);
                }
                else {
                    final int n3 = this.lastOffset & 0x7;
                    if (i <= 7 - n3) {
                        this.lastCode <<= i;
                    }
                    else {
                        int n4 = (this.lastOffset >> 3) + 3 - this.bufferBase;
                        if (n4 >= this.bufferTop) {
                            final int n5 = n4 + this.bufferBase;
                            this.fillBuffer(n5);
                            n4 = n5 - this.bufferBase;
                        }
                        int n6 = 8 - n3;
                        do {
                            this.lastCode <<= n6;
                            this.lastCode |= (this.buffer[n4] & 0xFF);
                            i -= n6;
                            ++n4;
                            n6 = 8;
                        } while (i >= 8);
                        this.lastCode <<= i;
                    }
                }
                this.lastOffset = this.offset;
                return this.lastCode;
            }
            catch (IOException ex) {
                throw new ArrayIndexOutOfBoundsException("Corrupted RLE data caused by an IOException while reading raw data: " + ex.toString());
            }
        }
        
        private void fillBuffer(final int bufferBase) throws IOException {
            this.bufferBase = bufferBase;
            synchronized (this.stream) {
                try {
                    this.stream.seek(bufferBase);
                    this.bufferTop = this.stream.read(this.buffer);
                }
                catch (EOFException ex) {
                    this.bufferTop = -1;
                }
                if (this.bufferTop > -1 && this.bufferTop < 3) {
                    while (this.bufferTop < 3) {
                        int read;
                        try {
                            read = this.stream.read();
                        }
                        catch (EOFException ex2) {
                            read = -1;
                        }
                        this.buffer[this.bufferTop++] = (byte)((read == -1) ? 0 : ((byte)(read & 0xFF)));
                    }
                }
            }
            this.bufferTop -= 3;
            if (this.bufferTop < 0) {
                Arrays.fill(this.buffer, (byte)0);
                this.bufferTop = this.buffer.length - 3;
            }
        }
        
        private void align() {
            this.offset = this.offset + 7 >> 3 << 3;
        }
    }
    
    private static final class Code
    {
        Code[] subTable;
        final int bitLength;
        final int codeWord;
        final int runLength;
        
        Code(final int[] array) {
            this.subTable = null;
            this.bitLength = array[0];
            this.codeWord = array[1];
            this.runLength = array[2];
        }
        
        @Override
        public String toString() {
            return this.bitLength + "/" + this.codeWord + "/" + this.runLength;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Code && ((Code)o).bitLength == this.bitLength && ((Code)o).codeWord == this.codeWord && ((Code)o).runLength == this.runLength;
        }
    }
}
