// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import pora.types.prontoweb.eng.it.ArrayOfFORNITURACP;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "elencoForniture", "periodoIni", "periodoFin", "filter" })
@XmlRootElement(name = "GetCurvePrelievo")
public class GetCurvePrelievo
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected ArrayOfFORNITURACP elencoForniture;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodoIni;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodoFin;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filter;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public ArrayOfFORNITURACP getElencoForniture() {
        return this.elencoForniture;
    }
    
    public void setElencoForniture(final ArrayOfFORNITURACP value) {
        this.elencoForniture = value;
    }
    
    public XMLGregorianCalendar getPeriodoIni() {
        return this.periodoIni;
    }
    
    public void setPeriodoIni(final XMLGregorianCalendar value) {
        this.periodoIni = value;
    }
    
    public XMLGregorianCalendar getPeriodoFin() {
        return this.periodoFin;
    }
    
    public void setPeriodoFin(final XMLGregorianCalendar value) {
        this.periodoFin = value;
    }
    
    public PWEBTABLEFILTER getFilter() {
        return this.filter;
    }
    
    public void setFilter(final PWEBTABLEFILTER value) {
        this.filter = value;
    }
}
