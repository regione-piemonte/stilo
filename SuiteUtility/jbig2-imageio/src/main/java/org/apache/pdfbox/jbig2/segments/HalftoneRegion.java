// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import java.util.Collection;
import org.apache.pdfbox.jbig2.image.Bitmaps;
import java.util.Arrays;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import java.util.ArrayList;
import org.apache.pdfbox.jbig2.Bitmap;
import org.apache.pdfbox.jbig2.util.CombinationOperator;
import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.Region;

public class HalftoneRegion implements Region
{
    private final Logger log;
    private SubInputStream subInputStream;
    private SegmentHeader segmentHeader;
    private long dataHeaderOffset;
    private long dataHeaderLength;
    private long dataOffset;
    private long dataLength;
    private RegionSegmentInformation regionInfo;
    private byte hDefaultPixel;
    private CombinationOperator hCombinationOperator;
    private boolean hSkipEnabled;
    private byte hTemplate;
    private boolean isMMREncoded;
    private int hGridWidth;
    private int hGridHeight;
    private int hGridX;
    private int hGridY;
    private int hRegionX;
    private int hRegionY;
    private Bitmap halftoneRegionBitmap;
    private ArrayList<Bitmap> patterns;
    
    public HalftoneRegion() {
        this.log = LoggerFactory.getLogger(HalftoneRegion.class);
    }
    
    public HalftoneRegion(final SubInputStream subInputStream) {
        this.log = LoggerFactory.getLogger(HalftoneRegion.class);
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(subInputStream);
    }
    
    public HalftoneRegion(final SubInputStream subInputStream, final SegmentHeader segmentHeader) {
        this.log = LoggerFactory.getLogger(HalftoneRegion.class);
        this.subInputStream = subInputStream;
        this.segmentHeader = segmentHeader;
        this.regionInfo = new RegionSegmentInformation(subInputStream);
    }
    
    private void parseHeader() throws IOException, InvalidHeaderValueException {
        this.regionInfo.parseHeader();
        this.hDefaultPixel = (byte)this.subInputStream.readBit();
        this.hCombinationOperator = CombinationOperator.translateOperatorCodeToEnum((short)(this.subInputStream.readBits(3) & 0xFL));
        if (this.subInputStream.readBit() == 1) {
            this.hSkipEnabled = true;
        }
        this.hTemplate = (byte)(this.subInputStream.readBits(2) & 0xFL);
        if (this.subInputStream.readBit() == 1) {
            this.isMMREncoded = true;
        }
        this.hGridWidth = (int)(this.subInputStream.readBits(32) & -1L);
        this.hGridHeight = (int)(this.subInputStream.readBits(32) & -1L);
        this.hGridX = (int)this.subInputStream.readBits(32);
        this.hGridY = (int)this.subInputStream.readBits(32);
        this.hRegionX = ((int)this.subInputStream.readBits(16) & 0xFFFF);
        this.hRegionY = ((int)this.subInputStream.readBits(16) & 0xFFFF);
        this.computeSegmentDataStructure();
        this.checkInput();
    }
    
    private void computeSegmentDataStructure() throws IOException {
        this.dataOffset = this.subInputStream.getStreamPosition();
        this.dataHeaderLength = this.dataOffset - this.dataHeaderOffset;
        this.dataLength = this.subInputStream.length() - this.dataHeaderLength;
    }
    
    private void checkInput() throws InvalidHeaderValueException {
        if (this.isMMREncoded) {
            if (this.hTemplate != 0) {
                this.log.info("hTemplate = " + this.hTemplate + " (should contain the value 0)");
            }
            if (this.hSkipEnabled) {
                this.log.info("hSkipEnabled 0 " + this.hSkipEnabled + " (should contain the value false)");
            }
        }
    }
    
    @Override
    public Bitmap getRegionBitmap() throws IOException, InvalidHeaderValueException {
        if (null == this.halftoneRegionBitmap) {
            this.halftoneRegionBitmap = new Bitmap(this.regionInfo.getBitmapWidth(), this.regionInfo.getBitmapHeight());
            if (this.patterns == null) {
                this.patterns = this.getPatterns();
            }
            if (this.hDefaultPixel == 1) {
                Arrays.fill(this.halftoneRegionBitmap.getByteArray(), (byte)(-1));
            }
            this.renderPattern(this.grayScaleDecoding((int)Math.ceil(Math.log(this.patterns.size()) / Math.log(2.0))));
        }
        return this.halftoneRegionBitmap;
    }
    
    private void renderPattern(final int[][] array) {
        for (int i = 0; i < this.hGridHeight; ++i) {
            for (int j = 0; j < this.hGridWidth; ++j) {
                Bitmaps.blit(this.patterns.get(array[i][j]), this.halftoneRegionBitmap, this.computeX(i, j) + this.hGridX, this.computeY(i, j) + this.hGridY, this.hCombinationOperator);
            }
        }
    }
    
    private ArrayList<Bitmap> getPatterns() throws InvalidHeaderValueException, IOException {
        final ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        final SegmentHeader[] rtSegments = this.segmentHeader.getRtSegments();
        for (int length = rtSegments.length, i = 0; i < length; ++i) {
            list.addAll(((PatternDictionary)rtSegments[i].getSegmentData()).getDictionary());
        }
        return list;
    }
    
    private int[][] grayScaleDecoding(final int n) throws IOException {
        short[] array = null;
        short[] array2 = null;
        if (!this.isMMREncoded) {
            array = new short[4];
            array2 = new short[4];
            if (this.hTemplate <= 1) {
                array[0] = 3;
            }
            else if (this.hTemplate >= 2) {
                array[0] = 2;
            }
            array2[0] = -1;
            array[1] = -3;
            array2[1] = -1;
            array[2] = 2;
            array2[2] = -2;
            array2[3] = (array[3] = -2);
        }
        Bitmap[] combineGrayScalePlanes = new Bitmap[n];
        final GenericRegion genericRegion = new GenericRegion(this.subInputStream);
        genericRegion.setParameters(this.isMMREncoded, this.dataOffset, this.dataLength, this.hGridHeight, this.hGridWidth, this.hTemplate, false, this.hSkipEnabled, array, array2);
        int i = n - 1;
        combineGrayScalePlanes[i] = genericRegion.getRegionBitmap();
        while (i > 0) {
            --i;
            genericRegion.resetBitmap();
            combineGrayScalePlanes[i] = genericRegion.getRegionBitmap();
            combineGrayScalePlanes = this.combineGrayScalePlanes(combineGrayScalePlanes, i);
        }
        return this.computeGrayScaleValues(combineGrayScalePlanes, n);
    }
    
    private Bitmap[] combineGrayScalePlanes(final Bitmap[] array, final int n) {
        int n2 = 0;
        for (int i = 0; i < array[n].getHeight(); ++i) {
            for (int j = 0; j < array[n].getWidth(); j += 8) {
                array[n].setByte(n2++, Bitmaps.combineBytes(array[n].getByte(n2), array[n + 1].getByte(n2), CombinationOperator.XOR));
            }
        }
        return array;
    }
    
    private int[][] computeGrayScaleValues(final Bitmap[] array, final int n) {
        final int[][] array2 = new int[this.hGridHeight][this.hGridWidth];
        for (int i = 0; i < this.hGridHeight; ++i) {
            for (int j = 0; j < this.hGridWidth; j += 8) {
                final int n2 = (this.hGridWidth - j > 8) ? 8 : (this.hGridWidth - j);
                final int byteIndex = array[0].getByteIndex(j, i);
                for (int k = 0; k < n2; ++k) {
                    final int n3 = k + j;
                    array2[i][n3] = 0;
                    for (int l = 0; l < n; ++l) {
                        final int[] array3 = array2[i];
                        final int n4 = n3;
                        array3[n4] += (array[l].getByte(byteIndex) >> (7 - n3 & 0x7) & 0x1) * (1 << l);
                    }
                }
            }
        }
        return array2;
    }
    
    private int computeX(final int n, final int n2) {
        return this.shiftAndFill(this.hGridX + n * this.hRegionY + n2 * this.hRegionX);
    }
    
    private int computeY(final int n, final int n2) {
        return this.shiftAndFill(this.hGridY + n * this.hRegionX - n2 * this.hRegionY);
    }
    
    private int shiftAndFill(int i) {
        i >>= 8;
        if (i < 0) {
            for (int n = (int)(Math.log(Integer.highestOneBit(i)) / Math.log(2.0)), j = 1; j < 31 - n; ++j) {
                i |= 1 << 31 - j;
            }
        }
        return i;
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IOException {
        this.segmentHeader = segmentHeader;
        this.subInputStream = subInputStream;
        this.regionInfo = new RegionSegmentInformation(this.subInputStream);
        this.parseHeader();
    }
    
    public CombinationOperator getCombinationOperator() {
        return this.hCombinationOperator;
    }
    
    @Override
    public RegionSegmentInformation getRegionInfo() {
        return this.regionInfo;
    }
    
    protected byte getHTemplate() {
        return this.hTemplate;
    }
    
    protected boolean isHSkipEnabled() {
        return this.hSkipEnabled;
    }
    
    protected boolean isMMREncoded() {
        return this.isMMREncoded;
    }
    
    protected int getHGridWidth() {
        return this.hGridWidth;
    }
    
    protected int getHGridHeight() {
        return this.hGridHeight;
    }
    
    protected int getHGridX() {
        return this.hGridX;
    }
    
    protected int getHGridY() {
        return this.hGridY;
    }
    
    protected int getHRegionX() {
        return this.hRegionX;
    }
    
    protected int getHRegionY() {
        return this.hRegionY;
    }
    
    protected byte getHDefaultPixel() {
        return this.hDefaultPixel;
    }
}
