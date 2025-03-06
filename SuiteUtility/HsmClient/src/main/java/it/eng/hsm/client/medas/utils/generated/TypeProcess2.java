
package it.eng.hsm.client.medas.utils.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeProcess2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeProcess2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docTypeList" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDocTypeList2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeProcess2", propOrder = {
    "code",
    "description",
    "docTypeList"
})
public class TypeProcess2 {

    @XmlElement(required = true)
    protected String code;
    protected String description;
    protected TypeDocTypeList2 docTypeList;

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
     * Gets the value of the docTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link TypeDocTypeList2 }
     *     
     */
    public TypeDocTypeList2 getDocTypeList() {
        return docTypeList;
    }

    /**
     * Sets the value of the docTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDocTypeList2 }
     *     
     */
    public void setDocTypeList(TypeDocTypeList2 value) {
        this.docTypeList = value;
    }

}
