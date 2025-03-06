
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodeContainerInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodeContainerInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="ChildCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ChildTypes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeContainerInfo", propOrder = {
    "childCount",
    "childTypes"
})
public class NodeContainerInfo
    extends ServiceDataObject
{

    @XmlElement(name = "ChildCount")
    protected int childCount;
    @XmlElement(name = "ChildTypes")
    protected List<String> childTypes;

    /**
     * Recupera il valore della proprietà childCount.
     * 
     */
    public int getChildCount() {
        return childCount;
    }

    /**
     * Imposta il valore della proprietà childCount.
     * 
     */
    public void setChildCount(int value) {
        this.childCount = value;
    }

    /**
     * Gets the value of the childTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the childTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getChildTypes() {
        if (childTypes == null) {
            childTypes = new ArrayList<String>();
        }
        return this.childTypes;
    }

}
