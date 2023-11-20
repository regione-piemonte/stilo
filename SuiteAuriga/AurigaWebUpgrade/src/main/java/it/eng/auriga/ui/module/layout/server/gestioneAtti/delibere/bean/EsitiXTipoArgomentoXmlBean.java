/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class EsitiXTipoArgomentoXmlBean {
	
	@NumeroColonna(numero = "1")
	private String tipo;
	
	@NumeroColonna(numero = "2")
	private String esito;
	
	@NumeroColonna(numero = "3")
	private String flgAdozioneAtto;
	
	@NumeroColonna(numero = "4")
	private String flgAssegnazioneNumerazione;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getFlgAdozioneAtto() {
		return flgAdozioneAtto;
	}

	public void setFlgAdozioneAtto(String flgAdozioneAtto) {
		this.flgAdozioneAtto = flgAdozioneAtto;
	}

	public String getFlgAssegnazioneNumerazione() {
		return flgAssegnazioneNumerazione;
	}

	public void setFlgAssegnazioneNumerazione(String flgAssegnazioneNumerazione) {
		this.flgAssegnazioneNumerazione = flgAssegnazioneNumerazione;
	}

}