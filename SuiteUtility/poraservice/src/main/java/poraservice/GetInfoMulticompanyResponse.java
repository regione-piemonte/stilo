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
@XmlType(name = "", propOrder = { "getInfoMulticompanyResult" })
@XmlRootElement(name = "GetInfoMulticompanyResponse")
public class GetInfoMulticompanyResponse
{
    @XmlElement(name = "GetInfoMulticompanyResult", nillable = true)
    protected RESULTMSG getInfoMulticompanyResult;
    
    public RESULTMSG getGetInfoMulticompanyResult() {
        return this.getInfoMulticompanyResult;
    }
    
    public void setGetInfoMulticompanyResult(final RESULTMSG value) {
        this.getInfoMulticompanyResult = value;
    }
}
