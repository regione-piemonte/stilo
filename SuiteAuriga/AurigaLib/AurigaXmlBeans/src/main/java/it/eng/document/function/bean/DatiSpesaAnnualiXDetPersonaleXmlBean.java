/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiSpesaAnnualiXDetPersonaleXmlBean {

	@NumeroColonna(numero = "1")
	private String anno;
	
	@NumeroColonna(numero = "2")
	private String capitolo;		
	
	@NumeroColonna(numero = "3")
	private String articolo;		
	
	@NumeroColonna(numero = "4")
	private String numero;		
	
	@NumeroColonna(numero = "5")
	private String importo;

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}

	public String getArticolo() {
		return articolo;
	}

	public void setArticolo(String articolo) {
		this.articolo = articolo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}		
	
}
