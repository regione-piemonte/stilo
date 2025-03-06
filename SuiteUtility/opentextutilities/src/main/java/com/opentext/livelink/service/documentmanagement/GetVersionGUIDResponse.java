
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
 *         &lt;element name="GetVersionGUIDResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getVersionGUIDResult"
})
@XmlRootElement(name = "GetVersionGUIDResponse")
public class GetVersionGUIDResponse {

    @XmlElement(name = "GetVersionGUIDResult")
    protected String getVersionGUIDResult;

    /**
     * Recupera il valore della proprietà getVersionGUIDResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetVersionGUIDResult() {
        return getVersionGUIDResult;
    }

    /**
     * Imposta il valore della proprietà getVersionGUIDResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetVersionGUIDResult(String value) {
        this.getVersionGUIDResult = value;
    }

}
