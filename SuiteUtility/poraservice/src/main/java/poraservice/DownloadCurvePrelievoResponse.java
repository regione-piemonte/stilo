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
@XmlType(name = "", propOrder = { "downloadCurvePrelievoResult" })
@XmlRootElement(name = "DownloadCurvePrelievoResponse")
public class DownloadCurvePrelievoResponse
{
    @XmlElement(name = "DownloadCurvePrelievoResult", nillable = true)
    protected RESULTMSG downloadCurvePrelievoResult;
    
    public RESULTMSG getDownloadCurvePrelievoResult() {
        return this.downloadCurvePrelievoResult;
    }
    
    public void setDownloadCurvePrelievoResult(final RESULTMSG value) {
        this.downloadCurvePrelievoResult = value;
    }
}
