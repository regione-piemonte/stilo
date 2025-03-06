// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.cache;

import java.util.Iterator;
import org.apache.pdfbox.jbig2.util.ServiceLookup;

public class CacheFactory
{
    private static CacheBridge cacheBridge;
    private static ClassLoader clsLoader;
    
    public static Cache getCache(final ClassLoader paramClassLoader) {
        if (CacheFactory.cacheBridge == null) {
            final ServiceLookup serviceLookup = new ServiceLookup();
            final Iterator<CacheBridge> iterator = serviceLookup.getServices(CacheBridge.class, paramClassLoader);
            if (!iterator.hasNext()) {
                throw new IllegalStateException("No implementation of " + CacheBridge.class + " was avaliable using META-INF/services lookup");
            }
            CacheFactory.cacheBridge = iterator.next();
        }
        return CacheFactory.cacheBridge.getCache();
    }
    
    public static Cache getCache() {
        return new SoftReferenceCache();
    }
    
    public static void setClassLoader(final ClassLoader paramClassLoader) {
        CacheFactory.clsLoader = paramClassLoader;
    }
}
