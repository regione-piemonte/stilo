// 
// Decompiled by Procyon v0.5.36
// 

package alert.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INFOPROSSIMABOLL", propOrder = { "contrattoanno", "contrattoid", "contrattonumero", "fornituracod", "soggettocod" })
@XmlSeeAlso({ DATAPROSSIMABOLL.class, CONSUMOROSSIMABOLL.class })
public class INFOPROSSIMABOLL
{
    @XmlElement(name = "CONTRATTOANNO")
    protected Integer contrattoanno;
    @XmlElement(name = "CONTRATTOID", nillable = true)
    protected String contrattoid;
    @XmlElement(name = "CONTRATTONUMERO", nillable = true)
    protected String contrattonumero;
    @XmlElement(name = "FORNITURACOD", nillable = true)
    protected String fornituracod;
    @XmlElement(name = "SOGGETTOCOD", required = true, nillable = true)
    protected String soggettocod;
    
    public Integer getCONTRATTOANNO() {
        return this.contrattoanno;
    }
    
    public void setCONTRATTOANNO(final Integer value) {
        this.contrattoanno = value;
    }
    
    public String getCONTRATTOID() {
        return this.contrattoid;
    }
    
    public void setCONTRATTOID(final String value) {
        this.contrattoid = value;
    }
    
    public String getCONTRATTONUMERO() {
        return this.contrattonumero;
    }
    
    public void setCONTRATTONUMERO(final String value) {
        this.contrattonumero = value;
    }
    
    public String getFORNITURACOD() {
        return this.fornituracod;
    }
    
    public void setFORNITURACOD(final String value) {
        this.fornituracod = value;
    }
    
    public String getSOGGETTOCOD() {
        return this.soggettocod;
    }
    
    public void setSOGGETTOCOD(final String value) {
        this.soggettocod = value;
    }
}
