
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
 * <p>Classe Java per TimeStamp complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TimeStamp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="encodedTst" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="genTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="genTimeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="hashAlgorithm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="policyID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serialNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signer" type="{http://server.pkbox.it/xsd}Signer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeStamp", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "encodedTst",
    "error",
    "errorMsg",
    "genTime",
    "genTimeDate",
    "hashAlgorithm",
    "policyID",
    "serialNum",
    "signer"
})
public class TimeStamp {

    @XmlElement(required = true, nillable = true)
    protected byte[] encodedTst;
    protected int error;
    @XmlElement(required = true, nillable = true)
    protected String errorMsg;
    @XmlElement(required = true, nillable = true)
    protected String genTime;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar genTimeDate;
    @XmlElement(required = true, nillable = true)
    protected String hashAlgorithm;
    @XmlElement(required = true, nillable = true)
    protected String policyID;
    @XmlElement(required = true, nillable = true)
    protected String serialNum;
    @XmlElementRef(name = "signer", namespace = "http://server.pkbox.it/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<Signer> signer;

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
     * Recupera il valore della proprietà error.
     * 
     */
    public int getError() {
        return error;
    }

    /**
     * Imposta il valore della proprietà error.
     * 
     */
    public void setError(int value) {
        this.error = value;
    }

    /**
     * Recupera il valore della proprietà errorMsg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Imposta il valore della proprietà errorMsg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMsg(String value) {
        this.errorMsg = value;
    }

    /**
     * Recupera il valore della proprietà genTime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenTime() {
        return genTime;
    }

    /**
     * Imposta il valore della proprietà genTime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenTime(String value) {
        this.genTime = value;
    }

    /**
     * Recupera il valore della proprietà genTimeDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGenTimeDate() {
        return genTimeDate;
    }

    /**
     * Imposta il valore della proprietà genTimeDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGenTimeDate(XMLGregorianCalendar value) {
        this.genTimeDate = value;
    }

    /**
     * Recupera il valore della proprietà hashAlgorithm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    /**
     * Imposta il valore della proprietà hashAlgorithm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashAlgorithm(String value) {
        this.hashAlgorithm = value;
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
     *     {@link JAXBElement }{@code <}{@link Signer }{@code >}
     *     
     */
    public JAXBElement<Signer> getSigner() {
        return signer;
    }

    /**
     * Imposta il valore della proprietà signer.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Signer }{@code >}
     *     
     */
    public void setSigner(JAXBElement<Signer> value) {
        this.signer = value;
    }

}
