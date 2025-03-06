
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
 *         &lt;element name="AddMajorVersionContextResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "addMajorVersionContextResult"
})
@XmlRootElement(name = "AddMajorVersionContextResponse")
public class AddMajorVersionContextResponse {

    @XmlElement(name = "AddMajorVersionContextResult")
    protected String addMajorVersionContextResult;

    /**
     * Recupera il valore della proprietà addMajorVersionContextResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddMajorVersionContextResult() {
        return addMajorVersionContextResult;
    }

    /**
     * Imposta il valore della proprietà addMajorVersionContextResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddMajorVersionContextResult(String value) {
        this.addMajorVersionContextResult = value;
    }

}
