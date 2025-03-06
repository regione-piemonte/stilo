// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

import java.io.Serializable;

public class HashTable implements Serializable
{
    protected static final int INITIAL_CAPACITY = 11;
    protected Entry[] table;
    protected int count;
    
    public HashTable() {
        this.table = new Entry[11];
    }
    
    public HashTable(final int n) {
        this.table = new Entry[n];
    }
    
    public HashTable(final HashTable hashTable) {
        this.count = hashTable.count;
        this.table = new Entry[hashTable.table.length];
        for (int i = 0; i < this.table.length; ++i) {
            final Entry entry = hashTable.table[i];
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
    
    public int size() {
        return this.count;
    }
    
    public Object get(final Object obj) {
        final int n = (obj == null) ? 0 : (obj.hashCode() & Integer.MAX_VALUE);
        for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
            if (next.hash == n && ((next.key == null && obj == null) || (next.key != null && next.key.equals(obj)))) {
                return next.value;
            }
        }
        return null;
    }
    
    public Object put(final Object obj, final Object value) {
        final int n = (obj == null) ? 0 : (obj.hashCode() & Integer.MAX_VALUE);
        int n2 = n % this.table.length;
        for (Entry next = this.table[n2]; next != null; next = next.next) {
            if (next.hash == n && ((next.key == null && obj == null) || (next.key != null && next.key.equals(obj)))) {
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
        this.table[n2] = new Entry(n, obj, value, this.table[n2]);
        return null;
    }
    
    public Object remove(final Object obj) {
        final int n = (obj == null) ? 0 : (obj.hashCode() & Integer.MAX_VALUE);
        final int n2 = n % this.table.length;
        Entry entry = null;
        for (Entry next = this.table[n2]; next != null; next = next.next) {
            if (next.hash == n && ((next.key == null && obj == null) || (next.key != null && next.key.equals(obj)))) {
                final Object value = next.value;
                if (entry == null) {
                    this.table[n2] = next.next;
                }
                else {
                    entry.next = next.next;
                }
                --this.count;
                return value;
            }
            entry = next;
        }
        return null;
    }
    
    public Object key(final int n) {
        if (n < 0 || n >= this.count) {
            return null;
        }
        int n2 = 0;
    Label_0069:
        for (int i = 0; i < this.table.length; ++i) {
            Entry next = this.table[i];
            if (next != null) {
                while (n2++ != n) {
                    next = next.next;
                    if (next == null) {
                        continue Label_0069;
                    }
                }
                return next.key;
            }
        }
        return null;
    }
    
    public Object item(final int n) {
        if (n < 0 || n >= this.count) {
            return null;
        }
        int n2 = 0;
    Label_0069:
        for (int i = 0; i < this.table.length; ++i) {
            Entry next = this.table[i];
            if (next != null) {
                while (n2++ != n) {
                    next = next.next;
                    if (next == null) {
                        continue Label_0069;
                    }
                }
                return next.value;
            }
        }
        return null;
    }
    
    public void clear() {
        for (int i = 0; i < this.table.length; ++i) {
            this.table[i] = null;
        }
        this.count = 0;
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
    
    protected static class Entry implements Serializable
    {
        public int hash;
        public Object key;
        public Object value;
        public Entry next;
        
        public Entry(final int hash, final Object key, final Object value, final Entry next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
