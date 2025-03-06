
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
 *         &lt;element name="EnqueueDocResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeEnqueueDocResp"/>
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
    "enqueueDocResp"
})
@XmlRootElement(name = "EnqueueDocResp")
public class EnqueueDocResp {

    @XmlElement(name = "EnqueueDocResp", required = true)
    protected TypeEnqueueDocResp enqueueDocResp;

    /**
     * Gets the value of the enqueueDocResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeEnqueueDocResp }
     *     
     */
    public TypeEnqueueDocResp getEnqueueDocResp() {
        return enqueueDocResp;
    }

    /**
     * Sets the value of the enqueueDocResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeEnqueueDocResp }
     *     
     */
    public void setEnqueueDocResp(TypeEnqueueDocResp value) {
        this.enqueueDocResp = value;
    }

}
