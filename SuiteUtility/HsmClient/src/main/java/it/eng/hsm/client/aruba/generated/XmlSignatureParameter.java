
package it.eng.hsm.client.aruba.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xmlSignatureParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xmlSignatureParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="canonicalizedType" type="{http://arubasignservice.arubapec.it/}canonicalizedType" minOccurs="0"/>
 *         &lt;element name="transforms" type="{http://arubasignservice.arubapec.it/}transform" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://arubasignservice.arubapec.it/}xmlSignatureType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xmlSignatureParameter", propOrder = {
    "canonicalizedType",
    "transforms",
    "type"
})
public class XmlSignatureParameter {

    protected CanonicalizedType canonicalizedType;
    @XmlElement(nillable = true)
    protected List<Transform> transforms;
    protected XmlSignatureType type;

    /**
     * Gets the value of the canonicalizedType property.
     * 
     * @return
     *     possible object is
     *     {@link CanonicalizedType }
     *     
     */
    public CanonicalizedType getCanonicalizedType() {
        return canonicalizedType;
    }

    /**
     * Sets the value of the canonicalizedType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanonicalizedType }
     *     
     */
    public void setCanonicalizedType(CanonicalizedType value) {
        this.canonicalizedType = value;
    }

    /**
     * Gets the value of the transforms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transforms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransforms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Transform }
     * 
     * 
     */
    public List<Transform> getTransforms() {
        if (transforms == null) {
            transforms = new ArrayList<Transform>();
        }
        return this.transforms;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link XmlSignatureType }
     *     
     */
    public XmlSignatureType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlSignatureType }
     *     
     */
    public void setType(XmlSignatureType value) {
        this.type = value;
    }

}
