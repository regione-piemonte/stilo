/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class VisualizzaFatturaBean {

	private String uriFile;
	private Boolean remoteUri;
	private String estremi;
	private String html;
	private Boolean flgCurrentVersion;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getEstremi() {
		return estremi;
	}

	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public Boolean getRemoteUri() {
		return remoteUri;
	}

	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}

	public Boolean getFlgCurrentVersion() {
		return flgCurrentVersion;
	}

	public void setFlgCurrentVersion(Boolean flgCurrentVersion) {
		this.flgCurrentVersion = flgCurrentVersion;
	}

}
