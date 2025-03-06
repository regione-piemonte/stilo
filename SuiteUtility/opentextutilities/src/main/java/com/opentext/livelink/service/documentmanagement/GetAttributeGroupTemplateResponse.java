
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
 *         &lt;element name="GetAttributeGroupTemplateResult" type="{urn:DocMan.service.livelink.opentext.com}AttributeGroup" minOccurs="0"/>
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
    "getAttributeGroupTemplateResult"
})
@XmlRootElement(name = "GetAttributeGroupTemplateResponse")
public class GetAttributeGroupTemplateResponse {

    @XmlElement(name = "GetAttributeGroupTemplateResult")
    protected AttributeGroup getAttributeGroupTemplateResult;

    /**
     * Recupera il valore della proprietà getAttributeGroupTemplateResult.
     * 
     * @return
     *     possible object is
     *     {@link AttributeGroup }
     *     
     */
    public AttributeGroup getGetAttributeGroupTemplateResult() {
        return getAttributeGroupTemplateResult;
    }

    /**
     * Imposta il valore della proprietà getAttributeGroupTemplateResult.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeGroup }
     *     
     */
    public void setGetAttributeGroupTemplateResult(AttributeGroup value) {
        this.getAttributeGroupTemplateResult = value;
    }

}
