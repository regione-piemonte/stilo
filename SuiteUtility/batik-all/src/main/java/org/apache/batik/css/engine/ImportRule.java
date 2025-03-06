// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.apache.batik.util.ParsedURL;

public class ImportRule extends MediaRule
{
    public static final short TYPE = 2;
    protected ParsedURL uri;
    
    public short getType() {
        return 2;
    }
    
    public void setURI(final ParsedURL uri) {
        this.uri = uri;
    }
    
    public ParsedURL getURI() {
        return this.uri;
    }
    
    public String toString(final CSSEngine cssEngine) {
        final StringBuffer sb = new StringBuffer();
        sb.append("@import \"");
        sb.append(this.uri);
        sb.append("\"");
        if (this.mediaList != null) {
            for (int i = 0; i < this.mediaList.getLength(); ++i) {
                sb.append(' ');
                sb.append(this.mediaList.item(i));
            }
        }
        sb.append(";\n");
        return sb.toString();
    }
}
