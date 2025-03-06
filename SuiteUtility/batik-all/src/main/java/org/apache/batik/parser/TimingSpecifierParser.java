// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.util.Calendar;
import java.io.IOException;

public class TimingSpecifierParser extends TimingParser
{
    protected TimingSpecifierHandler timingSpecifierHandler;
    
    public TimingSpecifierParser(final boolean b, final boolean b2) {
        super(b, b2);
        this.timingSpecifierHandler = DefaultTimingSpecifierHandler.INSTANCE;
    }
    
    public void setTimingSpecifierHandler(final TimingSpecifierHandler timingSpecifierHandler) {
        this.timingSpecifierHandler = timingSpecifierHandler;
    }
    
    public TimingSpecifierHandler getTimingSpecifierHandler() {
        return this.timingSpecifierHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.current = this.reader.read();
        final Object[] timingSpecifier = this.parseTimingSpecifier();
        this.skipSpaces();
        if (this.current != -1) {
            this.reportError("end.of.stream.expected", new Object[] { new Integer(this.current) });
        }
        this.handleTimingSpecifier(timingSpecifier);
    }
    
    protected void handleTimingSpecifier(final Object[] array) {
        switch ((int)array[0]) {
            case 0: {
                this.timingSpecifierHandler.offset((float)array[1]);
                break;
            }
            case 1: {
                this.timingSpecifierHandler.syncbase((float)array[1], (String)array[2], (String)array[3]);
                break;
            }
            case 2: {
                this.timingSpecifierHandler.eventbase((float)array[1], (String)array[2], (String)array[3]);
                break;
            }
            case 3: {
                final float floatValue = (float)array[1];
                final String s = (String)array[2];
                if (array[3] == null) {
                    this.timingSpecifierHandler.repeat(floatValue, s);
                    break;
                }
                this.timingSpecifierHandler.repeat(floatValue, s, (int)array[3]);
                break;
            }
            case 4: {
                this.timingSpecifierHandler.accesskey((float)array[1], (char)array[2]);
                break;
            }
            case 5: {
                this.timingSpecifierHandler.accessKeySVG12((float)array[1], (String)array[2]);
                break;
            }
            case 6: {
                this.timingSpecifierHandler.mediaMarker((String)array[1], (String)array[2]);
                break;
            }
            case 7: {
                this.timingSpecifierHandler.wallclock((Calendar)array[1]);
                break;
            }
            case 8: {
                this.timingSpecifierHandler.indefinite();
                break;
            }
        }
    }
}
