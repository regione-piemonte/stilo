
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Attribute complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Attribute">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxValues" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinValues" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ReadOnly" type="{urn:Core.service.livelink.opentext.com}BooleanObject" minOccurs="0"/>
 *         &lt;element name="Required" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Searchable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attribute", propOrder = {
    "displayName",
    "id",
    "key",
    "maxValues",
    "minValues",
    "readOnly",
    "required",
    "searchable",
    "type"
})
@XmlSeeAlso({
    PrimitiveAttribute.class,
    SetAttribute.class
})
public class Attribute
    extends ServiceDataObject
{

    @XmlElement(name = "DisplayName")
    protected String displayName;
    @XmlElement(name = "ID")
    protected long id;
    @XmlElement(name = "Key")
    protected String key;
    @XmlElement(name = "MaxValues", required = true, type = Integer.class, nillable = true)
    protected Integer maxValues;
    @XmlElement(name = "MinValues", required = true, type = Integer.class, nillable = true)
    protected Integer minValues;
    @XmlElement(name = "ReadOnly")
    protected BooleanObject readOnly;
    @XmlElement(name = "Required", required = true, type = Boolean.class, nillable = true)
    protected Boolean required;
    @XmlElement(name = "Searchable", required = true, type = Boolean.class, nillable = true)
    protected Boolean searchable;
    @XmlElement(name = "Type")
    protected String type;

    /**
     * Recupera il valore della proprietà displayName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Imposta il valore della proprietà displayName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     */
    public long getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     */
    public void setID(long value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà key.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Imposta il valore della proprietà key.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Recupera il valore della proprietà maxValues.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxValues() {
        return maxValues;
    }

    /**
     * Imposta il valore della proprietà maxValues.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxValues(Integer value) {
        this.maxValues = value;
    }

    /**
     * Recupera il valore della proprietà minValues.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinValues() {
        return minValues;
    }

    /**
     * Imposta il valore della proprietà minValues.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinValues(Integer value) {
        this.minValues = value;
    }

    /**
     * Recupera il valore della proprietà readOnly.
     * 
     * @return
     *     possible object is
     *     {@link BooleanObject }
     *     
     */
    public BooleanObject getReadOnly() {
        return readOnly;
    }

    /**
     * Imposta il valore della proprietà readOnly.
     * 
     * @param value
     *     allowed object is
     *     {@link BooleanObject }
     *     
     */
    public void setReadOnly(BooleanObject value) {
        this.readOnly = value;
    }

    /**
     * Recupera il valore della proprietà required.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequired() {
        return required;
    }

    /**
     * Imposta il valore della proprietà required.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequired(Boolean value) {
        this.required = value;
    }

    /**
     * Recupera il valore della proprietà searchable.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSearchable() {
        return searchable;
    }

    /**
     * Imposta il valore della proprietà searchable.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSearchable(Boolean value) {
        this.searchable = value;
    }

    /**
     * Recupera il valore della proprietà type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della proprietà type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
