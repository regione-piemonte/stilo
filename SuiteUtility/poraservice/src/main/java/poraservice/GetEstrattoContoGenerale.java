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
@XmlType(name = "", propOrder = { "multicompany", "filterTbl", "soggettoCod", "ruoloSoggettoCod", "nominativo", "codiceFiscale", "fornituraCod", "contrattoNum", "prodottoCod", "elencoStati", "soggettoCodAmm", "partitaIVA", "tipoServizioCod", "codicePODPDR" })
@XmlRootElement(name = "GetEstrattoContoGenerale")
public class GetEstrattoContoGenerale
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filterTbl;
    @XmlElement(nillable = true)
    protected String soggettoCod;
    @XmlElement(nillable = true)
    protected String ruoloSoggettoCod;
    @XmlElement(nillable = true)
    protected String nominativo;
    @XmlElement(nillable = true)
    protected String codiceFiscale;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    @XmlElement(nillable = true)
    protected String contrattoNum;
    @XmlElement(nillable = true)
    protected String prodottoCod;
    @XmlElement(nillable = true)
    protected ArrayOfSTATOFORNITURA elencoStati;
    @XmlElement(nillable = true)
    protected String soggettoCodAmm;
    @XmlElement(nillable = true)
    protected String partitaIVA;
    @XmlElement(nillable = true)
    protected String tipoServizioCod;
    @XmlElement(nillable = true)
    protected String codicePODPDR;
    
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
    
    public String getProdottoCod() {
        return this.prodottoCod;
    }
    
    public void setProdottoCod(final String value) {
        this.prodottoCod = value;
    }
    
    public ArrayOfSTATOFORNITURA getElencoStati() {
        return this.elencoStati;
    }
    
    public void setElencoStati(final ArrayOfSTATOFORNITURA value) {
        this.elencoStati = value;
    }
    
    public String getSoggettoCodAmm() {
        return this.soggettoCodAmm;
    }
    
    public void setSoggettoCodAmm(final String value) {
        this.soggettoCodAmm = value;
    }
    
    public String getPartitaIVA() {
        return this.partitaIVA;
    }
    
    public void setPartitaIVA(final String value) {
        this.partitaIVA = value;
    }
    
    public String getTipoServizioCod() {
        return this.tipoServizioCod;
    }
    
    public void setTipoServizioCod(final String value) {
        this.tipoServizioCod = value;
    }
    
    public String getCodicePODPDR() {
        return this.codicePODPDR;
    }
    
    public void setCodicePODPDR(final String value) {
        this.codicePODPDR = value;
    }
}
