/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class DestinatariXFileXlsRegMultiplaUscitaBean {
	
	private boolean isInError;
	private String errorMessage;
	private String nomeFile;
	private boolean isUploaded;
	List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestinatari;
	List<DestinatarioRegistrazioneMultiplaUscitaInError> listaDestintariInError;
	
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
	public boolean isUploaded() {
		return isUploaded;
	}
	public void setUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}
	public List<DestinatariRegistrazioneMultiplaUscitaXmlBean> getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
	public List<DestinatarioRegistrazioneMultiplaUscitaInError> getListaDestintariInError() {
		return listaDestintariInError;
	}
	public void setListaDestintariInError(List<DestinatarioRegistrazioneMultiplaUscitaInError> listaDestintariInError) {
		this.listaDestintariInError = listaDestintariInError;
	}

}
