
package com.opentext.livelink.service.memberservice;

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
 *         &lt;element name="GetSearchResultsResult" type="{urn:MemberService.service.livelink.opentext.com}MemberSearchResults" minOccurs="0"/>
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
    "getSearchResultsResult"
})
@XmlRootElement(name = "GetSearchResultsResponse")
public class GetSearchResultsResponse {

    @XmlElement(name = "GetSearchResultsResult")
    protected MemberSearchResults getSearchResultsResult;

    /**
     * Recupera il valore della proprietà getSearchResultsResult.
     * 
     * @return
     *     possible object is
     *     {@link MemberSearchResults }
     *     
     */
    public MemberSearchResults getGetSearchResultsResult() {
        return getSearchResultsResult;
    }

    /**
     * Imposta il valore della proprietà getSearchResultsResult.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberSearchResults }
     *     
     */
    public void setGetSearchResultsResult(MemberSearchResults value) {
        this.getSearchResultsResult = value;
    }

}
