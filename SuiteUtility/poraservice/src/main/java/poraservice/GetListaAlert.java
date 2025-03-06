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
@XmlType(name = "", propOrder = { "multicompany", "filterTbldateProssBoll", "filterTblConsStim", "filterTbldocPagare", "soggettoCod", "fornituraCod" })
@XmlRootElement(name = "GetListaAlert")
public class GetListaAlert
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filterTbldateProssBoll;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filterTblConsStim;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filterTbldocPagare;
    @XmlElement(nillable = true)
    protected String soggettoCod;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public PWEBTABLEFILTER getFilterTbldateProssBoll() {
        return this.filterTbldateProssBoll;
    }
    
    public void setFilterTbldateProssBoll(final PWEBTABLEFILTER value) {
        this.filterTbldateProssBoll = value;
    }
    
    public PWEBTABLEFILTER getFilterTblConsStim() {
        return this.filterTblConsStim;
    }
    
    public void setFilterTblConsStim(final PWEBTABLEFILTER value) {
        this.filterTblConsStim = value;
    }
    
    public PWEBTABLEFILTER getFilterTbldocPagare() {
        return this.filterTbldocPagare;
    }
    
    public void setFilterTbldocPagare(final PWEBTABLEFILTER value) {
        this.filterTbldocPagare = value;
    }
    
    public String getSoggettoCod() {
        return this.soggettoCod;
    }
    
    public void setSoggettoCod(final String value) {
        this.soggettoCod = value;
    }
    
    public String getFornituraCod() {
        return this.fornituraCod;
    }
    
    public void setFornituraCod(final String value) {
        this.fornituraCod = value;
    }
}
