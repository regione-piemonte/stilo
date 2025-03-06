
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pkcs7sign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pkcs7sign">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignRequest" type="{http://arubasignservice.arubapec.it/}signRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pkcs7sign", propOrder = {
    "signRequest"
})
public class Pkcs7Sign {

    @XmlElement(name = "SignRequest")
    protected SignRequest signRequest;

    /**
     * Gets the value of the signRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SignRequest }
     *     
     */
    public SignRequest getSignRequest() {
        return signRequest;
    }

    /**
     * Sets the value of the signRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignRequest }
     *     
     */
    public void setSignRequest(SignRequest value) {
        this.signRequest = value;
    }

}
