
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeArssPadesProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeArssPadesProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdfProfile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arssPadesPropertiesApparence" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeArssPadesPropertiesApparence" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeArssPadesProperties", propOrder = {
    "pdfProfile",
    "location",
    "reason",
    "arssPadesPropertiesApparence"
})
public class TypeArssPadesProperties {

    protected String pdfProfile;
    protected String location;
    protected String reason;
    protected TypeArssPadesPropertiesApparence arssPadesPropertiesApparence;

    /**
     * Gets the value of the pdfProfile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPdfProfile() {
        return pdfProfile;
    }

    /**
     * Sets the value of the pdfProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPdfProfile(String value) {
        this.pdfProfile = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the arssPadesPropertiesApparence property.
     * 
     * @return
     *     possible object is
     *     {@link TypeArssPadesPropertiesApparence }
     *     
     */
    public TypeArssPadesPropertiesApparence getArssPadesPropertiesApparence() {
        return arssPadesPropertiesApparence;
    }

    /**
     * Sets the value of the arssPadesPropertiesApparence property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeArssPadesPropertiesApparence }
     *     
     */
    public void setArssPadesPropertiesApparence(TypeArssPadesPropertiesApparence value) {
        this.arssPadesPropertiesApparence = value;
    }

}
