// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class LengthListParser extends LengthParser
{
    public LengthListParser() {
        this.lengthHandler = DefaultLengthListHandler.INSTANCE;
    }
    
    public void setLengthListHandler(final LengthListHandler lengthHandler) {
        this.lengthHandler = lengthHandler;
    }
    
    public LengthListHandler getLengthListHandler() {
        return (LengthListHandler)this.lengthHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        ((LengthListHandler)this.lengthHandler).startLengthList();
        this.current = this.reader.read();
        this.skipSpaces();
        try {
            do {
                this.lengthHandler.startLength();
                this.parseLength();
                this.lengthHandler.endLength();
                this.skipCommaSpaces();
            } while (this.current != -1);
        }
        catch (NumberFormatException ex) {
            this.reportUnexpectedCharacterError(this.current);
        }
        ((LengthListHandler)this.lengthHandler).endLengthList();
    }
}
