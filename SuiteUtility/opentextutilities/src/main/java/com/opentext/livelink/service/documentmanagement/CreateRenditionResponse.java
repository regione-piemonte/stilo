
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
 *         &lt;element name="CreateRenditionResult" type="{urn:DocMan.service.livelink.opentext.com}Version" minOccurs="0"/>
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
    "createRenditionResult"
})
@XmlRootElement(name = "CreateRenditionResponse")
public class CreateRenditionResponse {

    @XmlElement(name = "CreateRenditionResult")
    protected Version createRenditionResult;

    /**
     * Recupera il valore della proprietà createRenditionResult.
     * 
     * @return
     *     possible object is
     *     {@link Version }
     *     
     */
    public Version getCreateRenditionResult() {
        return createRenditionResult;
    }

    /**
     * Imposta il valore della proprietà createRenditionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Version }
     *     
     */
    public void setCreateRenditionResult(Version value) {
        this.createRenditionResult = value;
    }

}
