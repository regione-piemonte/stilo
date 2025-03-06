// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.sac;

import org.w3c.css.sac.AttributeCondition;

public abstract class AbstractAttributeCondition implements AttributeCondition, ExtendedCondition
{
    protected String value;
    
    protected AbstractAttributeCondition(final String value) {
        this.value = value;
    }
    
    public boolean equals(final Object o) {
        return o != null && o.getClass() == this.getClass() && ((AbstractAttributeCondition)o).value.equals(this.value);
    }
    
    public int hashCode() {
        return (this.value == null) ? -1 : this.value.hashCode();
    }
    
    public int getSpecificity() {
        return 256;
    }
    
    public String getValue() {
        return this.value;
    }
}
