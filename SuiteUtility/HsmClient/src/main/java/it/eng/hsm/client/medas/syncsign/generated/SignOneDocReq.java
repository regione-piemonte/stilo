
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
 *         &lt;element name="SignOneDocReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSignOneDocReq"/>
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
    "signOneDocReq"
})
@XmlRootElement(name = "SignOneDocReq")
public class SignOneDocReq {

    @XmlElement(name = "SignOneDocReq", required = true)
    protected TypeSignOneDocReq signOneDocReq;

    /**
     * Gets the value of the signOneDocReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSignOneDocReq }
     *     
     */
    public TypeSignOneDocReq getSignOneDocReq() {
        return signOneDocReq;
    }

    /**
     * Sets the value of the signOneDocReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSignOneDocReq }
     *     
     */
    public void setSignOneDocReq(TypeSignOneDocReq value) {
        this.signOneDocReq = value;
    }

}
