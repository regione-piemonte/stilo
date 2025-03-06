
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
 *         &lt;element name="EnqueueOneDocResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeEnqueueOneDocResp"/>
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
    "enqueueOneDocResp"
})
@XmlRootElement(name = "EnqueueOneDocResp")
public class EnqueueOneDocResp {

    @XmlElement(name = "EnqueueOneDocResp", required = true)
    protected TypeEnqueueOneDocResp enqueueOneDocResp;

    /**
     * Gets the value of the enqueueOneDocResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeEnqueueOneDocResp }
     *     
     */
    public TypeEnqueueOneDocResp getEnqueueOneDocResp() {
        return enqueueOneDocResp;
    }

    /**
     * Sets the value of the enqueueOneDocResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeEnqueueOneDocResp }
     *     
     */
    public void setEnqueueOneDocResp(TypeEnqueueOneDocResp value) {
        this.enqueueOneDocResp = value;
    }

}
