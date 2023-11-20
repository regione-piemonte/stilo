/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.certverify.clientws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificationResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerificationResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="verificationID" type="{verify.cryptoutil.eng.it}VerificationTypes" form="qualified"/>
 *         &lt;element name="verificationStatus" type="{verify.cryptoutil.eng.it}CertVerificationStatusType" form="qualified"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="warnings" minOccurs="0" form="qualified">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="warnMesage" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" form="qualified"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificationResultType", propOrder = {
    "verificationID",
    "verificationStatus",
    "errorMessage",
    "warnings"
})
public class VerificationResultType {

    @XmlElement(required = true)
    protected VerificationTypes verificationID;
    @XmlElement(required = true)
    protected CertVerificationStatusType verificationStatus;
    protected String errorMessage;
    protected VerificationResultType.Warnings warnings;

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

    /**
     * Gets the value of the verificationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CertVerificationStatusType }
     *     
     */
    public CertVerificationStatusType getVerificationStatus() {
        return verificationStatus;
    }

    /**
     * Sets the value of the verificationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CertVerificationStatusType }
     *     
     */
    public void setVerificationStatus(CertVerificationStatusType value) {
        this.verificationStatus = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationResultType.Warnings }
     *     
     */
    public VerificationResultType.Warnings getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationResultType.Warnings }
     *     
     */
    public void setWarnings(VerificationResultType.Warnings value) {
        this.warnings = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="warnMesage" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" form="qualified"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "warnMesage"
    })
    public static class Warnings {

        @XmlElement(required = true)
        protected List<String> warnMesage;

        /**
         * Gets the value of the warnMesage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the warnMesage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWarnMesage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getWarnMesage() {
            if (warnMesage == null) {
                warnMesage = new ArrayList<String>();
            }
            return this.warnMesage;
        }

    }

}
