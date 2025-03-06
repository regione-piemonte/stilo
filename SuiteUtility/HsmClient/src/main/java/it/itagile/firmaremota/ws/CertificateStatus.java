
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CertificateStatus complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CertificateStatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="checkTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="invalidCertificateMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="invalidCertificateCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="trustedIdentity" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="untrustedIdentityMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="untrustedIdentityCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="validCertificate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CertificateStatus", propOrder = {
    "certificate",
    "checkTime",
    "invalidCertificateMessage",
    "invalidCertificateCode",
    "trustedIdentity",
    "untrustedIdentityMessage",
    "untrustedIdentityCode",
    "validCertificate"
})
public class CertificateStatus {

    @XmlElement(required = true, nillable = true)
    protected byte[] certificate;
    protected long checkTime;
    @XmlElement(required = true, nillable = true)
    protected String invalidCertificateMessage;
    @XmlElement(required = true, nillable = true)
    protected String invalidCertificateCode;
    protected boolean trustedIdentity;
    @XmlElement(required = true, nillable = true)
    protected String untrustedIdentityMessage;
    @XmlElement(required = true, nillable = true)
    protected String untrustedIdentityCode;
    protected boolean validCertificate;

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
     * Recupera il valore della proprietà checkTime.
     * 
     */
    public long getCheckTime() {
        return checkTime;
    }

    /**
     * Imposta il valore della proprietà checkTime.
     * 
     */
    public void setCheckTime(long value) {
        this.checkTime = value;
    }

    /**
     * Recupera il valore della proprietà invalidCertificateMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvalidCertificateMessage() {
        return invalidCertificateMessage;
    }

    /**
     * Imposta il valore della proprietà invalidCertificateMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidCertificateMessage(String value) {
        this.invalidCertificateMessage = value;
    }

    /**
     * Recupera il valore della proprietà invalidCertificateCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvalidCertificateCode() {
        return invalidCertificateCode;
    }

    /**
     * Imposta il valore della proprietà invalidCertificateCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidCertificateCode(String value) {
        this.invalidCertificateCode = value;
    }

    /**
     * Recupera il valore della proprietà trustedIdentity.
     * 
     */
    public boolean isTrustedIdentity() {
        return trustedIdentity;
    }

    /**
     * Imposta il valore della proprietà trustedIdentity.
     * 
     */
    public void setTrustedIdentity(boolean value) {
        this.trustedIdentity = value;
    }

    /**
     * Recupera il valore della proprietà untrustedIdentityMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUntrustedIdentityMessage() {
        return untrustedIdentityMessage;
    }

    /**
     * Imposta il valore della proprietà untrustedIdentityMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUntrustedIdentityMessage(String value) {
        this.untrustedIdentityMessage = value;
    }

    /**
     * Recupera il valore della proprietà untrustedIdentityCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUntrustedIdentityCode() {
        return untrustedIdentityCode;
    }

    /**
     * Imposta il valore della proprietà untrustedIdentityCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUntrustedIdentityCode(String value) {
        this.untrustedIdentityCode = value;
    }

    /**
     * Recupera il valore della proprietà validCertificate.
     * 
     */
    public boolean isValidCertificate() {
        return validCertificate;
    }

    /**
     * Imposta il valore della proprietà validCertificate.
     * 
     */
    public void setValidCertificate(boolean value) {
        this.validCertificate = value;
    }

}
