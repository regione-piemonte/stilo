// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultAngleHandler implements AngleHandler
{
    public static final AngleHandler INSTANCE;
    
    protected DefaultAngleHandler() {
    }
    
    public void startAngle() throws ParseException {
    }
    
    public void angleValue(final float n) throws ParseException {
    }
    
    public void deg() throws ParseException {
    }
    
    public void grad() throws ParseException {
    }
    
    public void rad() throws ParseException {
    }
    
    public void endAngle() throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultAngleHandler();
    }
}
