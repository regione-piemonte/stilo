/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class AreeCommercialiBean {

	@NumeroColonna(numero="1")
	private String idAreaCommerciali;
	
	@NumeroColonna(numero="2")
	private String denominazioneAreaCommerciali;

	public String getIdAreaCommerciali() {
		return idAreaCommerciali;
	}

	public void setIdAreaCommerciali(String idAreaCommerciali) {
		this.idAreaCommerciali = idAreaCommerciali;
	}

	public String getDenominazioneAreaCommerciali() {
		return denominazioneAreaCommerciali;
	}

	public void setDenominazioneAreaCommerciali(String denominazioneAreaCommerciali) {
		this.denominazioneAreaCommerciali = denominazioneAreaCommerciali;
	}


	
}
