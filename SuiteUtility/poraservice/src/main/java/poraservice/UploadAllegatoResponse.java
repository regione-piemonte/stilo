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
@XmlType(name = "", propOrder = { "uploadAllegatoResult" })
@XmlRootElement(name = "UploadAllegatoResponse")
public class UploadAllegatoResponse
{
    @XmlElement(name = "UploadAllegatoResult", nillable = true)
    protected RESULTMSG uploadAllegatoResult;
    
    public RESULTMSG getUploadAllegatoResult() {
        return this.uploadAllegatoResult;
    }
    
    public void setUploadAllegatoResult(final RESULTMSG value) {
        this.uploadAllegatoResult = value;
    }
}
