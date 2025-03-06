// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import pora.types.prontoweb.eng.it.INDIRIZZO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATISOGGETTO", propOrder = { "cellulare", "codcomunedinascita", "codicefiscale", "codlingua", "codnazionedinascita", "codnazionepartitaiva", "cognome", "datanascita", "datascaddocident", "datirappresentantelegale", "email", "entedocident", "fax", "indirizzorecapito", "indirizzorecapitorappresentantelegale", "indirizzoresidenza", "isprospect", "linguasoggetto", "luogonascita", "nazioneiva", "newsletter", "nome", "nominativointestatario", "nominativointestatariosup", "numdocident", "numtel", "partitaiva", "ragionesociale", "sesso", "soggettocod", "soggettocodsup", "soggettocodsupruolo", "soggettoid", "tipodocidentcod", "tipodocidentdesc", "tiposoggettocod", "tiposoggettodesc", "tiposoggettoid" })
@XmlSeeAlso({ DATISOGGETTOEXT.class })
public class DATISOGGETTO
{
    @XmlElement(name = "CELLULARE", nillable = true)
    protected String cellulare;
    @XmlElement(name = "CODCOMUNEDINASCITA", nillable = true)
    protected String codcomunedinascita;
    @XmlElement(name = "CODICEFISCALE", nillable = true)
    protected String codicefiscale;
    @XmlElement(name = "CODLINGUA", nillable = true)
    protected String codlingua;
    @XmlElement(name = "CODNAZIONEDINASCITA", nillable = true)
    protected String codnazionedinascita;
    @XmlElement(name = "CODNAZIONEPARTITAIVA", nillable = true)
    protected String codnazionepartitaiva;
    @XmlElement(name = "COGNOME", nillable = true)
    protected String cognome;
    @XmlElement(name = "DATANASCITA", nillable = true)
    protected String datanascita;
    @XmlElement(name = "DATASCADDOCIDENT", nillable = true)
    protected String datascaddocident;
    @XmlElement(name = "DATIRAPPRESENTANTELEGALE", nillable = true)
    protected DATISOGGETTO datirappresentantelegale;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "ENTEDOCIDENT", nillable = true)
    protected String entedocident;
    @XmlElement(name = "FAX", nillable = true)
    protected String fax;
    @XmlElement(name = "INDIRIZZORECAPITO", nillable = true)
    protected INDIRIZZO indirizzorecapito;
    @XmlElement(name = "INDIRIZZORECAPITORAPPRESENTANTELEGALE", nillable = true)
    protected INDIRIZZO indirizzorecapitorappresentantelegale;
    @XmlElement(name = "INDIRIZZORESIDENZA", nillable = true)
    protected INDIRIZZO indirizzoresidenza;
    @XmlElement(name = "ISPROSPECT")
    protected Boolean isprospect;
    @XmlElement(name = "LINGUASOGGETTO", nillable = true)
    protected String linguasoggetto;
    @XmlElement(name = "LUOGONASCITA", nillable = true)
    protected String luogonascita;
    @XmlElement(name = "NAZIONEIVA", nillable = true)
    protected String nazioneiva;
    @XmlElement(name = "NEWSLETTER", nillable = true)
    protected String newsletter;
    @XmlElement(name = "NOME", nillable = true)
    protected String nome;
    @XmlElement(name = "NOMINATIVOINTESTATARIO", nillable = true)
    protected String nominativointestatario;
    @XmlElement(name = "NOMINATIVOINTESTATARIOSUP", nillable = true)
    protected String nominativointestatariosup;
    @XmlElement(name = "NUMDOCIDENT", nillable = true)
    protected String numdocident;
    @XmlElement(name = "NUMTEL", nillable = true)
    protected String numtel;
    @XmlElement(name = "PARTITAIVA", nillable = true)
    protected String partitaiva;
    @XmlElement(name = "RAGIONESOCIALE", nillable = true)
    protected String ragionesociale;
    @XmlElement(name = "SESSO", nillable = true)
    protected String sesso;
    @XmlElement(name = "SOGGETTOCOD", required = true, nillable = true)
    protected String soggettocod;
    @XmlElement(name = "SOGGETTOCODSUP", nillable = true)
    protected String soggettocodsup;
    @XmlElement(name = "SOGGETTOCODSUPRUOLO", nillable = true)
    protected String soggettocodsupruolo;
    @XmlElement(name = "SOGGETTOID", nillable = true)
    protected String soggettoid;
    @XmlElement(name = "TIPODOCIDENTCOD", nillable = true)
    protected String tipodocidentcod;
    @XmlElement(name = "TIPODOCIDENTDESC", nillable = true)
    protected String tipodocidentdesc;
    @XmlElement(name = "TIPOSOGGETTOCOD", nillable = true)
    protected String tiposoggettocod;
    @XmlElement(name = "TIPOSOGGETTODESC", nillable = true)
    protected String tiposoggettodesc;
    @XmlElement(name = "TIPOSOGGETTOID", nillable = true)
    protected String tiposoggettoid;
    
    public String getCELLULARE() {
        return this.cellulare;
    }
    
    public void setCELLULARE(final String value) {
        this.cellulare = value;
    }
    
    public String getCODCOMUNEDINASCITA() {
        return this.codcomunedinascita;
    }
    
    public void setCODCOMUNEDINASCITA(final String value) {
        this.codcomunedinascita = value;
    }
    
    public String getCODICEFISCALE() {
        return this.codicefiscale;
    }
    
    public void setCODICEFISCALE(final String value) {
        this.codicefiscale = value;
    }
    
    public String getCODLINGUA() {
        return this.codlingua;
    }
    
    public void setCODLINGUA(final String value) {
        this.codlingua = value;
    }
    
    public String getCODNAZIONEDINASCITA() {
        return this.codnazionedinascita;
    }
    
    public void setCODNAZIONEDINASCITA(final String value) {
        this.codnazionedinascita = value;
    }
    
    public String getCODNAZIONEPARTITAIVA() {
        return this.codnazionepartitaiva;
    }
    
    public void setCODNAZIONEPARTITAIVA(final String value) {
        this.codnazionepartitaiva = value;
    }
    
    public String getCOGNOME() {
        return this.cognome;
    }
    
    public void setCOGNOME(final String value) {
        this.cognome = value;
    }
    
    public String getDATANASCITA() {
        return this.datanascita;
    }
    
    public void setDATANASCITA(final String value) {
        this.datanascita = value;
    }
    
    public String getDATASCADDOCIDENT() {
        return this.datascaddocident;
    }
    
    public void setDATASCADDOCIDENT(final String value) {
        this.datascaddocident = value;
    }
    
    public DATISOGGETTO getDATIRAPPRESENTANTELEGALE() {
        return this.datirappresentantelegale;
    }
    
    public void setDATIRAPPRESENTANTELEGALE(final DATISOGGETTO value) {
        this.datirappresentantelegale = value;
    }
    
    public String getEMAIL() {
        return this.email;
    }
    
    public void setEMAIL(final String value) {
        this.email = value;
    }
    
    public String getENTEDOCIDENT() {
        return this.entedocident;
    }
    
    public void setENTEDOCIDENT(final String value) {
        this.entedocident = value;
    }
    
    public String getFAX() {
        return this.fax;
    }
    
    public void setFAX(final String value) {
        this.fax = value;
    }
    
    public INDIRIZZO getINDIRIZZORECAPITO() {
        return this.indirizzorecapito;
    }
    
    public void setINDIRIZZORECAPITO(final INDIRIZZO value) {
        this.indirizzorecapito = value;
    }
    
    public INDIRIZZO getINDIRIZZORECAPITORAPPRESENTANTELEGALE() {
        return this.indirizzorecapitorappresentantelegale;
    }
    
    public void setINDIRIZZORECAPITORAPPRESENTANTELEGALE(final INDIRIZZO value) {
        this.indirizzorecapitorappresentantelegale = value;
    }
    
    public INDIRIZZO getINDIRIZZORESIDENZA() {
        return this.indirizzoresidenza;
    }
    
    public void setINDIRIZZORESIDENZA(final INDIRIZZO value) {
        this.indirizzoresidenza = value;
    }
    
    public Boolean isISPROSPECT() {
        return this.isprospect;
    }
    
    public void setISPROSPECT(final Boolean value) {
        this.isprospect = value;
    }
    
    public String getLINGUASOGGETTO() {
        return this.linguasoggetto;
    }
    
    public void setLINGUASOGGETTO(final String value) {
        this.linguasoggetto = value;
    }
    
    public String getLUOGONASCITA() {
        return this.luogonascita;
    }
    
    public void setLUOGONASCITA(final String value) {
        this.luogonascita = value;
    }
    
    public String getNAZIONEIVA() {
        return this.nazioneiva;
    }
    
    public void setNAZIONEIVA(final String value) {
        this.nazioneiva = value;
    }
    
    public String getNEWSLETTER() {
        return this.newsletter;
    }
    
    public void setNEWSLETTER(final String value) {
        this.newsletter = value;
    }
    
    public String getNOME() {
        return this.nome;
    }
    
    public void setNOME(final String value) {
        this.nome = value;
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
    
    public String getNUMDOCIDENT() {
        return this.numdocident;
    }
    
    public void setNUMDOCIDENT(final String value) {
        this.numdocident = value;
    }
    
    public String getNUMTEL() {
        return this.numtel;
    }
    
    public void setNUMTEL(final String value) {
        this.numtel = value;
    }
    
    public String getPARTITAIVA() {
        return this.partitaiva;
    }
    
    public void setPARTITAIVA(final String value) {
        this.partitaiva = value;
    }
    
    public String getRAGIONESOCIALE() {
        return this.ragionesociale;
    }
    
    public void setRAGIONESOCIALE(final String value) {
        this.ragionesociale = value;
    }
    
    public String getSESSO() {
        return this.sesso;
    }
    
    public void setSESSO(final String value) {
        this.sesso = value;
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
    
    public String getSOGGETTOCODSUPRUOLO() {
        return this.soggettocodsupruolo;
    }
    
    public void setSOGGETTOCODSUPRUOLO(final String value) {
        this.soggettocodsupruolo = value;
    }
    
    public String getSOGGETTOID() {
        return this.soggettoid;
    }
    
    public void setSOGGETTOID(final String value) {
        this.soggettoid = value;
    }
    
    public String getTIPODOCIDENTCOD() {
        return this.tipodocidentcod;
    }
    
    public void setTIPODOCIDENTCOD(final String value) {
        this.tipodocidentcod = value;
    }
    
    public String getTIPODOCIDENTDESC() {
        return this.tipodocidentdesc;
    }
    
    public void setTIPODOCIDENTDESC(final String value) {
        this.tipodocidentdesc = value;
    }
    
    public String getTIPOSOGGETTOCOD() {
        return this.tiposoggettocod;
    }
    
    public void setTIPOSOGGETTOCOD(final String value) {
        this.tiposoggettocod = value;
    }
    
    public String getTIPOSOGGETTODESC() {
        return this.tiposoggettodesc;
    }
    
    public void setTIPOSOGGETTODESC(final String value) {
        this.tiposoggettodesc = value;
    }
    
    public String getTIPOSOGGETTOID() {
        return this.tiposoggettoid;
    }
    
    public void setTIPOSOGGETTOID(final String value) {
        this.tiposoggettoid = value;
    }
}
