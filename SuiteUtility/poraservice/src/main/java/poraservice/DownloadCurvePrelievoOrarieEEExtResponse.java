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
@XmlType(name = "", propOrder = { "downloadCurvePrelievoOrarieEEExtResult" })
@XmlRootElement(name = "DownloadCurvePrelievoOrarieEEExtResponse")
public class DownloadCurvePrelievoOrarieEEExtResponse
{
    @XmlElement(name = "DownloadCurvePrelievoOrarieEEExtResult", nillable = true)
    protected RESULTMSG downloadCurvePrelievoOrarieEEExtResult;
    
    public RESULTMSG getDownloadCurvePrelievoOrarieEEExtResult() {
        return this.downloadCurvePrelievoOrarieEEExtResult;
    }
    
    public void setDownloadCurvePrelievoOrarieEEExtResult(final RESULTMSG value) {
        this.downloadCurvePrelievoOrarieEEExtResult = value;
    }
}
