/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import java.io.Serializable;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class TagAttoXmlBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String codiceTag;

	public String getCodiceTag() {
		return codiceTag;
	}

	public void setCodiceTag(String codiceTag) {
		this.codiceTag = codiceTag;
	}

}
