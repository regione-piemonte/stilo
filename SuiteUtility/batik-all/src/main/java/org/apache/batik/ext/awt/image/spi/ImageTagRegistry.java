// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.spi;

import org.apache.batik.util.Service;
import java.util.Collections;
import java.util.Collection;
import java.util.ListIterator;
import java.io.StreamCorruptedException;
import java.io.IOException;
import java.util.Iterator;
import java.io.BufferedInputStream;
import java.io.InputStream;
import org.apache.batik.ext.awt.image.renderable.ProfileRable;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.ext.awt.color.ICCColorSpaceExt;
import org.apache.batik.util.ParsedURL;
import java.util.LinkedList;
import org.apache.batik.ext.awt.image.URLImageCache;
import java.util.List;

public class ImageTagRegistry implements ErrorConstants
{
    List entries;
    List extensions;
    List mimeTypes;
    URLImageCache rawCache;
    URLImageCache imgCache;
    static ImageTagRegistry registry;
    static BrokenLinkProvider defaultProvider;
    static BrokenLinkProvider brokenLinkProvider;
    
    public ImageTagRegistry() {
        this(null, null);
    }
    
    public ImageTagRegistry(URLImageCache rawCache, URLImageCache imgCache) {
        this.entries = new LinkedList();
        this.extensions = null;
        this.mimeTypes = null;
        if (rawCache == null) {
            rawCache = new URLImageCache();
        }
        if (imgCache == null) {
            imgCache = new URLImageCache();
        }
        this.rawCache = rawCache;
        this.imgCache = imgCache;
    }
    
    public void flushCache() {
        this.rawCache.flush();
        this.imgCache.flush();
    }
    
    public void flushImage(final ParsedURL parsedURL) {
        this.rawCache.clear(parsedURL);
        this.imgCache.clear(parsedURL);
    }
    
    public Filter checkCache(final ParsedURL parsedURL, final ICCColorSpaceExt iccColorSpaceExt) {
        URLImageCache urlImageCache;
        if (iccColorSpaceExt != null) {
            urlImageCache = this.rawCache;
        }
        else {
            urlImageCache = this.imgCache;
        }
        Filter request = urlImageCache.request(parsedURL);
        if (request == null) {
            urlImageCache.clear(parsedURL);
            return null;
        }
        if (iccColorSpaceExt != null) {
            request = new ProfileRable(request, iccColorSpaceExt);
        }
        return request;
    }
    
    public Filter readURL(final ParsedURL parsedURL) {
        return this.readURL(null, parsedURL, null, true, true);
    }
    
    public Filter readURL(final ParsedURL parsedURL, final ICCColorSpaceExt iccColorSpaceExt) {
        return this.readURL(null, parsedURL, iccColorSpaceExt, true, true);
    }
    
    public Filter readURL(InputStream openStream, final ParsedURL parsedURL, final ICCColorSpaceExt iccColorSpaceExt, final boolean b, final boolean b2) {
        if (openStream != null && !openStream.markSupported()) {
            openStream = new BufferedInputStream(openStream);
        }
        final boolean b3 = iccColorSpaceExt != null;
        Filter filter = null;
        URLImageCache urlImageCache = null;
        if (parsedURL != null) {
            if (b3) {
                urlImageCache = this.rawCache;
            }
            else {
                urlImageCache = this.imgCache;
            }
            filter = urlImageCache.request(parsedURL);
            if (filter != null) {
                if (iccColorSpaceExt != null) {
                    filter = new ProfileRable(filter, iccColorSpaceExt);
                }
                return filter;
            }
        }
        int n = 0;
        final List registeredMimeTypes = this.getRegisteredMimeTypes();
        for (final RegistryEntry registryEntry : this.entries) {
            if (registryEntry instanceof URLRegistryEntry) {
                if (parsedURL == null) {
                    continue;
                }
                if (!b) {
                    continue;
                }
                final URLRegistryEntry urlRegistryEntry = (URLRegistryEntry)registryEntry;
                if (!urlRegistryEntry.isCompatibleURL(parsedURL)) {
                    continue;
                }
                filter = urlRegistryEntry.handleURL(parsedURL, b3);
                if (filter != null) {
                    break;
                }
                continue;
            }
            else {
                if (!(registryEntry instanceof StreamRegistryEntry)) {
                    continue;
                }
                final StreamRegistryEntry streamRegistryEntry = (StreamRegistryEntry)registryEntry;
                if (n != 0) {
                    continue;
                }
                try {
                    if (openStream == null) {
                        if (parsedURL == null || !b) {
                            break;
                        }
                        try {
                            openStream = parsedURL.openStream(registeredMimeTypes.iterator());
                        }
                        catch (IOException ex) {
                            n = 1;
                            continue;
                        }
                        if (!openStream.markSupported()) {
                            openStream = new BufferedInputStream(openStream);
                        }
                    }
                    if (!streamRegistryEntry.isCompatibleStream(openStream)) {
                        continue;
                    }
                    filter = streamRegistryEntry.handleStream(openStream, parsedURL, b3);
                    if (filter != null) {
                        break;
                    }
                    continue;
                }
                catch (StreamCorruptedException ex2) {
                    openStream = null;
                }
            }
        }
        if (urlImageCache != null) {
            urlImageCache.put(parsedURL, filter);
        }
        if (filter == null) {
            if (!b2) {
                return null;
            }
            if (n != 0) {
                return getBrokenLinkImage(this, "url.unreachable", null);
            }
            return getBrokenLinkImage(this, "url.uninterpretable", null);
        }
        else {
            if (BrokenLinkProvider.hasBrokenLinkProperty(filter)) {
                return b2 ? filter : null;
            }
            if (iccColorSpaceExt != null) {
                filter = new ProfileRable(filter, iccColorSpaceExt);
            }
            return filter;
        }
    }
    
    public Filter readStream(final InputStream inputStream) {
        return this.readStream(inputStream, null);
    }
    
    public Filter readStream(InputStream in, final ICCColorSpaceExt iccColorSpaceExt) {
        if (!in.markSupported()) {
            in = new BufferedInputStream(in);
        }
        final boolean b = iccColorSpaceExt != null;
        Filter handleStream = null;
        for (final RegistryEntry registryEntry : this.entries) {
            if (!(registryEntry instanceof StreamRegistryEntry)) {
                continue;
            }
            final StreamRegistryEntry streamRegistryEntry = (StreamRegistryEntry)registryEntry;
            try {
                if (!streamRegistryEntry.isCompatibleStream(in)) {
                    continue;
                }
                handleStream = streamRegistryEntry.handleStream(in, null, b);
                if (handleStream != null) {
                    break;
                }
                continue;
                continue;
            }
            catch (StreamCorruptedException ex) {}
            break;
        }
        if (handleStream == null) {
            return getBrokenLinkImage(this, "stream.unreadable", null);
        }
        if (iccColorSpaceExt != null && !BrokenLinkProvider.hasBrokenLinkProperty(handleStream)) {
            handleStream = new ProfileRable(handleStream, iccColorSpaceExt);
        }
        return handleStream;
    }
    
    public synchronized void register(final RegistryEntry registryEntry) {
        final float priority = registryEntry.getPriority();
        final ListIterator<RegistryEntry> listIterator = (ListIterator<RegistryEntry>)this.entries.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().getPriority() > priority) {
                listIterator.previous();
                listIterator.add(registryEntry);
                return;
            }
        }
        listIterator.add(registryEntry);
        this.extensions = null;
        this.mimeTypes = null;
    }
    
    public synchronized List getRegisteredExtensions() {
        if (this.extensions != null) {
            return this.extensions;
        }
        this.extensions = new LinkedList();
        final Iterator<RegistryEntry> iterator = this.entries.iterator();
        while (iterator.hasNext()) {
            this.extensions.addAll(iterator.next().getStandardExtensions());
        }
        return this.extensions = Collections.unmodifiableList((List<?>)this.extensions);
    }
    
    public synchronized List getRegisteredMimeTypes() {
        if (this.mimeTypes != null) {
            return this.mimeTypes;
        }
        this.mimeTypes = new LinkedList();
        final Iterator<RegistryEntry> iterator = this.entries.iterator();
        while (iterator.hasNext()) {
            this.mimeTypes.addAll(iterator.next().getMimeTypes());
        }
        return this.mimeTypes = Collections.unmodifiableList((List<?>)this.mimeTypes);
    }
    
    public static synchronized ImageTagRegistry getRegistry() {
        if (ImageTagRegistry.registry != null) {
            return ImageTagRegistry.registry;
        }
        (ImageTagRegistry.registry = new ImageTagRegistry()).register(new JDKRegistryEntry());
        final Iterator providers = Service.providers(RegistryEntry.class);
        while (providers.hasNext()) {
            ImageTagRegistry.registry.register(providers.next());
        }
        return ImageTagRegistry.registry;
    }
    
    public static synchronized Filter getBrokenLinkImage(final Object o, final String s, final Object[] array) {
        Filter filter = null;
        if (ImageTagRegistry.brokenLinkProvider != null) {
            filter = ImageTagRegistry.brokenLinkProvider.getBrokenLinkImage(o, s, array);
        }
        if (filter == null) {
            filter = ImageTagRegistry.defaultProvider.getBrokenLinkImage(o, s, array);
        }
        return filter;
    }
    
    public static synchronized void setBrokenLinkProvider(final BrokenLinkProvider brokenLinkProvider) {
        ImageTagRegistry.brokenLinkProvider = brokenLinkProvider;
    }
    
    static {
        ImageTagRegistry.registry = null;
        ImageTagRegistry.defaultProvider = new DefaultBrokenLinkProvider();
        ImageTagRegistry.brokenLinkProvider = null;
    }
}
