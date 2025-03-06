
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
 *         &lt;element name="OpenSignSessionResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeOpenSignSessionResp"/>
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
    "openSignSessionResp"
})
@XmlRootElement(name = "OpenSignSessionResp")
public class OpenSignSessionResp {

    @XmlElement(name = "OpenSignSessionResp", required = true)
    protected TypeOpenSignSessionResp openSignSessionResp;

    /**
     * Gets the value of the openSignSessionResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOpenSignSessionResp }
     *     
     */
    public TypeOpenSignSessionResp getOpenSignSessionResp() {
        return openSignSessionResp;
    }

    /**
     * Sets the value of the openSignSessionResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOpenSignSessionResp }
     *     
     */
    public void setOpenSignSessionResp(TypeOpenSignSessionResp value) {
        this.openSignSessionResp = value;
    }

}
