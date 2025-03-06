// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class DoublyIndexedTable
{
    protected int initialCapacity;
    protected Entry[] table;
    protected int count;
    
    public DoublyIndexedTable() {
        this(16);
    }
    
    public DoublyIndexedTable(final int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.table = new Entry[initialCapacity];
    }
    
    public DoublyIndexedTable(final DoublyIndexedTable doublyIndexedTable) {
        this.initialCapacity = doublyIndexedTable.initialCapacity;
        this.table = new Entry[doublyIndexedTable.table.length];
        for (int i = 0; i < doublyIndexedTable.table.length; ++i) {
            Entry entry = null;
            for (Entry next = doublyIndexedTable.table[i]; next != null; next = next.next) {
                entry = new Entry(next.hash, next.key1, next.key2, next.value, entry);
            }
            this.table[i] = entry;
        }
        this.count = doublyIndexedTable.count;
    }
    
    public int size() {
        return this.count;
    }
    
    public Object put(final Object o, final Object o2, final Object value) {
        final int n = this.hashCode(o, o2) & Integer.MAX_VALUE;
        int n2 = n % this.table.length;
        for (Entry next = this.table[n2]; next != null; next = next.next) {
            if (next.hash == n && next.match(o, o2)) {
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
        this.table[n2] = new Entry(n, o, o2, value, this.table[n2]);
        return null;
    }
    
    public Object get(final Object o, final Object o2) {
        final int n = this.hashCode(o, o2) & Integer.MAX_VALUE;
        for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
            if (next.hash == n && next.match(o, o2)) {
                return next.value;
            }
        }
        return null;
    }
    
    public Object remove(final Object o, final Object o2) {
        final int n = this.hashCode(o, o2) & Integer.MAX_VALUE;
        final int n2 = n % this.table.length;
        final Entry entry = this.table[n2];
        if (entry == null) {
            return null;
        }
        if (entry.hash == n && entry.match(o, o2)) {
            this.table[n2] = entry.next;
            --this.count;
            return entry.value;
        }
        Entry entry2 = entry;
        for (Entry entry3 = entry.next; entry3 != null; entry3 = entry3.next) {
            if (entry3.hash == n && entry3.match(o, o2)) {
                entry2.next = entry3.next;
                --this.count;
                return entry3.value;
            }
            entry2 = entry3;
        }
        return null;
    }
    
    public Object[] getValuesArray() {
        final Object[] array = new Object[this.count];
        int n = 0;
        for (int i = 0; i < this.table.length; ++i) {
            for (Entry next = this.table[i]; next != null; next = next.next) {
                array[n++] = next.value;
            }
        }
        return array;
    }
    
    public void clear() {
        this.table = new Entry[this.initialCapacity];
        this.count = 0;
    }
    
    public Iterator iterator() {
        return new TableIterator();
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
    
    protected class TableIterator implements Iterator
    {
        private int nextIndex;
        private Entry nextEntry;
        private boolean finished;
        
        public TableIterator() {
            while (this.nextIndex < DoublyIndexedTable.this.table.length) {
                this.nextEntry = DoublyIndexedTable.this.table[this.nextIndex];
                if (this.nextEntry != null) {
                    break;
                }
                ++this.nextIndex;
            }
            this.finished = (this.nextEntry == null);
        }
        
        public boolean hasNext() {
            return !this.finished;
        }
        
        public Object next() {
            if (this.finished) {
                throw new NoSuchElementException();
            }
            final Entry nextEntry = this.nextEntry;
            this.findNext();
            return nextEntry;
        }
        
        protected void findNext() {
            this.nextEntry = this.nextEntry.next;
            if (this.nextEntry == null) {
                ++this.nextIndex;
                while (this.nextIndex < DoublyIndexedTable.this.table.length) {
                    this.nextEntry = DoublyIndexedTable.this.table[this.nextIndex];
                    if (this.nextEntry != null) {
                        break;
                    }
                    ++this.nextIndex;
                }
            }
            this.finished = (this.nextEntry == null);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public static class Entry
    {
        protected int hash;
        protected Object key1;
        protected Object key2;
        protected Object value;
        protected Entry next;
        
        public Entry(final int hash, final Object key1, final Object key2, final Object value, final Entry next) {
            this.hash = hash;
            this.key1 = key1;
            this.key2 = key2;
            this.value = value;
            this.next = next;
        }
        
        public Object getKey1() {
            return this.key1;
        }
        
        public Object getKey2() {
            return this.key2;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        protected boolean match(final Object obj, final Object obj2) {
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
