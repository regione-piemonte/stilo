// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RUOLO", propOrder = { "ruolocod", "ruolodesc" })
public class RUOLO
{
    @XmlElement(name = "RUOLOCOD", nillable = true)
    protected String ruolocod;
    @XmlElement(name = "RUOLODESC", nillable = true)
    protected String ruolodesc;
    
    public String getRUOLOCOD() {
        return this.ruolocod;
    }
    
    public void setRUOLOCOD(final String value) {
        this.ruolocod = value;
    }
    
    public String getRUOLODESC() {
        return this.ruolodesc;
    }
    
    public void setRUOLODESC(final String value) {
        this.ruolodesc = value;
    }
}
