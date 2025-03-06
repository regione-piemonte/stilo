// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONSENSO", propOrder = { "livellocod", "statoocod", "tipocod" })
public class CONSENSO
{
    @XmlElement(name = "LIVELLOCOD", required = true, nillable = true)
    protected String livellocod;
    @XmlElement(name = "STATOOCOD", required = true, nillable = true)
    protected String statoocod;
    @XmlElement(name = "TIPOCOD", required = true, nillable = true)
    protected String tipocod;
    
    public String getLIVELLOCOD() {
        return this.livellocod;
    }
    
    public void setLIVELLOCOD(final String value) {
        this.livellocod = value;
    }
    
    public String getSTATOOCOD() {
        return this.statoocod;
    }
    
    public void setSTATOOCOD(final String value) {
        this.statoocod = value;
    }
    
    public String getTIPOCOD() {
        return this.tipocod;
    }
    
    public void setTIPOCOD(final String value) {
        this.tipocod = value;
    }
}
