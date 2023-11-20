/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class CUIBean {
	
	@NumeroColonna(numero = "1")
	private String codiceCUI;
	
	@NumeroColonna(numero = "2")
	private String annoRif;

	public String getCodiceCUI() {
		return codiceCUI;
	}

	public void setCodiceCUI(String codiceCUI) {
		this.codiceCUI = codiceCUI;
	}

	public String getAnnoRif() {
		return annoRif;
	}

	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}
	
}