/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EditorHtmlBean extends EventoCustomBean{

	private String descrizione;
	private String idDocAssTask;
	private String uriModAssDocTask;
	private String idDocTipDocTask;
	private String uriUltimaVersDocTask;
	private String mimetypeDocTipAssTask;
	private String nomeFileDocTipAssTask;
	private String uriPdfGenerato;
	private String uriHtmlGenerato;
	private String smartId;
	
	public String getIdDocAssTask() {
		return idDocAssTask;
	}
	public String getUriModAssDocTask() {
		return uriModAssDocTask;
	}
	public String getIdDocTipDocTask() {
		return idDocTipDocTask;
	}
	public String getUriUltimaVersDocTask() {
		return uriUltimaVersDocTask;
	}
	public String getMimetypeDocTipAssTask() {
		return mimetypeDocTipAssTask;
	}
	public String getNomeFileDocTipAssTask() {
		return nomeFileDocTipAssTask;
	}
	public void setIdDocAssTask(String idDocAssTask) {
		this.idDocAssTask = idDocAssTask;
	}
	public void setUriModAssDocTask(String uriModAssDocTask) {
		this.uriModAssDocTask = uriModAssDocTask;
	}
	public void setIdDocTipDocTask(String idDocTipDocTask) {
		this.idDocTipDocTask = idDocTipDocTask;
	}
	public void setUriUltimaVersDocTask(String uriUltimaVersDocTask) {
		this.uriUltimaVersDocTask = uriUltimaVersDocTask;
	}
	public void setMimetypeDocTipAssTask(String mimetypeDocTipAssTask) {
		this.mimetypeDocTipAssTask = mimetypeDocTipAssTask;
	}
	public void setNomeFileDocTipAssTask(String nomeFileDocTipAssTask) {
		this.nomeFileDocTipAssTask = nomeFileDocTipAssTask;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getUriPdfGenerato() {
		return uriPdfGenerato;
	}
	public void setUriPdfGenerato(String uriPdfGenerato) {
		this.uriPdfGenerato = uriPdfGenerato;
	}
	public String getSmartId() {
		return smartId;
	}
	public void setSmartId(String smartId) {
		this.smartId = smartId;
	}
	public String getUriHtmlGenerato() {
		return uriHtmlGenerato;
	}
	public void setUriHtmlGenerato(String uriHtmlGenerato) {
		this.uriHtmlGenerato = uriHtmlGenerato;
	}
	
}
