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
@XmlType(name = "", propOrder = { "getPDFNETAResult" })
@XmlRootElement(name = "GetPDF_NETAResponse")
public class GetPDFNETAResponse
{
    @XmlElement(name = "GetPDF_NETAResult", nillable = true)
    protected RESULTMSG getPDFNETAResult;
    
    public RESULTMSG getGetPDFNETAResult() {
        return this.getPDFNETAResult;
    }
    
    public void setGetPDFNETAResult(final RESULTMSG value) {
        this.getPDFNETAResult = value;
    }
}
