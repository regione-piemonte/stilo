// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FORNITURACP", propOrder = { "fornituracod" })
public class FORNITURACP
{
    @XmlElement(name = "FORNITURACOD")
    protected Long fornituracod;
    
    public Long getFORNITURACOD() {
        return this.fornituracod;
    }
    
    public void setFORNITURACOD(final Long value) {
        this.fornituracod = value;
    }
}
