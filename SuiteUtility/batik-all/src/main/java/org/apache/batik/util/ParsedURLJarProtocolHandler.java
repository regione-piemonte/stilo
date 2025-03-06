// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.net.MalformedURLException;
import java.net.URL;

public class ParsedURLJarProtocolHandler extends ParsedURLDefaultProtocolHandler
{
    public static final String JAR = "jar";
    
    public ParsedURLJarProtocolHandler() {
        super("jar");
    }
    
    public ParsedURLData parseURL(final ParsedURL parsedURL, final String spec) {
        if (spec.substring(0, "jar".length() + 1).toLowerCase().equals("jar:")) {
            return this.parseURL(spec);
        }
        try {
            return this.constructParsedURLData(new URL(new URL(parsedURL.toString()), spec));
        }
        catch (MalformedURLException ex) {
            return super.parseURL(parsedURL, spec);
        }
    }
}
