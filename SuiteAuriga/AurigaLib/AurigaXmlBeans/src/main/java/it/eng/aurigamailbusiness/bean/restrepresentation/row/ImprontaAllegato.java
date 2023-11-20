/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="improntaAllegato")
public class ImprontaAllegato {
	
	@NumeroColonna(numero="1")
	@XmlElement(name="impronta")
	private String impronta;

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

}
