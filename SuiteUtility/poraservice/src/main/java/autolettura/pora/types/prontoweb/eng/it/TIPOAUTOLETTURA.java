// 
// Decompiled by Procyon v0.5.36
// 

package autolettura.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TIPOAUTOLETTURA")
@XmlEnum
public enum TIPOAUTOLETTURA
{
    SWITCHING("SWITCHING", 0), 
    CICLO("CICLO", 1);
    
    private TIPOAUTOLETTURA(final String name, final int ordinal) {
    }
    
    public String value() {
        return this.name();
    }
    
    public static TIPOAUTOLETTURA fromValue(final String v) {
        return valueOf(v);
    }
}
