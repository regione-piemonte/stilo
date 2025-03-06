
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="repositoryId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType" minOccurs="0"/&gt;
 *         &lt;element name="path" type="{backoffice.acaris.acta.doqui.it}enumBackOfficeNavigationPathType"/&gt;
 *         &lt;element name="rootNodeId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="date" type="{common.acaris.acta.doqui.it}date"/&gt;
 *         &lt;element name="depth" type="{common.acaris.acta.doqui.it}integer" minOccurs="0"/&gt;
 *         &lt;element name="filter" type="{common.acaris.acta.doqui.it}PropertyFilterType"/&gt;
 *         &lt;element name="maxItems" type="{common.acaris.acta.doqui.it}integer" minOccurs="0"/&gt;
 *         &lt;element name="skipCount" type="{common.acaris.acta.doqui.it}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "repositoryId",
    "principalId",
    "path",
    "rootNodeId",
    "date",
    "depth",
    "filter",
    "maxItems",
    "skipCount"
})
@XmlRootElement(name = "getDescendants", namespace = "backoffice.acaris.acta.doqui.it")
public class GetDescendants {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    protected PrincipalIdType principalId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumBackOfficeNavigationPathType path;
    @XmlElement(required = true)
    protected ObjectIdType rootNodeId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    protected Integer depth;
    @XmlElement(required = true)
    protected PropertyFilterType filter;
    protected Integer maxItems;
    protected Integer skipCount;

    /**
     * Recupera il valore della propriet� repositoryId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getRepositoryId() {
        return repositoryId;
    }

    /**
     * Imposta il valore della propriet� repositoryId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setRepositoryId(ObjectIdType value) {
        this.repositoryId = value;
    }

    /**
     * Recupera il valore della propriet� principalId.
     * 
     * @return
     *     possible object is
     *     {@link PrincipalIdType }
     *     
     */
    public PrincipalIdType getPrincipalId() {
        return principalId;
    }

    /**
     * Imposta il valore della propriet� principalId.
     * 
     * @param value
     *     allowed object is
     *     {@link PrincipalIdType }
     *     
     */
    public void setPrincipalId(PrincipalIdType value) {
        this.principalId = value;
    }

    /**
     * Recupera il valore della propriet� path.
     * 
     * @return
     *     possible object is
     *     {@link EnumBackOfficeNavigationPathType }
     *     
     */
    public EnumBackOfficeNavigationPathType getPath() {
        return path;
    }

    /**
     * Imposta il valore della propriet� path.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumBackOfficeNavigationPathType }
     *     
     */
    public void setPath(EnumBackOfficeNavigationPathType value) {
        this.path = value;
    }

    /**
     * Recupera il valore della propriet� rootNodeId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getRootNodeId() {
        return rootNodeId;
    }

    /**
     * Imposta il valore della propriet� rootNodeId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setRootNodeId(ObjectIdType value) {
        this.rootNodeId = value;
    }

    /**
     * Recupera il valore della propriet� date.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Imposta il valore della propriet� date.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Recupera il valore della propriet� depth.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * Imposta il valore della propriet� depth.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDepth(Integer value) {
        this.depth = value;
    }

    /**
     * Recupera il valore della propriet� filter.
     * 
     * @return
     *     possible object is
     *     {@link PropertyFilterType }
     *     
     */
    public PropertyFilterType getFilter() {
        return filter;
    }

    /**
     * Imposta il valore della propriet� filter.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyFilterType }
     *     
     */
    public void setFilter(PropertyFilterType value) {
        this.filter = value;
    }

    /**
     * Recupera il valore della propriet� maxItems.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxItems() {
        return maxItems;
    }

    /**
     * Imposta il valore della propriet� maxItems.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxItems(Integer value) {
        this.maxItems = value;
    }

    /**
     * Recupera il valore della propriet� skipCount.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSkipCount() {
        return skipCount;
    }

    /**
     * Imposta il valore della propriet� skipCount.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSkipCount(Integer value) {
        this.skipCount = value;
    }

}
