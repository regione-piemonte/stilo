// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoFornitura", propOrder = { "codSito", "societaId", "sezioneId", "prodOffCodX0020", "residenzaCodX0020", "potenzaValX0020X0020X0020", "tipoFornituraCodX0020X0020X0020" })
public class TipoFornitura
{
    @XmlElement(name = "CodSito")
    protected Integer codSito;
    @XmlElement(name = "SocietaId", nillable = true)
    protected String societaId;
    @XmlElement(name = "SezioneId", nillable = true)
    protected String sezioneId;
    @XmlElement(name = "ProdOffCod_x0020_", nillable = true)
    protected String prodOffCodX0020;
    @XmlElement(name = "ResidenzaCod_x0020_", nillable = true)
    protected String residenzaCodX0020;
    @XmlElement(name = "PotenzaVal_x0020__x0020__x0020_", nillable = true)
    protected String potenzaValX0020X0020X0020;
    @XmlElement(name = "TipoFornituraCod_x0020__x0020__x0020_", nillable = true)
    protected String tipoFornituraCodX0020X0020X0020;
    
    public Integer getCodSito() {
        return this.codSito;
    }
    
    public void setCodSito(final Integer value) {
        this.codSito = value;
    }
    
    public String getSocietaId() {
        return this.societaId;
    }
    
    public void setSocietaId(final String value) {
        this.societaId = value;
    }
    
    public String getSezioneId() {
        return this.sezioneId;
    }
    
    public void setSezioneId(final String value) {
        this.sezioneId = value;
    }
    
    public String getProdOffCodX0020() {
        return this.prodOffCodX0020;
    }
    
    public void setProdOffCodX0020(final String value) {
        this.prodOffCodX0020 = value;
    }
    
    public String getResidenzaCodX0020() {
        return this.residenzaCodX0020;
    }
    
    public void setResidenzaCodX0020(final String value) {
        this.residenzaCodX0020 = value;
    }
    
    public String getPotenzaValX0020X0020X0020() {
        return this.potenzaValX0020X0020X0020;
    }
    
    public void setPotenzaValX0020X0020X0020(final String value) {
        this.potenzaValX0020X0020X0020 = value;
    }
    
    public String getTipoFornituraCodX0020X0020X0020() {
        return this.tipoFornituraCodX0020X0020X0020;
    }
    
    public void setTipoFornituraCodX0020X0020X0020(final String value) {
        this.tipoFornituraCodX0020X0020X0020 = value;
    }
}
