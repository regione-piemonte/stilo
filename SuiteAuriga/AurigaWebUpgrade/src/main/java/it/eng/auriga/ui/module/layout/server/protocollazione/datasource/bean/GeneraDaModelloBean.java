/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class GeneraDaModelloBean {

	private String idModello;
	private String idProcess;
	private String tipoModello;
	private BigDecimal idDocPrimario;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private MimeTypeFirmaBean infoFile;	
	private String idUd;
	private String idFolder;
	private String processId;
	private String oggetto;
	private List<DestInvioCCBean> listaUoCoinvolte;
	
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	
	private Boolean flgMostraDatiSensibili;
	
	// Variabili per gestione errori
	private boolean inError;
	private String errorMessage;
	
	public String getIdModello() {
		return idModello;
	}
	
	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}
	
	public String getIdProcess() {
		return idProcess;
	}
	
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public BigDecimal getIdDocPrimario() {
		return idDocPrimario;
	}
	
	public void setIdDocPrimario(BigDecimal idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getIdFolder() {
		return idFolder;
	}
	
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	
	public String getProcessId() {
		return processId;
	}
	
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public List<DestInvioCCBean> getListaUoCoinvolte() {
		return listaUoCoinvolte;
	}
	
	public void setListaUoCoinvolte(List<DestInvioCCBean> listaUoCoinvolte) {
		this.listaUoCoinvolte = listaUoCoinvolte;
	}
	
	public Map<String, Object> getValori() {
		return valori;
	}
	
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
	
	public Boolean getFlgMostraDatiSensibili() {
		return flgMostraDatiSensibili;
	}
	
	public void setFlgMostraDatiSensibili(Boolean flgMostraDatiSensibili) {
		this.flgMostraDatiSensibili = flgMostraDatiSensibili;
	}

	public boolean isInError() {
		return inError;
	}
	
	public void setInError(boolean inError) {
		this.inError = inError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
	
}
