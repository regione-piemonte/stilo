// 
// Decompiled by Procyon v0.5.36
// 

package lettureconsumi.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TIPOFILE")
@XmlEnum
public enum TIPOFILE
{
    CSV("CSV", 0), 
    XLSX("XLSX", 1);
    
    private TIPOFILE(final String name, final int ordinal) {
    }
    
    public String value() {
        return this.name();
    }
    
    public static TIPOFILE fromValue(final String v) {
        return valueOf(v);
    }
}
