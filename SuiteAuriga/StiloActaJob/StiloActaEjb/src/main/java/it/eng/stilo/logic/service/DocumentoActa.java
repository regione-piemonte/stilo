/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DocumentoActa {

	private String idDocumento;
	private String oggetto;
	private String autoreFisico;
	private String autoreGiuridico;
	private String scrittore;
	private String origineInterna;
	private String datiPersonali;
	private String datiSensibili;
	private String datiRiservati;
	private String paroleChiave;
	private String dataDocCronica;
	private String numRepertorio;
	private String indiceClassificazione;
	private String changeToken;
	private String docConAllegati;
	
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getAutoreFisico() {
		return autoreFisico;
	}
	public void setAutoreFisico(String autoreFisico) {
		this.autoreFisico = autoreFisico;
	}
	public String getAutoreGiuridico() {
		return autoreGiuridico;
	}
	public void setAutoreGiuridico(String autoreGiuridico) {
		this.autoreGiuridico = autoreGiuridico;
	}
	public String getScrittore() {
		return scrittore;
	}
	public void setScrittore(String scrittore) {
		this.scrittore = scrittore;
	}
	public String getOrigineInterna() {
		return origineInterna;
	}
	public void setOrigineInterna(String origineInterna) {
		this.origineInterna = origineInterna;
	}
	public String getDatiPersonali() {
		return datiPersonali;
	}
	public void setDatiPersonali(String datiPersonali) {
		this.datiPersonali = datiPersonali;
	}
	public String getDatiSensibili() {
		return datiSensibili;
	}
	public void setDatiSensibili(String datiSensibili) {
		this.datiSensibili = datiSensibili;
	}
	public String getDatiRiservati() {
		return datiRiservati;
	}
	public void setDatiRiservati(String datiRiservati) {
		this.datiRiservati = datiRiservati;
	}
	public String getParoleChiave() {
		return paroleChiave;
	}
	public void setParoleChiave(String paroleChiave) {
		this.paroleChiave = paroleChiave;
	}
	public String getDataDocCronica() {
		return dataDocCronica;
	}
	public void setDataDocCronica(String dataDocCronica) {
		this.dataDocCronica = dataDocCronica;
	}
	public String getNumRepertorio() {
		return numRepertorio;
	}
	public void setNumRepertorio(String numRepertorio) {
		this.numRepertorio = numRepertorio;
	}
	
	public String getIndiceClassificazione() {
		return indiceClassificazione;
	}
	public void setIndiceClassificazione(String indiceClassificazione) {
		this.indiceClassificazione = indiceClassificazione;
	}
	
	public String getChangeToken() {
		return changeToken;
	}
	public void setChangeToken(String changeToken) {
		this.changeToken = changeToken;
	}
	
	
	public String getDocConAllegati() {
		return docConAllegati;
	}
	public void setDocConAllegati(String docConAllegati) {
		this.docConAllegati = docConAllegati;
	}
	
	@Override
	public String toString() {
		return "DocumentoActa [idDocumento=" + idDocumento + ", oggetto=" + oggetto + ", autoreFisico=" + autoreFisico
				+ ", autoreGiuridico=" + autoreGiuridico + ", scrittore=" + scrittore + ", origineInterna="
				+ origineInterna + ", datiPersonali=" + datiPersonali + ", datiSensibili=" + datiSensibili
				+ ", datiRiservati=" + datiRiservati + ", paroleChiave=" + paroleChiave + ", dataDocCronica="
				+ dataDocCronica + ", numRepertorio=" + numRepertorio + ", indiceClassificazione="
				+ indiceClassificazione + ", changeToken=" + changeToken + ", docConAllegati=" + docConAllegati + "]";
	}
	
	
	
}
