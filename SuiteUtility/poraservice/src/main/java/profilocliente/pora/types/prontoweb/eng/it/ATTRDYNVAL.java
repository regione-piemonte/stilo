// 
// Decompiled by Procyon v0.5.36
// 

package profilocliente.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ATTRDYNVAL", propOrder = { "valdesc", "valid" })
public class ATTRDYNVAL
{
    @XmlElement(name = "VAL_DESC", nillable = true)
    protected String valdesc;
    @XmlElement(name = "VAL_ID", nillable = true)
    protected String valid;
    
    public String getVALDESC() {
        return this.valdesc;
    }
    
    public void setVALDESC(final String value) {
        this.valdesc = value;
    }
    
    public String getVALID() {
        return this.valid;
    }
    
    public void setVALID(final String value) {
        this.valid = value;
    }
}
