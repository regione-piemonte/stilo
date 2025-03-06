// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

public class HashTableStack
{
    protected Link current;
    
    public HashTableStack() {
        this.current = new Link(null);
    }
    
    public void push() {
        final Link current = this.current;
        ++current.pushCount;
    }
    
    public void pop() {
        if (this.current.pushCount-- == 0) {
            this.current = this.current.next;
        }
    }
    
    public String put(final String s, final String defaultStr) {
        if (this.current.pushCount != 0) {
            final Link current = this.current;
            --current.pushCount;
            this.current = new Link(this.current);
        }
        if (s.length() == 0) {
            this.current.defaultStr = defaultStr;
        }
        return (String)this.current.table.put(s, defaultStr);
    }
    
    public String get(final String s) {
        if (s.length() == 0) {
            return this.current.defaultStr;
        }
        for (Link link = this.current; link != null; link = link.next) {
            final String s2 = (String)link.table.get(s);
            if (s2 != null) {
                return s2;
            }
        }
        return null;
    }
    
    protected static class Link
    {
        public HashTable table;
        public Link next;
        public String defaultStr;
        public int pushCount;
        
        public Link(final Link next) {
            this.pushCount = 0;
            this.table = new HashTable();
            this.next = next;
            if (this.next != null) {
                this.defaultStr = this.next.defaultStr;
            }
        }
    }
}
