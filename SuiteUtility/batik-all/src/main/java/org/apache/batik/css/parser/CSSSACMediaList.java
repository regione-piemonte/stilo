// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.SACMediaList;

public class CSSSACMediaList implements SACMediaList
{
    protected String[] list;
    protected int length;
    
    public CSSSACMediaList() {
        this.list = new String[3];
    }
    
    public int getLength() {
        return this.length;
    }
    
    public String item(final int n) {
        if (n < 0 || n >= this.length) {
            return null;
        }
        return this.list[n];
    }
    
    public void append(final String s) {
        if (this.length == this.list.length) {
            final String[] list = this.list;
            System.arraycopy(list, 0, this.list = new String[1 + this.list.length + this.list.length / 2], 0, list.length);
        }
        this.list[this.length++] = s;
    }
}
