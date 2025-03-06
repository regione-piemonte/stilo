
package it.eng.hsm.client.medas.asyncsign.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeCloseSignSessionReq;

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
 *         &lt;element name="CloseSignSessionReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCloseSignSessionReq"/>
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
    "closeSignSessionReq"
})
@XmlRootElement(name = "CloseSignSessionReq")
public class CloseSignSessionReq {

    @XmlElement(name = "CloseSignSessionReq", required = true)
    protected TypeCloseSignSessionReq closeSignSessionReq;

    /**
     * Gets the value of the closeSignSessionReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCloseSignSessionReq }
     *     
     */
    public TypeCloseSignSessionReq getCloseSignSessionReq() {
        return closeSignSessionReq;
    }

    /**
     * Sets the value of the closeSignSessionReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCloseSignSessionReq }
     *     
     */
    public void setCloseSignSessionReq(TypeCloseSignSessionReq value) {
        this.closeSignSessionReq = value;
    }

}
