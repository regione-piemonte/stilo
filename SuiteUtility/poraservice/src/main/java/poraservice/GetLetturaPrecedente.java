// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "filter", "fornituracod", "dataRiferimento" })
@XmlRootElement(name = "GetLetturaPrecedente")
public class GetLetturaPrecedente
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filter;
    @XmlElement(nillable = true)
    protected String fornituracod;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRiferimento;
    
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
    
    public String getFornituracod() {
        return this.fornituracod;
    }
    
    public void setFornituracod(final String value) {
        this.fornituracod = value;
    }
    
    public XMLGregorianCalendar getDataRiferimento() {
        return this.dataRiferimento;
    }
    
    public void setDataRiferimento(final XMLGregorianCalendar value) {
        this.dataRiferimento = value;
    }
}
