// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STATOCONSENSO", propOrder = { "cod", "descrizione" })
public class STATOCONSENSO
{
    @XmlElement(name = "COD", nillable = true)
    protected String cod;
    @XmlElement(name = "DESCRIZIONE", nillable = true)
    protected String descrizione;
    
    public String getCOD() {
        return this.cod;
    }
    
    public void setCOD(final String value) {
        this.cod = value;
    }
    
    public String getDESCRIZIONE() {
        return this.descrizione;
    }
    
    public void setDESCRIZIONE(final String value) {
        this.descrizione = value;
    }
}
