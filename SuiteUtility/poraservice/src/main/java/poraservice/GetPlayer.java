// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "funzioneCod", "lineaProdottoCod", "tipoServizioCod" })
@XmlRootElement(name = "GetPlayer")
public class GetPlayer
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String funzioneCod;
    @XmlElement(nillable = true)
    protected String lineaProdottoCod;
    @XmlElement(nillable = true)
    protected String tipoServizioCod;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getFunzioneCod() {
        return this.funzioneCod;
    }
    
    public void setFunzioneCod(final String value) {
        this.funzioneCod = value;
    }
    
    public String getLineaProdottoCod() {
        return this.lineaProdottoCod;
    }
    
    public void setLineaProdottoCod(final String value) {
        this.lineaProdottoCod = value;
    }
    
    public String getTipoServizioCod() {
        return this.tipoServizioCod;
    }
    
    public void setTipoServizioCod(final String value) {
        this.tipoServizioCod = value;
    }
}
