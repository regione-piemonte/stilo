// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.lang.ref.SoftReference;
import java.lang.ref.ReferenceQueue;

public class AWTGlyphGeometryCache
{
    protected static final int INITIAL_CAPACITY = 71;
    protected Entry[] table;
    protected int count;
    protected ReferenceQueue referenceQueue;
    
    public AWTGlyphGeometryCache() {
        this.referenceQueue = new ReferenceQueue();
        this.table = new Entry[71];
    }
    
    public AWTGlyphGeometryCache(final int n) {
        this.referenceQueue = new ReferenceQueue();
        this.table = new Entry[n];
    }
    
    public int size() {
        return this.count;
    }
    
    public Value get(final char c) {
        final int n = this.hashCode(c) & Integer.MAX_VALUE;
        for (Entry next = this.table[n % this.table.length]; next != null; next = next.next) {
            if (next.hash == n && next.match(c)) {
                return next.get();
            }
        }
        return null;
    }
    
    public Value put(final char c, final Value value) {
        this.removeClearedEntries();
        final int n = this.hashCode(c) & Integer.MAX_VALUE;
        int n2 = n % this.table.length;
        final Entry entry = this.table[n2];
        if (entry != null) {
            if (entry.hash == n && entry.match(c)) {
                final Value value2 = entry.get();
                this.table[n2] = new Entry(n, c, value, entry.next);
                return value2;
            }
            Entry entry2 = entry;
            for (Entry entry3 = entry.next; entry3 != null; entry3 = entry3.next) {
                if (entry3.hash == n && entry3.match(c)) {
                    final Value value3 = entry3.get();
                    entry2.next = new Entry(n, c, value, entry3.next);
                    return value3;
                }
                entry2 = entry3;
            }
        }
        final int length = this.table.length;
        if (this.count++ >= length - (length >> 2)) {
            this.rehash();
            n2 = n % this.table.length;
        }
        this.table[n2] = new Entry(n, c, value, this.table[n2]);
        return null;
    }
    
    public void clear() {
        this.table = new Entry[71];
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
    
    protected int hashCode(final char c) {
        return c;
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
        public char c;
        public Entry next;
        
        public Entry(final int hash, final char c, final Value referent, final Entry next) {
            super(referent, AWTGlyphGeometryCache.this.referenceQueue);
            this.hash = hash;
            this.c = c;
            this.next = next;
        }
        
        public boolean match(final char c) {
            return this.c == c;
        }
    }
    
    public static class Value
    {
        protected Shape outline;
        protected Rectangle2D gmB;
        protected Rectangle2D outlineBounds;
        
        public Value(final Shape outline, final Rectangle2D gmB) {
            this.outline = outline;
            this.outlineBounds = outline.getBounds2D();
            this.gmB = gmB;
        }
        
        public Shape getOutline() {
            return this.outline;
        }
        
        public Rectangle2D getBounds2D() {
            return this.gmB;
        }
        
        public Rectangle2D getOutlineBounds2D() {
            return this.outlineBounds;
        }
    }
}
