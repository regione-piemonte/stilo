// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIPOSERVIZIORESULT", propOrder = { "lineaprodottocod", "tiposerviziocod", "tiposerviziodes" })
public class TIPOSERVIZIORESULT
{
    @XmlElement(name = "LINEAPRODOTTOCOD", nillable = true)
    protected String lineaprodottocod;
    @XmlElement(name = "TIPOSERVIZIOCOD", nillable = true)
    protected String tiposerviziocod;
    @XmlElement(name = "TIPOSERVIZIODES", nillable = true)
    protected String tiposerviziodes;
    
    public String getLINEAPRODOTTOCOD() {
        return this.lineaprodottocod;
    }
    
    public void setLINEAPRODOTTOCOD(final String value) {
        this.lineaprodottocod = value;
    }
    
    public String getTIPOSERVIZIOCOD() {
        return this.tiposerviziocod;
    }
    
    public void setTIPOSERVIZIOCOD(final String value) {
        this.tiposerviziocod = value;
    }
    
    public String getTIPOSERVIZIODES() {
        return this.tiposerviziodes;
    }
    
    public void setTIPOSERVIZIODES(final String value) {
        this.tiposerviziodes = value;
    }
}
