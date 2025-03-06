// 
// Decompiled by Procyon v0.5.36
// 

package estrattoconto.pora.types.prontoweb.eng.it;

import pora.types.prontoweb.eng.it.INDIRIZZO;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DOCUMENTO", propOrder = { "anno", "canaleincassocod", "dataemissione", "datapagamento", "datariferimento", "datascadenza", "desctipopagamento", "documentoid", "documentoidnotencrypted", "domiciliazionecod", "esistenzapdf", "fornituracod", "importofornitura", "importorata", "indirizzoubicazione", "nominativointestatario", "nominativointestatariosup", "numero", "pagamentoid", "pdrpod", "rata", "riscontroid", "sezionale", "soggettocod", "soggettocodsup", "testatapagamentoid", "tipodocumentocod", "tipodocumentodesc", "tipofornituracod", "tipofornituradesc" })
public class DOCUMENTO
{
    @XmlElement(name = "ANNO")
    protected Integer anno;
    @XmlElement(name = "CANALEINCASSOCOD", nillable = true)
    protected String canaleincassocod;
    @XmlElement(name = "DATAEMISSIONE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataemissione;
    @XmlElement(name = "DATAPAGAMENTO")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datapagamento;
    @XmlElement(name = "DATARIFERIMENTO")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datariferimento;
    @XmlElement(name = "DATASCADENZA")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datascadenza;
    @XmlElement(name = "DESCTIPOPAGAMENTO", nillable = true)
    protected String desctipopagamento;
    @XmlElement(name = "DOCUMENTOID", required = true, nillable = true)
    protected String documentoid;
    @XmlElement(name = "DOCUMENTOID_NOTENCRYPTED", nillable = true)
    protected String documentoidnotencrypted;
    @XmlElement(name = "DOMICILIAZIONECOD", nillable = true)
    protected String domiciliazionecod;
    @XmlElement(name = "ESISTENZAPDF")
    protected Boolean esistenzapdf;
    @XmlElement(name = "FORNITURACOD", nillable = true)
    protected String fornituracod;
    @XmlElement(name = "IMPORTOFORNITURA")
    protected Double importofornitura;
    @XmlElement(name = "IMPORTORATA")
    protected Double importorata;
    @XmlElement(name = "INDIRIZZOUBICAZIONE", nillable = true)
    protected INDIRIZZO indirizzoubicazione;
    @XmlElement(name = "NOMINATIVOINTESTATARIO", nillable = true)
    protected String nominativointestatario;
    @XmlElement(name = "NOMINATIVOINTESTATARIOSUP", nillable = true)
    protected String nominativointestatariosup;
    @XmlElement(name = "NUMERO")
    protected Long numero;
    @XmlElement(name = "PAGAMENTOID", nillable = true)
    protected String pagamentoid;
    @XmlElement(name = "PDRPOD", nillable = true)
    protected String pdrpod;
    @XmlElement(name = "RATA")
    protected Integer rata;
    @XmlElement(name = "RISCONTROID", nillable = true)
    protected String riscontroid;
    @XmlElement(name = "SEZIONALE", nillable = true)
    protected String sezionale;
    @XmlElement(name = "SOGGETTOCOD", nillable = true)
    protected String soggettocod;
    @XmlElement(name = "SOGGETTOCODSUP", nillable = true)
    protected String soggettocodsup;
    @XmlElement(name = "TESTATAPAGAMENTOID", nillable = true)
    protected String testatapagamentoid;
    @XmlElement(name = "TIPODOCUMENTOCOD", nillable = true)
    protected String tipodocumentocod;
    @XmlElement(name = "TIPODOCUMENTODESC", nillable = true)
    protected String tipodocumentodesc;
    @XmlElement(name = "TIPOFORNITURACOD", nillable = true)
    protected String tipofornituracod;
    @XmlElement(name = "TIPOFORNITURADESC", nillable = true)
    protected String tipofornituradesc;
    
    public Integer getANNO() {
        return this.anno;
    }
    
    public void setANNO(final Integer value) {
        this.anno = value;
    }
    
    public String getCANALEINCASSOCOD() {
        return this.canaleincassocod;
    }
    
    public void setCANALEINCASSOCOD(final String value) {
        this.canaleincassocod = value;
    }
    
    public XMLGregorianCalendar getDATAEMISSIONE() {
        return this.dataemissione;
    }
    
    public void setDATAEMISSIONE(final XMLGregorianCalendar value) {
        this.dataemissione = value;
    }
    
    public XMLGregorianCalendar getDATAPAGAMENTO() {
        return this.datapagamento;
    }
    
    public void setDATAPAGAMENTO(final XMLGregorianCalendar value) {
        this.datapagamento = value;
    }
    
    public XMLGregorianCalendar getDATARIFERIMENTO() {
        return this.datariferimento;
    }
    
    public void setDATARIFERIMENTO(final XMLGregorianCalendar value) {
        this.datariferimento = value;
    }
    
    public XMLGregorianCalendar getDATASCADENZA() {
        return this.datascadenza;
    }
    
    public void setDATASCADENZA(final XMLGregorianCalendar value) {
        this.datascadenza = value;
    }
    
    public String getDESCTIPOPAGAMENTO() {
        return this.desctipopagamento;
    }
    
    public void setDESCTIPOPAGAMENTO(final String value) {
        this.desctipopagamento = value;
    }
    
    public String getDOCUMENTOID() {
        return this.documentoid;
    }
    
    public void setDOCUMENTOID(final String value) {
        this.documentoid = value;
    }
    
    public String getDOCUMENTOIDNOTENCRYPTED() {
        return this.documentoidnotencrypted;
    }
    
    public void setDOCUMENTOIDNOTENCRYPTED(final String value) {
        this.documentoidnotencrypted = value;
    }
    
    public String getDOMICILIAZIONECOD() {
        return this.domiciliazionecod;
    }
    
    public void setDOMICILIAZIONECOD(final String value) {
        this.domiciliazionecod = value;
    }
    
    public Boolean isESISTENZAPDF() {
        return this.esistenzapdf;
    }
    
    public void setESISTENZAPDF(final Boolean value) {
        this.esistenzapdf = value;
    }
    
    public String getFORNITURACOD() {
        return this.fornituracod;
    }
    
    public void setFORNITURACOD(final String value) {
        this.fornituracod = value;
    }
    
    public Double getIMPORTOFORNITURA() {
        return this.importofornitura;
    }
    
    public void setIMPORTOFORNITURA(final Double value) {
        this.importofornitura = value;
    }
    
    public Double getIMPORTORATA() {
        return this.importorata;
    }
    
    public void setIMPORTORATA(final Double value) {
        this.importorata = value;
    }
    
    public INDIRIZZO getINDIRIZZOUBICAZIONE() {
        return this.indirizzoubicazione;
    }
    
    public void setINDIRIZZOUBICAZIONE(final INDIRIZZO value) {
        this.indirizzoubicazione = value;
    }
    
    public String getNOMINATIVOINTESTATARIO() {
        return this.nominativointestatario;
    }
    
    public void setNOMINATIVOINTESTATARIO(final String value) {
        this.nominativointestatario = value;
    }
    
    public String getNOMINATIVOINTESTATARIOSUP() {
        return this.nominativointestatariosup;
    }
    
    public void setNOMINATIVOINTESTATARIOSUP(final String value) {
        this.nominativointestatariosup = value;
    }
    
    public Long getNUMERO() {
        return this.numero;
    }
    
    public void setNUMERO(final Long value) {
        this.numero = value;
    }
    
    public String getPAGAMENTOID() {
        return this.pagamentoid;
    }
    
    public void setPAGAMENTOID(final String value) {
        this.pagamentoid = value;
    }
    
    public String getPDRPOD() {
        return this.pdrpod;
    }
    
    public void setPDRPOD(final String value) {
        this.pdrpod = value;
    }
    
    public Integer getRATA() {
        return this.rata;
    }
    
    public void setRATA(final Integer value) {
        this.rata = value;
    }
    
    public String getRISCONTROID() {
        return this.riscontroid;
    }
    
    public void setRISCONTROID(final String value) {
        this.riscontroid = value;
    }
    
    public String getSEZIONALE() {
        return this.sezionale;
    }
    
    public void setSEZIONALE(final String value) {
        this.sezionale = value;
    }
    
    public String getSOGGETTOCOD() {
        return this.soggettocod;
    }
    
    public void setSOGGETTOCOD(final String value) {
        this.soggettocod = value;
    }
    
    public String getSOGGETTOCODSUP() {
        return this.soggettocodsup;
    }
    
    public void setSOGGETTOCODSUP(final String value) {
        this.soggettocodsup = value;
    }
    
    public String getTESTATAPAGAMENTOID() {
        return this.testatapagamentoid;
    }
    
    public void setTESTATAPAGAMENTOID(final String value) {
        this.testatapagamentoid = value;
    }
    
    public String getTIPODOCUMENTOCOD() {
        return this.tipodocumentocod;
    }
    
    public void setTIPODOCUMENTOCOD(final String value) {
        this.tipodocumentocod = value;
    }
    
    public String getTIPODOCUMENTODESC() {
        return this.tipodocumentodesc;
    }
    
    public void setTIPODOCUMENTODESC(final String value) {
        this.tipodocumentodesc = value;
    }
    
    public String getTIPOFORNITURACOD() {
        return this.tipofornituracod;
    }
    
    public void setTIPOFORNITURACOD(final String value) {
        this.tipofornituracod = value;
    }
    
    public String getTIPOFORNITURADESC() {
        return this.tipofornituradesc;
    }
    
    public void setTIPOFORNITURADESC(final String value) {
        this.tipofornituradesc = value;
    }
}
