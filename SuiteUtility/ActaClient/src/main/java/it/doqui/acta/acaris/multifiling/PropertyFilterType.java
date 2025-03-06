
package it.doqui.acta.acaris.multifiling;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PropertyFilterType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PropertyFilterType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="filterType" type="{common.acaris.acta.doqui.it}enumPropertyFilter"/&gt;
 *         &lt;element name="propertyList" type="{common.acaris.acta.doqui.it}QueryNameType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyFilterType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "filterType",
    "propertyList"
})
public class PropertyFilterType {

    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumPropertyFilter filterType;
    @XmlElement(namespace = "")
    protected List<QueryNameType> propertyList;

    /**
     * Recupera il valore della proprietà filterType.
     * 
     * @return
     *     possible object is
     *     {@link EnumPropertyFilter }
     *     
     */
    public EnumPropertyFilter getFilterType() {
        return filterType;
    }

    /**
     * Imposta il valore della proprietà filterType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumPropertyFilter }
     *     
     */
    public void setFilterType(EnumPropertyFilter value) {
        this.filterType = value;
    }

    /**
     * Gets the value of the propertyList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryNameType }
     * 
     * 
     */
    public List<QueryNameType> getPropertyList() {
        if (propertyList == null) {
            propertyList = new ArrayList<QueryNameType>();
        }
        return this.propertyList;
    }

}
