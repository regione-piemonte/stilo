/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


public class AttiAutorizzazioneAnnRegXmlBean {
	
	@NumeroColonna(numero = "2")
	private String idAtto;	
	
	@NumeroColonna(numero = "4")
	private String nroAtto;
	
	@NumeroColonna(numero = "14")
	private String nroBozza;
	
	@NumeroColonna(numero = "15")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRegBozza;
	
	@NumeroColonna(numero = "18")
	private String oggetto;	
	
	@NumeroColonna(numero = "54")
	private String flgAttoChiuso;	
	
	@NumeroColonna(numero = "201")
	@TipoData(tipo = Tipo.DATA)	
	private Date tsRegAtto;

	public String getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(String idAtto) {
		this.idAtto = idAtto;
	}

	public String getNroAtto() {
		return nroAtto;
	}

	public void setNroAtto(String nroAtto) {
		this.nroAtto = nroAtto;
	}

	public String getNroBozza() {
		return nroBozza;
	}

	public void setNroBozza(String nroBozza) {
		this.nroBozza = nroBozza;
	}

	public Date getTsRegBozza() {
		return tsRegBozza;
	}

	public void setTsRegBozza(Date tsRegBozza) {
		this.tsRegBozza = tsRegBozza;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	

	public Date getTsRegAtto() {
		return tsRegAtto;
	}

	public void setTsRegAtto(Date tsRegAtto) {
		this.tsRegAtto = tsRegAtto;
	}

	public String getFlgAttoChiuso() {
		return flgAttoChiuso;
	}

	public void setFlgAttoChiuso(String flgAttoChiuso) {
		this.flgAttoChiuso = flgAttoChiuso;
	}
}
