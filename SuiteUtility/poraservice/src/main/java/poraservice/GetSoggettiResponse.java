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
@XmlType(name = "", propOrder = { "getSoggettiResult" })
@XmlRootElement(name = "GetSoggettiResponse")
public class GetSoggettiResponse
{
    @XmlElement(name = "GetSoggettiResult", nillable = true)
    protected RESULTMSG getSoggettiResult;
    
    public RESULTMSG getGetSoggettiResult() {
        return this.getSoggettiResult;
    }
    
    public void setGetSoggettiResult(final RESULTMSG value) {
        this.getSoggettiResult = value;
    }
}
