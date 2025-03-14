// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class NumberListParser extends NumberParser
{
    protected NumberListHandler numberListHandler;
    
    public NumberListParser() {
        this.numberListHandler = DefaultNumberListHandler.INSTANCE;
    }
    
    public void setNumberListHandler(final NumberListHandler numberListHandler) {
        this.numberListHandler = numberListHandler;
    }
    
    public NumberListHandler getNumberListHandler() {
        return this.numberListHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.numberListHandler.startNumberList();
        this.current = this.reader.read();
        this.skipSpaces();
        try {
            do {
                this.numberListHandler.startNumber();
                this.numberListHandler.numberValue(this.parseFloat());
                this.numberListHandler.endNumber();
                this.skipCommaSpaces();
            } while (this.current != -1);
        }
        catch (NumberFormatException ex) {
            this.reportUnexpectedCharacterError(this.current);
        }
        this.numberListHandler.endNumberList();
    }
}
