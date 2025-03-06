
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
 *         &lt;element name="UpgradeCategoryItemsContextResult" type="{urn:Core.service.livelink.opentext.com}ChunkedOperationContext" minOccurs="0"/>
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
    "upgradeCategoryItemsContextResult"
})
@XmlRootElement(name = "UpgradeCategoryItemsContextResponse")
public class UpgradeCategoryItemsContextResponse {

    @XmlElement(name = "UpgradeCategoryItemsContextResult")
    protected ChunkedOperationContext upgradeCategoryItemsContextResult;

    /**
     * Recupera il valore della proprietà upgradeCategoryItemsContextResult.
     * 
     * @return
     *     possible object is
     *     {@link ChunkedOperationContext }
     *     
     */
    public ChunkedOperationContext getUpgradeCategoryItemsContextResult() {
        return upgradeCategoryItemsContextResult;
    }

    /**
     * Imposta il valore della proprietà upgradeCategoryItemsContextResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ChunkedOperationContext }
     *     
     */
    public void setUpgradeCategoryItemsContextResult(ChunkedOperationContext value) {
        this.upgradeCategoryItemsContextResult = value;
    }

}
