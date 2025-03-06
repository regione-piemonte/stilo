// 
// Decompiled by Procyon v0.5.36
// 

package prospect.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOGGETTOPROSPECTBASE", propOrder = { "codtiposoggetto", "email" })
public class SOGGETTOPROSPECTBASE
{
    @XmlElement(name = "CODTIPOSOGGETTO", required = true, nillable = true)
    protected String codtiposoggetto;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    
    public String getCODTIPOSOGGETTO() {
        return this.codtiposoggetto;
    }
    
    public void setCODTIPOSOGGETTO(final String value) {
        this.codtiposoggetto = value;
    }
    
    public String getEMAIL() {
        return this.email;
    }
    
    public void setEMAIL(final String value) {
        this.email = value;
    }
}
