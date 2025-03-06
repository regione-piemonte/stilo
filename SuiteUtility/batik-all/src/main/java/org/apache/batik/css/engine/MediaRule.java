// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.w3c.css.sac.SACMediaList;

public class MediaRule extends StyleSheet implements Rule
{
    public static final short TYPE = 1;
    protected SACMediaList mediaList;
    
    public short getType() {
        return 1;
    }
    
    public void setMediaList(final SACMediaList mediaList) {
        this.mediaList = mediaList;
    }
    
    public SACMediaList getMediaList() {
        return this.mediaList;
    }
    
    public String toString(final CSSEngine cssEngine) {
        final StringBuffer sb = new StringBuffer();
        sb.append("@media");
        if (this.mediaList != null) {
            for (int i = 0; i < this.mediaList.getLength(); ++i) {
                sb.append(' ');
                sb.append(this.mediaList.item(i));
            }
        }
        sb.append(" {\n");
        for (int j = 0; j < this.size; ++j) {
            sb.append(this.rules[j].toString(cssEngine));
        }
        sb.append("}\n");
        return sb.toString();
    }
}
