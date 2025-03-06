
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
 *         &lt;element name="AddVersionContextResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "addVersionContextResult"
})
@XmlRootElement(name = "AddVersionContextResponse")
public class AddVersionContextResponse {

    @XmlElement(name = "AddVersionContextResult")
    protected String addVersionContextResult;

    /**
     * Recupera il valore della proprietà addVersionContextResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddVersionContextResult() {
        return addVersionContextResult;
    }

    /**
     * Imposta il valore della proprietà addVersionContextResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddVersionContextResult(String value) {
        this.addVersionContextResult = value;
    }

}
