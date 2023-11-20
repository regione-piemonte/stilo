/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiDistintaSpedizioneOut {
	
	//	1: Nro di protocollo/registrazione
	@NumeroColonna(numero = "1")
	private String nroReg;
	
	//	2: Sigla del registro di registrazione
	@NumeroColonna(numero = "2")
	private String siglaReg;
	
	//	3: Data di registrazione
	@NumeroColonna(numero = "3")
	private String dataOraReg;
	
	//	4: Denominazione / cognome e nome del destinatario
	@NumeroColonna(numero = "4")
	private String destinatario;
	
	//	5: Indirizzo di spedizione
	@NumeroColonna(numero = "5")
	private String indirizzoSpedizione;
	
	//	6: Mezzo di trasmissione
	@NumeroColonna(numero = "6")
	private String descMezzoTrasmissione;

	public String getNroReg() {
		return nroReg;
	}

	public String getSiglaReg() {
		return siglaReg;
	}

	public String getDataOraReg() {
		return dataOraReg;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}

	public String getDescMezzoTrasmissione() {
		return descMezzoTrasmissione;
	}

	public void setNroReg(String nroReg) {
		this.nroReg = nroReg;
	}

	public void setSiglaReg(String siglaReg) {
		this.siglaReg = siglaReg;
	}

	public void setDataOraReg(String dataOraReg) {
		this.dataOraReg = dataOraReg;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public void setDescMezzoTrasmissione(String descMezzoTrasmissione) {
		this.descMezzoTrasmissione = descMezzoTrasmissione;
	}
	
}
