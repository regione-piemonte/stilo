
package com.opentext.livelink.service.documentmanagement;

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
 *         &lt;element name="ListNodesByPageAndNameResult" type="{urn:DocMan.service.livelink.opentext.com}NodePageResult" minOccurs="0"/>
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
    "listNodesByPageAndNameResult"
})
@XmlRootElement(name = "ListNodesByPageAndNameResponse")
public class ListNodesByPageAndNameResponse {

    @XmlElement(name = "ListNodesByPageAndNameResult")
    protected NodePageResult listNodesByPageAndNameResult;

    /**
     * Recupera il valore della proprietà listNodesByPageAndNameResult.
     * 
     * @return
     *     possible object is
     *     {@link NodePageResult }
     *     
     */
    public NodePageResult getListNodesByPageAndNameResult() {
        return listNodesByPageAndNameResult;
    }

    /**
     * Imposta il valore della proprietà listNodesByPageAndNameResult.
     * 
     * @param value
     *     allowed object is
     *     {@link NodePageResult }
     *     
     */
    public void setListNodesByPageAndNameResult(NodePageResult value) {
        this.listNodesByPageAndNameResult = value;
    }

}
