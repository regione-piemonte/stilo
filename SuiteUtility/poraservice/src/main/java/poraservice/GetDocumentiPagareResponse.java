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
@XmlType(name = "", propOrder = { "getDocumentiPagareResult" })
@XmlRootElement(name = "GetDocumentiPagareResponse")
public class GetDocumentiPagareResponse
{
    @XmlElement(name = "GetDocumentiPagareResult", nillable = true)
    protected RESULTMSG getDocumentiPagareResult;
    
    public RESULTMSG getGetDocumentiPagareResult() {
        return this.getDocumentiPagareResult;
    }
    
    public void setGetDocumentiPagareResult(final RESULTMSG value) {
        this.getDocumentiPagareResult = value;
    }
}
