
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DespatchOtpResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDespatchOtpResp"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "despatchOtpResp"
})
@XmlRootElement(name = "DespatchOtpResp")
public class DespatchOtpResp {

    @XmlElement(name = "DespatchOtpResp", required = true)
    protected TypeDespatchOtpResp despatchOtpResp;

    /**
     * Gets the value of the despatchOtpResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeDespatchOtpResp }
     *     
     */
    public TypeDespatchOtpResp getDespatchOtpResp() {
        return despatchOtpResp;
    }

    /**
     * Sets the value of the despatchOtpResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDespatchOtpResp }
     *     
     */
    public void setDespatchOtpResp(TypeDespatchOtpResp value) {
        this.despatchOtpResp = value;
    }

}
