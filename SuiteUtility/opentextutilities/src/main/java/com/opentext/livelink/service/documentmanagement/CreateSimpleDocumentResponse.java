
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
 *         &lt;element name="CreateSimpleDocumentResult" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "createSimpleDocumentResult"
})
@XmlRootElement(name = "CreateSimpleDocumentResponse")
public class CreateSimpleDocumentResponse {

    @XmlElement(name = "CreateSimpleDocumentResult")
    protected long createSimpleDocumentResult;

    /**
     * Recupera il valore della proprietà createSimpleDocumentResult.
     * 
     */
    public long getCreateSimpleDocumentResult() {
        return createSimpleDocumentResult;
    }

    /**
     * Imposta il valore della proprietà createSimpleDocumentResult.
     * 
     */
    public void setCreateSimpleDocumentResult(long value) {
        this.createSimpleDocumentResult = value;
    }

}
