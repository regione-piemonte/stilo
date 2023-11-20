/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class RicevutePostaInUscitaBean {

	private String idEmail;
	private String categoria;			
	private String dataRicezione;
	private String mittente;

	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(String dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
				
}
