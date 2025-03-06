// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.util.Calendar;

public class DefaultTimingSpecifierHandler implements TimingSpecifierHandler
{
    public static final TimingSpecifierHandler INSTANCE;
    
    protected DefaultTimingSpecifierHandler() {
    }
    
    public void offset(final float n) {
    }
    
    public void syncbase(final float n, final String s, final String s2) {
    }
    
    public void eventbase(final float n, final String s, final String s2) {
    }
    
    public void repeat(final float n, final String s) {
    }
    
    public void repeat(final float n, final String s, final int n2) {
    }
    
    public void accesskey(final float n, final char c) {
    }
    
    public void accessKeySVG12(final float n, final String s) {
    }
    
    public void mediaMarker(final String s, final String s2) {
    }
    
    public void wallclock(final Calendar calendar) {
    }
    
    public void indefinite() {
    }
    
    static {
        INSTANCE = new DefaultTimingSpecifierHandler();
    }
}
