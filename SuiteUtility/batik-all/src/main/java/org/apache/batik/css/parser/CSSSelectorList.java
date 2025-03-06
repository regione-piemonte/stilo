// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

public class CSSSelectorList implements SelectorList
{
    protected Selector[] list;
    protected int length;
    
    public CSSSelectorList() {
        this.list = new Selector[3];
    }
    
    public int getLength() {
        return this.length;
    }
    
    public Selector item(final int n) {
        if (n < 0 || n >= this.length) {
            return null;
        }
        return this.list[n];
    }
    
    public void append(final Selector selector) {
        if (this.length == this.list.length) {
            final Selector[] list = this.list;
            System.arraycopy(list, 0, this.list = new Selector[1 + this.list.length + this.list.length / 2], 0, list.length);
        }
        this.list[this.length++] = selector;
    }
}
