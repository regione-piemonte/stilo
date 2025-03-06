
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSignOneDocResp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSignOneDocResp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signedDocBin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signedDocHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeMessage"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSignOneDocResp", propOrder = {
    "signedDocBin",
    "signedDocHash",
    "message"
})
public class TypeSignOneDocResp {

    protected String signedDocBin;
    protected String signedDocHash;
    @XmlElement(required = true)
    protected TypeMessage message;

    /**
     * Gets the value of the signedDocBin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignedDocBin() {
        return signedDocBin;
    }

    /**
     * Sets the value of the signedDocBin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignedDocBin(String value) {
        this.signedDocBin = value;
    }

    /**
     * Gets the value of the signedDocHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignedDocHash() {
        return signedDocHash;
    }

    /**
     * Sets the value of the signedDocHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignedDocHash(String value) {
        this.signedDocHash = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link TypeMessage }
     *     
     */
    public TypeMessage getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeMessage }
     *     
     */
    public void setMessage(TypeMessage value) {
        this.message = value;
    }

}
