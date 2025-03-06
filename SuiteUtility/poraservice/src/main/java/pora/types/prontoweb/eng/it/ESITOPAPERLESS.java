// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESITOPAPERLESS", propOrder = { "email", "livellodettagliobolletta", "paperlessattivo" })
public class ESITOPAPERLESS
{
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "LIVELLODETTAGLIOBOLLETTA", nillable = true)
    protected String livellodettagliobolletta;
    @XmlElement(name = "PAPERLESSATTIVO")
    protected Boolean paperlessattivo;
    
    public String getEMAIL() {
        return this.email;
    }
    
    public void setEMAIL(final String value) {
        this.email = value;
    }
    
    public String getLIVELLODETTAGLIOBOLLETTA() {
        return this.livellodettagliobolletta;
    }
    
    public void setLIVELLODETTAGLIOBOLLETTA(final String value) {
        this.livellodettagliobolletta = value;
    }
    
    public Boolean isPAPERLESSATTIVO() {
        return this.paperlessattivo;
    }
    
    public void setPAPERLESSATTIVO(final Boolean value) {
        this.paperlessattivo = value;
    }
}
