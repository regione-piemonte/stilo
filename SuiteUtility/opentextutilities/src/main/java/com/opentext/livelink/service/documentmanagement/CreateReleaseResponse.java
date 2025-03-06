
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
 *         &lt;element name="CreateReleaseResult" type="{urn:DocMan.service.livelink.opentext.com}CompoundDocRelease" minOccurs="0"/>
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
    "createReleaseResult"
})
@XmlRootElement(name = "CreateReleaseResponse")
public class CreateReleaseResponse {

    @XmlElement(name = "CreateReleaseResult")
    protected CompoundDocRelease createReleaseResult;

    /**
     * Recupera il valore della proprietà createReleaseResult.
     * 
     * @return
     *     possible object is
     *     {@link CompoundDocRelease }
     *     
     */
    public CompoundDocRelease getCreateReleaseResult() {
        return createReleaseResult;
    }

    /**
     * Imposta il valore della proprietà createReleaseResult.
     * 
     * @param value
     *     allowed object is
     *     {@link CompoundDocRelease }
     *     
     */
    public void setCreateReleaseResult(CompoundDocRelease value) {
        this.createReleaseResult = value;
    }

}
