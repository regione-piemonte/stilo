// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultLengthListHandler extends DefaultLengthHandler implements LengthListHandler
{
    public static final LengthListHandler INSTANCE;
    
    protected DefaultLengthListHandler() {
    }
    
    public void startLengthList() throws ParseException {
    }
    
    public void endLengthList() throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultLengthListHandler();
    }
}
