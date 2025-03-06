
package it.eng.hsm.client.medas.syncsign.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSignaturePower complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSignaturePower">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="automatic" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="certificateList" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCertificateList" minOccurs="0"/>
 *         &lt;element name="processes" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeProcess" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSignaturePower", propOrder = {
    "code",
    "description",
    "automatic",
    "certificateList",
    "processes"
})
public class TypeSignaturePower {

    @XmlElement(required = true)
    protected String code;
    protected String description;
    protected Boolean automatic;
    protected TypeCertificateList certificateList;
    @XmlElement(nillable = true)
    protected List<TypeProcess> processes;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the automatic property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutomatic() {
        return automatic;
    }

    /**
     * Sets the value of the automatic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutomatic(Boolean value) {
        this.automatic = value;
    }

    /**
     * Gets the value of the certificateList property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCertificateList }
     *     
     */
    public TypeCertificateList getCertificateList() {
        return certificateList;
    }

    /**
     * Sets the value of the certificateList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCertificateList }
     *     
     */
    public void setCertificateList(TypeCertificateList value) {
        this.certificateList = value;
    }

    /**
     * Gets the value of the processes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcesses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeProcess }
     * 
     * 
     */
    public List<TypeProcess> getProcesses() {
        if (processes == null) {
            processes = new ArrayList<TypeProcess>();
        }
        return this.processes;
    }

}
