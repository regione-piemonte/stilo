/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 10:39:55 AM CET 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="x509cert" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="dateRif" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element name="VerificationInfo" type="{verify.cryptoutil.eng.it}VerificationInfo"/>
 *         &lt;/sequence>
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
    "x509Cert",
    "dateRif",
    "verificationInfo"
})
@XmlRootElement(name = "VerificationRequest", namespace = "verify.cryptoutil.eng.it")
public class VerificationRequest
    implements Serializable
{

    @XmlElement(name = "x509cert", namespace = "verify.cryptoutil.eng.it", required = true)
    protected byte[] x509Cert;
    @XmlElement(namespace = "verify.cryptoutil.eng.it", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateRif;
    @XmlElement(name = "VerificationInfo", namespace = "verify.cryptoutil.eng.it", required = true)
    protected List<VerificationInfo> verificationInfo;

    /**
     * Gets the value of the x509Cert property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509Cert() {
        return x509Cert;
    }

    /**
     * Sets the value of the x509Cert property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509Cert(byte[] value) {
        this.x509Cert = ((byte[]) value);
    }

    /**
     * Gets the value of the dateRif property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateRif() {
        return dateRif;
    }

    /**
     * Sets the value of the dateRif property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateRif(XMLGregorianCalendar value) {
        this.dateRif = value;
    }

    /**
     * Gets the value of the verificationInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationInfo }
     * 
     * 
     */
    public List<VerificationInfo> getVerificationInfo() {
        if (verificationInfo == null) {
            verificationInfo = new ArrayList<VerificationInfo>();
        }
        return this.verificationInfo;
    }

}
