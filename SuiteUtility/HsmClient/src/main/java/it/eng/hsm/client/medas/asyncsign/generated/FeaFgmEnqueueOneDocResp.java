
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
 *         &lt;element name="FeaFgmEnqueueOneDocResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeFeaFgmEnqueueOneDocResp"/>
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
    "feaFgmEnqueueOneDocResp"
})
@XmlRootElement(name = "FeaFgmEnqueueOneDocResp")
public class FeaFgmEnqueueOneDocResp {

    @XmlElement(name = "FeaFgmEnqueueOneDocResp", required = true)
    protected TypeFeaFgmEnqueueOneDocResp feaFgmEnqueueOneDocResp;

    /**
     * Gets the value of the feaFgmEnqueueOneDocResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeFeaFgmEnqueueOneDocResp }
     *     
     */
    public TypeFeaFgmEnqueueOneDocResp getFeaFgmEnqueueOneDocResp() {
        return feaFgmEnqueueOneDocResp;
    }

    /**
     * Sets the value of the feaFgmEnqueueOneDocResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeFeaFgmEnqueueOneDocResp }
     *     
     */
    public void setFeaFgmEnqueueOneDocResp(TypeFeaFgmEnqueueOneDocResp value) {
        this.feaFgmEnqueueOneDocResp = value;
    }

}
