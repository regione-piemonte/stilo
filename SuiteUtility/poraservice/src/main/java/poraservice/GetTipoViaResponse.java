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
@XmlType(name = "", propOrder = { "getTipoViaResult" })
@XmlRootElement(name = "GetTipoViaResponse")
public class GetTipoViaResponse
{
    @XmlElement(name = "GetTipoViaResult", nillable = true)
    protected RESULTMSG getTipoViaResult;
    
    public RESULTMSG getGetTipoViaResult() {
        return this.getTipoViaResult;
    }
    
    public void setGetTipoViaResult(final RESULTMSG value) {
        this.getTipoViaResult = value;
    }
}
