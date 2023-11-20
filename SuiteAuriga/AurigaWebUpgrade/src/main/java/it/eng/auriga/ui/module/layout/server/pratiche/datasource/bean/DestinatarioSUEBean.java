/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DestinatarioSUEBean {

	@NumeroColonna(numero = "1")
	private String nome;

	@NumeroColonna(numero = "2")
	private String indirizzoMail;

	@NumeroColonna(numero = "3")
	private String tipoIndirizzo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}

	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}

	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}

}