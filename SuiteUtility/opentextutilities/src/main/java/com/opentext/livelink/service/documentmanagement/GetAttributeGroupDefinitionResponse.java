
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
 *         &lt;element name="GetAttributeGroupDefinitionResult" type="{urn:DocMan.service.livelink.opentext.com}AttributeGroupDefinition" minOccurs="0"/>
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
    "getAttributeGroupDefinitionResult"
})
@XmlRootElement(name = "GetAttributeGroupDefinitionResponse")
public class GetAttributeGroupDefinitionResponse {

    @XmlElement(name = "GetAttributeGroupDefinitionResult")
    protected AttributeGroupDefinition getAttributeGroupDefinitionResult;

    /**
     * Recupera il valore della proprietà getAttributeGroupDefinitionResult.
     * 
     * @return
     *     possible object is
     *     {@link AttributeGroupDefinition }
     *     
     */
    public AttributeGroupDefinition getGetAttributeGroupDefinitionResult() {
        return getAttributeGroupDefinitionResult;
    }

    /**
     * Imposta il valore della proprietà getAttributeGroupDefinitionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeGroupDefinition }
     *     
     */
    public void setGetAttributeGroupDefinitionResult(AttributeGroupDefinition value) {
        this.getAttributeGroupDefinitionResult = value;
    }

}
