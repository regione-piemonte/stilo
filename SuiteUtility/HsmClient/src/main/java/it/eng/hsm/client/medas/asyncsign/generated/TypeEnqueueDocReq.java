
package it.eng.hsm.client.medas.asyncsign.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeDocument;
import it.eng.hsm.client.medas.syncsign.generated.TypeSignProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeEnqueueDocReq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeEnqueueDocReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "typeEnqueueDocReq", propOrder = {
    "sessionId",
    "document",
    "signProperties"
})
public class TypeEnqueueDocReq {

    @XmlElement(required = true)
    protected String sessionId;
    @XmlElement(required = true)
    protected TypeDocument document;
    @XmlElement(required = true)
    protected TypeSignProperties signProperties;

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
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
