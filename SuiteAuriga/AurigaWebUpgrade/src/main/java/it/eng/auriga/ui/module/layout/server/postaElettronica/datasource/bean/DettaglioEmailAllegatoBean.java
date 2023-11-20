/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class DettaglioEmailAllegatoBean {

	private String nomeFileAllegato;
	private String displayFileNameAllegato;
	private String uriFileAllegato;
	private Boolean remoteUri;
	private MimeTypeFirmaBean infoFile;
	private String idProvReg;
	private String estremiReg;
	private String numeroProgAllegato;
	private String flgFirmaValida;

	public String getNomeFileAllegato() {
		return nomeFileAllegato;
	}

	public void setNomeFileAllegato(String nomeFileAllegato) {
		this.nomeFileAllegato = nomeFileAllegato;
	}

	public String getUriFileAllegato() {
		return uriFileAllegato;
	}

	public void setUriFileAllegato(String uriFileAllegato) {
		this.uriFileAllegato = uriFileAllegato;
	}

	public Boolean getRemoteUri() {
		return remoteUri;
	}

	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public String getIdProvReg() {
		return idProvReg;
	}

	public void setIdProvReg(String idProvReg) {
		this.idProvReg = idProvReg;
	}

	public String getEstremiReg() {
		return estremiReg;
	}

	public void setEstremiReg(String estremiReg) {
		this.estremiReg = estremiReg;
	}

	public String getDisplayFileNameAllegato() {
		return displayFileNameAllegato;
	}

	public void setDisplayFileNameAllegato(String displayFileNameAllegato) {
		this.displayFileNameAllegato = displayFileNameAllegato;
	}

	public String getNumeroProgAllegato() {
		return numeroProgAllegato;
	}

	public void setNumeroProgAllegato(String numeroProgAllegato) {
		this.numeroProgAllegato = numeroProgAllegato;
	}

	public String getFlgFirmaValida() {
		return flgFirmaValida;
	}

	public void setFlgFirmaValida(String flgFirmaValida) {
		this.flgFirmaValida = flgFirmaValida;
	}
}
