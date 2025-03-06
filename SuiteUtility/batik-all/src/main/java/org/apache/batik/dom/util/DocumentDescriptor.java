// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

import org.apache.batik.util.CleanerThread;
import org.w3c.dom.Element;

public class DocumentDescriptor
{
    protected static final int INITIAL_CAPACITY = 101;
    protected Entry[] table;
    protected int count;
    
    public DocumentDescriptor() {
        this.table = new Entry[101];
    }
    
    public int getNumberOfElements() {
        synchronized (this) {
            return this.count;
        }
    }
    
    public int getLocationLine(final Element element) {
        synchronized (this) {
            final int n = element.hashCode() & Integer.MAX_VALUE;
            for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
                if (next.hash == n) {
                    if (next.get() == element) {
                        return next.locationLine;
                    }
                }
            }
        }
        return 0;
    }
    
    public int getLocationColumn(final Element element) {
        synchronized (this) {
            final int n = element.hashCode() & Integer.MAX_VALUE;
            for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
                if (next.hash == n) {
                    if (next.get() == element) {
                        return next.locationColumn;
                    }
                }
            }
        }
        return 0;
    }
    
    public void setLocation(final Element element, final int locationLine, final int n) {
        synchronized (this) {
            final int n2 = element.hashCode() & Integer.MAX_VALUE;
            int n3 = n2 % this.table.length;
            for (Entry next = this.table[n3]; next != null; next = next.next) {
                if (next.hash == n2) {
                    if (next.get() == element) {
                        next.locationLine = locationLine;
                    }
                }
            }
            final int length = this.table.length;
            if (this.count++ >= length - (length >> 2)) {
                this.rehash();
                n3 = n2 % this.table.length;
            }
            this.table[n3] = new Entry(n2, element, locationLine, n, this.table[n3]);
        }
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
    
    protected void removeEntry(final Entry entry) {
        synchronized (this) {
            final int n = entry.hash % this.table.length;
            Entry next = this.table[n];
            Entry entry2 = null;
            while (next != entry) {
                entry2 = next;
                next = next.next;
            }
            if (next == null) {
                return;
            }
            if (entry2 == null) {
                this.table[n] = next.next;
            }
            else {
                entry2.next = next.next;
            }
            --this.count;
        }
    }
    
    protected class Entry extends CleanerThread.WeakReferenceCleared
    {
        public int hash;
        public int locationLine;
        public int locationColumn;
        public Entry next;
        
        public Entry(final int hash, final Element element, final int locationLine, final int locationColumn, final Entry next) {
            super(element);
            this.hash = hash;
            this.locationLine = locationLine;
            this.locationColumn = locationColumn;
            this.next = next;
        }
        
        public void cleared() {
            DocumentDescriptor.this.removeEntry(this);
        }
    }
}
