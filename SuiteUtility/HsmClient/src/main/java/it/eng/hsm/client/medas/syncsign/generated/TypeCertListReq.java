
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeCertListReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeCertListReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeUser"/>
 *         &lt;element name="credentials" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCredentials"/>
 *         &lt;element name="certificateId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeCertListReq", propOrder = {
    "user",
    "credentials",
    "certificateId"
})
public class TypeCertListReq {

    @XmlElement(required = true)
    protected TypeUser user;
    @XmlElement(required = true)
    protected TypeCredentials credentials;
    protected String certificateId;

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
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCredentials }
     *     
     */
    public TypeCredentials getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCredentials }
     *     
     */
    public void setCredentials(TypeCredentials value) {
        this.credentials = value;
    }

    /**
     * Gets the value of the certificateId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateId() {
        return certificateId;
    }

    /**
     * Sets the value of the certificateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateId(String value) {
        this.certificateId = value;
    }

}
