/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ElencoAgibilita implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> agibilita;

	public List<String> getAgibilita() {
		return agibilita;
	}

	public void setAgibilita(List<String> agibilita) {
		this.agibilita = agibilita;
	}
	
	
}
