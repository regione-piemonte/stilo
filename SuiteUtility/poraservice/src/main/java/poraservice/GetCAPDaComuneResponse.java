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
@XmlType(name = "", propOrder = { "getCAPDaComuneResult" })
@XmlRootElement(name = "GetCAPDaComuneResponse")
public class GetCAPDaComuneResponse
{
    @XmlElement(name = "GetCAPDaComuneResult", nillable = true)
    protected RESULTMSG getCAPDaComuneResult;
    
    public RESULTMSG getGetCAPDaComuneResult() {
        return this.getCAPDaComuneResult;
    }
    
    public void setGetCAPDaComuneResult(final RESULTMSG value) {
        this.getCAPDaComuneResult = value;
    }
}
