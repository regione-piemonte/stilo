/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class SubProfiloBean implements Serializable {

	@NumeroColonna(numero = "1")
	private String idSubProfilo;
	
	@NumeroColonna(numero = "2")
	private String nomeSubProfilo;

	public String getIdSubProfilo() {
		return idSubProfilo;
	}

	public void setIdSubProfilo(String idSubProfilo) {
		this.idSubProfilo = idSubProfilo;
	}

	public String getNomeSubProfilo() {
		return nomeSubProfilo;
	}

	public void setNomeSubProfilo(String nomeSubProfilo) {
		this.nomeSubProfilo = nomeSubProfilo;
	}

}