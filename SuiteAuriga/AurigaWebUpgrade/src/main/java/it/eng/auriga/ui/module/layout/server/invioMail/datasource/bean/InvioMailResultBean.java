/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class InvioMailResultBean {

	private String idEmail;
	private String destinatari;
	private String destinatariCC;
	private String oggetto;
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
}