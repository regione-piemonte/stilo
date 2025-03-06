package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typePadesProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typePadesProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leftx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lefty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rightx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="righty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arssPadesProperties" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeArssPadesProperties" minOccurs="0"/>
 *         &lt;element name="dsPadesProperties" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDsPadesProperties" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typePadesProperties", propOrder = {
    "page",
    "leftx",
    "lefty",
    "rightx",
    "righty",
    "arssPadesProperties",
    "dsPadesProperties"
})
public class TypePadesProperties {

    protected String page;
    protected String leftx;
    protected String lefty;
    protected String rightx;
    protected String righty;
    protected TypeArssPadesProperties arssPadesProperties;
    protected TypeDsPadesProperties dsPadesProperties;

    /**
     * Gets the value of the page property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPage(String value) {
        this.page = value;
    }

    /**
     * Gets the value of the leftx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftx() {
        return leftx;
    }

    /**
     * Sets the value of the leftx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftx(String value) {
        this.leftx = value;
    }

    /**
     * Gets the value of the lefty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLefty() {
        return lefty;
    }

    /**
     * Sets the value of the lefty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLefty(String value) {
        this.lefty = value;
    }

    /**
     * Gets the value of the rightx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightx() {
        return rightx;
    }

    /**
     * Sets the value of the rightx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightx(String value) {
        this.rightx = value;
    }

    /**
     * Gets the value of the righty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRighty() {
        return righty;
    }

    /**
     * Sets the value of the righty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRighty(String value) {
        this.righty = value;
    }

    /**
     * Gets the value of the arssPadesProperties property.
     * 
     * @return
     *     possible object is
     *     {@link TypeArssPadesProperties }
     *     
     */
    public TypeArssPadesProperties getArssPadesProperties() {
        return arssPadesProperties;
    }

    /**
     * Sets the value of the arssPadesProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeArssPadesProperties }
     *     
     */
    public void setArssPadesProperties(TypeArssPadesProperties value) {
        this.arssPadesProperties = value;
    }

    /**
     * Gets the value of the dsPadesProperties property.
     * 
     * @return
     *     possible object is
     *     {@link TypeDsPadesProperties }
     *     
     */
    public TypeDsPadesProperties getDsPadesProperties() {
        return dsPadesProperties;
    }

    /**
     * Sets the value of the dsPadesProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDsPadesProperties }
     *     
     */
    public void setDsPadesProperties(TypeDsPadesProperties value) {
        this.dsPadesProperties = value;
    }

}
