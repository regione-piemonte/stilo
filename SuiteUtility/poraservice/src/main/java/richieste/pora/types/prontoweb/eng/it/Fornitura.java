// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fornitura", propOrder = { "codicePDR", "codFornitura", "codProdOff", "codLineaProd", "codTipoServizio", "codPlayerUscente", "matricola", "consumoAnnuo", "codCategoriaUso", "codResidenza", "codPotenza", "codPlayerDistributore", "descPlayerDistributore", "codRemi", "codTipoFornitura", "listaAttributiDinamiciForn", "codTipoMercatoProvenienza" })
public class Fornitura
{
    @XmlElement(name = "CodicePDR", nillable = true)
    protected String codicePDR;
    @XmlElement(name = "CodFornitura")
    protected Long codFornitura;
    @XmlElement(name = "CodProdOff", nillable = true)
    protected String codProdOff;
    @XmlElement(name = "CodLineaProd", nillable = true)
    protected String codLineaProd;
    @XmlElement(name = "CodTipoServizio", nillable = true)
    protected String codTipoServizio;
    @XmlElement(name = "CodPlayerUscente", nillable = true)
    protected String codPlayerUscente;
    @XmlElement(name = "Matricola", nillable = true)
    protected String matricola;
    @XmlElement(name = "ConsumoAnnuo", nillable = true)
    protected String consumoAnnuo;
    @XmlElement(name = "CodCategoriaUso", nillable = true)
    protected String codCategoriaUso;
    @XmlElement(name = "CodResidenza", nillable = true)
    protected String codResidenza;
    @XmlElement(name = "CodPotenza", nillable = true)
    protected String codPotenza;
    @XmlElement(name = "CodPlayerDistributore", nillable = true)
    protected String codPlayerDistributore;
    @XmlElement(name = "DescPlayerDistributore", nillable = true)
    protected String descPlayerDistributore;
    @XmlElement(name = "CodRemi", nillable = true)
    protected String codRemi;
    @XmlElement(name = "CodTipoFornitura", nillable = true)
    protected String codTipoFornitura;
    @XmlElement(name = "ListaAttributiDinamiciForn", nillable = true)
    protected String listaAttributiDinamiciForn;
    @XmlElement(name = "CodTipoMercatoProvenienza", nillable = true)
    protected String codTipoMercatoProvenienza;
    
    public String getCodicePDR() {
        return this.codicePDR;
    }
    
    public void setCodicePDR(final String value) {
        this.codicePDR = value;
    }
    
    public Long getCodFornitura() {
        return this.codFornitura;
    }
    
    public void setCodFornitura(final Long value) {
        this.codFornitura = value;
    }
    
    public String getCodProdOff() {
        return this.codProdOff;
    }
    
    public void setCodProdOff(final String value) {
        this.codProdOff = value;
    }
    
    public String getCodLineaProd() {
        return this.codLineaProd;
    }
    
    public void setCodLineaProd(final String value) {
        this.codLineaProd = value;
    }
    
    public String getCodTipoServizio() {
        return this.codTipoServizio;
    }
    
    public void setCodTipoServizio(final String value) {
        this.codTipoServizio = value;
    }
    
    public String getCodPlayerUscente() {
        return this.codPlayerUscente;
    }
    
    public void setCodPlayerUscente(final String value) {
        this.codPlayerUscente = value;
    }
    
    public String getMatricola() {
        return this.matricola;
    }
    
    public void setMatricola(final String value) {
        this.matricola = value;
    }
    
    public String getConsumoAnnuo() {
        return this.consumoAnnuo;
    }
    
    public void setConsumoAnnuo(final String value) {
        this.consumoAnnuo = value;
    }
    
    public String getCodCategoriaUso() {
        return this.codCategoriaUso;
    }
    
    public void setCodCategoriaUso(final String value) {
        this.codCategoriaUso = value;
    }
    
    public String getCodResidenza() {
        return this.codResidenza;
    }
    
    public void setCodResidenza(final String value) {
        this.codResidenza = value;
    }
    
    public String getCodPotenza() {
        return this.codPotenza;
    }
    
    public void setCodPotenza(final String value) {
        this.codPotenza = value;
    }
    
    public String getCodPlayerDistributore() {
        return this.codPlayerDistributore;
    }
    
    public void setCodPlayerDistributore(final String value) {
        this.codPlayerDistributore = value;
    }
    
    public String getDescPlayerDistributore() {
        return this.descPlayerDistributore;
    }
    
    public void setDescPlayerDistributore(final String value) {
        this.descPlayerDistributore = value;
    }
    
    public String getCodRemi() {
        return this.codRemi;
    }
    
    public void setCodRemi(final String value) {
        this.codRemi = value;
    }
    
    public String getCodTipoFornitura() {
        return this.codTipoFornitura;
    }
    
    public void setCodTipoFornitura(final String value) {
        this.codTipoFornitura = value;
    }
    
    public String getListaAttributiDinamiciForn() {
        return this.listaAttributiDinamiciForn;
    }
    
    public void setListaAttributiDinamiciForn(final String value) {
        this.listaAttributiDinamiciForn = value;
    }
    
    public String getCodTipoMercatoProvenienza() {
        return this.codTipoMercatoProvenienza;
    }
    
    public void setCodTipoMercatoProvenienza(final String value) {
        this.codTipoMercatoProvenienza = value;
    }
}
