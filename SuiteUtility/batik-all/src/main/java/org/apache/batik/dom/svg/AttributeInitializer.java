// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.util.DoublyIndexedTable;

public class AttributeInitializer
{
    protected String[] keys;
    protected int length;
    protected DoublyIndexedTable values;
    
    public AttributeInitializer(final int n) {
        this.values = new DoublyIndexedTable();
        this.keys = new String[n * 3];
    }
    
    public void addAttribute(final String s, final String s2, final String s3, final String s4) {
        final int length = this.keys.length;
        if (this.length == length) {
            final String[] keys = new String[length * 2];
            System.arraycopy(this.keys, 0, keys, 0, length);
            this.keys = keys;
        }
        this.keys[this.length++] = s;
        this.keys[this.length++] = s2;
        this.keys[this.length++] = s3;
        this.values.put(s, s3, s4);
    }
    
    public void initializeAttributes(final AbstractElement abstractElement) {
        for (int i = this.length - 1; i >= 2; i -= 3) {
            this.resetAttribute(abstractElement, this.keys[i - 2], this.keys[i - 1], this.keys[i]);
        }
    }
    
    public boolean resetAttribute(final AbstractElement abstractElement, final String s, final String str, String string) {
        final String s2 = (String)this.values.get(s, string);
        if (s2 == null) {
            return false;
        }
        if (str != null) {
            string = str + ':' + string;
        }
        abstractElement.setUnspecifiedAttribute(s, string, s2);
        return true;
    }
}
