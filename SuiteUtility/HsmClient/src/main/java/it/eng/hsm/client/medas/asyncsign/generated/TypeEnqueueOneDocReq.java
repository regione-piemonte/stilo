
package it.eng.hsm.client.medas.asyncsign.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeDocument;
import it.eng.hsm.client.medas.syncsign.generated.TypeSignProperties;
import it.eng.hsm.client.medas.syncsign.generated.TypeUser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeEnqueueOneDocReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeEnqueueOneDocReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeUser"/>
 *         &lt;element name="profileId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="callBackURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="document" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDocument"/>
 *         &lt;element name="certificateId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signaturePowerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "typeEnqueueOneDocReq", propOrder = {
    "user",
    "profileId",
    "callBackURL",
    "document",
    "certificateId",
    "signaturePowerId",
    "signProperties"
})
public class TypeEnqueueOneDocReq {

    @XmlElement(required = true)
    protected TypeUser user;
    @XmlElement(required = true)
    protected String profileId;
    @XmlElement(required = true)
    protected String callBackURL;
    @XmlElement(required = true)
    protected TypeDocument document;
    @XmlElement(required = true)
    protected String certificateId;
    @XmlElement(required = true)
    protected String signaturePowerId;
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
     * Gets the value of the profileId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * Sets the value of the profileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileId(String value) {
        this.profileId = value;
    }

    /**
     * Gets the value of the callBackURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallBackURL() {
        return callBackURL;
    }

    /**
     * Sets the value of the callBackURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallBackURL(String value) {
        this.callBackURL = value;
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
