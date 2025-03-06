
package com.opentext.livelink.service.searchservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="queries" type="{urn:SearchServices.service.livelink.opentext.com}SingleSearchRequest" minOccurs="0"/>
 *         &lt;element name="partitionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "queries",
    "partitionName"
})
@XmlRootElement(name = "Search")
public class Search {

    protected SingleSearchRequest queries;
    protected String partitionName;

    /**
     * Recupera il valore della proprietà queries.
     * 
     * @return
     *     possible object is
     *     {@link SingleSearchRequest }
     *     
     */
    public SingleSearchRequest getQueries() {
        return queries;
    }

    /**
     * Imposta il valore della proprietà queries.
     * 
     * @param value
     *     allowed object is
     *     {@link SingleSearchRequest }
     *     
     */
    public void setQueries(SingleSearchRequest value) {
        this.queries = value;
    }

    /**
     * Recupera il valore della proprietà partitionName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitionName() {
        return partitionName;
    }

    /**
     * Imposta il valore della proprietà partitionName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitionName(String value) {
        this.partitionName = value;
    }

}
