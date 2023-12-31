/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 10:39:55 AM CET 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerificationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="verificationID" type="{verify.cryptoutil.eng.it}VerificationTypes"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificationInfo", namespace = "verify.cryptoutil.eng.it", propOrder = {
    "verificationID"
})
public class VerificationInfo
    implements Serializable
{

    @XmlElement(required = true)
    protected VerificationTypes verificationID;

    /**
     * Gets the value of the verificationID property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationTypes }
     *     
     */
    public VerificationTypes getVerificationID() {
        return verificationID;
    }

    /**
     * Sets the value of the verificationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationTypes }
     *     
     */
    public void setVerificationID(VerificationTypes value) {
        this.verificationID = value;
    }

}
