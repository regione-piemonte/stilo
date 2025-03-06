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
@XmlType(name = "", propOrder = { "getStatiFornResult" })
@XmlRootElement(name = "GetStatiFornResponse")
public class GetStatiFornResponse
{
    @XmlElement(name = "GetStatiFornResult", nillable = true)
    protected RESULTMSG getStatiFornResult;
    
    public RESULTMSG getGetStatiFornResult() {
        return this.getStatiFornResult;
    }
    
    public void setGetStatiFornResult(final RESULTMSG value) {
        this.getStatiFornResult = value;
    }
}
