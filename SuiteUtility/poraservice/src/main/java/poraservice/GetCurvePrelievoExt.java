// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import pora.types.prontoweb.eng.it.ArrayOfSTATOFORNITURA;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggettoCod", "ruolosoggCod", "nominativo", "codiceFiscale", "partitaIva", "soggCodAmm", "tipoServizioCod", "codicePDRPOD", "fornituraCod", "contrattoNum", "prodottoCod", "elencoStati", "periodoIni", "periodoFin", "filter" })
@XmlRootElement(name = "GetCurvePrelievoExt")
public class GetCurvePrelievoExt
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String soggettoCod;
    @XmlElement(nillable = true)
    protected String ruolosoggCod;
    @XmlElement(nillable = true)
    protected String nominativo;
    @XmlElement(nillable = true)
    protected String codiceFiscale;
    @XmlElement(nillable = true)
    protected String partitaIva;
    @XmlElement(nillable = true)
    protected String soggCodAmm;
    @XmlElement(nillable = true)
    protected String tipoServizioCod;
    @XmlElement(nillable = true)
    protected String codicePDRPOD;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    @XmlElement(nillable = true)
    protected String contrattoNum;
    @XmlElement(nillable = true)
    protected String prodottoCod;
    @XmlElement(nillable = true)
    protected ArrayOfSTATOFORNITURA elencoStati;
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
    
    public String getSoggettoCod() {
        return this.soggettoCod;
    }
    
    public void setSoggettoCod(final String value) {
        this.soggettoCod = value;
    }
    
    public String getRuolosoggCod() {
        return this.ruolosoggCod;
    }
    
    public void setRuolosoggCod(final String value) {
        this.ruolosoggCod = value;
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
    
    public String getPartitaIva() {
        return this.partitaIva;
    }
    
    public void setPartitaIva(final String value) {
        this.partitaIva = value;
    }
    
    public String getSoggCodAmm() {
        return this.soggCodAmm;
    }
    
    public void setSoggCodAmm(final String value) {
        this.soggCodAmm = value;
    }
    
    public String getTipoServizioCod() {
        return this.tipoServizioCod;
    }
    
    public void setTipoServizioCod(final String value) {
        this.tipoServizioCod = value;
    }
    
    public String getCodicePDRPOD() {
        return this.codicePDRPOD;
    }
    
    public void setCodicePDRPOD(final String value) {
        this.codicePDRPOD = value;
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
