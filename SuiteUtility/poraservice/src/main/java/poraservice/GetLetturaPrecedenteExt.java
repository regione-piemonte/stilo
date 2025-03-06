// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import pora.types.prontoweb.eng.it.ArrayOfSTATOFORNITURA;
import pora.types.prontoweb.eng.it.PWEBTABLEFILTER;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "filter", "soggettoCOD", "tipoServizioCod", "codicePDRPOR", "fornituracod", "contrattoNum", "prodottoCod", "elencoStati", "dataRiferimento" })
@XmlRootElement(name = "GetLetturaPrecedenteExt")
public class GetLetturaPrecedenteExt
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected PWEBTABLEFILTER filter;
    @XmlElement(nillable = true)
    protected String soggettoCOD;
    @XmlElement(nillable = true)
    protected String tipoServizioCod;
    @XmlElement(nillable = true)
    protected String codicePDRPOR;
    @XmlElement(nillable = true)
    protected String fornituracod;
    @XmlElement(nillable = true)
    protected String contrattoNum;
    @XmlElement(nillable = true)
    protected String prodottoCod;
    @XmlElement(nillable = true)
    protected ArrayOfSTATOFORNITURA elencoStati;
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
    
    public String getSoggettoCOD() {
        return this.soggettoCOD;
    }
    
    public void setSoggettoCOD(final String value) {
        this.soggettoCOD = value;
    }
    
    public String getTipoServizioCod() {
        return this.tipoServizioCod;
    }
    
    public void setTipoServizioCod(final String value) {
        this.tipoServizioCod = value;
    }
    
    public String getCodicePDRPOR() {
        return this.codicePDRPOR;
    }
    
    public void setCodicePDRPOR(final String value) {
        this.codicePDRPOR = value;
    }
    
    public String getFornituracod() {
        return this.fornituracod;
    }
    
    public void setFornituracod(final String value) {
        this.fornituracod = value;
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
    
    public XMLGregorianCalendar getDataRiferimento() {
        return this.dataRiferimento;
    }
    
    public void setDataRiferimento(final XMLGregorianCalendar value) {
        this.dataRiferimento = value;
    }
}
