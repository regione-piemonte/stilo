
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
 *         &lt;element name="GetCategoryTemplateResult" type="{urn:DocMan.service.livelink.opentext.com}AttributeGroup" minOccurs="0"/>
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
    "getCategoryTemplateResult"
})
@XmlRootElement(name = "GetCategoryTemplateResponse")
public class GetCategoryTemplateResponse {

    @XmlElement(name = "GetCategoryTemplateResult")
    protected AttributeGroup getCategoryTemplateResult;

    /**
     * Recupera il valore della proprietà getCategoryTemplateResult.
     * 
     * @return
     *     possible object is
     *     {@link AttributeGroup }
     *     
     */
    public AttributeGroup getGetCategoryTemplateResult() {
        return getCategoryTemplateResult;
    }

    /**
     * Imposta il valore della proprietà getCategoryTemplateResult.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeGroup }
     *     
     */
    public void setGetCategoryTemplateResult(AttributeGroup value) {
        this.getCategoryTemplateResult = value;
    }

}
