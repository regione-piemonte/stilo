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
@XmlType(name = "", propOrder = { "cambioPasswordResult" })
@XmlRootElement(name = "CambioPasswordResponse")
public class CambioPasswordResponse
{
    @XmlElement(name = "CambioPasswordResult", nillable = true)
    protected RESULTMSG cambioPasswordResult;
    
    public RESULTMSG getCambioPasswordResult() {
        return this.cambioPasswordResult;
    }
    
    public void setCambioPasswordResult(final RESULTMSG value) {
        this.cambioPasswordResult = value;
    }
}
