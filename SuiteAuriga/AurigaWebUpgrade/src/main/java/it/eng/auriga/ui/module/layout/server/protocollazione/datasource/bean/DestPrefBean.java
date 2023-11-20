/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DestPrefBean {

	// 1: UO o SV + ID_UO/ID_SCRIVANIA
	@NumeroColonna(numero = "1")
	private String idDestPref;

	// 2: Livelli
	@NumeroColonna(numero = "2")
	private String codRapido;

	// 3: Denominazione
	@NumeroColonna(numero = "3")
	private String denominazione;
	
	@NumeroColonna(numero = "4")
	private String idRubrica;
	
	@NumeroColonna(numero = "5")
	private String flgSelXAssegnazione;

	public String getIdDestPref() {
		return idDestPref;
	}

	public void setIdDestPref(String idDestPref) {
		this.idDestPref = idDestPref;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public String getIdRubrica() {
		return idRubrica;
	}

	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}

	public String getFlgSelXAssegnazione() {
		return flgSelXAssegnazione;
	}

	public void setFlgSelXAssegnazione(String flgSelXAssegnazione) {
		this.flgSelXAssegnazione = flgSelXAssegnazione;
	}

}
