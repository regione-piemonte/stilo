// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import java.io.IOException;
import java.io.InputStream;
import org.apache.batik.util.ParsedURL;

public class SVGConverterURLSource implements SVGConverterSource
{
    protected static final String SVG_EXTENSION = ".svg";
    protected static final String SVGZ_EXTENSION = ".svgz";
    public static final String ERROR_INVALID_URL = "SVGConverterURLSource.error.invalid.url";
    ParsedURL purl;
    String name;
    
    public SVGConverterURLSource(final String s) throws SVGConverterException {
        this.purl = new ParsedURL(s);
        final String path = this.purl.getPath();
        final int lastIndex = path.lastIndexOf(47);
        String name = path;
        if (lastIndex != -1) {
            name = path.substring(lastIndex + 1);
        }
        if (name.length() == 0) {
            name = path.substring(path.lastIndexOf(47, lastIndex - 1) + 1, lastIndex);
        }
        if (name.length() == 0) {
            throw new SVGConverterException("SVGConverterURLSource.error.invalid.url", new Object[] { s });
        }
        final int index = name.indexOf(63);
        String substring = "";
        if (index != -1) {
            substring = name.substring(index + 1);
            name = name.substring(0, index);
        }
        this.name = name;
        final String ref = this.purl.getRef();
        if (ref != null && ref.length() != 0) {
            this.name = this.name + "_" + ref.hashCode();
        }
        if (substring != null && substring.length() != 0) {
            this.name = this.name + "_" + substring.hashCode();
        }
    }
    
    public String toString() {
        return this.purl.toString();
    }
    
    public String getURI() {
        return this.toString();
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof SVGConverterURLSource && this.purl.equals(((SVGConverterURLSource)o).purl);
    }
    
    public int hashCode() {
        return this.purl.hashCode();
    }
    
    public InputStream openStream() throws IOException {
        return this.purl.openStream();
    }
    
    public boolean isSameAs(final String anObject) {
        return this.toString().equals(anObject);
    }
    
    public boolean isReadable() {
        return true;
    }
    
    public String getName() {
        return this.name;
    }
}
