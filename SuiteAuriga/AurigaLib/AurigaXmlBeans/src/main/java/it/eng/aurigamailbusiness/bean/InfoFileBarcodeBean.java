/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class InfoFileBarcodeBean {

	private String qrCodeRilevati;
	private String datamatrixRilevati; 
	private String erroreScansione;
	
	public String getQrCodeRilevati() {
		return qrCodeRilevati;
	}
	public String getDatamatrixRilevati() {
		return datamatrixRilevati;
	}
	public String getErroreScansione() {
		return erroreScansione;
	}
	public void setQrCodeRilevati(String qrCodeRilevati) {
		this.qrCodeRilevati = qrCodeRilevati;
	}
	public void setDatamatrixRilevati(String datamatrixRilevati) {
		this.datamatrixRilevati = datamatrixRilevati;
	}
	public void setErroreScansione(String erroreScansione) {
		this.erroreScansione = erroreScansione;
	}
	
	
	
	
}
