// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultLengthHandler implements LengthHandler
{
    public static final LengthHandler INSTANCE;
    
    protected DefaultLengthHandler() {
    }
    
    public void startLength() throws ParseException {
    }
    
    public void lengthValue(final float n) throws ParseException {
    }
    
    public void em() throws ParseException {
    }
    
    public void ex() throws ParseException {
    }
    
    public void in() throws ParseException {
    }
    
    public void cm() throws ParseException {
    }
    
    public void mm() throws ParseException {
    }
    
    public void pc() throws ParseException {
    }
    
    public void pt() throws ParseException {
    }
    
    public void px() throws ParseException {
    }
    
    public void percentage() throws ParseException {
    }
    
    public void endLength() throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultLengthHandler();
    }
}
