// 
// Decompiled by Procyon v0.5.36
// 

package domiciliazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DOMICILIAZIONE", propOrder = { "banca", "cin", "classificazioneconto", "codabi", "codcab", "codfisintestatario", "codfissottoscrittore", "contocorrente", "dataattivazione", "datarevoca", "domiciliazionecod", "iban", "intestatario", "messaggio", "nomeintestatario", "nomesottoscrittore", "pivaintestatario", "pivasottoscrittore", "statodomiciliazionecod" })
public class DOMICILIAZIONE
{
    @XmlElement(name = "BANCA", nillable = true)
    protected String banca;
    @XmlElement(name = "CIN", nillable = true)
    protected String cin;
    @XmlElement(name = "CLASSIFICAZIONECONTO", nillable = true)
    protected String classificazioneconto;
    @XmlElement(name = "CODABI", nillable = true)
    protected String codabi;
    @XmlElement(name = "CODCAB", nillable = true)
    protected String codcab;
    @XmlElement(name = "CODFISINTESTATARIO", nillable = true)
    protected String codfisintestatario;
    @XmlElement(name = "CODFISSOTTOSCRITTORE", nillable = true)
    protected String codfissottoscrittore;
    @XmlElement(name = "CONTOCORRENTE", nillable = true)
    protected String contocorrente;
    @XmlElement(name = "DATAATTIVAZIONE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataattivazione;
    @XmlElement(name = "DATAREVOCA")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datarevoca;
    @XmlElement(name = "DOMICILIAZIONECOD", nillable = true)
    protected String domiciliazionecod;
    @XmlElement(name = "IBAN", nillable = true)
    protected String iban;
    @XmlElement(name = "INTESTATARIO", nillable = true)
    protected String intestatario;
    @XmlElement(name = "MESSAGGIO", nillable = true)
    protected String messaggio;
    @XmlElement(name = "NOMEINTESTATARIO", nillable = true)
    protected String nomeintestatario;
    @XmlElement(name = "NOMESOTTOSCRITTORE", nillable = true)
    protected String nomesottoscrittore;
    @XmlElement(name = "PIVAINTESTATARIO", nillable = true)
    protected String pivaintestatario;
    @XmlElement(name = "PIVASOTTOSCRITTORE", nillable = true)
    protected String pivasottoscrittore;
    @XmlElement(name = "STATODOMICILIAZIONECOD", nillable = true)
    protected String statodomiciliazionecod;
    
    public String getBANCA() {
        return this.banca;
    }
    
    public void setBANCA(final String value) {
        this.banca = value;
    }
    
    public String getCIN() {
        return this.cin;
    }
    
    public void setCIN(final String value) {
        this.cin = value;
    }
    
    public String getCLASSIFICAZIONECONTO() {
        return this.classificazioneconto;
    }
    
    public void setCLASSIFICAZIONECONTO(final String value) {
        this.classificazioneconto = value;
    }
    
    public String getCODABI() {
        return this.codabi;
    }
    
    public void setCODABI(final String value) {
        this.codabi = value;
    }
    
    public String getCODCAB() {
        return this.codcab;
    }
    
    public void setCODCAB(final String value) {
        this.codcab = value;
    }
    
    public String getCODFISINTESTATARIO() {
        return this.codfisintestatario;
    }
    
    public void setCODFISINTESTATARIO(final String value) {
        this.codfisintestatario = value;
    }
    
    public String getCODFISSOTTOSCRITTORE() {
        return this.codfissottoscrittore;
    }
    
    public void setCODFISSOTTOSCRITTORE(final String value) {
        this.codfissottoscrittore = value;
    }
    
    public String getCONTOCORRENTE() {
        return this.contocorrente;
    }
    
    public void setCONTOCORRENTE(final String value) {
        this.contocorrente = value;
    }
    
    public XMLGregorianCalendar getDATAATTIVAZIONE() {
        return this.dataattivazione;
    }
    
    public void setDATAATTIVAZIONE(final XMLGregorianCalendar value) {
        this.dataattivazione = value;
    }
    
    public XMLGregorianCalendar getDATAREVOCA() {
        return this.datarevoca;
    }
    
    public void setDATAREVOCA(final XMLGregorianCalendar value) {
        this.datarevoca = value;
    }
    
    public String getDOMICILIAZIONECOD() {
        return this.domiciliazionecod;
    }
    
    public void setDOMICILIAZIONECOD(final String value) {
        this.domiciliazionecod = value;
    }
    
    public String getIBAN() {
        return this.iban;
    }
    
    public void setIBAN(final String value) {
        this.iban = value;
    }
    
    public String getINTESTATARIO() {
        return this.intestatario;
    }
    
    public void setINTESTATARIO(final String value) {
        this.intestatario = value;
    }
    
    public String getMESSAGGIO() {
        return this.messaggio;
    }
    
    public void setMESSAGGIO(final String value) {
        this.messaggio = value;
    }
    
    public String getNOMEINTESTATARIO() {
        return this.nomeintestatario;
    }
    
    public void setNOMEINTESTATARIO(final String value) {
        this.nomeintestatario = value;
    }
    
    public String getNOMESOTTOSCRITTORE() {
        return this.nomesottoscrittore;
    }
    
    public void setNOMESOTTOSCRITTORE(final String value) {
        this.nomesottoscrittore = value;
    }
    
    public String getPIVAINTESTATARIO() {
        return this.pivaintestatario;
    }
    
    public void setPIVAINTESTATARIO(final String value) {
        this.pivaintestatario = value;
    }
    
    public String getPIVASOTTOSCRITTORE() {
        return this.pivasottoscrittore;
    }
    
    public void setPIVASOTTOSCRITTORE(final String value) {
        this.pivasottoscrittore = value;
    }
    
    public String getSTATODOMICILIAZIONECOD() {
        return this.statodomiciliazionecod;
    }
    
    public void setSTATODOMICILIAZIONECOD(final String value) {
        this.statodomiciliazionecod = value;
    }
}
