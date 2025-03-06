// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.color;

import org.apache.batik.util.SoftReferenceCache;

public class NamedProfileCache extends SoftReferenceCache
{
    static NamedProfileCache theCache;
    
    public static NamedProfileCache getDefaultCache() {
        return NamedProfileCache.theCache;
    }
    
    public synchronized boolean isPresent(final String s) {
        return super.isPresentImpl(s);
    }
    
    public synchronized boolean isDone(final String s) {
        return super.isDoneImpl(s);
    }
    
    public synchronized ICCColorSpaceExt request(final String s) {
        return (ICCColorSpaceExt)super.requestImpl(s);
    }
    
    public synchronized void clear(final String s) {
        super.clearImpl(s);
    }
    
    public synchronized void put(final String s, final ICCColorSpaceExt iccColorSpaceExt) {
        super.putImpl(s, iccColorSpaceExt);
    }
    
    static {
        NamedProfileCache.theCache = new NamedProfileCache();
    }
}
