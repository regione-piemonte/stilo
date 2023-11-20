/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ImpostazioniUnioneFileBean {

	private Boolean infoAllegatoAttiva;
	private String infoAllegatoPagine;
	private String infoAllegatoFormato;
	private String infoAllegatoMargine;
	private String infoAllegatoOrientamento;
	private String infoAllegatoFont;
	private String infoAllegatoFontSize;
	private String infoAllegatoTextColor;
	private String infoAllegatoStyle;
	private int infoAllegatoMaxLenNomeFileAllegato;

	private Boolean nroPaginaAttiva;
	private String nroPaginaFormato;
	private String nroPaginaPosizione;
	private Boolean nroPaginaNumerazioneDistintaXFileUniti;
	private Boolean nroPaginaEscludiAllegatiMaggioreA4;
	private String nroPaginaFont;
	private String nroPaginaFontSize;
	private String nroPaginaTextColor;
	private String nroPaginaStyle;
	private Boolean nroPaginaEscludiAllegatiUnitiInMezzo;
	private Boolean nroPaginaEscludiAllegatiUnitiInFondo;
	private Boolean nroPaginaEscludiFoglioFirmeGrafiche;
	private Boolean nroPaginaEscludiListaAllegatiSeparati;
	private Boolean nroPaginaEscludiAppendice;

	private String idModelloDocFirmeGrafiche;
	private String tipoModelloDocFirmeGrafiche;
	private String nomeModelloDocFirmeGrafiche;
	private String idDocModelloFirmeGrafiche;
	private String nroPagineFirmeGrafiche;

	private Boolean posizioneAllegatiUnitiAttiva;
	
	public Boolean getInfoAllegatoAttiva() {
		return infoAllegatoAttiva;
	}

	public void setInfoAllegatoAttiva(Boolean infoAllegatoAttiva) {
		this.infoAllegatoAttiva = infoAllegatoAttiva;
	}

	public String getInfoAllegatoPagine() {
		return infoAllegatoPagine;
	}

	public void setInfoAllegatoPagine(String infoAllegatoPagine) {
		this.infoAllegatoPagine = infoAllegatoPagine;
	}

	public String getInfoAllegatoFormato() {
		return infoAllegatoFormato;
	}

	public void setInfoAllegatoFormato(String infoAllegatoFormato) {
		this.infoAllegatoFormato = infoAllegatoFormato;
	}

	public String getInfoAllegatoMargine() {
		return infoAllegatoMargine;
	}

	public void setInfoAllegatoMargine(String infoAllegatoMargine) {
		this.infoAllegatoMargine = infoAllegatoMargine;
	}

	public String getInfoAllegatoOrientamento() {
		return infoAllegatoOrientamento;
	}

	public void setInfoAllegatoOrientamento(String infoAllegatoOrientamento) {
		this.infoAllegatoOrientamento = infoAllegatoOrientamento;
	}

	public String getInfoAllegatoFont() {
		return infoAllegatoFont;
	}

	public void setInfoAllegatoFont(String infoAllegatoFont) {
		this.infoAllegatoFont = infoAllegatoFont;
	}

	public String getInfoAllegatoFontSize() {
		return infoAllegatoFontSize;
	}

	public void setInfoAllegatoFontSize(String infoAllegatoFontSize) {
		this.infoAllegatoFontSize = infoAllegatoFontSize;
	}

	public String getInfoAllegatoTextColor() {
		return infoAllegatoTextColor;
	}

	public void setInfoAllegatoTextColor(String infoAllegatoTextColor) {
		this.infoAllegatoTextColor = infoAllegatoTextColor;
	}

	public String getInfoAllegatoStyle() {
		return infoAllegatoStyle;
	}

	public void setInfoAllegatoStyle(String infoAllegatoStyle) {
		this.infoAllegatoStyle = infoAllegatoStyle;
	}

	public int getInfoAllegatoMaxLenNomeFileAllegato() {
		return infoAllegatoMaxLenNomeFileAllegato;
	}

	public void setInfoAllegatoMaxLenNomeFileAllegato(int infoAllegatoMaxLenNomeFileAllegato) {
		this.infoAllegatoMaxLenNomeFileAllegato = infoAllegatoMaxLenNomeFileAllegato;
	}

	public Boolean getNroPaginaAttiva() {
		return nroPaginaAttiva;
	}

	public void setNroPaginaAttiva(Boolean nroPaginaAttiva) {
		this.nroPaginaAttiva = nroPaginaAttiva;
	}

	public String getNroPaginaFormato() {
		return nroPaginaFormato;
	}

	public void setNroPaginaFormato(String nroPaginaFormato) {
		this.nroPaginaFormato = nroPaginaFormato;
	}

	public String getNroPaginaPosizione() {
		return nroPaginaPosizione;
	}

	public void setNroPaginaPosizione(String nroPaginaPosizione) {
		this.nroPaginaPosizione = nroPaginaPosizione;
	}

	public Boolean getNroPaginaNumerazioneDistintaXFileUniti() {
		return nroPaginaNumerazioneDistintaXFileUniti;
	}

	public void setNroPaginaNumerazioneDistintaXFileUniti(Boolean nroPaginaNumerazioneDistintaXFileUniti) {
		this.nroPaginaNumerazioneDistintaXFileUniti = nroPaginaNumerazioneDistintaXFileUniti;
	}

	public Boolean getNroPaginaEscludiAllegatiMaggioreA4() {
		return nroPaginaEscludiAllegatiMaggioreA4;
	}

	public void setNroPaginaEscludiAllegatiMaggioreA4(Boolean nroPaginaEscludiAllegatiMaggioreA4) {
		this.nroPaginaEscludiAllegatiMaggioreA4 = nroPaginaEscludiAllegatiMaggioreA4;
	}

	public String getNroPaginaFont() {
		return nroPaginaFont;
	}

	public void setNroPaginaFont(String nroPaginaFont) {
		this.nroPaginaFont = nroPaginaFont;
	}

	public String getNroPaginaFontSize() {
		return nroPaginaFontSize;
	}

	public void setNroPaginaFontSize(String nroPaginaFontSize) {
		this.nroPaginaFontSize = nroPaginaFontSize;
	}

	public String getNroPaginaTextColor() {
		return nroPaginaTextColor;
	}

	public void setNroPaginaTextColor(String nroPaginaTextColor) {
		this.nroPaginaTextColor = nroPaginaTextColor;
	}

	public String getNroPaginaStyle() {
		return nroPaginaStyle;
	}

	public void setNroPaginaStyle(String nroPaginaStyle) {
		this.nroPaginaStyle = nroPaginaStyle;
	}

	public Boolean getNroPaginaEscludiAllegatiUnitiInMezzo() {
		return nroPaginaEscludiAllegatiUnitiInMezzo;
	}

	public void setNroPaginaEscludiAllegatiUnitiInMezzo(Boolean nroPaginaEscludiAllegatiUnitiInMezzo) {
		this.nroPaginaEscludiAllegatiUnitiInMezzo = nroPaginaEscludiAllegatiUnitiInMezzo;
	}

	public Boolean getNroPaginaEscludiAllegatiUnitiInFondo() {
		return nroPaginaEscludiAllegatiUnitiInFondo;
	}

	public void setNroPaginaEscludiAllegatiUnitiInFondo(Boolean nroPaginaEscludiAllegatiUnitiInFondo) {
		this.nroPaginaEscludiAllegatiUnitiInFondo = nroPaginaEscludiAllegatiUnitiInFondo;
	}

	public Boolean getNroPaginaEscludiFoglioFirmeGrafiche() {
		return nroPaginaEscludiFoglioFirmeGrafiche;
	}

	public void setNroPaginaEscludiFoglioFirmeGrafiche(Boolean nroPaginaEscludiFoglioFirmeGrafiche) {
		this.nroPaginaEscludiFoglioFirmeGrafiche = nroPaginaEscludiFoglioFirmeGrafiche;
	}

	public Boolean getNroPaginaEscludiListaAllegatiSeparati() {
		return nroPaginaEscludiListaAllegatiSeparati;
	}

	public void setNroPaginaEscludiListaAllegatiSeparati(Boolean nroPaginaEscludiListaAllegatiSeparati) {
		this.nroPaginaEscludiListaAllegatiSeparati = nroPaginaEscludiListaAllegatiSeparati;
	}

	public String getIdModelloDocFirmeGrafiche() {
		return idModelloDocFirmeGrafiche;
	}

	public void setIdModelloDocFirmeGrafiche(String idModelloDocFirmeGrafiche) {
		this.idModelloDocFirmeGrafiche = idModelloDocFirmeGrafiche;
	}

	public String getTipoModelloDocFirmeGrafiche() {
		return tipoModelloDocFirmeGrafiche;
	}

	public void setTipoModelloDocFirmeGrafiche(String tipoModelloDocFirmeGrafiche) {
		this.tipoModelloDocFirmeGrafiche = tipoModelloDocFirmeGrafiche;
	}

	public String getNomeModelloDocFirmeGrafiche() {
		return nomeModelloDocFirmeGrafiche;
	}

	public void setNomeModelloDocFirmeGrafiche(String nomeModelloDocFirmeGrafiche) {
		this.nomeModelloDocFirmeGrafiche = nomeModelloDocFirmeGrafiche;
	}

	public String getIdDocModelloFirmeGrafiche() {
		return idDocModelloFirmeGrafiche;
	}

	public void setIdDocModelloFirmeGrafiche(String idDocModelloFirmeGrafiche) {
		this.idDocModelloFirmeGrafiche = idDocModelloFirmeGrafiche;
	}

	public String getNroPagineFirmeGrafiche() {
		return nroPagineFirmeGrafiche;
	}

	public void setNroPagineFirmeGrafiche(String nroPagineFirmeGrafiche) {
		this.nroPagineFirmeGrafiche = nroPagineFirmeGrafiche;
	}

	public Boolean getPosizioneAllegatiUnitiAttiva() {
		return posizioneAllegatiUnitiAttiva;
	}

	public void setPosizioneAllegatiUnitiAttiva(Boolean posizioneAllegatiUnitiAttiva) {
		this.posizioneAllegatiUnitiAttiva = posizioneAllegatiUnitiAttiva;
	}

	public Boolean getNroPaginaEscludiAppendice() {
		return nroPaginaEscludiAppendice;
	}

	public void setNroPaginaEscludiAppendice(Boolean nroPaginaEscludiAppendice) {
		this.nroPaginaEscludiAppendice = nroPaginaEscludiAppendice;
	}

}