/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine.
// Generato il: 2018.06.06 alle 03:56:55 PM CEST
//

package it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per anonymous complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Esito" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}ResultType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ResponseActionsOnDocList")
public class ResponseActionsOnDocList {

	@Override
	public String toString() {
		return "ResponseActionsOnDocList [esito=" + esito + "]";
	}

	@XmlAttribute(name = "Esito", required = true)
	protected ResultType esito;

	/**
	 * Recupera il valore della proprietà esito.
	 * 
	 * @return possible object is {@link ResultType }
	 * 
	 */
	public ResultType getEsito() {
		return esito;
	}

	/**
	 * Imposta il valore della proprietà esito.
	 * 
	 * @param value
	 *            allowed object is {@link ResultType }
	 * 
	 */
	public void setEsito(ResultType value) {
		this.esito = value;
	}

}
