
package it.eng.hsm.client.medas.asyncsign.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeOpenSignSessionReq;

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
 *         &lt;element name="OpenSignSessionReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeOpenSignSessionReq"/>
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
    "openSignSessionReq"
})
@XmlRootElement(name = "OpenSignSessionReq")
public class OpenSignSessionReq {

    @XmlElement(name = "OpenSignSessionReq", required = true)
    protected TypeOpenSignSessionReq openSignSessionReq;

    /**
     * Gets the value of the openSignSessionReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOpenSignSessionReq }
     *     
     */
    public TypeOpenSignSessionReq getOpenSignSessionReq() {
        return openSignSessionReq;
    }

    /**
     * Sets the value of the openSignSessionReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOpenSignSessionReq }
     *     
     */
    public void setOpenSignSessionReq(TypeOpenSignSessionReq value) {
        this.openSignSessionReq = value;
    }

}
