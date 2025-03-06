// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATIINSERIMENTORICHIESTA", propOrder = { "codsoggetto", "codsoggettogestito", "codtiporichiesta", "email", "nominativo", "richiesta" })
public class DATIINSERIMENTORICHIESTA
{
    @XmlElement(name = "CODSOGGETTO", nillable = true)
    protected String codsoggetto;
    @XmlElement(name = "CODSOGGETTOGESTITO", nillable = true)
    protected String codsoggettogestito;
    @XmlElement(name = "CODTIPORICHIESTA")
    @XmlSchemaType(name = "string")
    protected TIPORICHIESTA codtiporichiesta;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "NOMINATIVO", nillable = true)
    protected String nominativo;
    @XmlElement(name = "Richiesta", nillable = true)
    protected Richiesta richiesta;
    
    public String getCODSOGGETTO() {
        return this.codsoggetto;
    }
    
    public void setCODSOGGETTO(final String value) {
        this.codsoggetto = value;
    }
    
    public String getCODSOGGETTOGESTITO() {
        return this.codsoggettogestito;
    }
    
    public void setCODSOGGETTOGESTITO(final String value) {
        this.codsoggettogestito = value;
    }
    
    public TIPORICHIESTA getCODTIPORICHIESTA() {
        return this.codtiporichiesta;
    }
    
    public void setCODTIPORICHIESTA(final TIPORICHIESTA value) {
        this.codtiporichiesta = value;
    }
    
    public String getEMAIL() {
        return this.email;
    }
    
    public void setEMAIL(final String value) {
        this.email = value;
    }
    
    public String getNOMINATIVO() {
        return this.nominativo;
    }
    
    public void setNOMINATIVO(final String value) {
        this.nominativo = value;
    }
    
    public Richiesta getRichiesta() {
        return this.richiesta;
    }
    
    public void setRichiesta(final Richiesta value) {
        this.richiesta = value;
    }
}
