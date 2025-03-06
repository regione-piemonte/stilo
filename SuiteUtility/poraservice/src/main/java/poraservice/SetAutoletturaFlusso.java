// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import autolettura.pora.types.prontoweb.eng.it.TIPOAUTOLETTURA;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "fornituraCod", "matricolaMis", "valoreLetturaMis", "matricolaCorr", "valoreLetturaCorr", "dataLettura", "tipoAutoLettura" })
@XmlRootElement(name = "SetAutoletturaFlusso")
public class SetAutoletturaFlusso
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String fornituraCod;
    @XmlElement(nillable = true)
    protected String matricolaMis;
    protected BigDecimal valoreLetturaMis;
    @XmlElement(nillable = true)
    protected String matricolaCorr;
    protected BigDecimal valoreLetturaCorr;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLettura;
    @XmlSchemaType(name = "string")
    protected TIPOAUTOLETTURA tipoAutoLettura;
    
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
    
    public String getMatricolaMis() {
        return this.matricolaMis;
    }
    
    public void setMatricolaMis(final String value) {
        this.matricolaMis = value;
    }
    
    public BigDecimal getValoreLetturaMis() {
        return this.valoreLetturaMis;
    }
    
    public void setValoreLetturaMis(final BigDecimal value) {
        this.valoreLetturaMis = value;
    }
    
    public String getMatricolaCorr() {
        return this.matricolaCorr;
    }
    
    public void setMatricolaCorr(final String value) {
        this.matricolaCorr = value;
    }
    
    public BigDecimal getValoreLetturaCorr() {
        return this.valoreLetturaCorr;
    }
    
    public void setValoreLetturaCorr(final BigDecimal value) {
        this.valoreLetturaCorr = value;
    }
    
    public XMLGregorianCalendar getDataLettura() {
        return this.dataLettura;
    }
    
    public void setDataLettura(final XMLGregorianCalendar value) {
        this.dataLettura = value;
    }
    
    public TIPOAUTOLETTURA getTipoAutoLettura() {
        return this.tipoAutoLettura;
    }
    
    public void setTipoAutoLettura(final TIPOAUTOLETTURA value) {
        this.tipoAutoLettura = value;
    }
}
