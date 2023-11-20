/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class PdVBean extends PdVXmlBean {
	
	private List<CronologiaCambiStatoPdVBean> cronologiaCambiStato;
	private List<ErroriRdVBean> erroriRdV;
	private String uriFileIndice;
	private String uriFileInf;
	private String uriFileRicevutaTrasmissione;
	private String uriFileRdV;
	
	public List<CronologiaCambiStatoPdVBean> getCronologiaCambiStato() {
		return cronologiaCambiStato;
	}
	public void setCronologiaCambiStato(List<CronologiaCambiStatoPdVBean> cronologiaCambiStato) {
		this.cronologiaCambiStato = cronologiaCambiStato;
	}
	public List<ErroriRdVBean> getErroriRdV() {
		return erroriRdV;
	}
	public void setErroriRdV(List<ErroriRdVBean> erroriRdV) {
		this.erroriRdV = erroriRdV;
	}
	public String getUriFileIndice() {
		return uriFileIndice;
	}
	public void setUriFileIndice(String uriFileIndice) {
		this.uriFileIndice = uriFileIndice;
	}
	public String getUriFileInf() {
		return uriFileInf;
	}
	public void setUriFileInf(String uriFileInf) {
		this.uriFileInf = uriFileInf;
	}
	public String getUriFileRicevutaTrasmissione() {
		return uriFileRicevutaTrasmissione;
	}
	public void setUriFileRicevutaTrasmissione(String uriFileRicevutaTrasmissione) {
		this.uriFileRicevutaTrasmissione = uriFileRicevutaTrasmissione;
	}
	public String getUriFileRdV() {
		return uriFileRdV;
	}
	public void setUriFileRdV(String uriFileRdV) {
		this.uriFileRdV = uriFileRdV;
	}
}
