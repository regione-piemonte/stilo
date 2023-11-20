/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Operations">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Operation" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;choice minOccurs="0">
 *                             &lt;element name="SignOpOption">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Signer" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="CertOwnerUserid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="DelegateUserid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}SignatureType" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="SendEmail">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="SenderAccount" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="Template">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/choice>
 *                           &lt;attribute name="type" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}OperationType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IndexAttach" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="IdLottoDoc" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="30"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="FormatoLottoDoc" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}ArchiverType" />
 *       &lt;attribute name="NroDoc" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="NroFile" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="TipoDoc" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="100"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "operations",
    "indexAttach"
})
@XmlRootElement(name = "RequestActionsOnDocList")
public class RequestActionsOnDocList {

    @XmlElement(name = "Operations", required = true)
    protected RequestActionsOnDocList.Operations operations;
    @XmlElement(name = "IndexAttach")
    @XmlMimeType("text/plain")
    @XmlSchemaType(name = "base64Binary")
    protected String indexAttach;
    @XmlAttribute(name = "IdLottoDoc", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String idLottoDoc;
    @XmlAttribute(name = "FormatoLottoDoc", required = true)
    protected String formatoLottoDoc;
    @XmlAttribute(name = "NroDoc", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroDoc;
    @XmlAttribute(name = "NroFile", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroFile;
    @XmlAttribute(name = "TipoDoc", required = true)
    protected String tipoDoc;

    /**
     * Recupera il valore della propriet� operations.
     * 
     * @return
     *     possible object is
     *     {@link RequestActionsOnDocList.Operations }
     *     
     */
    public RequestActionsOnDocList.Operations getOperations() {
        return operations;
    }

    /**
     * Imposta il valore della propriet� operations.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestActionsOnDocList.Operations }
     *     
     */
    public void setOperations(RequestActionsOnDocList.Operations value) {
        this.operations = value;
    }

    /**
     * Recupera il valore della propriet� indexAttach.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexAttach() {
        return indexAttach;
    }

    /**
     * Imposta il valore della propriet� indexAttach.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexAttach(String value) {
        this.indexAttach = value;
    }

    /**
     * Recupera il valore della propriet� idLottoDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdLottoDoc() {
        return idLottoDoc;
    }

    /**
     * Imposta il valore della propriet� idLottoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdLottoDoc(String value) {
        this.idLottoDoc = value;
    }

    /**
     * Recupera il valore della propriet� formatoLottoDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatoLottoDoc() {
        return formatoLottoDoc;
    }

    /**
     * Imposta il valore della propriet� formatoLottoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatoLottoDoc(String value) {
        this.formatoLottoDoc = value;
    }

    /**
     * Recupera il valore della propriet� nroDoc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroDoc() {
        return nroDoc;
    }

    /**
     * Imposta il valore della propriet� nroDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroDoc(BigInteger value) {
        this.nroDoc = value;
    }

    /**
     * Recupera il valore della propriet� nroFile.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroFile() {
        return nroFile;
    }

    /**
     * Imposta il valore della propriet� nroFile.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroFile(BigInteger value) {
        this.nroFile = value;
    }

    /**
     * Recupera il valore della propriet� tipoDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDoc() {
        return tipoDoc;
    }

    /**
     * Imposta il valore della propriet� tipoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDoc(String value) {
        this.tipoDoc = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Operation" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;choice minOccurs="0">
     *                   &lt;element name="SignOpOption">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Signer" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="CertOwnerUserid" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="DelegateUserid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}SignatureType" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="SendEmail">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="SenderAccount" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *                             &lt;element name="Template">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/choice>
     *                 &lt;attribute name="type" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}OperationType" />
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
    @XmlType(name = "", propOrder = {
        "operation"
    })
    public static class Operations {

        @XmlElement(name = "Operation", required = true)
        protected List<RequestActionsOnDocList.Operations.Operation> operation;

        /**
         * Gets the value of the operation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the operation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOperation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RequestActionsOnDocList.Operations.Operation }
         * 
         * 
         */
        public List<RequestActionsOnDocList.Operations.Operation> getOperation() {
            if (operation == null) {
                operation = new ArrayList<RequestActionsOnDocList.Operations.Operation>();
            }
            return this.operation;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;choice minOccurs="0">
         *         &lt;element name="SignOpOption">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Signer" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="CertOwnerUserid" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="DelegateUserid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}SignatureType" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="SendEmail">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="SenderAccount" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
         *                   &lt;element name="Template">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/choice>
         *       &lt;attribute name="type" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}OperationType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "signOpOption",
            "sendEmail"
        })
        public static class Operation {

            @XmlElement(name = "SignOpOption")
            protected RequestActionsOnDocList.Operations.Operation.SignOpOption signOpOption;
            @XmlElement(name = "SendEmail")
            protected RequestActionsOnDocList.Operations.Operation.SendEmail sendEmail;
            @XmlAttribute(name = "type", required = true)
            protected OperationType type;

            /**
             * Recupera il valore della propriet� signOpOption.
             * 
             * @return
             *     possible object is
             *     {@link RequestActionsOnDocList.Operations.Operation.SignOpOption }
             *     
             */
            public RequestActionsOnDocList.Operations.Operation.SignOpOption getSignOpOption() {
                return signOpOption;
            }

            /**
             * Imposta il valore della propriet� signOpOption.
             * 
             * @param value
             *     allowed object is
             *     {@link RequestActionsOnDocList.Operations.Operation.SignOpOption }
             *     
             */
            public void setSignOpOption(RequestActionsOnDocList.Operations.Operation.SignOpOption value) {
                this.signOpOption = value;
            }

            /**
             * Recupera il valore della propriet� sendEmail.
             * 
             * @return
             *     possible object is
             *     {@link RequestActionsOnDocList.Operations.Operation.SendEmail }
             *     
             */
            public RequestActionsOnDocList.Operations.Operation.SendEmail getSendEmail() {
                return sendEmail;
            }

            /**
             * Imposta il valore della propriet� sendEmail.
             * 
             * @param value
             *     allowed object is
             *     {@link RequestActionsOnDocList.Operations.Operation.SendEmail }
             *     
             */
            public void setSendEmail(RequestActionsOnDocList.Operations.Operation.SendEmail value) {
                this.sendEmail = value;
            }

            /**
             * Recupera il valore della propriet� type.
             * 
             * @return
             *     possible object is
             *     {@link OperationType }
             *     
             */
            public OperationType getType() {
                return type;
            }

            /**
             * Imposta il valore della propriet� type.
             * 
             * @param value
             *     allowed object is
             *     {@link OperationType }
             *     
             */
            public void setType(OperationType value) {
                this.type = value;
            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="SenderAccount" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
             *         &lt;element name="Template">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;/restriction>
             *           &lt;/simpleType>
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
            @XmlType(name = "", propOrder = {
                "senderAccount",
                "template"
            })
            public static class SendEmail {

                @XmlElement(name = "SenderAccount")
                protected List<String> senderAccount;
                @XmlElement(name = "Template", required = true)
                protected String template;

                /**
                 * Gets the value of the senderAccount property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the senderAccount property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSenderAccount().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getSenderAccount() {
                    if (senderAccount == null) {
                        senderAccount = new ArrayList<String>();
                    }
                    return this.senderAccount;
                }

                /**
                 * Recupera il valore della propriet� template.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTemplate() {
                    return template;
                }

                /**
                 * Imposta il valore della propriet� template.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTemplate(String value) {
                    this.template = value;
                }

            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="Signer" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CertOwnerUserid" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="DelegateUserid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="type" use="required" type="{http://operazioniaurigalottidoc.webservices.repository2.auriga.eng.it}SignatureType" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "signer"
            })
            public static class SignOpOption {

                @XmlElement(name = "Signer", required = true)
                protected List<RequestActionsOnDocList.Operations.Operation.SignOpOption.Signer> signer;
                @XmlAttribute(name = "type", required = true)
                protected SignatureType type;

                /**
                 * Gets the value of the signer property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the signer property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSigner().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RequestActionsOnDocList.Operations.Operation.SignOpOption.Signer }
                 * 
                 * 
                 */
                public List<RequestActionsOnDocList.Operations.Operation.SignOpOption.Signer> getSigner() {
                    if (signer == null) {
                        signer = new ArrayList<RequestActionsOnDocList.Operations.Operation.SignOpOption.Signer>();
                    }
                    return this.signer;
                }

                /**
                 * Recupera il valore della propriet� type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link SignatureType }
                 *     
                 */
                public SignatureType getType() {
                    return type;
                }

                /**
                 * Imposta il valore della propriet� type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link SignatureType }
                 *     
                 */
                public void setType(SignatureType value) {
                    this.type = value;
                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CertOwnerUserid" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="DelegateUserid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                    "certOwnerUserid",
                    "delegateUserid"
                })
                public static class Signer {

                    @XmlElement(name = "CertOwnerUserid", required = true)
                    protected String certOwnerUserid;
                    @XmlElement(name = "DelegateUserid")
                    protected String delegateUserid;

                    /**
                     * Recupera il valore della propriet� certOwnerUserid.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCertOwnerUserid() {
                        return certOwnerUserid;
                    }

                    /**
                     * Imposta il valore della propriet� certOwnerUserid.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCertOwnerUserid(String value) {
                        this.certOwnerUserid = value;
                    }

                    /**
                     * Recupera il valore della propriet� delegateUserid.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDelegateUserid() {
                        return delegateUserid;
                    }

                    /**
                     * Imposta il valore della propriet� delegateUserid.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDelegateUserid(String value) {
                        this.delegateUserid = value;
                    }

                }

            }

        }

    }

}