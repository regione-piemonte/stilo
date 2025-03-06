// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultPreserveAspectRatioHandler implements PreserveAspectRatioHandler
{
    public static final PreserveAspectRatioHandler INSTANCE;
    
    protected DefaultPreserveAspectRatioHandler() {
    }
    
    public void startPreserveAspectRatio() throws ParseException {
    }
    
    public void none() throws ParseException {
    }
    
    public void xMaxYMax() throws ParseException {
    }
    
    public void xMaxYMid() throws ParseException {
    }
    
    public void xMaxYMin() throws ParseException {
    }
    
    public void xMidYMax() throws ParseException {
    }
    
    public void xMidYMid() throws ParseException {
    }
    
    public void xMidYMin() throws ParseException {
    }
    
    public void xMinYMax() throws ParseException {
    }
    
    public void xMinYMid() throws ParseException {
    }
    
    public void xMinYMin() throws ParseException {
    }
    
    public void meet() throws ParseException {
    }
    
    public void slice() throws ParseException {
    }
    
    public void endPreserveAspectRatio() throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultPreserveAspectRatioHandler();
    }
}
