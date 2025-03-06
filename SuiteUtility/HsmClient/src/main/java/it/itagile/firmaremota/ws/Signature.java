
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Signature complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Signature"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="signType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="digestAlg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="signTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="givenName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="surName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fiscalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="organization" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orgUnit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="certID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="certType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="certSerial" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="certKeyUsage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="trustSp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="certDateFrom" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="certDateTo" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="tsAuthority" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tsLenght" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="signatureField" type="{http://ws.firmaremota.itagile.it}SignatureField"/&gt;
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="validSign" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="validCert" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="validTrust" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="validTimestamp" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="signErrCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="certErrCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="trustErrCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="p7mLevel" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="p7mPath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="x509" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signature", propOrder = {
    "signType",
    "digestAlg",
    "signTime",
    "givenName",
    "surName",
    "fiscalCode",
    "organization",
    "orgUnit",
    "certID",
    "certType",
    "certSerial",
    "certKeyUsage",
    "trustSp",
    "certDateFrom",
    "certDateTo",
    "timestamp",
    "tsAuthority",
    "tsLenght",
    "signatureField",
    "valid",
    "validSign",
    "validCert",
    "validTrust",
    "validTimestamp",
    "signErrCode",
    "certErrCode",
    "trustErrCode",
    "p7MLevel",
    "p7MPath",
    "x509"
})
public class Signature {

    @XmlElement(required = true, nillable = true)
    protected String signType;
    @XmlElement(required = true, nillable = true)
    protected String digestAlg;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long signTime;
    @XmlElement(required = true, nillable = true)
    protected String givenName;
    @XmlElement(required = true, nillable = true)
    protected String surName;
    @XmlElement(required = true, nillable = true)
    protected String fiscalCode;
    @XmlElement(required = true, nillable = true)
    protected String organization;
    @XmlElement(required = true, nillable = true)
    protected String orgUnit;
    @XmlElement(required = true, nillable = true)
    protected String certID;
    @XmlElement(required = true, nillable = true)
    protected String certType;
    @XmlElement(required = true, nillable = true)
    protected String certSerial;
    @XmlElement(required = true, nillable = true)
    protected String certKeyUsage;
    @XmlElement(required = true, nillable = true)
    protected String trustSp;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long certDateFrom;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long certDateTo;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean timestamp;
    @XmlElement(required = true, nillable = true)
    protected String tsAuthority;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long tsLenght;
    @XmlElement(required = true, nillable = true)
    protected SignatureField signatureField;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean valid;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean validSign;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean validCert;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean validTrust;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean validTimestamp;
    @XmlElement(required = true, nillable = true)
    protected String signErrCode;
    @XmlElement(required = true, nillable = true)
    protected String certErrCode;
    @XmlElement(required = true, nillable = true)
    protected String trustErrCode;
    @XmlElement(name = "p7mLevel", required = true, type = Integer.class, nillable = true)
    protected Integer p7MLevel;
    @XmlElement(name = "p7mPath", required = true, nillable = true)
    protected String p7MPath;
    @XmlElement(required = true, nillable = true)
    protected byte[] x509;

    /**
     * Recupera il valore della proprietà signType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignType() {
        return signType;
    }

    /**
     * Imposta il valore della proprietà signType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignType(String value) {
        this.signType = value;
    }

    /**
     * Recupera il valore della proprietà digestAlg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigestAlg() {
        return digestAlg;
    }

    /**
     * Imposta il valore della proprietà digestAlg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigestAlg(String value) {
        this.digestAlg = value;
    }

    /**
     * Recupera il valore della proprietà signTime.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSignTime() {
        return signTime;
    }

    /**
     * Imposta il valore della proprietà signTime.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSignTime(Long value) {
        this.signTime = value;
    }

    /**
     * Recupera il valore della proprietà givenName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Imposta il valore della proprietà givenName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * Recupera il valore della proprietà surName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurName() {
        return surName;
    }

    /**
     * Imposta il valore della proprietà surName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurName(String value) {
        this.surName = value;
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
     * Recupera il valore della proprietà organization.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Imposta il valore della proprietà organization.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganization(String value) {
        this.organization = value;
    }

    /**
     * Recupera il valore della proprietà orgUnit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgUnit() {
        return orgUnit;
    }

    /**
     * Imposta il valore della proprietà orgUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgUnit(String value) {
        this.orgUnit = value;
    }

    /**
     * Recupera il valore della proprietà certID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertID() {
        return certID;
    }

    /**
     * Imposta il valore della proprietà certID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertID(String value) {
        this.certID = value;
    }

    /**
     * Recupera il valore della proprietà certType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertType() {
        return certType;
    }

    /**
     * Imposta il valore della proprietà certType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertType(String value) {
        this.certType = value;
    }

    /**
     * Recupera il valore della proprietà certSerial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSerial() {
        return certSerial;
    }

    /**
     * Imposta il valore della proprietà certSerial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSerial(String value) {
        this.certSerial = value;
    }

    /**
     * Recupera il valore della proprietà certKeyUsage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertKeyUsage() {
        return certKeyUsage;
    }

    /**
     * Imposta il valore della proprietà certKeyUsage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertKeyUsage(String value) {
        this.certKeyUsage = value;
    }

    /**
     * Recupera il valore della proprietà trustSp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrustSp() {
        return trustSp;
    }

    /**
     * Imposta il valore della proprietà trustSp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrustSp(String value) {
        this.trustSp = value;
    }

    /**
     * Recupera il valore della proprietà certDateFrom.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCertDateFrom() {
        return certDateFrom;
    }

    /**
     * Imposta il valore della proprietà certDateFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCertDateFrom(Long value) {
        this.certDateFrom = value;
    }

    /**
     * Recupera il valore della proprietà certDateTo.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCertDateTo() {
        return certDateTo;
    }

    /**
     * Imposta il valore della proprietà certDateTo.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCertDateTo(Long value) {
        this.certDateTo = value;
    }

    /**
     * Recupera il valore della proprietà timestamp.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTimestamp() {
        return timestamp;
    }

    /**
     * Imposta il valore della proprietà timestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTimestamp(Boolean value) {
        this.timestamp = value;
    }

    /**
     * Recupera il valore della proprietà tsAuthority.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsAuthority() {
        return tsAuthority;
    }

    /**
     * Imposta il valore della proprietà tsAuthority.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsAuthority(String value) {
        this.tsAuthority = value;
    }

    /**
     * Recupera il valore della proprietà tsLenght.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTsLenght() {
        return tsLenght;
    }

    /**
     * Imposta il valore della proprietà tsLenght.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTsLenght(Long value) {
        this.tsLenght = value;
    }

    /**
     * Recupera il valore della proprietà signatureField.
     * 
     * @return
     *     possible object is
     *     {@link SignatureField }
     *     
     */
    public SignatureField getSignatureField() {
        return signatureField;
    }

    /**
     * Imposta il valore della proprietà signatureField.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureField }
     *     
     */
    public void setSignatureField(SignatureField value) {
        this.signatureField = value;
    }

    /**
     * Recupera il valore della proprietà valid.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValid() {
        return valid;
    }

    /**
     * Imposta il valore della proprietà valid.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValid(Boolean value) {
        this.valid = value;
    }

    /**
     * Recupera il valore della proprietà validSign.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidSign() {
        return validSign;
    }

    /**
     * Imposta il valore della proprietà validSign.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidSign(Boolean value) {
        this.validSign = value;
    }

    /**
     * Recupera il valore della proprietà validCert.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidCert() {
        return validCert;
    }

    /**
     * Imposta il valore della proprietà validCert.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidCert(Boolean value) {
        this.validCert = value;
    }

    /**
     * Recupera il valore della proprietà validTrust.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidTrust() {
        return validTrust;
    }

    /**
     * Imposta il valore della proprietà validTrust.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidTrust(Boolean value) {
        this.validTrust = value;
    }

    /**
     * Recupera il valore della proprietà validTimestamp.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidTimestamp() {
        return validTimestamp;
    }

    /**
     * Imposta il valore della proprietà validTimestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidTimestamp(Boolean value) {
        this.validTimestamp = value;
    }

    /**
     * Recupera il valore della proprietà signErrCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignErrCode() {
        return signErrCode;
    }

    /**
     * Imposta il valore della proprietà signErrCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignErrCode(String value) {
        this.signErrCode = value;
    }

    /**
     * Recupera il valore della proprietà certErrCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertErrCode() {
        return certErrCode;
    }

    /**
     * Imposta il valore della proprietà certErrCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertErrCode(String value) {
        this.certErrCode = value;
    }

    /**
     * Recupera il valore della proprietà trustErrCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrustErrCode() {
        return trustErrCode;
    }

    /**
     * Imposta il valore della proprietà trustErrCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrustErrCode(String value) {
        this.trustErrCode = value;
    }

    /**
     * Recupera il valore della proprietà p7MLevel.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getP7MLevel() {
        return p7MLevel;
    }

    /**
     * Imposta il valore della proprietà p7MLevel.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setP7MLevel(Integer value) {
        this.p7MLevel = value;
    }

    /**
     * Recupera il valore della proprietà p7MPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getP7MPath() {
        return p7MPath;
    }

    /**
     * Imposta il valore della proprietà p7MPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setP7MPath(String value) {
        this.p7MPath = value;
    }

    /**
     * Recupera il valore della proprietà x509.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509() {
        return x509;
    }

    /**
     * Imposta il valore della proprietà x509.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509(byte[] value) {
        this.x509 = value;
    }

}
