/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fileOperationInput" type="{it.eng.fileoperation.ws}InputFileOperationType"/&gt;
 *         &lt;element ref="{it.eng.fileoperation.ws}operations"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fileOperationInput",
    "operations"
})
public class FileOperationRequest {

    @XmlElement(required = true)
    protected InputFileOperationType fileOperationInput;
    @XmlElement(required = true)
    protected Operations operations;

    /**
     * Recupera il valore della proprietà fileOperationInput.
     * 
     * @return
     *     possible object is
     *     {@link InputFileOperationType }
     *     
     */
    public InputFileOperationType getFileOperationInput() {
        return fileOperationInput;
    }

    /**
     * Imposta il valore della proprietà fileOperationInput.
     * 
     * @param value
     *     allowed object is
     *     {@link InputFileOperationType }
     *     
     */
    public void setFileOperationInput(InputFileOperationType value) {
        this.fileOperationInput = value;
    }

    /**
     * Recupera il valore della proprietà operations.
     * 
     * @return
     *     possible object is
     *     {@link Operations }
     *     
     */
    public Operations getOperations() {
        return operations;
    }

    /**
     * Imposta il valore della proprietà operations.
     * 
     * @param value
     *     allowed object is
     *     {@link Operations }
     *     
     */
    public void setOperations(Operations value) {
        this.operations = value;
    }

}
