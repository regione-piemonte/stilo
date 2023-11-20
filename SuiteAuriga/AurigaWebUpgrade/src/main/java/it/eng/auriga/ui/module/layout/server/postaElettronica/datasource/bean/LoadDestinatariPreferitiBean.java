/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author mzanetti
 * Bean con la finalità da utilizzare per il caricamento dei preferiti dell'utente
 */

public class LoadDestinatariPreferitiBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idUd;
	private List<AzioneRapidaBean> listaAzioniRapide;	
//	private List<DestinatarioPreferitoBean> listaUtentiPreferitiMail;
//	private List<DestinatarioPreferitoBean> listaUOPreferiteMail;
//	private List<DestinatarioPreferitoBean> listaUtentiPreferitiDoc;
//	private List<DestinatarioPreferitoBean> listaUOPreferiteDoc;
//	private List<DestinatarioPreferitoBean> listaUtentiPreferitiFolder;
//	private List<DestinatarioPreferitoBean> listaUOPreferiteFolder;	
	private HashMap<String , List<DestinatarioPreferitoBean>> mappaUtentiPreferiti;
	private HashMap<String , List<DestinatarioPreferitoBean>> mappaUOPreferite;
	private String errorMessage;
	private Boolean success;
	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public List<AzioneRapidaBean> getListaAzioniRapide() {
		return listaAzioniRapide;
	}
	public void setListaAzioniRapide(List<AzioneRapidaBean> listaAzioniRapide) {
		this.listaAzioniRapide = listaAzioniRapide;
	}
//	public List<DestinatarioPreferitoBean> getListaUtentiPreferitiMail() {
//		return listaUtentiPreferitiMail;
//	}
//	public void setListaUtentiPreferitiMail(List<DestinatarioPreferitoBean> listaUtentiPreferitiMail) {
//		this.listaUtentiPreferitiMail = listaUtentiPreferitiMail;
//	}
//	public List<DestinatarioPreferitoBean> getListaUOPreferiteMail() {
//		return listaUOPreferiteMail;
//	}
//	public void setListaUOPreferiteMail(List<DestinatarioPreferitoBean> listaUOPreferiteMail) {
//		this.listaUOPreferiteMail = listaUOPreferiteMail;
//	}
//	public List<DestinatarioPreferitoBean> getListaUtentiPreferitiDoc() {
//		return listaUtentiPreferitiDoc;
//	}
//	public void setListaUtentiPreferitiDoc(List<DestinatarioPreferitoBean> listaUtentiPreferitiDoc) {
//		this.listaUtentiPreferitiDoc = listaUtentiPreferitiDoc;
//	}
//	public List<DestinatarioPreferitoBean> getListaUOPreferiteDoc() {
//		return listaUOPreferiteDoc;
//	}
//	public void setListaUOPreferiteDoc(List<DestinatarioPreferitoBean> listaUOPreferiteDoc) {
//		this.listaUOPreferiteDoc = listaUOPreferiteDoc;
//	}
//	public List<DestinatarioPreferitoBean> getListaUtentiPreferitiFolder() {
//		return listaUtentiPreferitiFolder;
//	}
//	public void setListaUtentiPreferitiFolder(List<DestinatarioPreferitoBean> listaUtentiPreferitiFolder) {
//		this.listaUtentiPreferitiFolder = listaUtentiPreferitiFolder;
//	}
//	public List<DestinatarioPreferitoBean> getListaUOPreferiteFolder() {
//		return listaUOPreferiteFolder;
//	}
//	public void setListaUOPreferiteFolder(List<DestinatarioPreferitoBean> listaUOPreferiteFolder) {
//		this.listaUOPreferiteFolder = listaUOPreferiteFolder;
//	}
	public HashMap<String, List<DestinatarioPreferitoBean>> getMappaUtentiPreferiti() {
		return mappaUtentiPreferiti;
	}
	public void setMappaUtentiPreferiti(HashMap<String, List<DestinatarioPreferitoBean>> mappaUtentiPreferiti) {
		this.mappaUtentiPreferiti = mappaUtentiPreferiti;
	}
	public HashMap<String, List<DestinatarioPreferitoBean>> getMappaUOPreferite() {
		return mappaUOPreferite;
	}
	public void setMappaUOPreferite(HashMap<String, List<DestinatarioPreferitoBean>> mappaUOPreferite) {
		this.mappaUOPreferite = mappaUOPreferite;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
