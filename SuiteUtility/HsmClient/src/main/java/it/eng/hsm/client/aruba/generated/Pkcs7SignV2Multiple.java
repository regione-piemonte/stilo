
package it.eng.hsm.client.aruba.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pkcs7signV2_multiple complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pkcs7signV2_multiple">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identity" type="{http://arubasignservice.arubapec.it/}auth" minOccurs="0"/>
 *         &lt;element name="SignRequestV2" type="{http://arubasignservice.arubapec.it/}signRequestV2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="detached" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pkcs7signV2_multiple", propOrder = {
    "identity",
    "signRequestV2",
    "detached"
})
public class Pkcs7SignV2Multiple {

    protected Auth identity;
    @XmlElement(name = "SignRequestV2", nillable = true)
    protected List<SignRequestV2> signRequestV2;
    protected boolean detached;

    /**
     * Gets the value of the identity property.
     * 
     * @return
     *     possible object is
     *     {@link Auth }
     *     
     */
    public Auth getIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Auth }
     *     
     */
    public void setIdentity(Auth value) {
        this.identity = value;
    }

    /**
     * Gets the value of the signRequestV2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signRequestV2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignRequestV2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignRequestV2 }
     * 
     * 
     */
    public List<SignRequestV2> getSignRequestV2() {
        if (signRequestV2 == null) {
            signRequestV2 = new ArrayList<SignRequestV2>();
        }
        return this.signRequestV2;
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

}
