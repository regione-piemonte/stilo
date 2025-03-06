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
@XmlType(name = "", propOrder = { "getLetturaPrecedenteResult" })
@XmlRootElement(name = "GetLetturaPrecedenteResponse")
public class GetLetturaPrecedenteResponse
{
    @XmlElement(name = "GetLetturaPrecedenteResult", nillable = true)
    protected RESULTMSG getLetturaPrecedenteResult;
    
    public RESULTMSG getGetLetturaPrecedenteResult() {
        return this.getLetturaPrecedenteResult;
    }
    
    public void setGetLetturaPrecedenteResult(final RESULTMSG value) {
        this.getLetturaPrecedenteResult = value;
    }
}
