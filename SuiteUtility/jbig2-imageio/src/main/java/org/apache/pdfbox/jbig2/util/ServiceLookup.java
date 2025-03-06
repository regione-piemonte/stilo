// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util;

import java.util.ServiceLoader;
import java.util.Iterator;

public class ServiceLookup<B>
{
    public Iterator<B> getServices(final Class<B> clazz) {
        return this.getServices(clazz, null);
    }
    
    public Iterator<B> getServices(final Class<B> service, final ClassLoader loader) {
        Iterator<B> iterator = ServiceLoader.load(service).iterator();
        if (!iterator.hasNext()) {
            iterator = ServiceLoader.load(service, service.getClass().getClassLoader()).iterator();
        }
        if (!iterator.hasNext() && loader != null) {
            iterator = ServiceLoader.load(service, loader).iterator();
        }
        return iterator;
    }
}
