/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.certverify.clientws;

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
 *         &lt;element name="verificationID" type="{verify.cryptoutil.eng.it}VerificationTypes" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificationInfo", propOrder = {
    "verificationID"
})
public class VerificationInfo {

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
