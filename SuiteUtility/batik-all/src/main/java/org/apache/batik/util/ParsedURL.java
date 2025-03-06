// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import org.apache.batik.Version;
import java.util.ArrayList;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class ParsedURL
{
    ParsedURLData data;
    String userAgent;
    private static Map handlersMap;
    private static ParsedURLProtocolHandler defaultHandler;
    private static String globalUserAgent;
    
    public static String getGlobalUserAgent() {
        return ParsedURL.globalUserAgent;
    }
    
    public static void setGlobalUserAgent(final String globalUserAgent) {
        ParsedURL.globalUserAgent = globalUserAgent;
    }
    
    private static synchronized Map getHandlersMap() {
        if (ParsedURL.handlersMap != null) {
            return ParsedURL.handlersMap;
        }
        ParsedURL.handlersMap = new HashMap();
        registerHandler(new ParsedURLDataProtocolHandler());
        registerHandler(new ParsedURLJarProtocolHandler());
        final Iterator providers = Service.providers(ParsedURLProtocolHandler.class);
        while (providers.hasNext()) {
            registerHandler(providers.next());
        }
        return ParsedURL.handlersMap;
    }
    
    public static synchronized ParsedURLProtocolHandler getHandler(final String s) {
        if (s == null) {
            return ParsedURL.defaultHandler;
        }
        ParsedURLProtocolHandler defaultHandler = getHandlersMap().get(s);
        if (defaultHandler == null) {
            defaultHandler = ParsedURL.defaultHandler;
        }
        return defaultHandler;
    }
    
    public static synchronized void registerHandler(final ParsedURLProtocolHandler defaultHandler) {
        if (defaultHandler.getProtocolHandled() == null) {
            ParsedURL.defaultHandler = defaultHandler;
            return;
        }
        getHandlersMap().put(defaultHandler.getProtocolHandled(), defaultHandler);
    }
    
    public static InputStream checkGZIP(final InputStream inputStream) throws IOException {
        return ParsedURLData.checkGZIP(inputStream);
    }
    
    public ParsedURL(final String s) {
        this.userAgent = getGlobalUserAgent();
        this.data = parseURL(s);
    }
    
    public ParsedURL(final URL url) {
        this.userAgent = getGlobalUserAgent();
        this.data = new ParsedURLData(url);
    }
    
    public ParsedURL(final String s, final String s2) {
        this.userAgent = getGlobalUserAgent();
        if (s != null) {
            this.data = parseURL(s, s2);
        }
        else {
            this.data = parseURL(s2);
        }
    }
    
    public ParsedURL(final URL url, final String s) {
        this.userAgent = getGlobalUserAgent();
        if (url != null) {
            this.data = parseURL(new ParsedURL(url), s);
        }
        else {
            this.data = parseURL(s);
        }
    }
    
    public ParsedURL(final ParsedURL parsedURL, final String s) {
        if (parsedURL != null) {
            this.userAgent = parsedURL.getUserAgent();
            this.data = parseURL(parsedURL, s);
        }
        else {
            this.data = parseURL(s);
        }
    }
    
    public String toString() {
        return this.data.toString();
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof ParsedURL && this.data.equals(((ParsedURL)o).data);
    }
    
    public int hashCode() {
        return this.data.hashCode();
    }
    
    public boolean complete() {
        return this.data.complete();
    }
    
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getProtocol() {
        if (this.data.protocol == null) {
            return null;
        }
        return this.data.protocol;
    }
    
    public String getHost() {
        if (this.data.host == null) {
            return null;
        }
        return this.data.host;
    }
    
    public int getPort() {
        return this.data.port;
    }
    
    public String getPath() {
        if (this.data.path == null) {
            return null;
        }
        return this.data.path;
    }
    
    public String getRef() {
        if (this.data.ref == null) {
            return null;
        }
        return this.data.ref;
    }
    
    public String getPortStr() {
        return this.data.getPortStr();
    }
    
    public String getContentType() {
        return this.data.getContentType(this.userAgent);
    }
    
    public String getContentTypeMediaType() {
        return this.data.getContentTypeMediaType(this.userAgent);
    }
    
    public String getContentTypeCharset() {
        return this.data.getContentTypeCharset(this.userAgent);
    }
    
    public boolean hasContentTypeParameter(final String s) {
        return this.data.hasContentTypeParameter(this.userAgent, s);
    }
    
    public String getContentEncoding() {
        return this.data.getContentEncoding(this.userAgent);
    }
    
    public InputStream openStream() throws IOException {
        return this.data.openStream(this.userAgent, null);
    }
    
    public InputStream openStream(final String s) throws IOException {
        final ArrayList<Object> list = new ArrayList<Object>(1);
        list.add(s);
        return this.data.openStream(this.userAgent, list.iterator());
    }
    
    public InputStream openStream(final String[] array) throws IOException {
        final ArrayList<Object> list = new ArrayList<Object>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return this.data.openStream(this.userAgent, list.iterator());
    }
    
    public InputStream openStream(final Iterator iterator) throws IOException {
        return this.data.openStream(this.userAgent, iterator);
    }
    
    public InputStream openStreamRaw() throws IOException {
        return this.data.openStreamRaw(this.userAgent, null);
    }
    
    public InputStream openStreamRaw(final String s) throws IOException {
        final ArrayList<Object> list = new ArrayList<Object>(1);
        list.add(s);
        return this.data.openStreamRaw(this.userAgent, list.iterator());
    }
    
    public InputStream openStreamRaw(final String[] array) throws IOException {
        final ArrayList<Object> list = new ArrayList<Object>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return this.data.openStreamRaw(this.userAgent, list.iterator());
    }
    
    public InputStream openStreamRaw(final Iterator iterator) throws IOException {
        return this.data.openStreamRaw(this.userAgent, iterator);
    }
    
    public boolean sameFile(final ParsedURL parsedURL) {
        return this.data.sameFile(parsedURL.data);
    }
    
    protected static String getProtocol(final String s) {
        if (s == null) {
            return null;
        }
        int endIndex = 0;
        final int length = s.length();
        if (length == 0) {
            return null;
        }
        int n;
        for (n = s.charAt(endIndex); n == 45 || n == 43 || n == 46 || (n >= 97 && n <= 122) || (n >= 65 && n <= 90); n = s.charAt(endIndex)) {
            if (++endIndex == length) {
                n = 0;
                break;
            }
        }
        if (n == 58) {
            return s.substring(0, endIndex).toLowerCase();
        }
        return null;
    }
    
    public static ParsedURLData parseURL(final String s) {
        return getHandler(getProtocol(s)).parseURL(s);
    }
    
    public static ParsedURLData parseURL(final String s, final String s2) {
        if (s == null) {
            return parseURL(s2);
        }
        return parseURL(new ParsedURL(s), s2);
    }
    
    public static ParsedURLData parseURL(final ParsedURL parsedURL, final String s) {
        if (parsedURL == null) {
            return parseURL(s);
        }
        String s2 = getProtocol(s);
        if (s2 == null) {
            s2 = parsedURL.getProtocol();
        }
        return getHandler(s2).parseURL(parsedURL, s);
    }
    
    static {
        ParsedURL.handlersMap = null;
        ParsedURL.defaultHandler = new ParsedURLDefaultProtocolHandler();
        ParsedURL.globalUserAgent = "Batik/" + Version.getVersion();
    }
}
