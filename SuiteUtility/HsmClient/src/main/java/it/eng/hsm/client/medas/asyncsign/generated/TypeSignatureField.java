
package it.eng.hsm.client.medas.asyncsign.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSignatureField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSignatureField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fieldNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signer" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSigner"/>
 *         &lt;element name="position" type="{http://www.medas-solutions.it/ScrybaSignServer/}typePosition"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customMetaData" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="displayDate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="layout" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="imageToTextProportion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fieldDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deviceFieldDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSignatureField", propOrder = {
    "fieldNumber",
    "fieldName",
    "signer",
    "position",
    "displayName",
    "reason",
    "location",
    "customMetaData",
    "displayDate",
    "layout",
    "imageToTextProportion",
    "fieldDescription",
    "deviceFieldDescription"
})
public class TypeSignatureField {

    @XmlElement(required = true)
    protected BigInteger fieldNumber;
    @XmlElement(required = true)
    protected String fieldName;
    @XmlElement(required = true)
    protected TypeSigner signer;
    @XmlElement(required = true)
    protected TypePosition position;
    protected boolean displayName;
    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected String location;
    @XmlElement(required = true)
    protected String customMetaData;
    protected boolean displayDate;
    @XmlElement(required = true)
    protected BigInteger layout;
    @XmlElement(required = true)
    protected String imageToTextProportion;
    @XmlElement(required = true)
    protected String fieldDescription;
    @XmlElement(required = true)
    protected String deviceFieldDescription;

    /**
     * Gets the value of the fieldNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFieldNumber() {
        return fieldNumber;
    }

    /**
     * Sets the value of the fieldNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFieldNumber(BigInteger value) {
        this.fieldNumber = value;
    }

    /**
     * Gets the value of the fieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the value of the fieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSigner }
     *     
     */
    public TypeSigner getSigner() {
        return signer;
    }

    /**
     * Sets the value of the signer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSigner }
     *     
     */
    public void setSigner(TypeSigner value) {
        this.signer = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link TypePosition }
     *     
     */
    public TypePosition getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypePosition }
     *     
     */
    public void setPosition(TypePosition value) {
        this.position = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     */
    public boolean isDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     */
    public void setDisplayName(boolean value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the customMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomMetaData() {
        return customMetaData;
    }

    /**
     * Sets the value of the customMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomMetaData(String value) {
        this.customMetaData = value;
    }

    /**
     * Gets the value of the displayDate property.
     * 
     */
    public boolean isDisplayDate() {
        return displayDate;
    }

    /**
     * Sets the value of the displayDate property.
     * 
     */
    public void setDisplayDate(boolean value) {
        this.displayDate = value;
    }

    /**
     * Gets the value of the layout property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLayout() {
        return layout;
    }

    /**
     * Sets the value of the layout property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLayout(BigInteger value) {
        this.layout = value;
    }

    /**
     * Gets the value of the imageToTextProportion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageToTextProportion() {
        return imageToTextProportion;
    }

    /**
     * Sets the value of the imageToTextProportion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageToTextProportion(String value) {
        this.imageToTextProportion = value;
    }

    /**
     * Gets the value of the fieldDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldDescription() {
        return fieldDescription;
    }

    /**
     * Sets the value of the fieldDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldDescription(String value) {
        this.fieldDescription = value;
    }

    /**
     * Gets the value of the deviceFieldDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceFieldDescription() {
        return deviceFieldDescription;
    }

    /**
     * Sets the value of the deviceFieldDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceFieldDescription(String value) {
        this.deviceFieldDescription = value;
    }

}
