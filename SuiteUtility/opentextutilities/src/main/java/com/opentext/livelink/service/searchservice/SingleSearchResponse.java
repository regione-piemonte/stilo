
package com.opentext.livelink.service.searchservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SingleSearchResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SingleSearchResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:SearchServices.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="ResultAnalysis" type="{urn:SearchServices.service.livelink.opentext.com}SGraph" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Results" type="{urn:SearchServices.service.livelink.opentext.com}SResultPage" minOccurs="0"/>
 *         &lt;element name="Type" type="{urn:SearchServices.service.livelink.opentext.com}DataBagType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SingleSearchResponse", propOrder = {
    "resultAnalysis",
    "results",
    "type"
})
public class SingleSearchResponse
    extends ServiceDataObject
{

    @XmlElement(name = "ResultAnalysis")
    protected List<SGraph> resultAnalysis;
    @XmlElement(name = "Results")
    protected SResultPage results;
    @XmlElement(name = "Type")
    protected List<DataBagType> type;

    /**
     * Gets the value of the resultAnalysis property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resultAnalysis property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultAnalysis().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SGraph }
     * 
     * 
     */
    public List<SGraph> getResultAnalysis() {
        if (resultAnalysis == null) {
            resultAnalysis = new ArrayList<SGraph>();
        }
        return this.resultAnalysis;
    }

    /**
     * Recupera il valore della proprietà results.
     * 
     * @return
     *     possible object is
     *     {@link SResultPage }
     *     
     */
    public SResultPage getResults() {
        return results;
    }

    /**
     * Imposta il valore della proprietà results.
     * 
     * @param value
     *     allowed object is
     *     {@link SResultPage }
     *     
     */
    public void setResults(SResultPage value) {
        this.results = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataBagType }
     * 
     * 
     */
    public List<DataBagType> getType() {
        if (type == null) {
            type = new ArrayList<DataBagType>();
        }
        return this.type;
    }

}
