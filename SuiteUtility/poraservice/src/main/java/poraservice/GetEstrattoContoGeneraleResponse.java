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
@XmlType(name = "", propOrder = { "getEstrattoContoGeneraleResult" })
@XmlRootElement(name = "GetEstrattoContoGeneraleResponse")
public class GetEstrattoContoGeneraleResponse
{
    @XmlElement(name = "GetEstrattoContoGeneraleResult", nillable = true)
    protected RESULTMSG getEstrattoContoGeneraleResult;
    
    public RESULTMSG getGetEstrattoContoGeneraleResult() {
        return this.getEstrattoContoGeneraleResult;
    }
    
    public void setGetEstrattoContoGeneraleResult(final RESULTMSG value) {
        this.getEstrattoContoGeneraleResult = value;
    }
}
