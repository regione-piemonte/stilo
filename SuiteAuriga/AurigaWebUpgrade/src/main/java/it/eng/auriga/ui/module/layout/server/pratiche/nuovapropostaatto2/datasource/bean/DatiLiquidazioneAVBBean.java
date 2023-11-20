/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.function.bean.DatiLiquidazioneAVBXmlBean;

public class DatiLiquidazioneAVBBean extends DatiLiquidazioneAVBXmlBean {

	private String documentoDiDebito;
	private String beneficiarioSede;
	private String attoImpegno;
	private boolean isInError;
	private String errorMessage;
	private String nomeFile;
	private List<ImputazioneContabileAVBBean> listaImputazioneContabile;
	private List<RifSpesaEntrataAVBBean> listaRifSpesaEntrata;
	private List<DisimpegnoAVBBean> listaDisimpegni;
	private List<String> listaUriXlsToDelete;
	private boolean isUploaded;

	public String getDocumentoDiDebito() {
		return documentoDiDebito;
	}

	public void setDocumentoDiDebito(String documentoDiDebito) {
		this.documentoDiDebito = documentoDiDebito;
	}

	public String getBeneficiarioSede() {
		return beneficiarioSede;
	}

	public void setBeneficiarioSede(String beneficiarioSede) {
		this.beneficiarioSede = beneficiarioSede;
	}

	public String getAttoImpegno() {
		return attoImpegno;
	}

	public void setAttoImpegno(String attoImpegno) {
		this.attoImpegno = attoImpegno;
	}

	public boolean isInError() {
		return isInError;
	}

	public void setInError(boolean isInError) {
		this.isInError = isInError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public List<ImputazioneContabileAVBBean> getListaImputazioneContabile() {
		return listaImputazioneContabile;
	}

	public void setListaImputazioneContabile(List<ImputazioneContabileAVBBean> listaImputazioneContabile) {
		this.listaImputazioneContabile = listaImputazioneContabile;
	}

	public List<RifSpesaEntrataAVBBean> getListaRifSpesaEntrata() {
		return listaRifSpesaEntrata;
	}

	public void setListaRifSpesaEntrata(List<RifSpesaEntrataAVBBean> listaRifSpesaEntrata) {
		this.listaRifSpesaEntrata = listaRifSpesaEntrata;
	}

	public List<DisimpegnoAVBBean> getListaDisimpegni() {
		return listaDisimpegni;
	}

	public void setListaDisimpegni(List<DisimpegnoAVBBean> listaDisimpegni) {
		this.listaDisimpegni = listaDisimpegni;
	}

	public List<String> getListaUriXlsToDelete() {
		return listaUriXlsToDelete;
	}

	public void setListaUriXlsToDelete(List<String> listaUriXlsToDelete) {
		this.listaUriXlsToDelete = listaUriXlsToDelete;
	}

	public boolean isUploaded() {
		return isUploaded;
	}

	public void setUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}

}
