/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class RegistrazioneDestinatari {
	
	@NumeroColonna(numero = "1")
	private String tipoDestinatario;
	@NumeroColonna(numero = "2")
	private String idUoOScrivania;
	@NumeroColonna(numero = "3")
	private String valoreCCOAss;
	
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getIdUoOScrivania() {
		return idUoOScrivania;
	}
	public void setIdUoOScrivania(String idUoOScrivania) {
		this.idUoOScrivania = idUoOScrivania;
	}
	public String getValoreCCOAss() {
		return valoreCCOAss;
	}
	public void setValoreCCOAss(String valoreCCOAss) {
		this.valoreCCOAss = valoreCCOAss;
	}

}
