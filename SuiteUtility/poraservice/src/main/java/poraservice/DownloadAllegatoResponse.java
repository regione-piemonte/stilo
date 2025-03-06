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
@XmlType(name = "", propOrder = { "downloadAllegatoResult" })
@XmlRootElement(name = "DownloadAllegatoResponse")
public class DownloadAllegatoResponse
{
    @XmlElement(name = "DownloadAllegatoResult", nillable = true)
    protected RESULTMSG downloadAllegatoResult;
    
    public RESULTMSG getDownloadAllegatoResult() {
        return this.downloadAllegatoResult;
    }
    
    public void setDownloadAllegatoResult(final RESULTMSG value) {
        this.downloadAllegatoResult = value;
    }
}
