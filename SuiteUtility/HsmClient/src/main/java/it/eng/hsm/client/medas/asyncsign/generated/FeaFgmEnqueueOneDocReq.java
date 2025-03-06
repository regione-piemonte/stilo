
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
 *         &lt;element name="FeaFgmEnqueueOneDocReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeFeaFgmEnqueueOneDocReq"/>
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
    "feaFgmEnqueueOneDocReq"
})
@XmlRootElement(name = "FeaFgmEnqueueOneDocReq")
public class FeaFgmEnqueueOneDocReq {

    @XmlElement(name = "FeaFgmEnqueueOneDocReq", required = true)
    protected TypeFeaFgmEnqueueOneDocReq feaFgmEnqueueOneDocReq;

    /**
     * Gets the value of the feaFgmEnqueueOneDocReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeFeaFgmEnqueueOneDocReq }
     *     
     */
    public TypeFeaFgmEnqueueOneDocReq getFeaFgmEnqueueOneDocReq() {
        return feaFgmEnqueueOneDocReq;
    }

    /**
     * Sets the value of the feaFgmEnqueueOneDocReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeFeaFgmEnqueueOneDocReq }
     *     
     */
    public void setFeaFgmEnqueueOneDocReq(TypeFeaFgmEnqueueOneDocReq value) {
        this.feaFgmEnqueueOneDocReq = value;
    }

}
