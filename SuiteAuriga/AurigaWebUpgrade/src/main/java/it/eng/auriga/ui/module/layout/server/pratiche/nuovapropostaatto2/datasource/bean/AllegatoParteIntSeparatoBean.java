/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AllegatoParteIntSeparatoBean {

	@NumeroColonna(numero = "1")
	private String nomeFile;

	@NumeroColonna(numero = "2")
	private String impronta;

	@NumeroColonna(numero = "3")
	private String algoritmoImpronta;

	@NumeroColonna(numero = "4")
	private String encodingImpronta;

	@NumeroColonna(numero = "5")
	private String desTipoAllegato;

	@NumeroColonna(numero = "6")
	private String descrizione;

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getDesTipoAllegato() {
		return desTipoAllegato;
	}

	public void setDesTipoAllegato(String desTipoAllegato) {
		this.desTipoAllegato = desTipoAllegato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
		
}
