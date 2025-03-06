
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for signhash complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="signhash">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignHashRequest" type="{http://arubasignservice.arubapec.it/}signHashRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "signhash", propOrder = {
    "signHashRequest"
})
public class Signhash {

    @XmlElement(name = "SignHashRequest")
    protected SignHashRequest signHashRequest;

    /**
     * Gets the value of the signHashRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SignHashRequest }
     *     
     */
    public SignHashRequest getSignHashRequest() {
        return signHashRequest;
    }

    /**
     * Sets the value of the signHashRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignHashRequest }
     *     
     */
    public void setSignHashRequest(SignHashRequest value) {
        this.signHashRequest = value;
    }

}
