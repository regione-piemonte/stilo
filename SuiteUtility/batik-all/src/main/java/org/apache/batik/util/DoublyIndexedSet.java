// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

public class DoublyIndexedSet
{
    protected DoublyIndexedTable table;
    protected static Object value;
    
    public DoublyIndexedSet() {
        this.table = new DoublyIndexedTable();
    }
    
    public int size() {
        return this.table.size();
    }
    
    public void add(final Object o, final Object o2) {
        this.table.put(o, o2, DoublyIndexedSet.value);
    }
    
    public void remove(final Object o, final Object o2) {
        this.table.remove(o, o2);
    }
    
    public boolean contains(final Object o, final Object o2) {
        return this.table.get(o, o2) != null;
    }
    
    public void clear() {
        this.table.clear();
    }
    
    static {
        DoublyIndexedSet.value = new Object();
    }
}
