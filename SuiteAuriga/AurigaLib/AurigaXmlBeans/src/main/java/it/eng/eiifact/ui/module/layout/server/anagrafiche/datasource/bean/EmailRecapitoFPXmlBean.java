/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import it.eng.document.NumeroColonna;

/**
 * Rappresentazione java dei dati estratti dalla store sottoforma di xml
 * @author ottavio passalacqua
 *
 */
public class EmailRecapitoFPXmlBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "1")
	private String idEmailRecapitoFP;
	
	@NumeroColonna(numero = "2")
	private String codApplEistanzaAppl;

	@NumeroColonna(numero = "3")
	private String rifAmministrazione;

	@NumeroColonna(numero = "4")
	private String emailRecapitoFP;
	
	@NumeroColonna(numero = "5")
	private String denominazione;

	public String getIdEmailRecapitoFP() {
		return idEmailRecapitoFP;
	}

	public void setIdEmailRecapitoFP(String idEmailRecapitoFP) {
		this.idEmailRecapitoFP = idEmailRecapitoFP;
	}

	public String getRifAmministrazione() {
		return rifAmministrazione;
	}

	public void setRifAmministrazione(String rifAmministrazione) {
		this.rifAmministrazione = rifAmministrazione;
	}

	public String getEmailRecapitoFP() {
		return emailRecapitoFP;
	}

	public void setEmailRecapitoFP(String emailRecapitoFP) {
		this.emailRecapitoFP = emailRecapitoFP;
	}

	public String getCodApplEistanzaAppl() {
		return codApplEistanzaAppl;
	}

	public void setCodApplEistanzaAppl(String codApplEistanzaAppl) {
		this.codApplEistanzaAppl = codApplEistanzaAppl;
	}

	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	
}