// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RICHIESTAINFO", propOrder = { "annoprocesso", "causale", "codfornitura", "codicemacrostato", "codiceprocesso", "codsoggetto", "codsoggettogestito", "datacreazione", "datastato", "macrostato", "nominativosoggetto", "nominativosoggettogestito", "processofratelloid", "processoid", "processomasterid", "provenienza", "richiestaid", "stato", "ticket", "tipoprocesso", "tiporichiesta", "tiposervizio", "uploaddocweb" })
public class RICHIESTAINFO
{
    @XmlElement(name = "ANNOPROCESSO")
    protected Integer annoprocesso;
    @XmlElement(name = "CAUSALE", nillable = true)
    protected String causale;
    @XmlElement(name = "CODFORNITURA", nillable = true)
    protected String codfornitura;
    @XmlElement(name = "CODICEMACROSTATO", nillable = true)
    protected String codicemacrostato;
    @XmlElement(name = "CODICEPROCESSO", nillable = true)
    protected String codiceprocesso;
    @XmlElement(name = "CODSOGGETTO", nillable = true)
    protected String codsoggetto;
    @XmlElement(name = "CODSOGGETTOGESTITO", nillable = true)
    protected String codsoggettogestito;
    @XmlElement(name = "DATACREAZIONE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datacreazione;
    @XmlElement(name = "DATASTATO")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datastato;
    @XmlElement(name = "MACROSTATO", nillable = true)
    protected String macrostato;
    @XmlElement(name = "NOMINATIVOSOGGETTO", nillable = true)
    protected String nominativosoggetto;
    @XmlElement(name = "NOMINATIVOSOGGETTOGESTITO", nillable = true)
    protected String nominativosoggettogestito;
    @XmlElement(name = "PROCESSOFRATELLOID", nillable = true)
    protected String processofratelloid;
    @XmlElement(name = "PROCESSOID", nillable = true)
    protected String processoid;
    @XmlElement(name = "PROCESSOMASTERID", nillable = true)
    protected String processomasterid;
    @XmlElement(name = "PROVENIENZA", nillable = true)
    protected String provenienza;
    @XmlElement(name = "RICHIESTAID", nillable = true)
    protected String richiestaid;
    @XmlElement(name = "STATO", nillable = true)
    protected String stato;
    @XmlElement(name = "TICKET", nillable = true)
    protected String ticket;
    @XmlElement(name = "TIPOPROCESSO", nillable = true)
    protected String tipoprocesso;
    @XmlElement(name = "TIPORICHIESTA", nillable = true)
    protected String tiporichiesta;
    @XmlElement(name = "TIPOSERVIZIO", nillable = true)
    protected String tiposervizio;
    @XmlElement(name = "UPLOADDOCWEB")
    protected Boolean uploaddocweb;
    
    public Integer getANNOPROCESSO() {
        return this.annoprocesso;
    }
    
    public void setANNOPROCESSO(final Integer value) {
        this.annoprocesso = value;
    }
    
    public String getCAUSALE() {
        return this.causale;
    }
    
    public void setCAUSALE(final String value) {
        this.causale = value;
    }
    
    public String getCODFORNITURA() {
        return this.codfornitura;
    }
    
    public void setCODFORNITURA(final String value) {
        this.codfornitura = value;
    }
    
    public String getCODICEMACROSTATO() {
        return this.codicemacrostato;
    }
    
    public void setCODICEMACROSTATO(final String value) {
        this.codicemacrostato = value;
    }
    
    public String getCODICEPROCESSO() {
        return this.codiceprocesso;
    }
    
    public void setCODICEPROCESSO(final String value) {
        this.codiceprocesso = value;
    }
    
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
    
    public XMLGregorianCalendar getDATACREAZIONE() {
        return this.datacreazione;
    }
    
    public void setDATACREAZIONE(final XMLGregorianCalendar value) {
        this.datacreazione = value;
    }
    
    public XMLGregorianCalendar getDATASTATO() {
        return this.datastato;
    }
    
    public void setDATASTATO(final XMLGregorianCalendar value) {
        this.datastato = value;
    }
    
    public String getMACROSTATO() {
        return this.macrostato;
    }
    
    public void setMACROSTATO(final String value) {
        this.macrostato = value;
    }
    
    public String getNOMINATIVOSOGGETTO() {
        return this.nominativosoggetto;
    }
    
    public void setNOMINATIVOSOGGETTO(final String value) {
        this.nominativosoggetto = value;
    }
    
    public String getNOMINATIVOSOGGETTOGESTITO() {
        return this.nominativosoggettogestito;
    }
    
    public void setNOMINATIVOSOGGETTOGESTITO(final String value) {
        this.nominativosoggettogestito = value;
    }
    
    public String getPROCESSOFRATELLOID() {
        return this.processofratelloid;
    }
    
    public void setPROCESSOFRATELLOID(final String value) {
        this.processofratelloid = value;
    }
    
    public String getPROCESSOID() {
        return this.processoid;
    }
    
    public void setPROCESSOID(final String value) {
        this.processoid = value;
    }
    
    public String getPROCESSOMASTERID() {
        return this.processomasterid;
    }
    
    public void setPROCESSOMASTERID(final String value) {
        this.processomasterid = value;
    }
    
    public String getPROVENIENZA() {
        return this.provenienza;
    }
    
    public void setPROVENIENZA(final String value) {
        this.provenienza = value;
    }
    
    public String getRICHIESTAID() {
        return this.richiestaid;
    }
    
    public void setRICHIESTAID(final String value) {
        this.richiestaid = value;
    }
    
    public String getSTATO() {
        return this.stato;
    }
    
    public void setSTATO(final String value) {
        this.stato = value;
    }
    
    public String getTICKET() {
        return this.ticket;
    }
    
    public void setTICKET(final String value) {
        this.ticket = value;
    }
    
    public String getTIPOPROCESSO() {
        return this.tipoprocesso;
    }
    
    public void setTIPOPROCESSO(final String value) {
        this.tipoprocesso = value;
    }
    
    public String getTIPORICHIESTA() {
        return this.tiporichiesta;
    }
    
    public void setTIPORICHIESTA(final String value) {
        this.tiporichiesta = value;
    }
    
    public String getTIPOSERVIZIO() {
        return this.tiposervizio;
    }
    
    public void setTIPOSERVIZIO(final String value) {
        this.tiposervizio = value;
    }
    
    public Boolean isUPLOADDOCWEB() {
        return this.uploaddocweb;
    }
    
    public void setUPLOADDOCWEB(final Boolean value) {
        this.uploaddocweb = value;
    }
}
