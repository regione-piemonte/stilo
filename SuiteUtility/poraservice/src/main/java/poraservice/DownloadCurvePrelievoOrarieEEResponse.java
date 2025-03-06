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
@XmlType(name = "", propOrder = { "downloadCurvePrelievoOrarieEEResult" })
@XmlRootElement(name = "DownloadCurvePrelievoOrarieEEResponse")
public class DownloadCurvePrelievoOrarieEEResponse
{
    @XmlElement(name = "DownloadCurvePrelievoOrarieEEResult", nillable = true)
    protected RESULTMSG downloadCurvePrelievoOrarieEEResult;
    
    public RESULTMSG getDownloadCurvePrelievoOrarieEEResult() {
        return this.downloadCurvePrelievoOrarieEEResult;
    }
    
    public void setDownloadCurvePrelievoOrarieEEResult(final RESULTMSG value) {
        this.downloadCurvePrelievoOrarieEEResult = value;
    }
}
