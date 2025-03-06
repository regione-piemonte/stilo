
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
 *         &lt;element name="SearchForMembersResult" type="{urn:Core.service.livelink.opentext.com}PageHandle" minOccurs="0"/>
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
    "searchForMembersResult"
})
@XmlRootElement(name = "SearchForMembersResponse")
public class SearchForMembersResponse {

    @XmlElement(name = "SearchForMembersResult")
    protected PageHandle searchForMembersResult;

    /**
     * Recupera il valore della proprietà searchForMembersResult.
     * 
     * @return
     *     possible object is
     *     {@link PageHandle }
     *     
     */
    public PageHandle getSearchForMembersResult() {
        return searchForMembersResult;
    }

    /**
     * Imposta il valore della proprietà searchForMembersResult.
     * 
     * @param value
     *     allowed object is
     *     {@link PageHandle }
     *     
     */
    public void setSearchForMembersResult(PageHandle value) {
        this.searchForMembersResult = value;
    }

}
