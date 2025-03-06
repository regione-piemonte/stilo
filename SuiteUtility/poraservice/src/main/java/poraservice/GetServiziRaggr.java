// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import pora.types.prontoweb.eng.it.ArrayOfSTATOFORNITURA;
import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "filterTbl", "filterStatiForn", "fornituraCod" })
@XmlRootElement(name = "GetServiziRaggr")
public class GetServiziRaggr
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filterTbl;
    @XmlElement(nillable = true)
    protected ArrayOfSTATOFORNITURA filterStatiForn;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public PWEBTABLEFILTER getFilterTbl() {
        return this.filterTbl;
    }
    
    public void setFilterTbl(final PWEBTABLEFILTER value) {
        this.filterTbl = value;
    }
    
    public ArrayOfSTATOFORNITURA getFilterStatiForn() {
        return this.filterStatiForn;
    }
    
    public void setFilterStatiForn(final ArrayOfSTATOFORNITURA value) {
        this.filterStatiForn = value;
    }
    
    public String getFornituraCod() {
        return this.fornituraCod;
    }
    
    public void setFornituraCod(final String value) {
        this.fornituraCod = value;
    }
}
