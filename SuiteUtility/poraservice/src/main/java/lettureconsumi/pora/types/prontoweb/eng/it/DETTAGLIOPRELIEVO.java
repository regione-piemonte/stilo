// 
// Decompiled by Procyon v0.5.36
// 

package lettureconsumi.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DETTAGLIOPRELIEVO", propOrder = { "componentestrumento", "fornituracod", "fornituradesc", "periodofin", "periodoini", "podpdr", "statomisura", "tipologialettura", "tiposerviziocod", "tiposerviziodesc" })
public class DETTAGLIOPRELIEVO
{
    @XmlElement(name = "COMPONENTESTRUMENTO", required = true, nillable = true)
    protected String componentestrumento;
    @XmlElement(name = "FORNITURACOD", required = true, nillable = true)
    protected String fornituracod;
    @XmlElement(name = "FORNITURADESC", required = true, nillable = true)
    protected String fornituradesc;
    @XmlElement(name = "PERIODOFIN", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodofin;
    @XmlElement(name = "PERIODOINI", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodoini;
    @XmlElement(name = "PODPDR", required = true, nillable = true)
    protected String podpdr;
    @XmlElement(name = "STATOMISURA", required = true, nillable = true)
    protected String statomisura;
    @XmlElement(name = "TIPOLOGIALETTURA", required = true, nillable = true)
    protected String tipologialettura;
    @XmlElement(name = "TIPOSERVIZIOCOD", required = true, nillable = true)
    protected String tiposerviziocod;
    @XmlElement(name = "TIPOSERVIZIODESC", required = true, nillable = true)
    protected String tiposerviziodesc;
    
    public String getCOMPONENTESTRUMENTO() {
        return this.componentestrumento;
    }
    
    public void setCOMPONENTESTRUMENTO(final String value) {
        this.componentestrumento = value;
    }
    
    public String getFORNITURACOD() {
        return this.fornituracod;
    }
    
    public void setFORNITURACOD(final String value) {
        this.fornituracod = value;
    }
    
    public String getFORNITURADESC() {
        return this.fornituradesc;
    }
    
    public void setFORNITURADESC(final String value) {
        this.fornituradesc = value;
    }
    
    public XMLGregorianCalendar getPERIODOFIN() {
        return this.periodofin;
    }
    
    public void setPERIODOFIN(final XMLGregorianCalendar value) {
        this.periodofin = value;
    }
    
    public XMLGregorianCalendar getPERIODOINI() {
        return this.periodoini;
    }
    
    public void setPERIODOINI(final XMLGregorianCalendar value) {
        this.periodoini = value;
    }
    
    public String getPODPDR() {
        return this.podpdr;
    }
    
    public void setPODPDR(final String value) {
        this.podpdr = value;
    }
    
    public String getSTATOMISURA() {
        return this.statomisura;
    }
    
    public void setSTATOMISURA(final String value) {
        this.statomisura = value;
    }
    
    public String getTIPOLOGIALETTURA() {
        return this.tipologialettura;
    }
    
    public void setTIPOLOGIALETTURA(final String value) {
        this.tipologialettura = value;
    }
    
    public String getTIPOSERVIZIOCOD() {
        return this.tiposerviziocod;
    }
    
    public void setTIPOSERVIZIOCOD(final String value) {
        this.tiposerviziocod = value;
    }
    
    public String getTIPOSERVIZIODESC() {
        return this.tiposerviziodesc;
    }
    
    public void setTIPOSERVIZIODESC(final String value) {
        this.tiposerviziodesc = value;
    }
}
