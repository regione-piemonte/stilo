// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.cache;

public class SoftReferenceCacheBridge implements CacheBridge
{
    private static final SoftReferenceCache cache;
    
    @Override
    public Cache getCache() {
        return SoftReferenceCacheBridge.cache;
    }
    
    static {
        cache = new SoftReferenceCache();
    }
}
