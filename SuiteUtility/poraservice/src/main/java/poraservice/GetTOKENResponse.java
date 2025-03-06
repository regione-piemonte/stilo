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
@XmlType(name = "", propOrder = { "getTOKENResult" })
@XmlRootElement(name = "GetTOKENResponse")
public class GetTOKENResponse
{
    @XmlElement(name = "GetTOKENResult", nillable = true)
    protected RESULTMSG getTOKENResult;
    
    public RESULTMSG getGetTOKENResult() {
        return this.getTOKENResult;
    }
    
    public void setGetTOKENResult(final RESULTMSG value) {
        this.getTOKENResult = value;
    }
}
