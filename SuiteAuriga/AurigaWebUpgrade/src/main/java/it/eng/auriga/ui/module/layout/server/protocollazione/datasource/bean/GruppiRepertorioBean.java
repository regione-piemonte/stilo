/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class GruppiRepertorioBean extends VisualBean{

	@NumeroColonna(numero = "1")
	private String key;
	
	@NumeroColonna(numero = "2")
	private String value;
	
	@NumeroColonna(numero = "3")
	private String flgForzaSceltaTipoDoc;
	
	@NumeroColonna(numero = "4")
	private String codCategoria;
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFlgForzaSceltaTipoDoc() {
		return flgForzaSceltaTipoDoc;
	}

	public void setFlgForzaSceltaTipoDoc(String flgForzaSceltaTipoDoc) {
		this.flgForzaSceltaTipoDoc = flgForzaSceltaTipoDoc;
	}

	public String getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}

}
