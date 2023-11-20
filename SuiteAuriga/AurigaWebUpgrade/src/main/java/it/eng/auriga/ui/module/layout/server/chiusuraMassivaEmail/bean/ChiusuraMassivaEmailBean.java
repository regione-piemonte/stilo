/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class ChiusuraMassivaEmailBean {
	
	private String uoAssegnazione;
	private String nroGiorniSenzaOperazioni;	
	private String nroGiorniApertura;
	private Date dataInvioDa;
	private Date dataInvioA;	
	private String caselle;
	
	
	private String idJob;
	private String uri;
	public String getNroGiorniSenzaOperazioni() {
		return nroGiorniSenzaOperazioni;
	}
	public void setNroGiorniSenzaOperazioni(String nroGiorniSenzaOperazioni) {
		this.nroGiorniSenzaOperazioni = nroGiorniSenzaOperazioni;
	}
	public String getNroGiorniApertura() {
		return nroGiorniApertura;
	}
	public void setNroGiorniApertura(String nroGiorniApertura) {
		this.nroGiorniApertura = nroGiorniApertura;
	}
	public Date getDataInvioDa() {
		return dataInvioDa;
	}
	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}
	public Date getDataInvioA() {
		return dataInvioA;
	}
	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}
	public String getIdJob() {
		return idJob;
	}
	public void setIdJob(String idJob) {
		this.idJob = idJob;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUoAssegnazione() {
		return uoAssegnazione;
	}
	public void setUoAssegnazione(String uoAssegnazione) {
		this.uoAssegnazione = uoAssegnazione;
	}
	public String getCaselle() {
		return caselle;
	}
	public void setCaselle(String caselle) {
		this.caselle = caselle;
	}

		
}
