// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TIPORICHIESTA")
@XmlEnum
public enum TIPORICHIESTA
{
    RECL("RECL", 0), 
    INFO("INFO", 1), 
    VARREC("VARREC", 2), 
    VARPOT("VARPOT", 3), 
    VARPRD("VARPRD", 4), 
    VARANA("VARANA", 5), 
    ATT("ATT", 6), 
    SUB("SUB", 7), 
    SWITCHIN("SWITCHIN", 8), 
    VARSW("VARSW", 9), 
    ATTDOM("ATTDOM", 10), 
    ATTBOLMAIL("ATTBOLMAIL", 11), 
    INFOSW("INFOSW", 12);
    
    private TIPORICHIESTA(final String name, final int ordinal) {
    }
    
    public String value() {
        return this.name();
    }
    
    public static TIPORICHIESTA fromValue(final String v) {
        return valueOf(v);
    }
}
