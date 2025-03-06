
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docBin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signStringCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeDocument", propOrder = {
    "docBin",
    "docHash",
    "docType",
    "signStringCode"
})
public class TypeDocument {

    protected String docBin;
    protected String docHash;
    @XmlElement(required = true)
    protected String docType;
    @XmlElement(required = true)
    protected String signStringCode;

    /**
     * Gets the value of the docBin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocBin() {
        return docBin;
    }

    /**
     * Sets the value of the docBin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocBin(String value) {
        this.docBin = value;
    }

    /**
     * Gets the value of the docHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocHash() {
        return docHash;
    }

    /**
     * Sets the value of the docHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocHash(String value) {
        this.docHash = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

    /**
     * Gets the value of the signStringCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignStringCode() {
        return signStringCode;
    }

    /**
     * Sets the value of the signStringCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignStringCode(String value) {
        this.signStringCode = value;
    }

}
