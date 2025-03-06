// 
// Decompiled by Procyon v0.5.36
// 

package autolettura.pora.types.prontoweb.eng.it;

import pora.types.prontoweb.eng.it.FORNITURA;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LETTURAEXT", propOrder = { "corrvolumetrico", "fornitura" })
public class LETTURAEXT extends LETTURA
{
    @XmlElement(name = "CORRVOLUMETRICO", nillable = true)
    protected String corrvolumetrico;
    @XmlElement(name = "FORNITURA", required = true, nillable = true)
    protected FORNITURA fornitura;
    
    public String getCORRVOLUMETRICO() {
        return this.corrvolumetrico;
    }
    
    public void setCORRVOLUMETRICO(final String value) {
        this.corrvolumetrico = value;
    }
    
    public FORNITURA getFORNITURA() {
        return this.fornitura;
    }
    
    public void setFORNITURA(final FORNITURA value) {
        this.fornitura = value;
    }
}
