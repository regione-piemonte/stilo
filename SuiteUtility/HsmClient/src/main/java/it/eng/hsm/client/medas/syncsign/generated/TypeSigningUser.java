
package it.eng.hsm.client.medas.syncsign.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSigningUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSigningUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeUser"/>
 *         &lt;element name="certificates" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCertificate" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSigningUser", propOrder = {
    "user",
    "certificates"
})
public class TypeSigningUser {

    @XmlElement(required = true)
    protected TypeUser user;
    @XmlElement(nillable = true)
    protected List<TypeCertificate> certificates;

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
     * Gets the value of the certificates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the certificates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCertificates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeCertificate }
     * 
     * 
     */
    public List<TypeCertificate> getCertificates() {
        if (certificates == null) {
            certificates = new ArrayList<TypeCertificate>();
        }
        return this.certificates;
    }

}
