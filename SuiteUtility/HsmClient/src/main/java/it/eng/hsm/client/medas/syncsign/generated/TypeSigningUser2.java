
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSigningUser2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSigningUser2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeUser"/>
 *         &lt;element name="signaturePowerList" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSignaturePowerList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSigningUser2", propOrder = {
    "user",
    "signaturePowerList"
})
public class TypeSigningUser2 {

    @XmlElement(required = true)
    protected TypeUser user;
    protected TypeSignaturePowerList signaturePowerList;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link TypeUser }
     *     
     */
    public TypeUser getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeUser }
     *     
     */
    public void setUser(TypeUser value) {
        this.user = value;
    }

    /**
     * Gets the value of the signaturePowerList property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSignaturePowerList }
     *     
     */
    public TypeSignaturePowerList getSignaturePowerList() {
        return signaturePowerList;
    }

    /**
     * Sets the value of the signaturePowerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSignaturePowerList }
     *     
     */
    public void setSignaturePowerList(TypeSignaturePowerList value) {
        this.signaturePowerList = value;
    }

}
