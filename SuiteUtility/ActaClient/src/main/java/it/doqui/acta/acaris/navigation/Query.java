
package it.doqui.acta.acaris.navigation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType"/&gt;
 *         &lt;element name="target" type="{common.acaris.acta.doqui.it}QueryableObjectType"/&gt;
 *         &lt;element name="filter" type="{common.acaris.acta.doqui.it}PropertyFilterType"/&gt;
 *         &lt;element name="criteria" type="{common.acaris.acta.doqui.it}QueryConditionType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="navigationLimits" type="{common.acaris.acta.doqui.it}NavigationConditionInfoType" minOccurs="0"/&gt;
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
    "target",
    "filter",
    "criteria",
    "navigationLimits",
    "maxItems",
    "skipCount"
})
@XmlRootElement(name = "query", namespace = "common.acaris.acta.doqui.it")
public class Query {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(namespace = "", required = true)
    protected PrincipalIdType principalId;
    @XmlElement(namespace = "", required = true)
    protected QueryableObjectType target;
    @XmlElement(namespace = "", required = true)
    protected PropertyFilterType filter;
    @XmlElement(namespace = "", required = true)
    protected List<QueryConditionType> criteria;
    @XmlElement(namespace = "")
    protected NavigationConditionInfoType navigationLimits;
    @XmlElement(namespace = "")
    protected Integer maxItems;
    @XmlElement(namespace = "")
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
     * Recupera il valore della propriet� target.
     * 
     * @return
     *     possible object is
     *     {@link QueryableObjectType }
     *     
     */
    public QueryableObjectType getTarget() {
        return target;
    }

    /**
     * Imposta il valore della propriet� target.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryableObjectType }
     *     
     */
    public void setTarget(QueryableObjectType value) {
        this.target = value;
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
     * Gets the value of the criteria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the criteria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCriteria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryConditionType }
     * 
     * 
     */
    public List<QueryConditionType> getCriteria() {
        if (criteria == null) {
            criteria = new ArrayList<QueryConditionType>();
        }
        return this.criteria;
    }

    /**
     * Recupera il valore della propriet� navigationLimits.
     * 
     * @return
     *     possible object is
     *     {@link NavigationConditionInfoType }
     *     
     */
    public NavigationConditionInfoType getNavigationLimits() {
        return navigationLimits;
    }

    /**
     * Imposta il valore della propriet� navigationLimits.
     * 
     * @param value
     *     allowed object is
     *     {@link NavigationConditionInfoType }
     *     
     */
    public void setNavigationLimits(NavigationConditionInfoType value) {
        this.navigationLimits = value;
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
