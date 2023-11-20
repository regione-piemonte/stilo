/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FolderCustomBean {
	
	private String id;
	private String path;
	private String codice;	
	private Boolean flgCapofila;
	private String capofila;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public Boolean getFlgCapofila() {
		return flgCapofila;
	}
	public void setFlgCapofila(Boolean flgCapofila) {
		this.flgCapofila = flgCapofila;
	}
	public String getCapofila() {
		return capofila;
	}
	public void setCapofila(String capofila) {
		this.capofila = capofila;
	}
	
}
