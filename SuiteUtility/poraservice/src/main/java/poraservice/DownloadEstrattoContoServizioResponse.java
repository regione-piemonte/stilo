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
@XmlType(name = "", propOrder = { "downloadEstrattoContoServizioResult" })
@XmlRootElement(name = "DownloadEstrattoContoServizioResponse")
public class DownloadEstrattoContoServizioResponse
{
    @XmlElement(name = "DownloadEstrattoContoServizioResult", nillable = true)
    protected RESULTMSG downloadEstrattoContoServizioResult;
    
    public RESULTMSG getDownloadEstrattoContoServizioResult() {
        return this.downloadEstrattoContoServizioResult;
    }
    
    public void setDownloadEstrattoContoServizioResult(final RESULTMSG value) {
        this.downloadEstrattoContoServizioResult = value;
    }
}
