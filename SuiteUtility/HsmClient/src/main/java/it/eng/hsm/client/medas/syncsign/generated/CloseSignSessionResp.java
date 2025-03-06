
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
 *         &lt;element name="CloseSignSessionResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCloseSignSessionResp"/>
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
    "closeSignSessionResp"
})
@XmlRootElement(name = "CloseSignSessionResp")
public class CloseSignSessionResp {

    @XmlElement(name = "CloseSignSessionResp", required = true)
    protected TypeCloseSignSessionResp closeSignSessionResp;

    /**
     * Gets the value of the closeSignSessionResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCloseSignSessionResp }
     *     
     */
    public TypeCloseSignSessionResp getCloseSignSessionResp() {
        return closeSignSessionResp;
    }

    /**
     * Sets the value of the closeSignSessionResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCloseSignSessionResp }
     *     
     */
    public void setCloseSignSessionResp(TypeCloseSignSessionResp value) {
        this.closeSignSessionResp = value;
    }

}
