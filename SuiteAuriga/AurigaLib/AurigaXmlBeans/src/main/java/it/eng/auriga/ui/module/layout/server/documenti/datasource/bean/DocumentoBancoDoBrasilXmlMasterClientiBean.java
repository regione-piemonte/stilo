/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.math.BigDecimal;
import it.eng.document.NumeroColonna;


public class DocumentoBancoDoBrasilXmlMasterClientiBean {

	@NumeroColonna(numero="1")
	private String codiceCliente;		
	
	@NumeroColonna(numero="2")
	private String denominazioneCliente;
	
	@NumeroColonna(numero="3")
	private String accountNumber;
	
	@NumeroColonna(numero="4")
	private String codAccountClass;

    @NumeroColonna(numero="5")
	private String descAccountClass;
	
	@NumeroColonna(numero="6")
	private String codAgenziaBanca;		
	
	@NumeroColonna(numero="7")
	private String descAgenziaBanca;		
	
	@NumeroColonna(numero="8")
	private BigDecimal nrDocumenti;

	
	public String getCodiceCliente() {
		return codiceCliente;
	}

	
	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}

	
	public String getDenominazioneCliente() {
		return denominazioneCliente;
	}

	
	public void setDenominazioneCliente(String denominazioneCliente) {
		this.denominazioneCliente = denominazioneCliente;
	}

	
	public String getAccountNumber() {
		return accountNumber;
	}

	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
	public String getCodAccountClass() {
		return codAccountClass;
	}

	
	public void setCodAccountClass(String codAccountClass) {
		this.codAccountClass = codAccountClass;
	}

	
	public String getDescAccountClass() {
		return descAccountClass;
	}

	
	public void setDescAccountClass(String descAccountClass) {
		this.descAccountClass = descAccountClass;
	}

	
	public String getCodAgenziaBanca() {
		return codAgenziaBanca;
	}

	
	public void setCodAgenziaBanca(String codAgenziaBanca) {
		this.codAgenziaBanca = codAgenziaBanca;
	}

	
	public String getDescAgenziaBanca() {
		return descAgenziaBanca;
	}

	
	public void setDescAgenziaBanca(String descAgenziaBanca) {
		this.descAgenziaBanca = descAgenziaBanca;
	}

	
	public BigDecimal getNrDocumenti() {
		return nrDocumenti;
	}

	
	public void setNrDocumenti(BigDecimal nrDocumenti) {
		this.nrDocumenti = nrDocumenti;
	}
	
	
	}
