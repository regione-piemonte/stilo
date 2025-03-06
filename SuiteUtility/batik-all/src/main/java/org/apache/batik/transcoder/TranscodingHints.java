// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.transcoder;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class TranscodingHints extends HashMap
{
    public TranscodingHints() {
        this(null);
    }
    
    public TranscodingHints(final Map map) {
        super(7);
        if (map != null) {
            this.putAll(map);
        }
    }
    
    public boolean containsKey(final Object key) {
        return super.containsKey(key);
    }
    
    public Object get(final Object key) {
        return super.get(key);
    }
    
    public Object put(final Object o, final Object o2) {
        if (!((Key)o).isCompatibleValue(o2)) {
            throw new IllegalArgumentException(o2 + " incompatible with " + o);
        }
        return super.put(o, o2);
    }
    
    public Object remove(final Object key) {
        return super.remove(key);
    }
    
    public void putAll(final TranscodingHints m) {
        super.putAll(m);
    }
    
    public void putAll(final Map map) {
        if (map instanceof TranscodingHints) {
            this.putAll((TranscodingHints)map);
        }
        else {
            for (final Map.Entry<Object, Object> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public abstract static class Key
    {
        protected Key() {
        }
        
        public abstract boolean isCompatibleValue(final Object p0);
    }
}
