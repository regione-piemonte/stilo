// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOTTOTIPIRESULT", propOrder = { "lineaprodottocod", "sottotipocod", "sottotipodes", "tipoprocessocod", "tiporichiestacod" })
public class SOTTOTIPIRESULT
{
    @XmlElement(name = "LINEAPRODOTTOCOD", nillable = true)
    protected String lineaprodottocod;
    @XmlElement(name = "SOTTOTIPOCOD", nillable = true)
    protected String sottotipocod;
    @XmlElement(name = "SOTTOTIPODES", nillable = true)
    protected String sottotipodes;
    @XmlElement(name = "TIPOPROCESSOCOD", nillable = true)
    protected String tipoprocessocod;
    @XmlElement(name = "TIPORICHIESTACOD", nillable = true)
    protected String tiporichiestacod;
    
    public String getLINEAPRODOTTOCOD() {
        return this.lineaprodottocod;
    }
    
    public void setLINEAPRODOTTOCOD(final String value) {
        this.lineaprodottocod = value;
    }
    
    public String getSOTTOTIPOCOD() {
        return this.sottotipocod;
    }
    
    public void setSOTTOTIPOCOD(final String value) {
        this.sottotipocod = value;
    }
    
    public String getSOTTOTIPODES() {
        return this.sottotipodes;
    }
    
    public void setSOTTOTIPODES(final String value) {
        this.sottotipodes = value;
    }
    
    public String getTIPOPROCESSOCOD() {
        return this.tipoprocessocod;
    }
    
    public void setTIPOPROCESSOCOD(final String value) {
        this.tipoprocessocod = value;
    }
    
    public String getTIPORICHIESTACOD() {
        return this.tiporichiestacod;
    }
    
    public void setTIPORICHIESTACOD(final String value) {
        this.tiporichiestacod = value;
    }
}
