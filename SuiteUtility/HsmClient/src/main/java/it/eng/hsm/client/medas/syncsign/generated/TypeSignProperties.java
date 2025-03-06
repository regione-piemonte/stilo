
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSignProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSignProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="processId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="certificateSerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parallel" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="padesProperties" type="{http://www.medas-solutions.it/ScrybaSignServer/}typePadesProperties" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSignProperties", propOrder = {
    "signMode",
    "processId",
    "certificateSerialNumber",
    "parallel",
    "padesProperties"
})
public class TypeSignProperties {

    @XmlElement(required = true)
    protected String signMode;
    @XmlElement(required = true)
    protected String processId;
    protected String certificateSerialNumber;
    protected Boolean parallel;
    protected TypePadesProperties padesProperties;

    /**
     * Gets the value of the signMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignMode() {
        return signMode;
    }

    /**
     * Sets the value of the signMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignMode(String value) {
        this.signMode = value;
    }

    /**
     * Gets the value of the processId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * Sets the value of the processId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessId(String value) {
        this.processId = value;
    }

    /**
     * Gets the value of the certificateSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    /**
     * Sets the value of the certificateSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateSerialNumber(String value) {
        this.certificateSerialNumber = value;
    }

    /**
     * Gets the value of the parallel property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isParallel() {
        return parallel;
    }

    /**
     * Sets the value of the parallel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setParallel(Boolean value) {
        this.parallel = value;
    }

    /**
     * Gets the value of the padesProperties property.
     * 
     * @return
     *     possible object is
     *     {@link TypePadesProperties }
     *     
     */
    public TypePadesProperties getPadesProperties() {
        return padesProperties;
    }

    /**
     * Sets the value of the padesProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypePadesProperties }
     *     
     */
    public void setPadesProperties(TypePadesProperties value) {
        this.padesProperties = value;
    }

}
