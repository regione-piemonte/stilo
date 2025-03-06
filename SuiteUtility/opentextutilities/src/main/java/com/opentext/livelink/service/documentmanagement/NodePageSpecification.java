
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodePageSpecification complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodePageSpecification">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="ContainersOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ExcludeTypes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IncludeHiddenNodes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IncludeTypes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NameFilter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PageContext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PageSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TypeFilter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodePageSpecification", propOrder = {
    "containersOnly",
    "excludeTypes",
    "includeHiddenNodes",
    "includeTypes",
    "nameFilter",
    "pageContext",
    "pageNumber",
    "pageSize",
    "typeFilter"
})
public class NodePageSpecification
    extends ServiceDataObject
{

    @XmlElement(name = "ContainersOnly")
    protected boolean containersOnly;
    @XmlElement(name = "ExcludeTypes")
    protected List<String> excludeTypes;
    @XmlElement(name = "IncludeHiddenNodes")
    protected boolean includeHiddenNodes;
    @XmlElement(name = "IncludeTypes")
    protected List<String> includeTypes;
    @XmlElement(name = "NameFilter")
    protected String nameFilter;
    @XmlElement(name = "PageContext")
    protected String pageContext;
    @XmlElement(name = "PageNumber")
    protected int pageNumber;
    @XmlElement(name = "PageSize")
    protected int pageSize;
    @XmlElement(name = "TypeFilter")
    protected String typeFilter;

    /**
     * Recupera il valore della proprietà containersOnly.
     * 
     */
    public boolean isContainersOnly() {
        return containersOnly;
    }

    /**
     * Imposta il valore della proprietà containersOnly.
     * 
     */
    public void setContainersOnly(boolean value) {
        this.containersOnly = value;
    }

    /**
     * Gets the value of the excludeTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the excludeTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExcludeTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExcludeTypes() {
        if (excludeTypes == null) {
            excludeTypes = new ArrayList<String>();
        }
        return this.excludeTypes;
    }

    /**
     * Recupera il valore della proprietà includeHiddenNodes.
     * 
     */
    public boolean isIncludeHiddenNodes() {
        return includeHiddenNodes;
    }

    /**
     * Imposta il valore della proprietà includeHiddenNodes.
     * 
     */
    public void setIncludeHiddenNodes(boolean value) {
        this.includeHiddenNodes = value;
    }

    /**
     * Gets the value of the includeTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includeTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludeTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIncludeTypes() {
        if (includeTypes == null) {
            includeTypes = new ArrayList<String>();
        }
        return this.includeTypes;
    }

    /**
     * Recupera il valore della proprietà nameFilter.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameFilter() {
        return nameFilter;
    }

    /**
     * Imposta il valore della proprietà nameFilter.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameFilter(String value) {
        this.nameFilter = value;
    }

    /**
     * Recupera il valore della proprietà pageContext.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageContext() {
        return pageContext;
    }

    /**
     * Imposta il valore della proprietà pageContext.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageContext(String value) {
        this.pageContext = value;
    }

    /**
     * Recupera il valore della proprietà pageNumber.
     * 
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Imposta il valore della proprietà pageNumber.
     * 
     */
    public void setPageNumber(int value) {
        this.pageNumber = value;
    }

    /**
     * Recupera il valore della proprietà pageSize.
     * 
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Imposta il valore della proprietà pageSize.
     * 
     */
    public void setPageSize(int value) {
        this.pageSize = value;
    }

    /**
     * Recupera il valore della proprietà typeFilter.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeFilter() {
        return typeFilter;
    }

    /**
     * Imposta il valore della proprietà typeFilter.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeFilter(String value) {
        this.typeFilter = value;
    }

}
