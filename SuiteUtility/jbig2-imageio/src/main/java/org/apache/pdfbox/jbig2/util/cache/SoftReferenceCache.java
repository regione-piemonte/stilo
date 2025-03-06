// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.cache;

import java.util.Collections;
import java.util.WeakHashMap;
import java.lang.ref.SoftReference;
import java.util.Map;

public class SoftReferenceCache implements Cache
{
    private final Map<Object, SoftReference<?>> cache;
    
    public SoftReferenceCache() {
        this.cache = Collections.synchronizedMap(new WeakHashMap<Object, SoftReference<?>>());
    }
    
    @Override
    public Object put(final Object o, final Object referent, final int n) {
        return this.getValueNullSafe(this.cache.put(o, new SoftReference<Object>(referent)));
    }
    
    @Override
    public Object get(final Object o) {
        return this.getValueNullSafe(this.cache.get(o));
    }
    
    @Override
    public void clear() {
        this.cache.clear();
    }
    
    @Override
    public Object remove(final Object o) {
        return this.getValueNullSafe(this.cache.remove(o));
    }
    
    private Object getValueNullSafe(final SoftReference<?> softReference) {
        return (softReference == null) ? null : softReference.get();
    }
}
