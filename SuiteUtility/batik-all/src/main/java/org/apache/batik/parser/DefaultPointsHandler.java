// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultPointsHandler implements PointsHandler
{
    public static final DefaultPointsHandler INSTANCE;
    
    protected DefaultPointsHandler() {
    }
    
    public void startPoints() throws ParseException {
    }
    
    public void point(final float n, final float n2) throws ParseException {
    }
    
    public void endPoints() throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultPointsHandler();
    }
}
