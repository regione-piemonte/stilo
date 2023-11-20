/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import java.io.Serializable;

public class MezziTrasmissioneFilterBean implements Serializable {

	@NumeroColonna(numero = "1")
	private String idMezzoTrasmissione;
	
	@NumeroColonna(numero = "2")
	private String desMezzoTrasmissione;


	public String getIdMezzoTrasmissione() {
		return idMezzoTrasmissione;
	}


	public String getDesMezzoTrasmissione() {
		return desMezzoTrasmissione;
	}


	public void setIdMezzoTrasmissione(String idMezzoTrasmissione) {
		this.idMezzoTrasmissione = idMezzoTrasmissione;
	}


	public void setDesMezzoTrasmissione(String desMezzoTrasmissione) {
		this.desMezzoTrasmissione = desMezzoTrasmissione;
	}

}
