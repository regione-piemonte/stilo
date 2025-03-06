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
@XmlType(name = "", propOrder = { "getListaAlertResult" })
@XmlRootElement(name = "GetListaAlertResponse")
public class GetListaAlertResponse
{
    @XmlElement(name = "GetListaAlertResult", nillable = true)
    protected RESULTMSG getListaAlertResult;
    
    public RESULTMSG getGetListaAlertResult() {
        return this.getListaAlertResult;
    }
    
    public void setGetListaAlertResult(final RESULTMSG value) {
        this.getListaAlertResult = value;
    }
}
