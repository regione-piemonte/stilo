// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import pora.types.prontoweb.eng.it.ArrayOfSTATOFORNITURA;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggettoCOD", "ruolosoggCOD", "nominativo", "codiceFiscale", "partitaIVA", "soggCodAmm", "codicePDRPOR", "fornituraCod", "contrattoNum", "elencoStati", "filter" })
@XmlRootElement(name = "GetSoggetti")
public class GetSoggetti
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String soggettoCOD;
    @XmlElement(nillable = true)
    protected String ruolosoggCOD;
    @XmlElement(nillable = true)
    protected String nominativo;
    @XmlElement(nillable = true)
    protected String codiceFiscale;
    @XmlElement(nillable = true)
    protected String partitaIVA;
    @XmlElement(nillable = true)
    protected String soggCodAmm;
    @XmlElement(nillable = true)
    protected String codicePDRPOR;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    @XmlElement(nillable = true)
    protected String contrattoNum;
    @XmlElement(nillable = true)
    protected ArrayOfSTATOFORNITURA elencoStati;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filter;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getSoggettoCOD() {
        return this.soggettoCOD;
    }
    
    public void setSoggettoCOD(final String value) {
        this.soggettoCOD = value;
    }
    
    public String getRuolosoggCOD() {
        return this.ruolosoggCOD;
    }
    
    public void setRuolosoggCOD(final String value) {
        this.ruolosoggCOD = value;
    }
    
    public String getNominativo() {
        return this.nominativo;
    }
    
    public void setNominativo(final String value) {
        this.nominativo = value;
    }
    
    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }
    
    public void setCodiceFiscale(final String value) {
        this.codiceFiscale = value;
    }
    
    public String getPartitaIVA() {
        return this.partitaIVA;
    }
    
    public void setPartitaIVA(final String value) {
        this.partitaIVA = value;
    }
    
    public String getSoggCodAmm() {
        return this.soggCodAmm;
    }
    
    public void setSoggCodAmm(final String value) {
        this.soggCodAmm = value;
    }
    
    public String getCodicePDRPOR() {
        return this.codicePDRPOR;
    }
    
    public void setCodicePDRPOR(final String value) {
        this.codicePDRPOR = value;
    }
    
    public String getFornituraCod() {
        return this.fornituraCod;
    }
    
    public void setFornituraCod(final String value) {
        this.fornituraCod = value;
    }
    
    public String getContrattoNum() {
        return this.contrattoNum;
    }
    
    public void setContrattoNum(final String value) {
        this.contrattoNum = value;
    }
    
    public ArrayOfSTATOFORNITURA getElencoStati() {
        return this.elencoStati;
    }
    
    public void setElencoStati(final ArrayOfSTATOFORNITURA value) {
        this.elencoStati = value;
    }
    
    public PWEBTABLEFILTER getFilter() {
        return this.filter;
    }
    
    public void setFilter(final PWEBTABLEFILTER value) {
        this.filter = value;
    }
}
