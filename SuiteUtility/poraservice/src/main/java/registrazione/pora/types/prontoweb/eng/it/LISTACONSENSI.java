// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LISTACONSENSI", propOrder = { "staticonsenso", "tipiconsenso" })
public class LISTACONSENSI
{
    @XmlElement(name = "STATICONSENSO", required = true, nillable = true)
    protected ArrayOfSTATOCONSENSO staticonsenso;
    @XmlElement(name = "TIPICONSENSO", required = true, nillable = true)
    protected ArrayOfTIPOCONSENSO tipiconsenso;
    
    public ArrayOfSTATOCONSENSO getSTATICONSENSO() {
        return this.staticonsenso;
    }
    
    public void setSTATICONSENSO(final ArrayOfSTATOCONSENSO value) {
        this.staticonsenso = value;
    }
    
    public ArrayOfTIPOCONSENSO getTIPICONSENSO() {
        return this.tipiconsenso;
    }
    
    public void setTIPICONSENSO(final ArrayOfTIPOCONSENSO value) {
        this.tipiconsenso = value;
    }
}
