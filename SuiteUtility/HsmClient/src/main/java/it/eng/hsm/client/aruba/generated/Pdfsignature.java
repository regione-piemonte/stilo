
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pdfsignature complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdfsignature">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignRequest" type="{http://arubasignservice.arubapec.it/}signRequest" minOccurs="0"/>
 *         &lt;element name="Apparence" type="{http://arubasignservice.arubapec.it/}pdfSignApparence" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pdfsignature", propOrder = {
    "signRequest",
    "apparence"
})
public class Pdfsignature {

    @XmlElement(name = "SignRequest")
    protected SignRequest signRequest;
    @XmlElement(name = "Apparence")
    protected PdfSignApparence apparence;

    /**
     * Gets the value of the signRequest property.
     * 
     * @return
     *     possible object is
     *     {@link SignRequest }
     *     
     */
    public SignRequest getSignRequest() {
        return signRequest;
    }

    /**
     * Sets the value of the signRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignRequest }
     *     
     */
    public void setSignRequest(SignRequest value) {
        this.signRequest = value;
    }

    /**
     * Gets the value of the apparence property.
     * 
     * @return
     *     possible object is
     *     {@link PdfSignApparence }
     *     
     */
    public PdfSignApparence getApparence() {
        return apparence;
    }

    /**
     * Sets the value of the apparence property.
     * 
     * @param value
     *     allowed object is
     *     {@link PdfSignApparence }
     *     
     */
    public void setApparence(PdfSignApparence value) {
        this.apparence = value;
    }

}
