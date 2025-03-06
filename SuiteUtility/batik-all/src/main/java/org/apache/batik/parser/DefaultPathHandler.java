// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultPathHandler implements PathHandler
{
    public static final PathHandler INSTANCE;
    
    protected DefaultPathHandler() {
    }
    
    public void startPath() throws ParseException {
    }
    
    public void endPath() throws ParseException {
    }
    
    public void movetoRel(final float n, final float n2) throws ParseException {
    }
    
    public void movetoAbs(final float n, final float n2) throws ParseException {
    }
    
    public void closePath() throws ParseException {
    }
    
    public void linetoRel(final float n, final float n2) throws ParseException {
    }
    
    public void linetoAbs(final float n, final float n2) throws ParseException {
    }
    
    public void linetoHorizontalRel(final float n) throws ParseException {
    }
    
    public void linetoHorizontalAbs(final float n) throws ParseException {
    }
    
    public void linetoVerticalRel(final float n) throws ParseException {
    }
    
    public void linetoVerticalAbs(final float n) throws ParseException {
    }
    
    public void curvetoCubicRel(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
    }
    
    public void curvetoCubicAbs(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
    }
    
    public void curvetoCubicSmoothRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
    }
    
    public void curvetoCubicSmoothAbs(final float n, final float n2, final float n3, final float n4) throws ParseException {
    }
    
    public void curvetoQuadraticRel(final float n, final float n2, final float n3, final float n4) throws ParseException {
    }
    
    public void curvetoQuadraticAbs(final float n, final float n2, final float n3, final float n4) throws ParseException {
    }
    
    public void curvetoQuadraticSmoothRel(final float n, final float n2) throws ParseException {
    }
    
    public void curvetoQuadraticSmoothAbs(final float n, final float n2) throws ParseException {
    }
    
    public void arcRel(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
    }
    
    public void arcAbs(final float n, final float n2, final float n3, final boolean b, final boolean b2, final float n4, final float n5) throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultPathHandler();
    }
}
