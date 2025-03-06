// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

public class DefaultFragmentIdentifierHandler extends DefaultPreserveAspectRatioHandler implements FragmentIdentifierHandler
{
    public static final FragmentIdentifierHandler INSTANCE;
    
    protected DefaultFragmentIdentifierHandler() {
    }
    
    public void startFragmentIdentifier() throws ParseException {
    }
    
    public void idReference(final String s) throws ParseException {
    }
    
    public void viewBox(final float n, final float n2, final float n3, final float n4) throws ParseException {
    }
    
    public void startViewTarget() throws ParseException {
    }
    
    public void viewTarget(final String s) throws ParseException {
    }
    
    public void endViewTarget() throws ParseException {
    }
    
    public void startTransformList() throws ParseException {
    }
    
    public void matrix(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
    }
    
    public void rotate(final float n) throws ParseException {
    }
    
    public void rotate(final float n, final float n2, final float n3) throws ParseException {
    }
    
    public void translate(final float n) throws ParseException {
    }
    
    public void translate(final float n, final float n2) throws ParseException {
    }
    
    public void scale(final float n) throws ParseException {
    }
    
    public void scale(final float n, final float n2) throws ParseException {
    }
    
    public void skewX(final float n) throws ParseException {
    }
    
    public void skewY(final float n) throws ParseException {
    }
    
    public void endTransformList() throws ParseException {
    }
    
    public void zoomAndPan(final boolean b) {
    }
    
    public void endFragmentIdentifier() throws ParseException {
    }
    
    static {
        INSTANCE = new DefaultFragmentIdentifierHandler();
    }
}
