
package it.eng.hsm.client.medas.utils.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeGetUserInfo3Resp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeGetUserInfo3Resp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signerUser" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSigningUser3"/>
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
@XmlType(name = "typeGetUserInfo3Resp", propOrder = {
    "signerUser",
    "message"
})
public class TypeGetUserInfo3Resp {

    @XmlElement(required = true)
    protected TypeSigningUser3 signerUser;
    @XmlElement(required = true)
    protected TypeMessage message;

    /**
     * Gets the value of the signerUser property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSigningUser3 }
     *     
     */
    public TypeSigningUser3 getSignerUser() {
        return signerUser;
    }

    /**
     * Sets the value of the signerUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSigningUser3 }
     *     
     */
    public void setSignerUser(TypeSigningUser3 value) {
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
