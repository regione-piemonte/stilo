
package com.opentext.livelink.service.searchservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SingleSearchRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SingleSearchRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:SearchServices.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="DataCollectionSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstResultToRetrieve" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumResultsToRetrieve" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="QueryLanguage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultOrderSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultSetSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultTransformationSpec" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SingleSearchRequest", propOrder = {
    "dataCollectionSpec",
    "firstResultToRetrieve",
    "numResultsToRetrieve",
    "queryLanguage",
    "resultOrderSpec",
    "resultSetSpec",
    "resultTransformationSpec"
})
public class SingleSearchRequest
    extends ServiceDataObject
{

    @XmlElement(name = "DataCollectionSpec")
    protected String dataCollectionSpec;
    @XmlElement(name = "FirstResultToRetrieve")
    protected int firstResultToRetrieve;
    @XmlElement(name = "NumResultsToRetrieve")
    protected int numResultsToRetrieve;
    @XmlElement(name = "QueryLanguage")
    protected String queryLanguage;
    @XmlElement(name = "ResultOrderSpec")
    protected String resultOrderSpec;
    @XmlElement(name = "ResultSetSpec")
    protected String resultSetSpec;
    @XmlElement(name = "ResultTransformationSpec")
    protected List<String> resultTransformationSpec;

    /**
     * Recupera il valore della proprietà dataCollectionSpec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCollectionSpec() {
        return dataCollectionSpec;
    }

    /**
     * Imposta il valore della proprietà dataCollectionSpec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCollectionSpec(String value) {
        this.dataCollectionSpec = value;
    }

    /**
     * Recupera il valore della proprietà firstResultToRetrieve.
     * 
     */
    public int getFirstResultToRetrieve() {
        return firstResultToRetrieve;
    }

    /**
     * Imposta il valore della proprietà firstResultToRetrieve.
     * 
     */
    public void setFirstResultToRetrieve(int value) {
        this.firstResultToRetrieve = value;
    }

    /**
     * Recupera il valore della proprietà numResultsToRetrieve.
     * 
     */
    public int getNumResultsToRetrieve() {
        return numResultsToRetrieve;
    }

    /**
     * Imposta il valore della proprietà numResultsToRetrieve.
     * 
     */
    public void setNumResultsToRetrieve(int value) {
        this.numResultsToRetrieve = value;
    }

    /**
     * Recupera il valore della proprietà queryLanguage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryLanguage() {
        return queryLanguage;
    }

    /**
     * Imposta il valore della proprietà queryLanguage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryLanguage(String value) {
        this.queryLanguage = value;
    }

    /**
     * Recupera il valore della proprietà resultOrderSpec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultOrderSpec() {
        return resultOrderSpec;
    }

    /**
     * Imposta il valore della proprietà resultOrderSpec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultOrderSpec(String value) {
        this.resultOrderSpec = value;
    }

    /**
     * Recupera il valore della proprietà resultSetSpec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultSetSpec() {
        return resultSetSpec;
    }

    /**
     * Imposta il valore della proprietà resultSetSpec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultSetSpec(String value) {
        this.resultSetSpec = value;
    }

    /**
     * Gets the value of the resultTransformationSpec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resultTransformationSpec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultTransformationSpec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getResultTransformationSpec() {
        if (resultTransformationSpec == null) {
            resultTransformationSpec = new ArrayList<String>();
        }
        return this.resultTransformationSpec;
    }

}
