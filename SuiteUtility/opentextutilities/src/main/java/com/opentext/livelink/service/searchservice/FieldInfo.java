
package com.opentext.livelink.service.searchservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FieldInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FieldInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:SearchServices.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Collection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OnEdge" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="OnNode" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Retrievable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Searchable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="StorageModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XD" type="{urn:SearchServices.service.livelink.opentext.com}NV" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldInfo", propOrder = {
    "collection",
    "name",
    "onEdge",
    "onNode",
    "retrievable",
    "searchable",
    "storageModel",
    "type",
    "xd"
})
public class FieldInfo
    extends ServiceDataObject
{

    @XmlElement(name = "Collection")
    protected String collection;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "OnEdge")
    protected boolean onEdge;
    @XmlElement(name = "OnNode")
    protected boolean onNode;
    @XmlElement(name = "Retrievable")
    protected boolean retrievable;
    @XmlElement(name = "Searchable")
    protected boolean searchable;
    @XmlElement(name = "StorageModel")
    protected String storageModel;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "XD")
    protected List<NV> xd;

    /**
     * Recupera il valore della proprietà collection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollection() {
        return collection;
    }

    /**
     * Imposta il valore della proprietà collection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollection(String value) {
        this.collection = value;
    }

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà onEdge.
     * 
     */
    public boolean isOnEdge() {
        return onEdge;
    }

    /**
     * Imposta il valore della proprietà onEdge.
     * 
     */
    public void setOnEdge(boolean value) {
        this.onEdge = value;
    }

    /**
     * Recupera il valore della proprietà onNode.
     * 
     */
    public boolean isOnNode() {
        return onNode;
    }

    /**
     * Imposta il valore della proprietà onNode.
     * 
     */
    public void setOnNode(boolean value) {
        this.onNode = value;
    }

    /**
     * Recupera il valore della proprietà retrievable.
     * 
     */
    public boolean isRetrievable() {
        return retrievable;
    }

    /**
     * Imposta il valore della proprietà retrievable.
     * 
     */
    public void setRetrievable(boolean value) {
        this.retrievable = value;
    }

    /**
     * Recupera il valore della proprietà searchable.
     * 
     */
    public boolean isSearchable() {
        return searchable;
    }

    /**
     * Imposta il valore della proprietà searchable.
     * 
     */
    public void setSearchable(boolean value) {
        this.searchable = value;
    }

    /**
     * Recupera il valore della proprietà storageModel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageModel() {
        return storageModel;
    }

    /**
     * Imposta il valore della proprietà storageModel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageModel(String value) {
        this.storageModel = value;
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

    /**
     * Gets the value of the xd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXD().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NV }
     * 
     * 
     */
    public List<NV> getXD() {
        if (xd == null) {
            xd = new ArrayList<NV>();
        }
        return this.xd;
    }

}
