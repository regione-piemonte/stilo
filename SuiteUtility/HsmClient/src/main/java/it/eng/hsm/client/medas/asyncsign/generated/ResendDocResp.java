
package it.eng.hsm.client.medas.asyncsign.generated;

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
 *         &lt;element name="ResendDocResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeResendDocResp"/>
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
    "resendDocResp"
})
@XmlRootElement(name = "ResendDocResp")
public class ResendDocResp {

    @XmlElement(name = "ResendDocResp", required = true)
    protected TypeResendDocResp resendDocResp;

    /**
     * Gets the value of the resendDocResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeResendDocResp }
     *     
     */
    public TypeResendDocResp getResendDocResp() {
        return resendDocResp;
    }

    /**
     * Sets the value of the resendDocResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeResendDocResp }
     *     
     */
    public void setResendDocResp(TypeResendDocResp value) {
        this.resendDocResp = value;
    }

}
