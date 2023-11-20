/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.math.BigDecimal;
import it.eng.document.NumeroColonna;


public class DocumentoBancoDoBrasilXmlDetailDataBean {

	@NumeroColonna(numero="1")
	private String dataDocumento;
	
	@NumeroColonna(numero="2")
	private String dataElaborazione;
		
	@NumeroColonna(numero="3")
	private String codAccountClass;

    @NumeroColonna(numero="4")
	private String descAccountClass;
    
    @NumeroColonna(numero="5")
	private String codTipoDocumento;
	
    @NumeroColonna(numero="6")
	private String descTipoDocumento;
    
    @NumeroColonna(numero="7")
	private String codiceCliente;		
	
	@NumeroColonna(numero="8")
	private String denominazioneCliente;
	
	@NumeroColonna(numero="9")
	private String accountNumber;
	
	@NumeroColonna(numero="10")
	private String codBanca;
	
	@NumeroColonna(numero="11")
	private String denominazioneBanca;
	
	@NumeroColonna(numero="12")
	private String codAgenziaBanca;		
	
	@NumeroColonna(numero="13")
	private String denominazioneAgenziaBanca;		
		
	@NumeroColonna(numero="14")
	private String history;
    
	@NumeroColonna(numero="15")
	private String codTemplate;
	
	@NumeroColonna(numero="16")
	private String flgTemplateDefinitivo;
	
	@NumeroColonna(numero="17")
	private String flgStampaCliente;
	
	@NumeroColonna(numero="18")
	private String flgStampaSede;
	
	@NumeroColonna(numero="19")
	private String filename;
	
	@NumeroColonna(numero="20")
	private String uriUltimoFilename;
	
	@NumeroColonna(numero="21")
	private BigDecimal nrVersioni;

	@NumeroColonna(numero="22")
	private BigDecimal idDoc;
		
	@NumeroColonna(numero="23")
	private BigDecimal idUd;
	
	
	public String getDataDocumento() {
		return dataDocumento;
	}

	
	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	
	public String getDataElaborazione() {
		return dataElaborazione;
	}

	
	public void setDataElaborazione(String dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
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

	
	public String getCodTipoDocumento() {
		return codTipoDocumento;
	}

	
	public void setCodTipoDocumento(String codTipoDocumento) {
		this.codTipoDocumento = codTipoDocumento;
	}

	
	public String getDescTipoDocumento() {
		return descTipoDocumento;
	}

	
	public void setDescTipoDocumento(String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}

	
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

	
	public String getCodBanca() {
		return codBanca;
	}

	
	public void setCodBanca(String codBanca) {
		this.codBanca = codBanca;
	}

	
	public String getDenominazioneBanca() {
		return denominazioneBanca;
	}

	
	public void setDenominazioneBanca(String denominazioneBanca) {
		this.denominazioneBanca = denominazioneBanca;
	}

	
	public String getCodAgenziaBanca() {
		return codAgenziaBanca;
	}

	
	public void setCodAgenziaBanca(String codAgenziaBanca) {
		this.codAgenziaBanca = codAgenziaBanca;
	}

	
	public String getDenominazioneAgenziaBanca() {
		return denominazioneAgenziaBanca;
	}

	
	public void setDenominazioneAgenziaBanca(String denominazioneAgenziaBanca) {
		this.denominazioneAgenziaBanca = denominazioneAgenziaBanca;
	}

	
	public String getHistory() {
		return history;
	}

	
	public void setHistory(String history) {
		this.history = history;
	}

	
	public String getCodTemplate() {
		return codTemplate;
	}

	
	public void setCodTemplate(String codTemplate) {
		this.codTemplate = codTemplate;
	}

	
	public String getFlgTemplateDefinitivo() {
		return flgTemplateDefinitivo;
	}

	
	public void setFlgTemplateDefinitivo(String flgTemplateDefinitivo) {
		this.flgTemplateDefinitivo = flgTemplateDefinitivo;
	}

	
	public String getFlgStampaCliente() {
		return flgStampaCliente;
	}

	
	public void setFlgStampaCliente(String flgStampaCliente) {
		this.flgStampaCliente = flgStampaCliente;
	}

	
	public String getFlgStampaSede() {
		return flgStampaSede;
	}

	
	public void setFlgStampaSede(String flgStampaSede) {
		this.flgStampaSede = flgStampaSede;
	}

	
	public String getFilename() {
		return filename;
	}

	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	public String getUriUltimoFilename() {
		return uriUltimoFilename;
	}

	
	public void setUriUltimoFilename(String uriUltimoFilename) {
		this.uriUltimoFilename = uriUltimoFilename;
	}

	
	public BigDecimal getNrVersioni() {
		return nrVersioni;
	}

	
	public void setNrVersioni(BigDecimal nrVersioni) {
		this.nrVersioni = nrVersioni;
	}


	
	public BigDecimal getIdDoc() {
		return idDoc;
	}


	
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}


	public BigDecimal getIdUd() {
		return idUd;
	}


	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}


	
	
	
	
	}
