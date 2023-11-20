/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SigVerifyResultType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SigVerifyResultType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SigVerifyResult" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="formatResult" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="envelopeFormat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="SignatureValResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
 *                   &lt;element name="CertExpirationResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
 *                   &lt;element name="CRLResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
 *                   &lt;element name="CAReliabilityResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
 *                   &lt;element name="DetectionCodeResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
 *                   &lt;element name="timestampVerificationResult" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="timeStampInfo" type="{it.eng.fileoperation.ws}TimeStampInfotype" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="child" type="{it.eng.fileoperation.ws}SigVerifyResultType" minOccurs="0"/&gt;
 *                   &lt;element name="signerInformations" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="signerInformation" type="{it.eng.fileoperation.ws}signerInformationType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="message" type="{it.eng.fileoperation.ws.base}messageType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SigVerifyResultType", propOrder = {
    "sigVerifyResult",
    "message"
})
@XmlSeeAlso({
    SignerInformationType.class
})
public class SigVerifyResultType
    extends AbstractResponseOperationType
{

    @XmlElement(name = "SigVerifyResult")
    protected SigVerifyResultType.SigVerifyResult sigVerifyResult;
    protected MessageType message;

    /**
     * Recupera il valore della proprietà sigVerifyResult.
     * 
     * @return
     *     possible object is
     *     {@link SigVerifyResultType.SigVerifyResult }
     *     
     */
    public SigVerifyResultType.SigVerifyResult getSigVerifyResult() {
        return sigVerifyResult;
    }

    /**
     * Imposta il valore della proprietà sigVerifyResult.
     * 
     * @param value
     *     allowed object is
     *     {@link SigVerifyResultType.SigVerifyResult }
     *     
     */
    public void setSigVerifyResult(SigVerifyResultType.SigVerifyResult value) {
        this.sigVerifyResult = value;
    }

    /**
     * Recupera il valore della proprietà message.
     * 
     * @return
     *     possible object is
     *     {@link MessageType }
     *     
     */
    public MessageType getMessage() {
        return message;
    }

    /**
     * Imposta il valore della proprietà message.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageType }
     *     
     */
    public void setMessage(MessageType value) {
        this.message = value;
    }


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
     *         &lt;element name="formatResult" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="envelopeFormat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/extension&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="SignatureValResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
     *         &lt;element name="CertExpirationResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
     *         &lt;element name="CRLResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
     *         &lt;element name="CAReliabilityResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
     *         &lt;element name="DetectionCodeResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" minOccurs="0"/&gt;
     *         &lt;element name="timestampVerificationResult" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="timeStampInfo" type="{it.eng.fileoperation.ws}TimeStampInfotype" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="child" type="{it.eng.fileoperation.ws}SigVerifyResultType" minOccurs="0"/&gt;
     *         &lt;element name="signerInformations" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="signerInformation" type="{it.eng.fileoperation.ws}signerInformationType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "formatResult",
        "signatureValResult",
        "certExpirationResult",
        "crlResult",
        "caReliabilityResult",
        "detectionCodeResult",
        "timestampVerificationResult",
        "child",
        "signerInformations"
    })
    public static class SigVerifyResult {

        protected SigVerifyResultType.SigVerifyResult.FormatResult formatResult;
        @XmlElement(name = "SignatureValResult")
        protected AbstractResponseOperationType signatureValResult;
        @XmlElement(name = "CertExpirationResult")
        protected AbstractResponseOperationType certExpirationResult;
        @XmlElement(name = "CRLResult")
        protected AbstractResponseOperationType crlResult;
        @XmlElement(name = "CAReliabilityResult")
        protected AbstractResponseOperationType caReliabilityResult;
        @XmlElement(name = "DetectionCodeResult")
        protected AbstractResponseOperationType detectionCodeResult;
        protected SigVerifyResultType.SigVerifyResult.TimestampVerificationResult timestampVerificationResult;
        protected SigVerifyResultType child;
        protected SigVerifyResultType.SigVerifyResult.SignerInformations signerInformations;

        /**
         * Recupera il valore della proprietà formatResult.
         * 
         * @return
         *     possible object is
         *     {@link SigVerifyResultType.SigVerifyResult.FormatResult }
         *     
         */
        public SigVerifyResultType.SigVerifyResult.FormatResult getFormatResult() {
            return formatResult;
        }

        /**
         * Imposta il valore della proprietà formatResult.
         * 
         * @param value
         *     allowed object is
         *     {@link SigVerifyResultType.SigVerifyResult.FormatResult }
         *     
         */
        public void setFormatResult(SigVerifyResultType.SigVerifyResult.FormatResult value) {
            this.formatResult = value;
        }

        /**
         * Recupera il valore della proprietà signatureValResult.
         * 
         * @return
         *     possible object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public AbstractResponseOperationType getSignatureValResult() {
            return signatureValResult;
        }

        /**
         * Imposta il valore della proprietà signatureValResult.
         * 
         * @param value
         *     allowed object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public void setSignatureValResult(AbstractResponseOperationType value) {
            this.signatureValResult = value;
        }

        /**
         * Recupera il valore della proprietà certExpirationResult.
         * 
         * @return
         *     possible object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public AbstractResponseOperationType getCertExpirationResult() {
            return certExpirationResult;
        }

        /**
         * Imposta il valore della proprietà certExpirationResult.
         * 
         * @param value
         *     allowed object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public void setCertExpirationResult(AbstractResponseOperationType value) {
            this.certExpirationResult = value;
        }

        /**
         * Recupera il valore della proprietà crlResult.
         * 
         * @return
         *     possible object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public AbstractResponseOperationType getCRLResult() {
            return crlResult;
        }

        /**
         * Imposta il valore della proprietà crlResult.
         * 
         * @param value
         *     allowed object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public void setCRLResult(AbstractResponseOperationType value) {
            this.crlResult = value;
        }

        /**
         * Recupera il valore della proprietà caReliabilityResult.
         * 
         * @return
         *     possible object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public AbstractResponseOperationType getCAReliabilityResult() {
            return caReliabilityResult;
        }

        /**
         * Imposta il valore della proprietà caReliabilityResult.
         * 
         * @param value
         *     allowed object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public void setCAReliabilityResult(AbstractResponseOperationType value) {
            this.caReliabilityResult = value;
        }

        /**
         * Recupera il valore della proprietà detectionCodeResult.
         * 
         * @return
         *     possible object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public AbstractResponseOperationType getDetectionCodeResult() {
            return detectionCodeResult;
        }

        /**
         * Imposta il valore della proprietà detectionCodeResult.
         * 
         * @param value
         *     allowed object is
         *     {@link AbstractResponseOperationType }
         *     
         */
        public void setDetectionCodeResult(AbstractResponseOperationType value) {
            this.detectionCodeResult = value;
        }

        /**
         * Recupera il valore della proprietà timestampVerificationResult.
         * 
         * @return
         *     possible object is
         *     {@link SigVerifyResultType.SigVerifyResult.TimestampVerificationResult }
         *     
         */
        public SigVerifyResultType.SigVerifyResult.TimestampVerificationResult getTimestampVerificationResult() {
            return timestampVerificationResult;
        }

        /**
         * Imposta il valore della proprietà timestampVerificationResult.
         * 
         * @param value
         *     allowed object is
         *     {@link SigVerifyResultType.SigVerifyResult.TimestampVerificationResult }
         *     
         */
        public void setTimestampVerificationResult(SigVerifyResultType.SigVerifyResult.TimestampVerificationResult value) {
            this.timestampVerificationResult = value;
        }

        /**
         * Recupera il valore della proprietà child.
         * 
         * @return
         *     possible object is
         *     {@link SigVerifyResultType }
         *     
         */
        public SigVerifyResultType getChild() {
            return child;
        }

        /**
         * Imposta il valore della proprietà child.
         * 
         * @param value
         *     allowed object is
         *     {@link SigVerifyResultType }
         *     
         */
        public void setChild(SigVerifyResultType value) {
            this.child = value;
        }

        /**
         * Recupera il valore della proprietà signerInformations.
         * 
         * @return
         *     possible object is
         *     {@link SigVerifyResultType.SigVerifyResult.SignerInformations }
         *     
         */
        public SigVerifyResultType.SigVerifyResult.SignerInformations getSignerInformations() {
            return signerInformations;
        }

        /**
         * Imposta il valore della proprietà signerInformations.
         * 
         * @param value
         *     allowed object is
         *     {@link SigVerifyResultType.SigVerifyResult.SignerInformations }
         *     
         */
        public void setSignerInformations(SigVerifyResultType.SigVerifyResult.SignerInformations value) {
            this.signerInformations = value;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="envelopeFormat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/extension&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "envelopeFormat"
        })
        public static class FormatResult
            extends AbstractResponseOperationType
        {

            @XmlElement(required = true)
            protected String envelopeFormat;

            /**
             * Recupera il valore della proprietà envelopeFormat.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEnvelopeFormat() {
                return envelopeFormat;
            }

            /**
             * Imposta il valore della proprietà envelopeFormat.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEnvelopeFormat(String value) {
                this.envelopeFormat = value;
            }

        }


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
         *         &lt;element name="signerInformation" type="{it.eng.fileoperation.ws}signerInformationType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "signerInformation"
        })
        public static class SignerInformations {

            @XmlElement(nillable = true)
            protected List<SignerInformationType> signerInformation;

            /**
             * Gets the value of the signerInformation property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the signerInformation property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSignerInformation().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link SignerInformationType }
             * 
             * 
             */
            public List<SignerInformationType> getSignerInformation() {
                if (signerInformation == null) {
                    signerInformation = new ArrayList<SignerInformationType>();
                }
                return this.signerInformation;
            }

        }


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
         *         &lt;element name="timeStampInfo" type="{it.eng.fileoperation.ws}TimeStampInfotype" maxOccurs="unbounded" minOccurs="0"/&gt;
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
            "timeStampInfo"
        })
        public static class TimestampVerificationResult {

            @XmlElement(nillable = true)
            protected List<TimeStampInfotype> timeStampInfo;

            /**
             * Gets the value of the timeStampInfo property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the timeStampInfo property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTimeStampInfo().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link TimeStampInfotype }
             * 
             * 
             */
            public List<TimeStampInfotype> getTimeStampInfo() {
                if (timeStampInfo == null) {
                    timeStampInfo = new ArrayList<TimeStampInfotype>();
                }
                return this.timeStampInfo;
            }

        }

    }

}
