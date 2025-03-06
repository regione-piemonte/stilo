
package it.doqui.acta.acaris.management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ObjectMetadataType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ObjectMetadataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="queryName" type="{common.acaris.acta.doqui.it}QueryNameType"/&gt;
 *         &lt;element name="selectable" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="queryable" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="operators" type="{common.acaris.acta.doqui.it}string" maxOccurs="unbounded"/&gt;
 *         &lt;element name="dataType" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="updatable" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="propertyFilterConfigurationInfo" type="{common.acaris.acta.doqui.it}PropertyFilterConfigurationInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectMetadataType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "queryName",
    "selectable",
    "queryable",
    "operators",
    "dataType",
    "updatable",
    "propertyFilterConfigurationInfo"
})
public class ObjectMetadataType {

    @XmlElement(namespace = "", required = true)
    protected QueryNameType queryName;
    @XmlElement(namespace = "")
    protected boolean selectable;
    @XmlElement(namespace = "")
    protected boolean queryable;
    @XmlElement(namespace = "", required = true)
    protected List<String> operators;
    @XmlElement(namespace = "", required = true)
    protected String dataType;
    @XmlElement(namespace = "")
    protected boolean updatable;
    @XmlElement(namespace = "")
    protected List<PropertyFilterConfigurationInfoType> propertyFilterConfigurationInfo;

    /**
     * Recupera il valore della proprietà queryName.
     * 
     * @return
     *     possible object is
     *     {@link QueryNameType }
     *     
     */
    public QueryNameType getQueryName() {
        return queryName;
    }

    /**
     * Imposta il valore della proprietà queryName.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryNameType }
     *     
     */
    public void setQueryName(QueryNameType value) {
        this.queryName = value;
    }

    /**
     * Recupera il valore della proprietà selectable.
     * 
     */
    public boolean isSelectable() {
        return selectable;
    }

    /**
     * Imposta il valore della proprietà selectable.
     * 
     */
    public void setSelectable(boolean value) {
        this.selectable = value;
    }

    /**
     * Recupera il valore della proprietà queryable.
     * 
     */
    public boolean isQueryable() {
        return queryable;
    }

    /**
     * Imposta il valore della proprietà queryable.
     * 
     */
    public void setQueryable(boolean value) {
        this.queryable = value;
    }

    /**
     * Gets the value of the operators property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operators property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperators().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOperators() {
        if (operators == null) {
            operators = new ArrayList<String>();
        }
        return this.operators;
    }

    /**
     * Recupera il valore della proprietà dataType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Imposta il valore della proprietà dataType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Recupera il valore della proprietà updatable.
     * 
     */
    public boolean isUpdatable() {
        return updatable;
    }

    /**
     * Imposta il valore della proprietà updatable.
     * 
     */
    public void setUpdatable(boolean value) {
        this.updatable = value;
    }

    /**
     * Gets the value of the propertyFilterConfigurationInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyFilterConfigurationInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyFilterConfigurationInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyFilterConfigurationInfoType }
     * 
     * 
     */
    public List<PropertyFilterConfigurationInfoType> getPropertyFilterConfigurationInfo() {
        if (propertyFilterConfigurationInfo == null) {
            propertyFilterConfigurationInfo = new ArrayList<PropertyFilterConfigurationInfoType>();
        }
        return this.propertyFilterConfigurationInfo;
    }

}
