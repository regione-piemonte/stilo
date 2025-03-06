
package it.eng.hsm.client.pkbox.common.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Signer complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Signer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cRLNextUpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="cRLURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cRLUpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="certLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certificateID" type="{http://server.pkbox.it/xsd}CertificateID" minOccurs="0"/>
 *         &lt;element name="certificateSN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="certificateStatus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certificateStatusCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certificateStatusInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="certificateValue" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fiscalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invalidSignCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="issuerDN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keyType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="keyUsage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="md5Fingerprint" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="policies" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="qcCompliance" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qcLimitValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="qcLimitValueCurency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qcRetentionPeriod" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="qcSSCD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="revision" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="shaFingerprint" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="signatureFormat" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signedDigest" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="signerCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signers" type="{http://server.pkbox.it/xsd}Signer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="signingTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="subjectDN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timeStamp" type="{http://server.pkbox.it/xsd}TimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signer", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "crlNextUpdateTime",
    "crlurl",
    "crlUpdateTime",
    "certLevel",
    "certificateID",
    "certificateSN",
    "certificateStatus",
    "certificateStatusCode",
    "certificateStatusInfo",
    "certificateValue",
    "contact",
    "dateOfBirth",
    "endDate",
    "fiscalCode",
    "invalidSignCount",
    "issuerDN",
    "keyType",
    "keyUsage",
    "location",
    "md5Fingerprint",
    "policies",
    "qcCompliance",
    "qcLimitValue",
    "qcLimitValueCurency",
    "qcRetentionPeriod",
    "qcSSCD",
    "reason",
    "revision",
    "shaFingerprint",
    "signatureFormat",
    "signedDigest",
    "signerCount",
    "signers",
    "signingTime",
    "startDate",
    "subjectDN",
    "timeStamp"
})
public class Signer {

    @XmlElement(name = "cRLNextUpdateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar crlNextUpdateTime;
    @XmlElement(name = "cRLURL", required = true, nillable = true)
    protected String crlurl;
    @XmlElement(name = "cRLUpdateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar crlUpdateTime;
    protected int certLevel;
    @XmlElementRef(name = "certificateID", namespace = "http://server.pkbox.it/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<CertificateID> certificateID;
    @XmlElement(required = true, nillable = true)
    protected String certificateSN;
    protected int certificateStatus;
    protected int certificateStatusCode;
    @XmlElement(required = true, nillable = true)
    protected String certificateStatusInfo;
    @XmlElement(required = true, nillable = true)
    protected byte[] certificateValue;
    @XmlElement(required = true, nillable = true)
    protected String contact;
    @XmlElement(required = true, nillable = true)
    protected String dateOfBirth;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(required = true, nillable = true)
    protected String fiscalCode;
    protected int invalidSignCount;
    @XmlElement(required = true, nillable = true)
    protected String issuerDN;
    protected int keyType;
    protected int keyUsage;
    @XmlElement(required = true, nillable = true)
    protected String location;
    @XmlElement(required = true, nillable = true)
    protected byte[] md5Fingerprint;
    @XmlElement(required = true, nillable = true)
    protected List<String> policies;
    @XmlElement(required = true, nillable = true)
    protected String qcCompliance;
    protected int qcLimitValue;
    @XmlElement(required = true, nillable = true)
    protected String qcLimitValueCurency;
    protected int qcRetentionPeriod;
    @XmlElement(required = true, nillable = true)
    protected String qcSSCD;
    @XmlElement(required = true, nillable = true)
    protected String reason;
    protected int revision;
    @XmlElement(required = true, nillable = true)
    protected byte[] shaFingerprint;
    protected int signatureFormat;
    @XmlElement(required = true, nillable = true)
    protected byte[] signedDigest;
    protected int signerCount;
    @XmlElement(nillable = true)
    protected List<Signer> signers;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar signingTime;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(required = true, nillable = true)
    protected String subjectDN;
    @XmlElementRef(name = "timeStamp", namespace = "http://server.pkbox.it/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<TimeStamp> timeStamp;

    /**
     * Recupera il valore della proprietà crlNextUpdateTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCRLNextUpdateTime() {
        return crlNextUpdateTime;
    }

    /**
     * Imposta il valore della proprietà crlNextUpdateTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCRLNextUpdateTime(XMLGregorianCalendar value) {
        this.crlNextUpdateTime = value;
    }

    /**
     * Recupera il valore della proprietà crlurl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRLURL() {
        return crlurl;
    }

    /**
     * Imposta il valore della proprietà crlurl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRLURL(String value) {
        this.crlurl = value;
    }

    /**
     * Recupera il valore della proprietà crlUpdateTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCRLUpdateTime() {
        return crlUpdateTime;
    }

    /**
     * Imposta il valore della proprietà crlUpdateTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCRLUpdateTime(XMLGregorianCalendar value) {
        this.crlUpdateTime = value;
    }

    /**
     * Recupera il valore della proprietà certLevel.
     * 
     */
    public int getCertLevel() {
        return certLevel;
    }

    /**
     * Imposta il valore della proprietà certLevel.
     * 
     */
    public void setCertLevel(int value) {
        this.certLevel = value;
    }

    /**
     * Recupera il valore della proprietà certificateID.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CertificateID }{@code >}
     *     
     */
    public JAXBElement<CertificateID> getCertificateID() {
        return certificateID;
    }

    /**
     * Imposta il valore della proprietà certificateID.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CertificateID }{@code >}
     *     
     */
    public void setCertificateID(JAXBElement<CertificateID> value) {
        this.certificateID = value;
    }

    /**
     * Recupera il valore della proprietà certificateSN.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateSN() {
        return certificateSN;
    }

    /**
     * Imposta il valore della proprietà certificateSN.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateSN(String value) {
        this.certificateSN = value;
    }

    /**
     * Recupera il valore della proprietà certificateStatus.
     * 
     */
    public int getCertificateStatus() {
        return certificateStatus;
    }

    /**
     * Imposta il valore della proprietà certificateStatus.
     * 
     */
    public void setCertificateStatus(int value) {
        this.certificateStatus = value;
    }

    /**
     * Recupera il valore della proprietà certificateStatusCode.
     * 
     */
    public int getCertificateStatusCode() {
        return certificateStatusCode;
    }

    /**
     * Imposta il valore della proprietà certificateStatusCode.
     * 
     */
    public void setCertificateStatusCode(int value) {
        this.certificateStatusCode = value;
    }

    /**
     * Recupera il valore della proprietà certificateStatusInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateStatusInfo() {
        return certificateStatusInfo;
    }

    /**
     * Imposta il valore della proprietà certificateStatusInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateStatusInfo(String value) {
        this.certificateStatusInfo = value;
    }

    /**
     * Recupera il valore della proprietà certificateValue.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCertificateValue() {
        return certificateValue;
    }

    /**
     * Imposta il valore della proprietà certificateValue.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCertificateValue(byte[] value) {
        this.certificateValue = value;
    }

    /**
     * Recupera il valore della proprietà contact.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Imposta il valore della proprietà contact.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * Recupera il valore della proprietà dateOfBirth.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Imposta il valore della proprietà dateOfBirth.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfBirth(String value) {
        this.dateOfBirth = value;
    }

    /**
     * Recupera il valore della proprietà endDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Imposta il valore della proprietà endDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Recupera il valore della proprietà fiscalCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * Imposta il valore della proprietà fiscalCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiscalCode(String value) {
        this.fiscalCode = value;
    }

    /**
     * Recupera il valore della proprietà invalidSignCount.
     * 
     */
    public int getInvalidSignCount() {
        return invalidSignCount;
    }

    /**
     * Imposta il valore della proprietà invalidSignCount.
     * 
     */
    public void setInvalidSignCount(int value) {
        this.invalidSignCount = value;
    }

    /**
     * Recupera il valore della proprietà issuerDN.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuerDN() {
        return issuerDN;
    }

    /**
     * Imposta il valore della proprietà issuerDN.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuerDN(String value) {
        this.issuerDN = value;
    }

    /**
     * Recupera il valore della proprietà keyType.
     * 
     */
    public int getKeyType() {
        return keyType;
    }

    /**
     * Imposta il valore della proprietà keyType.
     * 
     */
    public void setKeyType(int value) {
        this.keyType = value;
    }

    /**
     * Recupera il valore della proprietà keyUsage.
     * 
     */
    public int getKeyUsage() {
        return keyUsage;
    }

    /**
     * Imposta il valore della proprietà keyUsage.
     * 
     */
    public void setKeyUsage(int value) {
        this.keyUsage = value;
    }

    /**
     * Recupera il valore della proprietà location.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Imposta il valore della proprietà location.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Recupera il valore della proprietà md5Fingerprint.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getMd5Fingerprint() {
        return md5Fingerprint;
    }

    /**
     * Imposta il valore della proprietà md5Fingerprint.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setMd5Fingerprint(byte[] value) {
        this.md5Fingerprint = value;
    }

    /**
     * Gets the value of the policies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPolicies() {
        if (policies == null) {
            policies = new ArrayList<String>();
        }
        return this.policies;
    }

    /**
     * Recupera il valore della proprietà qcCompliance.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQcCompliance() {
        return qcCompliance;
    }

    /**
     * Imposta il valore della proprietà qcCompliance.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQcCompliance(String value) {
        this.qcCompliance = value;
    }

    /**
     * Recupera il valore della proprietà qcLimitValue.
     * 
     */
    public int getQcLimitValue() {
        return qcLimitValue;
    }

    /**
     * Imposta il valore della proprietà qcLimitValue.
     * 
     */
    public void setQcLimitValue(int value) {
        this.qcLimitValue = value;
    }

    /**
     * Recupera il valore della proprietà qcLimitValueCurency.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQcLimitValueCurency() {
        return qcLimitValueCurency;
    }

    /**
     * Imposta il valore della proprietà qcLimitValueCurency.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQcLimitValueCurency(String value) {
        this.qcLimitValueCurency = value;
    }

    /**
     * Recupera il valore della proprietà qcRetentionPeriod.
     * 
     */
    public int getQcRetentionPeriod() {
        return qcRetentionPeriod;
    }

    /**
     * Imposta il valore della proprietà qcRetentionPeriod.
     * 
     */
    public void setQcRetentionPeriod(int value) {
        this.qcRetentionPeriod = value;
    }

    /**
     * Recupera il valore della proprietà qcSSCD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQcSSCD() {
        return qcSSCD;
    }

    /**
     * Imposta il valore della proprietà qcSSCD.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQcSSCD(String value) {
        this.qcSSCD = value;
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
     * Recupera il valore della proprietà revision.
     * 
     */
    public int getRevision() {
        return revision;
    }

    /**
     * Imposta il valore della proprietà revision.
     * 
     */
    public void setRevision(int value) {
        this.revision = value;
    }

    /**
     * Recupera il valore della proprietà shaFingerprint.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getShaFingerprint() {
        return shaFingerprint;
    }

    /**
     * Imposta il valore della proprietà shaFingerprint.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setShaFingerprint(byte[] value) {
        this.shaFingerprint = value;
    }

    /**
     * Recupera il valore della proprietà signatureFormat.
     * 
     */
    public int getSignatureFormat() {
        return signatureFormat;
    }

    /**
     * Imposta il valore della proprietà signatureFormat.
     * 
     */
    public void setSignatureFormat(int value) {
        this.signatureFormat = value;
    }

    /**
     * Recupera il valore della proprietà signedDigest.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignedDigest() {
        return signedDigest;
    }

    /**
     * Imposta il valore della proprietà signedDigest.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignedDigest(byte[] value) {
        this.signedDigest = value;
    }

    /**
     * Recupera il valore della proprietà signerCount.
     * 
     */
    public int getSignerCount() {
        return signerCount;
    }

    /**
     * Imposta il valore della proprietà signerCount.
     * 
     */
    public void setSignerCount(int value) {
        this.signerCount = value;
    }

    /**
     * Gets the value of the signers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSigners().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Signer }
     * 
     * 
     */
    public List<Signer> getSigners() {
        if (signers == null) {
            signers = new ArrayList<Signer>();
        }
        return this.signers;
    }

    /**
     * Recupera il valore della proprietà signingTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSigningTime() {
        return signingTime;
    }

    /**
     * Imposta il valore della proprietà signingTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSigningTime(XMLGregorianCalendar value) {
        this.signingTime = value;
    }

    /**
     * Recupera il valore della proprietà startDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Imposta il valore della proprietà startDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Recupera il valore della proprietà subjectDN.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectDN() {
        return subjectDN;
    }

    /**
     * Imposta il valore della proprietà subjectDN.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectDN(String value) {
        this.subjectDN = value;
    }

    /**
     * Recupera il valore della proprietà timeStamp.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TimeStamp }{@code >}
     *     
     */
    public JAXBElement<TimeStamp> getTimeStamp() {
        return timeStamp;
    }

    /**
     * Imposta il valore della proprietà timeStamp.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TimeStamp }{@code >}
     *     
     */
    public void setTimeStamp(JAXBElement<TimeStamp> value) {
        this.timeStamp = value;
    }

}
