/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */



import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


 
@Entity
@Table(name = "DMV_ESITI_JOB")
public class DmvEsitiJob implements java.io.Serializable {

	private static final long serialVersionUID = -2306906309867877818L;

	private String mandate;

	private String fattSocieta;

	private String numeroDocumento;

	private String dataRegistrazione;

	private BigDecimal chiaveDigidoc;

	private String codiceStato;

	private String dataCreazioneDigidoc;

	private String oraCreazioneDigidoc;

	private String dataModificaDigidoc;

	private String oraModificaDigidoc;

	private String fattDtReg;

	private String fattTipoDoc;

	private String fattEpc;

	private String fattDfc;
	
	private String organizzazioneCommerciale;
	
	private String canaleDiDistribuzione;
	
	private String dataInoltroSdi;
	
	private String oraInoltroSdi;
	
	private String dataFeedbackSdi;
	
	private String oraFeedbackSdi;
	
	private String linkPdf;
	
	private String linkXml;
	
	private String testoNotifica;
	
	private String progDaSap;
	
	private String nomeLotto;
	

	
	public DmvEsitiJob() {
	}


	@Column(name = "MANDANTE", length = 4000)
	public String getMandate() {
		return mandate;
	}



	public void setMandate(String mandate) {
		this.mandate = mandate;
	}


	@Column(name = "FATT_SOCIETA", length = 4000)
	public String getFattSocieta() {
		return fattSocieta;
	}



	public void setFattSocieta(String fattSocieta) {
		this.fattSocieta = fattSocieta;
	}


	@Column(name = "NUMERO_DOCUMENTO", length = 4000)
	public String getNumeroDocumento() {
		return numeroDocumento;
	}



	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}


	@Column(name = "DATA_REGISTRAZIONE", length = 4000)
	public String getDataRegistrazione() {
		return dataRegistrazione;
	}



	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	@Id
	@Column(name = "chiave_digidoc", nullable = false, precision = 22, scale = 0)
	public BigDecimal getChiaveDigidoc() {
		return chiaveDigidoc;
	}



	public void setChiaveDigidoc(BigDecimal chiaveDigidoc) {
		this.chiaveDigidoc = chiaveDigidoc;
	}


	@Column(name = "codice_stato", length = 4000)
	public String getCodiceStato() {
		return codiceStato;
	}



	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}


	@Column(name = "data_creazione_digidoc", length = 4000)
	public String getDataCreazioneDigidoc() {
		return dataCreazioneDigidoc;
	}



	public void setDataCreazioneDigidoc(String dataCreazioneDigidoc) {
		this.dataCreazioneDigidoc = dataCreazioneDigidoc;
	}


	@Column(name = "ora_creazione_digidoc", length = 4000)
	public String getOraCreazioneDigidoc() {
		return oraCreazioneDigidoc;
	}



	public void setOraCreazioneDigidoc(String oraCreazioneDigidoc) {
		this.oraCreazioneDigidoc = oraCreazioneDigidoc;
	}


	@Column(name = "data_modifica_digidoc", length = 4000)
	public String getDataModificaDigidoc() {
		return dataModificaDigidoc;
	}



	public void setDataModificaDigidoc(String dataModificaDigidoc) {
		this.dataModificaDigidoc = dataModificaDigidoc;
	}


	@Column(name = "ora_modifica_digidoc", length = 4000)
	public String getOraModificaDigidoc() {
		return oraModificaDigidoc;
	}



	public void setOraModificaDigidoc(String oraModificaDigidoc) {
		this.oraModificaDigidoc = oraModificaDigidoc;
	}


	@Column(name = "FATT_DTREG", length = 4000)
	public String getFattDtReg() {
		return fattDtReg;
	}



	public void setFattDtReg(String fattDtReg) {
		this.fattDtReg = fattDtReg;
	}


	@Column(name = "FATT_TIPODOC", length = 4000)
	public String getFattTipoDoc() {
		return fattTipoDoc;
	}



	public void setFattTipoDoc(String fattTipoDoc) {
		this.fattTipoDoc = fattTipoDoc;
	}


	@Column(name = "FATT_EPC", length = 4000)
	public String getFattEpc() {
		return fattEpc;
	}



	public void setFattEpc(String fattEpc) {
		this.fattEpc = fattEpc;
	}


	@Column(name = "FATT_DFC", length = 4000)
	public String getFattDfc() {
		return fattDfc;
	}



	public void setFattDfc(String fattDfc) {
		this.fattDfc = fattDfc;
	}


	@Column(name = "ORGANIZZAZIONE_COMMERCIALE", length = 4000)
	public String getOrganizzazioneCommerciale() {
		return organizzazioneCommerciale;
	}



	public void setOrganizzazioneCommerciale(String organizzazioneCommerciale) {
		this.organizzazioneCommerciale = organizzazioneCommerciale;
	}


	@Column(name = "CANALE_DI_DISTRIBUZIONE", length = 4000)
	public String getCanaleDiDistribuzione() {
		return canaleDiDistribuzione;
	}



	public void setCanaleDiDistribuzione(String canaleDiDistribuzione) {
		this.canaleDiDistribuzione = canaleDiDistribuzione;
	}


	@Column(name = "data_inoltro_sdi", length = 4000)
	public String getDataInoltroSdi() {
		return dataInoltroSdi;
	}



	public void setDataInoltroSdi(String dataInoltroSdi) {
		this.dataInoltroSdi = dataInoltroSdi;
	}


	@Column(name = "ora_inoltro_sdi", length = 4000)
	public String getOraInoltroSdi() {
		return oraInoltroSdi;
	}



	public void setOraInoltroSdi(String oraInoltroSdi) {
		this.oraInoltroSdi = oraInoltroSdi;
	}


	@Column(name = "data_feedback_sdi", length = 4000)
	public String getDataFeedbackSdi() {
		return dataFeedbackSdi;
	}



	public void setDataFeedbackSdi(String dataFeedbackSdi) {
		this.dataFeedbackSdi = dataFeedbackSdi;
	}


	@Column(name = "ora_feedback_sdi", length = 4000)
	public String getOraFeedbackSdi() {
		return oraFeedbackSdi;
	}



	public void setOraFeedbackSdi(String oraFeedbackSdi) {
		this.oraFeedbackSdi = oraFeedbackSdi;
	}


	@Column(name = "link_pdf", length = 4000)
	public String getLinkPdf() {
		return linkPdf;
	}



	public void setLinkPdf(String linkPdf) {
		this.linkPdf = linkPdf;
	}


	@Column(name = "link_xml", length = 4000)
	public String getLinkXml() {
		return linkXml;
	}



	public void setLinkXml(String linkXml) {
		this.linkXml = linkXml;
	}


	@Column(name = "testo_notifica", length = 4000)
	public String getTestoNotifica() {
		return testoNotifica;
	}



	public void setTestoNotifica(String testoNotifica) {
		this.testoNotifica = testoNotifica;
	}


	@Column(name = "progr_da_sap", length = 4000)
	public String getProgDaSap() {
		return progDaSap;
	}



	public void setProgDaSap(String progDaSap) {
		this.progDaSap = progDaSap;
	}


	@Column(name = "nome_lotto", length = 4000)
	public String getNomeLotto() {
		return nomeLotto;
	}



	public void setNomeLotto(String nomeLotto) {
		this.nomeLotto = nomeLotto;
	}

	
    

	

}
