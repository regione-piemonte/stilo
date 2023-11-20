/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="object" type="{common.acaris.acta.doqui.it}PagingResponseType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "object"
})
@XmlRootElement(name = "queryResponse")
public class QueryResponse {

    @XmlElement(required = true)
    protected PagingResponseType object;

    /**
     * Recupera il valore della proprietà object.
     * 
     * @return
     *     possible object is
     *     {@link PagingResponseType }
     *     
     */
    public PagingResponseType getObject() {
        return object;
    }

    /**
     * Imposta il valore della proprietà object.
     * 
     * @param value
     *     allowed object is
     *     {@link PagingResponseType }
     *     
     */
    public void setObject(PagingResponseType value) {
        this.object = value;
    }

}
