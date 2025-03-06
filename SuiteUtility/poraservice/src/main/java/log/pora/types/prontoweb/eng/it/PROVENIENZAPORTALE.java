// 
// Decompiled by Procyon v0.5.36
// 

package log.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PROVENIENZA_PORTALE")
@XmlEnum
public enum PROVENIENZAPORTALE
{
    CLIENTI("CLIENTI", 0), 
    AMMINISTRATORI("AMMINISTRATORI", 1);
    
    private PROVENIENZAPORTALE(final String name, final int ordinal) {
    }
    
    public String value() {
        return this.name();
    }
    
    public static PROVENIENZAPORTALE fromValue(final String v) {
        return valueOf(v);
    }
}
