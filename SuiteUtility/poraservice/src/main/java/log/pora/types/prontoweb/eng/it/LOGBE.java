// 
// Decompiled by Procyon v0.5.36
// 

package log.pora.types.prontoweb.eng.it;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.datacontract.schemas._2004._07.pweb_be_pora.InfoGUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LOGBE", propOrder = { "account", "esito", "fornitura", "guid", "llogbeparams", "llogbestorproc", "nomemetodo", "portale", "resultmsg", "sessionid", "soggetto", "tempoesecuzione", "timestamp", "tiporic" })
public class LOGBE extends InfoGUID
{
    @XmlElement(name = "ACCOUNT", nillable = true)
    protected String account;
    @XmlElement(name = "ESITO", nillable = true)
    protected String esito;
    @XmlElement(name = "FORNITURA", nillable = true)
    protected String fornitura;
    @XmlElement(name = "GUID", nillable = true)
    protected String guid;
    @XmlElement(name = "LLOGBEPARAMS", nillable = true)
    protected ArrayOfLOGBEPARAMETER llogbeparams;
    @XmlElement(name = "LLOGBESTORPROC", nillable = true)
    protected ArrayOfLOGBESTOREDPROCEDURE llogbestorproc;
    @XmlElement(name = "NOMEMETODO", nillable = true)
    protected String nomemetodo;
    @XmlElement(name = "PORTALE")
    @XmlSchemaType(name = "string")
    protected PROVENIENZAPORTALE portale;
    @XmlElement(name = "RESULTMSG", nillable = true)
    protected String resultmsg;
    @XmlElement(name = "SESSIONID", nillable = true)
    protected String sessionid;
    @XmlElement(name = "SOGGETTO", nillable = true)
    protected String soggetto;
    @XmlElement(name = "TEMPOESECUZIONE")
    protected Integer tempoesecuzione;
    @XmlElement(name = "TIMESTAMP")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "TIPORIC", nillable = true)
    protected String tiporic;
    
    public String getACCOUNT() {
        return this.account;
    }
    
    public void setACCOUNT(final String value) {
        this.account = value;
    }
    
    public String getESITO() {
        return this.esito;
    }
    
    public void setESITO(final String value) {
        this.esito = value;
    }
    
    public String getFORNITURA() {
        return this.fornitura;
    }
    
    public void setFORNITURA(final String value) {
        this.fornitura = value;
    }
    
    public String getGUID() {
        return this.guid;
    }
    
    public void setGUID(final String value) {
        this.guid = value;
    }
    
    public ArrayOfLOGBEPARAMETER getLLOGBEPARAMS() {
        return this.llogbeparams;
    }
    
    public void setLLOGBEPARAMS(final ArrayOfLOGBEPARAMETER value) {
        this.llogbeparams = value;
    }
    
    public ArrayOfLOGBESTOREDPROCEDURE getLLOGBESTORPROC() {
        return this.llogbestorproc;
    }
    
    public void setLLOGBESTORPROC(final ArrayOfLOGBESTOREDPROCEDURE value) {
        this.llogbestorproc = value;
    }
    
    public String getNOMEMETODO() {
        return this.nomemetodo;
    }
    
    public void setNOMEMETODO(final String value) {
        this.nomemetodo = value;
    }
    
    public PROVENIENZAPORTALE getPORTALE() {
        return this.portale;
    }
    
    public void setPORTALE(final PROVENIENZAPORTALE value) {
        this.portale = value;
    }
    
    public String getRESULTMSG() {
        return this.resultmsg;
    }
    
    public void setRESULTMSG(final String value) {
        this.resultmsg = value;
    }
    
    public String getSESSIONID() {
        return this.sessionid;
    }
    
    public void setSESSIONID(final String value) {
        this.sessionid = value;
    }
    
    public String getSOGGETTO() {
        return this.soggetto;
    }
    
    public void setSOGGETTO(final String value) {
        this.soggetto = value;
    }
    
    public Integer getTEMPOESECUZIONE() {
        return this.tempoesecuzione;
    }
    
    public void setTEMPOESECUZIONE(final Integer value) {
        this.tempoesecuzione = value;
    }
    
    public XMLGregorianCalendar getTIMESTAMP() {
        return this.timestamp;
    }
    
    public void setTIMESTAMP(final XMLGregorianCalendar value) {
        this.timestamp = value;
    }
    
    public String getTIPORIC() {
        return this.tiporic;
    }
    
    public void setTIPORIC(final String value) {
        this.tiporic = value;
    }
}
