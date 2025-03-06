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
@XmlType(name = "", propOrder = { "getLetturaPrecedenteExtResult" })
@XmlRootElement(name = "GetLetturaPrecedenteExtResponse")
public class GetLetturaPrecedenteExtResponse
{
    @XmlElement(name = "GetLetturaPrecedenteExtResult", nillable = true)
    protected RESULTMSG getLetturaPrecedenteExtResult;
    
    public RESULTMSG getGetLetturaPrecedenteExtResult() {
        return this.getLetturaPrecedenteExtResult;
    }
    
    public void setGetLetturaPrecedenteExtResult(final RESULTMSG value) {
        this.getLetturaPrecedenteExtResult = value;
    }
}
