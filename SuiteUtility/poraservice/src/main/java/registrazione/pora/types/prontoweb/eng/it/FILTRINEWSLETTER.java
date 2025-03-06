// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FILTRINEWSLETTER")
@XmlEnum
public enum FILTRINEWSLETTER
{
    TIPOSOGGETTO("TIPOSOGGETTO", 0), 
    STATOSOGGETTO("STATOSOGGETTO", 1), 
    CODSOGGETTO("CODSOGGETTO", 2), 
    LINGUA("LINGUA", 3), 
    TIPOSERVIZIO("TIPOSERVIZIO", 4), 
    STATOFORNITURA("STATOFORNITURA", 5), 
    PRODOTTO("PRODOTTO", 6), 
    DOMICILIAZIONEATTIVA("DOMICILIAZIONEATTIVA", 7), 
    REGIONEUBICAZIONEFORNITURA("REGIONEUBICAZIONEFORNITURA", 8), 
    PROVINCIAUBICAZIONEFORNITURA("PROVINCIAUBICAZIONEFORNITURA", 9), 
    COMUNEUBICAZIONEFORNITURA("COMUNEUBICAZIONEFORNITURA", 10), 
    LINEAPRODOTTO("LINEAPRODOTTO", 11), 
    PAPERLES("PAPERLES", 12), 
    TIPOCONTRATTODUAL("TIPOCONTRATTODUAL", 13), 
    DATASTIPULACONTRATTO("DATASTIPULACONTRATTO", 14);
    
    private FILTRINEWSLETTER(final String name, final int ordinal) {
    }
    
    public String value() {
        return this.name();
    }
    
    public static FILTRINEWSLETTER fromValue(final String v) {
        return valueOf(v);
    }
}
