// 
// Decompiled by Procyon v0.5.36
// 

package filtri.types.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FiltroRichiesta", propOrder = { "codfornitura", "codsoggetto", "codtiporichiesta", "codtiposervizio", "dadata", "modalitaricerca", "nominativosoggetto" })
public class FiltroRichiesta
{
    @XmlElement(name = "CODFORNITURA", nillable = true)
    protected String codfornitura;
    @XmlElement(name = "CODSOGGETTO", nillable = true)
    protected String codsoggetto;
    @XmlElement(name = "CODTIPORICHIESTA", nillable = true)
    protected String codtiporichiesta;
    @XmlElement(name = "CODTIPOSERVIZIO", nillable = true)
    protected String codtiposervizio;
    @XmlElement(name = "DADATA")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dadata;
    @XmlElement(name = "MODALITARICERCA", nillable = true)
    protected String modalitaricerca;
    @XmlElement(name = "NOMINATIVOSOGGETTO", nillable = true)
    protected String nominativosoggetto;
    
    public String getCODFORNITURA() {
        return this.codfornitura;
    }
    
    public void setCODFORNITURA(final String value) {
        this.codfornitura = value;
    }
    
    public String getCODSOGGETTO() {
        return this.codsoggetto;
    }
    
    public void setCODSOGGETTO(final String value) {
        this.codsoggetto = value;
    }
    
    public String getCODTIPORICHIESTA() {
        return this.codtiporichiesta;
    }
    
    public void setCODTIPORICHIESTA(final String value) {
        this.codtiporichiesta = value;
    }
    
    public String getCODTIPOSERVIZIO() {
        return this.codtiposervizio;
    }
    
    public void setCODTIPOSERVIZIO(final String value) {
        this.codtiposervizio = value;
    }
    
    public XMLGregorianCalendar getDADATA() {
        return this.dadata;
    }
    
    public void setDADATA(final XMLGregorianCalendar value) {
        this.dadata = value;
    }
    
    public String getMODALITARICERCA() {
        return this.modalitaricerca;
    }
    
    public void setMODALITARICERCA(final String value) {
        this.modalitaricerca = value;
    }
    
    public String getNOMINATIVOSOGGETTO() {
        return this.nominativosoggetto;
    }
    
    public void setNOMINATIVOSOGGETTO(final String value) {
        this.nominativosoggetto = value;
    }
}
