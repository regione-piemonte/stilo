// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import org.apache.pdfbox.jbig2.SegmentHeader;
import java.io.IOException;
import org.apache.pdfbox.jbig2.util.CombinationOperator;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.SegmentData;

public class RegionSegmentInformation implements SegmentData
{
    private SubInputStream subInputStream;
    private int bitmapWidth;
    private int bitmapHeight;
    private int xLocation;
    private int yLocation;
    private CombinationOperator combinationOperator;
    
    public RegionSegmentInformation(final SubInputStream subInputStream) {
        this.subInputStream = subInputStream;
    }
    
    public RegionSegmentInformation() {
    }
    
    public void parseHeader() throws IOException {
        this.bitmapWidth = (int)(this.subInputStream.readBits(32) & -1L);
        this.bitmapHeight = (int)(this.subInputStream.readBits(32) & -1L);
        this.xLocation = (int)(this.subInputStream.readBits(32) & -1L);
        this.yLocation = (int)(this.subInputStream.readBits(32) & -1L);
        this.subInputStream.readBits(5);
        this.readCombinationOperator();
    }
    
    private void readCombinationOperator() throws IOException {
        this.combinationOperator = CombinationOperator.translateOperatorCodeToEnum((short)(this.subInputStream.readBits(3) & 0xFL));
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IntegerMaxValueException, IOException {
    }
    
    public void setBitmapWidth(final int bitmapWidth) {
        this.bitmapWidth = bitmapWidth;
    }
    
    public int getBitmapWidth() {
        return this.bitmapWidth;
    }
    
    public void setBitmapHeight(final int bitmapHeight) {
        this.bitmapHeight = bitmapHeight;
    }
    
    public int getBitmapHeight() {
        return this.bitmapHeight;
    }
    
    public int getXLocation() {
        return this.xLocation;
    }
    
    public int getYLocation() {
        return this.yLocation;
    }
    
    public CombinationOperator getCombinationOperator() {
        return this.combinationOperator;
    }
}
