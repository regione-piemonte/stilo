// 
// Decompiled by Procyon v0.5.36
// 

package log.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LOGBESTOREDPROCEDURE", propOrder = { "esito", "storedparams", "storedprocedure", "timestamp", "tmpoesecuzione" })
public class LOGBESTOREDPROCEDURE
{
    @XmlElement(name = "ESITO", nillable = true)
    protected String esito;
    @XmlElement(name = "STOREDPARAMS", nillable = true)
    protected String storedparams;
    @XmlElement(name = "STOREDPROCEDURE", nillable = true)
    protected String storedprocedure;
    @XmlElement(name = "TIMESTAMP")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "TMPOESECUZIONE")
    protected Integer tmpoesecuzione;
    
    public String getESITO() {
        return this.esito;
    }
    
    public void setESITO(final String value) {
        this.esito = value;
    }
    
    public String getSTOREDPARAMS() {
        return this.storedparams;
    }
    
    public void setSTOREDPARAMS(final String value) {
        this.storedparams = value;
    }
    
    public String getSTOREDPROCEDURE() {
        return this.storedprocedure;
    }
    
    public void setSTOREDPROCEDURE(final String value) {
        this.storedprocedure = value;
    }
    
    public XMLGregorianCalendar getTIMESTAMP() {
        return this.timestamp;
    }
    
    public void setTIMESTAMP(final XMLGregorianCalendar value) {
        this.timestamp = value;
    }
    
    public Integer getTMPOESECUZIONE() {
        return this.tmpoesecuzione;
    }
    
    public void setTMPOESECUZIONE(final Integer value) {
        this.tmpoesecuzione = value;
    }
}
