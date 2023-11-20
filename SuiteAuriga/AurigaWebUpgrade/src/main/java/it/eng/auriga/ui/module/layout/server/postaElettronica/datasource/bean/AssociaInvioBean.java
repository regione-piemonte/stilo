/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;
import java.util.Date;

public class AssociaInvioBean implements Serializable{

	@NumeroColonna(numero = "1")
	private String idDestinatario;
	@NumeroColonna(numero = "2")
	private String indirizzoDestinatario;
	@NumeroColonna(numero = "3")
	private String dataInvio;
	private String nota;
	private String idEmailRicevuta;

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getIndirizzoDestinatario() {
		return indirizzoDestinatario;
	}

	public void setIndirizzoDestinatario(String indirizzoDestinatario) {
		this.indirizzoDestinatario = indirizzoDestinatario;
	}

	public String getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getIdEmailRicevuta() {
		return idEmailRicevuta;
	}

	public void setIdEmailRicevuta(String idEmailRicevuta) {
		this.idEmailRicevuta = idEmailRicevuta;
	}
	
	
	
	
}
