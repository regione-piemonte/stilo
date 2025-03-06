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
@XmlType(name = "", propOrder = { "downloadCurvePrelievoExtResult" })
@XmlRootElement(name = "DownloadCurvePrelievoExtResponse")
public class DownloadCurvePrelievoExtResponse
{
    @XmlElement(name = "DownloadCurvePrelievoExtResult", nillable = true)
    protected RESULTMSG downloadCurvePrelievoExtResult;
    
    public RESULTMSG getDownloadCurvePrelievoExtResult() {
        return this.downloadCurvePrelievoExtResult;
    }
    
    public void setDownloadCurvePrelievoExtResult(final RESULTMSG value) {
        this.downloadCurvePrelievoExtResult = value;
    }
}
