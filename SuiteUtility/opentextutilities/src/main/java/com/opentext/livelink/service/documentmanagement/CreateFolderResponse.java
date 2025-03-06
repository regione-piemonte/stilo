
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
 *         &lt;element name="CreateFolderResult" type="{urn:DocMan.service.livelink.opentext.com}Node" minOccurs="0"/>
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
    "createFolderResult"
})
@XmlRootElement(name = "CreateFolderResponse")
public class CreateFolderResponse {

    @XmlElement(name = "CreateFolderResult")
    protected Node createFolderResult;

    /**
     * Recupera il valore della proprietà createFolderResult.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getCreateFolderResult() {
        return createFolderResult;
    }

    /**
     * Imposta il valore della proprietà createFolderResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setCreateFolderResult(Node value) {
        this.createFolderResult = value;
    }

}
