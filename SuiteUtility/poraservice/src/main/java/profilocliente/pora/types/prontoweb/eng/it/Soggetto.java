// 
// Decompiled by Procyon v0.5.36
// 

package profilocliente.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Soggetto", propOrder = { "codsog", "codsogsup", "datafinerelazione", "datainiziorelazione", "gerarchia", "livello", "nominativosog", "nominativosogsup" })
public class Soggetto
{
    @XmlElement(name = "CODSOG", nillable = true)
    protected String codsog;
    @XmlElement(name = "CODSOGSUP", nillable = true)
    protected String codsogsup;
    @XmlElement(name = "DATAFINERELAZIONE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafinerelazione;
    @XmlElement(name = "DATAINIZIORELAZIONE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datainiziorelazione;
    @XmlElement(name = "GERARCHIA", nillable = true)
    protected String gerarchia;
    @XmlElement(name = "LIVELLO")
    protected Integer livello;
    @XmlElement(name = "NOMINATIVOSOG", nillable = true)
    protected String nominativosog;
    @XmlElement(name = "NOMINATIVOSOGSUP", nillable = true)
    protected String nominativosogsup;
    
    public String getCODSOG() {
        return this.codsog;
    }
    
    public void setCODSOG(final String value) {
        this.codsog = value;
    }
    
    public String getCODSOGSUP() {
        return this.codsogsup;
    }
    
    public void setCODSOGSUP(final String value) {
        this.codsogsup = value;
    }
    
    public XMLGregorianCalendar getDATAFINERELAZIONE() {
        return this.datafinerelazione;
    }
    
    public void setDATAFINERELAZIONE(final XMLGregorianCalendar value) {
        this.datafinerelazione = value;
    }
    
    public XMLGregorianCalendar getDATAINIZIORELAZIONE() {
        return this.datainiziorelazione;
    }
    
    public void setDATAINIZIORELAZIONE(final XMLGregorianCalendar value) {
        this.datainiziorelazione = value;
    }
    
    public String getGERARCHIA() {
        return this.gerarchia;
    }
    
    public void setGERARCHIA(final String value) {
        this.gerarchia = value;
    }
    
    public Integer getLIVELLO() {
        return this.livello;
    }
    
    public void setLIVELLO(final Integer value) {
        this.livello = value;
    }
    
    public String getNOMINATIVOSOG() {
        return this.nominativosog;
    }
    
    public void setNOMINATIVOSOG(final String value) {
        this.nominativosog = value;
    }
    
    public String getNOMINATIVOSOGSUP() {
        return this.nominativosogsup;
    }
    
    public void setNOMINATIVOSOGSUP(final String value) {
        this.nominativosogsup = value;
    }
}
