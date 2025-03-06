// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class SoftReferenceCache
{
    protected final Map map;
    
    protected SoftReferenceCache() {
        this.map = new HashMap();
    }
    
    public synchronized void flush() {
        this.map.clear();
        this.notifyAll();
    }
    
    protected final synchronized boolean isPresentImpl(final Object o) {
        if (!this.map.containsKey(o)) {
            return false;
        }
        final SoftReference<Object> value = this.map.get(o);
        if (value == null) {
            return true;
        }
        if (value.get() != null) {
            return true;
        }
        this.clearImpl(o);
        return false;
    }
    
    protected final synchronized boolean isDoneImpl(final Object o) {
        final SoftReference<Object> value = this.map.get(o);
        if (value == null) {
            return false;
        }
        if (value.get() != null) {
            return true;
        }
        this.clearImpl(o);
        return false;
    }
    
    protected final synchronized Object requestImpl(final Object o) {
        if (this.map.containsKey(o)) {
            SoftReference<Object> softReference;
            for (softReference = this.map.get(o); softReference == null; softReference = this.map.get(o)) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
                if (!this.map.containsKey(o)) {
                    break;
                }
            }
            if (softReference != null) {
                final Object value = softReference.get();
                if (value != null) {
                    return value;
                }
            }
        }
        this.map.put(o, null);
        return null;
    }
    
    protected final synchronized void clearImpl(final Object o) {
        this.map.remove(o);
        this.notifyAll();
    }
    
    protected final synchronized void putImpl(final Object o, final Object o2) {
        if (this.map.containsKey(o)) {
            this.map.put(o, new SoftRefKey(o2, o));
            this.notifyAll();
        }
    }
    
    class SoftRefKey extends CleanerThread.SoftReferenceCleared
    {
        Object key;
        
        public SoftRefKey(final Object o, final Object key) {
            super(o);
            this.key = key;
        }
        
        public void cleared() {
            final SoftReferenceCache this$0 = SoftReferenceCache.this;
            if (this$0 == null) {
                return;
            }
            synchronized (this$0) {
                if (!this$0.map.containsKey(this.key)) {
                    return;
                }
                final Object remove = this$0.map.remove(this.key);
                if (this == remove) {
                    this$0.notifyAll();
                }
                else {
                    this$0.map.put(this.key, remove);
                }
            }
        }
    }
}
