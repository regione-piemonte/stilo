// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

public class TriplyIndexedTable
{
    protected static final int INITIAL_CAPACITY = 11;
    protected Entry[] table;
    protected int count;
    
    public TriplyIndexedTable() {
        this.table = new Entry[11];
    }
    
    public TriplyIndexedTable(final int n) {
        this.table = new Entry[n];
    }
    
    public int size() {
        return this.count;
    }
    
    public Object put(final Object o, final Object o2, final Object o3, final Object value) {
        final int n = this.hashCode(o, o2, o3) & Integer.MAX_VALUE;
        int n2 = n % this.table.length;
        for (Entry next = this.table[n2]; next != null; next = next.next) {
            if (next.hash == n && next.match(o, o2, o3)) {
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
        this.table[n2] = new Entry(n, o, o2, o3, value, this.table[n2]);
        return null;
    }
    
    public Object get(final Object o, final Object o2, final Object o3) {
        final int n = this.hashCode(o, o2, o3) & Integer.MAX_VALUE;
        for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
            if (next.hash == n && next.match(o, o2, o3)) {
                return next.value;
            }
        }
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
    
    protected int hashCode(final Object o, final Object o2, final Object o3) {
        return ((o == null) ? 0 : o.hashCode()) ^ ((o2 == null) ? 0 : o2.hashCode()) ^ ((o3 == null) ? 0 : o3.hashCode());
    }
    
    protected static class Entry
    {
        public int hash;
        public Object key1;
        public Object key2;
        public Object key3;
        public Object value;
        public Entry next;
        
        public Entry(final int hash, final Object key1, final Object key2, final Object key3, final Object value, final Entry next) {
            this.hash = hash;
            this.key1 = key1;
            this.key2 = key2;
            this.key3 = key3;
            this.value = value;
            this.next = next;
        }
        
        public boolean match(final Object obj, final Object obj2, final Object obj3) {
            if (this.key1 != null) {
                if (!this.key1.equals(obj)) {
                    return false;
                }
            }
            else if (obj != null) {
                return false;
            }
            if (this.key2 != null) {
                if (!this.key2.equals(obj2)) {
                    return false;
                }
            }
            else if (obj2 != null) {
                return false;
            }
            if (this.key3 != null) {
                return this.key3.equals(obj3);
            }
            return obj3 == null;
        }
    }
}
