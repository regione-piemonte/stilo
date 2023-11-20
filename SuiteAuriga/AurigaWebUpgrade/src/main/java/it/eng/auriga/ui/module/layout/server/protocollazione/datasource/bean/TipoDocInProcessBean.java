/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class TipoDocInProcessBean {

	@NumeroColonna(numero = "1")
	private String idTipoDoc;	
	
	@NumeroColonna(numero = "2")
	private String descTipoDoc;		
	
	@NumeroColonna(numero = "3")
	private String flgAcqProd;
	
	@NumeroColonna(numero = "4")
	private String flgParteDispositivo;
	
	@NumeroColonna(numero = "5")
	private String flgParere;
	
	@NumeroColonna(numero = "6")
	private String flgNoPubbl;
	
	@NumeroColonna(numero = "7")
	private String flgPubblicaSeparato;
	
	@NumeroColonna(numero = "8")
	private String flgRichiestaFirmaDigitale;
		
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	
	public String getDescTipoDoc() {
		return descTipoDoc;
	}
	
	public void setDescTipoDoc(String descTipoDoc) {
		this.descTipoDoc = descTipoDoc;
	}
	
	public String getFlgAcqProd() {
		return flgAcqProd;
	}
	
	public void setFlgAcqProd(String flgAcqProd) {
		this.flgAcqProd = flgAcqProd;
	}

	public String getFlgParteDispositivo() {
		return flgParteDispositivo;
	}

	public void setFlgParteDispositivo(String flgParteDispositivo) {
		this.flgParteDispositivo = flgParteDispositivo;
	}

	public String getFlgParere() {
		return flgParere;
	}

	public void setFlgParere(String flgParere) {
		this.flgParere = flgParere;
	}

	public String getFlgNoPubbl() {
		return flgNoPubbl;
	}

	public void setFlgNoPubbl(String flgNoPubbl) {
		this.flgNoPubbl = flgNoPubbl;
	}

	public String getFlgPubblicaSeparato() {
		return flgPubblicaSeparato;
	}
	
	public void setFlgPubblicaSeparato(String flgPubblicaSeparato) {
		this.flgPubblicaSeparato = flgPubblicaSeparato;
	}

	public String getFlgRichiestaFirmaDigitale() {
		return flgRichiestaFirmaDigitale;
	}

	public void setFlgRichiestaFirmaDigitale(String flgRichiestaFirmaDigitale) {
		this.flgRichiestaFirmaDigitale = flgRichiestaFirmaDigitale;
	}

}
