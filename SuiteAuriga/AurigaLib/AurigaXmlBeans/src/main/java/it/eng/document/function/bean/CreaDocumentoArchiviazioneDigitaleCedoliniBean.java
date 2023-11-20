/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class CreaDocumentoArchiviazioneDigitaleCedoliniBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = 2003736494512929867L;

	@XmlVariabile(nome = "COGNOME_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String cognomeUp;
	@XmlVariabile(nome = "NOME_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nomeUp;
	@XmlVariabile(nome = "MATRICOLA_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String matricolaUp;
	@XmlVariabile(nome = "COD_FISCALE_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codFiscaleUp;
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "DT_NASCITA_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dataNascitaUp;
	@XmlVariabile(nome = "REPARTO_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String repartoUp;
	@XmlVariabile(nome = "ANNO_RIF_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private Integer annoUp;
	@XmlVariabile(nome = "MESE_RIF_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private Integer meseUp;
	// @XmlVariabile(nome = "TIPOLOGIA_DOCUMENTO", tipo =
	// TipoVariabile.SEMPLICE)
	// private String tipologiaDocumento;
	// @XmlVariabile(nome = "TIPOLOGIA_PERSONALE", tipo =
	// TipoVariabile.SEMPLICE)
	// private String tipologiaPersonale;
	@XmlVariabile(nome = "TIPO_CONTRATTO_LAVORO_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipologiaContrattoUp;
	@XmlVariabile(nome = "CATEGORIA_INQUADRAMENTO_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String categoriaInquadramentoUp;
	@TipoData(tipo = Tipo.DATA_ESTESA)
	@XmlVariabile(nome = "DT_CREAZIONE_UP_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dataCreazioneUp;

	public String getCognomeUp() {
		return cognomeUp;
	}

	public void setCognomeUp(String cognomeUp) {
		this.cognomeUp = cognomeUp;
	}

	public String getNomeUp() {
		return nomeUp;
	}

	public void setNomeUp(String nomeUp) {
		this.nomeUp = nomeUp;
	}

	public String getMatricolaUp() {
		return matricolaUp;
	}

	public void setMatricolaUp(String matricolaUp) {
		this.matricolaUp = matricolaUp;
	}

	public String getCodFiscaleUp() {
		return codFiscaleUp;
	}

	public void setCodFiscaleUp(String codFiscaleUp) {
		this.codFiscaleUp = codFiscaleUp;
	}

	public Date getDataNascitaUp() {
		return dataNascitaUp;
	}

	public void setDataNascitaUp(Date dataNascitaUp) {
		this.dataNascitaUp = dataNascitaUp;
	}

	public String getRepartoUp() {
		return repartoUp;
	}

	public void setRepartoUp(String repartoUp) {
		this.repartoUp = repartoUp;
	}

	public Integer getAnnoUp() {
		return annoUp;
	}

	public void setAnnoUp(Integer annoUp) {
		this.annoUp = annoUp;
	}

	public Integer getMeseUp() {
		return meseUp;
	}

	public void setMeseUp(Integer meseUp) {
		this.meseUp = meseUp;
	}

	public String getTipologiaContrattoUp() {
		return tipologiaContrattoUp;
	}

	public void setTipologiaContrattoUp(String tipologiaContrattoUp) {
		this.tipologiaContrattoUp = tipologiaContrattoUp;
	}

	public String getCategoriaInquadramentoUp() {
		return categoriaInquadramentoUp;
	}

	public void setCategoriaInquadramentoUp(String categoriaInquadramentoUp) {
		this.categoriaInquadramentoUp = categoriaInquadramentoUp;
	}

	public Date getDataCreazioneUp() {
		return dataCreazioneUp;
	}

	public void setDataCreazioneUp(Date dataCreazioneUp) {
		this.dataCreazioneUp = dataCreazioneUp;
	}

}
