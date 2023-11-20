/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class EditorAppletBean extends AttProcBean {

//	private String idDocAssTask;
//	private String uriModAssDocTask;
//	private String idDocTipDocTask;
//	private String uriUltimaVersDocTask;
//	private String mimetypeDocTipAssTask;
//	private String nomeFileDocTipAssTask;
	private String uriPdfGenerato;
	private String filenamePdfGenerato;	
	private String improntaPdf;
	private String uriDoc;
	private String impronta;
	private String desEvento;
	private String messaggio;
	private MimeTypeFirmaBean infoFileUltimaVersDocTask;
	
//	public String getIdDocAssTask() {
//		return idDocAssTask;
//	}
//	public void setIdDocAssTask(String idDocAssTask) {
//		this.idDocAssTask = idDocAssTask;
//	}
//	public String getUriModAssDocTask() {
//		return uriModAssDocTask;
//	}
//	public void setUriModAssDocTask(String uriModAssDocTask) {
//		this.uriModAssDocTask = uriModAssDocTask;
//	}
//	public String getIdDocTipDocTask() {
//		return idDocTipDocTask;
//	}
//	public void setIdDocTipDocTask(String idDocTipDocTask) {
//		this.idDocTipDocTask = idDocTipDocTask;
//	}
//	public String getUriUltimaVersDocTask() {
//		return uriUltimaVersDocTask;
//	}
//	public void setUriUltimaVersDocTask(String uriUltimaVersDocTask) {
//		this.uriUltimaVersDocTask = uriUltimaVersDocTask;
//	}
//	public String getMimetypeDocTipAssTask() {
//		return mimetypeDocTipAssTask;
//	}
//	public void setMimetypeDocTipAssTask(String mimetypeDocTipAssTask) {
//		this.mimetypeDocTipAssTask = mimetypeDocTipAssTask;
//	}
//	public String getNomeFileDocTipAssTask() {
//		return nomeFileDocTipAssTask;
//	}
//	public void setNomeFileDocTipAssTask(String nomeFileDocTipAssTask) {
//		this.nomeFileDocTipAssTask = nomeFileDocTipAssTask;
//	}
	public String getUriPdfGenerato() {
		return uriPdfGenerato;
	}
	public void setUriPdfGenerato(String uriPdfGenerato) {
		this.uriPdfGenerato = uriPdfGenerato;
	}
	public String getImprontaPdf() {
		return improntaPdf;
	}
	public void setImprontaPdf(String improntaPdf) {
		this.improntaPdf = improntaPdf;
	}
	public String getUriDoc() {
		return uriDoc;
	}
	public void setUriDoc(String uriDoc) {
		this.uriDoc = uriDoc;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getDesEvento() {
		return desEvento;
	}
	public void setDesEvento(String desEvento) {
		this.desEvento = desEvento;
	}
	public String getFilenamePdfGenerato() {
		return filenamePdfGenerato;
	}
	public void setFilenamePdfGenerato(String filenamePdfGenerato) {
		this.filenamePdfGenerato = filenamePdfGenerato;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public MimeTypeFirmaBean getInfoFileUltimaVersDocTask() {
		return infoFileUltimaVersDocTask;
	}	
	public void setInfoFileUltimaVersDocTask(MimeTypeFirmaBean infoFileUltimaVersDocTask) {
		this.infoFileUltimaVersDocTask = infoFileUltimaVersDocTask;
	}

}
