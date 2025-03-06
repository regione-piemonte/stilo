
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SignatureStatus complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SignatureStatus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="certStatus" type="{http://ws.firmaremota.itagile.it}CertificateStatus"/&gt;
 *         &lt;element name="counterSignatures" type="{http://ws.firmaremota.itagile.it}ArrayOfSignatureStatus"/&gt;
 *         &lt;element name="digestAlgorithm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="encryptionAlgorithm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="invalidSignatureMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="invalidTimestampMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="locality" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="signatureTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="timestampCertificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="validSignature" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="validTimestamp" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="invalidSignatureCode" type="{http://ws.firmaremota.itagile.it}ArrayOfString"/&gt;
 *         &lt;element name="invalidTimestampCode" type="{http://ws.firmaremota.itagile.it}ArrayOfString"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureStatus", propOrder = {
    "certStatus",
    "counterSignatures",
    "digestAlgorithm",
    "encryptionAlgorithm",
    "invalidSignatureMessage",
    "invalidTimestampMessage",
    "locality",
    "reason",
    "fieldName",
    "signatureTime",
    "timestamp",
    "timestampCertificate",
    "validSignature",
    "validTimestamp",
    "invalidSignatureCode",
    "invalidTimestampCode"
})
public class SignatureStatus {

    @XmlElement(required = true, nillable = true)
    protected CertificateStatus certStatus;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfSignatureStatus counterSignatures;
    @XmlElement(required = true, nillable = true)
    protected String digestAlgorithm;
    @XmlElement(required = true, nillable = true)
    protected String encryptionAlgorithm;
    @XmlElement(required = true, nillable = true)
    protected String invalidSignatureMessage;
    @XmlElement(required = true, nillable = true)
    protected String invalidTimestampMessage;
    @XmlElement(required = true, nillable = true)
    protected String locality;
    @XmlElement(required = true, nillable = true)
    protected String reason;
    @XmlElement(required = true, nillable = true)
    protected String fieldName;
    protected long signatureTime;
    protected boolean timestamp;
    @XmlElement(required = true, nillable = true)
    protected byte[] timestampCertificate;
    protected boolean validSignature;
    protected boolean validTimestamp;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfString invalidSignatureCode;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfString invalidTimestampCode;

    /**
     * Recupera il valore della proprietà certStatus.
     * 
     * @return
     *     possible object is
     *     {@link CertificateStatus }
     *     
     */
    public CertificateStatus getCertStatus() {
        return certStatus;
    }

    /**
     * Imposta il valore della proprietà certStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateStatus }
     *     
     */
    public void setCertStatus(CertificateStatus value) {
        this.certStatus = value;
    }

    /**
     * Recupera il valore della proprietà counterSignatures.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignatureStatus }
     *     
     */
    public ArrayOfSignatureStatus getCounterSignatures() {
        return counterSignatures;
    }

    /**
     * Imposta il valore della proprietà counterSignatures.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignatureStatus }
     *     
     */
    public void setCounterSignatures(ArrayOfSignatureStatus value) {
        this.counterSignatures = value;
    }

    /**
     * Recupera il valore della proprietà digestAlgorithm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    /**
     * Imposta il valore della proprietà digestAlgorithm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigestAlgorithm(String value) {
        this.digestAlgorithm = value;
    }

    /**
     * Recupera il valore della proprietà encryptionAlgorithm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    /**
     * Imposta il valore della proprietà encryptionAlgorithm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptionAlgorithm(String value) {
        this.encryptionAlgorithm = value;
    }

    /**
     * Recupera il valore della proprietà invalidSignatureMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvalidSignatureMessage() {
        return invalidSignatureMessage;
    }

    /**
     * Imposta il valore della proprietà invalidSignatureMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidSignatureMessage(String value) {
        this.invalidSignatureMessage = value;
    }

    /**
     * Recupera il valore della proprietà invalidTimestampMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvalidTimestampMessage() {
        return invalidTimestampMessage;
    }

    /**
     * Imposta il valore della proprietà invalidTimestampMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidTimestampMessage(String value) {
        this.invalidTimestampMessage = value;
    }

    /**
     * Recupera il valore della proprietà locality.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Imposta il valore della proprietà locality.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocality(String value) {
        this.locality = value;
    }

    /**
     * Recupera il valore della proprietà reason.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Imposta il valore della proprietà reason.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Recupera il valore della proprietà fieldName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Imposta il valore della proprietà fieldName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Recupera il valore della proprietà signatureTime.
     * 
     */
    public long getSignatureTime() {
        return signatureTime;
    }

    /**
     * Imposta il valore della proprietà signatureTime.
     * 
     */
    public void setSignatureTime(long value) {
        this.signatureTime = value;
    }

    /**
     * Recupera il valore della proprietà timestamp.
     * 
     */
    public boolean isTimestamp() {
        return timestamp;
    }

    /**
     * Imposta il valore della proprietà timestamp.
     * 
     */
    public void setTimestamp(boolean value) {
        this.timestamp = value;
    }

    /**
     * Recupera il valore della proprietà timestampCertificate.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getTimestampCertificate() {
        return timestampCertificate;
    }

    /**
     * Imposta il valore della proprietà timestampCertificate.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setTimestampCertificate(byte[] value) {
        this.timestampCertificate = value;
    }

    /**
     * Recupera il valore della proprietà validSignature.
     * 
     */
    public boolean isValidSignature() {
        return validSignature;
    }

    /**
     * Imposta il valore della proprietà validSignature.
     * 
     */
    public void setValidSignature(boolean value) {
        this.validSignature = value;
    }

    /**
     * Recupera il valore della proprietà validTimestamp.
     * 
     */
    public boolean isValidTimestamp() {
        return validTimestamp;
    }

    /**
     * Imposta il valore della proprietà validTimestamp.
     * 
     */
    public void setValidTimestamp(boolean value) {
        this.validTimestamp = value;
    }

    /**
     * Recupera il valore della proprietà invalidSignatureCode.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getInvalidSignatureCode() {
        return invalidSignatureCode;
    }

    /**
     * Imposta il valore della proprietà invalidSignatureCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setInvalidSignatureCode(ArrayOfString value) {
        this.invalidSignatureCode = value;
    }

    /**
     * Recupera il valore della proprietà invalidTimestampCode.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getInvalidTimestampCode() {
        return invalidTimestampCode;
    }

    /**
     * Imposta il valore della proprietà invalidTimestampCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setInvalidTimestampCode(ArrayOfString value) {
        this.invalidTimestampCode = value;
    }

}
