
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
 *         &lt;element name="SignOneDocResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSignOneDocResp"/>
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
    "signOneDocResp"
})
@XmlRootElement(name = "SignOneDocResp")
public class SignOneDocResp {

    @XmlElement(name = "SignOneDocResp", required = true)
    protected TypeSignOneDocResp signOneDocResp;

    /**
     * Gets the value of the signOneDocResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSignOneDocResp }
     *     
     */
    public TypeSignOneDocResp getSignOneDocResp() {
        return signOneDocResp;
    }

    /**
     * Sets the value of the signOneDocResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSignOneDocResp }
     *     
     */
    public void setSignOneDocResp(TypeSignOneDocResp value) {
        this.signOneDocResp = value;
    }

}
