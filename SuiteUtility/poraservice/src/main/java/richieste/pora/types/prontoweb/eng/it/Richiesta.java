// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import org.datacontract.schemas._2004._07.pweb_be_pora_types.ArrayOfCustomAttribute;
import registrazione.pora.types.prontoweb.eng.it.ArrayOfCONSENSO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Richiesta", propOrder = { "lForniture", "codSottotipo", "note", "iban", "classificConto", "flagStorno", "intestatarioConto", "cfIntestatarioConto", "sottoscrittore", "cfSottoscrittore", "telefono1", "telefono2", "fax", "email", "codAssenzaDatiCat", "tipoCatasto", "codComuneCatasto", "sezioneUrbana", "foglio", "particellaMappale", "particellaDenom", "tipoParticella", "subalterno", "indRecapitoBol", "indUbicazForn", "lConsensi", "potenza", "emailInvioBolletta", "codLingua", "customAttributes", "tipoStampaBolletta", "dataFirma", "dataValidit\u00e0Prodotto", "codCodizionePagamento", "agente", "codTipoProcesso" })
public class Richiesta
{
    @XmlElement(name = "LForniture", nillable = true)
    protected ArrayOfFornitura lForniture;
    @XmlElement(name = "CodSottotipo", nillable = true)
    protected String codSottotipo;
    @XmlElement(name = "Note", nillable = true)
    protected String note;
    @XmlElement(name = "IBAN", nillable = true)
    protected String iban;
    @XmlElement(name = "ClassificConto", nillable = true)
    protected String classificConto;
    @XmlElement(name = "FlagStorno", nillable = true)
    protected String flagStorno;
    @XmlElement(name = "IntestatarioConto", nillable = true)
    protected String intestatarioConto;
    @XmlElement(name = "CFIntestatarioConto", nillable = true)
    protected String cfIntestatarioConto;
    @XmlElement(name = "Sottoscrittore", nillable = true)
    protected String sottoscrittore;
    @XmlElement(name = "CFSottoscrittore", nillable = true)
    protected String cfSottoscrittore;
    @XmlElement(name = "Telefono1", nillable = true)
    protected String telefono1;
    @XmlElement(name = "Telefono2", nillable = true)
    protected String telefono2;
    @XmlElement(name = "Fax", nillable = true)
    protected String fax;
    @XmlElement(name = "Email", nillable = true)
    protected String email;
    @XmlElement(name = "CodAssenzaDatiCat", nillable = true)
    protected String codAssenzaDatiCat;
    @XmlElement(name = "TipoCatasto", nillable = true)
    protected String tipoCatasto;
    @XmlElement(name = "CodComuneCatasto", nillable = true)
    protected String codComuneCatasto;
    @XmlElement(name = "SezioneUrbana", nillable = true)
    protected String sezioneUrbana;
    @XmlElement(name = "Foglio", nillable = true)
    protected String foglio;
    @XmlElement(name = "ParticellaMappale", nillable = true)
    protected String particellaMappale;
    @XmlElement(name = "ParticellaDenom", nillable = true)
    protected String particellaDenom;
    @XmlElement(name = "TipoParticella", nillable = true)
    protected String tipoParticella;
    @XmlElement(name = "Subalterno", nillable = true)
    protected String subalterno;
    @XmlElement(name = "IndRecapitoBol", nillable = true)
    protected IndRecType indRecapitoBol;
    @XmlElement(name = "IndUbicazForn", nillable = true)
    protected IndUbiType indUbicazForn;
    @XmlElement(name = "LConsensi", nillable = true)
    protected ArrayOfCONSENSO lConsensi;
    @XmlElement(name = "Potenza", nillable = true)
    protected String potenza;
    @XmlElement(name = "EmailInvioBolletta", nillable = true)
    protected String emailInvioBolletta;
    @XmlElement(name = "CodLingua", nillable = true)
    protected String codLingua;
    @XmlElement(name = "CustomAttributes", nillable = true)
    protected ArrayOfCustomAttribute customAttributes;
    @XmlElement(name = "TipoStampaBolletta", nillable = true)
    protected String tipoStampaBolletta;
    @XmlElement(name = "DataFirma", nillable = true)
    protected String dataFirma;
    @XmlElement(name = "DataValidit\u00e0Prodotto", nillable = true)
    protected String dataValidit\u00e0Prodotto;
    @XmlElement(name = "CodCodizionePagamento", nillable = true)
    protected String codCodizionePagamento;
    @XmlElement(name = "Agente", nillable = true)
    protected Agente agente;
    @XmlElement(name = "CodTipoProcesso", nillable = true)
    protected String codTipoProcesso;
    
    public ArrayOfFornitura getLForniture() {
        return this.lForniture;
    }
    
    public void setLForniture(final ArrayOfFornitura value) {
        this.lForniture = value;
    }
    
    public String getCodSottotipo() {
        return this.codSottotipo;
    }
    
    public void setCodSottotipo(final String value) {
        this.codSottotipo = value;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public void setNote(final String value) {
        this.note = value;
    }
    
    public String getIBAN() {
        return this.iban;
    }
    
    public void setIBAN(final String value) {
        this.iban = value;
    }
    
    public String getClassificConto() {
        return this.classificConto;
    }
    
    public void setClassificConto(final String value) {
        this.classificConto = value;
    }
    
    public String getFlagStorno() {
        return this.flagStorno;
    }
    
    public void setFlagStorno(final String value) {
        this.flagStorno = value;
    }
    
    public String getIntestatarioConto() {
        return this.intestatarioConto;
    }
    
    public void setIntestatarioConto(final String value) {
        this.intestatarioConto = value;
    }
    
    public String getCFIntestatarioConto() {
        return this.cfIntestatarioConto;
    }
    
    public void setCFIntestatarioConto(final String value) {
        this.cfIntestatarioConto = value;
    }
    
    public String getSottoscrittore() {
        return this.sottoscrittore;
    }
    
    public void setSottoscrittore(final String value) {
        this.sottoscrittore = value;
    }
    
    public String getCFSottoscrittore() {
        return this.cfSottoscrittore;
    }
    
    public void setCFSottoscrittore(final String value) {
        this.cfSottoscrittore = value;
    }
    
    public String getTelefono1() {
        return this.telefono1;
    }
    
    public void setTelefono1(final String value) {
        this.telefono1 = value;
    }
    
    public String getTelefono2() {
        return this.telefono2;
    }
    
    public void setTelefono2(final String value) {
        this.telefono2 = value;
    }
    
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(final String value) {
        this.fax = value;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String value) {
        this.email = value;
    }
    
    public String getCodAssenzaDatiCat() {
        return this.codAssenzaDatiCat;
    }
    
    public void setCodAssenzaDatiCat(final String value) {
        this.codAssenzaDatiCat = value;
    }
    
    public String getTipoCatasto() {
        return this.tipoCatasto;
    }
    
    public void setTipoCatasto(final String value) {
        this.tipoCatasto = value;
    }
    
    public String getCodComuneCatasto() {
        return this.codComuneCatasto;
    }
    
    public void setCodComuneCatasto(final String value) {
        this.codComuneCatasto = value;
    }
    
    public String getSezioneUrbana() {
        return this.sezioneUrbana;
    }
    
    public void setSezioneUrbana(final String value) {
        this.sezioneUrbana = value;
    }
    
    public String getFoglio() {
        return this.foglio;
    }
    
    public void setFoglio(final String value) {
        this.foglio = value;
    }
    
    public String getParticellaMappale() {
        return this.particellaMappale;
    }
    
    public void setParticellaMappale(final String value) {
        this.particellaMappale = value;
    }
    
    public String getParticellaDenom() {
        return this.particellaDenom;
    }
    
    public void setParticellaDenom(final String value) {
        this.particellaDenom = value;
    }
    
    public String getTipoParticella() {
        return this.tipoParticella;
    }
    
    public void setTipoParticella(final String value) {
        this.tipoParticella = value;
    }
    
    public String getSubalterno() {
        return this.subalterno;
    }
    
    public void setSubalterno(final String value) {
        this.subalterno = value;
    }
    
    public IndRecType getIndRecapitoBol() {
        return this.indRecapitoBol;
    }
    
    public void setIndRecapitoBol(final IndRecType value) {
        this.indRecapitoBol = value;
    }
    
    public IndUbiType getIndUbicazForn() {
        return this.indUbicazForn;
    }
    
    public void setIndUbicazForn(final IndUbiType value) {
        this.indUbicazForn = value;
    }
    
    public ArrayOfCONSENSO getLConsensi() {
        return this.lConsensi;
    }
    
    public void setLConsensi(final ArrayOfCONSENSO value) {
        this.lConsensi = value;
    }
    
    public String getPotenza() {
        return this.potenza;
    }
    
    public void setPotenza(final String value) {
        this.potenza = value;
    }
    
    public String getEmailInvioBolletta() {
        return this.emailInvioBolletta;
    }
    
    public void setEmailInvioBolletta(final String value) {
        this.emailInvioBolletta = value;
    }
    
    public String getCodLingua() {
        return this.codLingua;
    }
    
    public void setCodLingua(final String value) {
        this.codLingua = value;
    }
    
    public ArrayOfCustomAttribute getCustomAttributes() {
        return this.customAttributes;
    }
    
    public void setCustomAttributes(final ArrayOfCustomAttribute value) {
        this.customAttributes = value;
    }
    
    public String getTipoStampaBolletta() {
        return this.tipoStampaBolletta;
    }
    
    public void setTipoStampaBolletta(final String value) {
        this.tipoStampaBolletta = value;
    }
    
    public String getDataFirma() {
        return this.dataFirma;
    }
    
    public void setDataFirma(final String value) {
        this.dataFirma = value;
    }
    
    public String getDataValidit\u00e0Prodotto() {
        return this.dataValidit\u00e0Prodotto;
    }
    
    public void setDataValidit\u00e0Prodotto(final String value) {
        this.dataValidit\u00e0Prodotto = value;
    }
    
    public String getCodCodizionePagamento() {
        return this.codCodizionePagamento;
    }
    
    public void setCodCodizionePagamento(final String value) {
        this.codCodizionePagamento = value;
    }
    
    public Agente getAgente() {
        return this.agente;
    }
    
    public void setAgente(final Agente value) {
        this.agente = value;
    }
    
    public String getCodTipoProcesso() {
        return this.codTipoProcesso;
    }
    
    public void setCodTipoProcesso(final String value) {
        this.codTipoProcesso = value;
    }
}
