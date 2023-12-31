/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 10:39:55 AM CET 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * possibili modi per descrive il file immagine per la copia conforme
 * 
 * <p>Java class for ImageFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImageFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="fileStream" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="fileUrl" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageFile", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "fileStream",
    "fileUrl"
})
public class ImageFile
    implements Serializable
{

    protected DataHandler fileStream;
    @XmlSchemaType(name = "anyURI")
    protected String fileUrl;

    /**
     * Gets the value of the fileStream property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public DataHandler getFileStream() {
        return fileStream;
    }

    /**
     * Sets the value of the fileStream property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFileStream(DataHandler value) {
        this.fileStream = ( value);
    }

    /**
     * Gets the value of the fileUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * Sets the value of the fileUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileUrl(String value) {
        this.fileUrl = value;
    }

}
