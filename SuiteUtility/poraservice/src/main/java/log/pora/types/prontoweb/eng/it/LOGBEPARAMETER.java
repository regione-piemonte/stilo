// 
// Decompiled by Procyon v0.5.36
// 

package log.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LOGBEPARAMETER", propOrder = { "nomeparam", "valparam" })
public class LOGBEPARAMETER
{
    @XmlElement(name = "NOMEPARAM", nillable = true)
    protected String nomeparam;
    @XmlElement(name = "VALPARAM", nillable = true)
    protected String valparam;
    
    public String getNOMEPARAM() {
        return this.nomeparam;
    }
    
    public void setNOMEPARAM(final String value) {
        this.nomeparam = value;
    }
    
    public String getVALPARAM() {
        return this.valparam;
    }
    
    public void setVALPARAM(final String value) {
        this.valparam = value;
    }
}
