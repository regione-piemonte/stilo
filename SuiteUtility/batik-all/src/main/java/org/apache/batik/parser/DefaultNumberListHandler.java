// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultNumberListHandler implements NumberListHandler
{
    public static final NumberListHandler INSTANCE;
    
    protected DefaultNumberListHandler() {
    }
    
    public void startNumberList() throws ParseException {
    }
    
    public void endNumberList() throws ParseException {
    }
    
    public void startNumber() throws ParseException {
    }
    
    public void numberValue(final float n) throws ParseException {
    }
    
    public void endNumber() throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultNumberListHandler();
    }
}
