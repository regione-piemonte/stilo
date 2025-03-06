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
@XmlType(name = "", propOrder = { "getPDFResult" })
@XmlRootElement(name = "GetPDFResponse")
public class GetPDFResponse
{
    @XmlElement(name = "GetPDFResult", nillable = true)
    protected RESULTMSG getPDFResult;
    
    public RESULTMSG getGetPDFResult() {
        return this.getPDFResult;
    }
    
    public void setGetPDFResult(final RESULTMSG value) {
        this.getPDFResult = value;
    }
}
