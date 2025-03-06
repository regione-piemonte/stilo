// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLEFORNITUREEXT", propOrder = { "elencofornitureext" })
public class TABLEFORNITUREEXT extends PWEBTABLE
{
    @XmlElement(name = "ELENCOFORNITUREEXT", required = true, nillable = true)
    protected ArrayOfFORNITURAEXT elencofornitureext;
    
    public ArrayOfFORNITURAEXT getELENCOFORNITUREEXT() {
        return this.elencofornitureext;
    }
    
    public void setELENCOFORNITUREEXT(final ArrayOfFORNITURAEXT value) {
        this.elencofornitureext = value;
    }
}
