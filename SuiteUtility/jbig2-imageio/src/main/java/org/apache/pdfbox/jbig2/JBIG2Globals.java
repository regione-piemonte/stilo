// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2;

import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.jbig2.util.log.Logger;

public class JBIG2Globals
{
    private static final Logger log;
    private Map<Integer, SegmentHeader> globalSegments;
    
    public JBIG2Globals() {
        this.globalSegments = new HashMap<Integer, SegmentHeader>();
    }
    
    protected SegmentHeader getSegment(final int i) {
        if (this.globalSegments.size() == 0 && JBIG2Globals.log.isErrorEnabled()) {
            JBIG2Globals.log.error("No global segment added so far. Use JBIG2ImageReader.setGlobals().");
        }
        return this.globalSegments.get(i);
    }
    
    protected void addSegment(final Integer n, final SegmentHeader segmentHeader) {
        this.globalSegments.put(n, segmentHeader);
    }
    
    static {
        log = LoggerFactory.getLogger(JBIG2Globals.class);
    }
}
