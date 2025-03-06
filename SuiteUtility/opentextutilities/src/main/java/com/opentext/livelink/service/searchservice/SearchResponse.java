
package com.opentext.livelink.service.searchservice;

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
 *         &lt;element name="SearchResult" type="{urn:SearchServices.service.livelink.opentext.com}SingleSearchResponse" minOccurs="0"/>
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
    "searchResult"
})
@XmlRootElement(name = "SearchResponse")
public class SearchResponse {

    @XmlElement(name = "SearchResult")
    protected SingleSearchResponse searchResult;

    /**
     * Recupera il valore della proprietà searchResult.
     * 
     * @return
     *     possible object is
     *     {@link SingleSearchResponse }
     *     
     */
    public SingleSearchResponse getSearchResult() {
        return searchResult;
    }

    /**
     * Imposta il valore della proprietà searchResult.
     * 
     * @param value
     *     allowed object is
     *     {@link SingleSearchResponse }
     *     
     */
    public void setSearchResult(SingleSearchResponse value) {
        this.searchResult = value;
    }

}
