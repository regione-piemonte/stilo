// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import org.apache.pdfbox.jbig2.decoder.arithmetic.CX;
import org.apache.pdfbox.jbig2.decoder.arithmetic.ArithmeticDecoder;
import org.apache.pdfbox.jbig2.Bitmap;
import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.Region;

public class GenericRefinementRegion implements Region
{
    private static final Logger log;
    private static final Template T0;
    private static final Template T1;
    private SubInputStream subInputStream;
    private SegmentHeader segmentHeader;
    private RegionSegmentInformation regionInfo;
    private boolean isTPGROn;
    private short templateID;
    private Template template;
    private short[] grAtX;
    private short[] grAtY;
    private Bitmap regionBitmap;
    private Bitmap referenceBitmap;
    private int referenceDX;
    private int referenceDY;
    private ArithmeticDecoder arithDecoder;
    private CX cx;
    private boolean override;
    private boolean[] grAtOverride;
    
    public GenericRefinementRegion() {
    }
    
    public GenericRefinementRegion(final SubInputStream subInputStream) {
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(subInputStream);
    }
    
    public GenericRefinementRegion(final SubInputStream subInputStream, final SegmentHeader segmentHeader) {
        this.subInputStream = subInputStream;
        this.segmentHeader = segmentHeader;
        this.regionInfo = new RegionSegmentInformation(subInputStream);
    }
    
    private void parseHeader() throws IOException {
        this.regionInfo.parseHeader();
        this.subInputStream.readBits(6);
        if (this.subInputStream.readBit() == 1) {
            this.isTPGROn = true;
        }
        switch (this.templateID = (short)this.subInputStream.readBit()) {
            case 0: {
                this.template = GenericRefinementRegion.T0;
                this.readAtPixels();
                break;
            }
            case 1: {
                this.template = GenericRefinementRegion.T1;
                break;
            }
        }
    }
    
    private void readAtPixels() throws IOException {
        this.grAtX = new short[2];
        this.grAtY = new short[2];
        this.grAtX[0] = this.subInputStream.readByte();
        this.grAtY[0] = this.subInputStream.readByte();
        this.grAtX[1] = this.subInputStream.readByte();
        this.grAtY[1] = this.subInputStream.readByte();
    }
    
    @Override
    public Bitmap getRegionBitmap() throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        if (null == this.regionBitmap) {
            int n = 0;
            if (this.referenceBitmap == null) {
                this.referenceBitmap = this.getGrReference();
            }
            if (this.arithDecoder == null) {
                this.arithDecoder = new ArithmeticDecoder(this.subInputStream);
            }
            if (this.cx == null) {
                this.cx = new CX(8192, 1);
            }
            this.regionBitmap = new Bitmap(this.regionInfo.getBitmapWidth(), this.regionInfo.getBitmapHeight());
            if (this.templateID == 0) {
                this.updateOverride();
            }
            final int n2 = this.regionBitmap.getWidth() + 7 & 0xFFFFFFF8;
            final int n3 = this.isTPGROn ? (-this.referenceDY * this.referenceBitmap.getRowStride()) : 0;
            final int n4 = n3 + 1;
            for (int i = 0; i < this.regionBitmap.getHeight(); ++i) {
                if (this.isTPGROn) {
                    n ^= this.decodeSLTP();
                }
                if (n == 0) {
                    this.decodeOptimized(i, this.regionBitmap.getWidth(), this.regionBitmap.getRowStride(), this.referenceBitmap.getRowStride(), n2, n3, n4);
                }
                else {
                    this.decodeTypicalPredictedLine(i, this.regionBitmap.getWidth(), this.regionBitmap.getRowStride(), this.referenceBitmap.getRowStride(), n2, n3);
                }
            }
        }
        return this.regionBitmap;
    }
    
    private int decodeSLTP() throws IOException {
        this.template.setIndex(this.cx);
        return this.arithDecoder.decode(this.cx);
    }
    
    private Bitmap getGrReference() throws IntegerMaxValueException, InvalidHeaderValueException, IOException {
        return ((Region)this.segmentHeader.getRtSegments()[0].getSegmentData()).getRegionBitmap();
    }
    
    private void decodeOptimized(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) throws IOException {
        final int n8 = n - this.referenceDY;
        final int byteIndex = this.referenceBitmap.getByteIndex(Math.max(0, -this.referenceDX), n8);
        final int byteIndex2 = this.regionBitmap.getByteIndex(Math.max(0, this.referenceDX), n);
        switch (this.templateID) {
            case 0: {
                this.decodeTemplate(n, n2, n3, n4, n5, n6, n7, byteIndex2, n8, byteIndex, GenericRefinementRegion.T0);
                break;
            }
            case 1: {
                this.decodeTemplate(n, n2, n3, n4, n5, n6, n7, byteIndex2, n8, byteIndex, GenericRefinementRegion.T1);
                break;
            }
        }
    }
    
    private void decodeTemplate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, int n8, final int n9, int n10, final Template template) throws IOException {
        int byteAsInteger;
        int n13;
        int n12;
        int n11 = n12 = (n13 = (byteAsInteger = 0));
        if (n9 >= 1 && n9 - 1 < this.referenceBitmap.getHeight()) {
            n12 = this.referenceBitmap.getByteAsInteger(n10 - n4);
        }
        if (n9 >= 0 && n9 < this.referenceBitmap.getHeight()) {
            n11 = this.referenceBitmap.getByteAsInteger(n10);
        }
        if (n9 >= -1 && n9 + 1 < this.referenceBitmap.getHeight()) {
            n13 = this.referenceBitmap.getByteAsInteger(n10 + n4);
        }
        ++n10;
        if (n >= 1) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n8 - n3);
        }
        ++n8;
        final int n14 = this.referenceDX % 8;
        final int n15 = 6 + n14;
        final int n16 = n10 % n4;
        short n17;
        short n18;
        short n19;
        if (n15 >= 0) {
            n17 = (short)(((n15 >= 8) ? 0 : (n12 >>> n15)) & 0x7);
            n18 = (short)(((n15 >= 8) ? 0 : (n11 >>> n15)) & 0x7);
            n19 = (short)(((n15 >= 8) ? 0 : (n13 >>> n15)) & 0x7);
            if (n15 == 6 && n16 > 1) {
                if (n9 >= 1 && n9 - 1 < this.referenceBitmap.getHeight()) {
                    n17 |= (short)(this.referenceBitmap.getByteAsInteger(n10 - n4 - 2) << 2 & 0x4);
                }
                if (n9 >= 0 && n9 < this.referenceBitmap.getHeight()) {
                    n18 |= (short)(this.referenceBitmap.getByteAsInteger(n10 - 2) << 2 & 0x4);
                }
                if (n9 >= -1 && n9 + 1 < this.referenceBitmap.getHeight()) {
                    n19 |= (short)(this.referenceBitmap.getByteAsInteger(n10 + n4 - 2) << 2 & 0x4);
                }
            }
            if (n15 == 0) {
                n11 = (n12 = (n13 = 0));
                if (n16 < n4 - 1) {
                    if (n9 >= 1 && n9 - 1 < this.referenceBitmap.getHeight()) {
                        n12 = this.referenceBitmap.getByteAsInteger(n10 - n4);
                    }
                    if (n9 >= 0 && n9 < this.referenceBitmap.getHeight()) {
                        n11 = this.referenceBitmap.getByteAsInteger(n10);
                    }
                    if (n9 >= -1 && n9 + 1 < this.referenceBitmap.getHeight()) {
                        n13 = this.referenceBitmap.getByteAsInteger(n10 + n4);
                    }
                }
                ++n10;
            }
        }
        else {
            final short n20 = (short)(n12 << 1 & 0x7);
            final short n21 = (short)(n11 << 1 & 0x7);
            final short n22 = (short)(n13 << 1 & 0x7);
            n11 = (n12 = (n13 = 0));
            if (n16 < n4 - 1) {
                if (n9 >= 1 && n9 - 1 < this.referenceBitmap.getHeight()) {
                    n12 = this.referenceBitmap.getByteAsInteger(n10 - n4);
                }
                if (n9 >= 0 && n9 < this.referenceBitmap.getHeight()) {
                    n11 = this.referenceBitmap.getByteAsInteger(n10);
                }
                if (n9 >= -1 && n9 + 1 < this.referenceBitmap.getHeight()) {
                    n13 = this.referenceBitmap.getByteAsInteger(n10 + n4);
                }
                ++n10;
            }
            n17 = (short)(n20 | (short)(n12 >>> 7 & 0x7));
            n18 = (short)(n21 | (short)(n11 >>> 7 & 0x7));
            n19 = (short)(n22 | (short)(n13 >>> 7 & 0x7));
        }
        short n23 = (short)(byteAsInteger >>> 6);
        short n24 = 0;
        final int n25 = (2 - n14) % 8;
        int byteAsInteger2 = n12 << n25;
        int byteAsInteger3 = n11 << n25;
        int byteAsInteger4 = n13 << n25;
        int byteAsInteger5 = byteAsInteger << 2;
        for (int i = 0; i < n2; ++i) {
            final int n26 = i & 0x7;
            final short form = template.form(n17, n18, n19, n23, n24);
            if (this.override) {
                this.cx.setIndex(this.overrideAtTemplate0(form, i, n, this.regionBitmap.getByte(this.regionBitmap.getByteIndex(i, n)), n26));
            }
            else {
                this.cx.setIndex(form);
            }
            final int decode = this.arithDecoder.decode(this.cx);
            this.regionBitmap.setPixel(i, n, (byte)decode);
            n17 = (short)((n17 << 1 | (0x1 & byteAsInteger2 >>> 7)) & 0x7);
            n18 = (short)((n18 << 1 | (0x1 & byteAsInteger3 >>> 7)) & 0x7);
            n19 = (short)((n19 << 1 | (0x1 & byteAsInteger4 >>> 7)) & 0x7);
            n23 = (short)((n23 << 1 | (0x1 & byteAsInteger5 >>> 7)) & 0x7);
            n24 = (short)decode;
            if ((i - this.referenceDX) % 8 == 5) {
                if ((i - this.referenceDX) / 8 + 1 >= this.referenceBitmap.getRowStride()) {
                    byteAsInteger3 = (byteAsInteger2 = (byteAsInteger4 = 0));
                }
                else {
                    if (n9 >= 1 && n9 - 1 < this.referenceBitmap.getHeight()) {
                        byteAsInteger2 = this.referenceBitmap.getByteAsInteger(n10 - n4);
                    }
                    else {
                        byteAsInteger2 = 0;
                    }
                    if (n9 >= 0 && n9 < this.referenceBitmap.getHeight()) {
                        byteAsInteger3 = this.referenceBitmap.getByteAsInteger(n10);
                    }
                    else {
                        byteAsInteger3 = 0;
                    }
                    if (n9 >= -1 && n9 + 1 < this.referenceBitmap.getHeight()) {
                        byteAsInteger4 = this.referenceBitmap.getByteAsInteger(n10 + n4);
                    }
                    else {
                        byteAsInteger4 = 0;
                    }
                }
                ++n10;
            }
            else {
                byteAsInteger2 <<= 1;
                byteAsInteger3 <<= 1;
                byteAsInteger4 <<= 1;
            }
            if (n26 == 5 && n >= 1) {
                if ((i >> 3) + 1 >= this.regionBitmap.getRowStride()) {
                    byteAsInteger5 = 0;
                }
                else {
                    byteAsInteger5 = this.regionBitmap.getByteAsInteger(n8 - n3);
                }
                ++n8;
            }
            else {
                byteAsInteger5 <<= 1;
            }
        }
    }
    
    private void updateOverride() {
        if (this.grAtX == null || this.grAtY == null) {
            GenericRefinementRegion.log.info("AT pixels not set");
            return;
        }
        if (this.grAtX.length != this.grAtY.length) {
            GenericRefinementRegion.log.info("AT pixel inconsistent");
            return;
        }
        this.grAtOverride = new boolean[this.grAtX.length];
        switch (this.templateID) {
            case 0: {
                if (this.grAtX[0] != -1 && this.grAtY[0] != -1) {
                    this.grAtOverride[0] = true;
                    this.override = true;
                }
                if (this.grAtX[1] != -1 && this.grAtY[1] != -1) {
                    this.grAtOverride[1] = true;
                    this.override = true;
                    break;
                }
                break;
            }
            case 1: {
                this.override = false;
                break;
            }
        }
    }
    
    private void decodeTypicalPredictedLine(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) throws IOException {
        final int n7 = n - this.referenceDY;
        final int byteIndex = this.referenceBitmap.getByteIndex(0, n7);
        final int byteIndex2 = this.regionBitmap.getByteIndex(0, n);
        switch (this.templateID) {
            case 0: {
                this.decodeTypicalPredictedLineTemplate0(n, n2, n3, n4, n5, n6, byteIndex2, n7, byteIndex);
                break;
            }
            case 1: {
                this.decodeTypicalPredictedLineTemplate1(n, n2, n3, n4, n5, n6, byteIndex2, n7, byteIndex);
                break;
            }
        }
    }
    
    private void decodeTypicalPredictedLineTemplate0(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, int n7, final int n8, int n9) throws IOException {
        int byteAsInteger;
        if (n > 0) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n7 - n3);
        }
        else {
            byteAsInteger = 0;
        }
        int n10;
        if (n8 > 0 && n8 <= this.referenceBitmap.getHeight()) {
            n10 = this.referenceBitmap.getByteAsInteger(n9 - n4 + n6) << 4;
        }
        else {
            n10 = 0;
        }
        int n11;
        if (n8 >= 0 && n8 < this.referenceBitmap.getHeight()) {
            n11 = this.referenceBitmap.getByteAsInteger(n9 + n6) << 1;
        }
        else {
            n11 = 0;
        }
        int byteAsInteger2;
        if (n8 > -2 && n8 < this.referenceBitmap.getHeight() - 1) {
            byteAsInteger2 = this.referenceBitmap.getByteAsInteger(n9 + n4 + n6);
        }
        else {
            byteAsInteger2 = 0;
        }
        int index = (byteAsInteger >> 5 & 0x6) | (byteAsInteger2 >> 2 & 0x30) | (n11 & 0x180) | (n10 & 0xC00);
        int n12;
        for (int i = 0; i < n5; i = n12) {
            byte b = 0;
            n12 = i + 8;
            final int n13 = (n2 - i > 8) ? 8 : (n2 - i);
            final boolean b2 = n12 < n2;
            final boolean b3 = n12 < this.referenceBitmap.getWidth();
            final int n14 = n6 + 1;
            if (n > 0) {
                byteAsInteger = (byteAsInteger << 8 | (b2 ? this.regionBitmap.getByteAsInteger(n7 - n3 + 1) : 0));
            }
            if (n8 > 0 && n8 <= this.referenceBitmap.getHeight()) {
                n10 = (n10 << 8 | (b3 ? (this.referenceBitmap.getByteAsInteger(n9 - n4 + n14) << 4) : 0));
            }
            if (n8 >= 0 && n8 < this.referenceBitmap.getHeight()) {
                n11 = (n11 << 8 | (b3 ? (this.referenceBitmap.getByteAsInteger(n9 + n14) << 1) : 0));
            }
            if (n8 > -2 && n8 < this.referenceBitmap.getHeight() - 1) {
                byteAsInteger2 = (byteAsInteger2 << 8 | (b3 ? this.referenceBitmap.getByteAsInteger(n9 + n4 + n14) : 0));
            }
            for (int j = 0; j < n13; ++j) {
                boolean b4 = false;
                int decode = 0;
                final int n15 = index >> 4 & 0x1FF;
                if (n15 == 511) {
                    b4 = true;
                    decode = 1;
                }
                else if (n15 == 0) {
                    b4 = true;
                    decode = 0;
                }
                if (!b4) {
                    if (this.override) {
                        this.cx.setIndex(this.overrideAtTemplate0(index, i + j, n, b, j));
                    }
                    else {
                        this.cx.setIndex(index);
                    }
                    decode = this.arithDecoder.decode(this.cx);
                }
                final int n16 = 7 - j;
                b |= (byte)(decode << n16);
                index = ((index & 0xDB6) << 1 | decode | (byteAsInteger >> n16 + 5 & 0x2) | (byteAsInteger2 >> n16 + 2 & 0x10) | (n11 >> n16 & 0x80) | (n10 >> n16 & 0x400));
            }
            this.regionBitmap.setByte(n7++, b);
            ++n9;
        }
    }
    
    private void decodeTypicalPredictedLineTemplate1(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, int n7, final int n8, int n9) throws IOException {
        int byteAsInteger;
        if (n > 0) {
            byteAsInteger = this.regionBitmap.getByteAsInteger(n7 - n3);
        }
        else {
            byteAsInteger = 0;
        }
        int n10;
        if (n8 > 0 && n8 <= this.referenceBitmap.getHeight()) {
            n10 = this.referenceBitmap.getByteAsInteger(n7 - n4 + n6) << 2;
        }
        else {
            n10 = 0;
        }
        int byteAsInteger2;
        if (n8 >= 0 && n8 < this.referenceBitmap.getHeight()) {
            byteAsInteger2 = this.referenceBitmap.getByteAsInteger(n7 + n6);
        }
        else {
            byteAsInteger2 = 0;
        }
        int byteAsInteger3;
        if (n8 > -2 && n8 < this.referenceBitmap.getHeight() - 1) {
            byteAsInteger3 = this.referenceBitmap.getByteAsInteger(n7 + n4 + n6);
        }
        else {
            byteAsInteger3 = 0;
        }
        int index = (byteAsInteger >> 5 & 0x6) | (byteAsInteger3 >> 2 & 0x30) | (byteAsInteger2 & 0xC0) | (n10 & 0x200);
        int n11 = (byteAsInteger3 >> 2 & 0x70) | (byteAsInteger2 & 0xC0) | (n10 & 0x700);
        int n12;
        for (int i = 0; i < n5; i = n12) {
            byte b = 0;
            n12 = i + 8;
            final int n13 = (n2 - i > 8) ? 8 : (n2 - i);
            final boolean b2 = n12 < n2;
            final boolean b3 = n12 < this.referenceBitmap.getWidth();
            final int n14 = n6 + 1;
            if (n > 0) {
                byteAsInteger = (byteAsInteger << 8 | (b2 ? this.regionBitmap.getByteAsInteger(n7 - n3 + 1) : 0));
            }
            if (n8 > 0 && n8 <= this.referenceBitmap.getHeight()) {
                n10 = (n10 << 8 | (b3 ? (this.referenceBitmap.getByteAsInteger(n9 - n4 + n14) << 2) : 0));
            }
            if (n8 >= 0 && n8 < this.referenceBitmap.getHeight()) {
                byteAsInteger2 = (byteAsInteger2 << 8 | (b3 ? this.referenceBitmap.getByteAsInteger(n9 + n14) : 0));
            }
            if (n8 > -2 && n8 < this.referenceBitmap.getHeight() - 1) {
                byteAsInteger3 = (byteAsInteger3 << 8 | (b3 ? this.referenceBitmap.getByteAsInteger(n9 + n4 + n14) : 0));
            }
            for (int j = 0; j < n13; ++j) {
                final int n15 = n11 >> 4 & 0x1FF;
                int decode;
                if (n15 == 511) {
                    decode = 1;
                }
                else if (n15 == 0) {
                    decode = 0;
                }
                else {
                    this.cx.setIndex(index);
                    decode = this.arithDecoder.decode(this.cx);
                }
                final int n16 = 7 - j;
                b |= (byte)(decode << n16);
                index = ((index & 0xD6) << 1 | decode | (byteAsInteger >> n16 + 5 & 0x2) | (byteAsInteger3 >> n16 + 2 & 0x10) | (byteAsInteger2 >> n16 & 0x40) | (n10 >> n16 & 0x200));
                n11 = ((n11 & 0xDB) << 1 | (byteAsInteger3 >> n16 + 2 & 0x10) | (byteAsInteger2 >> n16 & 0x80) | (n10 >> n16 & 0x400));
            }
            this.regionBitmap.setByte(n7++, b);
            ++n9;
        }
    }
    
    private int overrideAtTemplate0(int n, final int n2, final int n3, final int n4, final int n5) throws IOException {
        if (this.grAtOverride[0]) {
            n &= 0xFFF7;
            if (this.grAtY[0] == 0 && this.grAtX[0] >= -n5) {
                n |= (n4 >> 7 - (n5 + this.grAtX[0]) & 0x1) << 3;
            }
            else {
                n |= this.getPixel(this.regionBitmap, n2 + this.grAtX[0], n3 + this.grAtY[0]) << 3;
            }
        }
        if (this.grAtOverride[1]) {
            n &= 0xEFFF;
            if (this.grAtY[1] == 0 && this.grAtX[1] >= -n5) {
                n |= (n4 >> 7 - (n5 + this.grAtX[1]) & 0x1) << 12;
            }
            else {
                n |= this.getPixel(this.referenceBitmap, n2 + this.grAtX[1] + this.referenceDX, n3 + this.grAtY[1] + this.referenceDY) << 12;
            }
        }
        return n;
    }
    
    private byte getPixel(final Bitmap bitmap, final int n, final int n2) throws IOException {
        if (n < 0 || n >= bitmap.getWidth()) {
            return 0;
        }
        if (n2 < 0 || n2 >= bitmap.getHeight()) {
            return 0;
        }
        return bitmap.getPixel(n, n2);
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws IOException {
        this.segmentHeader = segmentHeader;
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(this.subInputStream);
        this.parseHeader();
    }
    
    protected void setParameters(final CX cx, final ArithmeticDecoder arithDecoder, final short templateID, final int bitmapWidth, final int bitmapHeight, final Bitmap referenceBitmap, final int referenceDX, final int referenceDY, final boolean isTPGROn, final short[] grAtX, final short[] grAtY) {
        if (null != cx) {
            this.cx = cx;
        }
        if (null != arithDecoder) {
            this.arithDecoder = arithDecoder;
        }
        this.templateID = templateID;
        this.regionInfo.setBitmapWidth(bitmapWidth);
        this.regionInfo.setBitmapHeight(bitmapHeight);
        this.referenceBitmap = referenceBitmap;
        this.referenceDX = referenceDX;
        this.referenceDY = referenceDY;
        this.isTPGROn = isTPGROn;
        this.grAtX = grAtX;
        this.grAtY = grAtY;
        this.regionBitmap = null;
    }
    
    @Override
    public RegionSegmentInformation getRegionInfo() {
        return this.regionInfo;
    }
    
    static {
        log = LoggerFactory.getLogger(GenericRefinementRegion.class);
        T0 = new Template0();
        T1 = new Template1();
    }
    
    public abstract static class Template
    {
        protected abstract short form(final short p0, final short p1, final short p2, final short p3, final short p4);
        
        protected abstract void setIndex(final CX p0);
    }
    
    private static class Template0 extends Template
    {
        @Override
        protected short form(final short n, final short n2, final short n3, final short n4, final short n5) {
            return (short)(n << 10 | n2 << 7 | n3 << 4 | n4 << 1 | n5);
        }
        
        @Override
        protected void setIndex(final CX cx) {
            cx.setIndex(256);
        }
    }
    
    private static class Template1 extends Template
    {
        @Override
        protected short form(final short n, final short n2, final short n3, final short n4, final short n5) {
            return (short)((n & 0x2) << 8 | n2 << 6 | (n3 & 0x3) << 4 | n4 << 1 | n5);
        }
        
        @Override
        protected void setIndex(final CX cx) {
            cx.setIndex(128);
        }
    }
}
