
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
 *         &lt;element name="EnqueueOneDocReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeEnqueueOneDocReq"/>
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
    "enqueueOneDocReq"
})
@XmlRootElement(name = "EnqueueOneDocReq")
public class EnqueueOneDocReq {

    @XmlElement(name = "EnqueueOneDocReq", required = true)
    protected TypeEnqueueOneDocReq enqueueOneDocReq;

    /**
     * Gets the value of the enqueueOneDocReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeEnqueueOneDocReq }
     *     
     */
    public TypeEnqueueOneDocReq getEnqueueOneDocReq() {
        return enqueueOneDocReq;
    }

    /**
     * Sets the value of the enqueueOneDocReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeEnqueueOneDocReq }
     *     
     */
    public void setEnqueueOneDocReq(TypeEnqueueOneDocReq value) {
        this.enqueueOneDocReq = value;
    }

}
