/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.12 at 12:00:07 PM CEST 
//


package it.eng.aurigamailbusiness.segnatura;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Origine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Origine">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}IndirizzoTelematico"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Mittente"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Origine", propOrder = {
    "indirizzoTelematico",
    "mittente"
})
public class Origine {

    @XmlElement(name = "IndirizzoTelematico", required = true)
    protected IndirizzoTelematico indirizzoTelematico;
    @XmlElement(name = "Mittente", required = true)
    protected Mittente mittente;

    /**
     * Gets the value of the indirizzoTelematico property.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoTelematico }
     *     
     */
    public IndirizzoTelematico getIndirizzoTelematico() {
        return indirizzoTelematico;
    }

    /**
     * Sets the value of the indirizzoTelematico property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoTelematico }
     *     
     */
    public void setIndirizzoTelematico(IndirizzoTelematico value) {
        this.indirizzoTelematico = value;
    }

    /**
     * Gets the value of the mittente property.
     * 
     * @return
     *     possible object is
     *     {@link Mittente }
     *     
     */
    public Mittente getMittente() {
        return mittente;
    }

    /**
     * Sets the value of the mittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mittente }
     *     
     */
    public void setMittente(Mittente value) {
        this.mittente = value;
    }

}
