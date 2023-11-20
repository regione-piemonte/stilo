/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class BarcodeResult {
	
	private String barcode;
	private String tipoBarcode;
	private boolean flagBarcodeValido = true;
	private boolean flagBarcodeFirma = false;
	private boolean flagFirmaPresente = false;
	private boolean flagQrcodeAggiuntivo = true;
	private int numPage;
	private String erroreScansione;
	
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getTipoBarcode() {
		return tipoBarcode;
	}
	public void setTipoBarcode(String tipoBarcode) {
		this.tipoBarcode = tipoBarcode;
	}
	
	public boolean isFlagBarcodeFirma() {
		return flagBarcodeFirma;
	}
	public void setFlagBarcodeFirma(boolean flagBarcodeFirma) {
		this.flagBarcodeFirma = flagBarcodeFirma;
	}
	public boolean isFlagFirmaPresente() {
		return flagFirmaPresente;
	}
	public void setFlagFirmaPresente(boolean flagFirmaPresente) {
		this.flagFirmaPresente = flagFirmaPresente;
	}
	public boolean isFlagBarcodeValido() {
		return flagBarcodeValido;
	}
	public void setFlagBarcodeValido(boolean flagBarcodeValido) {
		this.flagBarcodeValido = flagBarcodeValido;
	}
	public int getNumPage() {
		return numPage;
	}
	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}
	public String getErroreScansione() {
		return erroreScansione;
	}
	public void setErroreScansione(String erroreScansione) {
		this.erroreScansione = erroreScansione;
	}
	public boolean isFlagQrcodeAggiuntivo() {
		return flagQrcodeAggiuntivo;
	}
	public void setFlagQrcodeAggiuntivo(boolean flagQrcodeAggiuntivo) {
		this.flagQrcodeAggiuntivo = flagQrcodeAggiuntivo;
	}
	@Override
	public String toString() {
		return "BarcodeResult [barcode=" + barcode + ", tipoBarcode=" + tipoBarcode + ", flagBarcodeValido="
				+ flagBarcodeValido + ", flagBarcodeFirma=" + flagBarcodeFirma + ", flagFirmaPresente="
				+ flagFirmaPresente + ", flagQrcodeAggiuntivo=" + flagQrcodeAggiuntivo + ", numPage=" + numPage
				+ ", erroreScansione=" + erroreScansione + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		result = prime * result + ((erroreScansione == null) ? 0 : erroreScansione.hashCode());
		result = prime * result + (flagBarcodeFirma ? 1231 : 1237);
		result = prime * result + (flagBarcodeValido ? 1231 : 1237);
		result = prime * result + (flagFirmaPresente ? 1231 : 1237);
		result = prime * result + (flagQrcodeAggiuntivo ? 1231 : 1237);
		result = prime * result + numPage;
		result = prime * result + ((tipoBarcode == null) ? 0 : tipoBarcode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BarcodeResult other = (BarcodeResult) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (erroreScansione == null) {
			if (other.erroreScansione != null)
				return false;
		} else if (!erroreScansione.equals(other.erroreScansione))
			return false;
		if (flagBarcodeFirma != other.flagBarcodeFirma)
			return false;
		if (flagBarcodeValido != other.flagBarcodeValido)
			return false;
		if (flagFirmaPresente != other.flagFirmaPresente)
			return false;
		if (flagQrcodeAggiuntivo != other.flagQrcodeAggiuntivo)
			return false;
		if (numPage != other.numPage)
			return false;
		if (tipoBarcode == null) {
			if (other.tipoBarcode != null)
				return false;
		} else if (!tipoBarcode.equals(other.tipoBarcode))
			return false;
		return true;
	}
	
	

	
}
