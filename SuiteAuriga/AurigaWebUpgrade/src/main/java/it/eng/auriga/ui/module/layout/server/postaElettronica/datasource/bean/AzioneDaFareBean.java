/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AzioneDaFareBean extends OperazioneMassivaPostaElettronicaBean{

	private String codAzioneDaFare;
	private String azioneDaFare;
	private String dettaglioAzioneDaFare;
	private Integer flgRilascia;
	
	public String getCodAzioneDaFare() {
		return codAzioneDaFare;
	}
	public void setCodAzioneDaFare(String codAzioneDaFare) {
		this.codAzioneDaFare = codAzioneDaFare;
	}
	public String getDettaglioAzioneDaFare() {
		return dettaglioAzioneDaFare;
	}
	public void setDettaglioAzioneDaFare(String dettaglioAzioneDaFare) {
		this.dettaglioAzioneDaFare = dettaglioAzioneDaFare;
	}
	public String getAzioneDaFare() {
		return azioneDaFare;
	}
	public void setAzioneDaFare(String azioneDaFare) {
		this.azioneDaFare = azioneDaFare;
	}
	public Integer getFlgRilascia() {
		return flgRilascia;
	}
	public void setFlgRilascia(Integer flgRilascia) {
		this.flgRilascia = flgRilascia;
	}
	
	
	
	
}
