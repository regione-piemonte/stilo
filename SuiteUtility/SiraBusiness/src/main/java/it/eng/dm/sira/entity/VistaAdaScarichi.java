package it.eng.dm.sira.entity;

// Generated 23-gen-2014 14.37.53 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VistaAdaScarichi generated by hbm2java
 */
@Entity
@Table(name = "VISTA_ADA_SCARICHI")
public class VistaAdaScarichi implements java.io.Serializable {

	private static final long serialVersionUID = 5351113083101598993L;

	private BigDecimal idAda;

	private String idEcg;

	private String codiceAdaEnte;

	private Date dataAda;

	private String descrizione;

	private Date dataNotificaAda;

	private Date dataScadenzaAda;

	private String numProt;

	private String tipologiaAda;

	private String denominazioneEnte;

	private String descrizioneOggetto;

	private String tipoDocumento;

	private String denoTipoProvvedimento;

	private String denoRifnorm;

	private String denoNorma;

	private String descrizioneRifnorm;

	private BigDecimal numeroAllegatiFonte;

	private String intestatarioFonte;

	private String note;

	private BigDecimal oggettoAda;

	private BigDecimal riferimentoNormativo;

	private BigDecimal tipoProvvedimento;

	private BigDecimal idAdaCollegato;

	private BigDecimal idCcostIntestazione;

	private BigDecimal idSfIntestatario;

	private BigDecimal tipoAda;

	public VistaAdaScarichi() {
	}
	
	@Id
	@Column(name = "ID_ADA", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdAda() {
		return this.idAda;
	}

	public void setIdAda(BigDecimal idAda) {
		this.idAda = idAda;
	}

	@Column(name = "ID_ECG")
	public String getIdEcg() {
		return this.idEcg;
	}

	public void setIdEcg(String idEcg) {
		this.idEcg = idEcg;
	}

	@Column(name = "CODICE_ADA_ENTE")
	public String getCodiceAdaEnte() {
		return this.codiceAdaEnte;
	}

	public void setCodiceAdaEnte(String codiceAdaEnte) {
		this.codiceAdaEnte = codiceAdaEnte;
	}

	@Column(name = "DATA_ADA", nullable = false, length = 7)
	public Date getDataAda() {
		return this.dataAda;
	}

	public void setDataAda(Date dataAda) {
		this.dataAda = dataAda;
	}

	@Column(name = "DESCRIZIONE", nullable = false)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "DATA_NOTIFICA_ADA", length = 7)
	public Date getDataNotificaAda() {
		return this.dataNotificaAda;
	}

	public void setDataNotificaAda(Date dataNotificaAda) {
		this.dataNotificaAda = dataNotificaAda;
	}

	@Column(name = "DATA_SCADENZA_ADA", length = 7)
	public Date getDataScadenzaAda() {
		return this.dataScadenzaAda;
	}

	public void setDataScadenzaAda(Date dataScadenzaAda) {
		this.dataScadenzaAda = dataScadenzaAda;
	}

	@Column(name = "NUM_PROT", length = 4000)
	public String getNumProt() {
		return this.numProt;
	}

	public void setNumProt(String numProt) {
		this.numProt = numProt;
	}

	@Column(name = "TIPOLOGIA_ADA", length = 4000)
	public String getTipologiaAda() {
		return this.tipologiaAda;
	}

	public void setTipologiaAda(String tipologiaAda) {
		this.tipologiaAda = tipologiaAda;
	}

	@Column(name = "DENOMINAZIONE_ENTE")
	public String getDenominazioneEnte() {
		return this.denominazioneEnte;
	}

	public void setDenominazioneEnte(String denominazioneEnte) {
		this.denominazioneEnte = denominazioneEnte;
	}

	@Column(name = "DESCRIZIONE_OGGETTO", nullable = false)
	public String getDescrizioneOggetto() {
		return this.descrizioneOggetto;
	}

	public void setDescrizioneOggetto(String descrizioneOggetto) {
		this.descrizioneOggetto = descrizioneOggetto;
	}

	@Column(name = "TIPO_DOCUMENTO", nullable = false)
	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@Column(name = "DENO_TIPO_PROVVEDIMENTO", nullable = false)
	public String getDenoTipoProvvedimento() {
		return this.denoTipoProvvedimento;
	}

	public void setDenoTipoProvvedimento(String denoTipoProvvedimento) {
		this.denoTipoProvvedimento = denoTipoProvvedimento;
	}

	@Column(name = "DENO_RIFNORM")
	public String getDenoRifnorm() {
		return this.denoRifnorm;
	}

	public void setDenoRifnorm(String denoRifnorm) {
		this.denoRifnorm = denoRifnorm;
	}

	@Column(name = "DENO_NORMA")
	public String getDenoNorma() {
		return this.denoNorma;
	}

	public void setDenoNorma(String denoNorma) {
		this.denoNorma = denoNorma;
	}

	@Column(name = "DESCRIZIONE_RIFNORM", length = 511)
	public String getDescrizioneRifnorm() {
		return this.descrizioneRifnorm;
	}

	public void setDescrizioneRifnorm(String descrizioneRifnorm) {
		this.descrizioneRifnorm = descrizioneRifnorm;
	}

	@Column(name = "NUMERO_ALLEGATI_FONTE", precision = 22, scale = 0)
	public BigDecimal getNumeroAllegatiFonte() {
		return this.numeroAllegatiFonte;
	}

	public void setNumeroAllegatiFonte(BigDecimal numeroAllegatiFonte) {
		this.numeroAllegatiFonte = numeroAllegatiFonte;
	}

	@Column(name = "INTESTATARIO_FONTE", length = 511)
	public String getIntestatarioFonte() {
		return this.intestatarioFonte;
	}

	public void setIntestatarioFonte(String intestatarioFonte) {
		this.intestatarioFonte = intestatarioFonte;
	}

	@Column(name = "NOTE")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "OGGETTO_ADA", nullable = false, precision = 22, scale = 0)
	public BigDecimal getOggettoAda() {
		return this.oggettoAda;
	}

	public void setOggettoAda(BigDecimal oggettoAda) {
		this.oggettoAda = oggettoAda;
	}

	@Column(name = "RIFERIMENTO_NORMATIVO", precision = 22, scale = 0)
	public BigDecimal getRiferimentoNormativo() {
		return this.riferimentoNormativo;
	}

	public void setRiferimentoNormativo(BigDecimal riferimentoNormativo) {
		this.riferimentoNormativo = riferimentoNormativo;
	}

	@Column(name = "TIPO_PROVVEDIMENTO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getTipoProvvedimento() {
		return this.tipoProvvedimento;
	}

	public void setTipoProvvedimento(BigDecimal tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}

	@Column(name = "ID_ADA_COLLEGATO", precision = 22, scale = 0)
	public BigDecimal getIdAdaCollegato() {
		return this.idAdaCollegato;
	}

	public void setIdAdaCollegato(BigDecimal idAdaCollegato) {
		this.idAdaCollegato = idAdaCollegato;
	}

	@Column(name = "ID_CCOST_INTESTAZIONE", precision = 22, scale = 0)
	public BigDecimal getIdCcostIntestazione() {
		return this.idCcostIntestazione;
	}

	public void setIdCcostIntestazione(BigDecimal idCcostIntestazione) {
		this.idCcostIntestazione = idCcostIntestazione;
	}

	@Column(name = "ID_SF_INTESTATARIO", precision = 22, scale = 0)
	public BigDecimal getIdSfIntestatario() {
		return this.idSfIntestatario;
	}

	public void setIdSfIntestatario(BigDecimal idSfIntestatario) {
		this.idSfIntestatario = idSfIntestatario;
	}

	@Column(name = "TIPO_ADA", precision = 22, scale = 0)
	public BigDecimal getTipoAda() {
		return this.tipoAda;
	}

	public void setTipoAda(BigDecimal tipoAda) {
		this.tipoAda = tipoAda;
	}

}
