/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class AreeCommercialiXmlBean {

	@NumeroColonna(numero="1")
	private String idAreaCommerciali;
	
	
	public String getIdAreaCommerciali() {
		return idAreaCommerciali;
	}

	public void setIdAreaCommerciali(String idAreaCommerciali) {
		this.idAreaCommerciali = idAreaCommerciali;
	}

	

	
}
