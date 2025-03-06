
package it.eng.hsm.client.medas.asyncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSigner complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSigner">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fiscalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signaturePowerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="certificateId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSigner", propOrder = {
    "signerId",
    "fiscalCode",
    "signaturePowerId",
    "certificateId"
})
public class TypeSigner {

    @XmlElement(required = true)
    protected String signerId;
    @XmlElement(required = true)
    protected String fiscalCode;
    @XmlElement(required = true)
    protected String signaturePowerId;
    @XmlElement(required = true)
    protected String certificateId;

    /**
     * Gets the value of the signerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerId() {
        return signerId;
    }

    /**
     * Sets the value of the signerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerId(String value) {
        this.signerId = value;
    }

    /**
     * Gets the value of the fiscalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * Sets the value of the fiscalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiscalCode(String value) {
        this.fiscalCode = value;
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
