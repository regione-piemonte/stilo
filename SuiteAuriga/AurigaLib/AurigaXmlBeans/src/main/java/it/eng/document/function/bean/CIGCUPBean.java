/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class CIGCUPBean {
	
	@NumeroColonna(numero = "1")
	private String codiceCIG;
	
	@NumeroColonna(numero = "2")
	private String codiceCUP;

	@NumeroColonna(numero = "3")
	private String numGara;
	
	public String getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}

	public String getCodiceCUP() {
		return codiceCUP;
	}

	public void setCodiceCUP(String codiceCUP) {
		this.codiceCUP = codiceCUP;
	}

	public String getNumGara() {
		return numGara;
	}

	public void setNumGara(String numGara) {
		this.numGara = numGara;
	}
	
}