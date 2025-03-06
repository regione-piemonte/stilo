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
@XmlType(name = "", propOrder = { "multicompany", "fornituraCod", "dataLettura", "valFascia1", "valFascia2", "valFascia3", "forzaPeriodo", "forzaConsumo" })
@XmlRootElement(name = "SetAutoletturaEle")
public class SetAutoletturaEle
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLettura;
    protected BigDecimal valFascia1;
    @XmlElement(nillable = true)
    protected BigDecimal valFascia2;
    @XmlElement(nillable = true)
    protected BigDecimal valFascia3;
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
    
    public BigDecimal getValFascia1() {
        return this.valFascia1;
    }
    
    public void setValFascia1(final BigDecimal value) {
        this.valFascia1 = value;
    }
    
    public BigDecimal getValFascia2() {
        return this.valFascia2;
    }
    
    public void setValFascia2(final BigDecimal value) {
        this.valFascia2 = value;
    }
    
    public BigDecimal getValFascia3() {
        return this.valFascia3;
    }
    
    public void setValFascia3(final BigDecimal value) {
        this.valFascia3 = value;
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
