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
@XmlType(name = "", propOrder = { "multicompany", "filterTbl", "annoRiferimento", "soggettoCodAmm", "soggettoCod", "ruoloSoggettoCod", "codiceFiscale", "codicePODPDR", "fornituraCod", "elencoStati" })
@XmlRootElement(name = "GetTotaliConsumoFatturatoPagato")
public class GetTotaliConsumoFatturatoPagato
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filterTbl;
    protected Integer annoRiferimento;
    @XmlElement(nillable = true)
    protected String soggettoCodAmm;
    @XmlElement(nillable = true)
    protected String soggettoCod;
    @XmlElement(nillable = true)
    protected String ruoloSoggettoCod;
    @XmlElement(nillable = true)
    protected String codiceFiscale;
    @XmlElement(nillable = true)
    protected String codicePODPDR;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    @XmlElement(nillable = true)
    protected ArrayOfSTATOFORNITURA elencoStati;
    
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
    
    public Integer getAnnoRiferimento() {
        return this.annoRiferimento;
    }
    
    public void setAnnoRiferimento(final Integer value) {
        this.annoRiferimento = value;
    }
    
    public String getSoggettoCodAmm() {
        return this.soggettoCodAmm;
    }
    
    public void setSoggettoCodAmm(final String value) {
        this.soggettoCodAmm = value;
    }
    
    public String getSoggettoCod() {
        return this.soggettoCod;
    }
    
    public void setSoggettoCod(final String value) {
        this.soggettoCod = value;
    }
    
    public String getRuoloSoggettoCod() {
        return this.ruoloSoggettoCod;
    }
    
    public void setRuoloSoggettoCod(final String value) {
        this.ruoloSoggettoCod = value;
    }
    
    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }
    
    public void setCodiceFiscale(final String value) {
        this.codiceFiscale = value;
    }
    
    public String getCodicePODPDR() {
        return this.codicePODPDR;
    }
    
    public void setCodicePODPDR(final String value) {
        this.codicePODPDR = value;
    }
    
    public String getFornituraCod() {
        return this.fornituraCod;
    }
    
    public void setFornituraCod(final String value) {
        this.fornituraCod = value;
    }
    
    public ArrayOfSTATOFORNITURA getElencoStati() {
        return this.elencoStati;
    }
    
    public void setElencoStati(final ArrayOfSTATOFORNITURA value) {
        this.elencoStati = value;
    }
}
