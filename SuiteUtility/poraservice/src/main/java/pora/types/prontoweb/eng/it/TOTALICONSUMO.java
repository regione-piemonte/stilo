// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TOTALICONSUMO", propOrder = { "anno", "codicefiscale", "codicepodpdr", "elencostati", "fornituracod", "ruolosoggettocod", "soggettocod", "soggettocodagente", "totaleconsumoenele", "totaleconsumogm", "totalefatturato", "totalepagato" })
public class TOTALICONSUMO
{
    @XmlElement(name = "ANNO")
    protected int anno;
    @XmlElement(name = "CODICEFISCALE", nillable = true)
    protected String codicefiscale;
    @XmlElement(name = "CODICEPODPDR", nillable = true)
    protected String codicepodpdr;
    @XmlElement(name = "ELENCOSTATI", nillable = true)
    protected ArrayOfSTATOFORNITURA elencostati;
    @XmlElement(name = "FORNITURACOD", nillable = true)
    protected String fornituracod;
    @XmlElement(name = "RUOLOSOGGETTOCOD", nillable = true)
    protected String ruolosoggettocod;
    @XmlElement(name = "SOGGETTOCOD", required = true, nillable = true)
    protected String soggettocod;
    @XmlElement(name = "SOGGETTOCODAGENTE", required = true, nillable = true)
    protected String soggettocodagente;
    @XmlElement(name = "TOTALECONSUMOENELE")
    protected Double totaleconsumoenele;
    @XmlElement(name = "TOTALECONSUMOGM")
    protected Double totaleconsumogm;
    @XmlElement(name = "TOTALEFATTURATO")
    protected Double totalefatturato;
    @XmlElement(name = "TOTALEPAGATO")
    protected Double totalepagato;
    
    public int getANNO() {
        return this.anno;
    }
    
    public void setANNO(final int value) {
        this.anno = value;
    }
    
    public String getCODICEFISCALE() {
        return this.codicefiscale;
    }
    
    public void setCODICEFISCALE(final String value) {
        this.codicefiscale = value;
    }
    
    public String getCODICEPODPDR() {
        return this.codicepodpdr;
    }
    
    public void setCODICEPODPDR(final String value) {
        this.codicepodpdr = value;
    }
    
    public ArrayOfSTATOFORNITURA getELENCOSTATI() {
        return this.elencostati;
    }
    
    public void setELENCOSTATI(final ArrayOfSTATOFORNITURA value) {
        this.elencostati = value;
    }
    
    public String getFORNITURACOD() {
        return this.fornituracod;
    }
    
    public void setFORNITURACOD(final String value) {
        this.fornituracod = value;
    }
    
    public String getRUOLOSOGGETTOCOD() {
        return this.ruolosoggettocod;
    }
    
    public void setRUOLOSOGGETTOCOD(final String value) {
        this.ruolosoggettocod = value;
    }
    
    public String getSOGGETTOCOD() {
        return this.soggettocod;
    }
    
    public void setSOGGETTOCOD(final String value) {
        this.soggettocod = value;
    }
    
    public String getSOGGETTOCODAGENTE() {
        return this.soggettocodagente;
    }
    
    public void setSOGGETTOCODAGENTE(final String value) {
        this.soggettocodagente = value;
    }
    
    public Double getTOTALECONSUMOENELE() {
        return this.totaleconsumoenele;
    }
    
    public void setTOTALECONSUMOENELE(final Double value) {
        this.totaleconsumoenele = value;
    }
    
    public Double getTOTALECONSUMOGM() {
        return this.totaleconsumogm;
    }
    
    public void setTOTALECONSUMOGM(final Double value) {
        this.totaleconsumogm = value;
    }
    
    public Double getTOTALEFATTURATO() {
        return this.totalefatturato;
    }
    
    public void setTOTALEFATTURATO(final Double value) {
        this.totalefatturato = value;
    }
    
    public Double getTOTALEPAGATO() {
        return this.totalepagato;
    }
    
    public void setTOTALEPAGATO(final Double value) {
        this.totalepagato = value;
    }
}
