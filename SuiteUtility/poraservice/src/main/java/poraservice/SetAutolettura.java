// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "fornituraCod", "dataLettura", "valore", "forzaPeriodo", "forzaConsumo" })
@XmlRootElement(name = "SetAutolettura")
public class SetAutolettura
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLettura;
    protected BigDecimal valore;
    protected Boolean forzaPeriodo;
    protected Boolean forzaConsumo;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getFornituraCod() {
        return this.fornituraCod;
    }
    
    public void setFornituraCod(final String value) {
        this.fornituraCod = value;
    }
    
    public XMLGregorianCalendar getDataLettura() {
        return this.dataLettura;
    }
    
    public void setDataLettura(final XMLGregorianCalendar value) {
        this.dataLettura = value;
    }
    
    public BigDecimal getValore() {
        return this.valore;
    }
    
    public void setValore(final BigDecimal value) {
        this.valore = value;
    }
    
    public Boolean isForzaPeriodo() {
        return this.forzaPeriodo;
    }
    
    public void setForzaPeriodo(final Boolean value) {
        this.forzaPeriodo = value;
    }
    
    public Boolean isForzaConsumo() {
        return this.forzaConsumo;
    }
    
    public void setForzaConsumo(final Boolean value) {
        this.forzaConsumo = value;
    }
}
