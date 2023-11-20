/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class SupportiOrigDoc {

	/**
	 * codice del supporto originale - cartaceo, digitale, misto -  del documento (in entrata o uscita)
	 */
	@NumeroColonna(numero = "1")
	private String codSupporto;
	
	/**
	 * descrizione del supporto 
	 */
	@NumeroColonna(numero = "2")
	private String descrizioneSupporto;

	
	public String getCodSupporto() {
		return codSupporto;
	}

	public void setCodSupporto(String codSupporto) {
		this.codSupporto = codSupporto;
	}

	public String getDescrizioneSupporto() {
		return descrizioneSupporto;
	}

	public void setDescrizioneSupporto(String descrizioneSupporto) {
		this.descrizioneSupporto = descrizioneSupporto;
	}
}