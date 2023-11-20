/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PropertyFilterConfigurationInfoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PropertyFilterConfigurationInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="operation" type="{common.acaris.acta.doqui.it}enumPropertyFilterOperation"/&gt;
 *         &lt;element name="isAllAllowed" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="isNoneAllowed" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="isListAllowed" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyFilterConfigurationInfoType", propOrder = {
    "operation",
    "isAllAllowed",
    "isNoneAllowed",
    "isListAllowed"
})
public class PropertyFilterConfigurationInfoType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumPropertyFilterOperation operation;
    protected boolean isAllAllowed;
    protected boolean isNoneAllowed;
    protected boolean isListAllowed;

    /**
     * Recupera il valore della proprietà operation.
     * 
     * @return
     *     possible object is
     *     {@link EnumPropertyFilterOperation }
     *     
     */
    public EnumPropertyFilterOperation getOperation() {
        return operation;
    }

    /**
     * Imposta il valore della proprietà operation.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumPropertyFilterOperation }
     *     
     */
    public void setOperation(EnumPropertyFilterOperation value) {
        this.operation = value;
    }

    /**
     * Recupera il valore della proprietà isAllAllowed.
     * 
     */
    public boolean isIsAllAllowed() {
        return isAllAllowed;
    }

    /**
     * Imposta il valore della proprietà isAllAllowed.
     * 
     */
    public void setIsAllAllowed(boolean value) {
        this.isAllAllowed = value;
    }

    /**
     * Recupera il valore della proprietà isNoneAllowed.
     * 
     */
    public boolean isIsNoneAllowed() {
        return isNoneAllowed;
    }

    /**
     * Imposta il valore della proprietà isNoneAllowed.
     * 
     */
    public void setIsNoneAllowed(boolean value) {
        this.isNoneAllowed = value;
    }

    /**
     * Recupera il valore della proprietà isListAllowed.
     * 
     */
    public boolean isIsListAllowed() {
        return isListAllowed;
    }

    /**
     * Imposta il valore della proprietà isListAllowed.
     * 
     */
    public void setIsListAllowed(boolean value) {
        this.isListAllowed = value;
    }

}
