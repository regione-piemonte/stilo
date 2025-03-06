
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeGetUserInfoResp2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeGetUserInfoResp2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signerUser" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSigningUser2"/>
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
@XmlType(name = "typeGetUserInfoResp2", propOrder = {
    "signerUser",
    "message"
})
public class TypeGetUserInfoResp2 {

    @XmlElement(required = true)
    protected TypeSigningUser2 signerUser;
    @XmlElement(required = true)
    protected TypeMessage message;

    /**
     * Gets the value of the signerUser property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSigningUser2 }
     *     
     */
    public TypeSigningUser2 getSignerUser() {
        return signerUser;
    }

    /**
     * Sets the value of the signerUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSigningUser2 }
     *     
     */
    public void setSignerUser(TypeSigningUser2 value) {
        this.signerUser = value;
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
