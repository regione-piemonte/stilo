/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalvaFascicoloIn extends AllegatiBean implements Serializable{

	private Boolean isCaricaPraticaPregressa;
	private XmlFascicoloIn xmlFascicolo;

	public Boolean getIsCaricaPraticaPregressa() {
		return isCaricaPraticaPregressa;
	}

	public void setIsCaricaPraticaPregressa(Boolean isCaricaPraticaPregressa) {
		this.isCaricaPraticaPregressa = isCaricaPraticaPregressa;
	}
	
	public void setXmlFascicolo(XmlFascicoloIn xmlFascicolo) {
		this.xmlFascicolo = xmlFascicolo;
	}

	public XmlFascicoloIn getXmlFascicolo() {
		return xmlFascicolo;
	}
	
}
