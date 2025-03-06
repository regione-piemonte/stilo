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
@XmlType(name = "", propOrder = { "multicompany", "filterTbl", "soggettoCOD", "fornituraCOD", "ruoloSoggettoCod", "nominativo", "podpdrCod", "dataIniEmis", "nonPagati", "ultimaFatt" })
@XmlRootElement(name = "GetSituazioneDebitoria")
public class GetSituazioneDebitoria
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filterTbl;
    @XmlElement(nillable = true)
    protected String soggettoCOD;
    @XmlElement(nillable = true)
    protected String fornituraCOD;
    @XmlElement(nillable = true)
    protected String ruoloSoggettoCod;
    @XmlElement(nillable = true)
    protected String nominativo;
    @XmlElement(nillable = true)
    protected String podpdrCod;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIniEmis;
    protected Boolean nonPagati;
    protected Boolean ultimaFatt;
    
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
    
    public String getSoggettoCOD() {
        return this.soggettoCOD;
    }
    
    public void setSoggettoCOD(final String value) {
        this.soggettoCOD = value;
    }
    
    public String getFornituraCOD() {
        return this.fornituraCOD;
    }
    
    public void setFornituraCOD(final String value) {
        this.fornituraCOD = value;
    }
    
    public String getRuoloSoggettoCod() {
        return this.ruoloSoggettoCod;
    }
    
    public void setRuoloSoggettoCod(final String value) {
        this.ruoloSoggettoCod = value;
    }
    
    public String getNominativo() {
        return this.nominativo;
    }
    
    public void setNominativo(final String value) {
        this.nominativo = value;
    }
    
    public String getPodpdrCod() {
        return this.podpdrCod;
    }
    
    public void setPodpdrCod(final String value) {
        this.podpdrCod = value;
    }
    
    public XMLGregorianCalendar getDataIniEmis() {
        return this.dataIniEmis;
    }
    
    public void setDataIniEmis(final XMLGregorianCalendar value) {
        this.dataIniEmis = value;
    }
    
    public Boolean isNonPagati() {
        return this.nonPagati;
    }
    
    public void setNonPagati(final Boolean value) {
        this.nonPagati = value;
    }
    
    public Boolean isUltimaFatt() {
        return this.ultimaFatt;
    }
    
    public void setUltimaFatt(final Boolean value) {
        this.ultimaFatt = value;
    }
}
