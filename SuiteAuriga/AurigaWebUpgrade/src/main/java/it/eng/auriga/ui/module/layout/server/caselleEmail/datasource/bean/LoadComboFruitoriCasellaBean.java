/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class LoadComboFruitoriCasellaBean extends VisualBean {

	@NumeroColonna(numero = "1")
	private String idFruitoreCasella;

	@NumeroColonna(numero = "2")
	private String codice;

	@NumeroColonna(numero = "3")
	private String denominazione;

	@NumeroColonna(numero = "4")
	private String idUo;

	@NumeroColonna(numero = "5")
	private String tipoUo;

	public String getIdFruitoreCasella() {
		return idFruitoreCasella;
	}

	public void setIdFruitoreCasella(String idFruitoreCasella) {
		this.idFruitoreCasella = idFruitoreCasella;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getTipoUo() {
		return tipoUo;
	}

	public void setTipoUo(String tipoUo) {
		this.tipoUo = tipoUo;
	}

}
