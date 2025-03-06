
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
 *         &lt;element name="EnqueueDocReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeEnqueueDocReq"/>
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
    "enqueueDocReq"
})
@XmlRootElement(name = "EnqueueDocReq")
public class EnqueueDocReq {

    @XmlElement(name = "EnqueueDocReq", required = true)
    protected TypeEnqueueDocReq enqueueDocReq;

    /**
     * Gets the value of the enqueueDocReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeEnqueueDocReq }
     *     
     */
    public TypeEnqueueDocReq getEnqueueDocReq() {
        return enqueueDocReq;
    }

    /**
     * Sets the value of the enqueueDocReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeEnqueueDocReq }
     *     
     */
    public void setEnqueueDocReq(TypeEnqueueDocReq value) {
        this.enqueueDocReq = value;
    }

}
