// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.jbig2.util.CombinationOperator;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.SegmentData;

public class PageInformation implements SegmentData
{
    private final Logger log;
    private SubInputStream subInputStream;
    private int bitmapWidth;
    private int bitmapHeight;
    private int resolutionX;
    private int resolutionY;
    private boolean combinationOperatorOverrideAllowed;
    private CombinationOperator combinationOperator;
    private boolean requiresAuxiliaryBuffer;
    private short defaultPixelValue;
    private boolean mightContainRefinements;
    private boolean isLossless;
    private boolean isStriped;
    private short maxStripeSize;
    
    public PageInformation() {
        this.log = LoggerFactory.getLogger(PageInformation.class);
    }
    
    private void parseHeader() throws IOException, InvalidHeaderValueException {
        this.readWidthAndHeight();
        this.readResolution();
        this.subInputStream.readBit();
        this.readCombinationOperatorOverrideAllowed();
        this.readRequiresAuxiliaryBuffer();
        this.readCombinationOperator();
        this.readDefaultPixelvalue();
        this.readContainsRefinement();
        this.readIsLossless();
        this.readIsStriped();
        this.readMaxStripeSize();
        this.checkInput();
    }
    
    private void readResolution() throws IOException {
        this.resolutionX = ((int)this.subInputStream.readBits(32) & -1);
        this.resolutionY = ((int)this.subInputStream.readBits(32) & -1);
    }
    
    private void checkInput() throws InvalidHeaderValueException {
        if (this.bitmapHeight == 4294967295L && !this.isStriped) {
            this.log.info("isStriped should contaion the value true");
        }
    }
    
    private void readCombinationOperatorOverrideAllowed() throws IOException {
        if (this.subInputStream.readBit() == 1) {
            this.combinationOperatorOverrideAllowed = true;
        }
    }
    
    private void readRequiresAuxiliaryBuffer() throws IOException {
        if (this.subInputStream.readBit() == 1) {
            this.requiresAuxiliaryBuffer = true;
        }
    }
    
    private void readCombinationOperator() throws IOException {
        this.combinationOperator = CombinationOperator.translateOperatorCodeToEnum((short)(this.subInputStream.readBits(2) & 0xFL));
    }
    
    private void readDefaultPixelvalue() throws IOException {
        this.defaultPixelValue = (short)this.subInputStream.readBit();
    }
    
    private void readContainsRefinement() throws IOException {
        if (this.subInputStream.readBit() == 1) {
            this.mightContainRefinements = true;
        }
    }
    
    private void readIsLossless() throws IOException {
        if (this.subInputStream.readBit() == 1) {
            this.isLossless = true;
        }
    }
    
    private void readIsStriped() throws IOException {
        if (this.subInputStream.readBit() == 1) {
            this.isStriped = true;
        }
    }
    
    private void readMaxStripeSize() throws IOException {
        this.maxStripeSize = (short)(this.subInputStream.readBits(15) & 0xFFFFL);
    }
    
    private void readWidthAndHeight() throws IOException {
        this.bitmapWidth = (int)this.subInputStream.readBits(32);
        this.bitmapHeight = (int)this.subInputStream.readBits(32);
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IOException {
        this.subInputStream = subInputStream;
        this.parseHeader();
    }
    
    public int getWidth() {
        return this.bitmapWidth;
    }
    
    public int getHeight() {
        return this.bitmapHeight;
    }
    
    public int getResolutionX() {
        return this.resolutionX;
    }
    
    public int getResolutionY() {
        return this.resolutionY;
    }
    
    public short getDefaultPixelValue() {
        return this.defaultPixelValue;
    }
    
    public boolean isCombinationOperatorOverrideAllowed() {
        return this.combinationOperatorOverrideAllowed;
    }
    
    public CombinationOperator getCombinationOperator() {
        return this.combinationOperator;
    }
    
    public boolean isStriped() {
        return this.isStriped;
    }
    
    public short getMaxStripeSize() {
        return this.maxStripeSize;
    }
    
    public boolean isAuxiliaryBufferRequired() {
        return this.requiresAuxiliaryBuffer;
    }
    
    public boolean mightContainRefinements() {
        return this.mightContainRefinements;
    }
    
    public boolean isLossless() {
        return this.isLossless;
    }
    
    protected int getBitmapWidth() {
        return this.bitmapWidth;
    }
    
    protected int getBitmapHeight() {
        return this.bitmapHeight;
    }
}
