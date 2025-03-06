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
@XmlType(name = "", propOrder = { "getEstrattoContoServizioResult" })
@XmlRootElement(name = "GetEstrattoContoServizioResponse")
public class GetEstrattoContoServizioResponse
{
    @XmlElement(name = "GetEstrattoContoServizioResult", nillable = true)
    protected RESULTMSG getEstrattoContoServizioResult;
    
    public RESULTMSG getGetEstrattoContoServizioResult() {
        return this.getEstrattoContoServizioResult;
    }
    
    public void setGetEstrattoContoServizioResult(final RESULTMSG value) {
        this.getEstrattoContoServizioResult = value;
    }
}
