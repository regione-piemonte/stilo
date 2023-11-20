/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class CapitoloImportoDaVerificareBean{
	
	@NumeroColonna(numero = "1")
	private String keyCapitolo;
	
	@NumeroColonna(numero = "2")
	private String importo;
	
	@NumeroColonna(numero = "3")
	private String capitolo;
	
	@NumeroColonna(numero = "4")
	private String conto;
	
	@NumeroColonna(numero = "5")
	private String cdr;

	public String getKeyCapitolo() {
		return keyCapitolo;
	}

	public String getImporto() {
		return importo;
	}

	public void setKeyCapitolo(String keyCapitolo) {
		this.keyCapitolo = keyCapitolo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getCapitolo() {
		return capitolo;
	}

	public String getConto() {
		return conto;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}

	public void setConto(String conto) {
		this.conto = conto;
	}

	public String getCdr() {
		return cdr;
	}

	public void setCdr(String cdr) {
		this.cdr = cdr;
	}


}
