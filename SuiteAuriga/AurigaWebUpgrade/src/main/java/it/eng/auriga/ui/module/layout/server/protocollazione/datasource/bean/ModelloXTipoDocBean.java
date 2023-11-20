/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ModelloXTipoDocBean {	
	
	@NumeroColonna(numero = "1")
	private String idModello; 
	
	@NumeroColonna(numero = "2")
	private String nomeModello;	
	
	/*
	 * Prima era la 19: la select dei modelli (dmfn_load_combo con Tipo= MODELLO_X_TIPO_DOC) prima aveva un pezzo di codice che era stato commentato da Andrea perché la rimappatura
	 * dell’xml delle colonne faceva casino (per ottimizzarla avevo introdotto un bug). Quindi l’URI del modello veniva in colonna 19 anziché in colonna 3 come doveva.
	 * 
	 */
	@NumeroColonna(numero = "3")
	private String uriFileModello;
	
	@NumeroColonna(numero = "4")
	private String flgConvertiInPdf;
	
	@NumeroColonna(numero = "5")
	private String tipoModello;

	public String getIdModello() {
		return idModello;
	}

	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}

	public String getNomeModello() {
		return nomeModello;
	}
	
	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}
	
	public String getUriFileModello() {
		return uriFileModello;
	}
	
	public void setUriFileModello(String uriFileModello) {
		this.uriFileModello = uriFileModello;
	}

	public String getFlgConvertiInPdf() {
		return flgConvertiInPdf;
	}

	public void setFlgConvertiInPdf(String flgConvertiInPdf) {
		this.flgConvertiInPdf = flgConvertiInPdf;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

}
