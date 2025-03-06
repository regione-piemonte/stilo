// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "filter", "filtroAttrDyn" })
@XmlRootElement(name = "GetUtentiNewsletterExt")
public class GetUtentiNewsletterExt
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filter;
    @XmlElement(nillable = true)
    protected String filtroAttrDyn;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public PWEBTABLEFILTER getFilter() {
        return this.filter;
    }
    
    public void setFilter(final PWEBTABLEFILTER value) {
        this.filter = value;
    }
    
    public String getFiltroAttrDyn() {
        return this.filtroAttrDyn;
    }
    
    public void setFiltroAttrDyn(final String value) {
        this.filtroAttrDyn = value;
    }
}
