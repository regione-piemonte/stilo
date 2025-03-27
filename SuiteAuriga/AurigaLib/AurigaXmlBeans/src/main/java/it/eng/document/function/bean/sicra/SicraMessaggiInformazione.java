/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean.sicra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraMessaggiInformazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> informazioni = new ArrayList<String>(0);

	public List<String> getInformazioni() {
		return informazioni;
	}

	public void setInformazioni(List<String> informazioni) {
		this.informazioni = informazioni;
	}
	
}
