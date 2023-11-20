/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import it.eng.document.NumeroColonna;

public class TipoDocumentoBean {

	@NumeroColonna(numero = "1")
	private String idTipoDocumento;

	@NumeroColonna(numero = "2")
	private String descTipoDocumento;

	@NumeroColonna(numero = "3")
	private String codCategoriaAltraNumerazione;

	@NumeroColonna(numero = "4")
	private String siglaAltraNumerazione;

	@NumeroColonna(numero = "7")
	private Boolean flgTipoDocConVie;

	@NumeroColonna(numero = "8")
	private String siglaPraticaSuSistUfficioRichiedente;

	@NumeroColonna(numero = "9")
	private String idNodoDefaultRicercaAtti;
	
	@NumeroColonna(numero = "10")
	private Boolean flgOggettoNonObblig;

	@NumeroColonna(numero = "11")
	private String flgTipoProtModulo;
	
	@NumeroColonna(numero = "14")
	private String flgRichiestaFirmaDigitale;
	
	private LinkedHashMap<String, String> gruppiAttributiCustomTipoDoc;

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getDescTipoDocumento() {
		return descTipoDocumento;
	}

	public void setDescTipoDocumento(String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}

	public String getCodCategoriaAltraNumerazione() {
		return codCategoriaAltraNumerazione;
	}

	public void setCodCategoriaAltraNumerazione(String codCategoriaAltraNumerazione) {
		this.codCategoriaAltraNumerazione = codCategoriaAltraNumerazione;
	}

	public String getSiglaAltraNumerazione() {
		return siglaAltraNumerazione;
	}

	public void setSiglaAltraNumerazione(String siglaAltraNumerazione) {
		this.siglaAltraNumerazione = siglaAltraNumerazione;
	}

	public Boolean getFlgTipoDocConVie() {
		return flgTipoDocConVie;
	}

	public void setFlgTipoDocConVie(Boolean flgTipoDocConVie) {
		this.flgTipoDocConVie = flgTipoDocConVie;
	}

	public String getSiglaPraticaSuSistUfficioRichiedente() {
		return siglaPraticaSuSistUfficioRichiedente;
	}

	public void setSiglaPraticaSuSistUfficioRichiedente(String siglaPraticaSuSistUfficioRichiedente) {
		this.siglaPraticaSuSistUfficioRichiedente = siglaPraticaSuSistUfficioRichiedente;
	}

	public String getIdNodoDefaultRicercaAtti() {
		return idNodoDefaultRicercaAtti;
	}

	public void setIdNodoDefaultRicercaAtti(String idNodoDefaultRicercaAtti) {
		this.idNodoDefaultRicercaAtti = idNodoDefaultRicercaAtti;
	}
	
	public Boolean getFlgOggettoNonObblig() {
		return flgOggettoNonObblig;
	}

	public void setFlgOggettoNonObblig(Boolean flgOggettoNonObblig) {
		this.flgOggettoNonObblig = flgOggettoNonObblig;
	}
	
	public String getFlgTipoProtModulo() {
		return flgTipoProtModulo;
	}

	public void setFlgTipoProtModulo(String flgTipoProtModulo) {
		this.flgTipoProtModulo = flgTipoProtModulo;
	}

	public String getFlgRichiestaFirmaDigitale() {
		return flgRichiestaFirmaDigitale;
	}

	public void setFlgRichiestaFirmaDigitale(String flgRichiestaFirmaDigitale) {
		this.flgRichiestaFirmaDigitale = flgRichiestaFirmaDigitale;
	}

	public LinkedHashMap<String, String> getGruppiAttributiCustomTipoDoc() {
		return gruppiAttributiCustomTipoDoc;
	}

	public void setGruppiAttributiCustomTipoDoc(LinkedHashMap<String, String> gruppiAttributiCustomTipoDoc) {
		this.gruppiAttributiCustomTipoDoc = gruppiAttributiCustomTipoDoc;
	}
	
}
