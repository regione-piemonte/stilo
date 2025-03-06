// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

public class StringIntMap
{
    protected Entry[] table;
    protected int count;
    
    public StringIntMap(final int n) {
        this.table = new Entry[n - (n >> 2) + 1];
    }
    
    public int get(final String anObject) {
        final int n = anObject.hashCode() & Integer.MAX_VALUE;
        for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
            if (next.hash == n && next.key.equals(anObject)) {
                return next.value;
            }
        }
        return -1;
    }
    
    public void put(final String anObject, final int value) {
        final int n = anObject.hashCode() & Integer.MAX_VALUE;
        int n2 = n % this.table.length;
        for (Entry next = this.table[n2]; next != null; next = next.next) {
            if (next.hash == n && next.key.equals(anObject)) {
                next.value = value;
                return;
            }
        }
        final int length = this.table.length;
        if (this.count++ >= length - (length >> 2)) {
            this.rehash();
            n2 = n % this.table.length;
        }
        this.table[n2] = new Entry(n, anObject, value, this.table[n2]);
    }
    
    protected void rehash() {
        final Entry[] table = this.table;
        this.table = new Entry[table.length * 2 + 1];
        for (int i = table.length - 1; i >= 0; --i) {
            Entry entry;
            int n;
            for (Entry next = table[i]; next != null; next = next.next, n = entry.hash % this.table.length, entry.next = this.table[n], this.table[n] = entry) {
                entry = next;
            }
        }
    }
    
    protected static class Entry
    {
        public final int hash;
        public String key;
        public int value;
        public Entry next;
        
        public Entry(final int hash, final String key, final int value, final Entry next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
