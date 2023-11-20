/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


/**
 * Modella lo scambio dati tra client e server durante la fase di impostazione
 * del file primario del flusso custom per milano
 * 
 * @author massimo malvestio
 *
 */
public class ImpostaFilePrimarioBean {

	private String idProcess;
	private String idUd;
	private Boolean esito;
	private String error;
	private String idDocType;
	private String uriFilePrimario;
	private String nomeFilePrimario;
	
	public String getIdProcess() {
		return idProcess;
	}
	
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	
	public String getIdUd() {
		return idUd;
	}
	
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	
	public Boolean getEsito() {
		return esito;
	}
	
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getIdDocType() {
		return idDocType;
	}
	
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	
}
