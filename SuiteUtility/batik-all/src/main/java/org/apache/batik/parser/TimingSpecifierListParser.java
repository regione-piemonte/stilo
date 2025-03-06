// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class TimingSpecifierListParser extends TimingSpecifierParser
{
    public TimingSpecifierListParser(final boolean b, final boolean b2) {
        super(b, b2);
        this.timingSpecifierHandler = DefaultTimingSpecifierListHandler.INSTANCE;
    }
    
    public void setTimingSpecifierListHandler(final TimingSpecifierListHandler timingSpecifierHandler) {
        this.timingSpecifierHandler = timingSpecifierHandler;
    }
    
    public TimingSpecifierListHandler getTimingSpecifierListHandler() {
        return (TimingSpecifierListHandler)this.timingSpecifierHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.current = this.reader.read();
        ((TimingSpecifierListHandler)this.timingSpecifierHandler).startTimingSpecifierList();
        this.skipSpaces();
        if (this.current != -1) {
            while (true) {
                this.handleTimingSpecifier(this.parseTimingSpecifier());
                this.skipSpaces();
                if (this.current == -1) {
                    break;
                }
                if (this.current == 59) {
                    this.current = this.reader.read();
                }
                else {
                    this.reportUnexpectedCharacterError(this.current);
                }
            }
        }
        this.skipSpaces();
        if (this.current != -1) {
            this.reportUnexpectedCharacterError(this.current);
        }
        ((TimingSpecifierListHandler)this.timingSpecifierHandler).endTimingSpecifierList();
    }
}
