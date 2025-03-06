// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import allegati.pora.types.prontoweb.eng.it.ALLEGATORICHIESTA;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "filtro" })
@XmlRootElement(name = "UploadAllegato")
public class UploadAllegato
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected ALLEGATORICHIESTA filtro;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public ALLEGATORICHIESTA getFiltro() {
        return this.filtro;
    }
    
    public void setFiltro(final ALLEGATORICHIESTA value) {
        this.filtro = value;
    }
}
