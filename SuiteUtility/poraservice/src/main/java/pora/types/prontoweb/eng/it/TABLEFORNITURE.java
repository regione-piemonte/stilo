// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLEFORNITURE", propOrder = { "elencoforniture" })
public class TABLEFORNITURE extends PWEBTABLE
{
    @XmlElement(name = "ELENCOFORNITURE", required = true, nillable = true)
    protected ArrayOfFORNITURA elencoforniture;
    
    public ArrayOfFORNITURA getELENCOFORNITURE() {
        return this.elencoforniture;
    }
    
    public void setELENCOFORNITURE(final ArrayOfFORNITURA value) {
        this.elencoforniture = value;
    }
}
