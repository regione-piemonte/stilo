
package it.eng.hsm.client.pkbox.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.hsm.client.pkbox.common.generated.CertificateID;


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
 *         &lt;element name="environment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enc" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="customerinfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="decryptKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="thumbprint" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="certID" type="{http://server.pkbox.it/xsd}CertificateID"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keyPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="algorithm" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "environment",
    "enc",
    "customerinfo",
    "decryptKey",
    "certificate",
    "thumbprint",
    "certID",
    "pin",
    "keyPin",
    "algorithm"
})
@XmlRootElement(name = "rawdecipher")
public class Rawdecipher {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] enc;
    @XmlElement(required = true, nillable = true)
    protected String customerinfo;
    @XmlElement(required = true, nillable = true)
    protected String decryptKey;
    @XmlElement(required = true, nillable = true)
    protected byte[] certificate;
    @XmlElement(required = true, nillable = true)
    protected byte[] thumbprint;
    @XmlElement(required = true, nillable = true)
    protected CertificateID certID;
    @XmlElement(required = true, nillable = true)
    protected String pin;
    @XmlElement(required = true, nillable = true)
    protected String keyPin;
    protected int algorithm;

    /**
     * Recupera il valore della proprietà environment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Imposta il valore della proprietà environment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvironment(String value) {
        this.environment = value;
    }

    /**
     * Recupera il valore della proprietà enc.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEnc() {
        return enc;
    }

    /**
     * Imposta il valore della proprietà enc.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEnc(byte[] value) {
        this.enc = value;
    }

    /**
     * Recupera il valore della proprietà customerinfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerinfo() {
        return customerinfo;
    }

    /**
     * Imposta il valore della proprietà customerinfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerinfo(String value) {
        this.customerinfo = value;
    }

    /**
     * Recupera il valore della proprietà decryptKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecryptKey() {
        return decryptKey;
    }

    /**
     * Imposta il valore della proprietà decryptKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecryptKey(String value) {
        this.decryptKey = value;
    }

    /**
     * Recupera il valore della proprietà certificate.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCertificate() {
        return certificate;
    }

    /**
     * Imposta il valore della proprietà certificate.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCertificate(byte[] value) {
        this.certificate = value;
    }

    /**
     * Recupera il valore della proprietà thumbprint.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getThumbprint() {
        return thumbprint;
    }

    /**
     * Imposta il valore della proprietà thumbprint.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setThumbprint(byte[] value) {
        this.thumbprint = value;
    }

    /**
     * Recupera il valore della proprietà certID.
     * 
     * @return
     *     possible object is
     *     {@link CertificateID }
     *     
     */
    public CertificateID getCertID() {
        return certID;
    }

    /**
     * Imposta il valore della proprietà certID.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateID }
     *     
     */
    public void setCertID(CertificateID value) {
        this.certID = value;
    }

    /**
     * Recupera il valore della proprietà pin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPin() {
        return pin;
    }

    /**
     * Imposta il valore della proprietà pin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPin(String value) {
        this.pin = value;
    }

    /**
     * Recupera il valore della proprietà keyPin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyPin() {
        return keyPin;
    }

    /**
     * Imposta il valore della proprietà keyPin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyPin(String value) {
        this.keyPin = value;
    }

    /**
     * Recupera il valore della proprietà algorithm.
     * 
     */
    public int getAlgorithm() {
        return algorithm;
    }

    /**
     * Imposta il valore della proprietà algorithm.
     * 
     */
    public void setAlgorithm(int value) {
        this.algorithm = value;
    }

}
