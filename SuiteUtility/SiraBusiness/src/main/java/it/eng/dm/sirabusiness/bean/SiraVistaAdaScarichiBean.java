package it.eng.dm.sirabusiness.bean;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class SiraVistaAdaScarichiBean extends AbstractBean implements Serializable {

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

	public SiraVistaAdaScarichiBean() {
	}

	public BigDecimal getIdAda() {
		return idAda;
	}

	public void setIdAda(BigDecimal idAda) {
		this.idAda = idAda;
	}

	public String getIdEcg() {
		return idEcg;
	}

	public void setIdEcg(String idEcg) {
		this.idEcg = idEcg;
	}

	public String getCodiceAdaEnte() {
		return codiceAdaEnte;
	}

	public void setCodiceAdaEnte(String codiceAdaEnte) {
		this.codiceAdaEnte = codiceAdaEnte;
	}

	public Date getDataAda() {
		return dataAda;
	}

	public void setDataAda(Date dataAda) {
		this.dataAda = dataAda;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataNotificaAda() {
		return dataNotificaAda;
	}

	public void setDataNotificaAda(Date dataNotificaAda) {
		this.dataNotificaAda = dataNotificaAda;
	}

	public Date getDataScadenzaAda() {
		return dataScadenzaAda;
	}

	public void setDataScadenzaAda(Date dataScadenzaAda) {
		this.dataScadenzaAda = dataScadenzaAda;
	}

	public String getNumProt() {
		return numProt;
	}

	public void setNumProt(String numProt) {
		this.numProt = numProt;
	}

	public String getTipologiaAda() {
		return tipologiaAda;
	}

	public void setTipologiaAda(String tipologiaAda) {
		this.tipologiaAda = tipologiaAda;
	}

	public String getDenominazioneEnte() {
		return denominazioneEnte;
	}

	public void setDenominazioneEnte(String denominazioneEnte) {
		this.denominazioneEnte = denominazioneEnte;
	}

	public String getDescrizioneOggetto() {
		return descrizioneOggetto;
	}

	public void setDescrizioneOggetto(String descrizioneOggetto) {
		this.descrizioneOggetto = descrizioneOggetto;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDenoTipoProvvedimento() {
		return denoTipoProvvedimento;
	}

	public void setDenoTipoProvvedimento(String denoTipoProvvedimento) {
		this.denoTipoProvvedimento = denoTipoProvvedimento;
	}

	public String getDenoRifnorm() {
		return denoRifnorm;
	}

	public void setDenoRifnorm(String denoRifnorm) {
		this.denoRifnorm = denoRifnorm;
	}

	public String getDenoNorma() {
		return denoNorma;
	}

	public void setDenoNorma(String denoNorma) {
		this.denoNorma = denoNorma;
	}

	public String getDescrizioneRifnorm() {
		return descrizioneRifnorm;
	}

	public void setDescrizioneRifnorm(String descrizioneRifnorm) {
		this.descrizioneRifnorm = descrizioneRifnorm;
	}

	public BigDecimal getNumeroAllegatiFonte() {
		return numeroAllegatiFonte;
	}

	public void setNumeroAllegatiFonte(BigDecimal numeroAllegatiFonte) {
		this.numeroAllegatiFonte = numeroAllegatiFonte;
	}

	public String getIntestatarioFonte() {
		return intestatarioFonte;
	}

	public void setIntestatarioFonte(String intestatarioFonte) {
		this.intestatarioFonte = intestatarioFonte;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getOggettoAda() {
		return oggettoAda;
	}

	public void setOggettoAda(BigDecimal oggettoAda) {
		this.oggettoAda = oggettoAda;
	}

	public BigDecimal getRiferimentoNormativo() {
		return riferimentoNormativo;
	}

	public void setRiferimentoNormativo(BigDecimal riferimentoNormativo) {
		this.riferimentoNormativo = riferimentoNormativo;
	}

	public BigDecimal getTipoProvvedimento() {
		return tipoProvvedimento;
	}

	public void setTipoProvvedimento(BigDecimal tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}

	public BigDecimal getIdAdaCollegato() {
		return idAdaCollegato;
	}

	public void setIdAdaCollegato(BigDecimal idAdaCollegato) {
		this.idAdaCollegato = idAdaCollegato;
	}

	public BigDecimal getIdCcostIntestazione() {
		return idCcostIntestazione;
	}

	public void setIdCcostIntestazione(BigDecimal idCcostIntestazione) {
		this.idCcostIntestazione = idCcostIntestazione;
	}

	public BigDecimal getIdSfIntestatario() {
		return idSfIntestatario;
	}

	public void setIdSfIntestatario(BigDecimal idSfIntestatario) {
		this.idSfIntestatario = idSfIntestatario;
	}

	public BigDecimal getTipoAda() {
		return tipoAda;
	}

	public void setTipoAda(BigDecimal tipoAda) {
		this.tipoAda = tipoAda;
	}

}
