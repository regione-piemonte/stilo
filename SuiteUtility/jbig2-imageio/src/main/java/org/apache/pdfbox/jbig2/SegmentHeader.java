// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import org.apache.pdfbox.jbig2.segments.Table;
import org.apache.pdfbox.jbig2.segments.Profiles;
import org.apache.pdfbox.jbig2.segments.EndOfStripe;
import org.apache.pdfbox.jbig2.segments.PageInformation;
import org.apache.pdfbox.jbig2.segments.GenericRefinementRegion;
import org.apache.pdfbox.jbig2.segments.GenericRegion;
import org.apache.pdfbox.jbig2.segments.HalftoneRegion;
import org.apache.pdfbox.jbig2.segments.PatternDictionary;
import org.apache.pdfbox.jbig2.segments.TextRegion;
import org.apache.pdfbox.jbig2.segments.SymbolDictionary;
import java.util.HashMap;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import java.lang.ref.SoftReference;
import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import java.lang.ref.Reference;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import java.util.Map;
import org.apache.pdfbox.jbig2.util.log.Logger;

public class SegmentHeader
{
    private static final Logger log;
    private static final Map<Integer, Class<? extends SegmentData>> SEGMENT_TYPE_MAP;
    private int segmentNr;
    private int segmentType;
    private byte retainFlag;
    private int pageAssociation;
    private byte pageAssociationFieldSize;
    private SegmentHeader[] rtSegments;
    private long segmentHeaderLength;
    private long segmentDataLength;
    private long segmentDataStartOffset;
    private final SubInputStream subInputStream;
    private Reference<SegmentData> segmentData;
    
    public SegmentHeader(final JBIG2Document jbig2Document, final SubInputStream subInputStream, final long n, final int n2) throws IOException {
        this.parse(jbig2Document, this.subInputStream = subInputStream, n, n2);
    }
    
    private void parse(final JBIG2Document jbig2Document, final ImageInputStream imageInputStream, final long lng, final int n) throws IOException {
        this.printDebugMessage("\n########################");
        this.printDebugMessage("Segment parsing started.");
        imageInputStream.seek(lng);
        this.printDebugMessage("|-Seeked to offset: " + lng);
        this.readSegmentNumber(imageInputStream);
        this.readSegmentHeaderFlag(imageInputStream);
        final int amountOfReferredToSegments = this.readAmountOfReferredToSegments(imageInputStream);
        this.readSegmentPageAssociation(jbig2Document, imageInputStream, amountOfReferredToSegments, this.readReferredToSegmentsNumbers(imageInputStream, amountOfReferredToSegments));
        this.readSegmentDataLength(imageInputStream);
        this.readDataStartOffset(imageInputStream, n);
        this.readSegmentHeaderLength(imageInputStream, lng);
        this.printDebugMessage("########################\n");
    }
    
    private void readSegmentNumber(final ImageInputStream imageInputStream) throws IOException {
        this.segmentNr = (int)(imageInputStream.readBits(32) & -1L);
        this.printDebugMessage("|-Segment Nr: " + this.segmentNr);
    }
    
    private void readSegmentHeaderFlag(final ImageInputStream imageInputStream) throws IOException {
        this.retainFlag = (byte)imageInputStream.readBit();
        this.printDebugMessage("|-Retain flag: " + this.retainFlag);
        this.pageAssociationFieldSize = (byte)imageInputStream.readBit();
        this.printDebugMessage("|-Page association field size=" + this.pageAssociationFieldSize);
        this.segmentType = (int)(imageInputStream.readBits(6) & 0xFFL);
        this.printDebugMessage("|-Segment type=" + this.segmentType);
    }
    
    private int readAmountOfReferredToSegments(final ImageInputStream imageInputStream) throws IOException {
        int i = (int)(imageInputStream.readBits(3) & 0xFL);
        this.printDebugMessage("|-RTS count: " + i);
        this.printDebugMessage("  |-Stream position before RTS: " + imageInputStream.getStreamPosition());
        if (i <= 4) {
            final byte[] array = new byte[5];
            for (int j = 0; j <= 4; ++j) {
                array[j] = (byte)imageInputStream.readBit();
            }
        }
        else {
            i = (int)(imageInputStream.readBits(29) & -1L);
            final int n;
            final byte[] array2 = new byte[n = i + 8 >> 3 << 3];
            for (int k = 0; k < n; ++k) {
                array2[k] = (byte)imageInputStream.readBit();
            }
        }
        this.printDebugMessage("  |-Stream position after RTS: " + imageInputStream.getStreamPosition());
        return i;
    }
    
    private int[] readReferredToSegmentsNumbers(final ImageInputStream imageInputStream, final int n) throws IOException {
        final int[] array = new int[n];
        if (n > 0) {
            int n2 = 1;
            if (this.segmentNr > 256) {
                n2 = 2;
                if (this.segmentNr > 65536) {
                    n2 = 4;
                }
            }
            this.rtSegments = new SegmentHeader[n];
            this.printDebugMessage("|-Length of RT segments list: " + this.rtSegments.length);
            for (int i = 0; i < n; ++i) {
                array[i] = (int)(imageInputStream.readBits(n2 << 3) & -1L);
            }
        }
        return array;
    }
    
    private void readSegmentPageAssociation(final JBIG2Document jbig2Document, final ImageInputStream imageInputStream, final int n, final int[] array) throws IOException {
        if (this.pageAssociationFieldSize == 0) {
            this.pageAssociation = (short)(imageInputStream.readBits(8) & 0xFFL);
        }
        else {
            this.pageAssociation = (int)(imageInputStream.readBits(32) & -1L);
        }
        if (n > 0) {
            final JBIG2Page page = jbig2Document.getPage(this.pageAssociation);
            for (int i = 0; i < n; ++i) {
                this.rtSegments[i] = ((null != page) ? page.getSegment(array[i]) : jbig2Document.getGlobalSegment(array[i]));
            }
        }
    }
    
    private void readSegmentDataLength(final ImageInputStream imageInputStream) throws IOException {
        this.segmentDataLength = (imageInputStream.readBits(32) & -1L);
        this.printDebugMessage("|-Data length: " + this.segmentDataLength);
    }
    
    private void readDataStartOffset(final ImageInputStream imageInputStream, final int n) throws IOException {
        if (n == 1) {
            this.printDebugMessage("|-Organization is sequential.");
            this.segmentDataStartOffset = imageInputStream.getStreamPosition();
        }
    }
    
    private void readSegmentHeaderLength(final ImageInputStream imageInputStream, final long n) throws IOException {
        this.segmentHeaderLength = imageInputStream.getStreamPosition() - n;
        this.printDebugMessage("|-Segment header length: " + this.segmentHeaderLength);
    }
    
    private void printDebugMessage(final String s) {
        SegmentHeader.log.debug(s);
    }
    
    public int getSegmentNr() {
        return this.segmentNr;
    }
    
    public int getSegmentType() {
        return this.segmentType;
    }
    
    public long getSegmentHeaderLength() {
        return this.segmentHeaderLength;
    }
    
    public long getSegmentDataLength() {
        return this.segmentDataLength;
    }
    
    public long getSegmentDataStartOffset() {
        return this.segmentDataStartOffset;
    }
    
    public void setSegmentDataStartOffset(final long segmentDataStartOffset) {
        this.segmentDataStartOffset = segmentDataStartOffset;
    }
    
    public SegmentHeader[] getRtSegments() {
        return this.rtSegments;
    }
    
    public int getPageAssociation() {
        return this.pageAssociation;
    }
    
    public short getRetainFlag() {
        return this.retainFlag;
    }
    
    public SubInputStream getDataInputStream() {
        return new SubInputStream(this.subInputStream, this.segmentDataStartOffset, this.segmentDataLength);
    }
    
    public SegmentData getSegmentData() {
        SegmentData referent = null;
        if (null != this.segmentData) {
            referent = this.segmentData.get();
        }
        if (null == referent) {
            try {
                final Class<? extends SegmentData> clazz = SegmentHeader.SEGMENT_TYPE_MAP.get(this.segmentType);
                if (null == clazz) {
                    throw new IllegalArgumentException("No segment class for type " + this.segmentType);
                }
                referent = (SegmentData)clazz.newInstance();
                referent.init(this, this.getDataInputStream());
                this.segmentData = new SoftReference<SegmentData>(referent);
            }
            catch (Exception cause) {
                throw new RuntimeException("Can't instantiate segment class", cause);
            }
        }
        return referent;
    }
    
    public void cleanSegmentData() {
        if (this.segmentData != null) {
            this.segmentData = null;
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.rtSegments != null) {
            final SegmentHeader[] rtSegments = this.rtSegments;
            for (int length = rtSegments.length, i = 0; i < length; ++i) {
                sb.append(rtSegments[i].segmentNr + " ");
            }
        }
        else {
            sb.append("none");
        }
        return "\n#SegmentNr: " + this.segmentNr + "\n SegmentType: " + this.segmentType + "\n PageAssociation: " + this.pageAssociation + "\n Referred-to segments: " + sb.toString() + "\n";
    }
    
    static {
        log = LoggerFactory.getLogger(SegmentHeader.class);
        SEGMENT_TYPE_MAP = new HashMap<Integer, Class<? extends SegmentData>>();
        final Object[][] array = { { 0, SymbolDictionary.class }, { 4, TextRegion.class }, { 6, TextRegion.class }, { 7, TextRegion.class }, { 16, PatternDictionary.class }, { 20, HalftoneRegion.class }, { 22, HalftoneRegion.class }, { 23, HalftoneRegion.class }, { 36, GenericRegion.class }, { 38, GenericRegion.class }, { 39, GenericRegion.class }, { 40, GenericRefinementRegion.class }, { 42, GenericRefinementRegion.class }, { 43, GenericRefinementRegion.class }, { 48, PageInformation.class }, { 50, EndOfStripe.class }, { 52, Profiles.class }, { 53, Table.class } };
        for (int i = 0; i < array.length; ++i) {
            final Object[] array2 = array[i];
            SegmentHeader.SEGMENT_TYPE_MAP.put((Integer)array2[0], (Class<? extends SegmentData>)array2[1]);
        }
    }
}
