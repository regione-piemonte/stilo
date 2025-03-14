
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
 *         &lt;element name="CreateSimpleFolderResult" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "createSimpleFolderResult"
})
@XmlRootElement(name = "CreateSimpleFolderResponse")
public class CreateSimpleFolderResponse {

    @XmlElement(name = "CreateSimpleFolderResult")
    protected long createSimpleFolderResult;

    /**
     * Recupera il valore della proprietà createSimpleFolderResult.
     * 
     */
    public long getCreateSimpleFolderResult() {
        return createSimpleFolderResult;
    }

    /**
     * Imposta il valore della proprietà createSimpleFolderResult.
     * 
     */
    public void setCreateSimpleFolderResult(long value) {
        this.createSimpleFolderResult = value;
    }

}
