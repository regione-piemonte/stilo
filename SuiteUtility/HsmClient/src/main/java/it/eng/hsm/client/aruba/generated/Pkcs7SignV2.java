
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pkcs7signV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pkcs7signV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignRequestV2" type="{http://arubasignservice.arubapec.it/}signRequestV2" minOccurs="0"/>
 *         &lt;element name="detached" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="returnder" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pkcs7signV2", propOrder = {
    "signRequestV2",
    "detached",
    "returnder"
})
public class Pkcs7SignV2 {

    @XmlElement(name = "SignRequestV2")
    protected SignRequestV2 signRequestV2;
    protected boolean detached;
    protected boolean returnder;

    /**
     * Gets the value of the signRequestV2 property.
     * 
     * @return
     *     possible object is
     *     {@link SignRequestV2 }
     *     
     */
    public SignRequestV2 getSignRequestV2() {
        return signRequestV2;
    }

    /**
     * Sets the value of the signRequestV2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignRequestV2 }
     *     
     */
    public void setSignRequestV2(SignRequestV2 value) {
        this.signRequestV2 = value;
    }

    /**
     * Gets the value of the detached property.
     * 
     */
    public boolean isDetached() {
        return detached;
    }

    /**
     * Sets the value of the detached property.
     * 
     */
    public void setDetached(boolean value) {
        this.detached = value;
    }

    /**
     * Gets the value of the returnder property.
     * 
     */
    public boolean isReturnder() {
        return returnder;
    }

    /**
     * Sets the value of the returnder property.
     * 
     */
    public void setReturnder(boolean value) {
        this.returnder = value;
    }

}
