// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import pora.types.prontoweb.eng.it.RESULTMSG;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "setConsensiSoggettoResult" })
@XmlRootElement(name = "SetConsensiSoggettoResponse")
public class SetConsensiSoggettoResponse
{
    @XmlElement(name = "SetConsensiSoggettoResult", nillable = true)
    protected RESULTMSG setConsensiSoggettoResult;
    
    public RESULTMSG getSetConsensiSoggettoResult() {
        return this.setConsensiSoggettoResult;
    }
    
    public void setSetConsensiSoggettoResult(final RESULTMSG value) {
        this.setConsensiSoggettoResult = value;
    }
}
