// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.segments;

import org.apache.pdfbox.jbig2.SegmentHeader;
import org.apache.pdfbox.jbig2.image.Bitmaps;
import java.awt.Rectangle;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import java.io.IOException;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.jbig2.Bitmap;
import java.util.ArrayList;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.Dictionary;

public class PatternDictionary implements Dictionary
{
    private final Logger log;
    private SubInputStream subInputStream;
    private long dataHeaderOffset;
    private long dataHeaderLength;
    private long dataOffset;
    private long dataLength;
    private short[] gbAtX;
    private short[] gbAtY;
    private boolean isMMREncoded;
    private byte hdTemplate;
    private short hdpWidth;
    private short hdpHeight;
    private ArrayList<Bitmap> patterns;
    private int grayMax;
    
    public PatternDictionary() {
        this.log = LoggerFactory.getLogger(PatternDictionary.class);
        this.gbAtX = null;
        this.gbAtY = null;
    }
    
    private void parseHeader() throws IOException, InvalidHeaderValueException {
        this.subInputStream.readBits(5);
        this.readTemplate();
        this.readIsMMREncoded();
        this.readPatternWidthAndHeight();
        this.readGrayMax();
        this.computeSegmentDataStructure();
        this.checkInput();
    }
    
    private void readTemplate() throws IOException {
        this.hdTemplate = (byte)this.subInputStream.readBits(2);
    }
    
    private void readIsMMREncoded() throws IOException {
        if (this.subInputStream.readBit() == 1) {
            this.isMMREncoded = true;
        }
    }
    
    private void readPatternWidthAndHeight() throws IOException {
        this.hdpWidth = this.subInputStream.readByte();
        this.hdpHeight = this.subInputStream.readByte();
    }
    
    private void readGrayMax() throws IOException {
        this.grayMax = (int)(this.subInputStream.readBits(32) & -1L);
    }
    
    private void computeSegmentDataStructure() throws IOException {
        this.dataOffset = this.subInputStream.getStreamPosition();
        this.dataHeaderLength = this.dataOffset - this.dataHeaderOffset;
        this.dataLength = this.subInputStream.length() - this.dataHeaderLength;
    }
    
    private void checkInput() throws InvalidHeaderValueException {
        if (this.hdpHeight < 1 || this.hdpWidth < 1) {
            throw new InvalidHeaderValueException("Width/Heigth must be greater than zero.");
        }
        if (this.isMMREncoded && this.hdTemplate != 0) {
            this.log.info("hdTemplate should contain the value 0");
        }
    }
    
    @Override
    public ArrayList<Bitmap> getDictionary() throws IOException, InvalidHeaderValueException {
        if (null == this.patterns) {
            if (!this.isMMREncoded) {
                this.setGbAtPixels();
            }
            final GenericRegion genericRegion = new GenericRegion(this.subInputStream);
            genericRegion.setParameters(this.isMMREncoded, this.dataOffset, this.dataLength, this.hdpHeight, (this.grayMax + 1) * this.hdpWidth, this.hdTemplate, false, false, this.gbAtX, this.gbAtY);
            this.extractPatterns(genericRegion.getRegionBitmap());
        }
        return this.patterns;
    }
    
    private void extractPatterns(final Bitmap bitmap) {
        short n = 0;
        this.patterns = new ArrayList<Bitmap>(this.grayMax + 1);
        while (n <= this.grayMax) {
            this.patterns.add(Bitmaps.extract(new Rectangle(this.hdpWidth * n, 0, this.hdpWidth, this.hdpHeight), bitmap));
            ++n;
        }
    }
    
    private void setGbAtPixels() {
        if (this.hdTemplate == 0) {
            this.gbAtX = new short[4];
            this.gbAtY = new short[4];
            this.gbAtX[0] = (short)(-this.hdpWidth);
            this.gbAtY[0] = 0;
            this.gbAtX[1] = -3;
            this.gbAtY[1] = -1;
            this.gbAtX[2] = 2;
            this.gbAtY[2] = -2;
            this.gbAtX[3] = -2;
            this.gbAtY[3] = -2;
        }
        else {
            this.gbAtX = new short[1];
            this.gbAtY = new short[1];
            this.gbAtX[0] = (short)(-this.hdpWidth);
            this.gbAtY[0] = 0;
        }
    }
    
    @Override
    public void init(final SegmentHeader segmentHeader, final SubInputStream subInputStream) throws InvalidHeaderValueException, IOException {
        this.subInputStream = subInputStream;
        this.parseHeader();
    }
    
    protected boolean isMMREncoded() {
        return this.isMMREncoded;
    }
    
    protected byte getHdTemplate() {
        return this.hdTemplate;
    }
    
    protected short getHdpWidth() {
        return this.hdpWidth;
    }
    
    protected short getHdpHeight() {
        return this.hdpHeight;
    }
    
    protected int getGrayMax() {
        return this.grayMax;
    }
}
