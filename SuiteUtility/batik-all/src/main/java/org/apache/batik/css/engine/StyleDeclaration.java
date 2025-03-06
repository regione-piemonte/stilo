// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.apache.batik.css.engine.value.Value;

public class StyleDeclaration
{
    protected static final int INITIAL_LENGTH = 8;
    protected Value[] values;
    protected int[] indexes;
    protected boolean[] priorities;
    protected int count;
    
    public StyleDeclaration() {
        this.values = new Value[8];
        this.indexes = new int[8];
        this.priorities = new boolean[8];
    }
    
    public int size() {
        return this.count;
    }
    
    public Value getValue(final int n) {
        return this.values[n];
    }
    
    public int getIndex(final int n) {
        return this.indexes[n];
    }
    
    public boolean getPriority(final int n) {
        return this.priorities[n];
    }
    
    public void remove(final int n) {
        --this.count;
        final int n2 = n + 1;
        final int n3 = this.count - n;
        System.arraycopy(this.values, n2, this.values, n, n3);
        System.arraycopy(this.indexes, n2, this.indexes, n, n3);
        System.arraycopy(this.priorities, n2, this.priorities, n, n3);
        this.values[this.count] = null;
        this.indexes[this.count] = 0;
        this.priorities[this.count] = false;
    }
    
    public void put(final int n, final Value value, final int n2, final boolean b) {
        this.values[n] = value;
        this.indexes[n] = n2;
        this.priorities[n] = b;
    }
    
    public void append(final Value value, final int n, final boolean b) {
        if (this.values.length == this.count) {
            final Value[] values = new Value[this.count * 2];
            final int[] indexes = new int[this.count * 2];
            final boolean[] priorities = new boolean[this.count * 2];
            System.arraycopy(this.values, 0, values, 0, this.count);
            System.arraycopy(this.indexes, 0, indexes, 0, this.count);
            System.arraycopy(this.priorities, 0, priorities, 0, this.count);
            this.values = values;
            this.indexes = indexes;
            this.priorities = priorities;
        }
        for (int i = 0; i < this.count; ++i) {
            if (this.indexes[i] == n) {
                if (b || this.priorities[i] == b) {
                    this.values[i] = value;
                    this.priorities[i] = b;
                }
                return;
            }
        }
        this.values[this.count] = value;
        this.indexes[this.count] = n;
        this.priorities[this.count] = b;
        ++this.count;
    }
    
    public String toString(final CSSEngine cssEngine) {
        final StringBuffer sb = new StringBuffer(this.count * 8);
        for (int i = 0; i < this.count; ++i) {
            sb.append(cssEngine.getPropertyName(this.indexes[i]));
            sb.append(": ");
            sb.append(this.values[i]);
            sb.append(";\n");
        }
        return sb.toString();
    }
}
