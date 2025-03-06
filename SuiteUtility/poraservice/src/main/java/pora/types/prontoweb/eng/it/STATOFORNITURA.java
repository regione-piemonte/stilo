// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STATOFORNITURA", propOrder = { "statofornituracod", "statofornituradesc" })
public class STATOFORNITURA
{
    @XmlElement(name = "STATOFORNITURACOD", required = true, nillable = true)
    protected String statofornituracod;
    @XmlElement(name = "STATOFORNITURADESC", required = true, nillable = true)
    protected String statofornituradesc;
    
    public String getSTATOFORNITURACOD() {
        return this.statofornituracod;
    }
    
    public void setSTATOFORNITURACOD(final String value) {
        this.statofornituracod = value;
    }
    
    public String getSTATOFORNITURADESC() {
        return this.statofornituradesc;
    }
    
    public void setSTATOFORNITURADESC(final String value) {
        this.statofornituradesc = value;
    }
}
