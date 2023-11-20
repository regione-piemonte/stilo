/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.task.bean.FileDaUnireBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class UnioneFileAttoBean extends EventoCustomBean{

	private List<FileDaUnireBean> fileDaUnire;
	private String nomeFileUnione;
	private List<FileDaUnireBean> fileDaUnireVersIntegrale;
	private String nomeFileUnioneVersIntegrale;
	private String tipoDocFileUnioneVersIntegrale;

	private String uri;
	private String nomeFile;
	private MimeTypeFirmaBean infoFile;
	private String uriVersIntegrale;
	private String nomeFileVersIntegrale;
	private MimeTypeFirmaBean infoFileVersIntegrale;
	
	// Tengo l'uri dell'odt originale del file generato da modello
	private String uriFileOdtGenerato;
	
	public List<FileDaUnireBean> getFileDaUnire() {
		return fileDaUnire;
	}
	
	public void setFileDaUnire(List<FileDaUnireBean> fileDaUnire) {
		this.fileDaUnire = fileDaUnire;
	}
	
	public String getNomeFileUnione() {
		return nomeFileUnione;
	}
	
	public void setNomeFileUnione(String nomeFileUnione) {
		this.nomeFileUnione = nomeFileUnione;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}
	
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	
	public List<FileDaUnireBean> getFileDaUnireVersIntegrale() {
		return fileDaUnireVersIntegrale;
	}
	
	public void setFileDaUnireVersIntegrale(List<FileDaUnireBean> fileDaUnireVersIntegrale) {
		this.fileDaUnireVersIntegrale = fileDaUnireVersIntegrale;
	}
	
	public String getNomeFileUnioneVersIntegrale() {
		return nomeFileUnioneVersIntegrale;
	}
	
	public void setNomeFileUnioneVersIntegrale(String nomeFileUnione_VersIntegrale) {
		this.nomeFileUnioneVersIntegrale = nomeFileUnione_VersIntegrale;
	}
	
	public String getTipoDocFileUnioneVersIntegrale() {
		return tipoDocFileUnioneVersIntegrale;
	}
	
	public void setTipoDocFileUnioneVersIntegrale(String tipoDocFileUnioneVersIntegrale) {
		this.tipoDocFileUnioneVersIntegrale = tipoDocFileUnioneVersIntegrale;
	}
	
	public String getUriVersIntegrale() {
		return uriVersIntegrale;
	}
	
	public void setUriVersIntegrale(String uriVersIntegrale) {
		this.uriVersIntegrale = uriVersIntegrale;
	}
	
	public String getNomeFileVersIntegrale() {
		return nomeFileVersIntegrale;
	}
	
	public void setNomeFileVersIntegrale(String nomeFileVersIntegrale) {
		this.nomeFileVersIntegrale = nomeFileVersIntegrale;
	}
	
	public MimeTypeFirmaBean getInfoFileVersIntegrale() {
		return infoFileVersIntegrale;
	}
	
	public void setInfoFileVersIntegrale(MimeTypeFirmaBean infoFileVersIntegrale) {
		this.infoFileVersIntegrale = infoFileVersIntegrale;
	}

	public String getUriFileOdtGenerato() {
		return uriFileOdtGenerato;
	}
	
	public void setUriFileOdtGenerato(String uriFileOdtGenerato) {
		this.uriFileOdtGenerato = uriFileOdtGenerato;
	}
	
}
