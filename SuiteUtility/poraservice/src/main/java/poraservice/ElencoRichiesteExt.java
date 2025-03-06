// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import filtri.types.pora.types.prontoweb.eng.it.FiltroRichiesta;
import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggettoCod", "filter", "filtroRichiesta" })
@XmlRootElement(name = "ElencoRichiesteExt")
public class ElencoRichiesteExt
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String soggettoCod;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filter;
    @XmlElement(nillable = true)
    protected FiltroRichiesta filtroRichiesta;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getSoggettoCod() {
        return this.soggettoCod;
    }
    
    public void setSoggettoCod(final String value) {
        this.soggettoCod = value;
    }
    
    public PWEBTABLEFILTER getFilter() {
        return this.filter;
    }
    
    public void setFilter(final PWEBTABLEFILTER value) {
        this.filter = value;
    }
    
    public FiltroRichiesta getFiltroRichiesta() {
        return this.filtroRichiesta;
    }
    
    public void setFiltroRichiesta(final FiltroRichiesta value) {
        this.filtroRichiesta = value;
    }
}
