// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.SegmentHeader;
import javax.imageio.stream.ImageInputStream;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.jbig2.decoder.mmr.MMRDecompressor;
import org.apache.pdfbox.jbig2.decoder.arithmetic.CX;
import org.apache.pdfbox.jbig2.decoder.arithmetic.ArithmeticDecoder;
import org.apache.pdfbox.jbig2.Bitmap;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.Region;

public class GenericRegion implements Region
{
    private final Logger log;
    private SubInputStream subInputStream;
    private long dataHeaderOffset;
    private long dataHeaderLength;
    private long dataOffset;
    private long dataLength;
    private RegionSegmentInformation regionInfo;
    private boolean useExtTemplates;
    private boolean isTPGDon;
    private byte gbTemplate;
    private boolean isMMREncoded;
    private short[] gbAtX;
    private short[] gbAtY;
    private boolean[] gbAtOverride;
    private boolean override;
    private Bitmap regionBitmap;
    private ArithmeticDecoder arithDecoder;
    private CX cx;
    private MMRDecompressor mmrDecompressor;
    
    public GenericRegion() {
        this.log = LoggerFactory.getLogger(GenericRegion.class);
    }
    
    public GenericRegion(final SubInputStream subInputStream) {
        this.log = LoggerFactory.getLogger(GenericRegion.class);
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(subInputStream);
    }
    
    private void parseHeader() throws IOException, InvalidHeaderValueException {
        this.regionInfo.parseHeader();
        this.subInputStream.readBits(3);
        if (this.subInputStream.readBit() == 1) {
            this.useExtTemplates = true;
        }
        if (this.subInputStream.readBit() == 1) {
            this.isTPGDon = true;
        }
        this.gbTemplate = (byte)(this.subInputStream.readBits(2) & 0xFL);
        if (this.subInputStream.readBit() == 1) {
            this.isMMREncoded = true;
        }
        if (!this.isMMREncoded) {
            int n;
            if (this.gbTemplate == 0) {
                if (this.useExtTemplates) {
                    n = 12;
                }
                else {
                    n = 4;
                }
            }
            else {
                n = 1;
            }
            this.readGbAtPixels(n);
        }
        this.computeSegmentDataStructure();
        this.checkInput();
    }
    
    private void readGbAtPixels(final int n) throws IOException {
        this.gbAtX = new short[n];
        this.gbAtY = new short[n];
        for (int i = 0; i < n; ++i) {
            this.gbAtX[i] = this.subInputStream.readByte();
            this.gbAtY[i] = this.subInputStream.readByte();
        }
    }
    
    private void computeSegmentDataStructure() throws IOException {
        this.dataOffset = this.subInputStream.getStreamPosition();
        this.dataHeaderLength = this.dataOffset - this.dataHeaderOffset;
        this.dataLength = this.subInputStream.length() - this.dataHeaderLength;
    }
    
    private void checkInput() throws InvalidHeaderValueException {
        if (this.isMMREncoded && this.gbTemplate != 0) {
            this.log.info("gbTemplate should contain the value 0");
        }
    }
    
    @Override
    public Bitmap getRegionBitmap() throws IOException {
        if (null == this.regionBitmap) {
            if (this.isMMREncoded) {
                if (null == this.mmrDecompressor) {
                    this.mmrDecompressor = new MMRDecompressor(this.regionInfo.getBitmapWidth(), this.regionInfo.getBitmapHeight(), new SubInputStream(this.subInputStream, this.dataOffset, this.dataLength));
                }
                this.regionBitmap = this.mmrDecompressor.uncompress();
            }
            else {
                this.updateOverrideFlags();
                int n = 0;
                if (this.arithDecoder == null) {
                    this.arithDecoder = new ArithmeticDecoder(this.subInputStream);
                }
                if (this.cx == null) {
                    this.cx = new CX(65536, 1);
                }
                this.regionBitmap = new Bitmap(this.regionInfo.getBitmapWidth(), this.regionInfo.getBitmapHeight());
                final int n2 = this.regionBitmap.getWidth() + 7 & 0xFFFFFFF8;
                for (int i = 0; i < this.regionBitmap.getHeight(); ++i) {
                    if (this.isTPGDon) {
                        n ^= this.decodeSLTP();
                    }
                    if (n == 1) {
                        if (i > 0) {
                            this.copyLineAbove(i);
                        }
                    }
                    else {
                        this.decodeLine(i, this.regionBitmap.getWidth(), this.regionBitmap.getRowStride(), n2);
                    }
                }
            }
        }
        return this.regionBitmap;
    }
    
    private int decodeSLTP() throws IOException {
        switch (this.gbTemplate) {
            case 0: {
                this.cx.setIndex(39717);
                break;
            }
            case 1: {
                this.cx.setIndex(1941);
                break;
            }
            case 2: {
                this.cx.setIndex(229);
                break;
            }
            case 3: {
                this.cx.setIndex(405);
                break;
            }
        }
        return this.arithDecoder.decode(this.cx);
    }
    
    private void decodeLine(final int n, final int n2, final int n3, final int n4) throws IOException {
        final int byteIndex = this.regionBitmap.getByteIndex(0, n);
        final int n5 = byteIndex - n3;
        switch (this.gbTemplate) {
            case 0: {
                if (!this.useExtTemplates) {
                    this.decodeTemplate0a(n, n2, n3, n4, byteIndex, n5);
                    break;
                }
                this.decodeTemplate0b(n, n2, n3, n4, byteIndex, n5);
                break;
            }
            case 1: {
                this.decodeTemplate1(n, n2, n3, n4, byteIndex, n5);
                break;
            }
            case 2: {
                this.decodeTemplate2(n, n2, n3, n4, byteIndex, n5);
                break;
            }
            case 3: {
                this.decodeTemplate3(n, n2, n3, n4, byteIndex, n5);
                break;
            }
        }
    }
    
    private void copyLineAbove(final int n) {
        int n2 = n * this.regionBitmap.getRowStride();
        int n3 = n2 - this.regionBitmap.getRowStride();
        for (int i = 0; i < this.regionBitmap.getRowStride(); ++i) {
            this.regionBitmap.setByte(n2++, this.regionBitmap.getByte(n3++));
        }
    }
    
    private void decodeTemplate0a(final int n, final int n2, final int n3, final int n4, int n5, int n6) throws IOException {
        int byteAsInteger = 0;
        int n7 = 0;
        if (n >= 1) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n6);
        }
        if (n >= 2) {
            n7 = this.regionBitmap.getByteAsInteger(n6 - n3) << 6;
        }
        int index = (byteAsInteger & 0xF0) | (n7 & 0x3800);
        int n8;
        for (int i = 0; i < n4; i = n8) {
            byte b = 0;
            n8 = i + 8;
            final int n9 = (n2 - i > 8) ? 8 : (n2 - i);
            if (n > 0) {
                byteAsInteger = (byteAsInteger << 8 | ((n8 < n2) ? this.regionBitmap.getByteAsInteger(n6 + 1) : 0));
            }
            if (n > 1) {
                n7 = (n7 << 8 | ((n8 < n2) ? (this.regionBitmap.getByteAsInteger(n6 - n3 + 1) << 6) : 0));
            }
            for (int j = 0; j < n9; ++j) {
                final int n10 = 7 - j;
                if (this.override) {
                    this.cx.setIndex(this.overrideAtTemplate0a(index, i + j, n, b, j, n10));
                }
                else {
                    this.cx.setIndex(index);
                }
                final int decode = this.arithDecoder.decode(this.cx);
                b |= (byte)(decode << n10);
                index = ((index & 0x7BF7) << 1 | decode | (byteAsInteger >> n10 & 0x10) | (n7 >> n10 & 0x800));
            }
            this.regionBitmap.setByte(n5++, b);
            ++n6;
        }
    }
    
    private void decodeTemplate0b(final int n, final int n2, final int n3, final int n4, int n5, int n6) throws IOException {
        int byteAsInteger = 0;
        int n7 = 0;
        if (n >= 1) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n6);
        }
        if (n >= 2) {
            n7 = this.regionBitmap.getByteAsInteger(n6 - n3) << 6;
        }
        int index = (byteAsInteger & 0xF0) | (n7 & 0x3800);
        int n8;
        for (int i = 0; i < n4; i = n8) {
            byte b = 0;
            n8 = i + 8;
            final int n9 = (n2 - i > 8) ? 8 : (n2 - i);
            if (n > 0) {
                byteAsInteger = (byteAsInteger << 8 | ((n8 < n2) ? this.regionBitmap.getByteAsInteger(n6 + 1) : 0));
            }
            if (n > 1) {
                n7 = (n7 << 8 | ((n8 < n2) ? (this.regionBitmap.getByteAsInteger(n6 - n3 + 1) << 6) : 0));
            }
            for (int j = 0; j < n9; ++j) {
                final int n10 = 7 - j;
                if (this.override) {
                    this.cx.setIndex(this.overrideAtTemplate0b(index, i + j, n, b, j, n10));
                }
                else {
                    this.cx.setIndex(index);
                }
                final int decode = this.arithDecoder.decode(this.cx);
                b |= (byte)(decode << n10);
                index = ((index & 0x7BF7) << 1 | decode | (byteAsInteger >> n10 & 0x10) | (n7 >> n10 & 0x800));
            }
            this.regionBitmap.setByte(n5++, b);
            ++n6;
        }
    }
    
    private void decodeTemplate1(final int n, final int n2, final int n3, final int n4, int n5, int n6) throws IOException {
        int byteAsInteger = 0;
        int n7 = 0;
        if (n >= 1) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n6);
        }
        if (n >= 2) {
            n7 = this.regionBitmap.getByteAsInteger(n6 - n3) << 5;
        }
        int index = (byteAsInteger >> 1 & 0x1F8) | (n7 >> 1 & 0x1E00);
        int n8;
        for (int i = 0; i < n4; i = n8) {
            byte b = 0;
            n8 = i + 8;
            final int n9 = (n2 - i > 8) ? 8 : (n2 - i);
            if (n >= 1) {
                byteAsInteger = (byteAsInteger << 8 | ((n8 < n2) ? this.regionBitmap.getByteAsInteger(n6 + 1) : 0));
            }
            if (n >= 2) {
                n7 = (n7 << 8 | ((n8 < n2) ? (this.regionBitmap.getByteAsInteger(n6 - n3 + 1) << 5) : 0));
            }
            for (int j = 0; j < n9; ++j) {
                if (this.override) {
                    this.cx.setIndex(this.overrideAtTemplate1(index, i + j, n, b, j));
                }
                else {
                    this.cx.setIndex(index);
                }
                final int decode = this.arithDecoder.decode(this.cx);
                b |= (byte)(decode << 7 - j);
                final int n10 = 8 - j;
                index = ((index & 0xEFB) << 1 | decode | (byteAsInteger >> n10 & 0x8) | (n7 >> n10 & 0x200));
            }
            this.regionBitmap.setByte(n5++, b);
            ++n6;
        }
    }
    
    private void decodeTemplate2(final int n, final int n2, final int n3, final int n4, int n5, int n6) throws IOException {
        int byteAsInteger = 0;
        int n7 = 0;
        if (n >= 1) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n6);
        }
        if (n >= 2) {
            n7 = this.regionBitmap.getByteAsInteger(n6 - n3) << 4;
        }
        int index = (byteAsInteger >> 3 & 0x7C) | (n7 >> 3 & 0x380);
        int n8;
        for (int i = 0; i < n4; i = n8) {
            byte b = 0;
            n8 = i + 8;
            final int n9 = (n2 - i > 8) ? 8 : (n2 - i);
            if (n >= 1) {
                byteAsInteger = (byteAsInteger << 8 | ((n8 < n2) ? this.regionBitmap.getByteAsInteger(n6 + 1) : 0));
            }
            if (n >= 2) {
                n7 = (n7 << 8 | ((n8 < n2) ? (this.regionBitmap.getByteAsInteger(n6 - n3 + 1) << 4) : 0));
            }
            for (int j = 0; j < n9; ++j) {
                if (this.override) {
                    this.cx.setIndex(this.overrideAtTemplate2(index, i + j, n, b, j));
                }
                else {
                    this.cx.setIndex(index);
                }
                final int decode = this.arithDecoder.decode(this.cx);
                b |= (byte)(decode << 7 - j);
                final int n10 = 10 - j;
                index = ((index & 0x1BD) << 1 | decode | (byteAsInteger >> n10 & 0x4) | (n7 >> n10 & 0x80));
            }
            this.regionBitmap.setByte(n5++, b);
            ++n6;
        }
    }
    
    private void decodeTemplate3(final int n, final int n2, final int n3, final int n4, int n5, int n6) throws IOException {
        int byteAsInteger = 0;
        if (n >= 1) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n6);
        }
        int index = byteAsInteger >> 1 & 0x70;
        int n7;
        for (int i = 0; i < n4; i = n7) {
            byte b = 0;
            n7 = i + 8;
            final int n8 = (n2 - i > 8) ? 8 : (n2 - i);
            if (n >= 1) {
                byteAsInteger = (byteAsInteger << 8 | ((n7 < n2) ? this.regionBitmap.getByteAsInteger(n6 + 1) : 0));
            }
            for (int j = 0; j < n8; ++j) {
                if (this.override) {
                    this.cx.setIndex(this.overrideAtTemplate3(index, i + j, n, b, j));
                }
                else {
                    this.cx.setIndex(index);
                }
                final int decode = this.arithDecoder.decode(this.cx);
                b |= (byte)(decode << 7 - j);
                index = ((index & 0x1F7) << 1 | decode | (byteAsInteger >> 8 - j & 0x10));
            }
            this.regionBitmap.setByte(n5++, b);
            ++n6;
        }
    }
    
    private void updateOverrideFlags() {
        if (this.gbAtX == null || this.gbAtY == null) {
            this.log.info("AT pixels not set");
            return;
        }
        if (this.gbAtX.length != this.gbAtY.length) {
            this.log.info("AT pixel inconsistent, amount of x pixels: " + this.gbAtX.length + ", amount of y pixels:" + this.gbAtY.length);
            return;
        }
        this.gbAtOverride = new boolean[this.gbAtX.length];
        switch (this.gbTemplate) {
            case 0: {
                if (!this.useExtTemplates) {
                    if (this.gbAtX[0] != 3 || this.gbAtY[0] != -1) {
                        this.setOverrideFlag(0);
                    }
                    if (this.gbAtX[1] != -3 || this.gbAtY[1] != -1) {
                        this.setOverrideFlag(1);
                    }
                    if (this.gbAtX[2] != 2 || this.gbAtY[2] != -2) {
                        this.setOverrideFlag(2);
                    }
                    if (this.gbAtX[3] != -2 || this.gbAtY[3] != -2) {
                        this.setOverrideFlag(3);
                        break;
                    }
                    break;
                }
                else {
                    if (this.gbAtX[0] != -2 || this.gbAtY[0] != 0) {
                        this.setOverrideFlag(0);
                    }
                    if (this.gbAtX[1] != 0 || this.gbAtY[1] != -2) {
                        this.setOverrideFlag(1);
                    }
                    if (this.gbAtX[2] != -2 || this.gbAtY[2] != -1) {
                        this.setOverrideFlag(2);
                    }
                    if (this.gbAtX[3] != -1 || this.gbAtY[3] != -2) {
                        this.setOverrideFlag(3);
                    }
                    if (this.gbAtX[4] != 1 || this.gbAtY[4] != -2) {
                        this.setOverrideFlag(4);
                    }
                    if (this.gbAtX[5] != 2 || this.gbAtY[5] != -1) {
                        this.setOverrideFlag(5);
                    }
                    if (this.gbAtX[6] != -3 || this.gbAtY[6] != 0) {
                        this.setOverrideFlag(6);
                    }
                    if (this.gbAtX[7] != -4 || this.gbAtY[7] != 0) {
                        this.setOverrideFlag(7);
                    }
                    if (this.gbAtX[8] != 2 || this.gbAtY[8] != -2) {
                        this.setOverrideFlag(8);
                    }
                    if (this.gbAtX[9] != 3 || this.gbAtY[9] != -1) {
                        this.setOverrideFlag(9);
                    }
                    if (this.gbAtX[10] != -2 || this.gbAtY[10] != -2) {
                        this.setOverrideFlag(10);
                    }
                    if (this.gbAtX[11] != -3 || this.gbAtY[11] != -1) {
                        this.setOverrideFlag(11);
                        break;
                    }
                    break;
                }
                break;
            }
            case 1: {
                if (this.gbAtX[0] != 3 || this.gbAtY[0] != -1) {
                    this.setOverrideFlag(0);
                    break;
                }
                break;
            }
            case 2: {
                if (this.gbAtX[0] != 2 || this.gbAtY[0] != -1) {
                    this.setOverrideFlag(0);
                    break;
                }
                break;
            }
            case 3: {
                if (this.gbAtX[0] != 2 || this.gbAtY[0] != -1) {
                    this.setOverrideFlag(0);
                    break;
                }
                break;
            }
        }
    }
    
    private void setOverrideFlag(final int n) {
        this.gbAtOverride[n] = true;
        this.override = true;
    }
    
    private int overrideAtTemplate0a(int n, final int n2, final int n3, final int n4, final int n5, final int n6) throws IOException {
        if (this.gbAtOverride[0]) {
            n &= 0xFFEF;
            if (this.gbAtY[0] == 0 && this.gbAtX[0] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[0] & 0x1) << 4;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[0], n3 + this.gbAtY[0]) << 4;
            }
        }
        if (this.gbAtOverride[1]) {
            n &= 0xFBFF;
            if (this.gbAtY[1] == 0 && this.gbAtX[1] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[1] & 0x1) << 10;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[1], n3 + this.gbAtY[1]) << 10;
            }
        }
        if (this.gbAtOverride[2]) {
            n &= 0xF7FF;
            if (this.gbAtY[2] == 0 && this.gbAtX[2] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[2] & 0x1) << 11;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[2], n3 + this.gbAtY[2]) << 11;
            }
        }
        if (this.gbAtOverride[3]) {
            n &= 0x7FFF;
            if (this.gbAtY[3] == 0 && this.gbAtX[3] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[3] & 0x1) << 15;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[3], n3 + this.gbAtY[3]) << 15;
            }
        }
        return n;
    }
    
    private int overrideAtTemplate0b(int n, final int n2, final int n3, final int n4, final int n5, final int n6) throws IOException {
        if (this.gbAtOverride[0]) {
            n &= 0xFFFD;
            if (this.gbAtY[0] == 0 && this.gbAtX[0] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[0] & 0x1) << 1;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[0], n3 + this.gbAtY[0]) << 1;
            }
        }
        if (this.gbAtOverride[1]) {
            n &= 0xDFFF;
            if (this.gbAtY[1] == 0 && this.gbAtX[1] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[1] & 0x1) << 13;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[1], n3 + this.gbAtY[1]) << 13;
            }
        }
        if (this.gbAtOverride[2]) {
            n &= 0xFDFF;
            if (this.gbAtY[2] == 0 && this.gbAtX[2] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[2] & 0x1) << 9;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[2], n3 + this.gbAtY[2]) << 9;
            }
        }
        if (this.gbAtOverride[3]) {
            n &= 0xBFFF;
            if (this.gbAtY[3] == 0 && this.gbAtX[3] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[3] & 0x1) << 14;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[3], n3 + this.gbAtY[3]) << 14;
            }
        }
        if (this.gbAtOverride[4]) {
            n &= 0xEFFF;
            if (this.gbAtY[4] == 0 && this.gbAtX[4] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[4] & 0x1) << 12;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[4], n3 + this.gbAtY[4]) << 12;
            }
        }
        if (this.gbAtOverride[5]) {
            n &= 0xFFDF;
            if (this.gbAtY[5] == 0 && this.gbAtX[5] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[5] & 0x1) << 5;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[5], n3 + this.gbAtY[5]) << 5;
            }
        }
        if (this.gbAtOverride[6]) {
            n &= 0xFFFB;
            if (this.gbAtY[6] == 0 && this.gbAtX[6] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[6] & 0x1) << 2;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[6], n3 + this.gbAtY[6]) << 2;
            }
        }
        if (this.gbAtOverride[7]) {
            n &= 0xFFF7;
            if (this.gbAtY[7] == 0 && this.gbAtX[7] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[7] & 0x1) << 3;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[7], n3 + this.gbAtY[7]) << 3;
            }
        }
        if (this.gbAtOverride[8]) {
            n &= 0xF7FF;
            if (this.gbAtY[8] == 0 && this.gbAtX[8] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[8] & 0x1) << 11;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[8], n3 + this.gbAtY[8]) << 11;
            }
        }
        if (this.gbAtOverride[9]) {
            n &= 0xFFEF;
            if (this.gbAtY[9] == 0 && this.gbAtX[9] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[9] & 0x1) << 4;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[9], n3 + this.gbAtY[9]) << 4;
            }
        }
        if (this.gbAtOverride[10]) {
            n &= 0x7FFF;
            if (this.gbAtY[10] == 0 && this.gbAtX[10] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[10] & 0x1) << 15;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[10], n3 + this.gbAtY[10]) << 15;
            }
        }
        if (this.gbAtOverride[11]) {
            n &= 0xFDFF;
            if (this.gbAtY[11] == 0 && this.gbAtX[11] >= -n5) {
                n |= (n4 >> n6 - this.gbAtX[11] & 0x1) << 10;
            }
            else {
                n |= this.getPixel(n2 + this.gbAtX[11], n3 + this.gbAtY[11]) << 10;
            }
        }
        return n;
    }
    
    private int overrideAtTemplate1(int n, final int n2, final int n3, final int n4, final int n5) throws IOException {
        n &= 0x1FF7;
        if (this.gbAtY[0] == 0 && this.gbAtX[0] >= -n5) {
            return n | (n4 >> 7 - (n5 + this.gbAtX[0]) & 0x1) << 3;
        }
        return n | this.getPixel(n2 + this.gbAtX[0], n3 + this.gbAtY[0]) << 3;
    }
    
    private int overrideAtTemplate2(int n, final int n2, final int n3, final int n4, final int n5) throws IOException {
        n &= 0x3FB;
        if (this.gbAtY[0] == 0 && this.gbAtX[0] >= -n5) {
            return n | (n4 >> 7 - (n5 + this.gbAtX[0]) & 0x1) << 2;
        }
        return n | this.getPixel(n2 + this.gbAtX[0], n3 + this.gbAtY[0]) << 2;
    }
    
    private int overrideAtTemplate3(int n, final int n2, final int n3, final int n4, final int n5) throws IOException {
        n &= 0x3EF;
        if (this.gbAtY[0] == 0 && this.gbAtX[0] >= -n5) {
            return n | (n4 >> 7 - (n5 + this.gbAtX[0]) & 0x1) << 4;
        }
        return n | this.getPixel(n2 + this.gbAtX[0], n3 + this.gbAtY[0]) << 4;
    }
    
    private byte getPixel(final int n, final int n2) throws IOException {
        if (n < 0 || n >= this.regionBitmap.getWidth()) {
            return 0;
        }
        if (n2 < 0 || n2 >= this.regionBitmap.getHeight()) {
            return 0;
        }
        return this.regionBitmap.getPixel(n, n2);
    }
    
    protected void setParameters(final boolean isMMREncoded, final long dataOffset, final long dataLength, final int bitmapHeight, final int bitmapWidth) {
        this.isMMREncoded = isMMREncoded;
        this.dataOffset = dataOffset;
        this.dataLength = dataLength;
        this.regionInfo.setBitmapHeight(bitmapHeight);
        this.regionInfo.setBitmapWidth(bitmapWidth);
        this.mmrDecompressor = null;
        this.resetBitmap();
    }
    
    protected void setParameters(final boolean isMMREncoded, final byte gbTemplate, final boolean isTPGDon, final boolean b, final short[] gbAtX, final short[] gbAtY, final int bitmapWidth, final int bitmapHeight, final CX cx, final ArithmeticDecoder arithDecoder) {
        this.isMMREncoded = isMMREncoded;
        this.gbTemplate = gbTemplate;
        this.isTPGDon = isTPGDon;
        this.gbAtX = gbAtX;
        this.gbAtY = gbAtY;
        this.regionInfo.setBitmapWidth(bitmapWidth);
        this.regionInfo.setBitmapHeight(bitmapHeight);
        if (null != cx) {
            this.cx = cx;
        }
        if (null != arithDecoder) {
            this.arithDecoder = arithDecoder;
        }
        this.mmrDecompressor = null;
        this.resetBitmap();
    }
    
    protected void setParameters(final boolean isMMREncoded, final long dataOffset, final long dataLength, final int bitmapHeight, final int bitmapWidth, final byte gbTemplate, final boolean isTPGDon, final boolean b, final short[] gbAtX, final short[] gbAtY) {
        this.dataOffset = dataOffset;
        this.dataLength = dataLength;
        (this.regionInfo = new RegionSegmentInformation()).setBitmapHeight(bitmapHeight);
        this.regionInfo.setBitmapWidth(bitmapWidth);
        this.gbTemplate = gbTemplate;
        this.isMMREncoded = isMMREncoded;
        this.isTPGDon = isTPGDon;
        this.gbAtX = gbAtX;
        this.gbAtY = gbAtY;
    }
    
    protected void resetBitmap() {
        this.regionBitmap = null;
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IOException {
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(this.subInputStream);
        this.parseHeader();
    }
    
    @Override
    public RegionSegmentInformation getRegionInfo() {
        return this.regionInfo;
    }
    
    protected boolean useExtTemplates() {
        return this.useExtTemplates;
    }
    
    protected boolean isTPGDon() {
        return this.isTPGDon;
    }
    
    protected byte getGbTemplate() {
        return this.gbTemplate;
    }
    
    protected boolean isMMREncoded() {
        return this.isMMREncoded;
    }
    
    protected short[] getGbAtX() {
        return this.gbAtX;
    }
    
    protected short[] getGbAtY() {
        return this.gbAtY;
    }
}
