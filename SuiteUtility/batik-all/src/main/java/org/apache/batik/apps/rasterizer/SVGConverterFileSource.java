// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.io.File;

public class SVGConverterFileSource implements SVGConverterSource
{
    File file;
    String ref;
    
    public SVGConverterFileSource(final File file) {
        this.file = file;
    }
    
    public SVGConverterFileSource(final File file, final String ref) {
        this.file = file;
        this.ref = ref;
    }
    
    public String getName() {
        String str = this.file.getName();
        if (this.ref != null && !"".equals(this.ref)) {
            str = str + '#' + this.ref;
        }
        return str;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String toString() {
        return this.getName();
    }
    
    public String getURI() {
        try {
            String str = this.file.toURL().toString();
            if (this.ref != null && !"".equals(this.ref)) {
                str = str + '#' + this.ref;
            }
            return str;
        }
        catch (MalformedURLException ex) {
            throw new Error(ex.getMessage());
        }
    }
    
    public boolean equals(final Object o) {
        return o != null && o instanceof SVGConverterFileSource && this.file.equals(((SVGConverterFileSource)o).file);
    }
    
    public int hashCode() {
        return this.file.hashCode();
    }
    
    public InputStream openStream() throws FileNotFoundException {
        return new FileInputStream(this.file);
    }
    
    public boolean isSameAs(final String anObject) {
        return this.file.toString().equals(anObject);
    }
    
    public boolean isReadable() {
        return this.file.canRead();
    }
}
