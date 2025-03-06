// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggettoCOD", "ruolosoggCOD" })
@XmlRootElement(name = "GetStatiForn")
public class GetStatiForn
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String soggettoCOD;
    @XmlElement(nillable = true)
    protected String ruolosoggCOD;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getSoggettoCOD() {
        return this.soggettoCOD;
    }
    
    public void setSoggettoCOD(final String value) {
        this.soggettoCOD = value;
    }
    
    public String getRuolosoggCOD() {
        return this.ruolosoggCOD;
    }
    
    public void setRuolosoggCOD(final String value) {
        this.ruolosoggCOD = value;
    }
}
