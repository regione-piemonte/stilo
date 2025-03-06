
package it.eng.hsm.client.medas.utils.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeDespatchOtpReq;

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
 *         &lt;element name="DespatchOtpReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDespatchOtpReq"/>
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
    "despatchOtpReq"
})
@XmlRootElement(name = "DespatchOtpReq")
public class DespatchOtpReq {

    @XmlElement(name = "DespatchOtpReq", required = true)
    protected TypeDespatchOtpReq despatchOtpReq;

    /**
     * Gets the value of the despatchOtpReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeDespatchOtpReq }
     *     
     */
    public TypeDespatchOtpReq getDespatchOtpReq() {
        return despatchOtpReq;
    }

    /**
     * Sets the value of the despatchOtpReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDespatchOtpReq }
     *     
     */
    public void setDespatchOtpReq(TypeDespatchOtpReq value) {
        this.despatchOtpReq = value;
    }

}
