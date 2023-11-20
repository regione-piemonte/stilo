/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per InputFileOperationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputFileOperationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="inputType" type="{it.eng.fileoperation.ws}InputFile"/&gt;
 *         &lt;element name="timeStampFile" type="{it.eng.fileoperation.ws}InputFile" minOccurs="0"/&gt;
 *         &lt;element name="originalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="timeStampReference" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputFileOperationType", propOrder = {
    "inputType",
    "timeStampFile",
    "originalName",
    "timeStampReference"
})
public class InputFileOperationType {

    @XmlElement(required = true)
    protected InputFile inputType;
    protected InputFile timeStampFile;
    protected String originalName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStampReference;

    /**
     * Recupera il valore della proprietà inputType.
     * 
     * @return
     *     possible object is
     *     {@link InputFile }
     *     
     */
    public InputFile getInputType() {
        return inputType;
    }

    /**
     * Imposta il valore della proprietà inputType.
     * 
     * @param value
     *     allowed object is
     *     {@link InputFile }
     *     
     */
    public void setInputType(InputFile value) {
        this.inputType = value;
    }

    /**
     * Recupera il valore della proprietà timeStampFile.
     * 
     * @return
     *     possible object is
     *     {@link InputFile }
     *     
     */
    public InputFile getTimeStampFile() {
        return timeStampFile;
    }

    /**
     * Imposta il valore della proprietà timeStampFile.
     * 
     * @param value
     *     allowed object is
     *     {@link InputFile }
     *     
     */
    public void setTimeStampFile(InputFile value) {
        this.timeStampFile = value;
    }

    /**
     * Recupera il valore della proprietà originalName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Imposta il valore della proprietà originalName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalName(String value) {
        this.originalName = value;
    }

    /**
     * Recupera il valore della proprietà timeStampReference.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStampReference() {
        return timeStampReference;
    }

    /**
     * Imposta il valore della proprietà timeStampReference.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStampReference(XMLGregorianCalendar value) {
        this.timeStampReference = value;
    }

}
