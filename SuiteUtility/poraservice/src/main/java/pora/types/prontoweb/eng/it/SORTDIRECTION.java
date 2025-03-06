// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SORTDIRECTION")
@XmlEnum
public enum SORTDIRECTION
{
    ASC("ASC", 0), 
    DESC("DESC", 1);
    
    private SORTDIRECTION(final String name, final int ordinal) {
    }
    
    public String value() {
        return this.name();
    }
    
    public static SORTDIRECTION fromValue(final String v) {
        return valueOf(v);
    }
}
