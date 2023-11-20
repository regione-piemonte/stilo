/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FileDaSelezionareBean {
	
	private BigDecimal idDocAllegato;
	private String nomeFileAllegato;
	private String uriFileAllegato;
	private Boolean remoteUri;
	private MimeTypeFirmaBean infoFile;
	private String nroAllegato;

	
	public BigDecimal getIdDocAllegato() {
		return idDocAllegato;
	}
	public String getNomeFileAllegato() {
		return nomeFileAllegato;
	}
	public String getUriFileAllegato() {
		return uriFileAllegato;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setIdDocAllegato(BigDecimal idDocAllegato) {
		this.idDocAllegato = idDocAllegato;
	}
	public void setNomeFileAllegato(String nomeFileAllegato) {
		this.nomeFileAllegato = nomeFileAllegato;
	}
	public void setUriFileAllegato(String uriFileAllegato) {
		this.uriFileAllegato = uriFileAllegato;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public String getNroAllegato() {
		return nroAllegato;
	}
	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}

}
