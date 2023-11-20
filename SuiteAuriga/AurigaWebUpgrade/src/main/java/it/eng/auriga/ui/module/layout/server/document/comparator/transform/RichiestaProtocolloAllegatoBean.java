/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class RichiestaProtocolloAllegatoBean {
	private String descrizioneFileAllegato;
	private String nomeFileAllegato;
	private String uriFileAllegato;
	private Boolean remoteUri;
	private MimeTypeFirmaBean infoFile;
	public String getDescrizioneFileAllegato() {
		return descrizioneFileAllegato;
	}
	public void setDescrizioneFileAllegato(String descrizioneFileAllegato) {
		this.descrizioneFileAllegato = descrizioneFileAllegato;
	}
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
}
