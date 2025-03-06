// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FORNITURAMIN", propOrder = { "fornituracod", "tiposerviziocod", "pdrpod", "lineaprodottocod", "prodottocod" })
@XmlSeeAlso({ FORNITURA.class })
public class FORNITURAMIN
{
    @XmlElement(name = "FORNITURACOD")
    protected Long fornituracod;
    @XmlElement(name = "TIPOSERVIZIOCOD", nillable = true)
    protected String tiposerviziocod;
    @XmlElement(name = "PDRPOD", nillable = true)
    protected String pdrpod;
    @XmlElement(name = "LINEAPRODOTTOCOD", nillable = true)
    protected String lineaprodottocod;
    @XmlElement(name = "PRODOTTOCOD", nillable = true)
    protected String prodottocod;
    
    public Long getFORNITURACOD() {
        return this.fornituracod;
    }
    
    public void setFORNITURACOD(final Long value) {
        this.fornituracod = value;
    }
    
    public String getTIPOSERVIZIOCOD() {
        return this.tiposerviziocod;
    }
    
    public void setTIPOSERVIZIOCOD(final String value) {
        this.tiposerviziocod = value;
    }
    
    public String getPDRPOD() {
        return this.pdrpod;
    }
    
    public void setPDRPOD(final String value) {
        this.pdrpod = value;
    }
    
    public String getLINEAPRODOTTOCOD() {
        return this.lineaprodottocod;
    }
    
    public void setLINEAPRODOTTOCOD(final String value) {
        this.lineaprodottocod = value;
    }
    
    public String getPRODOTTOCOD() {
        return this.prodottocod;
    }
    
    public void setPRODOTTOCOD(final String value) {
        this.prodottocod = value;
    }
}
