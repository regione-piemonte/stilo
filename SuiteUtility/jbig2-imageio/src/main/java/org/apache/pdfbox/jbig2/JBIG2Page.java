// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.apache.pdfbox.jbig2.util.CombinationOperator;
import java.util.ArrayList;
import org.apache.pdfbox.jbig2.segments.EndOfStripe;
import org.apache.pdfbox.jbig2.segments.RegionSegmentInformation;
import org.apache.pdfbox.jbig2.image.Bitmaps;
import java.util.Arrays;
import org.apache.pdfbox.jbig2.err.InvalidHeaderValueException;
import org.apache.pdfbox.jbig2.err.IntegerMaxValueException;
import org.apache.pdfbox.jbig2.segments.PageInformation;
import java.io.IOException;
import org.apache.pdfbox.jbig2.err.JBIG2Exception;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;
import org.apache.pdfbox.jbig2.util.log.Logger;

class JBIG2Page
{
    private static final Logger log;
    private final Map<Integer, SegmentHeader> segments;
    private final int pageNumber;
    private Bitmap pageBitmap;
    private int finalHeight;
    private int finalWidth;
    private int resolutionX;
    private int resolutionY;
    private final JBIG2Document document;
    
    protected JBIG2Page(final JBIG2Document document, final int pageNumber) {
        this.segments = new TreeMap<Integer, SegmentHeader>();
        this.document = document;
        this.pageNumber = pageNumber;
    }
    
    public SegmentHeader getSegment(final int i) {
        final SegmentHeader segmentHeader = this.segments.get(i);
        if (null != segmentHeader) {
            return segmentHeader;
        }
        if (null != this.document) {
            return this.document.getGlobalSegment(i);
        }
        JBIG2Page.log.info("Segment not found, returning null.");
        return null;
    }
    
    protected SegmentHeader getPageInformationSegment() {
        for (final SegmentHeader segmentHeader : this.segments.values()) {
            if (segmentHeader.getSegmentType() == 48) {
                return segmentHeader;
            }
        }
        JBIG2Page.log.info("Page information segment not found.");
        return null;
    }
    
    protected Bitmap getBitmap() throws JBIG2Exception, IOException {
        if (null == this.pageBitmap) {
            this.composePageBitmap();
        }
        return this.pageBitmap;
    }
    
    private void composePageBitmap() throws IOException, JBIG2Exception {
        if (this.pageNumber > 0) {
            this.createPage((PageInformation)this.getPageInformationSegment().getSegmentData());
            this.clearSegmentData();
        }
    }
    
    private void createPage(final PageInformation pageInformation) throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        if (!pageInformation.isStriped() || pageInformation.getHeight() != -1) {
            this.createNormalPage(pageInformation);
        }
        else {
            this.createStripedPage(pageInformation);
        }
    }
    
    private void createNormalPage(final PageInformation pageInformation) throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        this.pageBitmap = new Bitmap(pageInformation.getWidth(), pageInformation.getHeight());
        if (pageInformation.getDefaultPixelValue() != 0) {
            Arrays.fill(this.pageBitmap.getByteArray(), (byte)(-1));
        }
        for (final SegmentHeader segmentHeader : this.segments.values()) {
            switch (segmentHeader.getSegmentType()) {
                case 6:
                case 7:
                case 22:
                case 23:
                case 38:
                case 39:
                case 42:
                case 43: {
                    final Region region = (Region)segmentHeader.getSegmentData();
                    final Bitmap regionBitmap = region.getRegionBitmap();
                    if (this.fitsPage(pageInformation, regionBitmap)) {
                        this.pageBitmap = regionBitmap;
                        continue;
                    }
                    final RegionSegmentInformation regionInfo = region.getRegionInfo();
                    Bitmaps.blit(regionBitmap, this.pageBitmap, regionInfo.getXLocation(), regionInfo.getYLocation(), this.getCombinationOperator(pageInformation, regionInfo.getCombinationOperator()));
                    continue;
                }
            }
        }
    }
    
    private boolean fitsPage(final PageInformation pageInformation, final Bitmap bitmap) {
        return this.countRegions() == 1 && pageInformation.getDefaultPixelValue() == 0 && pageInformation.getWidth() == bitmap.getWidth() && pageInformation.getHeight() == bitmap.getHeight();
    }
    
    private void createStripedPage(final PageInformation pageInformation) throws IOException, IntegerMaxValueException, InvalidHeaderValueException {
        final ArrayList<SegmentData> collectPageStripes = this.collectPageStripes();
        this.pageBitmap = new Bitmap(pageInformation.getWidth(), this.finalHeight);
        int n = 0;
        for (final SegmentData segmentData : collectPageStripes) {
            if (segmentData instanceof EndOfStripe) {
                n = ((EndOfStripe)segmentData).getLineNumber() + 1;
            }
            else {
                final Region region = (Region)segmentData;
                final RegionSegmentInformation regionInfo = region.getRegionInfo();
                Bitmaps.blit(region.getRegionBitmap(), this.pageBitmap, regionInfo.getXLocation(), n, this.getCombinationOperator(pageInformation, regionInfo.getCombinationOperator()));
            }
        }
    }
    
    private ArrayList<SegmentData> collectPageStripes() {
        final ArrayList<Region> list = (ArrayList<Region>)new ArrayList<SegmentData>();
        for (final SegmentHeader segmentHeader : this.segments.values()) {
            switch (segmentHeader.getSegmentType()) {
                case 6:
                case 7:
                case 22:
                case 23:
                case 38:
                case 39:
                case 42:
                case 43: {
                    list.add((Region)segmentHeader.getSegmentData());
                    continue;
                }
                case 50: {
                    final EndOfStripe e = (EndOfStripe)segmentHeader.getSegmentData();
                    list.add((Region)e);
                    this.finalHeight = e.getLineNumber() + 1;
                    continue;
                }
            }
        }
        return (ArrayList<SegmentData>)list;
    }
    
    private int countRegions() {
        int n = 0;
        final Iterator<SegmentHeader> iterator = this.segments.values().iterator();
        while (iterator.hasNext()) {
            switch (iterator.next().getSegmentType()) {
                case 6:
                case 7:
                case 22:
                case 23:
                case 38:
                case 39:
                case 42:
                case 43: {
                    ++n;
                    continue;
                }
            }
        }
        return n;
    }
    
    private CombinationOperator getCombinationOperator(final PageInformation pageInformation, final CombinationOperator combinationOperator) {
        if (pageInformation.isCombinationOperatorOverrideAllowed()) {
            return combinationOperator;
        }
        return pageInformation.getCombinationOperator();
    }
    
    protected void add(final SegmentHeader segmentHeader) {
        this.segments.put(segmentHeader.getSegmentNr(), segmentHeader);
    }
    
    private void clearSegmentData() {
        final Iterator<Integer> iterator = this.segments.keySet().iterator();
        while (iterator.hasNext()) {
            this.segments.get(iterator.next()).cleanSegmentData();
        }
    }
    
    protected void clearPageData() {
        this.pageBitmap = null;
    }
    
    protected int getHeight() throws IOException, JBIG2Exception {
        if (this.finalHeight == 0) {
            final PageInformation pageInformation = (PageInformation)this.getPageInformationSegment().getSegmentData();
            if (pageInformation.getHeight() == -1) {
                this.getBitmap();
            }
            else {
                this.finalHeight = pageInformation.getHeight();
            }
        }
        return this.finalHeight;
    }
    
    protected int getWidth() {
        if (this.finalWidth == 0) {
            this.finalWidth = ((PageInformation)this.getPageInformationSegment().getSegmentData()).getWidth();
        }
        return this.finalWidth;
    }
    
    protected int getResolutionX() {
        if (this.resolutionX == 0) {
            this.resolutionX = ((PageInformation)this.getPageInformationSegment().getSegmentData()).getResolutionX();
        }
        return this.resolutionX;
    }
    
    protected int getResolutionY() {
        if (this.resolutionY == 0) {
            this.resolutionY = ((PageInformation)this.getPageInformationSegment().getSegmentData()).getResolutionY();
        }
        return this.resolutionY;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (Page number: " + this.pageNumber + ")";
    }
    
    static {
        log = LoggerFactory.getLogger(JBIG2Page.class);
    }
}
