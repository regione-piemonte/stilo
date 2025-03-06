
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
 *         &lt;element name="AddVersionResult" type="{urn:DocMan.service.livelink.opentext.com}Version" minOccurs="0"/>
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
    "addVersionResult"
})
@XmlRootElement(name = "AddVersionResponse")
public class AddVersionResponse {

    @XmlElement(name = "AddVersionResult")
    protected Version addVersionResult;

    /**
     * Recupera il valore della proprietà addVersionResult.
     * 
     * @return
     *     possible object is
     *     {@link Version }
     *     
     */
    public Version getAddVersionResult() {
        return addVersionResult;
    }

    /**
     * Imposta il valore della proprietà addVersionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Version }
     *     
     */
    public void setAddVersionResult(Version value) {
        this.addVersionResult = value;
    }

}
