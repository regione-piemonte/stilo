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
@XmlType(name = "", propOrder = { "getListaAttributiDynResult" })
@XmlRootElement(name = "GetListaAttributiDynResponse")
public class GetListaAttributiDynResponse
{
    @XmlElement(name = "GetListaAttributiDynResult", nillable = true)
    protected RESULTMSG getListaAttributiDynResult;
    
    public RESULTMSG getGetListaAttributiDynResult() {
        return this.getListaAttributiDynResult;
    }
    
    public void setGetListaAttributiDynResult(final RESULTMSG value) {
        this.getListaAttributiDynResult = value;
    }
}
