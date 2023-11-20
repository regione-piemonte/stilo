/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AbstractResponseOperationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AbstractResponseOperationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="verificationStatus" type="{it.eng.fileoperation.ws.base}VerificationStatusType" form="qualified"/&gt;
 *         &lt;element name="errorsMessage" minOccurs="0" form="qualified"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="errMessage" type="{it.eng.fileoperation.ws.base}messageType" maxOccurs="unbounded" form="qualified"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="warnings" minOccurs="0" form="qualified"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="warnMessage" type="{it.eng.fileoperation.ws.base}messageType" maxOccurs="unbounded" form="qualified"/&gt;
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
@XmlType(name = "AbstractResponseOperationType", namespace = "it.eng.fileoperation.ws.base", propOrder = {
    "verificationStatus",
    "errorsMessage",
    "warnings"
})
@XmlSeeAlso({
    ResponseTimbroType.class,
    ResponseCompletaFirmaPadesType.class,
    ResponseCopiaConformeType.class,
    ResponsePreparaFirmaPadesType.class,
    ResponseUnpackType.class,
    TimeStampInfotype.class,
    ResponseFileCompressType.class,
    ResponseUnpackMultipartType.class,
    ResponseSigVerify.class,
    SigVerifyResultType.SigVerifyResult.FormatResult.class,
    it.eng.fileOperation.clientws.SignerInformationType.Marca.class,
    SigVerifyResultType.class,
    ResponsePdfConvResultType.class,
    ResponseFileDigestType.class,
    ResponseCodeDetector.class,
    ResponseFormatRecognitionType.class,
    ResponseRapportoVerificaType.class
})
public class AbstractResponseOperationType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected VerificationStatusType verificationStatus;
    protected AbstractResponseOperationType.ErrorsMessage errorsMessage;
    protected AbstractResponseOperationType.Warnings warnings;

    /**
     * Recupera il valore della proprietà verificationStatus.
     * 
     * @return
     *     possible object is
     *     {@link VerificationStatusType }
     *     
     */
    public VerificationStatusType getVerificationStatus() {
        return verificationStatus;
    }

    /**
     * Imposta il valore della proprietà verificationStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationStatusType }
     *     
     */
    public void setVerificationStatus(VerificationStatusType value) {
        this.verificationStatus = value;
    }

    /**
     * Recupera il valore della proprietà errorsMessage.
     * 
     * @return
     *     possible object is
     *     {@link AbstractResponseOperationType.ErrorsMessage }
     *     
     */
    public AbstractResponseOperationType.ErrorsMessage getErrorsMessage() {
        return errorsMessage;
    }

    /**
     * Imposta il valore della proprietà errorsMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractResponseOperationType.ErrorsMessage }
     *     
     */
    public void setErrorsMessage(AbstractResponseOperationType.ErrorsMessage value) {
        this.errorsMessage = value;
    }

    /**
     * Recupera il valore della proprietà warnings.
     * 
     * @return
     *     possible object is
     *     {@link AbstractResponseOperationType.Warnings }
     *     
     */
    public AbstractResponseOperationType.Warnings getWarnings() {
        return warnings;
    }

    /**
     * Imposta il valore della proprietà warnings.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractResponseOperationType.Warnings }
     *     
     */
    public void setWarnings(AbstractResponseOperationType.Warnings value) {
        this.warnings = value;
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
     *         &lt;element name="errMessage" type="{it.eng.fileoperation.ws.base}messageType" maxOccurs="unbounded" form="qualified"/&gt;
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
        "errMessage"
    })
    public static class ErrorsMessage {

        @XmlElement(namespace = "it.eng.fileoperation.ws.base", required = true)
        protected List<MessageType> errMessage;

        /**
         * Gets the value of the errMessage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the errMessage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getErrMessage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MessageType }
         * 
         * 
         */
        public List<MessageType> getErrMessage() {
            if (errMessage == null) {
                errMessage = new ArrayList<MessageType>();
            }
            return this.errMessage;
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
     *         &lt;element name="warnMessage" type="{it.eng.fileoperation.ws.base}messageType" maxOccurs="unbounded" form="qualified"/&gt;
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
        "warnMessage"
    })
    public static class Warnings {

        @XmlElement(namespace = "it.eng.fileoperation.ws.base", required = true)
        protected List<MessageType> warnMessage;

        /**
         * Gets the value of the warnMessage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the warnMessage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWarnMessage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MessageType }
         * 
         * 
         */
        public List<MessageType> getWarnMessage() {
            if (warnMessage == null) {
                warnMessage = new ArrayList<MessageType>();
            }
            return this.warnMessage;
        }

    }

}
