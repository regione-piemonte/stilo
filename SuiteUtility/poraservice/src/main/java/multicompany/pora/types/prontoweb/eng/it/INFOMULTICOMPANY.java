// 
// Decompiled by Procyon v0.5.36
// 

package multicompany.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import registrazione.pora.types.prontoweb.eng.it.ESITORECUPEROCREDENZIALI;
import registrazione.pora.types.prontoweb.eng.it.ACCESSOSITO;
import registrazione.pora.types.prontoweb.eng.it.SOGGETTONEWS;
import registrazione.pora.types.prontoweb.eng.it.ESITOREGISTRAZIONE;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.INFOLINGUA;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INFOMULTICOMPANY", propOrder = { "account", "codsito", "fornituracod", "sessionid", "sezioneid", "societaid", "soggettocod" })
@XmlSeeAlso({ INFOMULTICOMPANYEXT.class, ESITOREGISTRAZIONE.class, SOGGETTONEWS.class, ACCESSOSITO.class, ESITORECUPEROCREDENZIALI.class })
public class INFOMULTICOMPANY extends INFOLINGUA
{
    @XmlElement(name = "ACCOUNT", nillable = true)
    protected String account;
    @XmlElement(name = "COD_SITO")
    protected Integer codsito;
    @XmlElement(name = "FORNITURA_COD", nillable = true)
    protected String fornituracod;
    @XmlElement(name = "SESSIONID", nillable = true)
    protected String sessionid;
    @XmlElement(name = "SEZIONEID", nillable = true)
    protected String sezioneid;
    @XmlElement(name = "SOCIETAID", nillable = true)
    protected String societaid;
    @XmlElement(name = "SOGGETTO_COD", nillable = true)
    protected String soggettocod;
    
    public String getACCOUNT() {
        return this.account;
    }
    
    public void setACCOUNT(final String value) {
        this.account = value;
    }
    
    public Integer getCODSITO() {
        return this.codsito;
    }
    
    public void setCODSITO(final Integer value) {
        this.codsito = value;
    }
    
    public String getFORNITURACOD() {
        return this.fornituracod;
    }
    
    public void setFORNITURACOD(final String value) {
        this.fornituracod = value;
    }
    
    public String getSESSIONID() {
        return this.sessionid;
    }
    
    public void setSESSIONID(final String value) {
        this.sessionid = value;
    }
    
    public String getSEZIONEID() {
        return this.sezioneid;
    }
    
    public void setSEZIONEID(final String value) {
        this.sezioneid = value;
    }
    
    public String getSOCIETAID() {
        return this.societaid;
    }
    
    public void setSOCIETAID(final String value) {
        this.societaid = value;
    }
    
    public String getSOGGETTOCOD() {
        return this.soggettocod;
    }
    
    public void setSOGGETTOCOD(final String value) {
        this.soggettocod = value;
    }
}
