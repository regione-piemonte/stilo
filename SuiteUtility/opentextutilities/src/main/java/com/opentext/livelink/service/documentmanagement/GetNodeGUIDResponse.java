
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
 *         &lt;element name="GetNodeGUIDResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getNodeGUIDResult"
})
@XmlRootElement(name = "GetNodeGUIDResponse")
public class GetNodeGUIDResponse {

    @XmlElement(name = "GetNodeGUIDResult")
    protected String getNodeGUIDResult;

    /**
     * Recupera il valore della proprietà getNodeGUIDResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetNodeGUIDResult() {
        return getNodeGUIDResult;
    }

    /**
     * Imposta il valore della proprietà getNodeGUIDResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetNodeGUIDResult(String value) {
        this.getNodeGUIDResult = value;
    }

}
