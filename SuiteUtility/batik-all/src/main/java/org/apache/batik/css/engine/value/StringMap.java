// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

public class StringMap
{
    protected static final int INITIAL_CAPACITY = 11;
    protected Entry[] table;
    protected int count;
    
    public StringMap() {
        this.table = new Entry[11];
    }
    
    public StringMap(final StringMap stringMap) {
        this.count = stringMap.count;
        this.table = new Entry[stringMap.table.length];
        for (int i = 0; i < this.table.length; ++i) {
            final Entry entry = stringMap.table[i];
            if (entry != null) {
                Entry next = new Entry(entry.hash, entry.key, entry.value, null);
                this.table[i] = next;
                for (Entry entry2 = entry.next; entry2 != null; entry2 = entry2.next) {
                    next.next = new Entry(entry2.hash, entry2.key, entry2.value, null);
                    next = next.next;
                }
            }
        }
    }
    
    public Object get(final String s) {
        final int n = s.hashCode() & Integer.MAX_VALUE;
        for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
            if (next.hash == n && next.key == s) {
                return next.value;
            }
        }
        return null;
    }
    
    public Object put(final String s, final Object value) {
        final int n = s.hashCode() & Integer.MAX_VALUE;
        int n2 = n % this.table.length;
        for (Entry next = this.table[n2]; next != null; next = next.next) {
            if (next.hash == n && next.key == s) {
                final Object value2 = next.value;
                next.value = value;
                return value2;
            }
        }
        final int length = this.table.length;
        if (this.count++ >= length - (length >> 2)) {
            this.rehash();
            n2 = n % this.table.length;
        }
        this.table[n2] = new Entry(n, s, value, this.table[n2]);
        return null;
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
        public int hash;
        public String key;
        public Object value;
        public Entry next;
        
        public Entry(final int hash, final String key, final Object value, final Entry next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
