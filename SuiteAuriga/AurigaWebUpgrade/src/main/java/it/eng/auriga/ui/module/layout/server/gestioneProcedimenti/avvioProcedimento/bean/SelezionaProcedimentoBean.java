/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class SelezionaProcedimentoBean extends VisualBean {

	private String idProcessType;
	private String nome;
	public String getIdProcessType() {
		return idProcessType;
	}
	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
