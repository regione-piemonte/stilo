// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Agente", propOrder = { "codOperatore", "codProvenienza", "codProfilo" })
public class Agente
{
    @XmlElement(name = "CodOperatore", nillable = true)
    protected String codOperatore;
    @XmlElement(name = "CodProvenienza", nillable = true)
    protected String codProvenienza;
    @XmlElement(name = "CodProfilo", nillable = true)
    protected String codProfilo;
    
    public String getCodOperatore() {
        return this.codOperatore;
    }
    
    public void setCodOperatore(final String value) {
        this.codOperatore = value;
    }
    
    public String getCodProvenienza() {
        return this.codProvenienza;
    }
    
    public void setCodProvenienza(final String value) {
        this.codProvenienza = value;
    }
    
    public String getCodProfilo() {
        return this.codProfilo;
    }
    
    public void setCodProfilo(final String value) {
        this.codProfilo = value;
    }
}
