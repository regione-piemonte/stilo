/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class CanaliTrasmissioneClusterXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idUoCluster;
	
	@NumeroColonna(numero = "2")
	private String desCluster;
	
	@NumeroColonna(numero = "3")
	private String codiceCanale;
	
	@NumeroColonna(numero = "4")
	private String flgObsoleto;
	
	@NumeroColonna(numero = "5")
	private String idCasellaMail;
	
	@NumeroColonna(numero = "6")
	private String indirizzoMail;
	
	@NumeroColonna(numero = "7")
	private String emailDestinatario;
	
	@NumeroColonna(numero = "8")
	private String idUtente;
	
	@NumeroColonna(numero = "9")
	private String username;
	
	@NumeroColonna(numero = "10")	
	private String cognomeNome;

	public String getIdUoCluster() {
		return idUoCluster;
	}

	public void setIdUoCluster(String idUoCluster) {
		this.idUoCluster = idUoCluster;
	}

	public String getDesCluster() {
		return desCluster;
	}

	public void setDesCluster(String desCluster) {
		this.desCluster = desCluster;
	}

	public String getCodiceCanale() {
		return codiceCanale;
	}

	public void setCodiceCanale(String codiceCanale) {
		this.codiceCanale = codiceCanale;
	}

	public String getFlgObsoleto() {
		return flgObsoleto;
	}

	public void setFlgObsoleto(String flgObsoleto) {
		this.flgObsoleto = flgObsoleto;
	}

	public String getIdCasellaMail() {
		return idCasellaMail;
	}

	public void setIdCasellaMail(String idCasellaMail) {
		this.idCasellaMail = idCasellaMail;
	}

	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}
	
}