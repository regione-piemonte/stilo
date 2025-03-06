
package com.opentext.livelink.service.documentmanagement;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodeID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="categoryInheritanceObjs" type="{urn:DocMan.service.livelink.opentext.com}CategoryInheritance" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nodeID",
    "categoryInheritanceObjs"
})
@XmlRootElement(name = "SetCategoryInheritance")
public class SetCategoryInheritance {

    protected long nodeID;
    @XmlElement(nillable = true)
    protected List<CategoryInheritance> categoryInheritanceObjs;

    /**
     * Recupera il valore della proprietà nodeID.
     * 
     */
    public long getNodeID() {
        return nodeID;
    }

    /**
     * Imposta il valore della proprietà nodeID.
     * 
     */
    public void setNodeID(long value) {
        this.nodeID = value;
    }

    /**
     * Gets the value of the categoryInheritanceObjs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoryInheritanceObjs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryInheritanceObjs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CategoryInheritance }
     * 
     * 
     */
    public List<CategoryInheritance> getCategoryInheritanceObjs() {
        if (categoryInheritanceObjs == null) {
            categoryInheritanceObjs = new ArrayList<CategoryInheritance>();
        }
        return this.categoryInheritanceObjs;
    }

}
