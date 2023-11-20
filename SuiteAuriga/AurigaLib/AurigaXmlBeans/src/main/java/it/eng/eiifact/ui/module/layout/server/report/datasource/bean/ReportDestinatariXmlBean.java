/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import java.util.Date;

public class ReportDestinatariXmlBean {

	
	@NumeroColonna(numero="1")
    private String idReport;
	
	@NumeroColonna(numero="2")
	private String desSalesOrganization;
	
	@NumeroColonna(numero="3")
	private String desDistributionChannel;
	
    @NumeroColonna(numero="4")
	private String codiceDestinatario;

    @NumeroColonna(numero="5")	
	private String desDestinatario;
	
	@NumeroColonna(numero="6")
	private String desCanale;
	
	@NumeroColonna(numero="7")
	private String emailMittente;
	
	@NumeroColonna(numero="8")
	private String emailDestinatario;
	
	@NumeroColonna(numero="9")
	private String username;

    @NumeroColonna(numero="10")
	private String cognomeNome;
	
	@NumeroColonna(numero="11")
	private String flgObsoleto;
	
	@NumeroColonna(numero="12")
	@TipoData(tipo=Tipo.DATA)
    private Date dataUltimoAggiornamento;
	
	@NumeroColonna(numero="13")
	private String utenteUltimoAggiornamento;

	public String getDesSalesOrganization() {
		return desSalesOrganization;
	}

	public void setDesSalesOrganization(String desSalesOrganization) {
		this.desSalesOrganization = desSalesOrganization;
	}

	public String getDesDistributionChannel() {
		return desDistributionChannel;
	}

	public void setDesDistributionChannel(String desDistributionChannel) {
		this.desDistributionChannel = desDistributionChannel;
	}

	public String getCodiceDestinatario() {
		return codiceDestinatario;
	}

	public void setCodiceDestinatario(String codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

	public String getDesDestinatario() {
		return desDestinatario;
	}

	public void setDesDestinatario(String desDestinatario) {
		this.desDestinatario = desDestinatario;
	}

	public String getDesCanale() {
		return desCanale;
	}

	public void setDesCanale(String desCanale) {
		this.desCanale = desCanale;
	}

	public String getEmailMittente() {
		return emailMittente;
	}

	public void setEmailMittente(String emailMittente) {
		this.emailMittente = emailMittente;
	}

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
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



	public Date getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public String getUtenteUltimoAggiornamento() {
		return utenteUltimoAggiornamento;
	}

	public void setUtenteUltimoAggiornamento(String utenteUltimoAggiornamento) {
		this.utenteUltimoAggiornamento = utenteUltimoAggiornamento;
	}

	public String getIdReport() {
		return idReport;
	}

	public void setIdReport(String idReport) {
		this.idReport = idReport;
	}

	public String getFlgObsoleto() {
		return flgObsoleto;
	}

	public void setFlgObsoleto(String flgObsoleto) {
		this.flgObsoleto = flgObsoleto;
	}
					
}