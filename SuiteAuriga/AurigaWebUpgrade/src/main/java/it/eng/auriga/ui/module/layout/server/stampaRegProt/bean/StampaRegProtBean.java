/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class StampaRegProtBean {
	
	private String codCategoriaRegistro;
	private String tipoIntervallo;	
	private String annoReg;
	private String nroRegDa;
	private String nroRegA;
	private Date dataRegDa;
	private Date dataRegA;	
	private Boolean schermaRiservati;
	private String siglaRegistro;
	
	private String idJob;
	private String uri;

	public String getCodCategoriaRegistro() {
		return codCategoriaRegistro;
	}

	public void setCodCategoriaRegistro(String codCategoriaRegistro) {
		this.codCategoriaRegistro = codCategoriaRegistro;
	}

	public String getTipoIntervallo() {
		return tipoIntervallo;
	}

	public void setTipoIntervallo(String tipoIntervallo) {
		this.tipoIntervallo = tipoIntervallo;
	}
	
	public String getAnnoReg() {
		return annoReg;
	}

	public void setAnnoReg(String annoReg) {
		this.annoReg = annoReg;
	}

	public String getNroRegDa() {
		return nroRegDa;
	}

	public void setNroRegDa(String nroRegDa) {
		this.nroRegDa = nroRegDa;
	}

	public String getNroRegA() {
		return nroRegA;
	}

	public void setNroRegA(String nroRegA) {
		this.nroRegA = nroRegA;
	}

	public Date getDataRegDa() {
		return dataRegDa;
	}

	public void setDataRegDa(Date dataRegDa) {
		this.dataRegDa = dataRegDa;
	}

	public Date getDataRegA() {
		return dataRegA;
	}

	public void setDataRegA(Date dataRegA) {
		this.dataRegA = dataRegA;
	}

	public Boolean getSchermaRiservati() {
		return schermaRiservati;
	}

	public void setSchermaRiservati(Boolean schermaRiservati) {
		this.schermaRiservati = schermaRiservati;
	}
	
	public String getIdJob() {
		return idJob;
	}

	public void setIdJob(String idJob) {
		this.idJob = idJob;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSiglaRegistro() {
		return siglaRegistro;
	}

	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}
	
}
