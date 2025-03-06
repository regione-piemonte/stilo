
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
 *         &lt;element name="GetVersionByGUIDResult" type="{urn:DocMan.service.livelink.opentext.com}Version" minOccurs="0"/>
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
    "getVersionByGUIDResult"
})
@XmlRootElement(name = "GetVersionByGUIDResponse")
public class GetVersionByGUIDResponse {

    @XmlElement(name = "GetVersionByGUIDResult")
    protected Version getVersionByGUIDResult;

    /**
     * Recupera il valore della proprietà getVersionByGUIDResult.
     * 
     * @return
     *     possible object is
     *     {@link Version }
     *     
     */
    public Version getGetVersionByGUIDResult() {
        return getVersionByGUIDResult;
    }

    /**
     * Imposta il valore della proprietà getVersionByGUIDResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Version }
     *     
     */
    public void setGetVersionByGUIDResult(Version value) {
        this.getVersionByGUIDResult = value;
    }

}
