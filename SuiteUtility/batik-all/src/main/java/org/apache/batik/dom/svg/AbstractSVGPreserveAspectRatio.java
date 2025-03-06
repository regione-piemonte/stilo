// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.DefaultPreserveAspectRatioHandler;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PreserveAspectRatioHandler;
import org.apache.batik.parser.PreserveAspectRatioParser;
import org.w3c.dom.DOMException;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.svg.SVGPreserveAspectRatio;

public abstract class AbstractSVGPreserveAspectRatio implements SVGPreserveAspectRatio, SVGConstants
{
    protected static final String[] ALIGN_VALUES;
    protected static final String[] MEET_OR_SLICE_VALUES;
    protected short align;
    protected short meetOrSlice;
    
    public static String getValueAsString(final short n, final short n2) {
        if (n < 1 || n > 10) {
            return null;
        }
        final String str = AbstractSVGPreserveAspectRatio.ALIGN_VALUES[n];
        if (n == 1) {
            return str;
        }
        if (n2 < 1 || n2 > 2) {
            return null;
        }
        return str + ' ' + AbstractSVGPreserveAspectRatio.MEET_OR_SLICE_VALUES[n2];
    }
    
    public AbstractSVGPreserveAspectRatio() {
        this.align = 6;
        this.meetOrSlice = 1;
    }
    
    public short getAlign() {
        return this.align;
    }
    
    public short getMeetOrSlice() {
        return this.meetOrSlice;
    }
    
    public void setAlign(final short align) {
        this.align = align;
        this.setAttributeValue(this.getValueAsString());
    }
    
    public void setMeetOrSlice(final short meetOrSlice) {
        this.meetOrSlice = meetOrSlice;
        this.setAttributeValue(this.getValueAsString());
    }
    
    public void reset() {
        this.align = 6;
        this.meetOrSlice = 1;
    }
    
    protected abstract void setAttributeValue(final String p0) throws DOMException;
    
    protected abstract DOMException createDOMException(final short p0, final String p1, final Object[] p2);
    
    protected void setValueAsString(final String s) throws DOMException {
        final PreserveAspectRatioParserHandler preserveAspectRatioHandler = new PreserveAspectRatioParserHandler();
        try {
            final PreserveAspectRatioParser preserveAspectRatioParser = new PreserveAspectRatioParser();
            preserveAspectRatioParser.setPreserveAspectRatioHandler(preserveAspectRatioHandler);
            preserveAspectRatioParser.parse(s);
            this.align = preserveAspectRatioHandler.getAlign();
            this.meetOrSlice = preserveAspectRatioHandler.getMeetOrSlice();
        }
        catch (ParseException ex) {
            throw this.createDOMException((short)13, "preserve.aspect.ratio", new Object[] { s });
        }
    }
    
    protected String getValueAsString() {
        if (this.align < 1 || this.align > 10) {
            throw this.createDOMException((short)13, "preserve.aspect.ratio.align", new Object[] { new Integer(this.align) });
        }
        final String str = AbstractSVGPreserveAspectRatio.ALIGN_VALUES[this.align];
        if (this.align == 1) {
            return str;
        }
        if (this.meetOrSlice < 1 || this.meetOrSlice > 2) {
            throw this.createDOMException((short)13, "preserve.aspect.ratio.meet.or.slice", new Object[] { new Integer(this.meetOrSlice) });
        }
        return str + ' ' + AbstractSVGPreserveAspectRatio.MEET_OR_SLICE_VALUES[this.meetOrSlice];
    }
    
    static {
        ALIGN_VALUES = new String[] { null, "none", "xMinYMin", "xMidYMin", "xMaxYMin", "xMinYMid", "xMidYMid", "xMaxYMid", "xMinYMax", "xMidYMax", "xMaxYMax" };
        MEET_OR_SLICE_VALUES = new String[] { null, "meet", "slice" };
    }
    
    protected class PreserveAspectRatioParserHandler extends DefaultPreserveAspectRatioHandler
    {
        public short align;
        public short meetOrSlice;
        
        protected PreserveAspectRatioParserHandler() {
            this.align = 6;
            this.meetOrSlice = 1;
        }
        
        public short getAlign() {
            return this.align;
        }
        
        public short getMeetOrSlice() {
            return this.meetOrSlice;
        }
        
        public void none() throws ParseException {
            this.align = 1;
        }
        
        public void xMaxYMax() throws ParseException {
            this.align = 10;
        }
        
        public void xMaxYMid() throws ParseException {
            this.align = 7;
        }
        
        public void xMaxYMin() throws ParseException {
            this.align = 4;
        }
        
        public void xMidYMax() throws ParseException {
            this.align = 9;
        }
        
        public void xMidYMid() throws ParseException {
            this.align = 6;
        }
        
        public void xMidYMin() throws ParseException {
            this.align = 3;
        }
        
        public void xMinYMax() throws ParseException {
            this.align = 8;
        }
        
        public void xMinYMid() throws ParseException {
            this.align = 5;
        }
        
        public void xMinYMin() throws ParseException {
            this.align = 2;
        }
        
        public void meet() throws ParseException {
            this.meetOrSlice = 1;
        }
        
        public void slice() throws ParseException {
            this.meetOrSlice = 2;
        }
    }
}
