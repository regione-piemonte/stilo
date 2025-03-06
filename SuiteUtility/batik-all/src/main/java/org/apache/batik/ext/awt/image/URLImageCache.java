// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.util.SoftReferenceCache;

public class URLImageCache extends SoftReferenceCache
{
    static URLImageCache theCache;
    
    public static URLImageCache getDefaultCache() {
        return URLImageCache.theCache;
    }
    
    public synchronized boolean isPresent(final ParsedURL parsedURL) {
        return super.isPresentImpl(parsedURL);
    }
    
    public synchronized boolean isDone(final ParsedURL parsedURL) {
        return super.isDoneImpl(parsedURL);
    }
    
    public synchronized Filter request(final ParsedURL parsedURL) {
        return (Filter)super.requestImpl(parsedURL);
    }
    
    public synchronized void clear(final ParsedURL parsedURL) {
        super.clearImpl(parsedURL);
    }
    
    public synchronized void put(final ParsedURL parsedURL, final Filter filter) {
        super.putImpl(parsedURL, filter);
    }
    
    static {
        URLImageCache.theCache = new URLImageCache();
    }
}
