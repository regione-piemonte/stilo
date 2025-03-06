// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

public class KerningTable
{
    private Kern[] entries;
    
    public KerningTable(final Kern[] entries) {
        this.entries = entries;
    }
    
    public float getKerningValue(final int n, final int n2, final String s, final String s2) {
        for (int i = 0; i < this.entries.length; ++i) {
            if (this.entries[i].matchesFirstGlyph(n, s) && this.entries[i].matchesSecondGlyph(n2, s2)) {
                return this.entries[i].getAdjustValue();
            }
        }
        return 0.0f;
    }
}
