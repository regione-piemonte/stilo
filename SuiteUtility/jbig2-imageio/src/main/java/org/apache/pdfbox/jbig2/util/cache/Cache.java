// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.util.cache;

public interface Cache
{
    Object put(final Object p0, final Object p1, final int p2);
    
    Object get(final Object p0);
    
    void clear();
    
    Object remove(final Object p0);
}
