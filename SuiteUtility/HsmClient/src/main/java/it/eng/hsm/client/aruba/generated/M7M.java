
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for m7m complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="m7m">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MarkRequest" type="{http://arubasignservice.arubapec.it/}MarkRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "m7m", propOrder = {
    "markRequest"
})
public class M7M {

    @XmlElement(name = "MarkRequest", required = true)
    protected MarkRequest markRequest;

    /**
     * Gets the value of the markRequest property.
     * 
     * @return
     *     possible object is
     *     {@link MarkRequest }
     *     
     */
    public MarkRequest getMarkRequest() {
        return markRequest;
    }

    /**
     * Sets the value of the markRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkRequest }
     *     
     */
    public void setMarkRequest(MarkRequest value) {
        this.markRequest = value;
    }

}
