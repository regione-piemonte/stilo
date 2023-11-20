/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AttivaDelegaBean {

	private String idUser;
	private boolean error;
	private boolean ignora;
	// private boolean mantieniPreference;
	private String avvertimenti;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getAvvertimenti() {
		return avvertimenti;
	}

	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}

	public void setIgnora(boolean ignora) {
		this.ignora = ignora;
	}

	public boolean isIgnora() {
		return ignora;
	}
	// public void setMantieniPreference(boolean mantieniPreference) {
	// this.mantieniPreference = mantieniPreference;
	// }
	// public boolean isMantieniPreference() {
	// return mantieniPreference;
	// }
}
