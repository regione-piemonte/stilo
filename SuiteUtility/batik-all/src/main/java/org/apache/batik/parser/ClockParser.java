// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class ClockParser extends TimingParser
{
    protected ClockHandler clockHandler;
    protected boolean parseOffset;
    
    public ClockParser(final boolean parseOffset) {
        super(false, false);
        this.parseOffset = parseOffset;
    }
    
    public void setClockHandler(final ClockHandler clockHandler) {
        this.clockHandler = clockHandler;
    }
    
    public ClockHandler getClockHandler() {
        return this.clockHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.current = this.reader.read();
        final float n = this.parseOffset ? this.parseOffset() : this.parseClockValue();
        if (this.current != -1) {
            this.reportError("end.of.stream.expected", new Object[] { new Integer(this.current) });
        }
        if (this.clockHandler != null) {
            this.clockHandler.clockValue(n);
        }
    }
}
