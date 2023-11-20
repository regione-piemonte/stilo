/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AttachmentUDBean {

	private String fileNameAttach;
	private String uriAttach;
	private Integer nroAttach;
	private Boolean remoteUri;
	private Boolean firmato;
	private Boolean flgFirmaValida;
	private String mimetype;
	private Boolean flgAllegatoPISep;
	
	public String getFileNameAttach() {
		return fileNameAttach;
	}
	public void setFileNameAttach(String fileNameAttach) {
		this.fileNameAttach = fileNameAttach;
	}
	public String getUriAttach() {
		return uriAttach;
	}
	public void setUriAttach(String uriAttach) {
		this.uriAttach = uriAttach;
	}
	public Integer getNroAttach() {
		return nroAttach;
	}
	public void setNroAttach(Integer nroAttach) {
		this.nroAttach = nroAttach;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public Boolean getFirmato() {
		return firmato;
	}
	public void setFirmato(Boolean firmato) {
		this.firmato = firmato;
	}
	public Boolean getFlgFirmaValida() {
		return flgFirmaValida;
	}
	public void setFlgFirmaValida(Boolean flgFirmaValida) {
		this.flgFirmaValida = flgFirmaValida;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public Boolean getFlgAllegatoPISep() {
		return flgAllegatoPISep;
	}
	public void setFlgAllegatoPISep(Boolean flgAllegatoPISep) {
		this.flgAllegatoPISep = flgAllegatoPISep;
	}

}
