
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
 *         &lt;element name="SignDocResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSignDocResp"/>
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
    "signDocResp"
})
@XmlRootElement(name = "SignDocResp")
public class SignDocResp {

    @XmlElement(name = "SignDocResp", required = true)
    protected TypeSignDocResp signDocResp;

    /**
     * Gets the value of the signDocResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeSignDocResp }
     *     
     */
    public TypeSignDocResp getSignDocResp() {
        return signDocResp;
    }

    /**
     * Sets the value of the signDocResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeSignDocResp }
     *     
     */
    public void setSignDocResp(TypeSignDocResp value) {
        this.signDocResp = value;
    }

}
