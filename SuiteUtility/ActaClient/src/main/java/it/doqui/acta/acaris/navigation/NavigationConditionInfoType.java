
package it.doqui.acta.acaris.navigation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NavigationConditionInfoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NavigationConditionInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parentNodeId" type="{common.acaris.acta.doqui.it}ObjectIdType" minOccurs="0"/&gt;
 *         &lt;element name="limitToChildren" type="{common.acaris.acta.doqui.it}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NavigationConditionInfoType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "parentNodeId",
    "limitToChildren"
})
public class NavigationConditionInfoType {

    @XmlElement(namespace = "")
    protected ObjectIdType parentNodeId;
    @XmlElement(namespace = "")
    protected Boolean limitToChildren;

    /**
     * Recupera il valore della proprietà parentNodeId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getParentNodeId() {
        return parentNodeId;
    }

    /**
     * Imposta il valore della proprietà parentNodeId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setParentNodeId(ObjectIdType value) {
        this.parentNodeId = value;
    }

    /**
     * Recupera il valore della proprietà limitToChildren.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLimitToChildren() {
        return limitToChildren;
    }

    /**
     * Imposta il valore della proprietà limitToChildren.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLimitToChildren(Boolean value) {
        this.limitToChildren = value;
    }

}
