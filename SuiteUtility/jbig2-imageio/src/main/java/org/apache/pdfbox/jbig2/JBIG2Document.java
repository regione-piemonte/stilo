// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import java.io.EOFException;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.TreeMap;
import java.io.IOException;
import javax.imageio.stream.ImageInputStream;
import org.apache.pdfbox.jbig2.io.SubInputStream;
import java.util.Map;
import org.apache.pdfbox.jbig2.util.log.Logger;

class JBIG2Document
{
    private static final Logger log;
    private int[] FILE_HEADER_ID;
    private final Map<Integer, JBIG2Page> pages;
    private short fileHeaderLength;
    private short organisationType;
    public static final int RANDOM = 0;
    public static final int SEQUENTIAL = 1;
    private boolean amountOfPagesUnknown;
    private int amountOfPages;
    private boolean gbUseExtTemplate;
    private final SubInputStream subInputStream;
    private JBIG2Globals globalSegments;
    
    protected JBIG2Document(final ImageInputStream imageInputStream) throws IOException {
        this(imageInputStream, null);
    }
    
    protected JBIG2Document(final ImageInputStream imageInputStream, final JBIG2Globals globalSegments) throws IOException {
        this.FILE_HEADER_ID = new int[] { 151, 74, 66, 50, 13, 10, 26, 10 };
        this.pages = new TreeMap<Integer, JBIG2Page>();
        this.fileHeaderLength = 9;
        this.organisationType = 1;
        this.amountOfPagesUnknown = true;
        if (imageInputStream == null) {
            throw new IllegalArgumentException("imageInputStream must not be null");
        }
        this.subInputStream = new SubInputStream(imageInputStream, 0L, Long.MAX_VALUE);
        this.globalSegments = globalSegments;
        this.mapStream();
    }
    
    SegmentHeader getGlobalSegment(final int n) {
        if (null != this.globalSegments) {
            return this.globalSegments.getSegment(n);
        }
        if (JBIG2Document.log.isErrorEnabled()) {
            JBIG2Document.log.error("Segment not found. Returning null.");
        }
        return null;
    }
    
    protected JBIG2Page getPage(final int i) {
        return this.pages.get(i);
    }
    
    protected int getAmountOfPages() throws IOException {
        if (this.amountOfPagesUnknown || this.amountOfPages == 0) {
            if (this.pages.size() == 0) {
                this.mapStream();
            }
            return this.pages.size();
        }
        return this.amountOfPages;
    }
    
    private void mapStream() throws IOException {
        final LinkedList<SegmentHeader> list = new LinkedList<SegmentHeader>();
        long streamPosition = 0L;
        int segmentType = 0;
        if (this.isFileHeaderPresent()) {
            this.parseFileHeader();
            streamPosition += this.fileHeaderLength;
        }
        if (this.globalSegments == null) {
            this.globalSegments = new JBIG2Globals();
        }
        while (segmentType != 51 && !this.reachedEndOfStream(streamPosition)) {
            final SegmentHeader segmentHeader = new SegmentHeader(this, this.subInputStream, streamPosition, this.organisationType);
            final int pageAssociation = segmentHeader.getPageAssociation();
            segmentType = segmentHeader.getSegmentType();
            if (pageAssociation != 0) {
                JBIG2Page page = this.getPage(pageAssociation);
                if (page == null) {
                    page = new JBIG2Page(this, pageAssociation);
                    this.pages.put(pageAssociation, page);
                }
                page.add(segmentHeader);
            }
            else {
                this.globalSegments.addSegment(segmentHeader.getSegmentNr(), segmentHeader);
            }
            list.add(segmentHeader);
            streamPosition = this.subInputStream.getStreamPosition();
            if (this.organisationType == 1) {
                streamPosition += segmentHeader.getSegmentDataLength();
            }
        }
        this.determineRandomDataOffsets(list, streamPosition);
    }
    
    private boolean isFileHeaderPresent() throws IOException {
        final SubInputStream subInputStream = this.subInputStream;
        subInputStream.mark();
        final int[] file_HEADER_ID = this.FILE_HEADER_ID;
        for (int length = file_HEADER_ID.length, i = 0; i < length; ++i) {
            if (file_HEADER_ID[i] != subInputStream.read()) {
                subInputStream.reset();
                return false;
            }
        }
        subInputStream.reset();
        return true;
    }
    
    private void determineRandomDataOffsets(final List<SegmentHeader> list, long segmentDataStartOffset) {
        if (this.organisationType == 0) {
            for (final SegmentHeader segmentHeader : list) {
                segmentHeader.setSegmentDataStartOffset(segmentDataStartOffset);
                segmentDataStartOffset += segmentHeader.getSegmentDataLength();
            }
        }
    }
    
    private void parseFileHeader() throws IOException {
        this.subInputStream.seek(0L);
        this.subInputStream.skipBytes(8);
        this.subInputStream.readBits(5);
        if (this.subInputStream.readBit() == 1) {
            this.gbUseExtTemplate = true;
        }
        if (this.subInputStream.readBit() != 1) {
            this.amountOfPagesUnknown = false;
        }
        this.organisationType = (short)this.subInputStream.readBit();
        if (!this.amountOfPagesUnknown) {
            this.amountOfPages = (int)this.subInputStream.readUnsignedInt();
            this.fileHeaderLength = 13;
        }
    }
    
    private boolean reachedEndOfStream(final long pos) throws IOException {
        try {
            this.subInputStream.seek(pos);
            this.subInputStream.readBits(32);
            return false;
        }
        catch (EOFException ex) {
            return true;
        }
        catch (IndexOutOfBoundsException ex2) {
            return true;
        }
    }
    
    protected JBIG2Globals getGlobalSegments() {
        return this.globalSegments;
    }
    
    protected boolean isAmountOfPagesUnknown() {
        return this.amountOfPagesUnknown;
    }
    
    boolean isGbUseExtTemplate() {
        return this.gbUseExtTemplate;
    }
    
    static {
        log = LoggerFactory.getLogger(JBIG2Document.class);
    }
}
