/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EtichettaBean {

	
	private int numeroAllegato;
	private String testo;
	private String barcode;
	private String testoBarcode;
	private String testoRepertorio;
	
	public int getNumeroAllegato() {
		return numeroAllegato;
	}
	public void setNumeroAllegato(int numeroAllegato) {
		this.numeroAllegato = numeroAllegato;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getTestoBarcode() {
		return testoBarcode;
	}
	public void setTestoBarcode(String testoBarcode) {
		this.testoBarcode = testoBarcode;
	}
	public String getTestoRepertorio() {
		return testoRepertorio;
	}
	public void setTestoRepertorio(String testoRepertorio) {
		this.testoRepertorio = testoRepertorio;
	}	
}