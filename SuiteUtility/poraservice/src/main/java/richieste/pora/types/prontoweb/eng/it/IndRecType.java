// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndRecType", propOrder = { "nomeRecapito", "cognomeRecapito", "codNazione", "desNazione", "codRegione", "desRegione", "codProvincia", "desProvincia", "codComune", "desComune", "codLocalita", "desLocalita", "codCAP", "desCAP", "codTipoVia", "desTipoVia", "codVia", "desVia", "codTipoCiv", "desTipoCiv", "descrizioneCivico", "numeroCivico", "suffisso", "scala", "piano", "interno", "lettera" })
public class IndRecType
{
    @XmlElement(name = "NomeRecapito", nillable = true)
    protected String nomeRecapito;
    @XmlElement(name = "CognomeRecapito", nillable = true)
    protected String cognomeRecapito;
    @XmlElement(name = "CodNazione", nillable = true)
    protected String codNazione;
    @XmlElement(name = "DesNazione", nillable = true)
    protected String desNazione;
    @XmlElement(name = "CodRegione", nillable = true)
    protected String codRegione;
    @XmlElement(name = "DesRegione", nillable = true)
    protected String desRegione;
    @XmlElement(name = "CodProvincia", nillable = true)
    protected String codProvincia;
    @XmlElement(name = "DesProvincia", nillable = true)
    protected String desProvincia;
    @XmlElement(name = "CodComune", nillable = true)
    protected String codComune;
    @XmlElement(name = "DesComune", nillable = true)
    protected String desComune;
    @XmlElement(name = "CodLocalita", nillable = true)
    protected String codLocalita;
    @XmlElement(name = "DesLocalita", nillable = true)
    protected String desLocalita;
    @XmlElement(name = "CodCAP", nillable = true)
    protected String codCAP;
    @XmlElement(name = "DesCAP", nillable = true)
    protected String desCAP;
    @XmlElement(name = "CodTipoVia", nillable = true)
    protected String codTipoVia;
    @XmlElement(name = "DesTipoVia", nillable = true)
    protected String desTipoVia;
    @XmlElement(name = "CodVia", nillable = true)
    protected String codVia;
    @XmlElement(name = "DesVia", nillable = true)
    protected String desVia;
    @XmlElement(name = "CodTipoCiv", nillable = true)
    protected String codTipoCiv;
    @XmlElement(name = "DesTipoCiv", nillable = true)
    protected String desTipoCiv;
    @XmlElement(name = "DescrizioneCivico", nillable = true)
    protected String descrizioneCivico;
    @XmlElement(name = "NumeroCivico")
    protected Double numeroCivico;
    @XmlElement(name = "Suffisso", nillable = true)
    protected String suffisso;
    @XmlElement(name = "Scala", nillable = true)
    protected String scala;
    @XmlElement(name = "Piano", nillable = true)
    protected String piano;
    @XmlElement(name = "Interno", nillable = true)
    protected String interno;
    @XmlElement(name = "Lettera", nillable = true)
    protected String lettera;
    
    public String getNomeRecapito() {
        return this.nomeRecapito;
    }
    
    public void setNomeRecapito(final String value) {
        this.nomeRecapito = value;
    }
    
    public String getCognomeRecapito() {
        return this.cognomeRecapito;
    }
    
    public void setCognomeRecapito(final String value) {
        this.cognomeRecapito = value;
    }
    
    public String getCodNazione() {
        return this.codNazione;
    }
    
    public void setCodNazione(final String value) {
        this.codNazione = value;
    }
    
    public String getDesNazione() {
        return this.desNazione;
    }
    
    public void setDesNazione(final String value) {
        this.desNazione = value;
    }
    
    public String getCodRegione() {
        return this.codRegione;
    }
    
    public void setCodRegione(final String value) {
        this.codRegione = value;
    }
    
    public String getDesRegione() {
        return this.desRegione;
    }
    
    public void setDesRegione(final String value) {
        this.desRegione = value;
    }
    
    public String getCodProvincia() {
        return this.codProvincia;
    }
    
    public void setCodProvincia(final String value) {
        this.codProvincia = value;
    }
    
    public String getDesProvincia() {
        return this.desProvincia;
    }
    
    public void setDesProvincia(final String value) {
        this.desProvincia = value;
    }
    
    public String getCodComune() {
        return this.codComune;
    }
    
    public void setCodComune(final String value) {
        this.codComune = value;
    }
    
    public String getDesComune() {
        return this.desComune;
    }
    
    public void setDesComune(final String value) {
        this.desComune = value;
    }
    
    public String getCodLocalita() {
        return this.codLocalita;
    }
    
    public void setCodLocalita(final String value) {
        this.codLocalita = value;
    }
    
    public String getDesLocalita() {
        return this.desLocalita;
    }
    
    public void setDesLocalita(final String value) {
        this.desLocalita = value;
    }
    
    public String getCodCAP() {
        return this.codCAP;
    }
    
    public void setCodCAP(final String value) {
        this.codCAP = value;
    }
    
    public String getDesCAP() {
        return this.desCAP;
    }
    
    public void setDesCAP(final String value) {
        this.desCAP = value;
    }
    
    public String getCodTipoVia() {
        return this.codTipoVia;
    }
    
    public void setCodTipoVia(final String value) {
        this.codTipoVia = value;
    }
    
    public String getDesTipoVia() {
        return this.desTipoVia;
    }
    
    public void setDesTipoVia(final String value) {
        this.desTipoVia = value;
    }
    
    public String getCodVia() {
        return this.codVia;
    }
    
    public void setCodVia(final String value) {
        this.codVia = value;
    }
    
    public String getDesVia() {
        return this.desVia;
    }
    
    public void setDesVia(final String value) {
        this.desVia = value;
    }
    
    public String getCodTipoCiv() {
        return this.codTipoCiv;
    }
    
    public void setCodTipoCiv(final String value) {
        this.codTipoCiv = value;
    }
    
    public String getDesTipoCiv() {
        return this.desTipoCiv;
    }
    
    public void setDesTipoCiv(final String value) {
        this.desTipoCiv = value;
    }
    
    public String getDescrizioneCivico() {
        return this.descrizioneCivico;
    }
    
    public void setDescrizioneCivico(final String value) {
        this.descrizioneCivico = value;
    }
    
    public Double getNumeroCivico() {
        return this.numeroCivico;
    }
    
    public void setNumeroCivico(final Double value) {
        this.numeroCivico = value;
    }
    
    public String getSuffisso() {
        return this.suffisso;
    }
    
    public void setSuffisso(final String value) {
        this.suffisso = value;
    }
    
    public String getScala() {
        return this.scala;
    }
    
    public void setScala(final String value) {
        this.scala = value;
    }
    
    public String getPiano() {
        return this.piano;
    }
    
    public void setPiano(final String value) {
        this.piano = value;
    }
    
    public String getInterno() {
        return this.interno;
    }
    
    public void setInterno(final String value) {
        this.interno = value;
    }
    
    public String getLettera() {
        return this.lettera;
    }
    
    public void setLettera(final String value) {
        this.lettera = value;
    }
}
