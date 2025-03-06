// 
// Decompiled by Procyon v0.5.36
// 

package dettaglioservizio.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ELEMENTIDIFATTURAZIONE", propOrder = { "tipovocecod", "tipovocedesc", "vocecod", "vocedesc" })
public class ELEMENTIDIFATTURAZIONE
{
    @XmlElement(name = "TIPOVOCECOD", required = true, nillable = true)
    protected String tipovocecod;
    @XmlElement(name = "TIPOVOCEDESC", required = true, nillable = true)
    protected String tipovocedesc;
    @XmlElement(name = "VOCECOD", required = true, nillable = true)
    protected String vocecod;
    @XmlElement(name = "VOCEDESC", required = true, nillable = true)
    protected String vocedesc;
    
    public String getTIPOVOCECOD() {
        return this.tipovocecod;
    }
    
    public void setTIPOVOCECOD(final String value) {
        this.tipovocecod = value;
    }
    
    public String getTIPOVOCEDESC() {
        return this.tipovocedesc;
    }
    
    public void setTIPOVOCEDESC(final String value) {
        this.tipovocedesc = value;
    }
    
    public String getVOCECOD() {
        return this.vocecod;
    }
    
    public void setVOCECOD(final String value) {
        this.vocecod = value;
    }
    
    public String getVOCEDESC() {
        return this.vocedesc;
    }
    
    public void setVOCEDESC(final String value) {
        this.vocedesc = value;
    }
}
