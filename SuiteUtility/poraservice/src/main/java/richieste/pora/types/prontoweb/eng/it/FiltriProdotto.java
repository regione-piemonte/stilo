// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FiltriProdotto", propOrder = { "tipoServizioCod", "lineaProdottoCod", "raggrProdDualCod", "prodottoCod", "offertaCod", "residenzaCod", "potenzaCod", "prodottoPrecedenteCod", "prodottoPrecedenteDualCod", "prodottoDual", "attivazione", "subentro", "switching", "cambio" })
public class FiltriProdotto
{
    @XmlElement(nillable = true)
    protected String tipoServizioCod;
    @XmlElement(nillable = true)
    protected String lineaProdottoCod;
    @XmlElement(nillable = true)
    protected String raggrProdDualCod;
    @XmlElement(nillable = true)
    protected String prodottoCod;
    @XmlElement(nillable = true)
    protected String offertaCod;
    @XmlElement(nillable = true)
    protected String residenzaCod;
    @XmlElement(nillable = true)
    protected String potenzaCod;
    @XmlElement(nillable = true)
    protected String prodottoPrecedenteCod;
    @XmlElement(nillable = true)
    protected String prodottoPrecedenteDualCod;
    protected Boolean prodottoDual;
    protected Boolean attivazione;
    protected Boolean subentro;
    protected Boolean switching;
    protected Boolean cambio;
    
    public String getTipoServizioCod() {
        return this.tipoServizioCod;
    }
    
    public void setTipoServizioCod(final String value) {
        this.tipoServizioCod = value;
    }
    
    public String getLineaProdottoCod() {
        return this.lineaProdottoCod;
    }
    
    public void setLineaProdottoCod(final String value) {
        this.lineaProdottoCod = value;
    }
    
    public String getRaggrProdDualCod() {
        return this.raggrProdDualCod;
    }
    
    public void setRaggrProdDualCod(final String value) {
        this.raggrProdDualCod = value;
    }
    
    public String getProdottoCod() {
        return this.prodottoCod;
    }
    
    public void setProdottoCod(final String value) {
        this.prodottoCod = value;
    }
    
    public String getOffertaCod() {
        return this.offertaCod;
    }
    
    public void setOffertaCod(final String value) {
        this.offertaCod = value;
    }
    
    public String getResidenzaCod() {
        return this.residenzaCod;
    }
    
    public void setResidenzaCod(final String value) {
        this.residenzaCod = value;
    }
    
    public String getPotenzaCod() {
        return this.potenzaCod;
    }
    
    public void setPotenzaCod(final String value) {
        this.potenzaCod = value;
    }
    
    public String getProdottoPrecedenteCod() {
        return this.prodottoPrecedenteCod;
    }
    
    public void setProdottoPrecedenteCod(final String value) {
        this.prodottoPrecedenteCod = value;
    }
    
    public String getProdottoPrecedenteDualCod() {
        return this.prodottoPrecedenteDualCod;
    }
    
    public void setProdottoPrecedenteDualCod(final String value) {
        this.prodottoPrecedenteDualCod = value;
    }
    
    public Boolean isProdottoDual() {
        return this.prodottoDual;
    }
    
    public void setProdottoDual(final Boolean value) {
        this.prodottoDual = value;
    }
    
    public Boolean isAttivazione() {
        return this.attivazione;
    }
    
    public void setAttivazione(final Boolean value) {
        this.attivazione = value;
    }
    
    public Boolean isSubentro() {
        return this.subentro;
    }
    
    public void setSubentro(final Boolean value) {
        this.subentro = value;
    }
    
    public Boolean isSwitching() {
        return this.switching;
    }
    
    public void setSwitching(final Boolean value) {
        this.switching = value;
    }
    
    public Boolean isCambio() {
        return this.cambio;
    }
    
    public void setCambio(final Boolean value) {
        this.cambio = value;
    }
}
