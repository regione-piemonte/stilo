/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per InputRapportoVerificaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputRapportoVerificaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataRif" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="recursive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="childValidation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="signatureVerify" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CRLCheck" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                   &lt;element name="CAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                   &lt;element name="detectCode" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TimestampVerifiy" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="TSAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputRapportoVerificaType", propOrder = {
    "dataRif",
    "recursive",
    "childValidation",
    "signatureVerify",
    "timestampVerifiy"
})
public class InputRapportoVerificaType
    extends AbstractInputOperationType
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRif;
    @XmlElement(defaultValue = "true")
    protected Boolean recursive;
    @XmlElement(defaultValue = "false")
    protected Boolean childValidation;
    protected InputRapportoVerificaType.SignatureVerify signatureVerify;
    @XmlElement(name = "TimestampVerifiy")
    protected InputRapportoVerificaType.TimestampVerifiy timestampVerifiy;

    /**
     * Recupera il valore della proprietÓ dataRif.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRif() {
        return dataRif;
    }

    /**
     * Imposta il valore della proprietÓ dataRif.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRif(XMLGregorianCalendar value) {
        this.dataRif = value;
    }

    /**
     * Recupera il valore della proprietÓ recursive.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRecursive() {
        return recursive;
    }

    /**
     * Imposta il valore della proprietÓ recursive.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecursive(Boolean value) {
        this.recursive = value;
    }

    /**
     * Recupera il valore della proprietÓ childValidation.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isChildValidation() {
        return childValidation;
    }

    /**
     * Imposta il valore della proprietÓ childValidation.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setChildValidation(Boolean value) {
        this.childValidation = value;
    }

    /**
     * Recupera il valore della proprietÓ signatureVerify.
     * 
     * @return
     *     possible object is
     *     {@link InputRapportoVerificaType.SignatureVerify }
     *     
     */
    public InputRapportoVerificaType.SignatureVerify getSignatureVerify() {
        return signatureVerify;
    }

    /**
     * Imposta il valore della proprietÓ signatureVerify.
     * 
     * @param value
     *     allowed object is
     *     {@link InputRapportoVerificaType.SignatureVerify }
     *     
     */
    public void setSignatureVerify(InputRapportoVerificaType.SignatureVerify value) {
        this.signatureVerify = value;
    }

    /**
     * Recupera il valore della proprietÓ timestampVerifiy.
     * 
     * @return
     *     possible object is
     *     {@link InputRapportoVerificaType.TimestampVerifiy }
     *     
     */
    public InputRapportoVerificaType.TimestampVerifiy getTimestampVerifiy() {
        return timestampVerifiy;
    }

    /**
     * Imposta il valore della proprietÓ timestampVerifiy.
     * 
     * @param value
     *     allowed object is
     *     {@link InputRapportoVerificaType.TimestampVerifiy }
     *     
     */
    public void setTimestampVerifiy(InputRapportoVerificaType.TimestampVerifiy value) {
        this.timestampVerifiy = value;
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
     *         &lt;element name="CRLCheck" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *         &lt;element name="CAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *         &lt;element name="detectCode" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
        "crlCheck",
        "caReliability",
        "detectCode"
    })
    public static class SignatureVerify {

        @XmlElement(name = "CRLCheck", defaultValue = "true")
        protected boolean crlCheck;
        @XmlElement(name = "CAReliability", defaultValue = "true")
        protected boolean caReliability;
        protected boolean detectCode;

        /**
         * Recupera il valore della proprietÓ crlCheck.
         * 
         */
        public boolean isCRLCheck() {
            return crlCheck;
        }

        /**
         * Imposta il valore della proprietÓ crlCheck.
         * 
         */
        public void setCRLCheck(boolean value) {
            this.crlCheck = value;
        }

        /**
         * Recupera il valore della proprietÓ caReliability.
         * 
         */
        public boolean isCAReliability() {
            return caReliability;
        }

        /**
         * Imposta il valore della proprietÓ caReliability.
         * 
         */
        public void setCAReliability(boolean value) {
            this.caReliability = value;
        }

        /**
         * Recupera il valore della proprietÓ detectCode.
         * 
         */
        public boolean isDetectCode() {
            return detectCode;
        }

        /**
         * Imposta il valore della proprietÓ detectCode.
         * 
         */
        public void setDetectCode(boolean value) {
            this.detectCode = value;
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
     *         &lt;element name="TSAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
        "tsaReliability"
    })
    public static class TimestampVerifiy {

        @XmlElement(name = "TSAReliability")
        protected boolean tsaReliability;

        /**
         * Recupera il valore della proprietÓ tsaReliability.
         * 
         */
        public boolean isTSAReliability() {
            return tsaReliability;
        }

        /**
         * Imposta il valore della proprietÓ tsaReliability.
         * 
         */
        public void setTSAReliability(boolean value) {
            this.tsaReliability = value;
        }

    }

}
