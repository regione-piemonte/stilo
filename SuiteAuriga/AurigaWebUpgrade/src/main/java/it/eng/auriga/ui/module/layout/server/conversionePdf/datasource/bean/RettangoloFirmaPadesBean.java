/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Bean che contiene tutti i dati necessari per poter creare correttamente una firma posizionata all'interno di un file PDF
 */
package it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean;

import java.io.Serializable;

import it.eng.auriga.util.PosizioneFirma;

public class RettangoloFirmaPadesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String firmatario;
	private PosizioneFirma posizioneFirma;
	private int marginePaginaVerticale;
	private int marginePaginaOrizzontale;
	private int distanzaFirme;
	private int rectWidth;
	private int rectHeight;

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}

	public PosizioneFirma getPosizioneFirma() {
		return posizioneFirma;
	}

	public void setPosizioneFirma(PosizioneFirma posizioneFirma) {
		this.posizioneFirma = posizioneFirma;
	}

	public int getMarginePaginaVerticale() {
		return marginePaginaVerticale;
	}

	public void setMarginePaginaVerticale(int marginePaginaVerticale) {
		this.marginePaginaVerticale = marginePaginaVerticale;
	}

	public int getMarginePaginaOrizzontale() {
		return marginePaginaOrizzontale;
	}

	public void setMarginePaginaOrizzontale(int marginePaginaOrizzontale) {
		this.marginePaginaOrizzontale = marginePaginaOrizzontale;
	}

	public int getDistanzaFirme() {
		return distanzaFirme;
	}

	public void setDistanzaFirme(int distanzaFirme) {
		this.distanzaFirme = distanzaFirme;
	}

	public int getRectWidth() {
		return rectWidth;
	}

	public void setRectWidth(int rectWidth) {
		this.rectWidth = rectWidth;
	}

	public int getRectHeight() {
		return rectHeight;
	}

	public void setRectHeight(int rectHeight) {
		this.rectHeight = rectHeight;
	}

}
