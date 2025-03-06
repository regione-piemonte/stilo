// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.lang.ref.SoftReference;
import java.lang.ref.ReferenceQueue;

public class SoftDoublyIndexedTable
{
    protected static final int INITIAL_CAPACITY = 11;
    protected Entry[] table;
    protected int count;
    protected ReferenceQueue referenceQueue;
    
    public SoftDoublyIndexedTable() {
        this.referenceQueue = new ReferenceQueue();
        this.table = new Entry[11];
    }
    
    public SoftDoublyIndexedTable(final int n) {
        this.referenceQueue = new ReferenceQueue();
        this.table = new Entry[n];
    }
    
    public int size() {
        return this.count;
    }
    
    public Object get(final Object o, final Object o2) {
        final int n = this.hashCode(o, o2) & Integer.MAX_VALUE;
        for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
            if (next.hash == n && next.match(o, o2)) {
                return next.get();
            }
        }
        return null;
    }
    
    public Object put(final Object o, final Object o2, final Object o3) {
        this.removeClearedEntries();
        final int n = this.hashCode(o, o2) & Integer.MAX_VALUE;
        int n2 = n % this.table.length;
        final Entry entry = this.table[n2];
        if (entry != null) {
            if (entry.hash == n && entry.match(o, o2)) {
                final Object value = entry.get();
                this.table[n2] = new Entry(n, o, o2, o3, entry.next);
                return value;
            }
            Entry entry2 = entry;
            for (Entry entry3 = entry.next; entry3 != null; entry3 = entry3.next) {
                if (entry3.hash == n && entry3.match(o, o2)) {
                    final Object value2 = entry3.get();
                    entry2.next = new Entry(n, o, o2, o3, entry3.next);
                    return value2;
                }
                entry2 = entry3;
            }
        }
        final int length = this.table.length;
        if (this.count++ >= length - (length >> 2)) {
            this.rehash();
            n2 = n % this.table.length;
        }
        this.table[n2] = new Entry(n, o, o2, o3, this.table[n2]);
        return null;
    }
    
    public void clear() {
        this.table = new Entry[11];
        this.count = 0;
        this.referenceQueue = new ReferenceQueue();
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
    
    protected int hashCode(final Object o, final Object o2) {
        return ((o == null) ? 0 : o.hashCode()) ^ ((o2 == null) ? 0 : o2.hashCode());
    }
    
    protected void removeClearedEntries() {
        Entry entry;
        while ((entry = (Entry)this.referenceQueue.poll()) != null) {
            final int n = entry.hash % this.table.length;
            Entry entry2 = this.table[n];
            if (entry2 == entry) {
                this.table[n] = entry.next;
            }
            else {
                while (entry2 != null) {
                    final Entry next = entry2.next;
                    if (next == entry) {
                        entry2.next = entry.next;
                        break;
                    }
                    entry2 = next;
                }
            }
            --this.count;
        }
    }
    
    protected class Entry extends SoftReference
    {
        public int hash;
        public Object key1;
        public Object key2;
        public Entry next;
        
        public Entry(final int hash, final Object key1, final Object key2, final Object referent, final Entry next) {
            super(referent, SoftDoublyIndexedTable.this.referenceQueue);
            this.hash = hash;
            this.key1 = key1;
            this.key2 = key2;
            this.next = next;
        }
        
        public boolean match(final Object obj, final Object obj2) {
            if (this.key1 != null) {
                if (!this.key1.equals(obj)) {
                    return false;
                }
            }
            else if (obj != null) {
                return false;
            }
            if (this.key2 != null) {
                return this.key2.equals(obj2);
            }
            return obj2 == null;
        }
    }
}
