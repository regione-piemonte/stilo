
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
 *         &lt;element name="GetCategoryDefinitionResult" type="{urn:DocMan.service.livelink.opentext.com}AttributeGroupDefinition" minOccurs="0"/>
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
    "getCategoryDefinitionResult"
})
@XmlRootElement(name = "GetCategoryDefinitionResponse")
public class GetCategoryDefinitionResponse {

    @XmlElement(name = "GetCategoryDefinitionResult")
    protected AttributeGroupDefinition getCategoryDefinitionResult;

    /**
     * Recupera il valore della proprietà getCategoryDefinitionResult.
     * 
     * @return
     *     possible object is
     *     {@link AttributeGroupDefinition }
     *     
     */
    public AttributeGroupDefinition getGetCategoryDefinitionResult() {
        return getCategoryDefinitionResult;
    }

    /**
     * Imposta il valore della proprietà getCategoryDefinitionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeGroupDefinition }
     *     
     */
    public void setGetCategoryDefinitionResult(AttributeGroupDefinition value) {
        this.getCategoryDefinitionResult = value;
    }

}
