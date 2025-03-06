
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSignOneDocReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSignOneDocReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeUser"/>
 *         &lt;element name="credentials" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCredentials"/>
 *         &lt;element name="certificateId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signaturePowerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="document" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDocument"/>
 *         &lt;element name="signProperties" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSignProperties"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSignOneDocReq", propOrder = {
    "user",
    "credentials",
    "certificateId",
    "signaturePowerId",
    "document",
    "signProperties"
})
public class TypeSignOneDocReq {

    @XmlElement(required = true)
    protected TypeUser user;
    @XmlElement(required = true)
    protected TypeCredentials credentials;
    protected String certificateId;
    protected String signaturePowerId;
    @XmlElement(required = true)
    protected TypeDocument document;
    @XmlElement(required = true)
    protected TypeSignProperties signProperties;

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

    /**
     * Gets the value of the signaturePowerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignaturePowerId() {
        return signaturePowerId;
    }

    /**
     * Sets the value of the signaturePowerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignaturePowerId(String value) {
        this.signaturePowerId = value;
    }

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link TypeDocument }
     *     
     */
    public TypeDocument getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDocument }
     *     
     */
    public void setDocument(TypeDocument value) {
        this.document = value;
    }

    /**
     * Gets the value of the signProperties property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSignProperties }
     *     
     */
    public TypeSignProperties getSignProperties() {
        return signProperties;
    }

    /**
     * Sets the value of the signProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSignProperties }
     *     
     */
    public void setSignProperties(TypeSignProperties value) {
        this.signProperties = value;
    }

}
