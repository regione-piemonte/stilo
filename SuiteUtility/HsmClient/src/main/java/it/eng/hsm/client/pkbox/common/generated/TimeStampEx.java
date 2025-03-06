
package it.eng.hsm.client.pkbox.common.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per TimeStampEx complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TimeStampEx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="compliance" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="complianceCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="complianceInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encodedTst" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="genTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="hashAlgorithm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hashValue" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="policyID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serialNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signer" type="{http://server.pkbox.it/xsd}SignerEx" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeStampEx", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "compliance",
    "complianceCode",
    "complianceInfo",
    "encodedTst",
    "genTime",
    "hashAlgorithm",
    "hashValue",
    "policyID",
    "serialNum",
    "signer"
})
public class TimeStampEx {

    protected int compliance;
    protected int complianceCode;
    @XmlElement(required = true, nillable = true)
    protected String complianceInfo;
    @XmlElement(required = true, nillable = true)
    protected byte[] encodedTst;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar genTime;
    protected int hashAlgorithm;
    @XmlElement(required = true, nillable = true)
    protected byte[] hashValue;
    @XmlElement(required = true, nillable = true)
    protected String policyID;
    @XmlElement(required = true, nillable = true)
    protected String serialNum;
    @XmlElementRef(name = "signer", namespace = "http://server.pkbox.it/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<SignerEx> signer;

    /**
     * Recupera il valore della proprietà compliance.
     * 
     */
    public int getCompliance() {
        return compliance;
    }

    /**
     * Imposta il valore della proprietà compliance.
     * 
     */
    public void setCompliance(int value) {
        this.compliance = value;
    }

    /**
     * Recupera il valore della proprietà complianceCode.
     * 
     */
    public int getComplianceCode() {
        return complianceCode;
    }

    /**
     * Imposta il valore della proprietà complianceCode.
     * 
     */
    public void setComplianceCode(int value) {
        this.complianceCode = value;
    }

    /**
     * Recupera il valore della proprietà complianceInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplianceInfo() {
        return complianceInfo;
    }

    /**
     * Imposta il valore della proprietà complianceInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplianceInfo(String value) {
        this.complianceInfo = value;
    }

    /**
     * Recupera il valore della proprietà encodedTst.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEncodedTst() {
        return encodedTst;
    }

    /**
     * Imposta il valore della proprietà encodedTst.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEncodedTst(byte[] value) {
        this.encodedTst = value;
    }

    /**
     * Recupera il valore della proprietà genTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGenTime() {
        return genTime;
    }

    /**
     * Imposta il valore della proprietà genTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGenTime(XMLGregorianCalendar value) {
        this.genTime = value;
    }

    /**
     * Recupera il valore della proprietà hashAlgorithm.
     * 
     */
    public int getHashAlgorithm() {
        return hashAlgorithm;
    }

    /**
     * Imposta il valore della proprietà hashAlgorithm.
     * 
     */
    public void setHashAlgorithm(int value) {
        this.hashAlgorithm = value;
    }

    /**
     * Recupera il valore della proprietà hashValue.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHashValue() {
        return hashValue;
    }

    /**
     * Imposta il valore della proprietà hashValue.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHashValue(byte[] value) {
        this.hashValue = value;
    }

    /**
     * Recupera il valore della proprietà policyID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyID() {
        return policyID;
    }

    /**
     * Imposta il valore della proprietà policyID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyID(String value) {
        this.policyID = value;
    }

    /**
     * Recupera il valore della proprietà serialNum.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * Imposta il valore della proprietà serialNum.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNum(String value) {
        this.serialNum = value;
    }

    /**
     * Recupera il valore della proprietà signer.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SignerEx }{@code >}
     *     
     */
    public JAXBElement<SignerEx> getSigner() {
        return signer;
    }

    /**
     * Imposta il valore della proprietà signer.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SignerEx }{@code >}
     *     
     */
    public void setSigner(JAXBElement<SignerEx> value) {
        this.signer = value;
    }

}
