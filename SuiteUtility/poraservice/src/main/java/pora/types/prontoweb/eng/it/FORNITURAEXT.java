// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import domiciliazione.pora.types.prontoweb.eng.it.DOMICILIAZIONE;
import registrazione.pora.types.prontoweb.eng.it.DATISOGGETTO;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import dettaglioservizio.pora.types.prontoweb.eng.it.DETTAGLIOSERVIZIO;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FORNITURAEXT", propOrder = { "basecomputo", "categoriacod", "categoriadesc", "categoriadusocod", "categoriadusodesc", "consumo", "contrattodual", "corrvolumetrico", "datafineforn", "datainiziocontratto", "datainizioforn", "dataswitching", "datisoggetto", "destinatariocorrispondenza", "domiciliazione", "email", "lineaprodottodesc", "potenzacod", "potenzacontrcod", "potenzacontrdesc", "potenzadesc", "prodottodesc", "resicod", "residesc" })
@XmlSeeAlso({ DETTAGLIOSERVIZIO.class })
public class FORNITURAEXT extends FORNITURA
{
    @XmlElement(name = "BASECOMPUTO")
    protected Double basecomputo;
    @XmlElement(name = "CATEGORIACOD", nillable = true)
    protected String categoriacod;
    @XmlElement(name = "CATEGORIADESC", nillable = true)
    protected String categoriadesc;
    @XmlElement(name = "CATEGORIADUSOCOD", nillable = true)
    protected String categoriadusocod;
    @XmlElement(name = "CATEGORIADUSODESC", nillable = true)
    protected String categoriadusodesc;
    @XmlElement(name = "CONSUMO", nillable = true)
    protected String consumo;
    @XmlElement(name = "CONTRATTODUAL")
    protected Boolean contrattodual;
    @XmlElement(name = "CORRVOLUMETRICO", nillable = true)
    protected String corrvolumetrico;
    @XmlElement(name = "DATAFINEFORN")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafineforn;
    @XmlElement(name = "DATAINIZIOCONTRATTO")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datainiziocontratto;
    @XmlElement(name = "DATAINIZIOFORN")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datainizioforn;
    @XmlElement(name = "DATASWITCHING")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataswitching;
    @XmlElement(name = "DATISOGGETTO", nillable = true)
    protected DATISOGGETTO datisoggetto;
    @XmlElement(name = "DESTINATARIOCORRISPONDENZA", nillable = true)
    protected String destinatariocorrispondenza;
    @XmlElement(name = "DOMICILIAZIONE", nillable = true)
    protected DOMICILIAZIONE domiciliazione;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "LINEAPRODOTTODESC", nillable = true)
    protected String lineaprodottodesc;
    @XmlElement(name = "POTENZACOD", nillable = true)
    protected String potenzacod;
    @XmlElement(name = "POTENZACONTRCOD", nillable = true)
    protected String potenzacontrcod;
    @XmlElement(name = "POTENZACONTRDESC", nillable = true)
    protected String potenzacontrdesc;
    @XmlElement(name = "POTENZADESC", nillable = true)
    protected String potenzadesc;
    @XmlElement(name = "PRODOTTODESC", nillable = true)
    protected String prodottodesc;
    @XmlElement(name = "RESICOD", nillable = true)
    protected String resicod;
    @XmlElement(name = "RESIDESC", nillable = true)
    protected String residesc;
    
    public Double getBASECOMPUTO() {
        return this.basecomputo;
    }
    
    public void setBASECOMPUTO(final Double value) {
        this.basecomputo = value;
    }
    
    public String getCATEGORIACOD() {
        return this.categoriacod;
    }
    
    public void setCATEGORIACOD(final String value) {
        this.categoriacod = value;
    }
    
    public String getCATEGORIADESC() {
        return this.categoriadesc;
    }
    
    public void setCATEGORIADESC(final String value) {
        this.categoriadesc = value;
    }
    
    public String getCATEGORIADUSOCOD() {
        return this.categoriadusocod;
    }
    
    public void setCATEGORIADUSOCOD(final String value) {
        this.categoriadusocod = value;
    }
    
    public String getCATEGORIADUSODESC() {
        return this.categoriadusodesc;
    }
    
    public void setCATEGORIADUSODESC(final String value) {
        this.categoriadusodesc = value;
    }
    
    public String getCONSUMO() {
        return this.consumo;
    }
    
    public void setCONSUMO(final String value) {
        this.consumo = value;
    }
    
    public Boolean isCONTRATTODUAL() {
        return this.contrattodual;
    }
    
    public void setCONTRATTODUAL(final Boolean value) {
        this.contrattodual = value;
    }
    
    public String getCORRVOLUMETRICO() {
        return this.corrvolumetrico;
    }
    
    public void setCORRVOLUMETRICO(final String value) {
        this.corrvolumetrico = value;
    }
    
    public XMLGregorianCalendar getDATAFINEFORN() {
        return this.datafineforn;
    }
    
    public void setDATAFINEFORN(final XMLGregorianCalendar value) {
        this.datafineforn = value;
    }
    
    public XMLGregorianCalendar getDATAINIZIOCONTRATTO() {
        return this.datainiziocontratto;
    }
    
    public void setDATAINIZIOCONTRATTO(final XMLGregorianCalendar value) {
        this.datainiziocontratto = value;
    }
    
    public XMLGregorianCalendar getDATAINIZIOFORN() {
        return this.datainizioforn;
    }
    
    public void setDATAINIZIOFORN(final XMLGregorianCalendar value) {
        this.datainizioforn = value;
    }
    
    public XMLGregorianCalendar getDATASWITCHING() {
        return this.dataswitching;
    }
    
    public void setDATASWITCHING(final XMLGregorianCalendar value) {
        this.dataswitching = value;
    }
    
    public DATISOGGETTO getDATISOGGETTO() {
        return this.datisoggetto;
    }
    
    public void setDATISOGGETTO(final DATISOGGETTO value) {
        this.datisoggetto = value;
    }
    
    public String getDESTINATARIOCORRISPONDENZA() {
        return this.destinatariocorrispondenza;
    }
    
    public void setDESTINATARIOCORRISPONDENZA(final String value) {
        this.destinatariocorrispondenza = value;
    }
    
    public DOMICILIAZIONE getDOMICILIAZIONE() {
        return this.domiciliazione;
    }
    
    public void setDOMICILIAZIONE(final DOMICILIAZIONE value) {
        this.domiciliazione = value;
    }
    
    public String getEMAIL() {
        return this.email;
    }
    
    public void setEMAIL(final String value) {
        this.email = value;
    }
    
    public String getLINEAPRODOTTODESC() {
        return this.lineaprodottodesc;
    }
    
    public void setLINEAPRODOTTODESC(final String value) {
        this.lineaprodottodesc = value;
    }
    
    public String getPOTENZACOD() {
        return this.potenzacod;
    }
    
    public void setPOTENZACOD(final String value) {
        this.potenzacod = value;
    }
    
    public String getPOTENZACONTRCOD() {
        return this.potenzacontrcod;
    }
    
    public void setPOTENZACONTRCOD(final String value) {
        this.potenzacontrcod = value;
    }
    
    public String getPOTENZACONTRDESC() {
        return this.potenzacontrdesc;
    }
    
    public void setPOTENZACONTRDESC(final String value) {
        this.potenzacontrdesc = value;
    }
    
    public String getPOTENZADESC() {
        return this.potenzadesc;
    }
    
    public void setPOTENZADESC(final String value) {
        this.potenzadesc = value;
    }
    
    public String getPRODOTTODESC() {
        return this.prodottodesc;
    }
    
    public void setPRODOTTODESC(final String value) {
        this.prodottodesc = value;
    }
    
    public String getRESICOD() {
        return this.resicod;
    }
    
    public void setRESICOD(final String value) {
        this.resicod = value;
    }
    
    public String getRESIDESC() {
        return this.residesc;
    }
    
    public void setRESIDESC(final String value) {
        this.residesc = value;
    }
}
